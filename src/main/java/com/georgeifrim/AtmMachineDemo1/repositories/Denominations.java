package com.georgeifrim.AtmMachineDemo1.repositories;

import java.util.List;
public enum Denominations {
    ONE_HUNDRED(100), FIFTY(50), TEN(10), FIVE(5), ONE(1);
    private final Integer denominations;
    Denominations(Integer denominations){
        this.denominations = denominations;
    }
    public int getDenominationValue(){
        return denominations;
    }
    public static List<Denominations> getDenominationsList(){
        return List.of(ONE_HUNDRED, FIFTY, TEN, FIVE, ONE);
    }
}
