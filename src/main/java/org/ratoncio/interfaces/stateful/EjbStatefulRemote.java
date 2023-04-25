package org.ratoncio.interfaces.stateful;

import javax.ejb.Remote;
import java.sql.SQLException;

@Remote
public interface EjbStatefulRemote {
	public void increment(int num);
	
	public int result();

	public void insertDb(int contador) throws SQLException;
}
