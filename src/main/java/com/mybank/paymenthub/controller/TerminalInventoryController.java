package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.request.TerminalInventoryRequestDTO;
import com.mybank.paymenthub.dto.response.ApiResponse;
import com.mybank.paymenthub.dto.response.TerminalInventoryResponse;
import com.mybank.paymenthub.service.TerminalInventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/terminals")
@Tag(name = "08 - Terminal")
public class TerminalInventoryController {

    private final TerminalInventoryService terminalInventoryService;
    @PostMapping
    public ResponseEntity<ApiResponse<TerminalInventoryResponse>>
            create(
            @Valid
            @RequestBody TerminalInventoryRequestDTO request
    ){
        TerminalInventoryResponse response =
                terminalInventoryService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "201",
                                "Terminal Inventory Created Successfully",
                                response
                        )
                );
    }
    @Operation(
            summary = "Update"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TerminalInventoryResponse>>
        update(
                @PathVariable Long id,
                @Valid @RequestBody TerminalInventoryRequestDTO request
    ){
        TerminalInventoryResponse response =
            terminalInventoryService.update(request,id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Terminal Inventory Updated Successfully",
                        response
                )
        );

    }

    @Operation(
            summary = "Read All Terminal"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<Page<TerminalInventoryResponse>>>
            getAll(
                    @RequestParam (required = false) String search,
                    @ParameterObject
                    Pageable pageable
     ){
       Page<TerminalInventoryResponse> terminalInventoryResponsePage =
                terminalInventoryService.getAll(search,pageable);

       return ResponseEntity.ok(
               ApiResponse.success(
                       "200",
                       "Terminal Inventory Retrieved Successfully",
                       terminalInventoryResponsePage
               )
       );
    }

    @Operation(
            summary = "Read Terminal By ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TerminalInventoryResponse>>
            getById(@PathVariable Long id){

        TerminalInventoryResponse response =
                terminalInventoryService.getById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Terminal Inventory Retrieved Successfully",
                        response
                )
        );
    }

    @Operation(
            summary = "Deactivate"
    )
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>>
          deactivate(@PathVariable Long id){

        terminalInventoryService.deActivate(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Terminal Inventory DeActivated Successfully",
                        null
                )
        );
    }

    @Operation(
            summary = "Activate"
    )
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<Void>>
        activate(@PathVariable Long id){

        terminalInventoryService.activate(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Terminal Inventory Activated Successfully",
                        null
                )
        );
    }
}
