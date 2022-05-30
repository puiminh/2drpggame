package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Fireball extends Projectile{
	
	GamePanel gp;

	public OBJ_Fireball(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		// TODO Auto-generated constructor stub
		name = "Fireball";
		speed = 5;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;
		alive = false;
		getImage();
	}
	public void getImage() {
		up1 = setup("/projectiles/fireball_up_1");
		up2 = setup("/projectiles/fireball_up_2");
		down1 = setup("/projectiles/fireball_down_1");
		down2 = setup("/projectiles/fireball_down_2");
		left1 = setup("/projectiles/fireball_left_1");
		left2 = setup("/projectiles/fireball_left_2");
		right1 = setup("/projectiles/fireball_right_1");
		right2 = setup("/projectiles/fireball_right_2");
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
		Color color = new Color(240,50,0);
		return color;
	}
	
	public int getParticleSize() {
		int size = 10; //6px
		return size;
	}
	
	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}
	
	public int getParticleMaxLife() {
		int maxLife = 40;
		return maxLife;
	}
}
