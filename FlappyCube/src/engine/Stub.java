package engine;

import java.util.Random;

public class Stub
{
	public static final int sirinaStuba = 100;
	public static final int razmakIzmedjuStubova = 300;
	
	private int visinaProlaza;
	private int duzinaGornjeg;
	protected DeoStuba gornji;
	protected DeoStuba donji;

	public Stub(int visinaProlaza)
	{
		this.visinaProlaza = visinaProlaza;
		generisiVisinuStuba();
		gornji = new DeoStuba(800, 4, sirinaStuba, duzinaGornjeg);
		donji = new DeoStuba(800, duzinaGornjeg + this.visinaProlaza - 2, sirinaStuba, 600 - duzinaGornjeg - this.visinaProlaza);
	}

	private void generisiVisinuStuba()
	{
		// vraca visinu gornjeg stuba (oduzima se visina za otvor kuda se prolazi i jos 50 zbog trave - visina je 650)
		Random r = new Random();
		duzinaGornjeg = r.nextInt(600 - visinaProlaza);
	}
	
	public void pomeriSe()
	{
		gornji.pomeriSe();
		donji.pomeriSe();
	}
	
	public boolean daLiTrebaDaSeIzbaci()
	{
		if(gornji.getX() + sirinaStuba < 0)
			return true;
		else
			return false;
	}
	
	public static int sirinaPlusRazmak()
	{
		return sirinaStuba + razmakIzmedjuStubova;
	}
}
