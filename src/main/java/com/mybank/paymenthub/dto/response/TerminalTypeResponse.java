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
public class TerminalTypeResponse {

    private Long id;

    private String typeCode;

    private String typeName;

    private Status status;
}