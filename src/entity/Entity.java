package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	GamePanel gp;
	//vi tri thuc the (vi tri thuc tren ban do)
	
	
	//describes an Image with an accessible buffer of image data (use to store our image files)
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
	public BufferedImage image, image2, image3;
	
	//khoi tao dien tich vat the
	public Rectangle solidArea = new Rectangle(12,16,28,28);
	//khoi tao dien tich tan cong
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collision = false;
	String dialogues[] = new String[20];
	
	//STATE
	public int worldX, worldY;
	public String direction = "down";
	
	public int spriteNum = 1;
	int dialogueIndex = 0;
	// vo dich
	public boolean invincible = false;
	//co bi chan hay khong
	public boolean collisionOn = false;
	public boolean attacking =false;
	//trong projectile, alive co nghia la dan da bay xong hay chua?
	public boolean alive =true;
	public boolean dying =false;
	boolean hpBarOn = false;
	
	
	//COUNTER
	public int invincibleCounter = 0;
	// tranh quay qua nhieu
	public int actionLockCounter = 0;
	public int spriteCounter = 0;
	public int shotAvailableCounter = 0;
	public int dyingCounter = 0;
	public int hpBarCounter = 0;
	public int overCounter = 0;
	
	//CHARACTER ATTIBUTES
	public String name;
	
	public int speed;
	public int maxLife;
	public int life;
	public int maxMana;
	public int mana;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coin;
	public Entity currentWeapon;
	public Entity currentShield;
	public Projectile projectile;
	
	//ITEM ATTRIBUTES
	public int attackValue;
	public int defenseValue;
	public String description = "...";
	public int useCost;
	public int value;
	
	// TYPE
	
	public int type; // 0 = player, 1 = npc, 2 = monster
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_monster = 2;
	public final int type_sword = 3;
	public final int type_axe = 4;
	public final int type_shield = 5;
	public final int type_consumable =6;
	public final int type_pickUpOnly =7;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public void speak() {
		if(dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
		
		switch(gp.player.direction) {
		case "up":
			direction = "down";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;
		case "down":
			direction = "up";
			break;
		}
	}
	public String hightLightName() {
		String a = "[" + name + "]\n";
		return a;
	}
	public void setAction() {
	}
	public void damageReaction() {	
	}
	public void use(Entity entity) {
		//gp.ui.addMessage("This item can't consuming");
	}
	public void checkDrop() {
		
	}
	public void dropItem(Entity droppedItem) {
		for(int i = 0; i<gp.obj.length; i++) {
			
			//quet qua 1 vong array object, xem con cho trong nao khong, -> co thi bo vat pham drop vao
			
			if(gp.obj[gp.currentMap][i] == null) {
				gp.obj[gp.currentMap][i] = droppedItem;
				
				//toa do dead
				gp.obj[gp.currentMap][i].worldX = worldX;
				gp.obj[gp.currentMap][i].worldY = worldY;
				
				break;
			}
		}
	}
	
	public Color getParticleColor() {
		Color color = new Color(65,50,50);
		return color;
	}
	
	public int getParticleSize() {
		int size = 6; //6px
		return size;
	}
	
	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}
	
	public int getParticleMaxLife() {
		int maxLife = 20;
		return maxLife;
	}
	
	public void generateParticle(Entity generator, Entity target) {
		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();
		int speed = generator.getParticleSpeed();
		int maxLife = generator.getParticleMaxLife();
		
		//lay partice tu thuc the
		//public Particle(GamePanel gp, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd)
		Particle p1 = new Particle(gp, generator, color, size, speed, maxLife, -2, -2);
		Particle p2 = new Particle(gp, generator, color, size, speed, maxLife, 2, -2);
		Particle p3 = new Particle(gp, generator, color, size, speed, maxLife, -2, 2);
		Particle p4 = new Particle(gp, generator, color, size, speed, maxLife, 2, 2);
		gp.particleList.add(p1);
		gp.particleList.add(p2);
		gp.particleList.add(p3);
		gp.particleList.add(p4);
	}
	
	
	public void update() {
		setAction();
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.npc, false);
		gp.cChecker.checkEntity(this, gp.monster, false);
		gp.cChecker.checkEntity(this, gp.iTile, false);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		
		//neu quai vat chay toi nguoi choi
		if(this.type == type_monster && contactPlayer == true) {
			damagePlayer(attack);
		}
		
		if(collisionOn == false) {
			switch(direction) {
			case "up":
				worldY -= speed;
				break;
			case "down":
				worldY += speed;
				break;
			case "left":
				worldX -= speed;
				break;
			case "right":
				worldX += speed;
				break;

			}
		} else setAction();
		spriteCounter++;
		if (spriteCounter > 10) {
			if (spriteNum == 1) {
				spriteNum = 2;
			}
			else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
		
		//reset vo dich
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter>30) {
			invincible =false;
			invincibleCounter = 0;
			}
		}
		
		if(shotAvailableCounter < 30) {
			shotAvailableCounter++;
		}
	}
	
	public void damagePlayer(int attack) {
		if(gp.player.invincible == false) {
			gp.playSE(6);
			int damage = attack - gp.player.defense;
			if (damage < 0) {
				damage = 0; 
			}
			
			gp.player.life -= damage;
			gp.player.invincible = true;
		}
		
	}

	public BufferedImage setup(String imagePath) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	public BufferedImage setup(String imagePath, int width, int height) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, width, height);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	public void draw(Graphics2D g2) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		//trong khi toa do map nam trong vung xung quanh nguoi choi -> ve
		if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize && worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
				worldY > gp.player.worldY - gp.player.screenY - gp.tileSize && worldY < gp.player.worldY + gp.player.screenY + gp.tileSize)
			{
			BufferedImage image = null;
			
			switch(direction) {
				case "up":
					if(spriteNum == 1) {
						image = up1;
					}
					if(spriteNum == 2) {
						image = up2;
					}
					
					break;
				case "down":
					if(spriteNum == 1) {
						image = down1;
					}
					if(spriteNum == 2) {
						image = down2;
					}
					break;
				case "left":
					if(spriteNum == 1) {
						image = left1;
					}
					if(spriteNum == 2) {
						image = left2;
					}
					break;
				case "right":
					if(spriteNum == 1) {
						image = right1;
					}
					if(spriteNum == 2) {
						image = right2;
					}
					break;
			}
			
				//Monster HP Bar
				
				if(type ==2 && hpBarOn == true) {
					
					double oneScale = (double)gp.tileSize/maxLife;
					double hpBarValue = oneScale*life;
					
					//Max Health
					g2.setColor(new Color(35,35,35));
					g2.fillRect(screenX-1, screenY - 16, gp.tileSize+2, 12);
					
					//Real Health
					g2.setColor(new Color(255, 0, 30));
					g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);
					
					hpBarCounter++;
					
					if(hpBarCounter > 300) {
						hpBarCounter = 0;
						hpBarOn = false;
					}
				}			
			
			
				//Cho quai vat vo dich
				if(invincible == true) {
					if (invincibleCounter <=10 || invincibleCounter >20) {
						hpBarOn = true;
						hpBarCounter = 0;
						changeAlpha(g2,0.3f);
					}
				}
				
				if(dying == true) {
					dyingAnimation(g2);
				}
				
				g2.drawImage(image, screenX, screenY, null);
				
				//Reset
				changeAlpha(g2,1f);
				
				g2.setColor(Color.RED);
				g2.drawRect(screenX+solidArea.x, screenY+solidArea.y, solidArea.width, solidArea.height);
				
				
			}
		
	   
	}
	public void dyingAnimation(Graphics2D g2) {
		dyingCounter++;
		
		int i = 10;
		if(dyingCounter <= i) {
			changeAlpha(g2,0f);
		}
		if(dyingCounter > i && dyingCounter <=i*2) {
			changeAlpha(g2,1f);
		}
		if(dyingCounter > i*2 && dyingCounter <=i*3) {
			changeAlpha(g2,0f);
		}
		if(dyingCounter > i*3 && dyingCounter <=i*4) {
			changeAlpha(g2,1f);
		}
		if(dyingCounter > i*4) {
			
			alive = false;
		}
	}
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
}
