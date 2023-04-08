package com.georgeifrim.AtmMachineDemo1.controllers;

import com.georgeifrim.AtmMachineDemo1.dtos.AmountDto;
import com.georgeifrim.AtmMachineDemo1.services.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/admin")
public class AtmAdminController {
    private final AtmService atmService;

    @Autowired
    public AtmAdminController(AtmService atmService){
        this.atmService = atmService;
    }

    @GetMapping("/currentStock")
    public AmountDto getStock(){
        return atmService.getStock();
    }

    @PutMapping("/feedMoney/{amount}")
    public AmountDto feedMoney(@PathVariable int amount){
        return atmService.feedMoney(amount);
    }
}
