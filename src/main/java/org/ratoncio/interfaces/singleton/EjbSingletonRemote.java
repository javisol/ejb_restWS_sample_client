package org.ratoncio.interfaces.singleton;

import javax.ejb.Remote;
import java.sql.SQLException;

@Remote
public interface EjbSingletonRemote {
	public void increment(int num);
	
	public int result();

	public void insertDb(int contador) throws SQLException;
}
