#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package daos;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class PersonDaoImpl implements PersonDao {
	private JdbcTemplate tmpl;
	private final String[] primaryKey = new String[]{"id"};
	
	public void setDataSource(DataSource dataSource) {
		tmpl = new JdbcTemplate();
		tmpl.setDataSource(dataSource);
	}
	
	@Override
	public void insertUUID() {
		final String insertSql = "insert into UUIDTest (id) values (unhex(replace(uuid(), '-', '')))";
		tmpl.update(insertSql);
	}
}
