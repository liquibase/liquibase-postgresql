package liquibase.ext.copy;

import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.Driver;
import java.util.Properties;


public class CopyChangeIT {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @Before
    public void setup() {
        postgres.start();
    }

    @After
    public void stop() {
        postgres.stop();
    }

    @Test
    public void test() throws Exception {
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
