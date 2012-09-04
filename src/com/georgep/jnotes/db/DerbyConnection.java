package com.georgep.jnotes.db;

import java.awt.Toolkit;
import java.io.Console;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import static com.georgep.jnotes.db.DbConstants.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.georgep.jnotes.ProgressDialog;


public class DerbyConnection {
	
	private Connection conn = null;
	
	/**
	 * Connects to the database and creates the schema
	 */
	public DerbyConnection() {
		createConnection();
		createSchema();
	}
	
	private void createSchema() {
		try {
			if(conn==null) throw new Exception();
			conn.setAutoCommit(true);
			DatabaseMetaData dmd = conn.getMetaData();
			ResultSet rs = dmd.getTables(null, null, null, new String[] {"TABLE"});
			if(rs.next()==false) {
				Statement stmt = conn.createStatement();			
				stmt.executeUpdate("CREATE TABLE " + NOTES_TABLE + 
						"(" + ID_COLUMN + " int, " + TEXT_COLUMN +" CLOB)");
				stmt.executeUpdate("INSERT INTO " + NOTES_TABLE + " VALUES(1, '')");
				stmt.close();
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace(System.err);
			JOptionPane.showMessageDialog(null, "Cannot create schema", 
					"Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	Runnable dbconnection = new Runnable() {
		@Override
		public void run() {
			try {
				Class.forName(DbConstants.DB_DRIVER).newInstance();
				conn = DriverManager.getConnection(DbConstants.DB_URL);
			}
			catch(Exception e) {
				e.printStackTrace(System.err);
				JOptionPane.showMessageDialog(null, "Cannot connect to the local database", 
						"Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
	};
	
	private void createConnection() {
		ProgressDialog pd = new ProgressDialog(new JFrame(), dbconnection, 
				"Connecting to the database. \n Please wait.");
		pd.setSize(330, 60);
		int sw = Toolkit.getDefaultToolkit().getScreenSize().width;
		int sh = Toolkit.getDefaultToolkit().getScreenSize().height;
		pd.setLocation(sw/2 - pd.getWidth()/2, sh/2 - pd.getHeight()/2);
		pd.setVisible(true);
	}
	
	public Connection getConnection() {
		return conn;
	}
	
	public void closeConnection() {
		try {
			if(conn!=null) {
				DriverManager.getConnection(DB_SHUTDOWN_URL);
				conn.close();
			}
		}
		catch(Exception e) {
			//e.printStackTrace(System.err);
		}
	}

}

