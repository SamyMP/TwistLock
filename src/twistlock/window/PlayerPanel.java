package twistlock.window;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import twistlock.Controller;
import twistlock.job.Player;
import java.awt.geom.Path2D;


/**
 * Panel displaying the information about the player.
 */
class PlayerPanel extends JPanel
{
    public enum Alignment
    {
        LEFT,
        RIGHT
    }

    /** Player linked to the panel */
	private Player          player;
	/** Alignment of the player panel */
	private Alignment       alignment;


	/**
	 * Creates a panel for the player with the specified index.
	 * @param player Index of the player.
	 */
	PlayerPanel (Player player, Alignment alignment)
	{
		super();

        this.player     = player;
        this.alignment  = alignment;

        //this.setBorder( BorderFactory.createLineBorder(Color.red) );
		this.repaint();
	}

	/**
	 * Redraws the panel.
	 * @param g Player panel's {@link Graphics}.
	 */
	@Override
	protected void paintComponent (Graphics g)
	{
		super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);

        // Looks for the size factor according to the window's size
        Dimension dim = Controller.getController().getWindow().getSize();
        int sizeFactor = (int)(0.1f*dim.width);


        // Gets the current dimensions of the frame, and saves the lower one.
        JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
        int lowerValue = window.getHeight();
        if (getWidth() < lowerValue)
            lowerValue = window.getWidth();
        float windowFactor = lowerValue/1080f;


        // Updates the parent size
        Component parent = this.getParent();
        parent.setPreferredSize(new Dimension( sizeFactor, parent.getSize().height ));

        //The number of rows the twistlocks' display is going to take
        int     nbRow   = this.player.getTwistLock()/10;
        // Size factor of the current player panel
        float   factor  = this.getWidth()*0.1f;

        // Draws the little picture above the player's values
        this.draw(g2d);
		//g2d.drawImage( this.image.getScaledInstance(parent.getSize().width, this.getSize().height, Image.SCALE_SMOOTH), null, null );


        // Displays the pseudo of the player
        g2d.setFont(g2d.getFont().deriveFont( 10f*(windowFactor) ));
        g2d.setColor(Color.black);

        int width = g2d.getFontMetrics().stringWidth(this.player.getPseudo());
		g2d.drawString( this.player.getPseudo(),
                        (this.alignment == Alignment.LEFT) ? 0 : this.getWidth() - width,
                        (int)(this.getHeight() - factor*(nbRow+3)) );


		// Draws the dots corresponding to the twistlocks of the player
		int     row             = 0;
		int     col             = 0;
		float   reducingFactor  = 0.02f*this.getSize().width;
		for (int i = 0; i < this.player.getTwistLock(); i++)
		{
			if (i%10 == 0 && i != 0)
			{
				row++;
				col = 0;
			}
			g2d.fillOval(   (int)(factor*col + reducingFactor/2f),
                            (int)(this.getHeight() - factor*(nbRow+2) + (int)(factor*row)),
                            (int)(factor - reducingFactor),
                            (int)(factor - reducingFactor) );
			col++;
		}

        // Displays the score of the player
        width = g2d.getFontMetrics().stringWidth("" + this.player.getScore());
        g2d.drawString( "" + this.player.getScore(),
                        (this.alignment == Alignment.LEFT) ? 0 : this.getWidth() - width,
                        (int)(this.getHeight() - factor) );
    }

	/**
	 * Draws the image attached to the player's panel.
	 */
	void draw (Graphics2D g2d)
	{
        // Looks for the size factor according to the window's size
        Dimension dim = Controller.getController().getWindow().getSize();
        int lowerValue = dim.height;
        if (getWidth() < lowerValue)
            lowerValue = dim.width;

        float sizeFactor = lowerValue / 1080f;

        // Draws the shape
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Window.playerColors[player.getId()]);
        /*g2d.drawLine((int)(100*sizeFactor²), (int)(50*sizeFactor),  (int)(400*sizeFactor), (int)(50*sizeFactor));
        g2d.drawLine((int)(100*sizeFactor), (int)(50*sizeFactor),  (int)(100*sizeFactor), (int)(750*sizeFactor));
        g2d.drawLine((int)(400*sizeFactor), (int)(50*sizeFactor),  (int)(400*sizeFactor), (int)(750*sizeFactor));
        g2d.drawLine((int)(100*sizeFactor), (int)(350*sizeFactor), (int)(400*sizeFactor), (int)(350*sizeFactor));
        g2d.drawLine((int)(100*sizeFactor), (int)(50*sizeFactor),  (int)(400*sizeFactor), (int)(350*sizeFactor));
        g2d.drawLine((int)(100*sizeFactor), (int)(350*sizeFactor), (int)(400*sizeFactor), (int)(50*sizeFactor));
        g2d.drawLine((int)(100*sizeFactor), (int)(350*sizeFactor), (int)(400*sizeFactor), (int)(750*sizeFactor));
        g2d.drawLine((int)(100*sizeFactor), (int)(750*sizeFactor), (int)(400*sizeFactor), (int)(350*sizeFactor));

		Path2D.Double shape = new Path2D.Double();
		shape.moveTo(100, 750);
		shape.lineTo(400, 750);
		shape.lineTo(500, 850);
		shape.lineTo(0, 850);
        g2d.fill(shape);

        g2d.fillRect(0, 850, 500, 250);*/

		this.repaint();
	}
}
