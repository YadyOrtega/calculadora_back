package co.com.ath.calculadora.pruebas.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import co.com.ath.calculadora.pruebas.util.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "co.com.ath.calculadora.pruebas.repository", entityManagerFactoryRef = "sqlEntityManagerFactory", transactionManagerRef = "sqlTransactionManager")
public class DataSourceConfiguration {

	@Autowired
	private Environment env;

    @Primary
    @Bean(name = "readDataSource")
    DataSource readDatasource() {
        log.info(Constants.LOG_IN, Thread.currentThread().getStackTrace()[1].getMethodName());
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        /*dataSource.setUrl("jdbc:sqlserver://" + env.getProperty("db.host") + ":" + env.getProperty("db.port")
                + ";encrypt=true;databaseName=" + env.getProperty("db.nameDb"));*/
        dataSource.setUrl("jdbc:mysql://" + env.getProperty("db.host") + ":" + env.getProperty("db.port")
        + "/" + env.getProperty("db.nameDb"));
        dataSource.setUsername(env.getProperty("db.user"));
        dataSource.setPassword(env.getProperty("db.password"));
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        log.info("Conectando " + dataSource.getUrl());
        log.info(Constants.LOG_OUT, Thread.currentThread().getStackTrace()[1].getMethodName());
        return dataSource;
    }

	@Primary
	@Bean(name = "sqlEntityManagerFactory")
	LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		log.info(Constants.LOG_IN, Thread.currentThread().getStackTrace()[1].getMethodName());
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(readDatasource());
		em.setPackagesToScan("co.com.ath.calculadora.pruebas.entity");
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		/*
		 * Map<String, Object> properties = new HashMap<>();
		 * properties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
		 * properties.put("hibernate.dialect",
		 * env.getProperty("spring.jpa.database-platform"));
		 * em.setJpaPropertyMap(properties);
		 */
		// log.info("Modelo base de lectura: " +
		// env.getProperty("spring.dsread.package.model"));
		// log.info("Repositorio base de lectura: " +
		// env.getProperty("spring.dsread.package.repository"));
		log.info(Constants.LOG_OUT, Thread.currentThread().getStackTrace()[1].getMethodName());
		return em;
	}

	@Primary
	@Bean(name = "sqlTransactionManager")
	PlatformTransactionManager transactionManager() {
		log.info(Constants.LOG_IN, Thread.currentThread().getStackTrace()[1].getMethodName());
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		log.info("Recurso de base de datos de sql server cargado correctamente");
		log.info(Constants.LOG_IN, Thread.currentThread().getStackTrace()[1].getMethodName());
		return transactionManager;
	}
	
}
