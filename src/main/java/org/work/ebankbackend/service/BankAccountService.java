package org.work.ebankbackend.service;

import org.work.ebankbackend.dtos.*;
import org.work.ebankbackend.entities.BankAccount;
import org.work.ebankbackend.entities.CurrentAccount;
import org.work.ebankbackend.entities.Customer;
import org.work.ebankbackend.entities.SavingAccount;
import org.work.ebankbackend.exceptions.BalanceNotSufficientException;
import org.work.ebankbackend.exceptions.BankAccountNotFoundException;
import org.work.ebankbackend.exceptions.CustomerNotFoundException;
import org.work.ebankbackend.repositories.AccountOperationRepository;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    AccountOperationRepository getAccountOperationRepository();

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}
