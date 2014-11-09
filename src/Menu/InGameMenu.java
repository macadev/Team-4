package Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JTextField;

public class InGameMenu extends MenuTemplate{

    private String[] choice = {"Pause Game", "Continue Game","Return To Main Menu","Quit Game"};
    private int selectedChoice = 0;
    private Color titleColor;
    private Font titleFont;
    private Font font;
    
public InGameMenu() {
    titleColor = new Color(230, 200, 0);
    titleFont = new Font("Century Gothic", Font.PLAIN, 28);
    font = new Font("Arial", Font.PLAIN, 12);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D g1) {
		 g1.setColor(titleColor);
	        g1.setFont(titleFont);
	        g1.setPaint(new Color(255,255,255));
	        g1.drawString("", 80, 70);

	        //draw menu options
	        g1.setFont(font);
	        for(int i = 0; i < choice.length; i++) {
	            if (i == selectedChoice) {
	                g1.setColor(Color.WHITE);
	            } else {
	                g1.setColor(Color.RED);
	            }
	        }


	}

	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void select() {
		
	}
	

}
