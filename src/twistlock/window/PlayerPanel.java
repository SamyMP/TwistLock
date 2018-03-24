package twistlock.window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import twistlock.job.Player;

/**
 * TODO
 * 		draw()
 * 		comments
 */
class PlayerPanel extends JPanel
{
	BufferedImage image;

	PlayerPanel(Player player)
	{
		super();

		image = new BufferedImage(500, 1500, BufferedImage.TYPE_INT_ARGB);
		draw();
	}

	/**
	 * redessine le canvas
	 * @param g le graphic du JPanel
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), null, null);

	}

	void draw()
	{
		// System.out.print("reception de : ");
		// for (String param : params)
		// {
		// 	System.out.print(param + " , ");
		// }
		// System.out.println();

		Graphics2D g2 = image.createGraphics();

		g2.setColor(Color.black);
		g2.fillOval(250, 300, 200, 400);

		g2.setColor(Color.white);
		g2.fillRect(100, 500, 75, 20);

		repaint();
	}
}
