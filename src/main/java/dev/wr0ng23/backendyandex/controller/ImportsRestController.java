package dev.wr0ng23.backendyandex.controller;

import dev.wr0ng23.backendyandex.dto.ShopImportRequest;
import dev.wr0ng23.backendyandex.model.entity.ShopUnit;
import dev.wr0ng23.backendyandex.dto.Response;
import dev.wr0ng23.backendyandex.model.service.ShopUnitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ImportsRestController {
    ShopUnitsService shopUnitsService;

    @Autowired
    public ImportsRestController(ShopUnitsService shopUnitsService) {
        this.shopUnitsService = shopUnitsService;
    }

    @PostMapping("/imports")
    @ResponseStatus(value = HttpStatus.OK)
    public Response postShopUnits(@RequestBody ShopImportRequest shopImportRequest) {
        LocalDateTime date = shopImportRequest.updateDate();
        List<ShopUnit> shopUnits = shopImportRequest
                .items()
                .stream()
                .map(shopUnit -> shopUnit.toShopUnit(date))
                .toList();

        shopUnitsService.saveAll(shopUnits);

        return new Response(200, "Successful insertion or update!");
    }
}
