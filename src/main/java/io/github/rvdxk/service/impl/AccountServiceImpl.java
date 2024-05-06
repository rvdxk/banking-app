package io.github.rvdxk.service.impl;

import io.github.rvdxk.dto.AccountDto;
import io.github.rvdxk.entity.Account;
import io.github.rvdxk.mapper.AccountMapper;
import io.github.rvdxk.repository.AccountRepository;
import io.github.rvdxk.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
       Account account = accountRepository.findById(id)
               .orElseThrow(()-> new RuntimeException("Please, enter the correct account id"));

       return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Please, enter the correct account id"));

        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Please, enter the correct account id"));

        if (amount <= account.getBalance()){
            double total = account.getBalance() - amount;
            account.setBalance(total);
        } else {
            throw new RuntimeException("Please enter correct value");
        }
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Please, enter the correct account id"));

        accountRepository.deleteById(id);
    }
}
