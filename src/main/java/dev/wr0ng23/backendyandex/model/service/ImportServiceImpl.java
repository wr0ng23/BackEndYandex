package dev.wr0ng23.backendyandex.model.service;

import dev.wr0ng23.backendyandex.model.entity.ShopUnit;
import dev.wr0ng23.backendyandex.model.repository.ImportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportServiceImpl implements ImportService {
    ImportRepository importRepository;

    @Autowired
    public ImportServiceImpl(ImportRepository importRepository) {
        this.importRepository = importRepository;
    }

    @Override
    public void saveAll(List<ShopUnit> shopUnits) {
        importRepository.saveAll(shopUnits);
    }
}
