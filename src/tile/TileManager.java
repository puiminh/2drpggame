package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][][] = null;
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[10];
		mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/world01.txt",0);
		loadMap("/maps/world02.txt",1);
	}
	
	public void getTileImage() {
		
//		try {
//			tile[0] = new Tile();
//			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			//cai thien hieu suat bang cach cho anh size truoc
			setup(0, "grass", false);
			setup(1, "wall", true);
			setup(2, "water", true);
			setup(3, "earth", false);
			setup(4, "tree", true);
			setup(5, "sand", false);
			setup(6, "hut", false);
			setup(7, "floor01", false);
			setup(8, "table01", false);
	}
	public void setup(int index, String imagePath, boolean collision) {
		UtilityTool uTool =new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
			//co luon kich co khi khoi tao
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void loadMap(String filePath, int map) {
	
		try {
			InputStream is  = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();
				
				while(col <gp.maxWorldCol) {
					//ngan cach 0, bo vao number[0] = ...
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					System.out.print(num + " ");
					mapTileNum[map][col][row] = num;
					col++;
				}
				
				if (col == gp.maxWorldCol) {
					System.out.println("/n");
					col = 0;
					row++;
				}
			}
			br.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
//		g2.setColor(Color.white);
//		//ve hinh vuong
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
//		BufferedImage image = null;
		
//		switch(direction) {
//			case "up":
//				if(spriteNum == 1) {
//					image = up1;
//				}
//				if(spriteNum == 2) {
//					image = up2;
//				}
//				
//				break;
//			case "down":
//				if(spriteNum == 1) {
//					image = down1;
//				}
//				if(spriteNum == 2) {
//					image = down2;
//				}
//				break;
//			case "left":
//				if(spriteNum == 1) {
//					image = left1;
//				}
//				if(spriteNum == 2) {
//					image = left2;
//				}
//				break;
//			case "right":
//				if(spriteNum == 1) {
//					image = right1;
//				}
//				if(spriteNum == 2) {
//					image = right2;
//				}
//				break;
//		}
		
		int worldCol = 0;
		int worldRow = 0;

		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			// ve dua vao currentMap
			int tileNum =  mapTileNum[gp.currentMap][worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			//gp.player.worldX + gp.player.screenX -> vi tri cua nguoi choi. (Gan nhu tat ca deu se phai + screenX vi man hinh da bi di doi ra diem nay)
			//tinh ra toa do ve tren man hinh hien tai. Diem x = Vi tri x thuc te tren map - vi tri thuc te x nguoi choi + vi tri giua man hinh x 
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			//trong khi toa do map nam trong vung xung quanh nguoi choi -> ve
//			if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize && worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
//					worldY > gp.player.worldY - gp.player.screenY - gp.tileSize && worldY < gp.player.worldY + gp.player.screenY + gp.tileSize)
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			worldCol++;
		
			
			if(worldCol == gp.maxWorldCol) {
				worldCol=0;
				worldRow++;
				
			}
		}
		
//		//BAN DO CO DINH
//		int col = 0;
//		int row = 0;
//		int x = 0;
//		int y = 0;
//		
//		while(col < gp.maxScreenCol && row <gp.maxScreenRow) {
//			int tileNum = mapTileNum[col][row];
//			g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
//			col++;
//			x+= gp.tileSize;
//			if(col == gp.maxScreenCol) {
//				col = 0;
//				x = 0;
//				row++;
//				y += gp.tileSize;
//			}
//			
//		}
		
		
	
	}
}
