package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import engine.FlappyEngine;
import engine.HighscoreEngine;
import engine.Stub;
import engine.Tezina;

public class FlappyGUI extends JFrame implements KeyEventDispatcher
{
	private static final long serialVersionUID = -3692101989043863494L;
	
	private FlappyEngine engine;
	private JLabel rezultat;
	private JLabel highscore;
	private JLabel labelaSaPorukomZaPocetakIgre; // samo za poruku na ekranu
	private JPanel panelZaLabeluSaPorukom; // samo za poruku na ekranu
	private JComboBox<Tezina> tezinaComboBox;
	private JComboBox<Boja> bojaComboBox;
	private FlappySaGrafikom flappy;
	private GameScreen gamescreen;
	private int predjeniPut; // za dodavanje stubova
	private Timer timer;
	private List<StubSaGrafikom> stubovi;
	private boolean daLiJeKraj;
	private boolean daLiJeUnetRezultat;
	private HighscoreEngine highscoreEngine;
	
	public FlappyGUI()
	{
		super("Flappy Cube");

		gamescreen = new GameScreen();
		flappy = new FlappySaGrafikom(Boja.Crvena, gamescreen);
		engine = new FlappyEngine(Tezina.Lako, flappy);
		highscoreEngine = new HighscoreEngine();
		stubovi = new ArrayList<>();
		rezultat = new JLabel("Rezultat: 0");
		highscore = new JLabel("Najbolji rezultat: " + highscoreEngine.getHighscore());
		srediLabeluZaPocetakIgre();
		bojaComboBox = new JComboBox<>();
		tezinaComboBox = new JComboBox<>();
		daLiJeKraj = true;
		daLiJeUnetRezultat = true;
		
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(this);
		
		setLayout(new BorderLayout());
		setSever();
		setIstok();
		setCentar();
		
		napraviTimer();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null); // da pokrene na sredini ekrana (neophodno je da se zove nakon pack();)
		setVisible(true);
	}

	private void setSever()
	{
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 0));
		panel.add(rezultat);
		panel.add(highscore);
		getContentPane().add(panel, BorderLayout.NORTH);
	}

	private void setCentar()
	{
		pocetniEkran();
		getContentPane().add(gamescreen, BorderLayout.CENTER);
	}

	private void setIstok()
	{
		JPanel glavniPanel = new JPanel();
		Dimension d = new Dimension();
		d.width = 200;
		glavniPanel.setPreferredSize(d);
		
		JPanel panelZaBoxeve = new JPanel(new GridLayout(2, 2));
		
		JLabel labelaBoja = new JLabel("Izaberi boju: ");
		panelZaBoxeve.add(labelaBoja);
		bojaComboBox.addItem(Boja.Crvena);
		bojaComboBox.addItem(Boja.Zuta);
		bojaComboBox.addItem(Boja.Plava);
		bojaComboBox.addItem(Boja.Zelena);
		bojaComboBox.setFocusable(false); // da ne reaguje na tastaturu
		panelZaBoxeve.add(bojaComboBox);
		
		bojaComboBox.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					Boja boja = (Boja)bojaComboBox.getSelectedItem();
					flappy.promeniBoju(boja);
				}
			}
		});
		
		JLabel labelaTezina = new JLabel("Izaberi tezinu: ");
		panelZaBoxeve.add(labelaTezina);
		tezinaComboBox.addItem(Tezina.Lako);
		tezinaComboBox.addItem(Tezina.Srednje);
		tezinaComboBox.addItem(Tezina.Tesko);
		tezinaComboBox.setFocusable(false);
		panelZaBoxeve.add(tezinaComboBox);
		
		tezinaComboBox.addItemListener(new ItemListener()
		{	
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				if(e.getStateChange() == ItemEvent.SELECTED)
					engine.promeniTezinu((Tezina)tezinaComboBox.getSelectedItem());
			}
		});
		
		glavniPanel.add(panelZaBoxeve);
		
		JLabel labelaZaSliku = new JLabel();
		labelaZaSliku.setIcon(new ImageIcon(FlappyGUI.class.getResource("/images/cubelogo.png")));
		glavniPanel.add(labelaZaSliku);
		
		JPanel panelZaDugmice = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 0));
		
		JButton dugmeRezultati = new JButton();
		dugmeRezultati.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		ImageIcon slikaRezultati = new ImageIcon(FlappyGUI.class.getResource("/images/highscore.png"));
		dugmeRezultati.setIcon(resizeImageIcon(slikaRezultati, 60, 60));
		dugmeRezultati.setFocusable(false);
		dugmeRezultati.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				new HighscoreGUI(highscoreEngine);
			}
		});
		
		panelZaDugmice.add(dugmeRezultati);
		
		JButton dugmeNovaIgra = new JButton();
		dugmeNovaIgra.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		dugmeNovaIgra.setIcon(resizeImageIcon(new ImageIcon(FlappyGUI.class.getResource("/images/play.png")), 60, 60));
		dugmeNovaIgra.setFocusable(false);
		dugmeNovaIgra.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(daLiJeKraj == true && daLiJeUnetRezultat == true)
					novaIgra();
			}
		});
		
		panelZaDugmice.add(dugmeNovaIgra);
		
		glavniPanel.add(panelZaDugmice);
		getContentPane().add(glavniPanel, BorderLayout.EAST);
	}
	
	private ImageIcon resizeImageIcon(ImageIcon slika, int width, int height)
	{
		Image img = slika.getImage() ;  
		Image newimg = img.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH) ;  
		return new ImageIcon(newimg);
	}
	
	private void napraviTimer()
	{		
		timer = new Timer(5, new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(predjeniPut < Stub.sirinaPlusRazmak())
					predjeniPut++;
				else
				{
					predjeniPut = 0;
					StubSaGrafikom noviStub = new StubSaGrafikom(engine.getVisinaProlaza(), gamescreen);
					stubovi.add(noviStub);
					engine.dodajStub(noviStub); // napravljeni stub dodaje i u listu stubova u enginu
				}
				
				flappy.pomeriSe();
				
				for(int i = 0; i < stubovi.size(); i++)
					stubovi.get(i).pomeriSe();
				
				if(stubovi.get(0).daLiTrebaDaSeIzbaci())
				{
					stubovi.get(0).izbrisiGrafiku();
					stubovi.remove(0);
					engine.izbaciStub();
				}
				
				proveraIOsvezavanje();
			}
		});
	}
	
	private void srediLabeluZaPocetakIgre()
	{
		panelZaLabeluSaPorukom = new JPanel(new FlowLayout());
		labelaSaPorukomZaPocetakIgre = new JLabel("Pritisnite dugme Play za ili taster Enter pocetak igre");
		
		labelaSaPorukomZaPocetakIgre.setFont(labelaSaPorukomZaPocetakIgre.getFont().deriveFont(20.0f));
		panelZaLabeluSaPorukom.setBounds(0, 0, 800, 50);
		panelZaLabeluSaPorukom.add(labelaSaPorukomZaPocetakIgre);
		panelZaLabeluSaPorukom.setOpaque(false);
	}
	
	public void proveraIOsvezavanje()
	{
		if(engine.daLiJeKraj())
		{
			daLiJeKraj = true;
			
			timer.stop();
			
			String poruka = "Osvojili ste: " + engine.getRezultat() + "\n";
			int rezultat = engine.getRezultat();
			
			daLiJeUnetRezultat = false;
			
			if(highscoreEngine.daLiJeMoguceDodatiRezultat(rezultat))
			{
				poruka += "Unesite ime da se upisete na rang listu (ime ne sme sadrzati blanko karaktere): ";
				String ime;
					
				do
				{
					ime = JOptionPane.showInputDialog(poruka);
						
					if(ime != null && highscoreEngine.proveriIme(ime) == false)
						JOptionPane.showMessageDialog(null, "Ime ne sme sadrzati blanko karaktere");
				} while(ime != null && highscoreEngine.proveriIme(ime) == false);
					
				if(ime != null)
					highscoreEngine.dodajRezultat(ime, rezultat);	
			}
			else
				JOptionPane.showMessageDialog(null, poruka);
			
			highscore.setText("Najbolji rezultat: " + highscoreEngine.getHighscore());
			pocetniEkran(); // da opet pise za pocetak igre
				
			daLiJeUnetRezultat = true;
		}
		else
			gamescreen.repaint();
		
		rezultat.setText("Rezultat: " + engine.getRezultat());
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e)
	{
		if(e.getID() == KeyEvent.KEY_PRESSED)
		{
			if(daLiJeKraj == false && e.getKeyCode() == KeyEvent.VK_SPACE)
				flappy.skok();
			
			if(daLiJeKraj == true && daLiJeUnetRezultat == true && e.getKeyCode() == KeyEvent.VK_ENTER)
				novaIgra();
		}
		
		return false;
	}
	
	private void novaIgra()
	{		
		gamescreen.removeAll(); // izbaci sve komponente iz gamescreena
		
		flappy.resetuj(); // jer se sa pocetnog sve izbacuje, pa i flappy, pa da bi se video na ekranu
		engine = new FlappyEngine((Tezina)tezinaComboBox.getSelectedItem(), flappy);
		stubovi = new ArrayList<>();
		predjeniPut = 400;
		
		daLiJeKraj = false;

		gamescreen.repaint();
		timer.start();
	}
	
	private void pocetniEkran()
	{
		gamescreen.removeAll();
		
		flappy.resetuj();
		
		gamescreen.add(panelZaLabeluSaPorukom);
		gamescreen.repaint();
	}
}
