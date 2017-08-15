package Sprites;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class EnemySprite implements SpriteSheet{
	
	Image img;
	int frameX1, frameY1;
	int frameX2, frameY2;
	int tileWidth, tileHeight;
	long startTimeNano, runTimeNano;
	long startTimeMill, runTimeMill;
	public EnemySprite (String fileName)
	{
		img = new ImageIcon("src/res/" + fileName).getImage();
		int rows = 4; int columns = 4;
		tileWidth = img.getWidth(null) / columns; tileHeight = img.getHeight(null) / rows;
		frameX1 = 0; frameY1 = 0;
		frameX2 = tileWidth; frameY2 = tileHeight;
		startTimeNano = System.nanoTime();
		runTimeNano = 50000000;
		startTimeMill = System.currentTimeMillis();
		runTimeMill = 100;
	}
	
	public void idleAnimation()
	{
		frameY1 = tileHeight * 2; frameY2 = tileHeight * 3; 
		if(startTimeMill + runTimeMill < System.currentTimeMillis() - runTimeMill) {
			startTimeMill = System.currentTimeMillis();
			frameX1 += tileWidth;
			frameX2 += tileWidth;
			if(frameX2 > img.getWidth(null)) {frameX1 = 0; frameX2 = tileWidth;}
		}
	}
	
	int countFrames = 0;
	public void startAnimation(){
		frameY1 = tileHeight * countFrames; frameY2 = tileHeight * (countFrames + 1); 
		if(startTimeNano + runTimeNano < System.nanoTime() - runTimeNano) {
			startTimeNano = System.nanoTime();
			frameX1 += tileWidth;
			frameX2 += tileWidth;
			if(frameX2 > img.getWidth(null)) {frameX1 = 0; frameX2 = tileWidth; countFrames++;}
			if(frameY2 > img.getHeight(null) / 2) {countFrames = 0;}
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