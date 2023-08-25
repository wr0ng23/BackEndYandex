package dev.wr0ng23.backendyandex.controller;

import dev.wr0ng23.backendyandex.dto.Response;
import dev.wr0ng23.backendyandex.model.service.ShopUnitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class DeleteItemRestController {
    ShopUnitsService shopUnitsService;

    @Autowired
    public DeleteItemRestController(ShopUnitsService shopUnitsService) {
        this.shopUnitsService = shopUnitsService;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Response deleteItem(@PathVariable(name = "id") UUID uuid) {
        shopUnitsService.deleteItem(uuid);
        return new Response(200, "Success Deletion");
    }
}
