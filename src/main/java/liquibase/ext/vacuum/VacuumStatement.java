package liquibase.ext.vacuum;

import liquibase.statement.AbstractSqlStatement;

public class VacuumStatement extends AbstractSqlStatement {
    private String tableName;
    private String schemaName;
    private String catalogName;


    public VacuumStatement(String catalogName, String schemaName, String tableName) {
        this.catalogName = catalogName;
        this.tableName = tableName;
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getSchemaName() {
        return schemaName;
    }
}
