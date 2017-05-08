public class cell
{
	private int xCoord;	//coordinates represent an element of the 2d config array
	private int yCoord;

	private boolean alive;	//cell can be alive or dead

	public int getX()	//getters and setters and constructors, oh my!
	{
		return xCoord;
	}

	public int getY()
	{
		return yCoord;
	}


	public boolean getStatus()
	{
		return alive;
	}

	public void updateStatus(boolean b)
	{
		alive = b;
	}

	public cell()
	{
		xCoord = -1;
		yCoord = -1;

		alive = false;
	}

	public cell(int x, int y)
	{
		xCoord = x;
		yCoord = y;

		alive = false;
	}

	public cell(int x, int y, boolean state)
	{
		xCoord = x;
		yCoord = y;

		alive = state;
	}
}