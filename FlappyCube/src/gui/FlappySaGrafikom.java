package gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import engine.Flappy;

public class FlappySaGrafikom extends Flappy
{
	private JLabel grafickiPrikaz;
	private GameScreen gamescreen;
	
	public FlappySaGrafikom(Boja boja, GameScreen gamescreen)
	{
		napraviPrikaz(boja);
		this.gamescreen = gamescreen;
		gamescreen.add(grafickiPrikaz);
	}
	
	private void napraviPrikaz(Boja b)
	{
		grafickiPrikaz = new JLabel();
		grafickiPrikaz.setBounds(getBounds());
		promeniBoju(b);
	}
	
	public void promeniBoju(Boja b)
	{
		grafickiPrikaz.setIcon(new ImageIcon(FlappyGUI.class.getResource("/images/" + b + ".png")));
	}
	
	@Override
	public void pomeriSe()
	{
		super.pomeriSe();
		grafickiPrikaz.setBounds(super.getBounds());
	}
	
	@Override
	public void resetuj()
	{
		super.resetuj();
		grafickiPrikaz.setBounds(getBounds());
		gamescreen.add(grafickiPrikaz);
	}
}
