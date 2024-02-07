package school21.spring.service.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestApplicationConfig {
    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
    public EmbeddedDatabase db;

    @BeforeEach
    public void init() {
        db = builder
                .setType(EmbeddedDatabaseType.H2)
                .build();
        try (Connection connection = db.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS table2 (id SERIAL PRIMARY KEY, email VARCHAR(255) NOT NULL);");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void isConnectionTrue() {
        init();
        Connection conn;
        try {
            conn = db.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(conn);
        try {
            assertFalse(conn.isClosed());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
        db.shutdown();
    }
}
