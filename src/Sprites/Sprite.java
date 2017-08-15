package Sprites;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Sprite implements SpriteSheet{
	
	Image img;
	int frameX1, frameY1;
	int frameX2, frameY2;
	int tileWidth, tileHeight;
	long startTime, runTime;
	public Sprite (String fileName)
	{
		img = new ImageIcon("src/res/" + fileName).getImage();
		tileWidth = img.getWidth(null) / 4; tileHeight = img.getHeight(null) / 4;
		frameX1 = 0; frameY1 = 0;
		frameX2 = tileWidth; frameY2 = tileHeight;
		startTime = System.nanoTime();
		runTime = 100000000;
	}
	
	public void idleAnimation()
	{
		if(startTime + runTime < System.nanoTime() - runTime) {
			startTime = System.nanoTime();
			frameX1 += tileWidth;
			frameX2 += tileWidth;
			//if(frameX2 > img.getWidth(null)) {frameX1 = 0; frameX2 = tileWidth;frameY1 += tileHeight; frameY2 += tileHeight;}
			//if(frameY2 > img.getHeight(null)) {frameY1 = 0; frameY2 = tileHeight;}
			if(frameX2 > img.getWidth(null)) {frameX1 = 0; frameX2 = tileWidth;}
		}
	}

	@Override
	public void attackAnimation() {
		frameX1 = tileWidth * 1;
		frameX2 = tileWidth * 2;
		frameY1 = tileHeight * 0;
		frameY2 = tileHeight * 1;
	}

	@Override
	public void slashAnimation() {
		frameX1 = tileWidth * 2;
		frameX2 = tileWidth * 3;
		frameY1 = tileHeight * 0;
		frameY2 = tileHeight * 1;	
	}

	@Override
	public void bounce() {
		frameX1 = tileWidth * 0;
		frameX2 = tileWidth * 1;
		frameY1 = tileHeight * 0;
		frameY2 = tileHeight * 1;	
		
	}

	@Override
	public void backingOff() {
		frameX1 = tileWidth * 2;
		frameX2 = tileWidth * 3;
		frameY1 = tileHeight * 0;
		frameY2 = tileHeight * 1;	
	}
	
	public void draw(Graphics2D g, int x, int y, int width, int height)
	{
		g.drawImage(img, x - 20, y - 20, x + width + 20, y + height + 20, frameX1, frameY1, frameX2, frameY2,  null);
	}
}