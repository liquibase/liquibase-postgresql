package liquibase.ext.vacuum;

import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Driver;
import java.util.Properties;

public class VacuumChangeTest {
    
    @Test
    public void test() throws Exception {
        String url = "jdbc:postgresql://10.10.100.100/lbcat";
        final Driver driver = (Driver) Class.forName(DatabaseFactory.getInstance().findDefaultDriver(url), true, Thread.currentThread().getContextClassLoader()).newInstance();

        Properties info = new Properties();
        info.put("user", "lbuser");
        info.put("password", "lbuser");

        Connection connection = driver.connect(url, info);
        if (connection == null) {
            throw new DatabaseException("Connection could not be created to " + url + " with driver " + driver.getClass().getName() + ".  Possibly the wrong driver for the given database URL");
        }

        Liquibase liquiBase = new Liquibase("liquibase/ext/vacuum/changelog.test.xml", new ClassLoaderResourceAccessor(), new JdbcConnection(connection));
        liquiBase.dropAll();
        
        liquiBase = new Liquibase("liquibase/ext/vacuum/changelog.test.xml", new ClassLoaderResourceAccessor(), new JdbcConnection(connection));
        liquiBase.update((String) null);
    }
}
