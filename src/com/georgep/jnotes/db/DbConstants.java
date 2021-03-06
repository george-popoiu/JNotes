package com.georgep.jnotes.db;

public class DbConstants {
	
	/**
	 * The connection string for the database
	 */
	public static final String DB_URL = "jdbc:derby:myDB;create=true";
	
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
	
	/**
	 * The id column
	 */
	public static final String ID_COLUMN = "ID";
	
	/**
	 * Column for storing the note data
	 */
	public static final String TEXT_COLUMN = "TEXT";
}
