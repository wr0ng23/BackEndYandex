package dev.wr0ng23.backendyandex.model.service;

import dev.wr0ng23.backendyandex.enums.ShopUnitType;
import dev.wr0ng23.backendyandex.exception.NotFoundException;
import dev.wr0ng23.backendyandex.model.entity.ShopUnit;
import dev.wr0ng23.backendyandex.model.repository.ShopUnitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ShopUnitsServiceImpl implements ShopUnitsService {
    ShopUnitsRepository shopUnitsRepository;

    @Autowired
    public ShopUnitsServiceImpl(ShopUnitsRepository shopUnitsRepository) {
        this.shopUnitsRepository = shopUnitsRepository;
    }

    private void findAllParents(ShopUnit shopUnit) {
        if (shopUnit.getParentId() != null) {
            var parentOptional = shopUnitsRepository.findById(shopUnit.getParentId());
            if (parentOptional.isEmpty()) {
                return;
            }
            ShopUnit parent = parentOptional.get();
            parent.setDate(shopUnit.getDate());
            findAllParents(parent);
        }
    }

    @Override
    public void saveAll(List<ShopUnit> shopUnits) {
        // Pizdec circ
        // Update date..
        shopUnits.stream()
                .filter(shopUnit -> (shopUnit.getParentId() != null) &&
                        shopUnits.stream()
                                .allMatch(shopUnit1 ->
                                        shopUnit1.getId() != shopUnit.getParentId()))
                .forEach(this::findAllParents);

        shopUnitsRepository.saveAll(shopUnits);
    }

    @Override
    public void deleteItem(UUID uuid) {
        var shopUnit = shopUnitsRepository.findById(uuid);
        if (shopUnit.isEmpty()) {
            throw new NotFoundException("Item not found");
        }

        if (shopUnit.get().getType() == ShopUnitType.CATEGORY) {
            deleteChildren(uuid);
        }
    }

    private void deleteChildren(UUID uuid) {
        List<ShopUnit> children = shopUnitsRepository.findAllByParentId(uuid);
        if (!children.isEmpty()) {
            children.forEach(child -> deleteChildren(child.getId()));
        }
        shopUnitsRepository.deleteById(uuid);
    }

    @Override
    public ShopUnit getItem(UUID uuid) {
        var shopUnitOptional = shopUnitsRepository.findById(uuid);
        if (shopUnitOptional.isEmpty()) {
            throw new NotFoundException("Item not found");
        }

        ShopUnit shopUnit = shopUnitOptional.get();
        if (shopUnit.getType() == ShopUnitType.CATEGORY) {
            findAllChildren(shopUnit);
        }

        return shopUnit;
    }

    private int findAllChildren(ShopUnit shopUnit) {
        var children = shopUnitsRepository.findAllByParentId(shopUnit.getId());
        AtomicInteger size = new AtomicInteger();
        if (!children.isEmpty()) {
            shopUnit.setChildren(children);
            children.forEach(child -> {
                if (child.getType() == ShopUnitType.CATEGORY) {
                    size.addAndGet(findAllChildren(child));
                }
                //Strashno
                // Kogdato budet more beautiful but ne segodnya
                shopUnit.setPrice(((shopUnit.getPrice() == null) ?
                        0 : shopUnit.getPrice()) + ((child.getType() == ShopUnitType.CATEGORY) ?
                        child.getPrice() * child.getChildren().size() : child.getPrice()));
            });
            shopUnit.setPrice(shopUnit.getPrice() / ((size.get() == 0) ?
                    shopUnit.getChildren().size() : size.get()));
            return shopUnit.getChildren().size();
        }
        return 0;
    }
}
