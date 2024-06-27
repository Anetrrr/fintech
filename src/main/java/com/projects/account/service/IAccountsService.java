package com.projects.account.service;

import com.projects.account.dto.CustomerDto;

public interface IAccountsService {

    /**
     *
     *
     * @param customerDto - customerDto object
     */
    void createAccount(CustomerDto customerDto);
}
