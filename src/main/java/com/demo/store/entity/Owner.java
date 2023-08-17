package com.demo.store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
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
public class Owner extends CustomBaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3567651028631057234L;

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    private Store store;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    private String avatar;

}
