package dream.first.extjs.plugin.dba.dto;

import org.yelong.core.jdbc.sql.ddl.Table;

public class TableDTO extends Table {

	private String tableName;

	public TableDTO() {
		super("TABLE");
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
