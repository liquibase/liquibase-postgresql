package liquibase.ext.copy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import liquibase.database.Database;
import liquibase.database.core.PostgresDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

/**
 * 
 * @author snovak7
 *
 */
public class CopyPostgres extends AbstractSqlGenerator<CopyStatement> {

	@Override
	public boolean supports(CopyStatement copyStatement, Database database) {
        return database instanceof PostgresDatabase;
    }
	
	@Override
	public ValidationErrors validate(CopyStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
		return new ValidationErrors();
	}

	@Override
	public Sql[] generateSql(CopyStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
		
		List<Sql> ret = new ArrayList<Sql>();
		String sql = "";
		
		try {
			sql += "COPY ";
			sql += database.escapeTableName(statement.getCatalogName(), 
					statement.getSchemaName(), statement.getTableName());
			sql += " FROM STDIN;" + "\n";
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(statement.getInputStream()));
	        StringBuilder out = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            out.append(line);
	        }
	        
	        sql += "\\."; // End with \. for textual inputs
			sql += out.toString();	
			ret.add(new UnparsedSql(sql));
		} catch (IOException e) {
			// TODO: handle exception
			// Don't know how to handle this kind of problem ...
		}
		
		return ret.toArray(new Sql[ret.size()]);
	}

}
