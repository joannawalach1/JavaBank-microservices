CREATE KEYSPACE IF NOT EXISTS banking_keyspace
WITH REPLICATION = {
'class': 'SimpleStrategy',
'replication_factor': 1
};

USE banking_keyspace;

CREATE TABLE IF NOT EXISTS transactions (
id UUID PRIMARY KEY,
userid UUID,
accountid UUID,
accountfrom UUID,
amount DECIMAL,
currency TEXT,
transactiontype TEXT,
transactionstatus TEXT,
createdat TIMESTAMP
);