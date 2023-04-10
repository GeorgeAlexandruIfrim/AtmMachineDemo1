package com.georgeifrim.AtmMachineDemo1.unit;

import com.georgeifrim.AtmMachineDemo1.dtos.AmountFedDto;
import com.georgeifrim.AtmMachineDemo1.dtos.AmountWithdrawnDto;
import com.georgeifrim.AtmMachineDemo1.exceptions.NotEnoughMoney;
import com.georgeifrim.AtmMachineDemo1.repositories.AtmRepository;
import com.georgeifrim.AtmMachineDemo1.repositories.Denominations;
import com.georgeifrim.AtmMachineDemo1.services.AtmService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class ServiceTests {

    @MockBean
    private AtmRepository atmRepository;
    @Autowired
    private AtmService atmService;

    @Test
    @DisplayName("withdrawAmountFromStock")
    public void withdrawAmountFromStockWithEnoughBanknotes(){
        Map<Denominations, Integer> initialAtmStock = new TreeMap<>();
        initialAtmStock.put(Denominations.ONE_HUNDRED, 50);
        initialAtmStock.put(Denominations.FIFTY, 50);
        initialAtmStock.put(Denominations.TEN, 100);
        initialAtmStock.put(Denominations.FIVE, 100);
        initialAtmStock.put(Denominations.ONE, 100);

        var stockAfterWithrawal = new TreeMap<Denominations, Integer>();
        stockAfterWithrawal.put(Denominations.ONE_HUNDRED, 49);
        stockAfterWithrawal.put(Denominations.FIFTY, 49);
        stockAfterWithrawal.put(Denominations.TEN, 99);
        stockAfterWithrawal.put(Denominations.FIVE,99);
        stockAfterWithrawal.put(Denominations.ONE,99);


        given(atmRepository.getCurrentStockSet()).willReturn(initialAtmStock.entrySet());
        given(atmRepository.getAtmStock()).willReturn(initialAtmStock);

        var affectedStock = atmService.withdrawAmountFromStock(166);

        assertThat(affectedStock).containsExactlyEntriesOf(stockAfterWithrawal);
    }

    @Test
    @DisplayName("withdrawAmountFromStock")
    public void withdrawAmountFromStockWithNotEnoughBanknotes(){
        Map<Denominations, Integer> initialAtmStock = new TreeMap<>();
        initialAtmStock.put(Denominations.ONE_HUNDRED, 1);
        initialAtmStock.put(Denominations.FIFTY, 50);
        initialAtmStock.put(Denominations.TEN, 100);
        initialAtmStock.put(Denominations.FIVE, 100);
        initialAtmStock.put(Denominations.ONE, 100);

        var stockAfterWithrawal = new TreeMap<Denominations, Integer>();
        stockAfterWithrawal.put(Denominations.ONE_HUNDRED, 0);
        stockAfterWithrawal.put(Denominations.FIFTY, 47);
        stockAfterWithrawal.put(Denominations.TEN, 100);
        stockAfterWithrawal.put(Denominations.FIVE,100);
        stockAfterWithrawal.put(Denominations.ONE,100);


        given(atmRepository.getCurrentStockSet()).willReturn(initialAtmStock.entrySet());
        given(atmRepository.getAtmStock()).willReturn(initialAtmStock);

        var affectedStock = atmService.withdrawAmountFromStock(250);

        assertThat(affectedStock).containsExactlyEntriesOf(stockAfterWithrawal);
    }

    @Test
    @DisplayName("withdrawAmountFromStock")
    public void withdrawAmountFromStockWithZeroBanknotes(){
        Map<Denominations, Integer> initialAtmStock = new TreeMap<>();
        initialAtmStock.put(Denominations.ONE_HUNDRED, 0);
        initialAtmStock.put(Denominations.FIFTY, 50);
        initialAtmStock.put(Denominations.TEN, 100);
        initialAtmStock.put(Denominations.FIVE, 100);
        initialAtmStock.put(Denominations.ONE, 100);

        var stockAfterWithrawal = new TreeMap<Denominations, Integer>();
        stockAfterWithrawal.put(Denominations.ONE_HUNDRED, 0);
        stockAfterWithrawal.put(Denominations.FIFTY, 45);
        stockAfterWithrawal.put(Denominations.TEN, 100);
        stockAfterWithrawal.put(Denominations.FIVE,100);
        stockAfterWithrawal.put(Denominations.ONE,100);


        given(atmRepository.getCurrentStockSet()).willReturn(initialAtmStock.entrySet());
        given(atmRepository.getAtmStock()).willReturn(initialAtmStock);

        var affectedStock = atmService.withdrawAmountFromStock(250);

        assertThat(affectedStock).containsExactlyEntriesOf(stockAfterWithrawal);
    }

    @Test
    @DisplayName("withdrawAmount")
    public void withdrawAmountNotEnoughMoney() {

        given(atmRepository.currentStockValue()).willReturn(100);

        assertThrows(NotEnoughMoney.class, () -> atmService.withdrawAmount(166));
    }

    @Test
    @DisplayName("withdrawAmount")
    public void withdrawAmountWithEnoughMoneyReturnsRightAmountWithdrewDto() {

        Map<Denominations, Integer> initialAtmStock = new TreeMap<>();
        initialAtmStock.put(Denominations.ONE_HUNDRED, 50);
        initialAtmStock.put(Denominations.FIFTY, 50);
        initialAtmStock.put(Denominations.TEN, 100);
        initialAtmStock.put(Denominations.FIVE, 100);
        initialAtmStock.put(Denominations.ONE, 100);

        var withdrawnedMap = new TreeMap<Denominations, Integer>();
        withdrawnedMap.put(Denominations.ONE_HUNDRED, 1);
        withdrawnedMap.put(Denominations.FIFTY, 1);
        withdrawnedMap.put(Denominations.TEN, 1);
        withdrawnedMap.put(Denominations.FIVE,1);
        withdrawnedMap.put(Denominations.ONE,1);

        given(atmRepository.currentStockValue()).willReturn(9100);
        given(atmRepository.getCurrentStockSet()).willReturn(initialAtmStock.entrySet());
        given(atmRepository.getAtmStock()).willReturn(initialAtmStock);

        var withdrawnAmountDTO = atmService.withdrawAmount(166);

        assertEquals(withdrawnAmountDTO, new AmountWithdrawnDto(withdrawnedMap));
    }

//    @Test
//    @DisplayName("feedMoney")
//    public void feedMoneyCorrectlyAffectsStock(){
//        var initialAtmStock = new TreeMap<Denominations, Integer>();
//        initialAtmStock.put(Denominations.ONE_HUNDRED, 50);
//        initialAtmStock.put(Denominations.FIFTY, 50);
//        initialAtmStock.put(Denominations.TEN, 100);
//        initialAtmStock.put(Denominations.FIVE, 100);
//        initialAtmStock.put(Denominations.ONE, 100);
//
//        var expectedStock = new TreeMap<Denominations, Integer>();
//        initialAtmStock.put(Denominations.ONE_HUNDRED, 51);
//        initialAtmStock.put(Denominations.FIFTY, 51);
//        initialAtmStock.put(Denominations.TEN, 101);
//        initialAtmStock.put(Denominations.FIVE, 101);
//        initialAtmStock.put(Denominations.ONE, 101);
//
//        given(atmRepository.getAtmStock()).willReturn(initialAtmStock);
//
//        var amountFedDto = atmService.feedMoney(166);
//
//        assertEquals(amountFedDto, new AmountFedDto(expectedStock));
//
//    }

    @Test
    @DisplayName("splitIntoDenominations")
    public void splitIntoDenominationsReturnsCorrectSplit(){
        var expectedSplit = new TreeMap<Denominations, Integer>();
        expectedSplit.put(Denominations.ONE_HUNDRED, 1);
        expectedSplit.put(Denominations.FIFTY, 1);
        expectedSplit.put(Denominations.TEN, 1);
        expectedSplit.put(Denominations.FIVE,1);
        expectedSplit.put(Denominations.ONE,1);

        var splitedAmount = atmService.splitIntoDenominations(166);

        assertThat(splitedAmount).containsExactlyEntriesOf(expectedSplit);
    }



}

