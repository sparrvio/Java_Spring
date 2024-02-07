package school21.spring.service.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private JdbcTemplate jdbcTemplate;
    String tableName = "table2";
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        createDatabase();
    }

    public void createDatabase() {
        jdbcTemplate.execute("DROP TABLE " + tableName + " CASCADE");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS " + tableName + " (id SERIAL PRIMARY KEY, email VARCHAR(255) NOT NULL);");
    }


    @Override
    public void save(Object entity) {
        if (!(entity instanceof User)) {
            throw new IllegalArgumentException("Entity must be of type User.");
        }
        User user = (User) entity;
        String sql = "INSERT INTO " + tableName + " (email) VALUES (?)";
        jdbcTemplate.update(sql, user.getEmail());
    }



    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";

        User user = jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Long id = resultSet.getLong("id");
                String email = resultSet.getString("email");
                User user = new User(id, email);
                return user;
            }
        });
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM " + tableName;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String email = rs.getString("email");
            User user = new User(id, email);
            return user;
        });
    }

    @Override
    public void update(Object entity) {
        String sql = "UPDATE " + tableName + " SET email = ? WHERE id = ?";
        User user = (User) entity;
        jdbcTemplate.update(sql, user.getEmail(), user.getId());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM " + tableName + " WHERE email = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{email}, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Long id = rs.getLong("id");
                    String email = rs.getString("email");
                    User user = new User(id, email);
                    return user;
                }
            });
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
