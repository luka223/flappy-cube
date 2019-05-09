package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class HighscoreEngine
{
	private File datoteka;
	public Igrac igraci[];
	public int brojIgraca;
	
	public HighscoreEngine()
	{
		napraviFajl();
		igraci = new Igrac[10]; // pamti se maksimalno 10 najboljih rezultata
		brojIgraca = 0;
		ucitajRezultate();
	}
	
	private void napraviFajl()
	{
		datoteka = new File("highscore.txt");
		
		if(datoteka.exists() == false)
		{
			try
			{
				datoteka.createNewFile();
			} catch (IOException e)
			{
				System.err.println("Greska pri kreiranju fajla sa rezultatima!");
			}
		}
	}
	
	private void ucitajRezultate()
	{
		try
		{
			Scanner s = new Scanner(datoteka);
			
			while(s.hasNext())
				igraci[brojIgraca++] = new Igrac(s.next(), s.nextInt());
			
			s.close();
				
		} catch (FileNotFoundException e)
		{
			System.err.println("Ne moze se pristiputi rezultatima!");
		}
	}
	
	public boolean daLiJeMoguceDodatiRezultat(int rezultat)
	{
		if(rezultat == 0 || (brojIgraca == 10 && rezultat <= igraci[brojIgraca-1].rezultat))
			return false;
		else
			return true;
	}
	
	public void dodajRezultat(String ime, int rezultat)
	{
		if(brojIgraca < 10)
			igraci[brojIgraca++] = new Igrac(ime, rezultat);
		else
			igraci[brojIgraca-1] = new Igrac(ime, rezultat);
		
		for(int i = 0; i < brojIgraca-1; i++)
			for(int j = i+1; j < brojIgraca; j++)
				if(igraci[i].rezultat < igraci[j].rezultat)
				{
					Igrac t = igraci[i];
					igraci[i] = igraci[j];
					igraci[j] = t;
				}
		
		zapisiUFajl();
	}
	
	public boolean proveriIme(String ime)
	{
		if(ime.equals(""))
			return false;
		
		char karakteri[] = ime.toCharArray();
		
		for(char c : karakteri)
			if(c == ' ' || c == '\t' || c == '\n')
				return false;
		
		return true;
	}
	
	private void zapisiUFajl()
	{
		try
		{
			PrintWriter pw = new PrintWriter(datoteka);
			for(int i = 0; i < brojIgraca; i++)
				pw.println(igraci[i].ime + " " + igraci[i].rezultat);
			pw.close();
			
		} catch (FileNotFoundException e)
		{
			System.err.println("Greska pri otvaranju fajla sa rezultatima");
		}
	}
	
	public int getHighscore()
	{
		if(brojIgraca == 0)
			return 0;
		else
			return igraci[0].rezultat;
	}
	
	public void resetujRezultate()
	{
		igraci = new Igrac[10];
		brojIgraca = 0;
		zapisiUFajl(); // niz je prazan, pa ce fajl da bude takodje prazan
	}
}
