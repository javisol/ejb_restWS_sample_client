package org.ratoncio.singleton;

import javax.ejb.Remote;
import java.sql.SQLException;

@Remote
public interface EjbSessionPocSingletonRemote {
	public void increment(int num);
	
	public int result();

	public void insertDb(int contador) throws SQLException;
}
