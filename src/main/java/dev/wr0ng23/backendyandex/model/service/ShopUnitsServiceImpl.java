package dev.wr0ng23.backendyandex.model.service;

import dev.wr0ng23.backendyandex.enums.ShopUnitType;
import dev.wr0ng23.backendyandex.exception.NotFoundException;
import dev.wr0ng23.backendyandex.model.entity.ShopUnit;
import dev.wr0ng23.backendyandex.model.repository.ShopUnitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShopUnitsServiceImpl implements ShopUnitsService {
    ShopUnitsRepository shopUnitsRepository;

    @Autowired
    public ShopUnitsServiceImpl(ShopUnitsRepository shopUnitsRepository) {
        this.shopUnitsRepository = shopUnitsRepository;
    }

    @Override
    public void saveAll(List<ShopUnit> shopUnits) {
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

    private void findAllChildren(ShopUnit shopUnit) {
        var children = shopUnitsRepository.findAllByParentId(shopUnit.getId());
        if (!children.isEmpty()) {
            shopUnit.setChildren(children);
            children.forEach(child -> {
                if (child.getType() == ShopUnitType.CATEGORY) {
                    findAllChildren(child);
                }
                //Strashno
                shopUnit.setPrice(((shopUnit.getPrice() == null) ? 0 : shopUnit.getPrice())
                        + ((child.getPrice() == null) ? 0 : child.getPrice()));
            });
            shopUnit.setPrice(shopUnit.getPrice() / children.size());
        }
    }
}
