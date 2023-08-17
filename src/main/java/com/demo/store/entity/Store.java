package com.demo.store.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Table
@Entity
@DynamicUpdate
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Store extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -7853043642039334491L;

    private String name;

    @Column(unique = true)
    private String slug;

    private String logo;

    private String banner;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    @Column(name = "b_active")
    private Boolean isActive = Boolean.TRUE;

    @OneToOne(mappedBy = "store", cascade = CascadeType.ALL)
    private Owner owner;

    @OneToMany(mappedBy = "store", orphanRemoval = true)
    private List<Product> products;

    public Store() {
        this.products = new ArrayList<>();
    }

    public void setProducts(List<Product> products) {
        if (products != null) {
            this.products.clear();
            this.products.addAll(products);
        }
    }

}
