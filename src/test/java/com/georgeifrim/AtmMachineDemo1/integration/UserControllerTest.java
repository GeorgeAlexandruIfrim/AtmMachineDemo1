package com.georgeifrim.AtmMachineDemo1.integration;

import com.georgeifrim.AtmMachineDemo1.repositories.AtmRepository;
import com.georgeifrim.AtmMachineDemo1.repositories.Denominations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Map;
import java.util.TreeMap;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AtmRepository atmRepository;

    @Test
    public void withdrawAmount() throws Exception {

        Map<Denominations, Integer> initialAtmStock = new TreeMap<>();
        initialAtmStock.put(Denominations.ONE_HUNDRED, 50);
        initialAtmStock.put(Denominations.FIFTY, 50);
        initialAtmStock.put(Denominations.TEN, 100);
        initialAtmStock.put(Denominations.FIVE, 100);
        initialAtmStock.put(Denominations.ONE, 100);

        when(atmRepository.getAtmStock()).thenReturn(initialAtmStock);
        when(atmRepository.getCurrentStockSet()).thenReturn(initialAtmStock.entrySet());
        when(atmRepository.currentStockValue()).thenReturn(9100);

        mockMvc.perform(get("/withdrawAmount/{amount}","166"))
                .andExpect(jsonPath("$.oneHundredDollars").value("49"))
                .andExpect(jsonPath("$.fiftyDollars").value("49"))
                .andExpect(jsonPath("$.tenDollars").value("99"))
                .andExpect(jsonPath("$.fiveDollars").value("99"))
                .andExpect(jsonPath("$.oneDollars").value("99"));
    }

}
