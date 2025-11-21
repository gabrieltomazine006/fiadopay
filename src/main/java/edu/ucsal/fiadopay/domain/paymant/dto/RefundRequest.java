package edu.ucsal.fiadopay.domain.paymant.dto;

import jakarta.validation.constraints.NotBlank;

public record RefundRequest(
    @NotBlank String paymentId
) {}
