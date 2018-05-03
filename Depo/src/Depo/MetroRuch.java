package Depo;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.PriorityBlockingQueue;

import javax.swing.JButton;

public class MetroRuch {

SchemaMetro schemaMetro;
MetroRuch (SchemaMetro schemaMetro) {this.schemaMetro = schemaMetro;}
Line[] lines =new Line[3];

static List <StationV> stationsRed = new ArrayList<>();
static List <StationV> stationsBlue = new ArrayList<>();
static List <StationV> stationsGreen = new ArrayList<>();
static List <Thread> potoki = new ArrayList<> ();
static CopyOnWriteArrayList <Poizdka> poizdki = new CopyOnWriteArrayList<>();//poizdka=date,time,line,mashinist

public Line [] getlines(){return lines;}
public static List<StationV> getStationsRed() {return stationsRed;}
public static List<StationV> getStationsBlue() {return stationsBlue;}
public static List <Thread> getPotoki () {return potoki;}

public static Comparator <Mashinist> comparatorMashinist = new Comparator <Mashinist>(){ 
	@Override//sortuvannya v zvorotnomu napryamku 
	public int compare (Mashinist m1, Mashinist m2) {
	if (m1.getDosvid() > m2.getDosvid()) return -1;
	if (m1.getDosvid() < m2.getDosvid()) return 1;
	return 0;
	}};
public static PriorityBlockingQueue <Mashinist>  driversRed = new PriorityBlockingQueue <>(100, comparatorMashinist);
public static PriorityBlockingQueue <Mashinist>  driversRedTMP = new PriorityBlockingQueue <>(100, comparatorMashinist);
public static PriorityBlockingQueue <Mashinist>  driversBlue = new PriorityBlockingQueue <>(100, comparatorMashinist);
public static PriorityBlockingQueue <Mashinist>  driversBlueTMP = new PriorityBlockingQueue <>(100, comparatorMashinist);
public static PriorityBlockingQueue <Mashinist>  driversGreen = new PriorityBlockingQueue <>(100, comparatorMashinist);
public static PriorityBlockingQueue <Mashinist>  driversGreenTMP = new PriorityBlockingQueue <>(100, comparatorMashinist);

public static int praporKilkostiIS=0; 

Line[] metroRuch(){
	Depo depo1=new Depo (18,12, NazvaDepo.DepoDarniza);
	//Stvoruu potygi z vagoni ne depo				
	 Potyag potyag1= new Potyag(1,NazvaDepo.DepoDarniza); 		 
	 Potyag potyag2= new Potyag(2,NazvaDepo.DepoGeroivDnipra);
	 Potyag potyag3= new Potyag(3,NazvaDepo.DepoDarniza);
//priznachiv Mashinistiv
	 potyag1.setMashinist(new Mashinist("Ivanenko", "1111111111"));
	 potyag2.setMashinist(new Mashinist("Petrenko", "2222222222"));
	 potyag3.setMashinist(new Mashinist("Kozak", "4444444444"));
	 Mashinist xXX=new Mashinist("XXX", "XXXXXXXXXX");
//Dodau potyagi v depo 
	 depo1.dodatVdepo(potyag1);
	 depo1.dodatVdepo(potyag2);
	 depo1.dodatVdepo(potyag3);
//Stvoruu potygi z vagoni depo
	 Potyag potyag4= new Potyag(depo1);potyag4.setMashinist(new Mashinist("Sidorenko", "3333333333"));
	 depo1.dodatVdepo(potyag4);
	 Potyag potyag5= new Potyag(depo1);potyag5.setMashinist(xXX);
	 depo1.dodatVdepo(potyag5);
	 Potyag potyag6= new Potyag(depo1);potyag6.setMashinist(new Mashinist("Averchenko", "1023456789"));
	 depo1.dodatVdepo(potyag6);
	 Potyag potyag7= new Potyag(depo1);potyag7.setMashinist(new Mashinist("Bogdanenko", "1203456789"));
	 depo1.dodatVdepo(potyag7);
	 Potyag potyag8= new Potyag(depo1);potyag8.setMashinist(new Mashinist("Golovatenko", "1230456789"));
	 depo1.dodatVdepo(potyag8);
	 //stvoruu kimnatu ochikuvannya mashinistov RED Line
	 for (int i=0; i<8; i++){
		 Mashinist m = new Mashinist("PibRedWait_"+i, "123456789"+i, i);
		 driversRed.add(m);
	 }
	 
	 Line redLine1=new Line(1);
	 //stvoruu 4 station, kogna z 3 eskalatorami
	 for (int i=1; i<=VidstanMigSt1.values().length; i++) {
			StationV station = new StationV (redLine1.getNazvaLinii()+"_Station_1"+i);
			stationsRed.add(station);
			//generaziya pasagiriv, zapusk 3-x eskalatorov
			zapuskGeneraziiPasagirivTa3Eskalatora (station);
		 }		 
	 redLine1.vipuskNaLiniu(potyag1, potyag2, potyag3, potyag4, potyag5, potyag6, potyag7, potyag8 );
     depo1.getDepo().removeAll(redLine1.getPotyagNaLinii());
     lines[0]=redLine1;    
//=========================================================================
	 Potyag [] potyagiBL2=new Potyag[8];
	 Depo depo2=new Depo (27,18, NazvaDepo.DepoGeroivDnipra);
	 for (int i=0; i<8; i++){
		 potyagiBL2[i] = new Potyag(depo2);
		 potyagiBL2[i].setMashinist(new Mashinist("PIB_Blue"+i, "123456789"+i));
		 depo2.dodatVdepo(potyagiBL2[i]);
	 }
	//stvoruu kimnatu ochikuvannya mashinistov Blue Line
		 for (int i=0; i<8; i++){
			 Mashinist m = new Mashinist("PibBlueWait_"+i, "123456789"+i, i);
			 driversBlue.add(m);
		 }
	 Line blueLine2=new Line(2);
	//stvoruu 4 station, kogna z 3 eskalatorami
		 for (int i=1; i<=VidstanMigSt2.values().length; i++) {
				StationV station = new StationV (blueLine2.getNazvaLinii()+"_Station_2"+i);
				stationsBlue.add(station);
				//generaziya pasagiriv, zapusk 3-x eskalatorov
				zapuskGeneraziiPasagirivTa3Eskalatora (station);
			 }	
	 blueLine2.vipuskNaLiniuM(potyagiBL2);
	 depo2.getDepo().removeAll(blueLine2.getPotyagNaLinii());
	 lines[1]=blueLine2;
	 
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 Potyag [] potyagiGL3=new Potyag[8];
	 Depo depo3=new Depo (27,18, NazvaDepo.DepoSirez);// stvoruu depo 27 pasVagoniv ta 18 MashinistVagoniv
	 for (int i=0; i<8; i++){
		 potyagiGL3[i] = new Potyag(depo3);//stvoruu potyag z vagoniv depo 
		 potyagiGL3[i].setMashinist(new Mashinist("PIB_Green"+i, "123456789"+i));//priznachau vashinista
		 depo3.dodatVdepo(potyagiGL3[i]); //dlya pravilnoj numerazii potyagiv
	 }
	//stvoruu kimnatu ochikuvannya mashinistov Green Line
		 for (int i=0; i<8; i++){
			 Mashinist m = new Mashinist("PibGreenWait_"+i, "123456789"+i, i);
			 driversGreen.add(m);
		 }
	 Line greenLine3=new Line(3);
	//stvoruu 4 station, kogna z 3 eskalatorami
		 for (int i=1; i<=VidstanMigSt3.values().length; i++) {
				StationV station = new StationV (greenLine3.getNazvaLinii()+"_Station_3"+i);
				stationsGreen.add(station);
				//generaziya pasagiriv, zapusk 3-x eskalatorov
				zapuskGeneraziiPasagirivTa3Eskalatora (station);
			 }	
	greenLine3.vipuskNaLiniuGreen(potyagiGL3);
	depo3.getDepo().removeAll(greenLine3.getPotyagNaLinii());
	lines[2]=greenLine3;	 
	 
	 return lines;
}

void metroStop (Line[] ln) { 
	
	if (ln.length > 0) {
		
		for(Line line: ln) {
			
			if (null!=line) line.getTimer().cancel();
		}}}

void zapuskGeneraziiPasagirivTa3Eskalatora (StationV st) {
	Thread potokGeneraziyaPasagiriv = new Thread(st);
	potokGeneraziyaPasagiriv.start(); potoki.add(potokGeneraziyaPasagiriv);
	Thread potok1 = new Thread(new EskalatorV ("Esk 1", 200, st));//1400
	potok1.start(); potoki.add(potok1);
	Thread potok2 = new Thread(new EskalatorV ("Esk 2", 200, st));//1800
	potok2.start(); potoki.add(potok2);
	Thread potok3 = new Thread(new EskalatorV ("Esk 3", 200, st));//1700
	potok3.start(); potoki.add(potok3);	
}

void formaIS () {
	if (0==praporKilkostiIS) {
		InfoStation fIS = new InfoStation();
		praporKilkostiIS=1;
	}
}

void showAllStation () {
	
	class InfoRed extends TimerTask {
		InfoRed(){}
		public void run() {    
		int k=0;
		for (StationV st: stationsRed) {
			InfoStation.setText(1, k+1, st.getKilkistPasagirivVestibul());
			k++;
		    }}}

	class InfoBlue extends TimerTask {
		InfoBlue(){}
		public void run() {    
		int k=0;
		for (StationV st: stationsBlue) {
			InfoStation.setText(1, k+5, st.getKilkistPasagirivVestibul());
			k++;
		    }}}
	class InfoGreen extends TimerTask {
		InfoGreen(){}    
		public void run() {    
		int k=0;
		for (StationV st: stationsGreen) {
			InfoStation.setText(1, k+9, st.getKilkistPasagirivVestibul());
			k++;
		    }}}
	
	int firstTime=0;
    int period=100;
    Timer timerRed=new Timer("TimerRed");
    timerRed.schedule(new InfoRed(), firstTime, period);
    Timer timerBlue=new Timer("TimerBlue");
    timerBlue.schedule(new InfoBlue(), firstTime, period);
    Timer timerGreen=new Timer("TimerGreen");
    timerGreen.schedule(new InfoGreen(), firstTime, period);
}

void showOneStation (JButton button) {}
}