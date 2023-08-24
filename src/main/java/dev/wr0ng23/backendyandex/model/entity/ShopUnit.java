package dev.wr0ng23.backendyandex.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.wr0ng23.backendyandex.enums.ShopUnitType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "shop_unit")
@Getter
@Setter
@NoArgsConstructor
public class ShopUnit {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "parent_id")
    private UUID parentId;

    @Column(name = "price")
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ShopUnitType type;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    public static class ShopUnitBuilder {
        private static final ShopUnit shopUnit = new ShopUnit();

        public ShopUnitBuilder setId(UUID id) {
            shopUnit.id = id;
            return this;
        }

        public ShopUnitBuilder setName(String name) {
            shopUnit.name = name;
            return this;
        }

        public ShopUnitBuilder setParentId(UUID parentId) {
            shopUnit.parentId = parentId;
            return this;
        }

        public ShopUnitBuilder setPrice(Integer price) {
            shopUnit.price = price;
            return this;
        }

        public ShopUnitBuilder setType(ShopUnitType type) {
            shopUnit.type = type;
            return this;
        }

        public ShopUnitBuilder setDate(LocalDateTime date) {
            shopUnit.date = date;
            return this;
        }

        public ShopUnit build() {
            return shopUnit;
        }
    }
}
