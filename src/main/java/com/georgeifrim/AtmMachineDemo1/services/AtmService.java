package com.georgeifrim.AtmMachineDemo1.services;

import com.georgeifrim.AtmMachineDemo1.dtos.AmountFedDto;
import com.georgeifrim.AtmMachineDemo1.dtos.AmountWithdrawnDto;
import com.georgeifrim.AtmMachineDemo1.dtos.StockDto;
import com.georgeifrim.AtmMachineDemo1.exceptions.IllegalAmount;
import com.georgeifrim.AtmMachineDemo1.exceptions.NotEnoughMoney;
import com.georgeifrim.AtmMachineDemo1.repositories.AtmRepository;
import com.georgeifrim.AtmMachineDemo1.repositories.Denominations;
import com.georgeifrim.AtmMachineDemo1.repositories.LargeAmountWithdrawnAllert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

import static com.georgeifrim.AtmMachineDemo1.repositories.CriticalDenominationLevelLogger.lowDenominationLevelAlert;
import static com.georgeifrim.AtmMachineDemo1.repositories.LargeAmountWithdrawnAllert.largeAmountWithdrawn;

@Service
public class AtmService {
    private final AtmRepository atmRepository;
    @Autowired
    public AtmService(AtmRepository atmRepository){
        this.atmRepository = atmRepository;
    }

    public AmountWithdrawnDto withdrawAmount(int amount){
        if(amount<=0){
            throw new IllegalAmount();
        } else if(amount <= atmRepository.currentStockValue()){
            var initialStock = Map.copyOf(atmRepository.getAtmStock());
            var affectedStock =  withdrawAmountFromStock(amount);
            Map<Denominations, Integer> withdrawedMap = new TreeMap<>();

            for (Map.Entry<Denominations, Integer> mapEntry : initialStock.entrySet()){
                withdrawedMap.put(mapEntry.getKey(), initialStock.get(mapEntry.getKey()) - affectedStock.get(mapEntry.getKey()));
            }
            return new AmountWithdrawnDto(withdrawedMap);
        }else{
            throw new NotEnoughMoney();
        }
    }
    public Map<Denominations, Integer> withdrawAmountFromStock(int amount){

        largeAmountWithdrawn(amount);

        for (Map.Entry<Denominations, Integer> stockentry : atmRepository.getCurrentStockSet()) {

            int noBanknotes = amount / stockentry.getKey().getDenominationValue();

            if (noBanknotes <= stockentry.getValue()) {
                atmRepository.getAtmStock().replace(stockentry.getKey(), stockentry.getValue() - noBanknotes);
                amount = amount - noBanknotes * stockentry.getKey().getDenominationValue();
            } else {
                amount = amount - stockentry.getValue() * stockentry.getKey().getDenominationValue();
                atmRepository.getAtmStock().replace(stockentry.getKey(), 0);
            }
        }
        lowDenominationLevelAlert(atmRepository.getAtmStock());
        return atmRepository.getAtmStock();
    }

    public StockDto getStock(){
        return new StockDto(atmRepository.getAtmStock());
    }

    public AmountFedDto feedMoney(int amount) {
        Map<Denominations, Integer> map = splitIntoDenominations(amount);
        map.forEach((k,v) -> atmRepository.getAtmStock().merge(k,v,Integer::sum));
        return new AmountFedDto(map);
    }
    public Map<Denominations, Integer> splitIntoDenominations(int amount){

        Map<Denominations, Integer> map = new TreeMap<>();
        Denominations[] list = Denominations.values();

        for(Denominations den : list){
            int banknotes = amount/den.getDenominationValue();
            amount = amount % den.getDenominationValue();
            map.put(den, banknotes);
        }
        return map;
    }
}
