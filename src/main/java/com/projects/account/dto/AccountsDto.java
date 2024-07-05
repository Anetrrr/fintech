package com.projects.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
@Schema(

        name = "Accounts",
        description = "Schema to hold Account information"
)
public class AccountsDto {

    @Schema(

            description = "Account Number of Annie's"
    )

    @NotEmpty(message = "Account number cannot be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private Long accountNumber;

    @Schema(

            description = "Account type of Annie's", example = "Savings"
    )
    @NotEmpty(message = "Account Type cannot be null or empty")
    private String accountType;

    @Schema(

            description = "Annie's branch address"
    )
    @NotEmpty(message = "Branch address cannot be null or empty")
    private String branchAddress;
}
