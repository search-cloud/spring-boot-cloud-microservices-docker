package org.asion.demo.domain.infrastructure.domain.model;

import org.asion.demo.domain.model.Account;
import org.asion.demo.domain.model.AccountRepository;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * @author Asion.
 * @since 2017/3/20.
 */
public class AccountReposityImpl implements AccountRepository {

    @NotNull
    @Override
    public <S extends Account> S create(S entity) {
        return null;
    }

    @Override
    public <S extends Account> S update(S entity) {
        return null;
    }

    @Override
    public Optional<Account> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Account findOne(Long aLong) {
        return null;
    }

}
