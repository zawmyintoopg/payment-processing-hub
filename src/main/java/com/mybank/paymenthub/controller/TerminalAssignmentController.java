package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.request.TerminalAssignmentRequestDTO;
import com.mybank.paymenthub.dto.response.ApiResponse;
import com.mybank.paymenthub.dto.response.TerminalAssignmentResponseDTO;
import com.mybank.paymenthub.service.TerminalAssignmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@Tag(
        name ="Terminal Assignment Information",
        description = "terminal assignment crud"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/terminal-assignments")
public class TerminalAssignmentController {
    private final TerminalAssignmentService terminalAssignmentService;
    @PostMapping
    public ResponseEntity<ApiResponse<TerminalAssignmentResponseDTO>>
        createTerminalAssignment(@RequestBody TerminalAssignmentRequestDTO requestDTO){
        TerminalAssignmentResponseDTO createResponse =
                terminalAssignmentService.create(requestDTO);

        return ResponseEntity.status(
                HttpStatus.CREATED
        ).body(
                ApiResponse.success(
                        "201",
                        "Terminal Assignment Created Successfully",
                        createResponse
                )
        );

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TerminalAssignmentResponseDTO>>
        updateTerminalAssignment(@PathVariable Long id,
                                 @RequestBody TerminalAssignmentRequestDTO requestDTO){

        TerminalAssignmentResponseDTO updateResponse =
                terminalAssignmentService.update(id,requestDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Terminal Assignment Updated Successfully",
                        updateResponse
                )
        );
    }
    @GetMapping
    public ResponseEntity<ApiResponse<Page<TerminalAssignmentResponseDTO>>>
        getAllTerminalAssignments(@RequestParam (required = false) String search,
                                  @ParameterObject  Pageable pageable){
        Page<TerminalAssignmentResponseDTO> dataResponse =
                terminalAssignmentService.getAll(search,pageable);
            return ResponseEntity.ok(
                    ApiResponse.success(
                            "200",
                            "Terminal Assignment Retrieved Successfully",
                            dataResponse
                    )
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TerminalAssignmentResponseDTO>>
        getTerminalAssignmentById(@PathVariable Long id){

        TerminalAssignmentResponseDTO dataResponse =
                terminalAssignmentService.getById(id);

            return ResponseEntity.ok(
                    ApiResponse.success(
                            "200",
                            "Terminal Assignment Retrieved Successfully",
                            dataResponse
                    )
            );

    }
    @GetMapping("/{id}/deActivate")
    public ResponseEntity<ApiResponse<Void>>
        deActivateTerminalAssignment(@PathVariable Long id){

        terminalAssignmentService.deActivate(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Terminal Assignment Deactivated Successfully",
                        null
                )
        );

    }
    @GetMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<Void>>
    activateTerminalAssignment(@PathVariable Long id){

        terminalAssignmentService.activate(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Terminal Assignment Activated Successfully",
                        null
                )
        );

    }
}
