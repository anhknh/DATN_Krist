package com.example.kristp.payment;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VNPayRequest {
    private String orderId;
    private String amount;
    private String orderInfo;
    private String bankCode;
    private String returnUrl;
    private String orderIdSuccess;
}
