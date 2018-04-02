package Depo;

import java.util.*;
import java.util.logging.*;
import java.util.logging.Formatter;
import java.io.*;
/** klas generue 8800 Pasagiriv v vtstibule, spuskae ix 3-ma eskalatorami (bagatopotochno) na peron,
    zapuskae 8 potyagiv (bagatopotochno). 
    4 mashinista priznachautsya na potyagi v zalegnosti vid dosvidu,za kognu poizdku dosvid zbilshuetsya na 1. 
    Pasagiri z peronu vxodyat v potyag ta vixodyat pislya podorogi z potyaga v vestibul.
*/
public class Vestibul implements Runnable {
	static List <Pasagir> pasagiriVestibul = new ArrayList<> ();
	static List <Pasagir> pasagiriPeron = new ArrayList<> ();
	static List <Potyag> potyagi = new ArrayList<> ();
	static List <Thread> potoki = new ArrayList<> ();
	static int kilkistPasagiriv = 8800;
	static int praporKinezGeneraziiPasagiriv = 0;
	
	static Comparator <Pasagir> comparatorPasagir = new Comparator <Pasagir>(){ 
		@Override
		public int compare (Pasagir p1, Pasagir p2) {
		if (p1.getIdPasagir() > p2.getIdPasagir()) return 1;
		if (p1.getIdPasagir() < p2.getIdPasagir()) return -1;
		return 0;
		}};
	static Queue <Pasagir>  pasagiriVixid=new PriorityQueue<>(comparatorPasagir);
	
	public void run() {
try{int i=1;
	while (i<kilkistPasagiriv+1){
	Pasagir pas = new  Pasagir("PIB "+i, "IPN"+i, RandomVik.randomVik());
	pas.setIdPasagir(i);
	pasagiriVestibul.add(pas);
	synchronized (pasagiriVestibul) {pasagiriVestibul.notifyAll();}
	i++;
	Thread.sleep (5);//1 potok=5
	}
	System.out.println("Generaziya pasagiriv zakinchena"+" Potok " +Thread.currentThread().getName());
	Thread.sleep (1000);
	praporKinezGeneraziiPasagiriv=1;
	synchronized (pasagiriVestibul) {pasagiriVestibul.notifyAll();}
} catch (InterruptedException ie) {ie.printStackTrace();}	
}

static class MyFormatter extends Formatter {
	@Override
	public String format(LogRecord record) {
		return  record.getThreadID() + "::" + 
		        record.getSourceClassName() + "::" + 
				record.getSourceMethodName() + "::"+
				new Date(record.getMillis()) + "::" +
				record.getMessage() +  "::" +
				record.getParameters()[0] +
				"\n";
	}}
	
	public static void main(String[] args) {
System.out.println("PotokMain "+ Thread.currentThread().getName());
//generaziya pasagiriv
Thread potokGeneraziyaPasagiriv = new Thread(new Vestibul());
potokGeneraziyaPasagiriv.start();
//zapusk 3-x eskalatorov
Thread potok1 = new Thread(new Eskalator ("Esk 1", 200));//1400
potok1.start();
Thread potok2 = new Thread(new Eskalator ("Esk 2", 200));//1800
potok2.start();
Thread potok3 = new Thread(new Eskalator ("Esk 3", 200));//1700
potok3.start();		
//pochatok bloku loguvannya
try {System.setErr(new PrintStream(new File("logVestibul.txt")));
// Konfigurirovanie JavaUtilLogger
//LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
Logger log = Logger.getLogger("Vestibul");//Vestibul.class.getName()
		
log.setLevel(Level.FINE);
		
Handler fileHandler = new FileHandler("logJUL_Vestibul.txt", 20000, 5);
Handler fileHandlerXML = new FileHandler("logJUL_Vestibul.xml", 20000, 5);
fileHandler.setFormatter(new MyFormatter()); 
fileHandlerXML.setFormatter(new XMLFormatter());
		
log.addHandler(fileHandler);
log.addHandler(fileHandlerXML);
	
String param1 = " Vasya_0675930352 ";
//String param2 = " Ivan_0504260927 ";
		
log.log(new LogRecord(Level.INFO, "Pochatok LOGa"));		
//kinez bloka loguvannya					
		
		Mashinist ivanenko =new Mashinist("Ivanenko", "1111111111");
		ivanenko.setDosvid(3);
		Mashinist petrenko = new Mashinist("Petrenko", "2222222222");
		petrenko.setDosvid(1);
		Mashinist kozak= new Mashinist("Kozak", "4444444444");
		kozak.setDosvid(5);
		Mashinist xXX=new Mashinist("XXX", "5555555555");
		xXX.setDosvid(7);
	
	Comparator <Mashinist> comparator = new Comparator <Mashinist>(){ 
			@Override
			public int compare (Mashinist m1, Mashinist m2) {
			if (m1.getDosvid() > m2.getDosvid()) return -1;
			if (m1.getDosvid() < m2.getDosvid()) return 1;
			return 0;
			}};
			
	Queue <Mashinist>  mashinisti=new PriorityQueue<>(comparator);
	Queue <Mashinist>  mashinistiTMP=new PriorityQueue<>(comparator);
	
	mashinisti.add(ivanenko);
	mashinisti.add(petrenko);
	mashinisti.add(kozak);
	mashinisti.add(xXX);
	
final int kilkistMashinistiv = mashinisti.size();
Depo depo5=new Depo (27,18, NazvaDepo.DepoDarniza);
    
    for (int i=1; i<9; i++) {
    	Potyag potyag = new Potyag(depo5); 
        depo5.dodatVdepo(potyag);//dlya pravilnoj numerazii potyagiv 
     	
        Thread potokPotyg = new Thread(new  Runnable (){
     public void run(){  try {    
     for (int k=1; k<7; k++) {	 //1 potok k<7
     synchronized (mashinisti) {
    	potyag.setMashinist(mashinisti.poll());
      // posadka pasagiriv z perona v potyag v 1 potok {potyag.vxidPasagiriv(pasagiriPeron);}
    	if (!pasagiriPeron.isEmpty()) synchronized (pasagiriPeron) {potyag.vxidPasagiriv5Potokov(pasagiriPeron);}
    	
        System.out.println("POTYAG "+potyag.getNomerPotyga()+" " + potyag.getMashinist().toString() + " " + potyag.getVsixPasagirivPotyaga().toString());
        log.log(Level.INFO, "Start Potyg "+potyag.getNomerPotyga()+" " + potyag.getMashinist().toString()+potyag.getVsixPasagirivPotyaga().toString() +"Pasagir Vestibul "+pasagiriVestibul+" " +"Pasagir Eskalator1 " +"Pasagir Peron " + pasagiriPeron +" ", param1);
        Thread.sleep(1000);//+ new Random().nextInt(3000)
        
        potyag.getMashinist().setDosvid(potyag.getMashinist().getDosvid()+1);	
            
    synchronized (mashinistiTMP) {
        mashinistiTMP.add(potyag.getMashinist());    
       System.out.println("MashinistivTMP="+mashinistiTMP.size());       
        if (mashinistiTMP.size() >= kilkistMashinistiv) {
		    mashinisti.addAll(mashinistiTMP);
		    mashinistiTMP.clear();	    
}}}
        Thread.sleep(2000);//new Random().nextInt(2000)
        pasagiriVixid.addAll(potyag.getVsixPasagirivPotyaga());
        potyag.getPotyg().forEach(v->v.getPasigirVagon().clear());
        System.out.println(potyag.getNomerPotyga() + " zavershinij k=" + k);
}}
     catch (InterruptedException ie) {ie.printStackTrace();}   
     System.out.println("Potok "+ Thread.currentThread().getName()+" Potyg "+potyag.getNomerPotyga() + " zavershinij" );
}});
        potokPotyg.start(); 
        potoki.add(potokPotyg); 
}}	
  catch (IOException e) {e.printStackTrace();} 

    	try {
    	potokGeneraziyaPasagiriv.join();
    	potok1.join();
    	potok2.join();
    	potok3.join();
    	potoki.forEach(td->{try {td.join();} catch (InterruptedException ie) {ie.printStackTrace();}});
    	Thread.sleep(700);
    	while (!pasagiriVixid.isEmpty()) {System.out.println("pasagiriVixid " + pasagiriVixid.poll().getPIB());}
    	}
    	catch (InterruptedException ie) {ie.printStackTrace();} 

System.out.println("PotokMain "+ Thread.currentThread().getName() + " zavershinij" );
}}
