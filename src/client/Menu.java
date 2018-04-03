package client;

import client.view.JTextFieldLimit;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Menu on the client side.
 */
public class Menu extends JPanel implements ActionListener
{
    /** Text field where to enter the ip of the server for the player. */
	private JTextField 	ipServerTextField;
    private JTextField 	pseudo;
    private JTextField 	port;
	private JButton 	submit;
	private JComboBox   combo;


    /**
     * Creates the menu on the client side for him to enter his id and his pseudo.
     */
	public Menu()
	{
		this.setLayout( new GridLayout(4,2) );

		this.add(new JLabel("Ip du serveur : "));
		this.ipServerTextField = new JTextField();
		this.add(this.ipServerTextField);

		this.add(new JLabel("Pseudo : "));
		this.pseudo = new JTextFieldLimit(10);
		this.add(this.pseudo);

        this.add(new JLabel("Port : "));
        this.port = new JTextField("2009");
        this.add(this.port);

		String[] choices = new String[] { "Joueur", "IA 1 (Aléatoire)", "IA 2 (Aléatoire pondéré)", "IA 3 (maxDiff)" };
        this.combo = new JComboBox(choices);
        this.add(this.combo);

		this.submit = new JButton("Valider");
		this.submit.addActionListener(this);
		this.add(this.submit);
	}

    /**
     * CHecks on validation if the ip is valid.
     * @param e Event's information.
     */
	public void actionPerformed (ActionEvent e)
	{
	    int portAsInt;
	    try
        {
            portAsInt = Integer.parseInt( this.port.getText() );
        }
        catch (Exception ex)
        {
            JOptionPane d = new JOptionPane();
            d.showMessageDialog( this, "Le port est invalide.", "IP invalide", JOptionPane.ERROR_MESSAGE);
            return;
        }

		if ( !this.ipServerTextField.getText().matches("([0-9]{1,3}.){3}[0-9]{1,3}") )
		{
			JOptionPane d = new JOptionPane();
			d.showMessageDialog( this, "L'adresse IP n'est pas valide", "IP invalide", JOptionPane.ERROR_MESSAGE);
		}
		else
        {
            Client.createIA(this.combo.getSelectedIndex());
            Network n = Client.createNetwork(this.ipServerTextField.getText(), portAsInt);
            n.sendMessage(this.pseudo.getText());
        }
	}
}
