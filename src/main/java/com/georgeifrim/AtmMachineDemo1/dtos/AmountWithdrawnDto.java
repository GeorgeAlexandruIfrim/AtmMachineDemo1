package com.georgeifrim.AtmMachineDemo1.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.georgeifrim.AtmMachineDemo1.repositories.Denominations;

import java.util.Map;
import java.util.Objects;

public class AmountWithdrawnDto {

    int oneHundredDollars;
    int fiftyDollars;
    int tenDollars;
    int fiveDollars;
    int oneDollars;

    public AmountWithdrawnDto(Map<Denominations, Integer> map){
        this.oneHundredDollars = map.get(Denominations.ONE_HUNDRED);
        this.fiftyDollars = map.get(Denominations.FIFTY);
        this.tenDollars = map.get(Denominations.TEN);
        this.fiveDollars = map.get(Denominations.FIVE);
        this.oneDollars = map.get(Denominations.ONE);
    }

    @JsonProperty("One Hundred Dollar Bills")
    public int getOneHundredDollars() {
        return oneHundredDollars;
    }

    public void setOneHundredDollars(int oneHundredDollars) {
        this.oneHundredDollars = oneHundredDollars;
    }
    @JsonProperty("Fifty Dollar Bills")
    public int getFiftyDollars() {
        return fiftyDollars;
    }

    public void setFiftyDollars(int fiftyDollars) {
        this.fiftyDollars = fiftyDollars;
    }
    @JsonProperty("Ten Dollar Bills")
    public int getTenDollars() {
        return tenDollars;
    }

    public void setTenDollars(int tenDollars) {
        this.tenDollars = tenDollars;
    }
    @JsonProperty("Five Dollar Bills")
    public int getFiveDollars() {
        return fiveDollars;
    }

    public void setFiveDollars(int fiveDollars) {
        this.fiveDollars = fiveDollars;
    }
    @JsonProperty("One Dollar Bills")
    public int getOneDollars() {
        return oneDollars;
    }

    public void setOneDollars(int oneDollars) {
        this.oneDollars = oneDollars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmountWithdrawnDto that = (AmountWithdrawnDto) o;
        return getOneHundredDollars() == that.getOneHundredDollars() && getFiftyDollars() == that.getFiftyDollars() && getTenDollars() == that.getTenDollars() && getFiveDollars() == that.getFiveDollars() && getOneDollars() == that.getOneDollars();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOneHundredDollars(), getFiftyDollars(), getTenDollars(), getFiveDollars(), getOneDollars());
    }
}
