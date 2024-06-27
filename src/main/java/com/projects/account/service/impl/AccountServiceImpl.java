package com.projects.account.service.impl;

import com.projects.account.dto.CustomerDto;
import com.projects.account.repository.AccountsRepository;
import com.projects.account.repository.CustomerRepository;
import com.projects.account.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    /**
     * @param customerDto - customerDto object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {

    }
}
