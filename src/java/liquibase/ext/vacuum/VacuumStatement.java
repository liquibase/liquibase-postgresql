package liquibase.ext.vacuum;

import liquibase.statement.SqlStatement;

public class VacuumStatement implements SqlStatement {
    private String tableName;
    private String schemaName;


    public VacuumStatement(String schemaName, String tableName) {
        this.tableName = tableName;
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getSchemaName() {
        return schemaName;
    }
}
