package com.springboot.jpa.data.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "product_datail")
@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ProductDetail extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String decription;

    @OneToOne
    @JoinColumn(name = "product_number") // 매핑할 외래키 설정, name을 통해 명확하게 조인할 컬럼을 지정해주는것이 좋다.
    private Product product;

}
