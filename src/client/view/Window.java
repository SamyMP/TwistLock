package client.view;

import client.Client;
import client.Menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.SwingUtilities;

/**
 * Panel containing the whole game of Twistlock, including the {@link PlayerPanel} and {@link GameCanvas}.
 */
public class Window extends JFrame implements KeyListener, ActionListener
{
	/** Displays a recap of all the actions performed during the last players' turns */
	private JTextPane  log;
	/** Text field where the player enters the three characters corresponding to the action he wants to perform */
	private JTextField choice;

	private JButton submit;

	/**
	 * Creates the {@link GamePanel} to display in the current window.
	 */
	public Window()
	{
		super("client twistlock");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.createGamePanel();
		this.setMenuPanel();

		this.setSize(800, 600);
		this.setVisible(true);
	}

	public void setGamePanel()
	{
		setContentPane(createGamePanel());
		SwingUtilities.updateComponentTreeUI(this);
	}

	public void setMenuPanel()
	{
		setContentPane(new Menu());
		SwingUtilities.updateComponentTreeUI(this);
	}

	public JPanel createGamePanel()
	{
		JPanel gamePanel = new JPanel(new BorderLayout());

		log = new JTextPane();
		log.setEditable(false);
		JScrollPane scroll = new JScrollPane(log);

		gamePanel.add(scroll);

		JPanel south = new JPanel(new BorderLayout());
		choice = new JTextField();
		choice.addKeyListener(this);
		choice.setEditable(false);
		south.add(choice);

		submit = new JButton("Valider");
		submit.addActionListener(this);
		south.add(submit, "East");

		gamePanel.add(south, "South");
		return gamePanel;
	}

	/**
	 * Adds a message in the recap log of the game.
	 * @param msg Message to display.
	 */
	public void addLog (String msg)
	{
		addLog(msg, Color.black);
	}

	/**
	 * Adds a message with a certain color in the recap log of the game.
	 * @param msg Message to display.
	 * @param c Color of the message.
	 */
	public void addLog (String msg, Color c)
	{
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		int len = this.log.getDocument().getLength();
		this.log.setEditable(true);
		this.log.setCaretPosition(len);
		this.log.setCharacterAttributes(aSet, false);
		this.log.replaceSelection("\n" + msg);
		this.log.setCaretPosition(log.getDocument().getLength());
		this.log.setEditable(false);
	}

	/**
	 * Sends the action entered by the player.
	 */
	public void sendChoice ()
	{
		String tmp = choice.getText();
		tmp.replaceAll("[ \t\n]*", "");
		int row, corner;
		char col;

		try
		{
			row     = Integer.parseInt(tmp.charAt(0) + "");
			col     = (tmp.charAt(1));
			col     = Character.toUpperCase(col);
			corner  = Integer.parseInt(tmp.charAt(2) + "");
			Client.getNetwork().sendMessage(row + "" + col + "" + corner);
			this.canPlay(false);
		}
		catch (Exception ex)
		{
			JOptionPane d = new JOptionPane();
			d.showMessageDialog( this,"Saisie incorrecte.","Avertissement",JOptionPane.ERROR_MESSAGE);
		}
		choice.setText("");
	}

	public void canPlay (boolean canPlay)
	{
		choice.setEditable(canPlay);
		if (!canPlay)
		{
			choice.setText("");
		}
	}

	public void keyTyped (KeyEvent e) {}

	public void keyReleased (KeyEvent e) {}

	public void keyPressed (KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_ENTER){ sendChoice(); }
	}

	public void actionPerformed (ActionEvent e)
	{
		if ( e.getSource() == submit ) { sendChoice();}
	}
}
