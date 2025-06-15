package com.pmspProject.pmsp.service;

import com.pmspProject.pmsp.dto.PaymentMethodDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentMethodService {
    PaymentMethodDTO createPaymentMethod(PaymentMethodDTO paymentMethodDTO);

    PaymentMethodDTO updatePaymentMethod(Long id, PaymentMethodDTO paymentMethodDTO);

    void deletePaymentMethod(Long id);

    PaymentMethodDTO getPaymentMethodById(Long id);

    Page<PaymentMethodDTO> getAllPaymentMethods(Pageable pageable);

    List<PaymentMethodDTO> getAllEnabledPaymentMethods();
}