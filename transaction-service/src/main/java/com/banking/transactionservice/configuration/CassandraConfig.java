package com.banking.transactionservice.configuration;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.net.InetSocketAddress;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.contact-points:localhost}")
    private String contactPoints;

    @Value("${spring.data.cassandra.port:9043}")
    private int port;

    @Value("${spring.data.cassandra.local-datacenter:datacenter1}")
    private String datacenter1;

    @Value("${spring.data.cassandra.keyspace-name:transaction_keyspace}")
    private String keyspace;

    private CqlSession session;

    @Bean
    @Primary
    public CqlSession session() {
        if (session == null) {
            session = CqlSession.builder()
                    .addContactPoint(new InetSocketAddress(contactPoints, port))
                    .withLocalDatacenter(datacenter1)
                    .withKeyspace(keyspace)
                    .build();
        }
        return session;
    }

    @Bean
    public CassandraTemplate cassandraTemplate(CqlSession session) {
        return new CassandraTemplate(session);
    }


    public void init() {
        try (CqlSession initSession = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(contactPoints, port))
                .withLocalDatacenter(datacenter1)
                .build()) {

            String createKeyspaceCql = String.format(
                    "CREATE KEYSPACE IF NOT EXISTS %s WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};",
                    keyspace);
            initSession.execute(createKeyspaceCql);
        }

        session().execute(
                "CREATE TABLE IF NOT EXISTS transactions (" +
                        "id UUID PRIMARY KEY, " +
                        "user_id bigint, " +
                        "account_id text, " +
                        "account_from bigint, " +
                        "amount decimal, " +
                        "currency text, " +
                        "transaction_type text, " +
                        "transaction_status text, " +
                        "created_at timestamp" +
                        ");"
        );
    }

    @Override
    protected String getKeyspaceName() {
        return keyspace;
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    protected String getLocalDataCenter() {
        return datacenter1;
    }
}
