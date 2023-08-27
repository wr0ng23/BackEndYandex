package dev.wr0ng23.backendyandex.controller;

import dev.wr0ng23.backendyandex.dto.ShopUnitStatisticResponse;
import dev.wr0ng23.backendyandex.dto.ShopUnitStatisticUnit;
import dev.wr0ng23.backendyandex.model.service.ShopUnitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class SalesController {
    ShopUnitsService shopUnitsService;

    @Autowired
    public SalesController(ShopUnitsService shopUnitsService) {
        this.shopUnitsService = shopUnitsService;
    }

    @GetMapping("/sales")
    @ResponseStatus(value = HttpStatus.OK)
    public ShopUnitStatisticResponse getSales(@RequestParam(name = "date")
                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                              LocalDateTime date) {
        var sales = shopUnitsService.getSales(date)
                .stream()
                .map(ShopUnitStatisticUnit::fromShopUnit)
                .toList();

        return new ShopUnitStatisticResponse(sales);
    }
}
