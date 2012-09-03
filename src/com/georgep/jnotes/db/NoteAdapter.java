package com.georgep.jnotes.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import static com.georgep.jnotes.db.DbConstants.*;

import com.georgep.jnotes.NoteModel;

public class NoteAdapter {
	
	private Connection conn = null;
	private ArrayList<NoteModel> notes = null;
	
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
			rs.close();
			stmt.close();
		}
		catch(Exception e) {
			e.printStackTrace(System.err);
			JOptionPane.showMessageDialog(null, "Cannot query database", 
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void saveNotes() {
		//TODO add code for saving notes to the database
	}

}
