package org.ratoncio;

import javax.ejb.Remote;
import java.sql.SQLException;

@Remote
public interface EjbSessionPocRemote {
	public void increment(int num);
	
	public int result();

	public void insertDb(int contador) throws SQLException;
}
