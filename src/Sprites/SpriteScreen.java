package Sprites;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class SpriteScreen{
	
	Image img;
	int frameX1, frameY1;
	int frameX2, frameY2;
	int tileWidth, tileHeight;
	long startTime, runTime;
	public SpriteScreen (String fileName)
	{
		img = new ImageIcon("src/res/" + fileName).getImage();
		tileWidth = 800 / 2; tileHeight = 800 / 4;
		frameX1 = 0; frameY1 = 0;
		frameX2 = tileWidth; frameY2 = tileHeight;
		startTime = System.nanoTime();
		runTime = 120000000;
	}
	
	public void idleAnimation()
	{
		if(startTime + runTime < System.nanoTime() - runTime) {
			startTime = System.nanoTime();
			frameX1 += tileWidth;
			frameX2 += tileWidth;
			if(frameX2 > img.getWidth(null)) {frameX1 = 0; frameX2 = tileWidth;frameY1 += tileHeight; frameY2 += tileHeight;}
			if(frameY2 > img.getHeight(null)) {frameY1 = 0; frameY2 = tileHeight;}
			//if(frameX2 > img.getWidth(null)) {frameX1 = 0; frameX2 = tileWidth;}
		}
	}
	public void draw(Graphics2D g, int x, int y, int width, int height)
	{
		g.drawImage(img, x, y, x + width, y + height, frameX1, frameY1, frameX2, frameY2,  null);
	}
}