package gitj.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import gitj.Language;

@SuppressWarnings("serial")
public class LanguageBar extends JComponent implements MouseListener, MouseMotionListener {
	
	public static final Font FONT_BOLD = new Font("Arial", Font.BOLD, 12);
	public static final Font FONT_NORMAL = new Font("Arial", Font.PLAIN, 12);

	private List<Language> languages = new ArrayList<Language>();
	
	public LanguageBar() {
		super.setPreferredSize(new Dimension(50, 40));
		super.addMouseListener(this);
		super.addMouseMotionListener(this);
	}
	
	public LanguageBar(List<Language> languages) {
		this();
		this.languages = languages;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		super.paintComponent(g);
		
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		int max = 0;
		
		for (Language l : languages) {
			max += l.getLineCount();
		}
		
		int startText = 10;
		int start = 0;
		
		for (Language l : languages) {
			String s = l.getLanguage();
			float fp = (((float) l.getLineCount() / (float) max) * 100);
			String percent = String.format("%.4g%n", fp) + "%";
			
			if (fp < 0.1F) {
				continue;
			}
			
			g.setColor(Color.black);
			g.setFont(FONT_NORMAL);
			g.drawString(s, startText + 15, 5 + g.getFontMetrics().getHeight());
			g.setColor(Color.gray);
			g.setFont(FONT_BOLD);
			g.drawString(percent, startText + 17 + g.getFontMetrics().stringWidth(s), 5 + g.getFontMetrics().getHeight());
			
			if (l.isOther()) {
				g.setColor(Color.lightGray);
			} else {
				g.setColor(l.getColor());
			}
			
			g.fillOval(startText, 10, 10, 10);
			
			startText += g.getFontMetrics().stringWidth(s) + 25;
			startText += g.getFontMetrics().stringWidth(percent);
			
			int todraw = (int) (((float) l.getLineCount() / (float) max) * getWidth());
			
			g.fillRect(start, getHeight() - 10, todraw, 10);
			
			start += todraw;
		}
	}
	
	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

}
