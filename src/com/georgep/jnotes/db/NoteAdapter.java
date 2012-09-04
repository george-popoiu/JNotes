package com.georgep.jnotes.db;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import static com.georgep.jnotes.db.DbConstants.*;

import com.georgep.jnotes.NoteModel;
import com.georgep.jnotes.ProgressDialog;

public class NoteAdapter {
	
	private Connection conn = null;
	private ArrayList<NoteModel> notes = null;
	private Statement stmt = null;
	
	public NoteAdapter(Connection conn) {
		this.conn = conn;
		loadNotes();
	}
	
	public ArrayList<NoteModel> getNotes() {
		return notes;
	}
	
	public void setNotes(ArrayList<NoteModel> notes) {
		this.notes.clear();
		this.notes.addAll(notes);
	}
	
	public void loadNotes() {		
		try {
			Statement stmt = conn.createStatement();
			if(notes==null) notes = new ArrayList<NoteModel>();
			else notes.clear();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + NOTES_TABLE);
			while(rs.next()) {
				int id = rs.getInt(1);
				String text = rs.getString(2);
				notes.add(new NoteModel(id, text));
			}
			stmt.close();
		}
		catch(Exception e) {
			e.printStackTrace(System.err);
			JOptionPane.showMessageDialog(null, "Cannot query database", 
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	Runnable saveRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				if(stmt == null) stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM " + NOTES_TABLE);
				for(NoteModel nm : notes) {
					stmt.executeUpdate("INSERT INTO " + NOTES_TABLE + 
							" VALUES(" + nm.getID() + ",'" +  nm.getText() + "')");
				}
				stmt.close();
			}
			catch(Exception e) {
				e.printStackTrace(System.err);
				JOptionPane.showMessageDialog(null, "Cannot query database", 
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	};

	public void saveNotes() {
		ProgressDialog pd = new ProgressDialog(new JFrame(), saveRunnable, 
				"Saving notes.");
		pd.setSize(200, 60);
		int sw = Toolkit.getDefaultToolkit().getScreenSize().width;
		int sh = Toolkit.getDefaultToolkit().getScreenSize().height;
		pd.setLocation(sw/2 - pd.getWidth()/2, sh/2 - pd.getHeight()/2);
		pd.setVisible(true);
	}
	
	public int howMany() {
		return notes.size();
	}
	
	public void removeNote(NoteModel model) {
		notes.remove(model);
	}
	
	public NoteModel newNote() {
		NoteModel nm = new NoteModel(0, "");
		notes.add(nm);
		return nm;
	}

}
