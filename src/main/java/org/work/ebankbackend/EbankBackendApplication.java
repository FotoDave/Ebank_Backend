package org.work.ebankbackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.work.ebankbackend.dtos.BankAccountDTO;
import org.work.ebankbackend.dtos.CurrentBankAccountDTO;
import org.work.ebankbackend.dtos.CustomerDTO;
import org.work.ebankbackend.dtos.SavingBankAccountDTO;
import org.work.ebankbackend.entities.*;
import org.work.ebankbackend.enums.AccountStatus;
import org.work.ebankbackend.enums.OperationType;
import org.work.ebankbackend.exceptions.BalanceNotSufficientException;
import org.work.ebankbackend.exceptions.BankAccountNotFoundException;
import org.work.ebankbackend.exceptions.CustomerNotFoundException;
import org.work.ebankbackend.repositories.AccountOperationRepository;
import org.work.ebankbackend.repositories.BankAccountRepository;
import org.work.ebankbackend.repositories.CustomerRepository;
import org.work.ebankbackend.service.BankAccountService;
import org.work.ebankbackend.service.BankService;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankBackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(EbankBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args ->{
            Stream.of("Dave","Wallace","Maria").forEach(name->{
                CustomerDTO customer = new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customer);
            });

            bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000, 9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000, 5.5, customer.getId());

                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List <BankAccountDTO> bankAccountDTOS = bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccountDTO:bankAccountDTOS){
                for (int i=0; i<4; i++){
                    String accountId;
                    if(bankAccountDTO instanceof SavingBankAccountDTO){
                        accountId = ((SavingBankAccountDTO) bankAccountDTO).getId();
                    }else{
                        accountId = ((CurrentBankAccountDTO)bankAccountDTO).getId();
                    }
                    bankAccountService.credit(accountId,10000+Math.random()*120000,"Credit");
                    bankAccountService.debit(accountId,1000+Math.random()*9000, "Debit");
                }
            }
        };
    }


    //@Bean
   /* CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Dave","Debora","Maella","Maria").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            } );
            customerRepository.findAll().forEach(cust -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });
            bankAccountRepository.findAll().forEach(acc->{
                for(int i = 0; i < 12; i++){
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*120000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }
            } );

        };
    }*/
}
