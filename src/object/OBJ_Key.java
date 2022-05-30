package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity{
	
	public OBJ_Key(GamePanel gp) {
		super(gp);
		
		//type = type_consumable;
		name = "Key";
		down1 = setup("/objects/key");
		description = hightLightName() + "Can open any door";
	}
}
