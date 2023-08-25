package dev.wr0ng23.backendyandex.controller;

import dev.wr0ng23.backendyandex.model.entity.ShopUnit;
import dev.wr0ng23.backendyandex.model.service.ShopUnitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class NodesRestController {
    ShopUnitsService shopUnitsService;

    @Autowired
    public NodesRestController(ShopUnitsService shopUnitsService) {
        this.shopUnitsService = shopUnitsService;
    }

    @GetMapping("/nodes/{id}")
    public ShopUnit getShopUnit(@PathVariable(name = "id") UUID uuid) {
        return shopUnitsService.getItem(uuid);
    }
}
