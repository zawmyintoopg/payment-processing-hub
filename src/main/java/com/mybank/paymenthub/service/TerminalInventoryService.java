package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.request.TerminalInventoryRequestDTO;
import com.mybank.paymenthub.dto.response.TerminalInventoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TerminalInventoryService {

      TerminalInventoryResponse create(
              TerminalInventoryRequestDTO requestDTO
      );

      TerminalInventoryResponse update(
              TerminalInventoryRequestDTO requestDTO, Long id
      );

      Page<TerminalInventoryResponse> getAll(
              String search,
              Pageable pageable
      );

      TerminalInventoryResponse getById(
              Long id
      );

      void deActivate(
              Long id
      );

      void  activate(
              Long id
      );

}
