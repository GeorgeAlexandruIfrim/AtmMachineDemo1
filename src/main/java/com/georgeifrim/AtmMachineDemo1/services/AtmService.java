package com.georgeifrim.AtmMachineDemo1.services;

import com.georgeifrim.AtmMachineDemo1.dtos.AmountDto;
import com.georgeifrim.AtmMachineDemo1.exceptions.IllegalAmount;
import com.georgeifrim.AtmMachineDemo1.exceptions.NotEnoughMoney;
import com.georgeifrim.AtmMachineDemo1.repositories.AtmRepository;
import com.georgeifrim.AtmMachineDemo1.repositories.Denominations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class AtmService {
    private final AtmRepository atmRepository;
    @Autowired
    public AtmService(AtmRepository atmRepository){
        this.atmRepository = atmRepository;
    }

    public Map<Denominations, Integer> withdrawAmount(int amount){
        if(amount<=0){
            throw new IllegalAmount();
        } else if(amount <= atmRepository.currentStockValue()){
            return atmRepository.withdrawAmount(amount);
        }else{
            throw new NotEnoughMoney();
        }
    }
    public AmountDto getStock(){
        return new AmountDto(atmRepository.currentStock());
    }

    public AmountDto feedMoney(int amount) {
        return new AmountDto(atmRepository.feedMoney(amount));
    }
}
