package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import tile_interactive.IT_DryTree;

public class AssetSetter {
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
		int mapNum = 0;
		
		gp.obj[mapNum][0] = new OBJ_Coin_Bronze(gp);
		//cung cap toa do cho object
		gp.obj[mapNum][0].worldX = 23*gp.tileSize;
		gp.obj[mapNum][0].worldY = 7*gp.tileSize;
		
		gp.obj[mapNum][1] = new OBJ_Heart(gp);
		gp.obj[mapNum][1].worldX = 23*gp.tileSize;
		gp.obj[mapNum][1].worldY = 40*gp.tileSize;
		
		gp.obj[mapNum][4] = new OBJ_ManaCrystal(gp);
		gp.obj[mapNum][4].worldX = 38*gp.tileSize;
		gp.obj[mapNum][4].worldY = 8*gp.tileSize;
		
		gp.obj[mapNum][2] = new OBJ_Door(gp);
		gp.obj[mapNum][2].worldX = 8*gp.tileSize;
		gp.obj[mapNum][2].worldY = 28*gp.tileSize;
		
		gp.obj[mapNum][3] = new OBJ_Door(gp);
		gp.obj[mapNum][3].worldX = 10*gp.tileSize;
		gp.obj[mapNum][3].worldY = 11*gp.tileSize;
		
		gp.obj[mapNum][5] = new OBJ_Door(gp);
		gp.obj[mapNum][5].worldX = 12*gp.tileSize;
		gp.obj[mapNum][5].worldY = 22*gp.tileSize;
		
		gp.obj[mapNum][6] = new OBJ_Chest(gp);
		gp.obj[mapNum][6].worldX = 10*gp.tileSize;
		gp.obj[mapNum][6].worldY = 7*gp.tileSize;
		
		gp.obj[mapNum][7] = new OBJ_Boots(gp);
		gp.obj[mapNum][7].worldX = 37*gp.tileSize;
		gp.obj[mapNum][7].worldY = 42*gp.tileSize;
		
		gp.obj[mapNum][8] = new OBJ_Axe(gp);
		gp.obj[mapNum][8].worldX = 37*gp.tileSize;
		gp.obj[mapNum][8].worldY = 41*gp.tileSize;
		
		gp.obj[mapNum][9] = new OBJ_Shield_Blue(gp);
		gp.obj[mapNum][9].worldX = 37*gp.tileSize;
		gp.obj[mapNum][9].worldY = 35*gp.tileSize;
		
		gp.obj[mapNum][9] = new OBJ_Potion_Red(gp);
		gp.obj[mapNum][9].worldX = 23*gp.tileSize;
		gp.obj[mapNum][9].worldY = 20*gp.tileSize;
		
		
		
	}
	public void setNPC() {
		
		int mapNum = 0;
		
		gp.npc[mapNum][0] = new NPC_OldMan(gp);
		gp.npc[mapNum][0].worldX = 21 * gp.tileSize;
		gp.npc[mapNum][0].worldY = 21 * gp.tileSize;
		
		gp.npc[mapNum][1] = new NPC_OldMan(gp);
		gp.npc[mapNum][1].worldX = 23 * gp.tileSize;
		gp.npc[mapNum][1].worldY = 38 * gp.tileSize;

	}
	public void setMonster() {
		
		int mapNum = 0;
		
		int i = 0;
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*23;
		gp.monster[mapNum][i].worldY = gp.tileSize*36;
		
		i++;
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*23;
		gp.monster[mapNum][i].worldY = gp.tileSize*37;
		
		i++;
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*22;
		gp.monster[mapNum][i].worldY = gp.tileSize*37;
		
		i++;
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*21;
		gp.monster[mapNum][i].worldY = gp.tileSize*37;
		
		i++;
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*24;
		gp.monster[mapNum][i].worldY = gp.tileSize*37;
		
		
	}
	
	public void setInteractiveTile() {
		
		int mapNum = 0;
		int i = 0;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp,24,12);
		i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp,25,12);
		i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp,26,12);
		i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp,27,12);
		i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp,28,12);
		i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp,29,12);
		i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp,30,12);
		i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp,31,12);
		i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp,32,12);
		i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp,33,12);
		i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp,34,12);
		i++;
		
		
	}
}
