import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class SimulatedAnnealing {

	private int[][] cities;
	private String fileName;
	private int NUMBEROFCITIES;
	
	public SimulatedAnnealing(){}
	
	public SimulatedAnnealing(String filename){
		setFileName(filename);
		setNumberOfCities();
		setCities();
	}
	
	public void setFileName(String fileName){
		this.fileName=fileName;
	}
	
	/*public void setCities(){
		
		try{
			String line = null;
			FileReader fileReader = new FileReader(getFileName());
        	BufferedReader bufferedReader = new BufferedReader(fileReader);
        	
        	int[][] cities = new int[getNumberOfCities()][getNumberOfCities()];
        	int row = 0;
        	
        	while((line = bufferedReader.readLine()) != null)
        	{	 
        		String[] splitCities = line.split(" ");
        		
        		for(int i=0; i<getNumberOfCities(); i++)
        		{
        			int num = Integer.parseInt(splitCities[i]);
            		cities[row][i] = num;
        		}        	      		
        		row ++;
        	}
        	
        	this.cities=cities;
        	bufferedReader.close();
		}
		
		catch(Exception E)
		{
			E.printStackTrace();
			System.exit(0);
		}
	}*/
	
	/*public void setNumberOfCities(){
		try{
			String line = null;
			FileReader fileReader = new FileReader(getFileName());
        	BufferedReader bufferedReader = new BufferedReader(fileReader);
        	
        	int count = 0;
        	while((line = bufferedReader.readLine()) != null)
                count ++;
        	this.NUMBEROFCITIES=count;
        	bufferedReader.close();
		}
		catch(Exception E)
		{
			E.printStackTrace();
			System.exit(0);
		}
	}*/
	
	
	// Use this method for 312 cities
	public void setCities(){
		
		int[][] cities = new int[getNumberOfCities()][getNumberOfCities()];
		
		Scanner scanner;
		try {
			scanner = new Scanner(new File("cities2.txt"));
			int i = 0;
			int row = 0;
			for(int x=0; x<97344; x++)
			{
				cities[row][i] = scanner.nextInt();
				i++;
				if(i==312)
				{
					row ++;
					i=0;
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.cities=cities;
	}
	
	//User this method for 312 cities
	public void setNumberOfCities(){
		this.NUMBEROFCITIES=312;
	}
	
	public int[][] getCities(){
		return cities;
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public int getNumberOfCities(){
		return NUMBEROFCITIES;
	}
	
	public int randomNumber(int min, int max){
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	public ArrayList<Integer> generateRandomList(int size,int min,int max){
		
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for(int x=min; x<=max; x++)
			numbers.add(x);
			
		Collections.shuffle(numbers);			
		return numbers;
	}
	
	public boolean acceptMove(int parent,int bestChild,int temperature){
		if(bestChild < parent)
			return true;
		else if (temperature == 0)
			return false;
		else{
			double probability = Math.exp(-(bestChild - parent)/temperature);
			double randomNumber = Math.random();
			if(randomNumber < probability)
				return true;
			else
				return false;
		}
		
		
	}
}
