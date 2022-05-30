package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

public class MON_GreenSlime extends Entity{

	GamePanel gp;
	public MON_GreenSlime(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		name = "Green Slime";
		speed = 1;
		maxLife = 4;
		life = maxLife;
		
		attack = 5;
		defense = 0;
		
		exp =2;
		
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		projectile = new OBJ_Rock(gp);
		
		type = type_monster;
		
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/monsters/greenslime_down_1");
		up2 = setup("/monsters/greenslime_down_2");
		down1 = setup("/monsters/greenslime_down_1");
		down2 = setup("/monsters/greenslime_down_2");
		left1 = setup("/monsters/greenslime_down_1");
		left2 = setup("/monsters/greenslime_down_2");
		right1 = setup("/monsters/greenslime_down_1");
		right2 = setup("/monsters/greenslime_down_2");
	}
	
	public void setAction() {
		actionLockCounter ++;
		
		if(actionLockCounter >= 120) {
			Random random = new Random();
			//pick up random form 1 to 100
			int i = random.nextInt(100)+1;
			
			if(i <= 25) {
				direction = "up";
			}
			if(i > 25 && i <= 50) {
				direction = "down";
			}
			if(i > 50 && i <= 75) {
				direction = "right";
			}
			if(i > 75 && i <= 100) {
				direction = "left";
			}
			
			actionLockCounter = 0;
			
		}
		
		int i = new Random().nextInt(100) + 1;
		if(i>99 && projectile.alive == false && shotAvailableCounter == 30) {
			projectile.set(worldX, worldY, direction, true, this);
			gp.projectileList.add(projectile);
			shotAvailableCounter = 0;
		}
	}
	public void damageReaction() {
		actionLockCounter = 0;
		direction = gp.player.direction;
		speed = 2;
	}
	
	public void checkDrop() {
		
		int i = new Random().nextInt(100) +1;
		
		if(i<50) {
			dropItem(new OBJ_Coin_Bronze(gp));
		}
		if(i>=50 && i<75) {
			dropItem(new OBJ_Heart(gp));
		}
		if(i>=75) {
			dropItem(new OBJ_ManaCrystal(gp));
		}
	}
	
}
