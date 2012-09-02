package com.georgep.jnotes.db;

public class DbConstants {
	/**
	 * The database name.
	 */
	public static final String DB_NAME = "JNotesDB";
	
	/**
	 * The connection string for the database
	 */
	public static final String DB_URL = "jdbc:derby:" + DB_NAME + ";create=true";
	
	/**
	 * The URL for shutting down the database
	 */
	public static final String DB_SHUTDOWN_URL = DB_URL + ";shutdown=true";
	
	/**
	 * The name of the main table (where the notes' content will be kept)
	 */
	public static final String NOTES_TABLE = "notes";
	
	/**
	 * The database driver
	 */
	public static final String DB_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
}
