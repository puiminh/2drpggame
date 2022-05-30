package main;



public class EventHandler {
	GamePanel gp;
	EventRect eventRect[][][];
	
	int previousEventX, previousEventY;
	/// nen lam rieng cho tung event
	boolean canTouchEvent = true;
	int tempMap, tempCol, tempRow;
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		
		//where happen
		eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		
		int map = 0;
		int col = 0;
		int row = 0;
		while (col <gp.maxWorldCol && row <gp.maxWorldRow && map < gp.maxMap) {
			//event trigger when move a bit further (middle of the tile)
			eventRect[map][col][row] = new EventRect();
			eventRect[map][col][row].x = 23;
			eventRect[map][col][row].y = 23;
			eventRect[map][col][row].width = 2;
			eventRect[map][col][row].height = 2;
			eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
			eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
			
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
				
				if(row == gp.maxWorldRow) {
					row = 0;
					map++;
				}
			}
		}
	}
	
	public void checkEvent() {
		
		// Check if the player character is more than 1 tile away from the last event
		
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance,  yDistance);
		if(distance > gp.tileSize) {
			canTouchEvent = true;
		}
		
		if(canTouchEvent == true) {
			if(hit(0,23,14,"any") == true) {
				//truyen vao dialogueState -> kich hoat dialogue
				damagePit(gp.dialogueState);
				
			}
			else if(hit(0,23,7,"up") == true) {
				//truyen vao dialogueState -> kich hoat dialogue
				healingPool(gp.dialogueState);
				
			}
			else if(hit(0,12,37,"any") == true) {
				//truyen vao dialogueState -> kich hoat dialogue
				teleport(gp.dialogueState);
				
			}
			else if(hit(0,23,16,"any") == true) {
				teleport(gp.transitionState,1,23,15);
			}
			else if(hit(1,23,16,"any") == true) {
				teleport(gp.transitionState,0,23,17);
			}
		}
		
	}
	
	public boolean hit(int map, int col, int row, String reqDirection) {
		
		boolean hit = false;
		
		
		if(map == gp.currentMap) {
			//tim toa do dien tich thuc te cua x tren ban do
			gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
			gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
			
			//tim toa do dien tich thuc te cua vat
			eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
			eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;
			
			if(gp.player.solidArea.intersects(eventRect[map][col][row])) {
				if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
					hit =true;
					
					previousEventX = gp.player.worldX;
					previousEventY = gp.player.worldY;
				}
			}
			//after check, reset /// -> should have a new val
			gp.player.solidArea.x = gp.player.solidAreaDefaultX;
			gp.player.solidArea.y = gp.player.solidAreaDefaultY;
			eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
			eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
		}
		return hit;
	}
	public void teleport(int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "Teleport!";
		gp.player.worldX = gp.tileSize*37;
		gp.player.worldY = gp.tileSize*10;
		gp.playSE(5);
	}
	public void teleport(int gameState, int mapNumber, int col, int row) {
		
		gp.gameState = gameState;
		
		tempMap = mapNumber;
		tempCol = col;
		tempRow = row;
//		gp.currentMap = mapNumber;
//		gp.ui.currentDialogue = "Teleport!";
//		gp.player.worldX = gp.tileSize*col;
//		gp.player.worldY = gp.tileSize*row;
//		
//		previousEventX = gp.player.worldX;
//		previousEventX = gp.player.worldY;
		
		canTouchEvent = false;
		gp.playSE(5);
		
		
	}
	public void teleport(int gameState, int mapNumber) {
		gp.gameState = gameState;
		gp.currentMap = mapNumber;
		gp.ui.currentDialogue = "Teleport!";
		gp.player.worldX = gp.tileSize*23;
		gp.player.worldY = gp.tileSize*15;
		
		previousEventX = gp.player.worldX;
		previousEventX = gp.player.worldY;
		
		canTouchEvent = false;
		
		gp.playSE(5);
		
	}
	public void damagePit(int gameState) {

//* ONE TIME EVENT		
//		if(eventRect[col][row].eventDone != true) {
//			gp.gameState = gameState;
//			gp.ui.currentDialogue = "You fall into a pit!";
//			gp.player.life -= 1;
//		}
//		eventRect[col][row].eventDone = true;
		gp.playSE(6);
		gp.gameState = gameState;
		gp.ui.currentDialogue = "You fall into a pit!";
		gp.player.life -= 1;
		canTouchEvent = false;
	}
	public void healingPool(int gameState) {
		if(gp.keyH.enterPressed == true) {
			System.out.println("Healing");
			gp.gameState = gameState;
			gp.player.attackCanceled = true;
			gp.ui.currentDialogue = "You drink the water.\nYour life and mana has been recovered.";
			gp.player.life = gp.player.maxLife;
			gp.player.mana = gp.player.maxMana;
			//hoi sinh quai vat
			gp.aSetter.setMonster();
		}
	}
}
