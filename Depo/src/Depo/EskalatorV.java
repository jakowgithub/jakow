package Depo;

import java.util.ArrayList;
import java.util.List;

public class EskalatorV implements Runnable {
	List <Pasagir> pasagiriEskalator = new ArrayList<Pasagir> ();
	String nazvaEskalatora;
	int TimeProizdu;
	final StationV station;
	
	EskalatorV (String nazvaEskalatora, int timeProizdu, StationV station) {
		super();
		this.nazvaEskalatora = nazvaEskalatora;
		this.TimeProizdu = timeProizdu;
		this.station=station;
	}
	public List<Pasagir> getEskalator1() {return pasagiriEskalator;}
	public void setEskalator1(List<Pasagir> pas) {this.pasagiriEskalator = pas;}
	public String getNazvaEskalatora() {return nazvaEskalatora;}
	public void setNazvaEskalatora(String nazvaEskalatora) {this.nazvaEskalatora = nazvaEskalatora;}
	
	public void run() {
     int i=1;// Vestibul.kilkistPasagiriv+1
try{ while (true) {				
	    i++;
	    if (1 == station.getPraporKinezGeneraziiPasagiriv()) break;
	    
	    synchronized (station.getPasagiriVestibul()) {
	    if (station.getPasagiriVestibul().isEmpty()) station.getPasagiriVestibul().wait();		
		pasagiriEskalator.addAll(station.getPasagiriVestibul());		
		System.out.println(this.nazvaEskalatora+" "+pasagiriEskalator.toString());
		station.getPasagiriVestibul().removeAll(pasagiriEskalator);
		}
		Thread.sleep (TimeProizdu);//1400
		 synchronized (station.getPasagiriPeron()) {station.getPasagiriPeron().addAll(pasagiriEskalator);}
		//if (!StationV.pasagiriPeron.isEmpty()) synchronized (StationV.pasagiriPeron) {StationV.pasagiriPeron.notifyAll();}
		pasagiriEskalator.clear();		    		
		}}			
catch (InterruptedException ie){ ie.printStackTrace();}	
System.out.println(this.nazvaEskalatora+" Potok "+Thread.currentThread().getName()+ " zakinchen.");	
	}}
