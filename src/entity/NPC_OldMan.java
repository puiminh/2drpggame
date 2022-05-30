package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity {
	public NPC_OldMan(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		solidArea.x = 12;
		solidArea.y = 12;
		solidArea.width = 28;
		solidArea.height = 28;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
		setDialogue();
		
		
	}
	public void getImage() {
		up1 = setup("/npcs/oldman_up_1");
		up2 = setup("/npcs/oldman_up_2");
		down1 = setup("/npcs/oldman_down_1");
		down2 = setup("/npcs/oldman_down_2");
		left1 = setup("/npcs/oldman_left_1");
		left2 = setup("/npcs/oldman_left_2");
		right1 = setup("/npcs/oldman_right_1");
		right2 = setup("/npcs/oldman_right_2");
	}
	public void setDialogue() {
		dialogues[0] = "Hello, lad. dasdas ddsa das\n asdasdasd asdasd sada";
		dialogues[1] = "Hello1, lad.";
		dialogues[2] = "Hello2, lad.";
		dialogues[3] = "Hello3, lad.";
		dialogues[4] = "Hello4, lad.";
		dialogues[5] = "Hello5, lad.";
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
		
		
	}
	public void speak() {
		
		super.speak();
	}
}
