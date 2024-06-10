package com.mugosimon.Product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CouponResponse {
    private String message;
    private Coupon entity;
    private int statusCode;
}
