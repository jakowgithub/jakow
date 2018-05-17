package Depo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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
     //vidalyau potygi yaki vijchli na liniu z depo
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

void zapuskGeneraziiPasagirivTa3Eskalatora (StationV station) {
	
	Thread potokGeneraziyaPasagiriv = new Thread(station);
	potokGeneraziyaPasagiriv.start(); potoki.add(potokGeneraziyaPasagiriv);
	
	EskalatorV esk1 = new EskalatorV ("Esk 1", 200, station); //1400
	Thread potok1 = new Thread(esk1);
	potok1.start(); 
	potoki.add(potok1);
	station.getEskalotori().add(esk1);
	
	EskalatorV esk2 = new EskalatorV ("Esk 2", 200, station); //1800
	Thread potok2 = new Thread(esk2); 
	potok2.start(); 
	potoki.add(potok2);
	station.getEskalotori().add(esk2);
	
	EskalatorV esk3 = new EskalatorV ("Esk 3", 200, station); //1700
	Thread potok3 = new Thread(esk3);
	potok3.start(); 
	potoki.add(potok3);	
	station.getEskalotori().add(esk3);
}

void formaIS () {
	if (0==praporKilkostiIS) {
		InfoStation fIS = new InfoStation();
		praporKilkostiIS=1;
	}
}
//metod vidobragae kilkist pasagiriv v rozrizi stanzij 
void showAllStation () {
		
	class SumaVestibulAll extends TimerTask {
		
		SumaVestibulAll(){}    
		
		int sumaVestibul=0, 
			sumaEsk1=0, 
			sumaEsk2=0, 
			sumaEsk3=0,
			sumaPeron=0; 
		
		public void run() {  
			//perebirau vsi stanzii
			for (int i=0; i<4; i++) {
				
				int vestibulStRed = stationsRed.get(i).getKilkistPasagirivVestibul();
				InfoStation.setText(1, i+1, vestibulStRed);
				int esk1StRed = stationsRed.get(i).getEskalotori().get(0).getKilkistPasagirivEskalatora();
				InfoStation.setText(2, i+1, esk1StRed);
				int esk2StRed = stationsRed.get(i).getEskalotori().get(1).getKilkistPasagirivEskalatora();
				InfoStation.setText(3, i+1, esk2StRed);
				int esk3StRed = stationsRed.get(i).getEskalotori().get(2).getKilkistPasagirivEskalatora();
				InfoStation.setText(4, i+1, esk3StRed);	
				int peronStRed = stationsRed.get(i).getKilkistPasagiriPeron();
				InfoStation.setText(5, i+1, peronStRed);
				
				int vestibulStBlue = stationsBlue.get(i).getKilkistPasagirivVestibul();
				InfoStation.setText(1, i+5, vestibulStBlue);
				int esk1StBlue = stationsBlue.get(i).getEskalotori().get(0).getKilkistPasagirivEskalatora();
				InfoStation.setText(2, i+5, esk1StBlue);
				int esk2StBlue = stationsBlue.get(i).getEskalotori().get(1).getKilkistPasagirivEskalatora();
				InfoStation.setText(3, i+5, esk2StBlue);
				int esk3StBlue = stationsBlue.get(i).getEskalotori().get(2).getKilkistPasagirivEskalatora();
				InfoStation.setText(4, i+5, esk3StBlue);
				int peronStBlue = stationsBlue.get(i).getKilkistPasagiriPeron();
				InfoStation.setText(5, i+5, peronStBlue);
				
				int vestibulStGreen = stationsGreen.get(i).getKilkistPasagirivVestibul();
				InfoStation.setText(1, i+9, vestibulStGreen);
				int esk1StGreen = stationsBlue.get(i).getEskalotori().get(0).getKilkistPasagirivEskalatora();
				InfoStation.setText(2, i+9, esk1StGreen);
				int esk2StGreen = stationsBlue.get(i).getEskalotori().get(1).getKilkistPasagirivEskalatora();
				InfoStation.setText(3, i+9, esk2StGreen);
				int esk3StGreen = stationsBlue.get(i).getEskalotori().get(2).getKilkistPasagirivEskalatora();
				InfoStation.setText(4, i+9, esk3StGreen);
				int peronStGreen = stationsGreen.get(i).getKilkistPasagiriPeron();
				InfoStation.setText(5, i+9, peronStGreen);
				
				sumaVestibul+=(vestibulStRed+vestibulStBlue+vestibulStGreen);
				sumaEsk1+=(esk1StRed+esk1StBlue+esk1StGreen);
				sumaEsk2+=(esk2StRed+esk2StBlue+esk2StGreen);
				sumaEsk3+=(esk3StRed+esk3StBlue+esk3StGreen);
				sumaPeron+=(peronStRed+peronStBlue+peronStGreen);
			}
			InfoStation.setText (1, 13, sumaVestibul);
			InfoStation.setText (2, 13, sumaEsk1);
			InfoStation.setText (3, 13, sumaEsk2);
			InfoStation.setText (4, 13, sumaEsk3);
			InfoStation.setText (5, 13, sumaPeron);
			InfoStation.setText (8, 13, Vixid.pasagiriVixid.size());
			
			/**
			getlines()[1].getPotyagNaLinii().forEach(potyag -> {
				
				switch (potyag.getCurentPosition()) {
			
			case 0  : InfoStation.setText (6, 5, potyag.getNomerPotyga()); break;
			case 18 : InfoStation.setText (6, 6, potyag.getNomerPotyga()); break;
			case 34 : InfoStation.setText (6, 7, potyag.getNomerPotyga()); break;
			case 43 : InfoStation.setText (6, 8, potyag.getNomerPotyga()); break;
			case 49 : InfoStation.setText (7, 5, potyag.getNomerPotyga()); break;
			case 58 : InfoStation.setText (7, 6, potyag.getNomerPotyga()); break;
			case 74 : InfoStation.setText (7, 7, potyag.getNomerPotyga()); break;
			case 92 : InfoStation.setText (7, 8, potyag.getNomerPotyga()); break; 
			}});
			getlines()[2].getPotyagNaLinii().forEach(potyag -> {
				
				switch (potyag.getCurentPosition()) {
			
			case 0  : InfoStation.setText (6, 9, potyag.getNomerPotyga());  break;
			case 11 : InfoStation.setText (6, 10, potyag.getNomerPotyga()); break;
			case 34 : InfoStation.setText (6, 11, potyag.getNomerPotyga()); break;
			case 44 : InfoStation.setText (6, 12, potyag.getNomerPotyga()); break;
			case 50 : InfoStation.setText (7, 9, potyag.getNomerPotyga());  break;
			case 60 : InfoStation.setText (7, 10, potyag.getNomerPotyga()); break;
			case 83 : InfoStation.setText (7, 11, potyag.getNomerPotyga()); break;
			case 94 : InfoStation.setText (7, 12, potyag.getNomerPotyga()); break; 
			}});
			*/
		}}
	int firstTime=0; 
	int period=200; 
    Timer timerSuma=new Timer("TimerSuma");  
    timerSuma.schedule(new SumaVestibulAll(), firstTime, period);
	}

void showOneStation (JButton button) {}
}