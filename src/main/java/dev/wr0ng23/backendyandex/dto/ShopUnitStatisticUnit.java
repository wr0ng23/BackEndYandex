package dev.wr0ng23.backendyandex.dto;

import dev.wr0ng23.backendyandex.enums.ShopUnitType;
import dev.wr0ng23.backendyandex.model.entity.ShopUnit;

import java.time.LocalDateTime;
import java.util.UUID;


public record ShopUnitStatisticUnit(
        UUID id,
        String name,
        UUID parentId,
        ShopUnitType type,
        Integer price,
        LocalDateTime date) {

    public static ShopUnitStatisticUnit fromShopUnit(ShopUnit shopUnit) {
        return new ShopUnitStatisticUnit(
                shopUnit.getId(),
                shopUnit.getName(),
                shopUnit.getParentId(),
                shopUnit.getType(),
                shopUnit.getPrice(),
                shopUnit.getDate());
    }
}
