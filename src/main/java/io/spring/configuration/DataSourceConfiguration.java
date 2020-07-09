/*
 * Copyright 2018-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author Michael Minella
 */
@Configuration
public class DataSourceConfiguration {

	@Bean(name = "springDataSourceProperties")
	@ConfigurationProperties("spring.datasource")
	@Primary
	public DataSourceProperties springDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "myDataSourceProperties")
	@ConfigurationProperties("my.datasource")
	public DataSourceProperties myDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "dataFlowDataSource")
	@Primary
	public DataSource dataSource(@Qualifier("springDataSourceProperties")DataSourceProperties springDataSourceProperties) {
		return DataSourceBuilder.create().driverClassName(springDataSourceProperties.getDriverClassName()).
			url(springDataSourceProperties.getUrl()).
			password(springDataSourceProperties.getPassword()).
			username(springDataSourceProperties.getUsername()).
			build();
	}

	@Bean
	public DataSource secondDataSource(@Qualifier("myDataSourceProperties") DataSourceProperties myDataSourceProperties) {
		return DataSourceBuilder.create().driverClassName(myDataSourceProperties.getDriverClassName()).
			url(myDataSourceProperties.getUrl()).
			password(myDataSourceProperties.getPassword()).
			username(myDataSourceProperties.getUsername()).
			build();
	}
}
