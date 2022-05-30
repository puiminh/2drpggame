package main;

import entity.Entity;

public class CollisionChecker {
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
		
		//check xem hitbox cua thuc the
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		// quy doi ra col, row
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		int tileNum1, tileNum2;
		//check direction va tinh truoc xem no co bi chan hay k
		switch(entity.direction) {
		case "up":
			//neu la up, check xem co lot hay khong
			//tileM la obj chua ban do map
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
			
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
			
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
			
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
			
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		}
	}
	public int checkObject(Entity entity, boolean player) {
		
		int index = 999;
		//vi minh chi co it object -> co the lam ntn.
		for(int i =0; i <gp.obj[gp.currentMap].length; i++) {
			if(gp.obj[gp.currentMap][i] != null) {
				//Get entity's solid area position
				//cho phan dien tich cua thuc the (vi tri bat dau ve) = chinh no + toa do => Toa do thuc tren ban do
				
				/// dang nhe nen tao ra 1 bien phu de luu tru toa do thuc te nay
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				//get the object's solid area position
				//vi chung ta da set 0 cho object's solid area x va y. Phan sau day chua co y nghia gi. (nhung se co neu chung ta dat gia tri cho tung object)
				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;
				
				switch(entity.direction) {
				case "up":
					//check xem khi cai nay di chuyen lieu co trung voi object dang co tren ban do hay khong
					entity.solidArea.y -= entity.speed;
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					break;
				}
				//check if 2 cai nay co trung nhau hay khong?
				if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
					
					System.out.println(i);
					if(gp.obj[gp.currentMap][i].collision == true) {
						entity.collisionOn = true;
					}
					if(player == true) {
						index = i;
					}
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
			}
		}
		return index;
	}
	// NPC OR MONSTER
	public int checkEntity(Entity entity, Entity[][] target, boolean player) {
		int index = 999;
		//vi minh chi co it object -> co the lam ntn.
		for(int i =0; i <target[gp.currentMap].length; i++) {
			if(target[gp.currentMap][i] != null) {
				//Get entity's solid area position
				//cho phan dien tich cua thuc the (vi tri bat dau ve) = chinh no + toa do => Toa do thuc tren ban do
				
				/// dang nhe nen tao ra 1 bien phu de luu tru toa do thuc te nay
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				//get the object's solid area position
				//vi chung ta da set 0 cho object's solid area x va y. Phan sau day chua co y nghia gi. (nhung se co neu chung ta dat gia tri cho tung object)
				target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
				target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;
				
				switch(entity.direction) {
				case "up":
					//check xem khi cai nay di chuyen lieu co trung voi object dang co tren ban do hay khong
					entity.solidArea.y -= entity.speed;
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					break;
				}
				//check if 2 cai nay co trung nhau hay khong?
				if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
					// tranh ban than no so sanh voi chinh no
					if(target[gp.currentMap][i] != entity) {
						System.out.println(i);
						entity.collisionOn = true;
						if(player == true) {
							index = i;
						}
					}
					
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
				target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;
			}
		}
		return index;
	}
	public boolean checkPlayer(Entity entity) {
		
		boolean contactPlayer = false;
		
		if(gp.player != null) {
			//Get entity's solid area position
			//cho phan dien tich cua thuc the (vi tri bat dau ve) = chinh no + toa do => Toa do thuc tren ban do
			
			/// dang nhe nen tao ra 1 bien phu de luu tru toa do thuc te nay
			entity.solidArea.x = entity.worldX + entity.solidArea.x;
			entity.solidArea.y = entity.worldY + entity.solidArea.y;
			//get the object's solid area position
			//vi chung ta da set 0 cho object's solid area x va y. Phan sau day chua co y nghia gi. (nhung se co neu chung ta dat gia tri cho tung object)
			gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
			gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
			
			switch(entity.direction) {
			case "up":
				//check xem khi cai nay di chuyen lieu co trung voi object dang co tren ban do hay khong
				entity.solidArea.y -= entity.speed;
				
				break;
			case "down":
				entity.solidArea.y += entity.speed;
				
				break;
			case "left":
				entity.solidArea.x -= entity.speed;
				
				break;
			case "right":
				entity.solidArea.x += entity.speed;
				
				break;
			}
			//check if 2 cai nay co trung nhau hay khong?
			if(entity.solidArea.intersects(gp.player.solidArea)) {
				System.out.println("Va cham voi nguoi choi");
				entity.collisionOn = true;
				contactPlayer =true;
			}
			entity.solidArea.x = entity.solidAreaDefaultX;
			entity.solidArea.y = entity.solidAreaDefaultY;
			gp.player.solidArea.x = gp.player.solidAreaDefaultX;
			gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		}
		
		return contactPlayer;
		
	}
}
