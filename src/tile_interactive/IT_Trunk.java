package tile_interactive;

import java.awt.Rectangle;

import entity.Entity;
import main.GamePanel;

public class IT_Trunk extends InteractiveTile{

	GamePanel gp;
	public IT_Trunk(GamePanel gp, int col, int row) {
		super(gp, col, row);
		
			this.gp =gp;
			
			this.worldX = gp.tileSize*col;
			this.worldY = gp.tileSize*row;
			
			down1 = setup("/tile_interactive/trunk");
			destructible = true;
			
			solidArea = new Rectangle(0,0,0,0);
		

		
	}

}
