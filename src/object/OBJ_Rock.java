package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile{
	GamePanel gp;

	public OBJ_Rock(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		// TODO Auto-generated constructor stub
		name = "Rock";
		speed = 8;
		maxLife = 30;
		life = maxLife;
		attack = 2;
		useCost = 1;
		alive = false;
		getImage();
	}
	public void getImage() {
		up1 = setup("/projectiles/rock_down_1");
		up2 = setup("/projectiles/rock_down_1");
		down1 = setup("/projectiles/rock_down_1");
		down2 = setup("/projectiles/rock_down_1");
		left1 = setup("/projectiles/rock_down_1");
		left2 = setup("/projectiles/rock_down_1");
		right1 = setup("/projectiles/rock_down_1");
		right2 = setup("/projectiles/rock_down_1");
	}
	public boolean haveResource(Entity user) {
		boolean haveResource = false;
		if(user.mana >= useCost) {
			haveResource = true;
		} else {
			
		}
		
		return haveResource;
	}
	public void subtractResource(Entity user) {
		user.mana -= useCost;
		if(user.mana < 0) {
			user.mana = 0;
		}
	}
	
	public Color getParticleColor() {
		Color color = new Color(40,50,50);
		return color;
	}
	
	public int getParticleSize() {
		int size = 10; //6px
		return size;
	}
	
	public int getParticleSpeed() {
		int speed = 2;
		return speed;
	}
	
	public int getParticleMaxLife() {
		int maxLife = 20;
		return maxLife;
	}
}
