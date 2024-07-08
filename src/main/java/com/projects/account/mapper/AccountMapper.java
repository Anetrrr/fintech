package com.projects.account.mapper;

import com.projects.account.dto.AccountsDto;
import com.projects.account.entity.Accounts;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor

public class AccountMapper {

// mapping accounts to accountsDto
    public static AccountsDto mapToDto(Accounts accounts, AccountsDto accountsDto){

        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());

        return accountsDto;

    }
// mapping accountsDto to account.
    public static Accounts maptoAccounts(AccountsDto accountsDto, Accounts accounts){

        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());


        return accounts;
    }



}
