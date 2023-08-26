package dev.wr0ng23.backendyandex.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.wr0ng23.backendyandex.enums.ShopUnitType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shop_unit")
public class ShopUnit {
    @Id
    @Column(name = "id", nullable = false)
    @JsonProperty(index = 1)
    private UUID id;

    @Column(name = "name", nullable = false)
    @JsonProperty(index = 2)
    private String name;

    @Column(name = "parent_id")
    @JsonProperty(index = 3)
    private UUID parentId;

    @Column(name = "price")
    @JsonProperty(index = 4)
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    @JsonProperty(index = 5)
    private ShopUnitType type;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Column(name = "date", nullable = false)
    @JsonProperty(index = 6)
    private LocalDateTime date;

    // This field will be ignoring
    // when we will be persisted object into database
    @Transient
    @JsonProperty(index = 7)
    private List<ShopUnit> children;

    public static class ShopUnitBuilder {
        private final ShopUnit shopUnit = new ShopUnit();

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
