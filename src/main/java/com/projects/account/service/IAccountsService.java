package com.projects.account.service;

import com.projects.account.dto.AccountsDto;
import com.projects.account.dto.CustomerDto;

import java.util.List;

public interface IAccountsService {

    /**
     *
     *
     * @param customerDto - customerDto object
     */
    void createAccount(CustomerDto customerDto);
    CustomerDto fetchAccount(String mobileNumber);

    List<CustomerDto> fetchAllAccounts();

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);


}
