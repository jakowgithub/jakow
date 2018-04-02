package Depo;

import java.util.ArrayList;
import java.util.List;

public class Eskalator implements Runnable {

	List <Pasagir> pasagiriEskalator = new ArrayList<Pasagir> ();
	String nazvaEskalatora;
	int TimeProizdu;
	
	Eskalator (String nazvaEskalatora, int timeProizdu) {
		super();
		this.nazvaEskalatora = nazvaEskalatora;
		this.TimeProizdu = timeProizdu;
	}
	public List<Pasagir> getEskalator1() {return pasagiriEskalator;}
	public void setEskalator1(List<Pasagir> pas) {this.pasagiriEskalator = pas;}
	public String getNazvaEskalatora() {return nazvaEskalatora;}
	public void setNazvaEskalatora(String nazvaEskalatora) {this.nazvaEskalatora = nazvaEskalatora;}
	
	public void run() {
     int i=1;// Vestibul.kilkistPasagiriv+1
try{ while (true){				
	    i++;
	    if (1 == Vestibul.praporKinezGeneraziiPasagiriv) break;
	    
	    synchronized (Vestibul.pasagiriVestibul) {
	    if (Vestibul.pasagiriVestibul.isEmpty()) Vestibul.pasagiriVestibul.wait();		
		pasagiriEskalator.addAll(Vestibul.pasagiriVestibul);		
		System.out.println(this.nazvaEskalatora+" "+pasagiriEskalator.toString());
		Vestibul.pasagiriVestibul.removeAll(pasagiriEskalator);
		}
		Thread.sleep (TimeProizdu);//1400
		Vestibul.pasagiriPeron.addAll(pasagiriEskalator);
		pasagiriEskalator.clear();		    		
		}}			
catch (InterruptedException ie){ ie.printStackTrace();}	
System.out.println(this.nazvaEskalatora+" Potok "+Thread.currentThread().getName()+ " zakinchen.");	
	}}
