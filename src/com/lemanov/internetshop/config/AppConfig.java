package com.lemanov.internetshop.config;

import javax.sql.DataSource;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

import com.lemanov.internetshop.dao.CustomerDao;

//@Configuration
public class AppConfig {
	
//	@Bean
	public JndiObjectFactoryBean logicDataSource() {
		JndiObjectFactoryBean ds = new JndiObjectFactoryBean();
		ds.setJndiName("java:comp/env/jdbc/Internetshop");
		return ds;
	}
	
//	@Bean
	public DataSource plainJDBC() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl("jdbc:postgresql://localhost:5432/internetshop");
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setUsername("postgres");
		ds.setPassword("87654321");
		return ds;
	}
	
//	@Bean
	public TransactionAttributeSource annotationTransactionAttributeSource() {
	    return new AnnotationTransactionAttributeSource();
	}

}
