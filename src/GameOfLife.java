import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

public class GameOfLife
{
	public void configFromFile(grid g,String fileName)
	{
		String line = null; //initialize string to represent line in file

        	try { //try
           		 // FileReader reads text files in the default encoding.
            		FileReader fileReader = 
                		new FileReader(fileName); //fileReader instance

			    // Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = 
				new BufferedReader(fileReader);
            
            		ArrayList<String> list = new ArrayList<String>();
            
		 	while((line = bufferedReader.readLine()) != null)
			{ //until we reach empty space in file
		    		list.add(line);
		    	}

			if(list.size() > 0)
			{
				//first line sets size, the minimum of what we need
				int firstLine = 1;
				for(int i = 0; i < list.size(); i++)
				{
					String[] toks = list.get(i).split(" ");	//tokenize each line
					if(toks.length == 2)
					{
						try{
	
							if(firstLine == 1)	//on the first line, set the sizes
							{
								
								int x = Integer.valueOf(toks[0]);
								int y = Integer.valueOf(toks[1]);
								if(x > 0 && y > 0){
									g.setXS(x+2);
									g.setYS(y+2);

									g.setConfig(new char[g.getXS()][g.getYS()]);
									g.resetConfig(g.getConfig());
									firstLine = 0;
								}
							}
							else
							{	//other runs demand the placement of live cells
								int x = Integer.valueOf(toks[0]);
								int y = Integer.valueOf(toks[1]);	
					
								if(x > g.getXS() || x <= 0){
									System.err.println("error on line " + i+ " xCoord out of range\n");
									continue;
								}
							
								if(y > g.getYS() || y <= 0){ 
									System.err.println("error on line " + i +" yCoord out of range\n");	
									continue;
								}
								g.setCharAlive(x,y);
							}
						}
						catch(Exception eee)
						{
							System.err.println("\nFormat error in file on line " + i + "\n");
						}
					}
					else
						System.err.println("\nFormat error in file on line " + i + "\n");
				}
			}
			else
			{
				System.err.println("\nNo Data Found\n");
			}
		}
		catch(FileNotFoundException ex) { //catch exception for file not there
		    System.err.println(
		        "Unable to open file '" + 
		        fileName + "' [FILE NOT FOUND]");
			System.exit(1);				
		}
		catch(IOException ex) { //catch exception for file corrupted
		    System.err.println(
		        "Error reading file '" 
		        + fileName + "'");
			System.exit(1);					
		    // Or we could just do this: 
		    // ex.printStackTrace();
		}

	}

	public static void main(String[] args)	
	{
		
		if(args.length == 1)	//we need an arg
		{
			GameOfLife program = new GameOfLife();

			grid g = new grid();	//create a new grid
			program.configFromFile(g,args[0]);	//and initialize it
			
				g.printGrid();	//print the initial grid
			try{
				g.performEvolutionCycles();	//and start the GAME OF LIFE
			}
			catch(Exception ee)
			{
				System.err.println("\n\nExeption Caught in Life\n\n");
			}

		}
		else
			System.out.println("\nUsage: <program> <datafile>.txt\n");
	}
}
