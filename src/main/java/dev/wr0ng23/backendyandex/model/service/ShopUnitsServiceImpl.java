package dev.wr0ng23.backendyandex.model.service;

import dev.wr0ng23.backendyandex.enums.ShopUnitType;
import dev.wr0ng23.backendyandex.exception.NotFoundException;
import dev.wr0ng23.backendyandex.model.entity.ShopUnit;
import dev.wr0ng23.backendyandex.model.repository.ShopUnitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
        if (!children.isEmpty()) {
            int size = 0;
            int childrenSize = 0;
            shopUnit.setChildren(children);
            for (var child : children) {
                if (child.getType() == ShopUnitType.CATEGORY) {
                    childrenSize = findAllChildren(child);
                    size += childrenSize;
                }

                int price = 0;
                if (shopUnit.getPrice() != null) {
                    price = shopUnit.getPrice();
                }

                if (child.getType() == ShopUnitType.CATEGORY) {
                    price += (child.getPrice() == null) ? 0 : child.getPrice() * childrenSize;
                } else {
                    price += child.getPrice();
                    size++;
                }
                shopUnit.setPrice(price);
            }

            shopUnit.setPrice(shopUnit.getPrice() / size);

            return size;
        }
        return 0;
    }

    @Override
    public List<ShopUnit> getSales(LocalDateTime date) {
        return shopUnitsRepository.findAllByDateBetween(date.minusDays(1), date);
    }
}
