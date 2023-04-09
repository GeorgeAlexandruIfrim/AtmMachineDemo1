package com.georgeifrim.AtmMachineDemo1.unit;

import com.georgeifrim.AtmMachineDemo1.repositories.AtmRepository;
import com.georgeifrim.AtmMachineDemo1.repositories.Denominations;
import com.georgeifrim.AtmMachineDemo1.services.AtmService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class ServiceTests {

    @MockBean
    private AtmRepository atmRepository;
    @Autowired
    private AtmService atmService;

    @Test
    public void withrawAmount(){
        var initialStockSet = new HashSet<Map.Entry<Denominations, Integer>>();
        initialStockSet.add(Map.entry(Denominations.ONE_HUNDRED, 50));
        initialStockSet.add(Map.entry(Denominations.FIFTY, 50));
        initialStockSet.add(Map.entry(Denominations.TEN, 100));
        initialStockSet.add(Map.entry(Denominations.FIVE, 100));
        initialStockSet.add(Map.entry(Denominations.ONE, 100));

        var stockAfterWithrawal = new TreeMap<Denominations, Integer>();
        stockAfterWithrawal.put(Denominations.ONE_HUNDRED, 49);
        stockAfterWithrawal.put(Denominations.FIFTY, 49);
        stockAfterWithrawal.put(Denominations.TEN, 99);
        stockAfterWithrawal.put(Denominations.FIVE,99);
        stockAfterWithrawal.put(Denominations.ONE,99);


        given(atmRepository.getCurrentStockSet()).willReturn(initialStockSet);
        given(atmRepository.getAtmStock()).willReturn(stockAfterWithrawal);

        var affectedStock = atmService.withdrawAmountFromStock(166);

        assertThat(affectedStock).containsExactlyEntriesOf(stockAfterWithrawal);
    }
}
