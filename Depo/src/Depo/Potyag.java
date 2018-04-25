package Depo;

import java.io.Serializable;
import java.util.ArrayList;
//import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "potyag")
public class Potyag implements Serializable {
	
	@DatabaseField(persisted = false)
	private static final long serialVersionUID = 1L;
	
	@DatabaseField(persisted = false)
	private List <Vagon> potyag = new LinkedList<Vagon> ();
	
	@DatabaseField(persisted = false)
	private Vagon vagon;
	
	@DatabaseField(generatedId = true) 
	private  int idPotyaga;
	
	@DatabaseField(index = true)
	private int nomerPotyga;
	
	@DatabaseField()
	private int maxNomerPotyaga;
	
	@DatabaseField()
	private int currentPosition=0;
	
	@DatabaseField()
	private int lichilnikSt=0;
	
	@DatabaseField()
    final  int dlinaPotyga=100;
   
	@DatabaseField(persisted = false)
    private Enum station; 
   
    @DatabaseField(persisted = false)
    private Mashinist mashinist;
    
    @DatabaseField(canBeNull = false)
    private String mashinistKey;
    
    @DatabaseField()
    private String depoKey;

    @DatabaseField()
    private String lineKey;
    
    @DatabaseField()
    private String stationKey;
    
 Potyag (){}//pustoj dlya ormlite
 //konstruktor potyaga z vagoniv ne z depo
 Potyag ( int nomerP, Enum st){
	if (nomerP>0){
	for (int i=1; i<=5; i++){
		if ((1==i)||(5==i)){
		    vagon=new Vagon (i+5*(nomerP-1),"VagonMashinist"); 
		    vagon.setEmnistVagona(210);
		} 
		else {
			vagon=new Vagon (i+5*(nomerP-1),"VagonPasagir");
			vagon.setEmnistVagona(220);
		}
		vagon.setNomerPotyagaKey(nomerP);
		vagon.setNazvaDepoKey("DodanVPotyag");
		potyag.add(vagon);
	    }
	    nomerPotyga=nomerP;
	    station=st;
	    stationKey=this.getStation().name();
	    mashinist=new Mashinist ("Jon Dow","0000000000");
	    mashinistKey=mashinist.getPIB();
    }
	else System.out.println("kilkistVagoniv ta/abo nomerPotyga < 0");
}
 //konstruktor potyaga 5 vagoniv=MPPPM z vagoniv danogo depo
 Potyag(Depo d){
if ((d.getKilkistVMDepo()<2) &&
	 ((d.getKilkistVPDepo()+d.getKilkistVMDepo())<5))
	System.out.println("V depo VagonMashinist<2 abo (VagonMashinist+VagonPasagir)<5");
	
else {  //poshuk maxNomerPotyaga v depo
		 for(Object ob:d.getDepo()){
			 if (ob instanceof Potyag){Potyag p=(Potyag)ob;
			 if (maxNomerPotyaga<p.nomerPotyga){maxNomerPotyaga=p.nomerPotyga;}
			 }}
		 nomerPotyga=maxNomerPotyaga+1;	
		 int lichilnik=0;
		 for(Object ob:d.getDepo()){
		     if (ob instanceof Vagon){ Vagon v=(Vagon)ob;
             if (v.getNazvaVagona().equalsIgnoreCase("VagonMashinist")) {
            	 if (0==lichilnik) {
            		 potyag.add(0,v); 
            		 v.setNomerPotyagaKey(nomerPotyga);
            		 v.setNazvaDepoKey("DodanVPotyag");	 
            	 }
                  	 lichilnik++;
 }}
		     if (1==lichilnik) break;   
}
		 for(Object ob:d.getDepo()){
		     if (ob instanceof Vagon){ Vagon v=(Vagon)ob;
		     if (v.getNazvaVagona().equalsIgnoreCase("VagonPasagir")&&
                ((lichilnik>0)&&(lichilnik<4))){
		    	 potyag.add(lichilnik,v); 
		    	 v.setNomerPotyagaKey(nomerPotyga);
		    	 v.setNazvaDepoKey("DodanVPotyag");
            	 lichilnik++;
}}
		     if (4==lichilnik) break;
}
potyag.forEach(v-> d.getDepo().remove(v));

for(Object ob: d.getDepo()){
    if (ob instanceof Vagon){ Vagon v=(Vagon)ob;
    if (v.getNazvaVagona().equalsIgnoreCase("VagonMashinist")) {
   	 if (4==lichilnik) {
   		 potyag.add(4,v); 
   		 v.setNomerPotyagaKey(nomerPotyga);
   		 v.setNazvaDepoKey("DodanVPotyag");	 
   	 }
         	 lichilnik++;
}}
    if (5==lichilnik) break;   
}
    d.getDepo().remove(potyag.get(4));
    station=d.getNazvaDepo(); 
    stationKey=d.getNazvaDepo().name();
	d.setKilkistVPDepo(d.getKilkistVPDepo()-3);
	d.setKilkistVMDepo(d.getKilkistVMDepo()-2);
	mashinist=new Mashinist ("Jon Dow","0000000000");
	mashinistKey=mashinist.getPIB();
}}
 
//klonuvannya potyaga
 Potyag (Potyag pot){this (pot.getNomerPotyga(), pot.getStation());}

int getNomerPotyga(){return nomerPotyga;}
int getMaxNomerPotyaga(){return maxNomerPotyaga;}
Enum getStation (){return station;}
List <Vagon> getPotyg (){return potyag;}
Mashinist getMashinist(){return mashinist;}
int getCurentPosition (){return currentPosition;}
int getLichilnikSt (){return lichilnikSt;}
String getMashinistKey(){return mashinistKey;}
String getDepoKey(){return depoKey;}
String getLineKey(){return lineKey;}
String getStationKey(){return stationKey;}

void setStation(Enum st){station=st;}
void setMashinist(Mashinist m){mashinist=m; mashinistKey=m.getPIB();}
void setNomerPotyga (int nP){nomerPotyga=nP;}
void setCurrentPosition (int cP){currentPosition=cP;}
void setLichilnikSt (int lSt){lichilnikSt=lSt;}
//setMashinistKey vidsutnij - annotaziya
void setMashinistKey (String mk){mashinistKey=mk;}
void setDepoKey(String dk){depoKey=dk;}
void setLineKey(String lk){lineKey=lk;}
void setStationKey(String ss){stationKey=ss;}
//metod v 1 potok zavantague pasagiriv v potyag (vo vsi 5 vagoniv)
//ta pribirae zich pasagiriv z pereliku pasagiriv na vxode
void vxidPasagiriv(List <Pasagir> arrayListPasagir){
	List <Pasagir> pasTMP = new ArrayList<Pasagir> ();
	if (!arrayListPasagir.isEmpty()) {
	for (Pasagir pas:arrayListPasagir) {
		//emnist potyaga = 2*210+3*220=1080 pasagiriv
		if(getPotochnaKilkistPasagiriv()  >=
		  (2*getPotyg().get(0).getEmnistVagona() +
		   3*getPotyg().get(1).getEmnistVagona())) 
			break;
		if (pas!=null) {
	       for (Vagon v: this.getPotyg()) {
               if (v.setPasigirVagon(pas)) { 
            	   pasTMP.add(pas);
                   break;
               }}}}	
	if (!pasTMP.isEmpty()) arrayListPasagir.removeAll(pasTMP); 
}}
//metod v 5 potokov zavantague pasagiriv v potyag (vo vsi 5 vagoniv)
//ta pribirae zich pasagiriv z pereliku pasagiriv na vxode
void vxidPasagiriv5Potokov (List <Pasagir> pasagiri){
	if (!pasagiri.isEmpty()) {
		for (int i=0; i<5; i++) {
			final Vagon vagon = potyag.get(i);
			final int j = i;
			final int d15= (pasagiri.size()/5);
		
			new Thread (()-> {try { 
					int pochatok = (d15*j-1)==-1? 0 : (d15*j-1);
					int kinez = d15*(j+1)-1;
					CopyOnWriteArrayList <Pasagir> pasagiriTMP = new CopyOnWriteArrayList <Pasagir> (pasagiri.subList(pochatok, kinez));
					//dodau ostanjogo pasagira (subList ne mistit pasagiriTMP[kinez])
					if (j==4) pasagiriTMP.add(pasagiri.get(pasagiri.size()-1));	
						
				    for (Pasagir pas: pasagiriTMP) {
					    if (vagon.setPasigirVagon(pas)) { 	
						System.out.println("Potyag "+ getNomerPotyga() + " Vagon "+ vagon.getNomerVagona()+ " zajchov " + pas.getPIB());
						Thread.sleep(2);//new Random().nextInt(5)
					}
					    else break;
					}
				    synchronized (Vestibul.pasagiriPeron) { Vestibul.pasagiriPeron.removeAll(vagon.getPasigirVagon());}
				//Thread.sleep( new Random().nextInt(1));
			     } catch (InterruptedException ie) {ie.printStackTrace();}  
				System.out.println("Potok "+Thread.currentThread().getName()+" Vagon " +vagon.getNomerVagona() +" zakinchen");
				}).start();	
		}}}
//metod v 5 potokov zavantague pasagiriv v potyag (vo vsi 5 vagoniv)
//ta pribirae zich pasagiriv z pereliku pasagiriv perona
void vxidPv5Potokov (StationV station, List <Pasagir> pasagiri){
	if (!pasagiri.isEmpty()) {
		
		Thread potokVxid5 = new Thread (()-> {	
		synchronized (station.getPasagiriPeron()) {
		//List <Pasagir> pasagiriTMP = new ArrayList<Pasagir>();		
		CopyOnWriteArrayList <Pasagir> pasagiriZajchli = new CopyOnWriteArrayList<Pasagir>();
		List <Thread> potokiVagon = new ArrayList<> ();
		int d15 = (int)(pasagiri.size()/5);
		//int zalichok = (pasagiri.size()%5);
		
		for (int i=0; i<5; i++) {
			final Vagon vagon = this.potyag.get(i);		
			final  CopyOnWriteArrayList <Pasagir> pasagiriVagonTMP;
			
			if (!pasagiri.isEmpty()) {
				if(0!=d15) {
			      int pochatok = (d15*i-1) ==-1? 0 : (d15*i-1);
			      int kinez = (d15*(i+1)-1) ==-1? 0: (d15*(i+1)-1);	
			      System.out.println("i="+i+" pasagiri.size()="+pasagiri.size()+" d15="+ d15 + " pochatok=" + pochatok + " kinez=" + kinez);
			      pasagiriVagonTMP = new CopyOnWriteArrayList<Pasagir>(pasagiri.subList(pochatok, kinez));	   
			//dodau ostanjogo pasagira (subList ne mistit pasagiriTMP[kinez]) ta zalichok.
			   if (4==i)  for(int k = kinez; k<pasagiri.size(); k++) {pasagiriVagonTMP.add (pasagiri.get(k));}
				} else {
					i=5; //vixid z ziklu
					pasagiriVagonTMP = new CopyOnWriteArrayList<>();
				    pasagiriVagonTMP.addAll(pasagiri);    
				}
				Thread potokVagon=new Thread (()-> {try { 
			   for (Pasagir pas: pasagiriVagonTMP) {
				   synchronized (vagon.getPasigirVagon()) {
				    if (vagon.setPasigirVagon(pas)) { 
				    	pasagiriZajchli.add(pas);			    		
						System.out.println("Potyag "+ getNomerPotyga() + " Vagon " + vagon.getNomerVagona() + " zajchov " + pas.getPIB());
						Thread.sleep(2);//new Random().nextInt(5)    
					}		    		
					    else break;
				    }}
				    } catch (InterruptedException ie) {ie.printStackTrace();}  
				System.out.println("PotokVxid "+Thread.currentThread().getName()+" Vagon " +vagon.getNomerVagona() +" zakinchen");
				});
			potokVagon.start();
			potokiVagon.add(potokVagon);
				
				}
			
			else break;
		}
		//po zakinchennu 5 potokiv vidalyau z pasagiriPeron pasagiriv yaki zajchli
		potokiVagon.forEach(potokVagon->{try {potokVagon.join();} catch (InterruptedException ie) {ie.printStackTrace();}});
		station.getPasagiriPeron().removeAll(pasagiriZajchli);
				}
		
		});
		potokVxid5.start();
		}}
//metod v 5 potokov vigrugae pasagiriv z potyaga (zo vsix 5 vagoniv) v Vixid.pasagiriVixid 
void vixidV5Potokov (){
	if (!this.getVsixPasagirivPotyaga().isEmpty()) {
		
		Thread potokVixid5=new Thread (()-> {
	    List <Thread> potokiVixid = new ArrayList<> ();
	    CopyOnWriteArrayList <Pasagir> pasagiriTMP = new CopyOnWriteArrayList<>();
		for (int i=0; i<5; i++) {
			final Vagon vagon = this.potyag.get(i);	
			
			Thread potokVixid = new Thread (()-> {try { 
				synchronized (vagon.getPasigirVagon()) {
				for (Pasagir pas: vagon.getPasigirVagon())  {
				    //kinzeva Station (LineT/LineN) - vixid vsix pasagiriv
					//currentPosition=54,114 - RedLine, currentPosition=43,92 - BlueLine 
					if( (54 == this.currentPosition) || //&& (this.getLineKey().equals("Red")))  
				        (114== this.currentPosition) || //&& (this.getLineKey().equals("Red"))   
				        (43 == this.currentPosition) || //&& (this.getLineKey().equals("Blue"))
				        (92 == this.currentPosition) || //&& (this.getLineKey().equals("Blue")))
				        (44 == this.currentPosition) || //&& (this.getLineKey().equals("Green")))
				        (94 == this.currentPosition)) { //&& (this.getLineKey().equals("Green")))
				    	synchronized (Vixid.pasagiriVixid) {Vixid.pasagiriVixid.add(pas);}
				        pasagiriTMP.add(pas);
					    System.out.println("Potyag "+ getNomerPotyga() + " Vagon "+ vagon.getNomerVagona()+" kinzeva " + pas.getPIB()+" currentPosition "+this.currentPosition+" LineKey " + this.getLineKey());
					    Thread.sleep(2);//new Random().nextInt(5)
				    }	
					//ne kizeva Station (LineT/LineN) - vixid 30% pasagiriv
				    else if(new Random().nextInt(100) <= 33) {
				    	  synchronized (Vixid.pasagiriVixid) {Vixid.pasagiriVixid.add(pas);}
					      pasagiriTMP.add(pas);
						  System.out.println("Potyag "+ getNomerPotyga() + " Vagon "+ vagon.getNomerVagona()+ " vijchov " + pas.getPIB()+" currentPosition "+this.currentPosition+" LineKey " + this.getLineKey());
						  Thread.sleep(2);//new Random().nextInt(5)
					}}
				    vagon.getPasigirVagon().removeAll(pasagiriTMP);
				 }
			     } catch (InterruptedException ie) {ie.printStackTrace();}  
				System.out.println("Potok "+Thread.currentThread().getName()+" Vagon " +vagon.getNomerVagona() +" zakinchen");
				});
			potokVixid.start();
			potokiVixid.add(potokVixid);	
		}
		//po zakinchennu 5 potokiv vidalyau z vagoniv pasagiriv yaki vijchli
		//potokiVixid.forEach(p->{try {p.join();} catch (InterruptedException ie) {ie.printStackTrace();}});
		//this.potyag.forEach(v -> v.getPasigirVagon().removeAll(pasagiriTMP));
		});
		potokVixid5.start();
		}}

//metod povertae potochnu kilkist pasagiriv v potyazi
int getPotochnaKilkistPasagiriv () {
	int pkp=0;
	for (Vagon v: this.getPotyg()) {pkp+=v.getPasigirVagon().size();}
		return pkp;
}
//metod povertae vsix pasagiriv v potyazi yak ArrayList
List <Pasagir> getVsixPasagirivPotyaga(){
	List <Pasagir> pasTMP = new ArrayList<Pasagir> ();
	for (Vagon v: this.getPotyg()) {
		pasTMP.addAll(v.getPasigirVagon());
		//v.getPasigirVagon().clear(); //vidalenny pasagiriv ne zavgdi potribno 
		}
	return pasTMP;
}

public static void main(String[] args) {
	//1. Создайте классы Вагон, Поезд	
	Potyag potyag1= new Potyag(1,StationL1.Station11);
	Potyag potyag2= new Potyag(potyag1);
	Potyag potyag3= new Potyag(3,StationL3.Station31);
	
	if (("Revers"!=potyag1.getStation().name())&&
		("VDorozi"!=potyag1.getStation().name())){
	StationL1.valueOf(potyag1.getStation().name()).setPotochnaKPas(1000);
	}
	
	for(Vagon v:potyag1.potyag){System.out.println(potyag1.nomerPotyga
			+" "+v.getNazvaVagona()+ " "+v.getNomerVagona()+" "+potyag1.hashCode()+" "+potyag1.getStation());}
	System.out.println();
	for(Vagon v:potyag2.potyag){System.out.println(potyag2.nomerPotyga+
			" "+v.getNazvaVagona()+ " "+v.getNomerVagona()+" "+potyag2.hashCode()+" "+potyag1.getStation());}
	System.out.println();
	for(Vagon v:potyag3.potyag){System.out.println(potyag3.nomerPotyga+
			" "+v.getNazvaVagona()+ " "+v.getNomerVagona()+" "+potyag3.hashCode()+" "+potyag1.getStation());}
	System.out.println();
		
		potyag2.nomerPotyga=2;
		potyag2.setStation(StationL2.Station21);
		int n=1;
		for(Vagon v:potyag2.potyag) {v.setNomerVagona(5+n);n++;}
		
		for(Vagon v:potyag1.potyag){System.out.println(potyag1.nomerPotyga+" "+v.getNazvaVagona()+ " "+v.getNomerVagona());}
		System.out.println();
		for(Vagon v:potyag2.potyag){System.out.println(potyag2.nomerPotyga+" "+v.getNazvaVagona()+ " "+v.getNomerVagona());}
		System.out.println();
		for(Vagon v:potyag3.potyag){System.out.println(potyag3.nomerPotyga+" "+v.getNazvaVagona()+ " "+v.getNomerVagona());}
		System.out.println();
		
		System.out.println(potyag1.nomerPotyga+" "+potyag1.potyag.get(1).getNomerVagona()+ " "+potyag1.hashCode());
		System.out.println(potyag2.nomerPotyga+" "+potyag2.potyag.get(1).getNomerVagona()+ " "+potyag2.hashCode());
		System.out.println(potyag3.nomerPotyga+" "+potyag3.potyag.get(1).getNomerVagona()+ " "+potyag3.hashCode());
		
}
}


