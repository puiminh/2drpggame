package entity;

import main.GamePanel;

public class Projectile extends Entity {

	Entity user;
	public Projectile(GamePanel gp) {
		super(gp);
		// TODO Auto-generated constructor stub
	}
	//user: player and monster can use(npc too!)
	public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.alive = alive;
		this.user = user;
		//life mean quang duong no di
		this.life = this.maxLife;
	}
	
	public boolean haveResource(Entity user) {
		boolean haveResource = false;
		return haveResource;
	}
	public void subtractResource(Entity user) {
		
	}
	public void update() {
		
		if(user == gp.player) {
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster, true);
			if(monsterIndex != 999) {
				gp.player.damageMonster(monsterIndex, attack);
				
				generateParticle(user.projectile,gp.monster[gp.currentMap][monsterIndex]);
				
				//dan bi chan
				alive = false;
			}
		}
		if(user != gp.player) {
			boolean contactPlayer = gp.cChecker.checkPlayer(this);
			if(gp.player.invincible == false && contactPlayer == true) {
				damagePlayer(attack);
				
				generateParticle(user.projectile,gp.player);
				alive = false;
			}
		}
		
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
		//quang duong
		life--;
		if(life <= 0) {
			alive = false;
		}
		
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
	}

}
