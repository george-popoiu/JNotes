package com.georgep.jnotes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JTextPane;

public class YellowJTextPane extends JTextPane {
	
	public YellowJTextPane(String text) {
		super();
		setText(text);
		setOpaque(false);
		
		setBackground(new Color(0, 0, 0, 0));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(new Color(255, 238, 153));
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		super.paintComponent(g);
	}

}
