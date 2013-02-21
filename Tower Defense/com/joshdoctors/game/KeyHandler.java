package com.joshdoctors.game;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import com.joshdoctors.game.gfx.Screen;

public class KeyHandler implements MouseMotionListener,MouseListener {


	public void mouseClicked(MouseEvent e) {
	
		
	}

	
	public void mouseEntered(MouseEvent e) {
		
		
	}

	
	public void mouseExited(MouseEvent e) {
		
		
	}

	
	public void mousePressed(MouseEvent e) {
		
		Screen.store.click(e.getButton());
		
		if(!Screen.hasEnteredGame)
			try {
				Screen.menu.click(e.getButton());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}

	
	public void mouseReleased(MouseEvent e) {
		
		
	}

	
	public void mouseDragged(MouseEvent e) {
		Screen.mse = new Point((e.getX()) - ((Game.size.width - Screen.myWidth)/2) , (e.getY()) - ((Game.size.height - (Screen.myHeight)) - (Game.size.width - Screen.myWidth)/2));
		
	}

	
	public void mouseMoved(MouseEvent e) {
		Screen.mse = new Point((e.getX()) - ((Game.size.width - Screen.myWidth)/2) , (e.getY()) - ((Game.size.height - (Screen.myHeight)) - (Game.size.width - Screen.myWidth)/2));
		
	}

}
