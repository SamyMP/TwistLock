package twistlock.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import java.net.DatagramPacket;

import twistlock.Controller;
import twistlock.util.InetAddressWithPort;

/**
 * Panel containing the main menu of the Twistlock game.
 * @author Javane
 * @version 2018-03-26
 */
class MenuPanel extends JPanel implements ActionListener,ChangeListener
{
	// c'est le panel qu'on a au demarrage avec un formulaire pour le nombre de playerPanel et tout le bordel a demander pour creer le jeu
	// 4 Textfield noms playerPanel
	private PlayerOptionsPanel playerPanel;
	// Checkbox this.rndButton ou fixe
	// deux TextField pour dimensionPanel ( 4 à 9 )
	private JPanel      dimensionPanel;
	/** Text field to put number of rows. */
    private JSpinner    rowSpinner;
    /** Text field to put number of columns. */
    private JSpinner    colSpinner;
    /** Button to click to this.rndButtonize the number of columns and rows in their respective fields. */
    private JButton     rndButton;
    /** Panel containing the button to quit and the one to confirm the options before launching the game. */
	private JPanel      bottomPanel;
	/** Confirm button to launch the game with the selected options. */
    private JButton     confirmButton;
    /** Quit button to completely close the application. */
    private JButton     quitButton;


    /**
     * Creates a menu panel to launch a game with custom options chosen by the players.
     */
    MenuPanel()
    {
        setLayout( new BoxLayout(this, BoxLayout.Y_AXIS));

        this.playerPanel    = new PlayerOptionsPanel();
        this.playerPanel.setAlignmentX(BoxLayout.Y_AXIS);
        this.add(this.playerPanel);

        this.add(Box.createRigidArea(new Dimension(0,5)));

        this.dimensionPanel = new JPanel( new GridLayout(1,3,5,5));
        this.rowSpinner     = new JSpinner();
        this.rowSpinner.setValue(7);
        this.rowSpinner.addChangeListener(this);
        this.dimensionPanel.add(this.rowSpinner);
        this.colSpinner     = new JSpinner();
        this.dimensionPanel.add(this.colSpinner);
        this.colSpinner.setValue(7);
        this.colSpinner.addChangeListener(this);
        this.rndButton      = new JButton("Aléatoire");
        this.rndButton.addActionListener(this);
        this.dimensionPanel.add(this.rndButton);
        this.dimensionPanel.setAlignmentX(BoxLayout.Y_AXIS);
        add(this.dimensionPanel);

		add(Box.createRigidArea(new Dimension(0,5)));

        this.bottomPanel   = new JPanel( new GridLayout(1,2,5,5));
        this.confirmButton  = new JButton("Confirmer");
        this.confirmButton.addActionListener(this);
        this.bottomPanel.add(this.confirmButton);
        this.quitButton     = new JButton("Quitter");
        this.quitButton.addActionListener(this);
        this.bottomPanel.add(this.quitButton);
        this.bottomPanel.setAlignmentX(BoxLayout.Y_AXIS);
        this.add(this.bottomPanel);

        this.setVisible(true);

        setVisible(true);
		
		MenuPanel that = this;
		
		new Thread()
		{
			public void run()
			{
				that.waitingPlayer();
				that.waitingPlayer();
			}
		}.start();
    }
	
	public void waitingPlayer ()
	{
		try
		{
			DatagramPacket dp = Controller.getController().newConnection();
			this.playerPanel.addPlayer( new String( dp.getData() , "UTF-8"),
										 new InetAddressWithPort(dp.getAddress(), dp.getPort()) );
		} catch (Exception e) {}
	}

    /**
     * Performs the actions attached to the buttons of the menu : generateing random numbers for the "Random" button,
     * and creating a game for the "Confirm" one.
     * @param e Event's information.
     */
    public void actionPerformed (ActionEvent e)
    {
        if ( e.getSource() == this.rndButton )
        {
            int delta = 6;
            int min = 4;
            int lig = (int)(Math.random()*delta) + min;
            int col = (int)(Math.random()*delta) + min;
            rowSpinner.setValue(lig);
            colSpinner.setValue(col);
        }
        if ( e.getSource() == this.confirmButton )
        {
			if (this.playerPanel.isReady())
			{
				int lig = (int) this.rowSpinner.getValue();
				int col = (int) this.colSpinner.getValue();
				Map<String, InetAddressWithPort> players = this.playerPanel.getPlayers();
				this.confirmButton.setEnabled(false);
				Controller.getController().createGame(col,lig,players);
			}
			else
			{
				JOptionPane d = new JOptionPane();
				d.showMessageDialog( this,"Il manque de(s) joueur(s) !", "Error", JOptionPane.ERROR_MESSAGE);
			}
        }
        if ( e.getSource() == this.quitButton )
        {
            System.exit(0);
        }
    }

    /**
     * Listens for a click on the arrow of the spinners, to clamp their values.
     * @param e Event's information.
     */
    public void stateChanged (ChangeEvent e)
    {
        JSpinner j = (JSpinner) ( e.getSource());
        int value = (int) (j.getValue());
        if ( value < 4 )
            j.setValue(4);
        else
            if ( value > 9 )
                j.setValue(9);
    }
}


/**
 * Panel containing the players on the main menu
 * @author Javane
 * @version 2018-03-26
 */
class PlayerOptionsPanel extends JPanel implements ActionListener
{
    private InetAddressWithPort[]   ips;
    private ArrayList<String>       players;
    private ArrayList<JTextField>   fields;
    private JPanel                  playerPanel;
    private JButton                 add;
    private JButton                 remove;


    PlayerOptionsPanel ()
    {
        super();
		this.ips = new InetAddressWithPort[2];
		this.fields = new ArrayList<JTextField>();
        this.players = new ArrayList<String>();
        this.players.add("");
        this.players.add("");
        setLayout( new BorderLayout());
		
        String[] couleurs = {"Rouge","Vert"};


        this.playerPanel = new JPanel( new GridLayout(2,2,5,5));
        for ( int i = 0 ; i < 2 ; i++ )
        {
            JLabel lab = new JLabel("Joueur " + couleurs[i] + " :");
            JTextField text = new JTextField();
            text.setFocusable(false);
            this.playerPanel.add(lab);
            this.playerPanel.add(text);
            this.fields.add(text);
        }
        add(playerPanel);


        setVisible(true);
    }

    void maj ()
    {
        if ( this.players.size() > 2 )
            remove.setVisible(true);
        else
            remove.setVisible(false);
        if ( this.players.size() < 4 )
            add.setVisible(true);
        else
            add.setVisible(false);
    }

    private void addPlayer ()
    {
        this.players.add("");
        int toAdd = this.players.size();
        this.playerPanel.getComponent((toAdd*2)-1).setVisible(true);
        this.playerPanel.getComponent((toAdd*2)-2).setVisible(true);
    }

    private void removePlayer ()
    {
        int toRm = this.players.size();
        this.players.remove(toRm-1);
        this.playerPanel.getComponent((toRm*2)-1).setVisible(false);
        this.playerPanel.getComponent((toRm*2)-2).setVisible(false);
    }

    public Map<String, InetAddressWithPort> getPlayers ()
    {
        Map<String, InetAddressWithPort> ret = new HashMap<String, InetAddressWithPort>();
        for (int i = 0; i < this.players.size(); i++)
        {
            ret.put(this.fields.get(i).getText(), this.ips[i]);
        }
        return ret;
    }
	
	protected void addPlayer(String name, InetAddressWithPort ip)
	{
		if ( !this.fields.get(this.fields.size()-1).getText().equals("") )
		{
			Controller.getController().sendMessage("91",ip);
			return;
		}
		
		int i = 0;
		for (;!this.fields.get(i).getText().equals("");i++);
		
		Controller.getController().sendMessage("" + (i + 1),ip, name);
		
		this.fields.get(i).setText(name);
		ips[i] = ip;
	}
	
	protected boolean isReady ()
	{
		return !this.fields.get( this.fields.size() - 1 ).getText().equals("");
	}

    public Map<String, InetAddressWithPort> getJoueurs ()
    {
        Map<String, InetAddressWithPort> retour = new HashMap<String, InetAddressWithPort>();
        for ( int i = 0 ; i < this.players.size() ; i++ )
		{
			retour.put(this.fields.get(i).getText(), ips[i]);
		}
        return retour;
    }

    public void actionPerformed (ActionEvent e)
    {
        if ( e.getSource() == add )     addPlayer();
        if ( e.getSource() == remove )  removePlayer();
        maj();
    }
}
