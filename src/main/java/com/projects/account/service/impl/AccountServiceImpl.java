package com.projects.account.service.impl;

import com.projects.account.constants.AccountsConstants;
import com.projects.account.dto.CustomerDto;
import com.projects.account.entity.Accounts;
import com.projects.account.entity.Customer;
import com.projects.account.exception.CustomerAlreadyExistsException;
import com.projects.account.mapper.CustomerMapper;
import com.projects.account.repository.AccountsRepository;
import com.projects.account.repository.CustomerRepository;
import com.projects.account.service.IAccountsService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountsService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    private  CustomerMapper customerMapper;

    /**
     * @param customerDto - customerDto object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());

        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number: " + customerDto.getMobileNumber());
        }


        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");

       Customer savedCustomer = customerRepository.save(customer);

       accountsRepository.save(createNewAccount(savedCustomer));

    }


    private Accounts createNewAccount(Customer customer){
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());

        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedBy("Anonymous");
        newAccount.setCreatedAt(LocalDateTime.now());

        return newAccount;

    }
}
