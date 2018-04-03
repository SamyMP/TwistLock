package twistlock.window;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Collections;

import javax.swing.*;

import twistlock.Controller;
import twistlock.job.Container;


/**
 * Main container on which the whole Twistlock game display is drawn.
 * @author Javane
 * @version 2018-03-27
 */
class GameCanvas extends JPanel
{
	/** Margin set on both sides of the canvas */
	private static final int MARGIN_SIDE 					= 100;//px
	/** Margin set on the top of the canvas */
	private static final int MARGIN_TOP 					= 200;//px
    /** Radius of the curved arc on the corner */
	private static final int BORDER_RADIUS 					= 15;//px
    /** Half the size of the total space between the containers */
	private static final int HALF_PADDING 	                = 2;//px

	/**
	 * Creates a canvas used to draw the containers on.
	 */
	GameCanvas ()
	{
		super();
		this.repaint();
	}

	/**
	 * Redraws the canvas.
	 * @param g Main panel's {@link Graphics}.
	 */
	@Override
	protected void paintComponent (Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		Container[][] containers = Controller.getController().getContainers();

		// Looks for the size factor according to the window's size
		Dimension dim = Controller.getController().getWindow().getSize();
		int lowerValue = dim.height;
		if (getWidth() < lowerValue)
			lowerValue = dim.width;

		float sizeFactor = lowerValue / 1080f;


        // Actual values according to the size of the window
        int actualRadius        = (int) (GameCanvas.BORDER_RADIUS * sizeFactor);
        int actualMarginSide    = (int) (GameCanvas.MARGIN_SIDE * sizeFactor);
        int actualHalfPadding   = (int) (GameCanvas.HALF_PADDING * sizeFactor);

		int width 	= (getWidth() - 2 * actualMarginSide) / containers.length;
		int height 	= (int) ((getHeight() - MARGIN_TOP - 30*sizeFactor) / containers[0].length);

        // Sets the font for all the container's value
        float   fontSize    = 1.2f * actualRadius;
        Font    font        = g2d.getFont().deriveFont(fontSize);
        font = font.deriveFont( Collections.singletonMap(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD) );


        // Display of the upper line containing the letters corresponding to the containers below
		for (int j = 0; j < containers.length; j++)
        {
            g2d.setFont( font.deriveFont(1.5f * actualRadius) );
            g2d.drawString( String.format("%c", j + 'A'),
                            (int)( actualMarginSide - actualRadius*0.5 + width/2f + j*width ),
                            (int)( MARGIN_TOP - (30*sizeFactor) ) );
        }

        // Display of the left line containing the numbers corresponding to the containers below
        for (int i = 0; i < containers[0].length; i++)
        {
            g2d.setFont( font.deriveFont(1.5f * actualRadius) );
            g2d.drawString( String.format("%02d", (i+1)),
                    (int)( actualMarginSide - (30*sizeFactor) - actualRadius*0.5 ),
                    (int)( MARGIN_TOP + actualRadius*0.5 + height/2f + i*height ) );
        }

        // Displays the containers and their contents
		for (int i = 0; i < containers.length; i++)
		{
			for (int j = 0; j < containers[i].length; j++)
			{
				/*------------------------------*/
				/*     CONTAINERS' DRAWING      */
				/*------------------------------*/

				// Draws the lines forming the rectangle
				g2d.drawLine(
					actualMarginSide + actualRadius + i * width + actualHalfPadding,
					MARGIN_TOP + j * height + actualHalfPadding,
					actualMarginSide - actualRadius + (i + 1) * width - actualHalfPadding,
					MARGIN_TOP + j * height + actualHalfPadding
				);
				g2d.drawLine(
					actualMarginSide + i * width + actualHalfPadding,
					MARGIN_TOP + actualRadius + j * height + actualHalfPadding,
					actualMarginSide + i * width + actualHalfPadding,
					MARGIN_TOP - actualRadius + (j + 1) * height - actualHalfPadding
				);
				g2d.drawLine(
					actualMarginSide + actualRadius + i * width + actualHalfPadding,
					MARGIN_TOP + (j + 1) * height - actualHalfPadding,
					actualMarginSide - actualRadius + (i + 1) * width - actualHalfPadding,
					MARGIN_TOP + (j + 1) * height - actualHalfPadding
				);
				g2d.drawLine(
					actualMarginSide + (i + 1) * width - actualHalfPadding,
					MARGIN_TOP + actualRadius + j * height + actualHalfPadding,
					actualMarginSide + (i + 1) * width - actualHalfPadding,
					MARGIN_TOP - actualRadius + (j + 1) * height - actualHalfPadding
				);


				/*------------------------------*/
				/*   ROUNDED CORNERS' DRAWING   */
				/*------------------------------*/

				// Draws the arcs in the corners of the rectangle
				g2d.drawArc(
					actualMarginSide - actualRadius + i * width + actualHalfPadding,
					MARGIN_TOP - actualRadius + j * height + actualHalfPadding,
					2 * actualRadius,	2 * actualRadius, 270, 90
				);
				g2d.drawArc(
					actualMarginSide - actualRadius + (i + 1) * width - actualHalfPadding,
					MARGIN_TOP - actualRadius + j * height + actualHalfPadding,
					2 * actualRadius,	2 * actualRadius, 180, 90
				);
				g2d.drawArc(
					actualMarginSide - actualRadius + (i + 1) * width - actualHalfPadding,
					MARGIN_TOP - actualRadius + (j + 1) * height - actualHalfPadding,
					2 * actualRadius,	2 * actualRadius, 90, 90
				);
				g2d.drawArc(
					actualMarginSide - actualRadius + i * width + actualHalfPadding,
					MARGIN_TOP - actualRadius + (j + 1) * height - actualHalfPadding,
					2 * actualRadius,	2 * actualRadius, 0, 90
				);


				/*------------------------------*/
				/*        VALUES' DRAWING       */
				/*------------------------------*/

				Container cont = containers[i][j];

				// Checks if the rectangle container is possessed : if it is, draws a circle in the middle of it
				if (cont.getOwner() >= 0)
				{
					g2d.setColor(Window.playerColors[cont.getOwner()]);
					g2d.fillOval(
						(int)((i + 0.5) * width) + actualMarginSide - actualRadius,
						(int)((j + 0.5) * height) + MARGIN_TOP - actualRadius,
						2 * actualRadius, 2 * actualRadius
					);

					g2d.setColor(Color.black);
				}

				// Changes the font of the value's text
				g2d.setFont( font );
				g2d.drawString( String.format("%02d", cont.getValue()),
                                (int)( (i + 0.5) * width + actualMarginSide - (int) (0.65 * actualRadius) ),
                                (int)( (j + 0.5) * height + MARGIN_TOP + (int)(0.55 * actualRadius) ) );


				/*------------------------------*/
				/*        LOCKS' DRAWING        */
				/*------------------------------*/

				// Draws the lock on the northwest if it is possessed
				if (cont.getLock(1).getOwner() >= 0)
				{
					g2d.setColor(Window.playerColors[cont.getLock(1).getOwner()]);
					g2d.fillOval(
						(i * width) + actualMarginSide - actualRadius,
						(j * height) + MARGIN_TOP - actualRadius,
						2 * actualRadius, 2 * actualRadius
					);
					g2d.setColor(Color.black);
				}

				// Draws the lock on the northeast if it is possessed
				if (i == containers.length - 1 && cont.getLock(2).getOwner() >= 0)
				{
					g2d.setColor(Window.playerColors[cont.getLock(2).getOwner()]);
					g2d.fillOval(
						((i + 1) * width) + actualMarginSide - actualRadius,
						(j * height) + MARGIN_TOP - actualRadius,
						2 * actualRadius, 2 * actualRadius
					);
					g2d.setColor(Color.black);
				}

				// Draws the lock on the southeast if it is possessed
				if (i == containers.length - 1 && j == containers[i].length - 1 && cont.getLock(3).getOwner() >= 0)
				{
					g2d.setColor(Window.playerColors[cont.getLock(3).getOwner()]);
					g2d.fillOval(
						((i + 1) * width) + actualMarginSide - actualRadius,
                        ((j + 1) * height) + MARGIN_TOP - actualRadius,
						2 * actualRadius, 2 * actualRadius
					);
					g2d.setColor(Color.black);
				}

				// Draws the lock on the southwest if it is possessed
				if (j == containers[i].length - 1 && cont.getLock(4).getOwner() >= 0)
				{
					g2d.setColor(Window.playerColors[cont.getLock(4).getOwner()]);
					g2d.fillOval(
						(i * width) + actualMarginSide - actualRadius,
						((j + 1) * height) + MARGIN_TOP - actualRadius,
						2 * actualRadius, 2 * actualRadius
					);
					g2d.setColor(Color.black);
				}
			}
		}
		// g2d.setColor(Color.black);
		// g2d.fillRect(0, 0, getWidth(), getHeight());
	}
}
