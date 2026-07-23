package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.request.MerchantRequestDTO;
import com.mybank.paymenthub.dto.response.ApiResponse;
import com.mybank.paymenthub.dto.response.MerchantResponseDTO;
import com.mybank.paymenthub.service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    // Show All Merchant
    @GetMapping
    public ResponseEntity<ApiResponse<List<MerchantResponseDTO>>> getAllMerchantList(){

        List<MerchantResponseDTO> response =  merchantService.getAllMerchantList();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "0000",
                        "Merchant Retrieved is Successfully",
                        response
                )
        );
    }
    // Show Merchant By Id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MerchantResponseDTO>> getMerchantById(@PathVariable Long id){
        MerchantResponseDTO responseDTO = merchantService.getMerchantById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "0000",
                        "Merchant Retrieved is Successfully",
                        responseDTO
                )
        );
    }
    //Search By Name
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<MerchantResponseDTO>>> searchByName(@RequestParam String name){

        List<MerchantResponseDTO> response = merchantService.searchMerchantByName(name);

        return  ResponseEntity.ok(
                ApiResponse.success(
                        "0000",
                        "Merchant Search Successfully",
                        response
                )
        );
    }

    //Create Merchants
    @PostMapping
    public ResponseEntity<ApiResponse<MerchantResponseDTO>>  createMerchant(
            @Valid @RequestBody MerchantRequestDTO requestDTO){

        MerchantResponseDTO responseDTO = merchantService.createMerchant(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                        "201",
                        "Merchant Created Successfully",
                        responseDTO
                )
        );
    }

    // Update Merchant
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MerchantResponseDTO>> updateMerchant(@PathVariable Long id, @Valid @RequestBody MerchantRequestDTO requestDTO){

        MerchantResponseDTO responseDTO =  merchantService.updateMerchant(id,requestDTO);

        return ResponseEntity.
                ok(
                ApiResponse.success(
                        "201",
                        "Merchant Updated Successfully",
                        responseDTO
                )
        );

    }
    @DeleteMapping("/{id}")
    // deactivate
    public ResponseEntity<ApiResponse<Void>> deactivateMerchant(
            @PathVariable Long id
    ){
        merchantService.deactivateMerchant(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "0003",
                        "Merchant deactivated successfully",
                        null
                )
        );
    }


    //pagination
    @GetMapping("/pagination")
    public ResponseEntity<ApiResponse<Page<MerchantResponseDTO>>> getPagination(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "20") int size){

            Page<MerchantResponseDTO> response =  merchantService.getMerchantPagination(page,size);

            return ResponseEntity.ok(
                    ApiResponse.success(
                            "0000",
                            "Merchant page Retrieved Successfully",
                            response
                    )
            );
    }

}
