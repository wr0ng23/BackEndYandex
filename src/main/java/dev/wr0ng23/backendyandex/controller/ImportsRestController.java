package dev.wr0ng23.backendyandex.controller;

import dev.wr0ng23.backendyandex.dto.ShopImportRequest;
import dev.wr0ng23.backendyandex.model.entity.ShopUnit;
import dev.wr0ng23.backendyandex.model.reponse.ImportResponse;
import dev.wr0ng23.backendyandex.model.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@ResponseStatus(value = HttpStatus.OK)
public class ImportsRestController {
    ImportService importService;

    @Autowired
    public ImportsRestController(ImportService importService) {
        this.importService = importService;
    }

    @PostMapping("/imports")
    public ImportResponse getImports(@RequestBody ShopImportRequest shopImportRequest) {
        LocalDateTime date = shopImportRequest.getUpdateDate();
        List<ShopUnit> shopUnits = shopImportRequest
                .getItems()
                .stream()
                .map(shopUnit -> shopUnit.toShopUnit(date))
                .toList();

        importService.saveAll(shopUnits);

        return new ImportResponse(200);
    }
}
