package org.asion.account;

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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<Account> accountMapper = (resultSet, i) -> {
        Account account = new Account();
        account.setId(resultSet.getLong("id"));
        account.setText(resultSet.getString("text"));
        account.setSummary(resultSet.getString("summary"));
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(resultSet.getDate("created_at"));
//        user.setCreatedAt(calendar);
//        calendar.setTime(resultSet.getDate("updated_at"));
//        user.setUpdatedAt(calendar);
        return account;
    };

    @Override
    public Iterable<Account> findAll() {
        return jdbcTemplate.query("select * from asion_account", accountMapper);
    }

    @Override
    public Account save(Account account) {
        int rows;
        if (account.getId() == null) {
            String insertSql = "insert into asion_account(text, summary, created_at, updated_at) values(?,?, now(), now())";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            rows = jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, account.getText());
                ps.setString(2, account.getSummary());
                return ps;
            }, keyHolder);

            account.setId(keyHolder.getKey().longValue());
//            rows = jdbcTemplate.update(insertSql, user.getText(), user.getSummary());
        } else {
            rows = jdbcTemplate.update("update asion_account set text=?, summary=?, updated_at=now() where id=?", account.getText(), account.getSummary(), account.getId());
        }
        assert rows == 1;
        return account;
    }

    @Override
    public Account findOne(Long id) {
        return jdbcTemplate.queryForObject("select * from asion_account where id = ?", accountMapper, id);
    }

    @Override
    public void delete(Long id) {
        int rows = jdbcTemplate.update("delete from asion_account where id = ?", id);
        assert rows == 1;
    }

}
