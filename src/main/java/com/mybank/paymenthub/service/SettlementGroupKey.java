package com.mybank.paymenthub.service;

public record SettlementGroupKey(
        Long merchantId,
        Long currencyId
) {
}
