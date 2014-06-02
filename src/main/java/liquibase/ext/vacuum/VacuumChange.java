package liquibase.ext.vacuum;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

@DatabaseChange(name="vacuum", description = "Vacuum Database", priority = 15)
public class VacuumChange extends AbstractChange {

    private String catalogName;
    private String schemaName;
    private String tableName;

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
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
                new VacuumStatement(catalogName, schemaName, tableName)
        };
    }
}
