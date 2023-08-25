package dev.wr0ng23.backendyandex.dto;

import dev.wr0ng23.backendyandex.enums.ShopUnitType;
import dev.wr0ng23.backendyandex.model.entity.ShopUnit;

import java.time.LocalDateTime;
import java.util.UUID;

public record ShopUnitImport(UUID id,
                             String name,
                             UUID parentId,
                             Integer price,
                             ShopUnitType type) {

    public ShopUnit toShopUnit(LocalDateTime date) {
        return new ShopUnit.ShopUnitBuilder()
                .setId(id)
                .setName(name)
                .setParentId(parentId)
                .setPrice(price)
                .setType(type)
                .setDate(date)
                .build();
    }
}
