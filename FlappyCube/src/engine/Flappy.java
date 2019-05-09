package engine;

public class Flappy extends ObjekatIgre
{
	private static final int visinaSkoka = 50; // konstanta za visinu skoka
	
	private Smer smer;
	private int brojacSkoka;
	
	public Flappy()
	{
		super(200, 275, 50, 50);
		smer = Smer.NaDole;
		brojacSkoka = 0;
	}

	@Override
	public void pomeriSe()
	{
		if(smer == Smer.NaDole)
			y++;
		else
		{
			if(brojacSkoka < visinaSkoka)
			{
				brojacSkoka++;
				y--;
			}
			else
			{
				brojacSkoka = 0;
				smer = Smer.NaDole;
			}
		}
	}
	
	public void skok()
	{
		smer = Smer.NaGore;
		brojacSkoka = 0;
	}
	
	public void resetuj()
	{
		setBounds(200, 275, 50, 50);
		smer = Smer.NaDole;
		brojacSkoka = 0;
	}
}
