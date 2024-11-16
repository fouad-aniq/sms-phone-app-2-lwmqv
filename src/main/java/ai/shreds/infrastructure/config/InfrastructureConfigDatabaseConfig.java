package ai.shreds.infrastructure.config;

import javax.sql.DataSource;
import javax.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class InfrastructureConfigDatabaseConfig {

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${spring.datasource.driver-class-name}")
    private String databaseDriverClass;

    @Value("${spring.datasource.username}")
    private String databaseUsername;

    @Value("${spring.datasource.password}")
    private String databasePassword;

    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateHbm2ddlAuto;

    @Bean
    public DataSource configureDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(databaseDriverClass);
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(databaseUsername);
        dataSource.setPassword(databasePassword);
        return dataSource;
    }

    @Bean
    public EntityManagerFactory configureEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(configureDataSource());
        entityManagerFactory.setPackagesToScan("ai.shreds.domain.entities");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
        jpaProperties.put("hibernate.dialect", hibernateDialect);

        entityManagerFactory.setJpaProperties(jpaProperties);

        entityManagerFactory.afterPropertiesSet();
        return entityManagerFactory.getObject();
    }
}