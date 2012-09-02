package com.georgep.jnotes;

public class NoteModel {
	
	private int id;
	private String text	;
	
	public NoteModel() { }
	
	/**
	 * Creates a NoteModel object
	 * @param id - The id corresponding to the note in the database
	 * @param text - The actual note's content
	 */
	public NoteModel(int id, String text) {
		this.id = id;
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}

}
