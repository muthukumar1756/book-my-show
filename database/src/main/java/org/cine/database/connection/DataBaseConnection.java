package org.cine.database.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import org.cine.database.exception.DatabaseConnectionFailureException;

/**
 * <p>
 * Manages database connection pool using HikariCP.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
@Component
public final class DataBaseConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseConnection.class);
    private static HikariDataSource dataSource;

    private DataBaseConnection() {
    }

    /**
     * <p>
     * Gets the database connection from the pool.
     * </p>
     *
     * @return The database connection
     */
    public static Connection get() {
        try {
            if (dataSource == null) {
                HikariConfig config = new HikariConfig();
                config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
                config.setUsername("postgres");
                config.setPassword("123");
                config.setMaximumPoolSize(10);
                config.setMinimumIdle(5);
                dataSource = new HikariDataSource(config);
                LOGGER.info("Database connection pool initialized successfully");
            }
            return dataSource.getConnection();
        } catch (SQLException exception) {
            LOGGER.error("Database connection failed: {}", exception.getMessage());
            throw new DatabaseConnectionFailureException(exception.getMessage());
        }
    }
}
