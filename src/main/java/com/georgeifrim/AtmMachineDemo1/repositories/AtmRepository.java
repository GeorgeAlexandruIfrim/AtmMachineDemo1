package com.georgeifrim.AtmMachineDemo1.repositories;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
@Repository
@Data
public class AtmRepository {
    private Map<Denominations, Integer> atmStock = new TreeMap<>();

    private AtmRepository(){
        atmStock.put(Denominations.ONE_HUNDRED,50);
        atmStock.put(Denominations.FIFTY,50);
        atmStock.put(Denominations.TEN,100);
        atmStock.put(Denominations.FIVE,100);
        atmStock.put(Denominations.ONE,100);
    }

    public Set<Map.Entry<Denominations, Integer>> getCurrentStockSet(){return atmStock.entrySet();}
    public int currentStockValue(){
        int sum = 0;
        for(Map.Entry<Denominations,Integer> entries : atmStock.entrySet()){
            sum += entries.getKey().getDenominationValue() * entries.getValue();
        }
        return sum;
    }

}
