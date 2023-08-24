package dev.wr0ng23.backendyandex.model.service;

import dev.wr0ng23.backendyandex.model.entity.ShopUnit;

import java.util.List;

public interface ImportService {
    void saveAll(List<ShopUnit> shopUnits);
}
