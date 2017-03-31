import java.util.*;
import javax.swing.*;

public class Board {
	public int dimensions; //stores the length/height of the board (board is square)
	public Room[][] boardStore; //stores rooms in a 2d array
	private int numObstacles; //stores the number of bats and pits to be randomly put into board
	public int[] adventurerLocation; //stores the adventurer's location for easy access
	public int[] wumpusLocation; //stores the wumpus' location for easy access
	
	public int[] setadventurerLocation(int x, int y) { 
		int[] adventurerLocation = {x, y};
		return this.adventurerLocation = adventurerLocation;
	}
	
	public int[] setWumpusLocation(int x, int y) {
		int[] wumpusLocation = {x, y};
		return this.wumpusLocation = wumpusLocation;
	}
	
	public Room[][] setDimensions() throws NumberFormatException {
		
		boolean repeat = true;
		while (repeat == true) {
			String dimensionsStr = JOptionPane.showInputDialog("Please enter the desired dimension of the board");
			if (dimensionsStr.matches("[0-9]+") == true) {
				int tempDimensions = Integer.parseInt(dimensionsStr);
				if (tempDimensions >= 5 && tempDimensions <= 50) { //allows dimensions to be between 5 and 50
					dimensions = tempDimensions;
					repeat = false;
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Incorrect Input");
			}
			boardStore = new Room[dimensions][dimensions];
		}		
		return boardStore;
	}
	
	public int getDimensions() {
		return this.dimensions;
	}
	
	public Room[][] setup(int dimensions) {
		Room[][] boardStore = new Room[dimensions][dimensions];
		return boardStore;
	}
	
	public int numberOfObstacles() { //represents number of pits and bats loaded into game
		numObstacles = 1;
		for (int k = 0; k < dimensions; k = k + 5) {
			numObstacles = numObstacles * 2;
		}
		return numObstacles;
	}
	
	public int rollDice() {//Use this method in actual backgammon.
		 Random rand = new Random(); 
		 int rollValue = rand.nextInt(dimensions);		
		 return rollValue;
	}
	
	public int[] randCoord() { //can be modified for super bat randomizer
		boolean validLocation = false;
		int[] xy = new int[2];
		while (validLocation == false) {
			int x = rollDice();
			int y = rollDice();
			if (boardStore[y][x].getIdentity() == 0) {
				xy[0] = x;
				xy[1] = y;
				validLocation = true;
			}
		}
		return xy;
	}
	
	public void addRoom(int identity) {
		Room newRoom = new Room(identity);
		boolean openSpace = false;
		while (openSpace == false) {
			int x = rollDice();
			int y = rollDice();
			if (boardStore[x][y].getIdentity() == 0) {
				if (identity == 5) {
					setadventurerLocation(x, y);
				}
				if (identity == 1) {
					setWumpusLocation(x, y);
				}
				boardStore[x][y] = newRoom;
				openSpace = true;
			}
		}		
	}
	
	public void initializeBoard() {
		for (int y = 0; y < boardStore.length; y++) {
			for (int x = 0; x < boardStore.length; x++) {
				Room emptyRoom = new Room(0);
				boardStore[x][y] = emptyRoom;
			}
		}
		for (int o = 0; o < numObstacles; o++) {
			addRoom(2);
			addRoom(3);
		}
		addRoom(1);
		addRoom(4);
		addRoom(5);
	}
	
	public void printBoard() {
		for (int y = boardStore.length-1; y >= 0; y--) {
			for (int x = 0; x < boardStore.length; x++) {
				System.out.print(boardStore[x][y].getAppearance() +" ");
				//System.out.print(x+","+y +" ");
			}
			System.out.println();
		}
	}
	
	public Board() {
		setDimensions();
		numberOfObstacles();
		initializeBoard();
	}
}
