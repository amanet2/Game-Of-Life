public class nation
{
	private int population;

	public void incrementPopulation(){population++;}
	public void decrementPopulation(){population--;}
	public int getPopulation(){ return population;}

	public nation(){population = 0;}
}