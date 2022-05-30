package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;
import tile_interactive.IT_DryTree;
import tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable{
	//SCREEN SETTING
	final int originalTileSize = 16; //16x16 tile 16px
	final int scale = 3;
	public final int tileSize = originalTileSize * scale; //48x48 tile
	public final int maxScreenCol = 16; 
	public final int maxScreenRow = 12;
	//make 16x12 box screen game
	public final int screenWidth = tileSize * maxScreenCol; //768 pixels
	public final int screenHeight = tileSize * maxScreenRow; //576 pixels
	
	//make 768x576 pixels screen
	
	// WORLD SETTING
	//Ban do thuc te
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int maxMap = 2;
	public int currentMap = 0;
	
	
	//FPS
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	
	//Tao ra 2 object de handle Sound and Music
	Sound music = new Sound();
	Sound se = new Sound();
	
	public KeyHandler keyH =  new KeyHandler(this);
	
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	Thread gameThread;
	
	//ENTITY AND OBJECT
	public Player player = new Player(this,keyH);
	//up to 10 object at the same time
	public Entity obj[][] = new Entity[maxMap][20];
	public Entity npc[][] = new Entity[maxMap][10];
	public Entity monster[][] = new Entity[maxMap][20];
	public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
	
	public ArrayList<Entity> projectileList = new ArrayList<>();
	public ArrayList<Entity> particleList = new ArrayList<>();
	//thu tu ve (list duoc render tren screen)
	public ArrayList<Entity> entityList =new ArrayList<>();
	
	// GAME STATE
	//gamestate de biet, dang menu hay lam gi?
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;
	
	
	public final int gameOverState = 6;
	public final int transitionState = 7;
	
	//Set player's default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); //set the size of this class (Jpanel)
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);  //all drawing from this component will be done in an offscreen painting buffer
		//in short, enabaling this can improve game's rendering performance.
		
		//gamepanel now can nhan ra keyinput
		this.addKeyListener(keyH);
		//with this, this GamePanel can be focused to receive key input
		this.setFocusable(true);
	}

	public void setupGame() {
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setInteractiveTile();
		playMusic(0);
		stopMusic();
		gameState = titleState;
	}
	
	public void startGameThread() {
		gameThread = new Thread(this); //passing game panel class to this constructor
		gameThread.start();  //auto call run method
			
	}
	
	public void retry() {
		player.resetStatus();
		aSetter.setNPC();
		aSetter.setMonster();
	}
	public void restart() {
		
		player.setDefaultValues();
		player.setItems();
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setInteractiveTile();
	}
	
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		//Trong khi gameThread van con
//		
//		double drawInterval = 1000000000/FPS;
//		//when the time come
//		double nextDrawTime =  System.nanoTime() + drawInterval;		
//		while (gameThread != null) {
//			
//			long currentTime = System.nanoTime();
//			
//			
//			System.out.println("X: " + playerX + " Y: " + playerY);
//			
//			//1 UPDATE: update information such as character position
//			update();
//			
//			//2 DRAW: draw the screen with the updated information
//			repaint();
//			
//			try {
//				//con bao nhieu thoi gian thua? -> ngu di, dung lam gi nua
//				double remainingTime = nextDrawTime -  System.nanoTime();
//				remainingTime =  remainingTime/1000000;
//				
//				if (remainingTime < 0) {
//					remainingTime = 0;
//				}
//				//Khong lam gi cho den khi thoi gian ngu ket thuc
//				Thread.sleep((long) remainingTime);
//				//thoi gian can thiet lai tang len
//				nextDrawTime += drawInterval;
//				
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}
//		
//		
//	}
	
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta  = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while (gameThread != null) {
			//check time hien tai
			currentTime = System.nanoTime();
			//sau moi loop, cong them 1 khoang time nho cho den khi cai delta nay = 1
			//co nghia la, tru phi toi thoi gian cho phep, khong thi update va repaint se khong chay
			delta += (currentTime - lastTime) / drawInterval;
			//bien de xem xet minh da ve bao nhieu lan
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			//khi delta nay >= 1 thi moi bat dau thuc hien update 
			if(delta >= 1) {
				update();
				repaint();
				//System.out.println("X: " + player.worldX + " Y: " + player.worldY);
				delta--;
				drawCount++;
			}
			
			if (timer >= 1000000000) {
				//luon ra 60
				//System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	public void update() {
		
		if(gameState == playState) {
			
			//PLAYER
			player.update();
			
			//NPC
			for(int i = 0; i<npc[currentMap].length; i++) {
				if(npc[currentMap][i] != null) {
					npc[currentMap][i].update();
				}
			}
			//MONSTER
			for(int i = 0; i<npc[currentMap].length; i++) {
				if(monster[currentMap][i] != null) {
					if(monster[currentMap][i].alive == true && monster[currentMap][i].dying == false) {
						monster[currentMap][i].update();
					}
					if(monster[currentMap][i].alive == false) {
						monster[currentMap][i].checkDrop();
						
						
						monster[currentMap][i] = null;
						 
					}
				}
			}
			//PROJECTITLE
			for(int i = 0; i<projectileList.size(); i++) {
				if(projectileList.get(i) != null) {
					if(projectileList.get(i).alive == true) {
						projectileList.get(i).update();
					}
					if(projectileList.get(i).alive == false) {
						projectileList.remove(i);
					}
				}
			}
			
			// UPDATE PARTICLE
			for(int i = 0; i<particleList.size(); i++) {
				if(particleList.get(i) != null) {
					if(particleList.get(i).alive == true) {
						particleList.get(i).update();
					}
					if(particleList.get(i).alive == false) {
						particleList.remove(i);
					}
				}
			}
		}
		
		
		
		for(int i = 0; i<iTile[currentMap].length; i++) {
			if(iTile[currentMap][i] != null) {
				iTile[currentMap][i].update();
			}
		}
		if(gameState == pauseState) {
			//STOP
		}
		
		
		
	}
	//for draw, g ~ pencil
	public void paintComponent(Graphics g) {
		
		//super co nghia la parent, trong truong hop nay la Jpanel
		super.paintComponent(g);
		//use g2 de hon
		Graphics2D g2 = (Graphics2D)g;
		
		//DEBUG
		long drawStart = 0;
		if(keyH.checkDrawTime == true) {
			drawStart = System.nanoTime();
		}
		
		// TITLE SCREEN
		if(gameState == titleState) {
			ui.draw(g2);
		}
		
		// OTHERS
		else {
			//TILE
			tileM.draw(g2);
		
			//INTERACTIVE TILE
			for(int i = 0; i <iTile[currentMap].length; i++) {
				if(iTile[currentMap][i] != null) {
					iTile[currentMap][i].draw(g2);
				}
			}
//			//OBJECT
//			for(int i = 0; i <obj.length; i++) {
//				if(obj[i] != null) {
//					obj[i].draw(g2, this);
//				}
//			}
//			// NPC
//			for(int i = 0; i < npc.length; i++) {
//				if(npc[i] != null) {
//					npc[i].draw(g2);
//				}
//			}
//			
//			//PLAYER
//			player.draw(g2);
			
			
			entityList.add(player);
			
			//ADD ENTITIES TO THE LIST
			for(int i = 0; i<npc[currentMap].length; i++) {
				if(npc[currentMap][i] != null) {
					entityList.add(npc[currentMap][i]);
				}
			}
			
			for(int i = 0; i < obj[currentMap].length; i++) {
				if(obj[currentMap][i] != null) {
					entityList.add(obj[currentMap][i]);
				}
			}
			
			for(int i = 0; i < monster[currentMap].length; i++) {
				if(monster[currentMap][i] != null) {
					entityList.add(monster[currentMap][i]);
				}
			}
			
			for(int i = 0; i < projectileList.size(); i++) {
				if(projectileList.get(i) != null) {
					entityList.add(projectileList.get(i));
				}
			}
			for(int i = 0; i < particleList.size(); i++) {
				if(particleList.get(i) != null) {
					entityList.add(particleList.get(i));
				}
			}
			
			
			
			
			//SORT
			Collections.sort(entityList, new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {
					// thap hon thi ve truoc
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}
				
			});
			
			// DRAW ENTITIES
			for(int i = 0; i< entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}
			
			//EMPTY ENTITY LIST
			entityList.clear();
			
			//UI
			ui.draw(g2);
		}
		
		
		
		// DEBUG
		if(keyH.checkDrawTime == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Draw Time: "+passed, 10, 400);
			System.out.println("Draw Time: "+passed);
		}
		
		
		
		//vut bo de save memory
		g2.dispose();
	}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	public void stopMusic() {
		music.stop();
	}
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
	
}
