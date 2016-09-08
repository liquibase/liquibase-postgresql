package liquibase.ext.copy;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.statement.SqlStatement;
import liquibase.util.StreamUtil;
import liquibase.util.StringUtils;

/**
 * Represents a Change for custom COPY FROM STDIN stored in a File.
 * <p/>
 * To create an instance call the constructor as normal and then call
 *
 * @author <a href="mailto:s@escendit.com">Simon Novak</a>
 * @link{#setResourceAccesssor(ResourceAccessor)} before calling setPath otherwise the
 * file will likely not be found.
 */
@DatabaseChange(name = "copy",
        description = "The 'copy' tag allows you to specify any copy file and have it stored externally in a file. It is useful for fast importing of data that are not supported through LiquiBase's subsystem.\n",
        priority = ChangeMetaData.PRIORITY_DEFAULT)
public class CopyChange extends AbstractChange {

	/**
	 * 
	 */
	private String path;
	
	/**
	 * 
	 */
	private Boolean relativeToChangelogFile = false;
	
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
    private String encoding;
    
	/**
	 * 
	 * @return
	 */
    @DatabaseChangeProperty(description = "The file path of the COPY file to load", requiredForDatabase = "postgres", exampleValue = "my/path/file.sql")
    public String getPath() {
        return path;
    }
    
    /**
     * Sets the file name but setUp must be called for the change to have impact.
     *
     * @param fileName The file to use
     */
    public void setPath(String fileName) {
        path = fileName;
    }
    
    /**
     * The encoding of the file containing SQL statements
     *
     * @return the encoding
     */
    @DatabaseChangeProperty(exampleValue = "utf8")
    public String getEncoding() {
        return encoding;
    }

    /**
     * @param encoding the encoding to set
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    
    /**
     * 
     * @return
     */
    public Boolean isRelativeToChangelogFile() {
        return relativeToChangelogFile;
    }

    /**
     * 
     * @param relativeToChangelogFile
     */
    public void setRelativeToChangelogFile(Boolean relativeToChangelogFile) {
        this.relativeToChangelogFile = relativeToChangelogFile;
    }
    
    /**
     * 
     * @return
     */
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

	@Override
	public SqlStatement[] generateStatements(Database arg0) {
		List<SqlStatement> ret = new ArrayList<SqlStatement>();
		try {
			ret.add(new CopyStatement(openSqlStream(), schemaName, tableName));
		} catch (IOException e) {
			
		}
		return ret.toArray(new SqlStatement[ret.size()]);
	}
	
	@Override
    public void finishInitialization() throws SetupException {
        if (path == null) {
            throw new SetupException("<copy> - No path specified");
        }
        
        if(tableName == null) {
        	throw new SetupException("<copy> - No tableName specified");
        }
    }
	
	public InputStream openSqlStream() throws IOException {
        if (path == null) {
            return null;
        }

        InputStream inputStream = null;
        try {
            inputStream = StreamUtil.openStream(path, isRelativeToChangelogFile(), getChangeSet(), getResourceAccessor());
        } catch (IOException e) {
            throw new IOException("Unable to read file '" + path + "'", e);
        }
        if (inputStream == null) {
            throw new IOException("File does not exist: '" + path + "'");
        }
        return inputStream;
    }
	
    @Override
    public ValidationErrors validate(Database database) {
        ValidationErrors validationErrors = new ValidationErrors();
        if (StringUtils.trimToNull(getPath()) == null) {
            validationErrors.addError("'path' is required");
        }
        
        if(StringUtils.trimToNull(getTableName()) == null) {
        	validationErrors.addError("'tableName' is required");
        }
        return validationErrors;
    }

    @Override
    public String getConfirmationMessage() {
        return "COPY imported in file " + path + " to " + tableName;
    }
}
