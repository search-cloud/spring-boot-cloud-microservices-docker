package org.asion.account.domain.infrastructure.model;

import org.asion.account.Account;
import org.asion.account.domain.model.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

/**
 * @author Asion
 * @since 16/4/30.
 */
@Repository("accountRepository")
public class AccountRepositoryImpl implements AccountRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Account> accountMapper = (resultSet, i) -> {
        Account account = new Account();
        account.setId(resultSet.getLong("id"));
        account.setCode(resultSet.getString("code"));
        account.setName(resultSet.getString("name"));
        account.setPassword(resultSet.getString("password"));
        account.setNickName(resultSet.getString("nick_name"));
        account.setEmail(resultSet.getString("email"));
        account.setMobile(resultSet.getString("mobile"));
        account.setEnabled(resultSet.getBoolean("enabled"));
        account.setCreatedAt(resultSet.getDate("created_at"));
        account.setUpdatedAt(resultSet.getDate("updated_at"));

        return account;
    };

    @Autowired
    public AccountRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Account> findAll() {
        return jdbcTemplate.query("select * from asion_account", accountMapper);
    }

    @Override
    public Account save(Account account) {
        if (account.getId() == null) {
            return create(account);
        } else {
            return update(account);
        }
    }

    @Override
    public Account create(Account account) {

        String insertSql = "insert into asion_account(code, name, password, nick_name, email, mobile, enabled, created_at, updated_at) values(?,?,?,?,?,?,?, now(), now())";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rows = jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getCode());
            ps.setString(2, account.getName());
            ps.setString(3, account.getPassword());
            ps.setString(4, account.getNickName());
            ps.setString(5, account.getEmail());
            ps.setString(6, account.getMobile());
            ps.setBoolean(7, account.isEnabled());
            return ps;
        }, keyHolder);
        account.setId(keyHolder.getKey().longValue());

        if (rows != 1) {
            throw new RuntimeException("data create fail!");
        }
        return account;
    }

    @Override
    public Account update(Account account) {
        int rows = jdbcTemplate.update(
                "update asion_account set code=?, name=?, password=?, nick_name=?, email=?, mobile=?, enabled=?, updated_at=now() where id=?",
                account.getCode(), account.getName(), account.getPassword(), account.getNickName(), account.getEmail(), account.getMobile(), account.isEnabled(), account.getId());
        if (rows != 1) {
            throw new RuntimeException("data update fail!");
        }
        return account;
    }

    @Override
    public Account findOne(Long id) {
        return jdbcTemplate.queryForObject("select * from asion_account where id = ?", accountMapper, id);
    }

    @Override
    public void delete(Long id) {
        int rows = jdbcTemplate.update("delete from asion_account where id = ?", id);
        if (rows != 1) {
            throw new RuntimeException("data delete fail!");
        }
    }

}
