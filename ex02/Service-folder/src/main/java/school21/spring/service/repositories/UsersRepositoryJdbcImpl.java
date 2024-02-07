package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("jdbcImplBean")
public class UsersRepositoryJdbcImpl implements UsersRepository {
    String tableName = "table1";
    private DataSource dataSource;

    @Autowired
    public UsersRepositoryJdbcImpl(@Qualifier("dataSourceDriverBean") DataSource dataSource) {
        this.dataSource = dataSource;
        createTable();
    }

    private void createTable() {
        String drop = "DROP TABLE IF EXISTS " + tableName;

        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (id SERIAL PRIMARY KEY, email VARCHAR(255) NOT NULL);";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(drop);
            statement.executeUpdate();
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional findById(Long id) {
        String email;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + tableName +
                     " WHERE id = " + id)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                email = resultSet.getString("email");
                return Optional.of(new User(id, email));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List findAll() {
        List<User> result = new ArrayList<>();
        Long maxId = 0L;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT MAX(id) FROM " + tableName)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                maxId = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + tableName +
                     " WHERE  id <= " + maxId + " ORDER BY id ASC")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String email = resultSet.getString("email");
                result.add(new User(id, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void save(Object entity) {
        String sql = "INSERT INTO " + tableName + " (email) VALUES (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, ((User) entity).getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Object entity) {
        String sql = "UPDATE " + tableName + " SET email = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, ((User) entity).getEmail());
            statement.setLong(2, ((User) entity).getIdentifier());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = new String("DELETE FROM " + tableName + " WHERE id = ?");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional findByEmail(String email) {
        String sql = "SELECT * FROM " + tableName +
                " WHERE email = " + "'" + email + "'";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                return Optional.of(new User(id, email));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
