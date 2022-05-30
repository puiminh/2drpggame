package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //have exit button work
		window.setResizable(false); //cant resize
		window.setTitle("2D Adventure"); //title
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack(); //Causes this Window to be sized to fit the perferred size and layouts of its subcomponennts (=GamePanel)
		
		
		
		window.setLocationRelativeTo(null); //center of screen
		window.setVisible(true); //can see
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
	}

}
