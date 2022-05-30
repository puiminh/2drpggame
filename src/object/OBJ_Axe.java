package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity{

	public OBJ_Axe(GamePanel gp) {
		super(gp);
		
		type = type_axe;
		name = "Woddcutter's Axe";
		down1 = setup("/objects/axe");
		attackValue = 2;
		
		attackArea.width = 30;
		attackArea.height = 30;
		
		description = hightLightName() + "A bit rusty but still\n can cut some trees"; 
	}

}
