package com.georgeifrim.AtmMachineDemo1.exceptions;

public class NotEnoughMoney extends RuntimeException {
    public NotEnoughMoney(){
        super("Not enough money in the ATM !");
    }
}
