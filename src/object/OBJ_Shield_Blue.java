package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity{

	public OBJ_Shield_Blue(GamePanel gp) {
		super(gp);

		type = type_shield;
		name = "Blue Shield";
		down1 = setup("/objects/shield_blue");
		defenseValue = 2;
		description = hightLightName() + "A shiny blue shield";
	}

}
