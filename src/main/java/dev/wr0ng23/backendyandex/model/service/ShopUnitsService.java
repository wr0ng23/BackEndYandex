package dev.wr0ng23.backendyandex.model.service;

import dev.wr0ng23.backendyandex.model.entity.ShopUnit;

import java.util.List;
import java.util.UUID;

public interface ShopUnitsService {
    void saveAll(List<ShopUnit> shopUnits);
    void deleteItem(UUID uuid);

    ShopUnit getItem(UUID uuid);
}
