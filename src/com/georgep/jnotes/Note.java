package com.georgep.jnotes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import com.georgep.jnotes.db.NoteAdapter;

public class Note extends JFrame {
	/**
	 * Default frame Width
	 */
	protected static int dWidth = 300;
	
	/**
	 * Default frame Height 
	 */
	protected static int dHeight = 300;
	
	private NoteModel model;
	private NoteAdapter adapter;
	
	/**
	 * Builds the UI
	 * @param model - the NoteModel corresponding to a note
	 */
	public Note(NoteModel model, NoteAdapter adapter) {
		this(model.getID(), model.getText());
		this.model = model;
		this.adapter = adapter;
	}
	
	/**
	 * Builds the UI
	 * @param id - the note's id in the database
	 * @param text - the note content
	 */
	private Note(int id, String text) {
		setSize(dWidth, dHeight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int sw = Toolkit.getDefaultToolkit().getScreenSize().width;
		int sh = Toolkit.getDefaultToolkit().getScreenSize().height;
		Random rand = new Random();
		int x = rand.nextInt(sw-dWidth);
		int y = rand.nextInt(sh-dHeight);
		setLocation(x, y);	
		
		buildGUI();
	}
	
	private JPanel buttonPanel = new JPanel();
	
	private JButton newButton = new JButton("New note");
	private JButton clearButton = new JButton("Clear note");
	private JButton deleteButton = new JButton("Delete note");
	private YellowJTextPane textPane = new YellowJTextPane("");
	
	private void buildGUI() {
		Box box = Box.createHorizontalBox();
		
		box.add(newButton);
		box.add(Box.createHorizontalGlue());
		box.add(clearButton);
		box.add(Box.createHorizontalGlue());
		box.add(deleteButton);
		
		buttonPanel.add(box);
		add(buttonPanel, BorderLayout.SOUTH);
				
		add(textPane, BorderLayout.CENTER);
	}
	
}
