package com.georgeifrim.AtmMachineDemo1.repositories;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
@Slf4j
public class CriticalDenominationLevelLogger {
    static void low_Denomination_Level_Alert(Map<Denominations, Integer> atmStock){

        if (atmStock.get(Denominations.ONE_HUNDRED) <= 5) {
            log.warn("One Hundred Dollar Bill - Critical Level <10% - admin@atm.ro");
        } else {
            if (atmStock.get(Denominations.ONE_HUNDRED) <= 10) {
                log.warn("One Hundred Dollar Bill - Critical Level <20% - +407123456");
            }
        }
        if(atmStock.get(Denominations.FIFTY) <= 7){
            log.warn("Fifty Dollar Bill - Critical Level <20% - admin@atm.ro - +407123456");
        }
    }
}
