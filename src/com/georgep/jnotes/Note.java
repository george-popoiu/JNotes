package com.georgep.jnotes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.Random;

import javax.print.attribute.standard.MediaSize.NA;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.georgep.jnotes.db.NoteAdapter;

public class Note extends JFrame {
	/**
	 * Default frame Width
	 */
	protected static int dWidth = 350;
	
	/**
	 * Default frame Height 
	 */
	protected static int dHeight = 350;
	
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
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
		
		int sw = Toolkit.getDefaultToolkit().getScreenSize().width;
		int sh = Toolkit.getDefaultToolkit().getScreenSize().height;
		Random rand = new Random();
		int x = rand.nextInt(sw-dWidth);
		int y = rand.nextInt(sh-dHeight);
		setLocation(x, y);
		
		textPane.setText(text);
		changeTitle();
				
		buildGUI();
	}
	
	protected void close() {
		if(adapter.howMany()==1) {
			adapter.saveNotes();
			dispose();
			System.exit(0);
		}
		else {
			int result = JOptionPane.showConfirmDialog(null, 
						"This will close all the notes. \n Are you sure ?", 
						"", JOptionPane.YES_NO_OPTION);
			if(result==JOptionPane.YES_OPTION) {
				adapter.saveNotes();
				dispose();
				System.exit(0);
			}
		}
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
				
		add(new JScrollPane(textPane), BorderLayout.CENTER);
		
		textPane.getDocument().addDocumentListener(listener);
		textPane.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		newButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newNote();
			}
		});
	}
	
	protected void newNote() {
		new Note(adapter.newNote(), adapter).setVisible(true);
	}
	
	protected void clear() {
		textPane.setText("");
	}
	
	protected void delete() {
		if(adapter.howMany()==1) {
			clear();
		}
		else {
			adapter.removeNote(model);
			dispose();
		}
	}
	
	private void changeTitle() {
		int i = textPane.getText().indexOf('\n');
		if(i!=-1) {
			String title = textPane.getText().substring(0, i);
			setTitle(title);
		}
		else {
			setTitle(textPane.getText());
		}
	}
	
	private DocumentListener listener = new DocumentListener() {
		@Override
		public void removeUpdate(DocumentEvent e) {
			changeTitle();
			model.setText(textPane.getText());
		}
		@Override
		public void insertUpdate(DocumentEvent e) {
			changeTitle();
			model.setText(textPane.getText());
		}
		@Override
		public void changedUpdate(DocumentEvent e) {
			changeTitle();
			model.setText(textPane.getText());
		}
	};
	
}
