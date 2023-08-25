package dev.wr0ng23.backendyandex.dto;

import java.time.LocalDateTime;
import java.util.List;
public record ShopImportRequest (List<ShopUnitImport> items, LocalDateTime updateDate) { }
