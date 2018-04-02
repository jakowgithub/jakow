package Depo;
import java.util.*;

public class QueuePriority {	

	static  List <Potyag> potyagi = new ArrayList<Potyag> ();
	static  List <Thread> potoki = new ArrayList<Thread> ();
	
	public static void main(String[] args) {
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
	
	Queue <Mashinist>  mashinistiPQ=new PriorityQueue<>(comparator);
    
	mashinistiPQ.add(ivanenko);
	mashinistiPQ.add(petrenko);
	mashinistiPQ.add(kozak);
	mashinistiPQ.add(xXX);
    System.out.println(mashinistiPQ.toString());
    
    Depo depo5=new Depo (21,14, NazvaDepo.DepoDarniza);
    
    Potyag potyag1= new Potyag(depo5);
    if (!mashinistiPQ.isEmpty()) potyag1.setMashinist(mashinistiPQ.poll());
    depo5.dodatVdepo(potyag1);//dlya pravilnoj numerazii potyagiv
    Potyag potyag2= new Potyag(depo5);
    if (!mashinistiPQ.isEmpty()) potyag2.setMashinist(mashinistiPQ.poll());
    depo5.dodatVdepo(potyag2);
    Potyag potyag3= new Potyag(depo5);	
    if (!mashinistiPQ.isEmpty()) potyag3.setMashinist(mashinistiPQ.poll());
    depo5.dodatVdepo(potyag3);
    Potyag potyag4= new Potyag(depo5);
    if (!mashinistiPQ.isEmpty()) potyag4.setMashinist(mashinistiPQ.poll());
    depo5.dodatVdepo(potyag4);
    
    ivanenko.setDosvid(RandomDosvid.randomDosvid());
    petrenko.setDosvid(RandomDosvid.randomDosvid());
    kozak.setDosvid(RandomDosvid.randomDosvid());
    xXX.setDosvid(RandomDosvid.randomDosvid());
    mashinistiPQ.add(ivanenko);
    mashinistiPQ.add(petrenko);
    mashinistiPQ.add(kozak);
    mashinistiPQ.add(xXX);
    System.out.println(mashinistiPQ.toString());
    
    while (!mashinistiPQ.isEmpty()) {
    	System.out.println(mashinistiPQ.poll().getDosvid());
	}
    List <Pasagir> pasagiri = new ArrayList<Pasagir> ();    
for (int i=1; i<=1100; i++) {
	Pasagir psr=new Pasagir ("PIB "+i,"IPN123456"+i,RandomVik.randomVik());
	pasagiri.add(psr);
	System.out.println(psr.toString());
}	
System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
potyag4.vxidPasagiriv(pasagiri);
System.out.println(potyag4.getPotochnaKilkistPasagiriv());
potyag4.getVsixPasagirivPotyaga().forEach(psr->System.out.println(psr.toString()));
System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
pasagiri.forEach(psr->System.out.println(psr.toString()));
System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
StationL1.Station11.setPasigirStation(potyag4,potyag4.getVsixPasagirivPotyaga());
StationL1.Station11.getPasigirStation().forEach(psr->System.out.println(psr.toString()));
System.out.println("==================================================================================");
potyag4.getVsixPasagirivPotyaga().forEach(psr->System.out.println(psr.toString()));
}}
