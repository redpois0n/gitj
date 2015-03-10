package com.redpois0n.gitj.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.JPanel;

import com.redpois0n.git.Chunk;
import com.redpois0n.git.CodeLine;
import com.redpois0n.git.Diff;

@SuppressWarnings("serial")
public class DiffPanel extends JPanel {
	
	public static final Color COLOR_ADDED = new Color(212, 234, 205);
	public static final Color COLOR_REMOVED = new Color(240, 214, 214);
	public static final Color COLOR_PANEL = new Color(237, 237, 237);
	public static final Color COLOR_PANEL_BORDER = new Color(204, 204, 204);
	
	private Diff diff;
	
	private int prefWidth;
	private int prefHeight;
	
	public DiffPanel(Diff diff) {
		this.diff = diff;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		
		if (prefWidth == 0 || prefHeight == 0) {
			prefHeight += 21;
			
			for (Chunk chunk : diff.getChunks()) {
				prefHeight += 10;
				for (CodeLine line : chunk.getLines()) {
					int sw = metrics.stringWidth(line.getLine());
					
					if (sw > prefWidth) {
						prefWidth = sw;
					}
					
					prefHeight += metrics.getHeight() + 2;
				}
			}
			
			if (metrics.stringWidth(diff.getFile().getAbsolutePath()) > prefWidth) {
				prefWidth = metrics.stringWidth(diff.getFile().getAbsolutePath());
			}
		}
		
		// Top diff file table
		g.setColor(COLOR_PANEL);
		g.fillRect(10, 10, prefWidth, 20);
		g.setColor(COLOR_PANEL_BORDER);
		g.drawRect(10, 10, prefWidth, 20);
		
		g.setColor(Color.gray);
		g.drawString(diff.getFile().getAbsolutePath(), 14, 24);
		
		// Left line number table
		g.setColor(COLOR_PANEL);
		g.fillRect(10, 30, 20, prefHeight);
		g.setColor(COLOR_PANEL_BORDER);
		g.drawRect(10, 30, 20, prefHeight);
						
		int y = 21;
		
		List<Chunk> chunks = diff.getChunks();
		
		for (Chunk chunk : chunks) {
			y += 10;
			for (CodeLine line : chunk.getLines()) {		
				if (line.getType() == CodeLine.Type.ADDED) {
					g.setColor(COLOR_ADDED);
				} else if (line.getType() == CodeLine.Type.REMOVED) {
					g.setColor(COLOR_REMOVED);
				} else {
					g.setColor(Color.white);
				}
								
				g.fillRect(31, y, prefWidth, metrics.getHeight() + 2);
				
				g.setColor(Color.black);
				g.drawString(line.getFixedLine(), 33, y + metrics.getHeight());
				
				y += metrics.getHeight() + 2;
			}
		}
					
		super.setSize(getDimension());
		super.setPreferredSize(getDimension());
	}
	
	public Dimension getDimension() {
		return new Dimension(prefWidth, prefHeight);
	}
	
	public int getPrefWidth() {
		return prefWidth;
	}
	
	public int getPrefHeight() {
		return prefHeight;
	}
}
