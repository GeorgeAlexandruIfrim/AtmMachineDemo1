package com.georgeifrim.AtmMachineDemo1.repositories;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.georgeifrim.AtmMachineDemo1.repositories.CriticalDenominationLevelLogger.*;
@Repository
@Data
public class AtmRepository {
    Map<Denominations, Integer> atmStock = new TreeMap<>();
    private AtmRepository(){
        atmStock.put(Denominations.ONE_HUNDRED,50);
        atmStock.put(Denominations.FIFTY,50);
        atmStock.put(Denominations.TEN,100);
        atmStock.put(Denominations.FIVE,100);
        atmStock.put(Denominations.ONE,100);
    }
    public Map<Denominations, Integer> feedMoney(int amount){
       Map<Denominations, Integer> map = splitIntoDenominations(amount, Denominations.ONE_HUNDRED);
        for(Map.Entry<Denominations, Integer> entry : map.entrySet()){
            for(Map.Entry<Denominations, Integer> stockentry : atmStock.entrySet()){
                if(stockentry.getKey().equals(entry.getKey())){
                    atmStock.replace(stockentry.getKey(), stockentry.getValue() + entry.getValue());
                }
            }
        }
        return map;
    }
    public Map<Denominations, Integer> withdrawAmount(int amount){

        Map<Denominations, Integer> withdrawal = new TreeMap<>();

        for (Map.Entry<Denominations, Integer> stockentry : atmStock.entrySet()) {

            int noBanknotes = amount / stockentry.getKey().getDenominationValue();

            if (noBanknotes <= stockentry.getValue()) {
                atmStock.replace(stockentry.getKey(), stockentry.getValue() - noBanknotes);
                amount = amount - noBanknotes * stockentry.getKey().getDenominationValue();
                withdrawal.put(stockentry.getKey(), noBanknotes);
            } else {
                withdrawal.put(stockentry.getKey(), stockentry.getValue());
                amount = amount - stockentry.getValue() * stockentry.getKey().getDenominationValue();
                atmStock.replace(stockentry.getKey(), 0);
                int denIndex = Denominations.getDenominationsList().indexOf(stockentry.getKey());
                Map<Denominations, Integer> patch = splitIntoDenominations(amount, Denominations.getDenominationsList().get(denIndex + 1));
                withdrawal.putAll(patch);
            }
        }
        low_Denomination_Level_Alert(atmStock);
    return withdrawal;
    }
    public Map<Denominations, Integer> currentStock(){
        return atmStock;
    }
    public int currentStockValue(){
        int sum = 0;
        for(Map.Entry<Denominations,Integer> entries : atmStock.entrySet()){
            sum += entries.getKey().getDenominationValue() * entries.getValue();
        }
        return sum;
    }
    public Map<Denominations, Integer> splitIntoDenominations(int amount, Denominations den){

        Map<Denominations, Integer> map = new TreeMap<>();
        Integer[] denominations = {100,50,10,5,1};
        Denominations[] list = Denominations.values();

        int denIndex = List.of(denominations).indexOf(den.getDenominationValue());

        for(int i = denIndex; i<denominations.length; i++){
            int numberOfBanknotes = amount/denominations[i];
            amount = amount % denominations[i];
            for(Denominations d : list){
                if(d.getDenominationValue() == denominations[i])
                    map.put(d,numberOfBanknotes);
            }
        }
        return map;
    }
}
