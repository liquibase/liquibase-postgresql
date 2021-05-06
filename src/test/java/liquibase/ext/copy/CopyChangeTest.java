package liquibase.ext.copy;

import liquibase.database.core.PostgresDatabase;
import liquibase.statement.SqlStatement;
import org.junit.Test;

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
}
