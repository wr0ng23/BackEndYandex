package dev.wr0ng23.backendyandex.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor  
public class ShopImportRequest {
    List<ShopUnitImport> items;
    LocalDateTime updateDate;
}
