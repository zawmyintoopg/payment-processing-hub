package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankResponse {

    private Long id;

    private String bankCode;

    private String bankName;

    private String shortName;

    private Status status;
}