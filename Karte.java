
import java.awt.*;

import javax.swing.*;


public class Karte extends JPanel {
	
	public ImageIcon motiv,motivResize;
	public ImageIcon rueckseite = new ImageIcon("karten/rueckseite.png");
	
	
	public Karte(String pfad) {
		
		this.motiv = new ImageIcon(pfad);
		Image image = this.motiv.getImage(); // transform it 
		Image newImg = image.getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH);
		this.motivResize = new ImageIcon(newImg);
		
	}
	
}
