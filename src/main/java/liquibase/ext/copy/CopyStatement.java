package liquibase.ext.copy;

import liquibase.database.PreparedStatementFactory;
import liquibase.database.core.PostgresDatabase;
import liquibase.exception.DatabaseException;
import liquibase.statement.ExecutablePreparedStatement;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author snovak7
 */
public class CopyStatement implements ExecutablePreparedStatement {

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
     *
     * @param stream
     */
    public CopyStatement(InputStream stream, String schema, String table) {
        inputStream = stream;
        schemaName = schema;
        tableName = table;
    }

    /**
     * @return
     */
    public String getCatalogName() {
        return catalogName;
    }

    /**
     * @param catalog
     */
    public void setCatalogName(String catalog) {
        catalogName = catalog;
    }

    /**
     * @return
     */
    public String getSchemaName() {
        return schemaName;
    }

    /**
     * @param schema
     */
    public void setSchemaName(String schema) {
        schemaName = schema;
    }

    /**
     * @return
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param table
     */
    public void setTableName(String table) {
        tableName = table;
    }

    /**
     * Setter for stream
     *
     * @param stream
     */
    public void setInputStream(InputStream stream) {
        inputStream = stream;
    }

    /**
     * Getter for stream
     *
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


    @Override
    public void execute(PreparedStatementFactory factory) throws DatabaseException {
        try (PreparedStatement preparedStatement = factory.create("select 1");
             PostgresDatabase database = new PostgresDatabase()) {
            Connection connection = preparedStatement.getConnection();
            if (connection instanceof BaseConnection) {
                BaseConnection con = (BaseConnection) connection;

                String escapedTable = database.escapeTableName(catalogName, schemaName, tableName);
                String sql = "COPY " + escapedTable + " FROM STDIN";
                CopyManager manager = new CopyManager(con);
                manager.copyIn(sql, inputStream);
            } else {
                throw new DatabaseException("Only postgresql support for Copy");
            }
        } catch (IOException | SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
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
