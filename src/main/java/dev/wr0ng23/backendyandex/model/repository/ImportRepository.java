package dev.wr0ng23.backendyandex.model.repository;

import dev.wr0ng23.backendyandex.model.entity.ShopUnit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ImportRepository extends CrudRepository<ShopUnit, UUID> {
    List<ShopUnit> findAllByParentId(UUID uuid);
}
