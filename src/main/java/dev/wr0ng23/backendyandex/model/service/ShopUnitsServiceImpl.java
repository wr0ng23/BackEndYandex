package dev.wr0ng23.backendyandex.model.service;

import dev.wr0ng23.backendyandex.exception.NotFoundException;
import dev.wr0ng23.backendyandex.model.entity.ShopUnit;
import dev.wr0ng23.backendyandex.model.repository.ImportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShopUnitsServiceImpl implements ShopUnitsService {
    ImportRepository importRepository;

    @Autowired
    public ShopUnitsServiceImpl(ImportRepository importRepository) {
        this.importRepository = importRepository;
    }

    @Override
    public void saveAll(List<ShopUnit> shopUnits) {
        importRepository.saveAll(shopUnits);
    }

    @Override
    public void deleteItem(UUID uuid) {
        var shopUnit = importRepository.findById(uuid);
        if (shopUnit.isEmpty()) {
            throw new NotFoundException("Item not found");
        }

        deleteChildren(uuid);
    }

    private void deleteChildren(UUID uuid) {
        List<ShopUnit> children = importRepository.findAllByParentId(uuid);
        if (!children.isEmpty()) {
            children.forEach(child -> deleteChildren(child.getId()));
        }
        importRepository.deleteById(uuid);
    }

    @Override
    public ShopUnit getItem(UUID uuid) {
        return null;
    }
}
