package Depo;

import java.util.concurrent.CopyOnWriteArrayList;

public class EskalatorV implements Runnable {
	private CopyOnWriteArrayList <Pasagir> pasagiriEskalator =  new CopyOnWriteArrayList <Pasagir> ();
	private String nazvaEskalatora;
	private int TimeProizdu;
	private final StationV station;
	
	EskalatorV (String nazvaEskalatora, int timeProizdu, StationV station) {
		super();
		this.nazvaEskalatora = nazvaEskalatora;
		this.TimeProizdu = timeProizdu;
		this.station=station;
	}
	public CopyOnWriteArrayList <Pasagir> getEskalator() {return pasagiriEskalator;}
	public void setEskalator(CopyOnWriteArrayList <Pasagir> pas) {this.pasagiriEskalator = pas;}
	public String getNazvaEskalatora() {return nazvaEskalatora;}
	public void setNazvaEskalatora(String nazvaEskalatora) {this.nazvaEskalatora = nazvaEskalatora;}
	
	public void run() {
     int i=1;// Vestibul.kilkistPasagiriv+1
try{ while (true) {				
	    i++;
	    if (1 == station.getPraporKinezGeneraziiPasagiriv()) break;
	    
	    synchronized (station.getPasagiriVestibul()) {
	    if (station.getPasagiriVestibul().isEmpty()) station.getPasagiriVestibul().wait();		
		this.getEskalator().addAllAbsent(station.getPasagiriVestibul());
	    //pasagiriEskalator.addAll(station.getPasagiriVestibul());		
		System.out.println(this.nazvaEskalatora+" "+pasagiriEskalator.toString());
		station.getPasagiriVestibul().removeAll(this.getEskalator());
		}
		Thread.sleep (TimeProizdu);//1400
		synchronized (station.getPasagiriPeron()) {station.getPasagiriPeron().addAll(this.getEskalator());}
		this.getEskalator().clear();	    		
		}}			
catch (InterruptedException ie){ ie.printStackTrace();}	
System.out.println(this.nazvaEskalatora+" Potok "+Thread.currentThread().getName()+ " zakinchen.");	
	}}
