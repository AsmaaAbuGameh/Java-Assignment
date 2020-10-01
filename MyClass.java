
package javaproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//a class car explain a car information
class car {
	public String carType;
	public String carNumber ;
	public String free_for_clean ;//o if the car is not clean 
	public String free_for_refuel ;//0 if the car is not refuel
	
	public car(){
		this.carType = "";
		this.carNumber = ""; 
		this.free_for_clean = "" ;
		this.free_for_refuel ="" ;
	}	
	
	//set
	public void setcarType(String s) {this.carType = s;}
	public void setcarNumber(String num) {this.carNumber=num ;}
	public void set_free_for_clean(String s) {this.free_for_clean = s;}
	public void set_free_for_refuel(String s) {this.free_for_refuel = s;}
	
	//get
	public String getcarType() {return this.carType ;}
	public String getcarNumber() {return this.carNumber;}
    public String getfree_for_clean() {return this.free_for_clean ;}
    public String getfree_for_refuel() {return this.free_for_refuel ;}
}

// the main class the include threads
public class MyClass extends Thread {
    public List<String> l ; 
    public static int numofclean ;
    public static int numofrefuel ;
    
	MyClass( List<String> name) {
		this.l = name ;
		this.numofclean = 0 ;
		this.numofrefuel = 0 ;}
	public synchronized void run() {
		 String num,s ,news="", chelp = "";
		 
	     try {
	    	 for(int i=0; i< l.size();i++) {
	    		 s = l.get(i);
	    		 num = s.charAt(0)+""+s.charAt(1) ;
	    	  	 for (int j=2 ; j< s.length() ;j++) { //in this for i get the car type and witch state the choose
	    	  		 if(s.charAt(j) != '_') {
		    			 news+= s.charAt(j);  
		    		}else {
		    			chelp = ""+s.charAt(j+1);
		    			break ;  
		    		}
	    	  	 }
	    		 if(chelp.equals("r")) { // i check here if the car wait for refueling 
	    			 if(news.equals("M.cycle") ){
	    				 System.out.println("Vehicle " +num+"  starts refueling  in time  "+numofrefuel);
	    				 addnumtorefuelnum(1);
	    			  }
	    			 if(news.equals("private")){
	    			     System.out.println("Vehicle " +num +"  starts refueling  in time  "+numofrefuel);  
	    			     addnumtorefuelnum(3);	     
	    			  }
	    			 if(news.equals("Trailer")){
	    				 System.out.println("Vehicle " +num +"  starts refueling  in time  "+numofrefuel);
	    				 addnumtorefuelnum(5);	
	    			  }	        		      			 
	               Thread.sleep(numofrefuel); 	    			 
	    		 }
	    			 if(chelp.equals("c")) { // i check here if the car wait for cleanling 
	    				 if(news.equals("M.cycle") ){
		    			     System.out.println("Vehicle " +num+"  starts cleaning in time  "+numofclean);
		    			     addnumtocleannum(2);	        
		    		    }
			    		 if(news.equals("private")){
			    		     System.out.println("Vehicle " +num+"  starts cleaning in time  "+numofclean);  
			    		     addnumtocleannum(4);	     
			    		}	
		    		    if(news.equals("Trailer")){
		    			    System.out.println("Vehicle " +num +"  starts cleaning in time  "+numofclean);
		    			    addnumtocleannum(6);		       
		    		    }
	        		  
		           }
		           Thread.sleep(numofclean); 
	        	   news =""; 
	    	 }
	    	 
	     }catch(InterruptedException e) { e.printStackTrace(); } 
   }

	
    public void addnumtocleannum(int n) {this.numofclean = this.numofclean+ n ;}
    
    public void addnumtorefuelnum(int n) {this.numofrefuel= this.numofrefuel+ n ;}
    
	public static boolean resultIfTheCarWantARefuel(String s) { // return true if the car want to refueling 
		if(s.isEmpty()) {return 0==1;}
	    char c = s.charAt(s.length()-1);
	    return (c == 'r' || c == 'R') ; 
    }
    public static boolean resultIfTheCarWantAClean(String s) {// return true if the car want to cleanling 
    	if(s.isEmpty()) {
    		System.out.println("the input is not exist") ;}
    	char c = s.charAt(s.length()-1);
    	return (c == 'c' || c == 'C') ; 
    }
    public static List<String> UpDtaeReplace(List<String> l ){ // here i distance the functions for the same car
    	List<String> newl = l;                                 //and order the states 
    	List<String> nl = new ArrayList<String>() ;
    	int j = 0 ,len , len2;
    	String fw ,h  ;
    	boolean flag ;
    	for(int i=0 ; i < newl.size();i++) {
    		fw = newl.get(0);
    		newl.remove(0) ;
    		nl.add(fw);
    		len2 =fw.length()-1 ;
    		flag = false ;
    		
    		if(fw.charAt(len2) == 'r'){
    			for(j=i; j<newl.size() && !flag ; j++) {
    				h = newl.get(j);
    				len = h.length()-1 ;
    				if(( !numberEquals(fw,h) && h.charAt(len) == 'c' )){
    					nl.add(h); 
    					newl.remove(j);
    					flag = true ;
    					i--;
    				}
    			}
    		}
    		if(fw.charAt((fw.length()-1)) == 'c'){
    			for(j=i ; j<newl.size() && !flag ; j++) {
    				h = newl.get(j);
    				len = h.length()-1 ;
    				if(h.charAt(len) == 'r' && !numberEquals(fw,h) ){
    					nl.add(h);
    					newl.remove(j);
    					flag = true ;
    					i--;
    				}
    			}
    		}
    	}
 		if(!newl.isEmpty()) {
			for(int t=0;t<newl.size();t++) {
				nl.add(newl.get(t));
				newl.remove(t);
				t--;
			}
		}
	
    	return nl ;
    }
    public static boolean numberEquals(String s1 , String s2) { // return true if the car number is the same
    	String c1,c2;
    	c1 =s1.charAt(0)+""+s1.charAt(1) ;
		c2 =s2.charAt(0)+""+s2.charAt(1) ;
		return (Integer.parseInt(c1))== (Integer.parseInt(c2)) ;
    }
    public static void printList(List<String> l) {// this function print the list 
    	for(int i=0;i<l.size();i++) {
    		System.out.print(" " + l.get(i)+ "  ");}
    	System.out.println();
    }
    public static void InsertFunc() {// a input function 
    	Scanner scanIn = new Scanner(System.in);
		System.out.println("please Enter the amount of cars : ");
		int num = scanIn.nextInt() ;
		car[] cars= new car[num];
 
		System.out.println("please Enter the information ");
		for(int i=0; i< cars.length ; i++) {
			System.out.println("please Enter car number :\n");
            cars[i].setcarNumber(scanIn.nextLine());
            
			System.out.println("choose the car Type :(M.cycle,Trailer,Private)\n"
					         + "Enter this here: ");

			cars[i].setcarType(scanIn.nextLine());	
				
			System.out.println("please Enter R if the car need a Fuel refill else enter x :\n");
			cars[i].set_free_for_refuel(scanIn.nextLine());
			
			System.out.println("please Enter c if the car need to do washing else enter x :\n");
			cars[i].set_free_for_clean(scanIn.nextLine());
			
		
			
		}
		
    }
	public static  void main(String[] args)  {
 		car[] cars= new car[15];
		for(int i=0 ; i< cars.length ; i++) {
			cars[i] = new car() ;
		}
		cars[0].setcarNumber("00");
		cars[0].setcarType("M.cycle");
		cars[0].set_free_for_clean("c");
		cars[0].set_free_for_refuel("r");
		
		cars[1].setcarNumber("01");
		cars[1].setcarType("Trailer");
		cars[1].set_free_for_refuel("r");
		
		cars[2].setcarNumber("02");
		cars[2].setcarType("M.cycle");
		cars[2].set_free_for_clean("c");
		cars[2].set_free_for_refuel("r");
		
		cars[3].setcarNumber("03");
		cars[3].setcarType("private");
		cars[3].set_free_for_clean("c");
		
		cars[4].setcarNumber("04");
		cars[4].setcarType("private");
		cars[4].set_free_for_clean("c");
		
		cars[5].setcarNumber("05");
		cars[5].setcarType("private");
		cars[5].set_free_for_refuel("r");
		
		cars[6].setcarNumber("06");
		cars[6].setcarType("private");
		cars[6].set_free_for_refuel("r");
		
		cars[7].setcarNumber("07");
		cars[7].setcarType("M.cycle");
		cars[7].set_free_for_refuel("r");
		
		cars[8].setcarNumber("08");
		cars[8].setcarType("M.cycle");
		cars[8].set_free_for_refuel("r");
		
		cars[9].setcarNumber("09");
		cars[9].setcarType("private");
		cars[9].set_free_for_clean("c");
		cars[9].set_free_for_refuel("r");
		
		cars[10].setcarNumber("10");
		cars[10].setcarType("Trailer");
		cars[10].set_free_for_clean("c");
		
		cars[11].setcarNumber("11");
		cars[11].setcarType("M.cycle");
		cars[11].set_free_for_refuel("r");
		
		cars[12].setcarNumber("12");
		cars[12].setcarType("private");
		cars[12].set_free_for_refuel("r");

		cars[13].setcarNumber("13");
		cars[13].setcarType("Trailer");
		cars[13].set_free_for_clean("c");
		cars[13].set_free_for_refuel("r");
		

		cars[14].setcarNumber("14");
		cars[14].setcarType("private");
		cars[14].set_free_for_refuel("r");
	 
		//you can use the function InsertFunc if you want to input the information
		//InsertFunc() ; 
		
		String help = "";
		List<String> l = new ArrayList<String>();
		for(int i=0 ; i<cars.length; i++) {
			if((cars[i].getfree_for_refuel()).equals("r") || (cars[i].getfree_for_refuel()).equals("R")) {
				help =cars[i].getcarNumber()+""+cars[i].getcarType()+"_"+cars[i].getfree_for_refuel() ;
				l.add(help);
			}	
			if((cars[i].getfree_for_clean()).equals("c") || (cars[i].getfree_for_clean()).equals("C")) {
				help =cars[i].getcarNumber()+""+cars[i].getcarType()+"_"+cars[i].getfree_for_clean();	
				l.add(help);
		    }
			help = "";
		}
		
		List<String> newl = UpDtaeReplace(l);
		MyClass t1 = new MyClass(newl);// run the thread
		t1.start();	
	}
}

