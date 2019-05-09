package engine;

import java.awt.Rectangle;

public abstract class ObjekatIgre
{
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	public ObjekatIgre(int x, int y, int width, int height)
	{
		setBounds(x, y, width, height);
	}
	
	public void setBounds(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, width, height);
	}
	
	public abstract void pomeriSe();

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
}
