package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountTypeResponse {

    private Long id;

    private String accountType;

    private String description;

    private Status status;
}