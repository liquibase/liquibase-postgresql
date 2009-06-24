package liquibase.ext.vacuum;

import liquibase.ext.vacuum.VacuumStatement;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.database.Database;
import liquibase.database.core.PostgresDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;

public class VacuumPostgres implements SqlGenerator<VacuumStatement> {
    public int getPriority() {
        return 15;
    }

    public boolean supports(VacuumStatement vacuumStatement, Database database) {
        return database instanceof PostgresDatabase;
    }

    public ValidationErrors validate(VacuumStatement vacuumStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return new ValidationErrors();
    }

    public Sql[] generateSql(VacuumStatement vacuumStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String sql = "VACUUM";
        if (vacuumStatement.getTableName() != null) {
            sql += " "+database.escapeTableName(vacuumStatement.getSchemaName(), vacuumStatement.getTableName());
        }
        return new Sql[] {
                new UnparsedSql(sql)
        };
    }
}
