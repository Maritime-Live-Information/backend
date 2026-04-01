# Marlin - Backend

## Requirements

- [JDK 21](https://www.oracle.com/de/java/technologies/downloads/#java21)
- [Docker](https://www.docker.com/) (Docker Desktop on Windows/Mac, or Docker Engine on Linux)
- [Maven](https://maven.apache.org/)

---

## Getting Started

### 1. Configure Environment Variables

Create a `.env` file in the root of the `backend/` directory. Use the `.env.example` file as a reference for all required fields:

```bash
cp .env.example .env
```

Open the `.env` file and fill in the values. The defaults in `.env.example` are sufficient for local development, but you will need to provide your own values for services like Google OAuth, mail, Stripe, and Firebase if you want those features to work.

### 2. Start the Database

Make sure Docker is running, then start the PostgreSQL/TimescaleDB container:

```bash
docker compose up -d
```

### 3. Install Dependencies

Install all Maven dependencies and build the project:

```bash
mvn clean install
```

### 4. Run the Application

#### IntelliJ IDEA

1. Open the project in IntelliJ.
2. Navigate to `src/main/kotlin/hs/flensburg/marlin/Main.kt`.
3. Click the green **Run** button next to the `main` function, or right-click the file and select **Run 'MainKt'**.

#### VS Code

1. Install the [Kotlin extension](https://marketplace.visualstudio.com/items?itemName=fwcd.kotlin) and the [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack).
2. Open the project folder in VS Code.
3. Open `src/main/kotlin/hs/flensburg/marlin/Main.kt`.
4. Click **Run** above the `main` function (provided by the Kotlin or Java extension).

#### Command Line

You can also run the application directly from the terminal using Maven:

```bash
mvn exec:java -Dexec.mainClass="hs.flensburg.marlin.MainKt"
```

---

Once running, the backend will be available at `http://localhost:8080`.
