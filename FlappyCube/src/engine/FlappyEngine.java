package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlappyEngine
{
	private int rezultat;
	private int visinaProlaza;
	private Tezina tezina;
	private Flappy flappy;
	private List<Stub> stubovi;
	private Stub tekuciStub;
	
	public FlappyEngine(Tezina tezina, Flappy flappy)
	{
		rezultat = 0;
		this.flappy = flappy;
		stubovi = new ArrayList<>();
		tekuciStub = null;
		promeniTezinu(tezina);
	}
	
	public void promeniTezinu(Tezina t)
	{
		switch (t)
		{
			case Lako:
				visinaProlaza = 200;
				break;

			case Srednje:
				visinaProlaza = 150;
				break;
				
			case Tesko:
				visinaProlaza = 120;
				break;
		}
		
		tezina = t;
	}
	
	private int multiplier()
	{
		switch (tezina)
		{
			case Lako:
				return 1;
				
			case Srednje:
				return 2;
	
			case Tesko:
				return 3;
		}
		
		return 0;
	}
	
	public int dajVisinuStuba()
	{
		// vraca visinu gornjeg stuba (oduzima se visina za otvor kuda se prolazi i jos 50 zbog trave - visina je 650)
		Random r = new Random();
		return r.nextInt(600 - visinaProlaza);
	}
	
	public boolean daLiJeKraj()
	{
		// prvi sledeci stub na koji se nailazi
		DeoStuba stubGore = tekuciStub.gornji;
		DeoStuba stubDole = tekuciStub.donji;
		
		// ispituje da li je flappy udario u stub i da li je udario u zemlju
		double flappy_x1 = flappy.getX();
		double flappy_y1 = flappy.getY();
		double flappy_x2 = flappy.getX() + flappy.getWidth();;
		double flappy_y2 = flappy.getY() + flappy.getHeight();
		
		// i gornji i donji stub imaju iste X koordinate
		double stub_x1 = stubGore.getX();
		double stub_x2 = stubGore.getX() + stubGore.getWidth();
		
		double stubGore_y2 = stubGore.getY() + stubGore.getHeight();
		double stubDole_y1 = stubDole.getY();
		
		if(flappy_y2 >= 600) // udario u pod (650 je visina ekrana, a 50 je trava)
			return true;
		
		if(flappy_x2 >= stub_x1 && flappy_x1 <= stub_x2 && flappy_y1 <= stubGore_y2) // udario u gornji stub
			return true;
		
		if(flappy_x2 >= stub_x1 && flappy_x1 <= stub_x2 && flappy_y2 >= stubDole_y1) // udario u donji stub
			return true;
		
		// ako se nije desio sudar, proveri da li se desio prolazak kroz izmedju i ako jeste uvecaj rezultat
		if(flappy_x1 == stub_x2)
		{
			rezultat += multiplier();
			int i = stubovi.indexOf(tekuciStub) + 1;
			tekuciStub = stubovi.get(i);
		}
		
		return false;
	}
	
	public int getRezultat()
	{
		return rezultat;
	}
	
	public int getVisinaProlaza()
	{
		return visinaProlaza;
	}
	
	public void dodajStub(Stub stub)
	{
		stubovi.add(stub);
		
		if(tekuciStub == null)
			tekuciStub = stub;
	}

	public void izbaciStub()
	{
		stubovi.remove(0);		
	}
}
