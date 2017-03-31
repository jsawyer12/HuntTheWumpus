
public class Room {
	private int identity;
	private String proxMessage;
	private String moveMessage;
	private char appearance;
	
	public String getProxMessage() {
		switch(this.identity) {
		case 1:
			proxMessage = "You smell a Wumpus!";
			break;
		case 2:
			proxMessage = "You hear the fluttering of wings!";
			break;
		case 3:
			proxMessage = "You hear a breeze...";
			break;
		case 4:
			proxMessage = "You see the shiny glitterness of gold!";
			break;
		case 6:
			proxMessage = "An exit is near...";
			break;
		default:
			proxMessage = "";
			break;
		}
		return this.proxMessage;
	}
	
	public String getMoveMessage() {
		switch(this.identity) {
		case 1:
			moveMessage = "You got eaten by the Wumpus!";
			break;
		case 2:
			moveMessage = "The superbat dropped you in a different location";
			break;
		case 3:
			moveMessage = "You fell down a pit!";
			break;
		case 4:
			moveMessage = "You've found and taken the gold!";
			break;
		default:
			moveMessage = "";
			break;
		}
		return this.moveMessage;
	}
	
	public char getAppearance() {
		if (this.identity == 5) {
			appearance = '◬'; //player
		}
		else if (this.identity == 7) {
			appearance = '◻'; //empty explored unit
		}
		else {
			appearance = '◼'; //empty unexplored unit

		}
		/*
		switch(this.identity) {
		case 0: 
			appearance = '◼'; //empty unexplored unit
			break;
		case 1:
			appearance = '◼'; //wumpus ◎
			break;
		case 2:
			appearance = '◼'; //superbat ◡
			break;
		case 3:
			appearance = '◼'; //pit ◯
			break;
		case 4:
			appearance = '◼'; //treasure ◊
			break;
		case 5:
			appearance = '◬'; //player
			break;
		case 6:
			appearance = '◼'; //exit @
			break;
		default:
			appearance = '◻'; //empty explored unit
			break;
		}
		*/
		return this.appearance;
	}
	
	public int getIdentity() {
		return this.identity;
	}
	
	public void setIdentity(int theIdentity) {
		this.identity = theIdentity;
	}
	
	public Room(int theIdentity) {
		identity = theIdentity;
	}
}

