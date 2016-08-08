//package org.asion.search;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Repository;
//
//import java.sql.PreparedStatement;
//
///**
// * @author Asion
// * @since 16/4/30.
// */
//@Repository("searchSampleRepository")
//public class SearchSampleRepositoryImpl implements SearchSampleRepository {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//
//    private static final RowMapper<SearchSample> userMapper = (resultSet, i) -> {
//        SearchSample sample = new SearchSample();
//        sample.setId(resultSet.getLong("id"));
//        sample.setText(resultSet.getString("text"));
//        sample.setSummary(resultSet.getString("summary"));
//        return sample;
//    };
//
//    @Override
//    public Iterable<SearchSample> findAll() {
//        return jdbcTemplate.query("select * from asion_search_sample", userMapper);
//    }
//
//    @Override
//    public SearchSample save(SearchSample sample) {
//        int rows;
//        if (sample.getId() == null) {
//            String insertSql = "insert into asion_search_sample(text, summary, created_at, updated_at) values(?,?, now(), now())";
//            KeyHolder keyHolder = new GeneratedKeyHolder();
//            rows = jdbcTemplate.update(con -> {
//                PreparedStatement ps = con.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
//                ps.setString(1, sample.getText());
//                ps.setString(2, sample.getSummary());
//                return ps;
//            }, keyHolder);
//
//            sample.setId(keyHolder.getKey().longValue());
////            rows = jdbcTemplate.update(insertSql, sample.getText(), sample.getSummary());
//        } else {
//            rows = jdbcTemplate.update("update asion_search_sample set text=?, summary=?, updated_at=now() where id=?", sample.getText(), sample.getSummary(), sample.getId());
//        }
//        assert rows == 1;
//        return sample;
//    }
//
//    @Override
//    public SearchSample findOne(Long id) {
//        return jdbcTemplate.queryForObject("select * from asion_search_sample where id = ?", userMapper, id);
//    }
//
//    @Override
//    public void delete(Long id) {
//        int rows = jdbcTemplate.update("delete from asion_search_sample where id = ?", id);
//        assert rows == 1;
//    }
//
//
//}
