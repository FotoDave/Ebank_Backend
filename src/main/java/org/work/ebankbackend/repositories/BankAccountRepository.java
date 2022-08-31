package org.work.ebankbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.work.ebankbackend.entities.BankAccount;
import org.work.ebankbackend.entities.Customer;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
