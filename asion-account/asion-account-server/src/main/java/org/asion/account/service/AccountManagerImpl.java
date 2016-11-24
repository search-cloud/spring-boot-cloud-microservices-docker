package org.asion.account.service;

import com.alibaba.dubbo.config.annotation.DubboService;
import org.asion.account.Account;
import org.asion.account.AccountManager;
import org.asion.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Asion.
 * @since 16/8/24.
 */
@Component("accountManager")
@DubboService(interfaceClass = AccountManager.class)
public class AccountManagerImpl implements AccountManager {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account findOne(Long id) {
        return accountRepository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        accountRepository.delete(id);
    }

    @Override
    public Iterable<Account> findAll() {
        return accountRepository.findAll();
    }
}