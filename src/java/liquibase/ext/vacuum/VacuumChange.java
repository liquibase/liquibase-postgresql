package liquibase.ext.vacuum;

import liquibase.change.AbstractChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

public class VacuumChange extends AbstractChange {

    private String schemaName;
    private String tableName;

    public VacuumChange() {
        super("vacuum", "Vacuum Database", 15);
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getConfirmationMessage() {
        return "Database vacuumed";
    }


    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[] {
                new VacuumStatement(schemaName, tableName)
        };
    }
}
