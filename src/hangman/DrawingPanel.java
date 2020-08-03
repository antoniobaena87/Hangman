package hangman;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawingPanel extends JPanel{
	
	private BufferedImage[] images;
	private Image currentImage;
	private final int numImagesInFolder = 7;
	private int currentIndex;
	
	public DrawingPanel() {
		
		images = new BufferedImage[numImagesInFolder];
		// load the images
		for(int i = 0; i < numImagesInFolder; i++) {
			try {
				images[i] = ImageIO.read(getClass().getResource("/assets/"+(i+1)+".gif"));
			}catch(IOException ex) {
				System.out.println(ex);
			}
		}
		currentImage = images[0];
		// resize to fit image
		setPreferredSize(new Dimension(currentImage.getWidth(null), currentImage.getHeight(null)));
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(currentImage, 0, 0, null);
	}
	
	protected void showNextImage() {
		currentIndex++;
		currentImage = images[currentIndex];
		repaint();
	}
	
	protected void reset() {
		currentImage = images[0];
		currentIndex = 0;
		repaint();
	}
}
