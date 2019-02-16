package liquibase.ext.copy;

import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.statement.ExecutableConnectionStatement;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.postgresql.core.BaseConnection;
import org.postgresql.copy.CopyManager;

/**
 * 
 * @author snovak7
 *
 */
public class CopyStatement implements ExecutableConnectionStatement {
	
	/**
	 * 
	 */
	private String catalogName;
	
    /**
     * 
     */
    private String schemaName;
    
    /**
     * 
     */
    private String tableName;
    
	/**
	 * 
	 */
	private InputStream inputStream;
	
	/**
	 * Default constructor
	 */
	protected CopyStatement() {
	}
	
	/**
	 * Loaded stream to push into database
	 * @param stream
	 */
	public CopyStatement(InputStream stream, String schema, String table) {
		inputStream = stream;
		schemaName = schema;
		tableName = table;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCatalogName() {
		return catalogName;
	}
	
	/**
	 * 
	 * @param catalog
	 */
	public void setCatalogName(String catalog) {
		catalogName = catalog;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getSchemaName() {
		return schemaName;
	}
	
	/**
	 * 
	 * @param schema
	 */
	public void setSchemaName(String schema) {
		schemaName = schema;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getTableName() {
		return tableName;
	}
	
	/**
	 * 
	 * @param table
	 */
	public void setTableName(String table) {
		tableName = table;
	}
	
	/**
	 * Setter for stream
	 * @param stream
	 */
	public void setInputStream(InputStream stream) {
		inputStream = stream;
	}
	
	/**
	 * Getter for stream
	 * @return stream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}
	/**
	 * 
	 */
	@Override
	public boolean skipOnUnsupported() {
		return false;
	}

	/**
	 * 
	 */
	@Override
	public void execute(JdbcConnection connection) throws DatabaseException {
		if(connection.getWrappedConnection() instanceof BaseConnection) {
			BaseConnection con = (BaseConnection) connection.getWrappedConnection();
			
			try {
				String escapedTable = new PostgresDatabase().escapeTableName(catalogName, schemaName, tableName);
				String sql = "COPY " + escapedTable + " FROM STDIN";
				CopyManager manager = new CopyManager(con);	
				manager.copyIn(sql, inputStream);
			} catch (IOException e) {
				throw new DatabaseException(e.getMessage(), e);
			} catch (SQLException e) {
				throw new DatabaseException(e.getMessage(), e);
			}
		} else {
			throw new DatabaseException("Only postgresql support for Copy");
		}
	}

	/**
	 * 
	 */
	@Override
	public boolean continueOnError() {
		return false;
	}

}
