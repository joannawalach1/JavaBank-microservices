-- Tworzenie keyspace
CREATE KEYSPACE IF NOT EXISTS transaction_keyspace
WITH replication = {
  'class': 'SimpleStrategy',
  'replication_factor': 1
};

-- Użycie keyspace
USE transaction_keyspace;

-- Tworzenie tabeli
CREATE TABLE IF NOT EXISTS transactions (
  id uuid PRIMARY KEY,
         user_id bigint,
         account_id bigint,
         account_from bigint,
         amount decimal,
         currency text,
         transaction_type text,
         transaction_status text,
         created_at timestamp
);
