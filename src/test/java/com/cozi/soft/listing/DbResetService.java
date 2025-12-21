package com.cozi.soft.listing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Set;

/**
 * This a generic service with the purpose of deleting all data from the database.
 */
@Slf4j
public class DbResetService {

    private static final Set<String> CRITICAL_TABLES = Set.of("databasechangelog", "databasechangeloglock");

    private final JdbcTemplate jdbcTemplate;

    public DbResetService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Deletes all data from the given tables;
     */
    public void resetTables(String... tables) {
        for (String table : tables) {
            // Disable triggers
            jdbcTemplate.execute("ALTER TABLE  %s DISABLE TRIGGER ALL".formatted(table));
            log.debug("Deleting all data from table: {}", table);
            jdbcTemplate.execute("DELETE FROM %s".formatted(table));
            // Enable triggers
            jdbcTemplate.execute("ALTER TABLE %s ENABLE TRIGGER ALL".formatted(table));
        }
    }

    public void resetAllTablesInSchemas(String... schemas) {
        for (String schema : schemas) {
            resetAllTablesInSchema(schema);
        }
    }

    public void resetAllTablesInSchema(String schema) {
        List<String> tableName = jdbcTemplate.query("SELECT table_name FROM information_schema.tables WHERE table_schema = '%s'".formatted(schema),
                        (rs, rowNum) -> rs.getString("table_name"))
                .stream()
                .filter(table -> CRITICAL_TABLES.stream().noneMatch(ignored -> ignored.equalsIgnoreCase(table)))
                .map(table -> schema + "." + table)
                .toList();

        resetTables(tableName.toArray(String[]::new));
    }

    /**
     * This retrieves all the schemas with suffix "_service" and delete all the data
     * from all the tables in the different schemas.
     */
    public void resetAll() {
        List<String> schemas = jdbcTemplate.query("SELECT schema_name FROM information_schema.schemata WHERE schema_name LIKE '%_service'",
                (rs, rowNum) -> rs.getString("schema_name"));
        resetAllTablesInSchemas(schemas.toArray(String[]::new));
    }
}
