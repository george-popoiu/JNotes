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

import com.georgep.jnotes.db.DerbyConnection;
import com.georgep.jnotes.db.NoteAdapter;

public class JNotes {

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
		
		final DerbyConnection dc = new DerbyConnection();
		final NoteAdapter na = new NoteAdapter(dc.getConnection());
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				for(NoteModel model : na.getNotes()) {
					new Note(model, na).setVisible(true);
				}
			}
		});
		
		class ShutdownHook extends Thread {
			@Override
			public void run() {
				na.saveNotes();
				dc.closeConnection();
			}
		}
		
		Runtime.getRuntime().addShutdownHook(new ShutdownHook());
	}		

}
