package com.georgeifrim.AtmMachineDemo1.exceptions;

public class IllegalAmount extends RuntimeException {
    public IllegalAmount(){
        super("Insert an amount larger than 0 !");
    }

}
