package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;
import object.SuperObject;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font arial_40, arial_80B, maruMonica, lolFont;
	BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank;
	public boolean messageOn = false;
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean gameFinished = false;
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	public int commandNum = 0;
	
	public String currentDialogue = "";
	
	public int slotCol = 0;
	public int slotRow = 0;
	
	public int counter = 0;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);
		
		
		try {
			InputStream is = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/fonts/Friz Quadrata Bold.otf");
			lolFont = Font.createFont(Font.TRUETYPE_FONT, is);
			
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		OBJ_Key key = new OBJ_Key(gp);
//		keyImage = key.image;
		
		// CREATE HUB OBJECT
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		
		Entity crystal = new OBJ_ManaCrystal(gp);
		crystal_full = crystal.image;
		crystal_blank = crystal.image2;
	}
	
	public void addMessage(String text) {
		
		message.add(text);
		messageCounter.add(0);
	}
	
	public void draw(Graphics2D g2) {
		// vi du rat xau: (goi nhieu lan) g2.setFont(new Font("Arial", Font.PLAIN, 40)); -> khai bao tu dau
		
		this.g2 = g2;
		
		g2.setFont(maruMonica);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);
		
		// TITLE STATE
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		
		// PLAY STATE
		if(gp.gameState == gp.playState) {
			// Do playState
			drawPlayerLife();
			drawMessage();
		}
		
		//PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
			drawPlayerLife();
		}
		
		// DIALOG STATE
		if(gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
			drawPlayerLife();
		}
		
		// CHARACTER STATE
		
		if(gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory();
			drawMessage();
		}
		
		// GAME OVER STATE
		if(gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}

		// Transition STATE
		if(gp.gameState == gp.transitionState) {
			drawTransition();
		}
		
	}
	
	private void drawTransition() {
		// TODO Auto-generated method stub
		counter++;
		g2.setColor(new Color(0,0,0,counter*5));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		 
		if(counter == 50) {
			counter = 0;
			gp.gameState = gp.playState;
			gp.currentMap = gp.eHandler.tempMap;
			gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
			gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
			
			gp.eHandler.previousEventX = gp.player.worldX;
			gp.eHandler.previousEventY = gp.player.worldY;
		}
	}

	public void drawGameOverScreen() {
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));
		
		text = "GAME OVER";
		//SHADOW
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.tileSize*4;
		g2.drawString(text, x, y);
		//TEXT
		g2.setColor(Color.white);
		x = getXforCenteredText(text);
		y = gp.tileSize*4;
		g2.drawString(text, x-4, y-4);
		
		// RETRY
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "Retry";
		x = getXforCenteredText(text);
		y += gp.tileSize*4;
		g2.drawString(text, x, y);
		
		if(commandNum == 0) {
			g2.drawString(">", x - 40, y);
		}
		
		// QUIT
		text = "QUIT";
		x = getXforCenteredText(text);
		y += 55;
		g2.drawString(text, x, y);
		
		if(commandNum == 1) {
			g2.drawString(">", x - 40, y);
		}
		
	}
	
	public void drawPlayerLife() {
		
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		
		int i = 0;
		
		//DRAW BLANK HEART
		
		while(i < gp.player.maxLife/2) {
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x+= gp.tileSize;
		}
		
		//RESET
		 x = gp.tileSize/2;
		 y = gp.tileSize/2;
		 i = 0;
		 
		//DRAW CURRENT HEART
		while(i < gp.player.life) {
			g2.drawImage(heart_half, x, y, null);
			i++;
			if(i <gp.player.life) {
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x+= gp.tileSize;
		}
		
		
		//DRAW MAX MANA
		x = gp.tileSize/2 - 5;
	    y = (int) (gp.tileSize*1.5);
		
		i = 0;
		
		//DRAW BLANK HEART
		
		while(i < gp.player.maxMana) {
			g2.drawImage(crystal_blank, x, y, null);
			i++;
			x+= 35;
		}
		
		//RESET
		 x = gp.tileSize/2 - 5;
		 y = (int) (gp.tileSize*1.5);
		 i = 0;
		 
		//DRAW CURRENT HEART
		while(i < gp.player.mana) {
			g2.drawImage(crystal_full, x, y, null);
			i++;
			x+= 35;
		}
		
		
		
	}
	
	public void drawMessage() {
		
		int messageX = gp.tileSize;
		int messageY = gp.tileSize*4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
		
		for(int i = 0; i < message.size(); i++) {
			if(message.get(i) != null) {
				
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX+2, messageY+2);
				
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
				
				int counter = messageCounter.get(i) + 1; //messageCounter ++
				messageCounter.set(i, counter); // set the counter to the array
			
				messageY += 50;
				if(messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}
	}
	
	public void drawTitleScreen() {
		
		// BACKGROUND
		g2.setColor(new Color(0,0,0));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		//TITLE NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
		String text =  "Blue Boy Adventure";
		int x = getXforCenteredText(text);
		int y = gp.tileSize*3;
		
		// SHADOW (lam 1 text i het, roi xe xich 1 chut)
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);
		
		
		// MAIN COLOR
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		// BLUE BOY IMAGE
		x = gp.screenWidth/2 - (gp.tileSize);
		y += gp.tileSize*2;
		g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
		
		// MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		
		text = "NEW GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize*3.5;
		g2.drawString(text, x, y);
		
		if(commandNum == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		text = "LOAD GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);

		if(commandNum == 1) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		text = "QUIT";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		
		if(commandNum == 2) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
	}
	public void drawPauseScreen() {
		String text = "PAUSED";
		
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		g2.drawString(text, x, y);
	}
	
	public void drawDialogueScreen() {
		// WINDOW
		int x = gp.tileSize * 2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*4);
		int height = gp.tileSize*4;
		
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
		x+= gp.tileSize;
		y+= gp.tileSize;
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y+= 40;
		}
		
	}
	
	public void drawInventory() {
		
		
		// FRAME
		int frameX = gp.tileSize*9;
		int frameY =  gp.tileSize;
		int frameWidth = gp.tileSize*6;
		int frameHeight = gp.tileSize*5;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// SLOT
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gp.tileSize + 3;
		
		// CURSOR
		int cursorX = slotXstart + (slotSize * slotCol);
		int cursorY = slotYstart + (slotSize * slotRow);
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;
		
		// DRAW PLAYER'S ITEMS
		for(int i = 0; i<gp.player.inventory.size(); i++) {
			
			// EQUIP CURSOR
			if(gp.player.inventory.get(i) == gp.player.currentWeapon 
					|| gp.player.inventory.get(i) == gp.player.currentShield) {
				g2.setColor(new Color(240,190,90));
				g2.fillRoundRect(slotX,slotY,cursorWidth,cursorHeight, 10, 10);
			}
			
			g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
			slotX += slotSize;
			
			if(i == 4 || i == 9 || i == 14) {
				//reset x
				slotX = slotXstart;
				slotY += slotSize;
			}
		}
		
		// DRAW CURSOR
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
		
		// DESCRIPTION FRAME
		int dFrameX = frameX;
		int dFrameY = frameY + frameHeight;
		int dFrameWidth = frameWidth;
		int dFrameHeight = gp.tileSize*3;
		
		
		// DESCRIPTION TEXT
		int textX = dFrameX + 20;
		int textY = dFrameY + gp.tileSize;
		g2.setFont(g2.getFont().deriveFont(28F));
		
		int itemIndex = getItemIndexOnSlot();
		
		if(itemIndex < gp.player.inventory.size()){
			drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
			for(String line: gp.player.inventory.get(itemIndex).description.split("\n")) {
				g2.drawString(line, textX, textY);
				textY += 32;
			}
		}
		
		
		
		
	}
	
	public int getItemIndexOnSlot() {
		int itemIndex = slotCol + (slotRow*5);
		return itemIndex;
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0, 0 ,0,210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
	}
	public void drawCharacterScreen() {
		
		// CREATE A FRAME
		final int frameX = gp.tileSize;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize*5;
		final int frameHeight = gp.tileSize*10;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 35;
		
		// NAMES
		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Life", textX, textY);
		textY += lineHeight;
		g2.drawString("Mana", textX, textY);
		textY += lineHeight;
		g2.drawString("Strength", textX, textY);
		textY += lineHeight;
		g2.drawString("Dexterity", textX, textY);
		textY += lineHeight;
		g2.drawString("Attack", textX, textY);
		textY += lineHeight;
		g2.drawString("Defense", textX, textY);
		textY += lineHeight;
		g2.drawString("Exp", textX, textY);
		textY += lineHeight;
		g2.drawString("Next Levl", textX, textY);
		textY += lineHeight;
		g2.drawString("Coin", textX, textY);
		textY += lineHeight + 10;
		g2.drawString("Weapon", textX, textY);
		textY += lineHeight + 15;
		g2.drawString("Shield", textX, textY);
		textY += lineHeight;
		
		//VALUES
		int tailX = (frameX + frameWidth) - 30;
		//Reset textY
		textY = frameY + gp.tileSize;
		String value;
		
		value = String.valueOf(gp.player.level);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value,textX, textY);
		
		textY += lineHeight;
		value = String.valueOf(gp.player.life + "/" +gp.player.maxLife);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value,textX, textY);
		
		textY += lineHeight;
		value = String.valueOf(gp.player.mana + "/" +gp.player.maxMana);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value,textX, textY);
		
		textY += lineHeight;
		value = String.valueOf(gp.player.strength);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value,textX, textY);
		
		textY += lineHeight;
		value = String.valueOf(gp.player.dexterity);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value,textX, textY);
		
		textY += lineHeight;
		value = String.valueOf(gp.player.attack);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value,textX, textY);
		
		textY += lineHeight;
		value = String.valueOf(gp.player.defense);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value,textX, textY);
		
		textY += lineHeight;
		value = String.valueOf(gp.player.exp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value,textX, textY);
		
		textY += lineHeight;
		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value,textX, textY);
		
		textY += lineHeight;
		value = String.valueOf(gp.player.coin);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value,textX, textY);
		
		textY += lineHeight;
		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY-24, null);
		textY += gp.tileSize;
		g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY-24, null);
	}
	public int getXforCenteredText(String text) {
		int textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
		int x = gp.screenWidth/2 - textLength/2;
		return x;
	}
	public int getXforAlignToRightText(String text, int tailX) {
		int textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
		int x = tailX - textLength;
		return x;
	}
}
