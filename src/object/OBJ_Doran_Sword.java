package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Doran_Sword extends Entity {
	public OBJ_Doran_Sword(GamePanel gp) {
		super(gp);
		
		type = type_sword;
		name = "Doran's Sword";
		down1 = setup("/objects/kiem_doran");
		attackValue = 1;
		description = hightLightName() + "Thanh kiem Doran, 450G";
		attackArea.width = 36;
		attackArea.height = 36;
	}
}
