package org.asion.account.service;

import com.alibaba.dubbo.config.annotation.DubboService;
import org.asion.account.domain.model.Account;
import org.asion.account.domain.application.AccountManager;
import org.asion.account.domain.model.AccountRepository;
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

    @Override
    public Account login(Account account) {
        return null;
    }
}
