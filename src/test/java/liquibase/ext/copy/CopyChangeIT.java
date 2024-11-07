package liquibase.ext.copy;

import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.startupcheck.IsRunningStartupCheckStrategy;

import java.sql.Connection;
import java.sql.Driver;
import java.util.Properties;


public class CopyChangeIT {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @BeforeAll
    public static void setup() {
        System.out.println("Before startup strategy");
        postgres.setStartupCheckStrategy(new IsRunningStartupCheckStrategy());
        System.out.println("Before startup");
        postgres.start();
    }

    @AfterAll
    public static void stop() {
        postgres.stop();
    }

    @Test
    public void test() throws Exception {
        System.out.println("In test");
        String url = postgres.getJdbcUrl();
        final Driver driver = (Driver) Class.forName(DatabaseFactory.getInstance().findDefaultDriver(url), true, Thread.currentThread().getContextClassLoader()).newInstance();

        Properties info = new Properties();
        info.put("user", postgres.getUsername());
        info.put("password", postgres.getPassword());

        Connection connection = driver.connect(url, info);
        if (connection == null) {
            throw new DatabaseException("Connection could not be created to " + url + " with driver " + driver.getClass().getName() + ".  Possibly the wrong driver for the given database URL");

        }

        Liquibase liquiBase = new Liquibase("liquibase/ext/copy/changelog.test.xml", new ClassLoaderResourceAccessor(), new JdbcConnection(connection));
        liquiBase.dropAll();

        liquiBase = new Liquibase("liquibase/ext/copy/changelog.test.xml", new ClassLoaderResourceAccessor(), new JdbcConnection(connection));
        liquiBase.update((String) null);
    }
}
