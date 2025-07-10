# Makefile dla automatycznego uruchomienia Cassandra z inicjalizacją

.PHONY: up down clean logs init status

# Uruchom wszystko
up:
	@echo "Starting Cassandra with automatic initialization..."
	docker-compose up -d
	@echo "Waiting for initialization to complete..."
	@sleep 10
	@docker-compose logs cassandra-init
	@echo "✅ Cassandra is ready with banking database!"

# Zatrzymaj wszystko
down:
	@echo "Stopping Cassandra..."
	docker-compose down

# Usuń wszystko (włącznie z danymi)
clean:
	@echo "Cleaning up everything..."
	docker-compose down -v
	docker system prune -f

# Pokaż logi
logs:
	docker-compose logs -f cassandra

# Sprawdź status
status:
	@echo "Checking Cassandra status..."
	docker exec cassandra-banking cqlsh -e "describe keyspaces;"

# Połącz się z Cassandra
connect:
	docker exec -it cassandra-banking cqlsh

# Zresetuj i uruchom ponownie
reset: clean up