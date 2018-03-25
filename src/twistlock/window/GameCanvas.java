package twistlock.window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import twistlock.Controller;
import twistlock.job.Container;

/**
 * TODO
 * 		draw()
 * 		comments
 */
class GameCanvas extends JPanel
{
	private static final int MARGIN_SIDE = 50;//px
	private static final int MARGIN_TOP = 200;//px
	private static final int BORDER_RADIUS = 15;//px
	private static final int DEMI_SPACE_BETWEEN_CONTAINERS = 2;//px

	/**
	 * create a canvas used to draw the containers
	 */
	GameCanvas()
	{
		super();
		repaint();
	}

	/**
	 * redrawn the canvas
	 * @param g JPanel's {@link Graphics}
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		Container[][] containers = Controller.getController().getContainers();

		int width = (getWidth() - 2 * MARGIN_SIDE) / containers.length;
		int height = ((getHeight() - MARGIN_TOP - 30) / containers[0].length);

		g2d.fillRect(MARGIN_SIDE, getHeight() - 30, getWidth() - 2 * MARGIN_SIDE, 30);

		for (int i = 0; i < containers.length; i++)
		{
			for (int j = 0; j < containers[i].length; j++)
			{
				g2d.drawLine(
					MARGIN_SIDE + BORDER_RADIUS + i * width + DEMI_SPACE_BETWEEN_CONTAINERS,
					MARGIN_TOP + j * height + DEMI_SPACE_BETWEEN_CONTAINERS,
					MARGIN_SIDE - BORDER_RADIUS + (i + 1) * width - DEMI_SPACE_BETWEEN_CONTAINERS,
					MARGIN_TOP + j * height + DEMI_SPACE_BETWEEN_CONTAINERS
				);
				g2d.drawLine(
					MARGIN_SIDE + i * width + DEMI_SPACE_BETWEEN_CONTAINERS,
					MARGIN_TOP + BORDER_RADIUS + j * height + DEMI_SPACE_BETWEEN_CONTAINERS,
					MARGIN_SIDE + i * width + DEMI_SPACE_BETWEEN_CONTAINERS,
					MARGIN_TOP - BORDER_RADIUS + (j + 1) * height - DEMI_SPACE_BETWEEN_CONTAINERS
				);
				g2d.drawLine(
					MARGIN_SIDE + BORDER_RADIUS + i * width + DEMI_SPACE_BETWEEN_CONTAINERS,
					MARGIN_TOP + (j + 1) * height - DEMI_SPACE_BETWEEN_CONTAINERS,
					MARGIN_SIDE - BORDER_RADIUS + (i + 1) * width - DEMI_SPACE_BETWEEN_CONTAINERS,
					MARGIN_TOP + (j + 1) * height - DEMI_SPACE_BETWEEN_CONTAINERS
				);
				g2d.drawLine(
					MARGIN_SIDE + (i + 1) * width - DEMI_SPACE_BETWEEN_CONTAINERS,
					MARGIN_TOP + BORDER_RADIUS + j * height + DEMI_SPACE_BETWEEN_CONTAINERS,
					MARGIN_SIDE + (i + 1) * width - DEMI_SPACE_BETWEEN_CONTAINERS,
					MARGIN_TOP - BORDER_RADIUS + (j + 1) * height - DEMI_SPACE_BETWEEN_CONTAINERS
				);
				g2d.drawArc(
					MARGIN_SIDE - BORDER_RADIUS + i * width + DEMI_SPACE_BETWEEN_CONTAINERS,
					MARGIN_TOP - BORDER_RADIUS + j * height + DEMI_SPACE_BETWEEN_CONTAINERS,
					2 * BORDER_RADIUS,	2 * BORDER_RADIUS, 270, 90
				);

				g2d.drawArc(
					MARGIN_SIDE - BORDER_RADIUS + (i + 1) * width - DEMI_SPACE_BETWEEN_CONTAINERS,
					MARGIN_TOP - BORDER_RADIUS + j * height + DEMI_SPACE_BETWEEN_CONTAINERS,
					2 * BORDER_RADIUS,	2 * BORDER_RADIUS, 180, 90
				);
				g2d.drawArc(
					MARGIN_SIDE - BORDER_RADIUS + (i + 1) * width - DEMI_SPACE_BETWEEN_CONTAINERS,
					MARGIN_TOP - BORDER_RADIUS + (j + 1) * height - DEMI_SPACE_BETWEEN_CONTAINERS,
					2 * BORDER_RADIUS,	2 * BORDER_RADIUS, 90, 90
				);
				g2d.drawArc(
					MARGIN_SIDE - BORDER_RADIUS + i * width + DEMI_SPACE_BETWEEN_CONTAINERS,
					MARGIN_TOP - BORDER_RADIUS + (j + 1) * height - DEMI_SPACE_BETWEEN_CONTAINERS,
					2 * BORDER_RADIUS,	2 * BORDER_RADIUS, 0, 90
				);

				g2d.drawString(containers[i][j].getValue() + "", (int)((i + 0.5) * width) + MARGIN_SIDE, (int)((j + 0.5) * height) + MARGIN_TOP);

				
			}
		}

		// g2d.setColor(Color.black);
		// g2d.fillRect(0, 0, getWidth(), getHeight());
	}
}