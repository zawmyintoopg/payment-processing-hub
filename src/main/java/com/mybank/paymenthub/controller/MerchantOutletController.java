package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.request.MerchantOutletRequestDTO;
import com.mybank.paymenthub.dto.response.ApiResponse;
import com.mybank.paymenthub.dto.response.MerchantOutletResponseDTO;
import com.mybank.paymenthub.dto.response.MerchantResponseDTO;
import com.mybank.paymenthub.entity.MerchantOutlet;
import com.mybank.paymenthub.mapper.MerchantOutletMapper;
import com.mybank.paymenthub.service.MerchantOutletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchant-outlet")
public class MerchantOutletController {
    //CRUD Search , Pagination

    private final MerchantOutletService merchantOutletService;
    //CREATE MERCHANT OUTLET
    @PostMapping
    public ResponseEntity<ApiResponse<MerchantOutletResponseDTO>> createMerchantOutlet(@Valid @RequestBody MerchantOutletRequestDTO merchantOutletRequestDTO) {

        MerchantOutletResponseDTO responseDTO = merchantOutletService.createMerchantOutlet(merchantOutletRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).
               body(
                       ApiResponse.success(
                               "201",
                               "Merchant Outlet Successfully",
                               responseDTO
                       )
               );

        }
        // GET MERCHANT LIST
    @GetMapping
    public ResponseEntity<ApiResponse<List<MerchantOutletResponseDTO>>> getAllMerchantOutletList(){
         //assgiment
         List<MerchantOutletResponseDTO> responseDTO = merchantOutletService.getAllMerchantOutletList();

         return ResponseEntity.status(HttpStatus.CREATED).body(
                 ApiResponse.success(
                         "201",
                         "Merchant Outlet Retried Successfully",
                         responseDTO
                 )
         );
    }


}
