import java.util.ArrayList;
import java.util.Collections;


public class Child {

	private ArrayList<Integer> POPULATION;
	private SimulatedAnnealing simulation;
	private int POOL = 3;
	private int distance;
	
	public Child(){}
	
	public Child (ArrayList<Integer> population,SimulatedAnnealing simulation,int index){
		setPopulation(population,index);
		setSimulatedAnnealing(simulation);
		generateDistance();
	}
	
	public void setPopulation(ArrayList<Integer> pop, int index){
		ArrayList<Integer> shuffle = new ArrayList<Integer>();
		
		int limit = index+104;	// plus the amount to shuffle	
		int position[] = new int[limit-index];
		
		for(int i=index; i<limit; i++)
		{
			int size = limit-i;
			shuffle.add(pop.get(i));
			position[position.length-size] = i;
		}
		Collections.shuffle(shuffle);
		
		for(int z=0; z<position.length; z++)
			pop.set(position[z], shuffle.get(z));
		
		this.POPULATION=pop;
	}
		
	public void setSimulatedAnnealing(SimulatedAnnealing simulation){
		this.simulation=simulation;
	}
	
	public int getDistance(){
		return distance;
	}

	public int getPool(){
		return POOL;
	}
	
	public ArrayList<Integer> getPopulation(){
		return POPULATION;
	}
	
	public SimulatedAnnealing getSimulatedAnnealing(){
		return simulation;
	}
	
	public void generateDistance(){
		int count = 0;
		for(int i=0; i<getPopulation().size()-1; i++)
		{
			count += getSimulatedAnnealing().getCities()[POPULATION.get(i)][POPULATION.get(i+1)];
			if(i+2==getPopulation().size())
			{
				count+= getSimulatedAnnealing().getCities()[POPULATION.get(i+1)][POPULATION.get(0)];
			}
		}
		this.distance=count;
	}
	
}
