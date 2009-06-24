package liquibase.ext.vacuum;

import org.junit.Test;
import liquibase.Liquibase;
import liquibase.exception.JDBCException;
import liquibase.database.DatabaseFactory;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Driver;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class VacuumChangeTest {
    
    @Test
    public void test() throws Exception {
        String url = "jdbc:postgresql://localhost/liquibase";
        final Driver driver = (Driver) Class.forName(DatabaseFactory.getInstance().findDefaultDriver(url), true, Thread.currentThread().getContextClassLoader()).newInstance();

        Properties info = new Properties();
        info.put("user", "liquibase");
        info.put("password", "liquibase");

        Connection connection = driver.connect(url, info);
        if (connection == null) {
            throw new JDBCException("Connection could not be created to " + url + " with driver " + driver.getClass().getName() + ".  Possibly the wrong driver for the given database URL");
        }

        Liquibase liquiBase = new Liquibase("liquibase/ext/vacuum/changelog.test.xml", new ClassLoaderResourceAccessor(), connection);
        liquiBase.dropAll();
        
        liquiBase = new Liquibase("liquibase/ext/vacuum/changelog.test.xml", new ClassLoaderResourceAccessor(), connection);
        liquiBase.update(null);
    }
}
