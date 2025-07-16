package com.banking;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.InetSocketAddress;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@Testcontainers
public class BaseIntegrationTest {

    @Container
    static CassandraContainer<?> cassandraContainer = new CassandraContainer<>("cassandra:3.11")
            .withExposedPorts(9042)
            .withReuse(true);

    @Autowired
    protected TestRestTemplate restTemplate;

    @LocalServerPort
    protected int port;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.cassandra.contact-points", cassandraContainer::getHost);
        registry.add("spring.data.cassandra.port", cassandraContainer::getFirstMappedPort);
        registry.add("spring.data.cassandra.keyspace-name", () -> "transaction_keyspace");
        registry.add("spring.data.cassandra.local-datacenter", () -> "datacenter1");
        registry.add("spring.data.cassandra.schema-action", () -> "create_if_not_exists");
    }

    @BeforeAll
    static void setUp() {
        cassandraContainer.start();
        initializeCassandra();
    }

    private static void initializeCassandra() {
        try (CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(
                        cassandraContainer.getHost(),
                        cassandraContainer.getMappedPort(9042)
                ))
                .withLocalDatacenter("datacenter1")
                .build()) {

            // Tworzenie keyspace
            session.execute(SimpleStatement.newInstance(
                    "CREATE KEYSPACE IF NOT EXISTS transaction_keyspace " +
                            "WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1}"
            ));

            // Użycie keyspace
            session.execute(SimpleStatement.newInstance("USE transaction_keyspace"));

            // Tworzenie tabeli transactions
            session.execute(SimpleStatement.newInstance(
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
                            ")"
            ));

            // Opcjonalnie: dodatkowe indeksy
            session.execute(SimpleStatement.newInstance(
                    "CREATE INDEX IF NOT EXISTS idx_user_id ON transactions (user_id)"
            ));

            session.execute(SimpleStatement.newInstance(
                    "CREATE INDEX IF NOT EXISTS idx_account_id ON transactions (account_id)"
            ));
        }
    }

    protected String getBaseUrl() {
        return "http://localhost:" + port;
    }
}

