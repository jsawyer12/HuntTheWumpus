import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class Game extends JPanel implements KeyListener {  

	public KeyEvent event;
	public Scanner consoleReader = new Scanner(System.in);
	public String userChoice;
	
	public Board newBoard;
	public JPanel gameBoard;
	
	public int arrows;
	public boolean isEasy;
	public boolean hasTreasure;
	public boolean gameOver;
	public boolean wumpusIsAlive;
	
	public final void initializeGameboard() {
        gameBoard = new JPanel(new GridLayout(0, newBoard.dimensions));
        gameBoard.setBorder(new LineBorder(Color.DARK_GRAY));
                
        for (int y = newBoard.boardStore.length-1; y >= 0; y--) {
			for (int x = 0; x < newBoard.boardStore.length; x++) {
				gameBoard.add(new JLabel(Character.toString(newBoard.boardStore[x][y].getAppearance())), SwingConstants.CENTER);
			}
		}       
		gameBoard.add(new JLabel(""));
	}				
	
	public JPanel displayGameboard() {
		JPanel gameBoard = new JPanel(new GridLayout(0, newBoard.dimensions));
		for (int y = newBoard.boardStore.length-1; y >= 0; y--) {
			for (int x = 0; x < newBoard.boardStore.length; x++) {
				gameBoard.add(new JLabel(Character.toString(newBoard.boardStore[x][y].getAppearance())), SwingConstants.CENTER);
			}
		}  
		this.gameBoard = gameBoard;
		return gameBoard;
	}
   
	public void turn() {
		int currentX = newBoard.adventurerLocation[0];
		int currentY = newBoard.adventurerLocation[1];
		printBoardGui();
		
		if (userChoice.equals("w") || userChoice.equals("a") || userChoice.equals("s") || userChoice.equals("d")) { //to move
			newBoard.boardStore[currentX][currentY].setIdentity(7); // sets currentRoom to already explored
			if (userChoice.equals("w")) {
				currentY++;
			}
			else if (userChoice.equals("s")) {
				currentY--;
			}
			else if (userChoice.equals("a")) {
				currentX--;
			}
			else if (userChoice.equals("d")) {
				currentX++;
			}
			int[] xy = torusCheck(currentX, currentY);
			turnMaker(xy);		
		}
		if (userChoice.equals("i") || userChoice.equals("k") || userChoice.equals("j") || userChoice.equals("l")) { //to shoot
			if (arrows > 0) {
				if (userChoice.equals("i")) {
					currentY++;
				}
				else if (userChoice.equals("k")) {
					currentY--;
				}
				else if (userChoice.equals("j")) {
					currentX--;
				}
				else if (userChoice.equals("l")) {
					currentX++;
				}
				int[] xy = torusCheck(currentX, currentY);

				arrows--;
				if (newBoard.boardStore[currentX][currentY].getIdentity() == 1) {
					System.out.println("You've killed the Wumpus!");
					JOptionPane.showMessageDialog(null, "You've killed the Wumpus!");
					newBoard.boardStore[currentX][currentY].setIdentity(7);
					wumpusIsAlive = false;
					addExit(); //adds exit if all objectives have been completed
				}
				else {
					System.out.println("You missed! the Wumpus has moved!");
					JOptionPane.showMessageDialog(null, "You missed! the Wumpus has moved!");
					Random coord = new Random();
					int locToMoveWumpTo = coord.nextInt(4); 
					/*
					 * the following switch will move the wumpus to a random adjacent location
					 */
					switch (locToMoveWumpTo) {
					case 1:
						xy[0]++;
						break;
					case 2:
						xy[0]--;
						break;
					case 3:
						xy[1]++;
						break;
					case 4:
						xy[1]--;
						break;
					default:
						xy[0]--;
						break;
					}					
					newBoard.setWumpusLocation(xy[0], xy[1]);
					newBoard.boardStore[xy[0]][xy[1]].setIdentity(1);
				}
			}
			if (arrows == 0) {
				System.out.println("You're all out of arrows!");
				JOptionPane.showMessageDialog(null, "You're all out of arrows!");
			}
		}
	}
	
	public void isWumpusAlive() {
		wumpusIsAlive = false;
		for (int y = 0; y < newBoard.dimensions; y++) {
			for (int x = 0; x < newBoard.dimensions; x++) {
				if (newBoard.boardStore[x][y].getIdentity() == 1) {
					wumpusIsAlive = true;
				}
			}
		}
	}
	
	public void addExit() {
		if (isEasy) {
			if (hasTreasure) {
				newBoard.addRoom(6); //adds exit once treasure is found;
			}
		}
		else {
			isWumpusAlive();
			if (hasTreasure && !wumpusIsAlive) {
				newBoard.addRoom(6); //adds exit once treasure is found and wumpus is killed
			}
		}	
	}
	
	public void turnMaker(int[] xy) {
		int x = xy[0];
		int y = xy[1];
		if (!newBoard.boardStore[x][y].getMoveMessage().equals("")) {
			JOptionPane.showMessageDialog(null, newBoard.boardStore[x][y].getMoveMessage());
			System.out.println(newBoard.boardStore[x][y].getMoveMessage());
			System.out.println();
		}
		if (newBoard.boardStore[x][y].getIdentity() == 1 || newBoard.boardStore[x][y].getIdentity() == 3) {
			gameOver = true;
		}
		if (newBoard.boardStore[x][y].getIdentity() == 2) {
			xy = newBoard.randCoord();
		}
		if (newBoard.boardStore[x][y].getIdentity() == 4) {
			hasTreasure = true;
			addExit(); //adds exit if all objectives have been completed
		}
		if (newBoard.boardStore[x][y].getIdentity() == 6) {
			System.out.println("You've escaped the Cave!");
			JOptionPane.showMessageDialog(null, "You've escaped the Cave!");
			gameOver = true;
		}
		newBoard.setadventurerLocation(xy[0], xy[1]);
		newBoard.boardStore[xy[0]][xy[1]].setIdentity(5); //sets destinations to Adventurer
	}
	
	public int[] torusCheck(int newX, int newY) { //going from top to bottom or left to right of board
		if (newX == newBoard.dimensions) {
			newX = 0;
		}
		if (newY == newBoard.dimensions) {
			newY = 0;
		}
		if (newX == -1) {
			newX = newBoard.dimensions-1;
		}
		if (newY == -1) {
			newY = newBoard.dimensions-1;
		}
		int[] xy = {newX, newY};
		return xy;
	}
	
 	public JPanel displayMessages() {
 		JPanel messages = new JPanel();
 		int[] currentPos = newBoard.adventurerLocation;
 		int x = currentPos[0];
 		int y = currentPos[1];
 		int[] searchX = {x-1, x, x+1};
 		int[] searchY = {y-1, y, y+1};
 		System.out.println();
 		for (int j = 0; j < searchY.length; j++) {
 			for (int i = 0; i < searchX.length; i++) {
 				int[] xy = torusCheck(searchX[i], searchY[j]); // re
 				if(!newBoard.boardStore[xy[0]][xy[1]].getProxMessage().equals("")) {
 					System.out.println(newBoard.boardStore[xy[0]][xy[1]].getProxMessage());
 					JOptionPane.showMessageDialog(null, newBoard.boardStore[xy[0]][xy[1]].getProxMessage());
 					messages.add(new JLabel(newBoard.boardStore[xy[0]][xy[1]].getProxMessage()));
 				}
 			}
 		}
 		System.out.println();
 		return messages;
 	}
 	
 	public void game() {
		boolean gameOver = false;
		while (gameOver == false) {
			newBoard.printBoard();
			//displayGameboard();
			displayMessages(); //displays messages in console and gui
			//keyPressed(event);
			turn();
			gameOver = isGameOver();
		}
	}
	
	public void setDifficulty() {
		boolean repeat = true;
		while (repeat == true) {
			String dimensionsStr = JOptionPane.showInputDialog("Press 1 for easy or 2 for hard (Kill the wumpus & escape with treasure)");
			if (dimensionsStr.equals("1")) {
				isEasy = true;
				repeat = false;
			}
			else if (dimensionsStr.equals("2")) {
				isEasy = false;
				repeat = false;
			}
			else {
				JOptionPane.showMessageDialog(null, "Incorrect Input");
			}
		}
	}
	
	public boolean isGameOver() {
		if (gameOver == true) {
			System.out.println("GAME OVER");
			JOptionPane.showMessageDialog(null, "GAME OVER");
			System.exit(0);
		}
		return gameOver;
	}
	
	public Board returnNewBoard() {
		newBoard = new Board();
		return this.newBoard;
	}
	
	public void printBoardGui() {
		StringBuilder JPaneBuilder = new StringBuilder();
		for (int y = newBoard.boardStore.length-1; y >= 0; y--) {
			for (int x = 0; x < newBoard.boardStore.length; x++) {
				System.out.print(newBoard.boardStore[x][y].getAppearance() +" ");
				JPaneBuilder.append(newBoard.boardStore[x][y].getAppearance() +" ");
				//System.out.print(x+","+y +" ");
			}
			System.out.println();
			JPaneBuilder.append("\n");
		}
		this.userChoice = JOptionPane.showInputDialog(JPaneBuilder.toString() +"\n Enter your move");
	}
	
	
	public Game() {
		returnNewBoard(); 
		//initializeGameboard();
		setDifficulty();
		//gameBoard.addKeyListener(this);
		//gameBoard.setFocusable(true);
		//gameBoard.requestFocusInWindow();
		hasTreasure = false;
		arrows = 5;
		wumpusIsAlive = true;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		int currentX = newBoard.adventurerLocation[0];
		int currentY = newBoard.adventurerLocation[1];
		newBoard.boardStore[currentX][currentY].setIdentity(6); // sets currentRoom to already explored
		if (event.getKeyCode() == KeyEvent.VK_UP) {
			currentY++;
		}
		else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
			currentY--;
		}
		else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
			currentX--;
		}
		else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
			currentX++;
		}
		int[] xy = torusCheck(currentX, currentY);
		newBoard.setadventurerLocation(xy[0], xy[1]);
		newBoard.boardStore[xy[0]][xy[1]].setIdentity(5); //sets destinations to Adventurer		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
}

