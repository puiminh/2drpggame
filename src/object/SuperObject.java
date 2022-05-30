package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.UtilityTool;

public class SuperObject {
	public BufferedImage image, image2, image3;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	//moi object deu nhu the nay
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	//Khi ve ra thi bat dau ntn? 0 - 0 -> bat dau nhu binh thuong
	public int solidAreaDefaultX =0;
	public int solidAreaDefaultY =0;
	UtilityTool uTool = new UtilityTool();
	
	public void draw(Graphics2D g2, GamePanel gp) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		//trong khi toa do map nam trong vung xung quanh nguoi choi -> ve
		if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize && worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
				worldY > gp.player.worldY - gp.player.screenY - gp.tileSize && worldY < gp.player.worldY + gp.player.screenY + gp.tileSize)
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
}
