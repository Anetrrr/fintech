package com.projects.account.controller;


import com.projects.account.constants.AccountsConstants;
import com.projects.account.dto.CustomerDto;
import com.projects.account.dto.ErrorResponseDto;
import com.projects.account.dto.ResponseDto;
import com.projects.account.entity.Customer;
import com.projects.account.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(
        name = "CRUD REST APIs for Account in Annie's",
        description = "CRUD REST APIs in Annie's to CREATE, UPDATE, FETCH and DELETE account details"
)
@RestController @AllArgsConstructor
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountsController {

    private IAccountsService iAccountsService;


    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer and Account inside Annie's"
    )
    @ApiResponse(
            responseCode = "200",
            description =  "HTTP Status OK"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
    iAccountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));

    }

    @Operation(
            summary = "Fetch Specific Account REST API",
            description = "REST API to fetch specific Customer and Account details on a mobile number"
    )
    @ApiResponse(
            responseCode = "200",
            description =  "HTTP Status OK"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                                                               @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                               String mobileNumber) {
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);
    }

    @Operation(
            summary = "Fetch All Accounts REST API",
            description = "REST API to fetch all Customers and Account details"
    )
    @ApiResponse(
            responseCode = "200",
            description =  "HTTP Status OK"
    )
    @GetMapping("/fetchAll")
    public ResponseEntity<List<CustomerDto>> fetchAllAccounts() {
        List<CustomerDto> customerDtos = iAccountsService.fetchAllAccounts();
        return ResponseEntity.status(HttpStatus.OK).body(customerDtos);
    }


    @Operation(
            summary = "Update Account REST API",
            description = "REST API to update Customer and Account detail based on an account number"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description =  "HTTP Status OK"
    ),
            @ApiResponse(
                    responseCode = "417",
                    description =  "Expectation failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description =  "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdated = iAccountsService.updateAccount(customerDto);

        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));

        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Account REST API",
            description = "REST API to delete Customer and Account detail based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description =  "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description =  "Expectation failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description =  "HTTP Status Internal Server Error"
            ),
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                                                @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                                String mobileNumber){
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);

        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));

        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }

}
