package gui;

import java.awt.Color;

import javax.swing.JPanel;

import engine.Stub;

public class StubSaGrafikom extends Stub
{
	private JPanel prikazGornjeg;
	private JPanel prikazDonjeg;
	GameScreen gamescreen;
	
	public StubSaGrafikom(int visinaProlaza, GameScreen gamescreen)
	{
		super(visinaProlaza);
		this.gamescreen = gamescreen;
		prikaziStubove();
	}
	
	private void prikaziStubove()
	{
		Color bojaStuba = new Color(0, 66, 0); // boja stuba (zelena)
		
		prikazGornjeg = new JPanel();
		prikazGornjeg.setBounds(gornji.getBounds()); // 4 zbog bordera
		prikazGornjeg.setBackground(bojaStuba);

		prikazDonjeg = new JPanel();
		prikazDonjeg.setBounds(donji.getBounds());
		prikazDonjeg.setBackground(bojaStuba);
		
		gamescreen.add(prikazGornjeg);
		gamescreen.add(prikazDonjeg);
	}
	
	@Override
	public void pomeriSe()
	{
		super.pomeriSe();
		prikazGornjeg.setBounds(gornji.getBounds());
		prikazDonjeg.setBounds(donji.getBounds());
	}
	
	public void izbrisiGrafiku()
	{
		gamescreen.remove(prikazGornjeg);
		gamescreen.remove(prikazDonjeg);
	}
}
