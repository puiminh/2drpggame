package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity{

	int value = 5;

	GamePanel gp;
	public OBJ_Potion_Red(GamePanel gp) {
		super(gp);

		this.gp = gp;
		type = type_consumable;
		name = "Red Potion";
		down1 = setup("/objects/hp");
		attackValue = 1;
		description = hightLightName() + "Heals your life by "+ value + ".";
	}
	
	public void use(Entity entity) {
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "You drink the " + name + "!\n" + "Your life has been recovered by "+ value + ".";
		
		if(entity.life + value <= entity.maxLife) {
			entity.life += value;
		} else {
			entity.life = entity.maxLife;
		}
		gp.playSE(2);
	}

}
