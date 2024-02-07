package school21.spring.service.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("school21.spring.service")
@ComponentScan("school21.spring.service.repositories")
@PropertySource("db.properties")
public class ApplicationConfig {

    @Autowired
    private Environment env;

    @Bean
    public HikariDataSource dataSourceHikariBean() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(env.getProperty("db.driver.name"));
        config.setJdbcUrl(env.getProperty("db.url"));
        config.setUsername(env.getProperty("db.user"));
        config.setPassword(env.getProperty("db.password"));
        return new HikariDataSource(config);
    }

    @Bean
    public DriverManagerDataSource dataSourceDriverBean() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver.name"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.user"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }
}