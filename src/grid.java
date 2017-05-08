import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class grid	//a bit more complicated
{
	private int xSize;
	private int ySize;
	private char alive;
	private char dead;
	private char[][] config;

	public void setCharAlive(int x, int y)
	{
		config[x][y] = alive;
	}

	public char[][] getConfig()
	{
		return config;
	}

	public void setConfig(char[][] c)
	{
		config = c;
	}

	public int getYS()
	{
		return ySize;
	}

	public int getXS()
	{
		return xSize;
	}

	public void setXS(int x)
	{
		xSize = x;
	}

	public void setYS(int y)
	{
		ySize = y;
	}

	public void printf(char c)
	{
		System.out.print(c);
	}

	public void printf(String s)
	{
		System.out.print(s);
	}

	public void resetConfig(char[][] configu)	//create a bordered world where all is dead
	{
		for(int i = 0; i < xSize; i++)
		{
			for(int j = 0; j < ySize; j++)
			{
				if(i == 0 || j == 0 || i == xSize-1 || j == ySize-1)
					configu[i][j] = '*';
				else
					configu[i][j] = dead;
			}
		}
	}

	public grid(int x, int y)
	{
		alive = 'o';
		dead = '-';
		xSize = x+2;	//set the size
		ySize = y+2;

		config = new char[xSize][ySize];	//initialize config
		resetConfig(config);
	}

	public grid()
	{
		alive = 'o';
		dead = '-';
	}

	public void printGrid()
	{
		for(int i = 0; i < xSize; i++)
		{
			for(int j = 0; j < ySize; j++)
			{
				printf(config[i][j]);
			}
			printf("\n");
		}
	}

	public void performEvolutionCycles() throws Exception
	{

			/* We will have the nation of the nations!  If I can figure out how...*/
		nation cyans = new nation();
		nation yellows = new nation();
		nation reds = new nation();
		nation greens = new nation();
		nation pinks = new nation();
		nation magentas = new nation();
		nation oranges = new nation();
		nation whites = new nation();

		int maxPopulation = 0;	//highest pop of a color of cell
		int generation = 1;
		nation bestTeam;	//the highest population on the grid at the current time

		JButton[][] buttons = new JButton[getXS()][getYS()];	//buttons for gui

		JFrame frame;	//make the frame

		frame = new JFrame("Game Of Life : Generation " + generation);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLayout(new GridLayout(getXS(),getYS()));	//grid layout


		for(int i = 0; i < getXS(); i++)	//add all buttons
		{
			for(int j = 0; j < getYS(); j++)
			{
				buttons[i][j] = new JButton("");	//properties
				buttons[i][j].setEnabled(false);
				buttons[i][j].setOpaque(true);

				if(i == 0 || j == 0 || i == getXS()-1 || j == getYS()-1)
					buttons[i][j].setBackground(Color.BLUE);
				else if(config[i][j] == dead) buttons[i][j].setBackground(Color.BLACK);
				else if(config[i][j] == alive)
					buttons[i][j].setBackground(Color.GREEN);

				frame.getContentPane().add(buttons[i][j]);	//add all buttons, regardless
			}
		}

		frame.setSize(1024,768);
//		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setVisible(true);


		while(true)
		{
			Thread.sleep(150);


			ArrayList<cell> listOfAllCells = new ArrayList<cell>();

			char[][] newConfig = new char[xSize][ySize];	//new array

			for(int i = 1; i < xSize-1; i++)	//scan each element of the config and set up the next state
			{
				for(int j = 1; j < ySize-1; j++)	//the parts where i/j == 0 or i/j == x/ySize-1 are borders '*'
				{
					cell c;

					if(config[i][j] == alive) c = new cell(i,j,true);
					else c = new cell(i,j,false);

					//we have alive/dead chars, don't use static chars
					int numberOfNeighbors = 0;

					//check all eight neighbors
					if(config[i-1][j-1] == alive)	numberOfNeighbors++;
					if(config[i-1][j] == alive)	numberOfNeighbors++;
					if(config[i-1][j+1] == alive)	numberOfNeighbors++;
					if(config[i][j-1] == alive)	numberOfNeighbors++;
					if(config[i][j+1] == alive)	numberOfNeighbors++;
					if(config[i+1][j-1] == alive)	numberOfNeighbors++;
					if(config[i+1][j] == alive)	numberOfNeighbors++;
					if(config[i+1][j+1] == alive)	numberOfNeighbors++;

					//now decidde the current cell's fate
					//more than three neighbors means death
					if(numberOfNeighbors > 3) c.updateStatus(false);
					else if(numberOfNeighbors < 2) c.updateStatus(false);
					else if(c.getStatus() == false && numberOfNeighbors == 3) c.updateStatus(true);

					listOfAllCells.add(c);
				}
			}



			int didSomething = 0;

			resetConfig(newConfig);
			//now use the cells arraylist to set the new config based on what it should be.
			for(cell c : listOfAllCells)
			{
				if(c.getStatus() == true)
				{
					didSomething = 1;
					newConfig[c.getX()][c.getY()] = alive;

					if(c.getX() >= 2*getXS()/5 && c.getX() < (3*getXS()/5) && c.getY() >= 2*getYS()/5 && c.getY() < (3*getYS()/5)){ whites.incrementPopulation();	buttons[c.getX()][c.getY()].setBackground(Color.WHITE);}
					else if(c.getX() >= getXS()/3 && c.getX() < (2*getXS()/3) && c.getY() >= getYS()/3 && c.getY() < (2*getYS()/3)){ pinks.incrementPopulation();	buttons[c.getX()][c.getY()].setBackground(Color.PINK);}
					else if(c.getX() >= getXS()/3 && c.getX() < 2*getXS()/3 && c.getY() < (getYS()/3)){
						buttons[c.getX()][c.getY()].setBackground(Color.MAGENTA);
						magentas.incrementPopulation();}
					else if(c.getX() >= getXS()/3 && c.getX() < 2*getXS()/3 && c.getY() >= 2*getYS()/3){
						buttons[c.getX()][c.getY()].setBackground(Color.ORANGE);
						oranges.incrementPopulation();}
					else if(c.getX() >= getXS()/2 && c.getY() >= getYS()/2){
						buttons[c.getX()][c.getY()].setBackground(Color.GREEN);
						greens.incrementPopulation();}
					else if(c.getX() < getXS()/2 && c.getY() >= getYS()/2){
						buttons[c.getX()][c.getY()].setBackground(Color.YELLOW);
						yellows.incrementPopulation();}
					else if(c.getX() < getXS()/2 && c.getY() < getYS()/2){
						buttons[c.getX()][c.getY()].setBackground(Color.CYAN);
						cyans.incrementPopulation();}
					else{ buttons[c.getX()][c.getY()].setBackground(Color.RED); reds.incrementPopulation();}
				}
				else
				{
					newConfig[c.getX()][c.getY()] = dead;
					buttons[c.getX()][c.getY()].setBackground(Color.BLACK);
				}
			}


			config = newConfig;	//set the config to be the new state
			frame.setTitle("Game Of Life : Generation " + generation);
			generation++;

			if(didSomething == 0)
			{
				System.out.print("\nSimulation over, world is dead.\nPress Any Key to continue... ");
				Scanner keys = new Scanner(System.in);
				String in = keys.next();
				System.exit(0);
			}
		}
	}
}
