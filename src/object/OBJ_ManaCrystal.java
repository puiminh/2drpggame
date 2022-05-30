package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity{

	GamePanel gp;
	public OBJ_ManaCrystal(GamePanel gp) {
		super(gp);
		this.gp =gp;
		type= type_pickUpOnly;
		value = 1;
		name = "Mana Crystal";
		down1 = setup("/objects/manacrystal_full");
		image = setup("/objects/manacrystal_full");
		image2 = setup("/objects/manacrystal_blank");
	}
	public void use(Entity entity) {
		
		gp.playSE(2);
		gp.ui.addMessage("Mana +" + value);
		gp.player.mana += value;
	}
}
