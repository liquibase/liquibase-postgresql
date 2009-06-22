package liquibase.change.ext.vacuum;

import liquibase.change.AbstractChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

public class VacuumChange extends AbstractChange {
    public VacuumChange() {
        super("vacuum", "Vacuum Database", 15);
    }

    public String getConfirmationMessage() {
        return "Database vacuumed";
    }

    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[0];  //To change body of implemented methods use File | Settings | File Templates.
    }
}
