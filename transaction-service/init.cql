CREATE KEYSPACE IF NOT EXISTS transaction_keyspace
WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};

USE transaction_keyspace;

CREATE TABLE IF NOT EXISTS transaction_keyspace.transactions (
    id UUID PRIMARY KEY,
        user_id bigint,
        account_id text,
        account_from bigint,
        amount decimal,
        currency text,
        transaction_type text,
        transaction_status text,
        created_at timestamp
);
