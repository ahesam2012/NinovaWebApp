# Variables
MVN=mvn

# Targets
.PHONY: run clean install

# Run the Spring Boot application
run:
	@echo "Running Spring Boot application..."
	$(MVN) spring-boot:run

# Clean the project
clean:
	@echo "Cleaning the project..."
	$(MVN) clean

# Install the dependencies and build the project
install:
	@echo "Installing dependencies and building the project..."
	$(MVN) clean install

# Full rebuild (clean, install, and run)
rebuild: clean install run
