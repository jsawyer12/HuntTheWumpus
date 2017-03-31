import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
	
	public static void main(String[] args) {
		//Runnable r = new Runnable() {
			//@Override
			//public void run() {
				Game game = new Game();
				//JFrame frame = new JFrame("Hunt the Wumpus");
				//frame.setSize(700, 700);
				//frame.add(game.displayGameboard());
				//frame.add(game.displayMessages());
				//frame.setVisible(true);
				//frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				while (game.gameOver == false) {
					game.game();
					//frame.remove(game.gameBoard);
					//frame.remove(game.displayMessages());
					//frame.add(game.gameBoard);
					//frame.add(game.displayMessages());
				}
			//}
		//};
		//SwingUtilities.invokeLater(r);
	}
}
