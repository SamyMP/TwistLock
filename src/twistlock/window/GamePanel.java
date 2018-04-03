package twistlock.window;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import twistlock.Controller;
import twistlock.window.PlayerPanel;
import twistlock.job.Player;

/**
 * Panel containing the whole game of Twistlock, including the {@link PlayerPanel} and {@link GameCanvas}.
 * @author Javane
 * @version 2018-03-26
 */
class GamePanel extends JPanel
{
	/** Displays a recap of all the actions performed during the last players' turns */
	private JTextPane           log;
	/** Text field where the player enters the three characters corresponding to the action he wants to perform */
	private JTextField          choice;
	/** Button used to submit what is written in {@link #choice}. */
	private JButton             submit;


	/**
	* Creates the {@link GamePanel} to display in the current window.
	*/
	GamePanel()
	{
		this.setLayout(new BorderLayout());

		this.add(new GameCanvas());

		Player[] tabPlayers = Controller.getController().getPlayers();


        Dimension dim = Controller.getController().getWindow().getSize();

        JPanel westPan = new JPanel(true);
        westPan.setPreferredSize(new Dimension( (int)(0.1f*dim.width), dim.height ));
        westPan.setLayout(new GridLayout(2, 1));

		for (int i = 0; i < tabPlayers.length; i+=2)
		{
			PlayerPanel playerPan = new PlayerPanel(tabPlayers[i], PlayerPanel.Alignment.LEFT);
			westPan.add(playerPan);
		}
		this.add(westPan, "West");

		JPanel eastPan = new JPanel(true);
        eastPan.setPreferredSize(new Dimension( (int)(0.1f*dim.width), dim.height ));
        eastPan.setLayout(new GridLayout(2, 1));

		for (int i = 1; i < tabPlayers.length; i+=2)
		{
			PlayerPanel playerPan = new PlayerPanel(tabPlayers[i], PlayerPanel.Alignment.RIGHT);
			eastPan.add(playerPan);
		}
		this.add(eastPan, "East");
	}
}
