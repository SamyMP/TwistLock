package twistlock.window;

import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 * TODO
 */
class GamePanel extends JPanel
{

	// on y met 4 PlayerPanel (1/joueur)
	// c'est lui qui gere l'affichage du jeu
	GamePanel()
	{
		setLayout(new BorderLayout());

		add(new GameCanvas());
	}
}
