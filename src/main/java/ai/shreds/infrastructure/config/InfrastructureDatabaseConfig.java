package ai.shreds.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

import org.springframework.boot.jdbc.DataSourceBuilder;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = 'ai.shreds.infrastructure.repositories')
public class InfrastructureDatabaseConfig {

    public void configureDatabase() {
        // Additional database configuration can be added here if necessary
    }

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName('com.mysql.cj.jdbc.Driver')
                .url('jdbc:mysql://localhost:3306/yourdb') // Replace with your database URL
                .username('username') // Replace with your database username
                .password('password') // Replace with your database password
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan('ai.shreds.domain.entities');

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        Properties jpaProperties = new Properties();
        jpaProperties.put('hibernate.dialect', 'org.hibernate.dialect.MySQL5Dialect');
        jpaProperties.put('hibernate.hbm2ddl.auto', 'update');
        jpaProperties.put('hibernate.show_sql', 'true');
        jpaProperties.put('hibernate.format_sql', 'true');
        // Optimistic locking is enabled by default when entities have @Version fields
        factoryBean.setJpaProperties(jpaProperties);

        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
