package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.HighscoreEngine;
import engine.Igrac;

public class HighscoreGUI extends JFrame
{
	private static final long serialVersionUID = -8624292020539112739L;
	
	private HighscoreEngine highscoreEngine;
	
	public HighscoreGUI(HighscoreEngine highscoreEngine)
	{
		super("Najbolji rezultati");
		
		this.highscoreEngine = highscoreEngine;
		
		setLayout(new BorderLayout());
		setSever();
		setCentar();
		setJug();
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void setSever()
	{
		JPanel panel = new JPanel(new FlowLayout());
		JLabel labela = new JLabel("Najbolji rezultati");
		panel.add(labela);
		getContentPane().add(panel, BorderLayout.NORTH);
		
	}

	private void setCentar()
	{
		int brojIgraca = highscoreEngine.brojIgraca;
		Igrac igraci[] = highscoreEngine.igraci;
		
		JPanel panel= new JPanel(new FlowLayout());
		JLabel labela;
		
		if(brojIgraca == 0)
		{
			labela = new JLabel("Nije sacuvan nijedan rezultat");
			panel.add(labela);
		}
		else
		{
			JPanel pomocniPanel = new JPanel(new GridLayout(brojIgraca, 1));
			
			for(int i = 0; i < brojIgraca; i++)
			{
				labela = new JLabel((i+1) + ". " + igraci[i].ime + " - " + igraci[i].rezultat);
				pomocniPanel.add(labela);
			}
			
			panel.add(pomocniPanel);
		}
		
		getContentPane().add(panel, BorderLayout.CENTER);
	}

	private void setJug()
	{
		JPanel panel = new JPanel(new FlowLayout());
		JButton resetDugme = new JButton("Resetuj rezultate");
		
		resetDugme.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				highscoreEngine.resetujRezultate();
			}
		});
		
		panel.add(resetDugme);
		getContentPane().add(panel, BorderLayout.SOUTH);
	}
}
