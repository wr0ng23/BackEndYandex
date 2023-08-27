package dev.wr0ng23.backendyandex.model.repository;

import dev.wr0ng23.backendyandex.model.entity.ShopUnit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ShopUnitsRepository extends CrudRepository<ShopUnit, UUID> {
    List<ShopUnit> findAllByParentId(UUID uuid);

    @Query("select u from ShopUnit u where u.date between ?1 and ?2 and u.type='OFFER'")
    List<ShopUnit> findAllByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
