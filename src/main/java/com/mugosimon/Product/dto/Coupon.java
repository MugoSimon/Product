package com.mugosimon.Product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Coupon {
    private Long id;
    private String code;
    private BigDecimal discount;
    @JsonProperty("expiration_date")
    private String expirationDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Character something_else = 'N';

    public Coupon(Long id, String code, BigDecimal discount, String expirationDate) {
        this.id = id;
        this.code = code;
        this.discount = discount;
        this.expirationDate = expirationDate;
    }
}
