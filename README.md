# Listing Service

Welcome to the Listing Service project. This repository contains a Spring Boot application that manages real estate listings (Sites, Plots of Land, etc.).

## Interview Information
The technical interview scenarios and instructions can be found in the [interview.md](interview.md) file.

## Prerequisites
- Java 25
- Docker and Docker Compose
- (Optional) Gradle (the project includes a Gradle wrapper)

## Project Setup

### Mac
1. **Install Java 25**:
   - Using Homebrew: `brew install openjdk@25` (Note: Ensure it's in your PATH)
   - Or download from [Adoptium](https://adoptium.net/).
2. **Install Docker**:
   - Download and install [Docker Desktop for Mac](https://www.docker.com/products/docker-desktop/).
3. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```
4. **Run the application**:
   ```bash
   ./gradlew bootRun
   ```
   The application uses Spring Boot Docker Compose support, so it will automatically start the PostgreSQL container defined in `compose.yaml`.

### Windows
1. **Install Java 25**:
   - Download and install from [Adoptium](https://adoptium.net/).
   - Set `JAVA_HOME` and update the `Path` environment variable.
2. **Install Docker**:
   - Install [Docker Desktop for Windows](https://www.docker.com/products/docker-desktop/).
   - Ensure WSL 2 backend is enabled.
3. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```
4. **Run the application**:
   ```cmd
   gradlew.bat bootRun
   ```

### Ubuntu
1. **Install Java 25**:
   ```bash
   sudo apt update
   sudo apt install openjdk-25-jdk
   ```
2. **Install Docker and Docker Compose**:
   ```bash
   sudo apt install docker.io docker-compose-v2
   sudo usermod -aG docker $USER
   # (Log out and back in for group changes to take effect)
   ```
3. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```
4. **Run the application**:
   ```bash
   ./gradlew bootRun
   ```

## Running Tests
To run all tests:
```bash
./gradlew test
```

## API Documentation
Once the application is running, the API is available at `http://localhost:1798/api`.
The OpenAPI specification can be found at `src/main/resources/openapi/listing-manager-api-spec.yaml`.
