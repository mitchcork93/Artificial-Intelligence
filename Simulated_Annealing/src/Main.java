import java.util.ArrayList;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SimulatedAnnealing simulation = new SimulatedAnnealing("cities2.txt");
		ArrayList<Integer> POPULATION = simulation.generateRandomList(312, 0, 311);
		
		int parentDistance = 0;		
		int[][] cities = simulation.getCities();
		Child bestChild = null;
		Child optimalChild = null;
		int optimalDistance = 0;
		int TEMPERATURE = 99999;
		
		for(int i=0; i<POPULATION.size()-1; i++)
		{
			parentDistance += cities[POPULATION.get(i)][POPULATION.get(i+1)];
			if(i==POPULATION.size()-2)
				parentDistance+= cities[POPULATION.get(i+1)][POPULATION.get(0)];
		}
		
		ArrayList<Integer> childPop = new ArrayList<Integer>(POPULATION);
	
		while(TEMPERATURE>0) // Temperature used to decided whether to move or not
		{	
			for(int x=0; x<209; x++) // x < size - shuffle amount
			{			
				Child spawn = new Child(childPop,simulation,x);
				
				if(spawn.getDistance()<parentDistance) // if its better then other child
				{
					System.out.println("NEW BEST DISTANCE IS " + spawn.getDistance());
					bestChild=spawn;
					parentDistance = spawn.getDistance();
				}
			}
			
			if(bestChild == null) // No child is better then parent. END
			{
				optimalDistance = parentDistance; 
				System.out.println("BEST CHILD IS NULL BREAK");
				break;
			}
			
			if(optimalChild != null && optimalChild.getDistance()<= bestChild.getDistance())
			{
				if(simulation.acceptMove(optimalChild.getDistance(), bestChild.getDistance(), TEMPERATURE)) // Should the child become new parent?
				{
					optimalChild = bestChild;
					childPop = new ArrayList<Integer>(bestChild.getPopulation());
				}
			}
			
			else if(optimalChild == null) // First time through the loop, save previous best child for comparing.
				optimalChild = bestChild;
			
			else{
				System.out.println(bestChild.getDistance() + " is better then previous " + optimalChild.getDistance());
				optimalChild = bestChild;
				childPop = new ArrayList<Integer>(bestChild.getPopulation()); 
			}
			TEMPERATURE --;
		}
		
		if( bestChild == null)
		{
			System.out.print("Best path is parent: " + parentDistance + " travelling from ");
			for(int Z=0; Z<POPULATION.size(); Z++)
				System.out.print(POPULATION.get(Z) + ",");
		}
		else
		{
			System.out.print("Best path is " + optimalChild.getDistance() + " Travelling from ");
			for(int y=0; y<optimalChild.getPopulation().size(); y++)
				System.out.print(optimalChild.getPopulation().get(y) + ",");
		}
	}

}
