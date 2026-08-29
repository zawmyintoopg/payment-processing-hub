package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.request.TerminalTypeRequest;
import com.mybank.paymenthub.dto.response.TerminalTypeResponse;
import com.mybank.paymenthub.service.TerminalTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/terminal-types")
@RequiredArgsConstructor
@Tag(
        name= "07- Terminal Type"
)
public class TerminalTypeController {

    private final TerminalTypeService terminalTypeService;

    // Create
    @PostMapping
    public ResponseEntity<TerminalTypeResponse> createTerminalType(
            @RequestBody TerminalTypeRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        terminalTypeService.createTerminalType(request)
                );
    }

    // Get All / Search / Pagination
    @GetMapping
    public ResponseEntity<Page<TerminalTypeResponse>> getAllTerminalTypes(
            @RequestParam(required = false) String search,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(
                terminalTypeService.getAllTerminalTypes(
                        search,
                        pageable
                )
        );
    }

    // Get By ID
    @GetMapping("/{id}")
    public ResponseEntity<TerminalTypeResponse> getTerminalTypeById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                terminalTypeService.getTerminalTypeById(id)
        );
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<TerminalTypeResponse> updateTerminalType(
            @PathVariable Long id,
            @RequestBody TerminalTypeRequest request
    ) {
        return ResponseEntity.ok(
                terminalTypeService.updateTerminalType(
                        id,
                        request
                )
        );
    }

    // Deactivate
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deActivateTerminalType(
            @PathVariable Long id
    ) {
        terminalTypeService.deActivateTerminalType(id);

        return ResponseEntity.noContent().build();
    }

    // Activate
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateTerminalType(
            @PathVariable Long id
    ) {
        terminalTypeService.activateTerminalType(id);

        return ResponseEntity.noContent().build();
    }
}