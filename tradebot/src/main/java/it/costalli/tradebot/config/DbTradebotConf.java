package it.costalli.tradebot.config;

//import javax.inject.Named;
import javax.sql.DataSource;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DbTradebotConf {
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.tradebot")
	public HikariConfig hikariConfig() {
	    return new HikariConfig();
	}

	@Bean(name = "tradebotDatasource", destroyMethod = "")
	public DataSource dataSource() {
	    return new HikariDataSource(hikariConfig());
	}
	
	
	/*
	
	@Bean(name = "sqlSessionFactoryTradebot", destroyMethod = "")
	public SqlSessionFactoryBean sqlSessionFactoryIdem(@Named("tradebotDatasource") final DataSource dataSource) throws Exception {
		final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		return sqlSessionFactoryBean;
	}
	
	@Bean(name = "sqlSessionArcfar", destroyMethod = "")
	public SqlSessionTemplate sqlSessionMD(@Named("sqlSessionFactoryTradebot") final SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
		SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
		return sessionTemplate;
	} 
	*/
	
}