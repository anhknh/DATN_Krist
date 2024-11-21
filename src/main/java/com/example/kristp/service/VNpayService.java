package com.example.kristp.service;

import com.example.kristp.payment.VNPayRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface VNpayService {
    String  createPayment(VNPayRequest request, HttpServletRequest reqs) throws Exception;
}
