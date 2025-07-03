package com.banking.transactionservice.configuration;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.CassandraTemplate;

import java.net.InetSocketAddress;

@Configuration
public class CassandraConfig {
    @Value("${spring.data.cassandra.contact-points:localhost}")
    private String contactPoints;

    @Bean
    public CqlSession session() {
        return CqlSession.builder()
                .addContactPoint(new InetSocketAddress(contactPoints, 9042))
                .withLocalDatacenter("datacenter1")
                .build();
    }
    @Bean
    public CassandraTemplate cassandraTemplate() {
        return new CassandraTemplate(session());
    }
}

