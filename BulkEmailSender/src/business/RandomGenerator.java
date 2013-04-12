package business;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class RandomGenerator {
	static Random rand;
	static
	{
		rand=new Random();
		rand.setSeed(5);
	}
	
	public static String getRandomSubject(){
		Scanner sc=null;
		String subject=new String();
		int i=0,pos;
		
		pos=rand.nextInt()%20;
		
		try {
			sc=new Scanner(new FileInputStream("subjects.txt"));
			while(i<pos){
				sc.nextLine();
				i++;
			}
			subject=sc.nextLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(sc!=null){
			sc.close();
		}
		
		return subject;
	}
}
