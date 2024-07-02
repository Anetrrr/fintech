package com.projects.account.service.impl;

import com.projects.account.constants.AccountsConstants;
import com.projects.account.dto.AccountsDto;
import com.projects.account.dto.CustomerDto;
import com.projects.account.entity.Accounts;
import com.projects.account.entity.Customer;
import com.projects.account.exception.CustomerAlreadyExistsException;
import com.projects.account.exception.ResourceNotFoundException;
import com.projects.account.mapper.AccountMapper;
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


    /**
     *
     *
     * @param mobileNumber - input mobile number
     * @return Accounts Details based on mobile number
     */

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
       Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)

       );

               Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                       () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));


         //mapping entities to dto
               CustomerDto customerDto = CustomerMapper.mapToDto(customer, new CustomerDto());
               customerDto.setAccountsDto(AccountMapper.mapToDto(accounts, new AccountsDto()));


        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();

        if (accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "Account Number", accountsDto.getAccountNumber().toString())
            );
            AccountMapper.maptoAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerId", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }


}
