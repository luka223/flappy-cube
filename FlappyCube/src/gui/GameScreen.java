package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class GameScreen extends JPanel
{
	private static final int width = 800;
	private static final int height = 650;
	
	private static final long serialVersionUID = -5002966980558209509L;
	private Image background;
	
	public GameScreen()
	{
		setPreferredSize(new Dimension(width, height)); // 800 x 650
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		setLayout(null);
		
		try
		{
			background = ImageIO.read(FlappyGUI.class.getResource("/images/background2.png"));
		} catch (IOException e)
		{
			System.err.println("Greska pri ucitavanju pozadine!");
		}
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		g.drawImage(background, 0, 0, null);
	}
}