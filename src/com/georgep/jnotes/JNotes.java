package com.georgep.jnotes;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.imageio.stream.FileImageInputStream;
import javax.swing.JFrame;
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
		
		final DerbyConnection dc = new DerbyConnection();
		final NoteAdapter na = new NoteAdapter(dc.getConnection());
		
		final Runnable showNotes = new Runnable() {
			@Override
			public void run() {
				for(NoteModel model : na.getNotes()) {
					new Note(model, na).setVisible(true);
				}
			}
		};
		
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
				dc.closeConnection();
			}
		}
		
		Runtime.getRuntime().addShutdownHook(new ShutdownHook());
	}		

}
