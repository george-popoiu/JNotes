package com.georgep.jnotes;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.imageio.stream.FileImageInputStream;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class JNotes {
	/**
	 * The filename of property file
	 */
	public static final String PROPERTY_FILE = "props";
	/**
	 *  The base filename for every file which is associated with every note
	 */
	public static final String NOTE_FILE_BASE = "note";
	/**
	 * The key for storing the current number of notes
	 */
	public static final String NOTES_KEY = "notes";

	/**
	 * Responsible for loading the existing notes and diplaying them
	 * @param args 
	 */
	public static void main(String[] args) {
		try {
			for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch(Exception e) { }
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				//TODO load notes from the database and display them
			}
		});
	}

}
