package dev.wr0ng23.backendyandex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.wr0ng23.backendyandex.model.entity.ShopUnit;
import dev.wr0ng23.backendyandex.enums.ShopUnitType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopUnitImport {
    private UUID id;
    private String name;
    @JsonProperty(value = "parent_id")
    private UUID parentId;
    private Integer price;
    private ShopUnitType type;

    public ShopUnit toShopUnit(LocalDateTime date) {
        return new ShopUnit.ShopUnitBuilder()
                .setId(id)
                .setName(name)
                .setParentId(parentId)
                .setPrice(price)
                .setType(type)
                .setDate(date)
                .build();
    }
}
