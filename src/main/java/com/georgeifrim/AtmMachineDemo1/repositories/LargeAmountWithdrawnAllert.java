package com.georgeifrim.AtmMachineDemo1.repositories;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LargeAmountWithdrawnAllert {

    public static void largeAmountWithdrawn(int amount){
        if(amount > 200){
            log.warn("A large amount is withdrawn ! - @email is sent");
        }
    }
}
