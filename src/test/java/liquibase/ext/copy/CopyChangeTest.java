package liquibase.ext.copy;

import junit.framework.Assert;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.statement.SqlStatement;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Driver;
import java.util.Properties;

import static junit.framework.TestCase.assertEquals;

public class CopyChangeTest {

    @Test
    public void generateStatements() {
        CopyChange change = new CopyChange();
        change.setTableName("my_table");
        SqlStatement[] sqlStatements = change.generateStatements(new PostgresDatabase());

        assertEquals(1, sqlStatements.length);
        assertEquals("my_table", ((CopyStatement) sqlStatements[0]).getTableName());
    }

    @Test
    public void test() throws Exception {
        String url = "jdbc:postgresql://localhost/lbtest";
        final Driver driver = (Driver) Class.forName(DatabaseFactory.getInstance().findDefaultDriver(url), true, Thread.currentThread().getContextClassLoader()).newInstance();

        Properties info = new Properties();
        info.put("user", "lbtest");
        info.put("password", "lbtest");

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
