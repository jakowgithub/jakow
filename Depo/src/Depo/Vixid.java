package Depo;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class Vixid {
	static Comparator <Pasagir> comparatorPasagir = new Comparator <Pasagir>(){ 
		@Override
		public int compare (Pasagir p1, Pasagir p2) {
		if (p1.getIdPasagir() > p2.getIdPasagir()) return 1;
		if (p1.getIdPasagir() < p2.getIdPasagir()) return -1;
		return 0;
		}};
	static PriorityBlockingQueue <Pasagir>  pasagiriVixid = new PriorityBlockingQueue <>(240000, comparatorPasagir);	
	
	static void drukPasagiriVixid () {
		
		try {
	    	MetroRuch.getPotoki().forEach(potok->{
	    		try {potok.join();
	    		    } catch (InterruptedException ie) {ie.printStackTrace();}});
	    	Thread.sleep(700);
	    	int i=1;
	    	while (!pasagiriVixid.isEmpty()) {System.out.println("pasagiriVixid " + pasagiriVixid.poll().getPIB()+ " N"+ i); i++;}
	    	//ZapisFile.saveFileDriver(pasagiriVixid, 3);
		   } catch (InterruptedException ie) {ie.printStackTrace();} 
	}
}
