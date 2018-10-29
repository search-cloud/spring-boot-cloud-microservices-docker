package org.asion.webflux;

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
@Repository
public class WebfluxRepositoryImpl implements WebfluxRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Webflux> userMapper = (resultSet, i) -> {
        Webflux sample = new Webflux();
        sample.setId(resultSet.getLong("id"));
        sample.setText(resultSet.getString("text"));
        sample.setSummary(resultSet.getString("summary"));
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(resultSet.getDate("created_at"));
//        sample.setCreatedAt(calendar);
//        calendar.setTime(resultSet.getDate("updated_at"));
//        sample.setUpdatedAt(calendar);
        return sample;
    };

    @Autowired
    public WebfluxRepositoryImpl(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Override
    public Iterable<Webflux> findAll() {
        return jdbcTemplate.query("select * from asion_sample_sample", userMapper);
    }

    @Override
    public Webflux save(Webflux sample) {
        int rows;
        if (sample.getId() == null) {
            String insertSql = "insert into asion_sample_sample(text, summary, created_at, updated_at) values(?,?, now(), now())";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            rows = jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, sample.getText());
                ps.setString(2, sample.getSummary());
                return ps;
            }, keyHolder);

            sample.setId(keyHolder.getKey().longValue());
//            rows = jdbcTemplate.update(insertSql, sample.getText(), sample.getSummary());
        } else {
            rows = jdbcTemplate.update("update asion_sample_sample set text=?, summary=?, updated_at=now() where id=?", sample.getText(), sample.getSummary(), sample.getId());
        }
        assert rows == 1;
        return sample;
    }

    @Override
    public Webflux findOne(Long id) {
        return jdbcTemplate.queryForObject("select * from asion_sample_sample where id = ?", userMapper, id);
    }

    @Override
    public void delete(Long id) {
        int rows = jdbcTemplate.update("delete from asion_sample_sample where id = ?", id);
        assert rows == 1;
    }

}
