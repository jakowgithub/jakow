package Depo;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

public class PQ2 {

	static  List <Potyag> potyagi = new ArrayList<Potyag> ();
	static  List <Thread> potoki = new ArrayList<Thread> ();
	
	static class MyFormatter extends Formatter {

		@Override
		public String format(LogRecord record) {
			return  record.getThreadID() + "::" + 
		            record.getSourceClassName() + "::" + 
				    record.getSourceMethodName() + "::"+
				    new Date(record.getMillis()) + "::" +
				    record.getMessage() + "\n";
	}}
	
	public static void main(String[] args) {
//pochatok bloku loguvannya
	try {System.setErr(new PrintStream(new File("logPQ2.txt")));
	// Konfigurirovanie JavaUtilLogger
		//LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
		Logger log = Logger.getLogger("PQ2");//PQ2.class.getName()
		
		log.setLevel(Level.FINE);
		
		Handler fileHandler = new FileHandler("logJUL_PQ2.txt", 20000, 5);
		Handler fileHandlerXML = new FileHandler("logJUL_XML_PQ2.xml", 20000, 5);
		fileHandler.setFormatter(new MyFormatter());
		fileHandlerXML.setFormatter(new XMLFormatter());
		
		log.addHandler(fileHandler);
		log.addHandler(fileHandlerXML);
	
		String param1 = " Vasya_0675930352 ";
		String param2 = " Ivan_0504260927 ";
		
		log.log(new LogRecord(Level.INFO, "Pochatok LOGa"));
		//log.log(Level.SEVERE, stringMessageFormat, param1);
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
    System.out.println(mashinisti.toString());
    
    final int kilkistMashinistiv = mashinisti.size();
    
    Depo depo5=new Depo (27,18, NazvaDepo.DepoDarniza);
    
    for (int i=1; i<9;i++){
    	Potyag potyag = new Potyag(depo5); 
        depo5.dodatVdepo(potyag);//dlya pravilnoj numerazii potyagiv 
     	
        Thread potok = new Thread(new  Runnable (){
   public void run(){  
	   try{ 
		   for (int k=1; k<4; k++) {
    	 
    synchronized (mashinisti) {
    	
    	potyag.setMashinist(mashinisti.poll());
      //synchronized (potyagi) {potyagi.add(potyag);}
        System.out.println("Potyg "+potyag.getNomerPotyga()+" " + potyag.getMashinist().toString());
        log.log(Level.INFO, "Start Potyg "+potyag.getNomerPotyga()+" " + potyag.getMashinist().toString(), param1);
        
        Thread.sleep(new Random().nextInt(1000));//+ new Random().nextInt(3000)
        
        potyag.getMashinist().setDosvid(potyag.getMashinist().getDosvid()+1);	
            
    synchronized (mashinistiTMP) {
        mashinistiTMP.add(potyag.getMashinist());    
	            
        if (mashinistiTMP.size()==kilkistMashinistiv) {
		    mashinisti.addAll(mashinistiTMP);
		    mashinistiTMP.clear();
		    //mashinisti.notifyAll();
}}}
        Thread.sleep(new Random().nextInt(2000));
        //System.out.println(potyag.getNomerPotyga() + " zavershinij k=" + k);
        log.log(Level.INFO, "Stop Potyg "+potyag.getNomerPotyga()+" " + potyag.getMashinist().toString(), param2);
}}
     catch (InterruptedException ie) {ie.printStackTrace();}   
}});
        potok.setName("Potok "+i);
        potok.start();    
	    potoki.add(potok);  
}}
	catch (IOException e) {e.printStackTrace();} 
	}}
