package school21.spring.service.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import school21.spring.service.config.TestApplicationConfig;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;

import javax.sql.DataSource;

public class UsersServiceImplTest {

    TestApplicationConfig testApplicationConfig;
    private DataSource dataSource;
    private UsersRepository usersRepository;
    private UsersServiceImpl usersService;

    @BeforeEach
    public void init() {
        testApplicationConfig = new TestApplicationConfig();
        testApplicationConfig.init();
        dataSource = testApplicationConfig.db;
        usersRepository = new UsersRepositoryJdbcTemplateImpl(dataSource);
        usersService = new UsersServiceImpl(usersRepository);
    }

    @Test
    void signUp() {
        String password = usersService.signUp("test@email");
        assertNotNull(password);
        assertEquals(10, password.length());
    }

}