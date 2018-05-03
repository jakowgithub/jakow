package Depo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class StationV implements Runnable {
	private CopyOnWriteArrayList <Pasagir> pasagiriVestibul = new CopyOnWriteArrayList <>();
	private CopyOnWriteArrayList <Pasagir> pasagiriPeron = new CopyOnWriteArrayList<> ();
	private List <Potyag> potyagi = new ArrayList<> ();
	private int kilkistPasagiriv = 8800;//8800
	private int praporKinezGeneraziiPasagiriv = 0;
	private String nazvaStation;
	
	public List<Pasagir> getPasagiriVestibul() {return pasagiriVestibul;}
	public List<Pasagir> getPasagiriPeron() {return pasagiriPeron;}
	
	public List<Potyag> getPotyagi() {return potyagi;}
	public void setPotyagi(List<Potyag> potyagi) {this.potyagi = potyagi;}

	public int getPraporKinezGeneraziiPasagiriv() {return praporKinezGeneraziiPasagiriv;}
	public void setPraporKinezGeneraziiPasagiriv(int praporKinezGeneraziiPasagiriv) {this.praporKinezGeneraziiPasagiriv = praporKinezGeneraziiPasagiriv;}

	public String getNazvaStation() {return nazvaStation;}
	public void setNazvaStation(String nazvaStation) {this.nazvaStation = nazvaStation;}
	
	public int getKilkistPasagirivVestibul () {return pasagiriVestibul.size();}
	public int getKilkistPasagiriPeron () {return pasagiriPeron.size();}
	
	public StationV (String nazvaSt) {this.nazvaStation = nazvaSt;} 
	
	public void run() {
		try{int i=1;
			while (i<=kilkistPasagiriv){
			Pasagir pas = new  Pasagir(nazvaStation+"_PIB "+i, "IPN"+i, RandomVik.randomVik());
			pas.setIdPasagir(i);
			synchronized (pasagiriVestibul) {
				pasagiriVestibul.add(pas);
			    pasagiriVestibul.notifyAll();
			    }
			i++;
			Thread.sleep (5);//1 potok=5
			}
			System.out.println("Generaziya pasagiriv zakinchena"+" Potok " +Thread.currentThread().getName());
			Thread.sleep (1000);
			praporKinezGeneraziiPasagiriv=1;
			synchronized (pasagiriVestibul) {pasagiriVestibul.notifyAll();}
	    } catch (InterruptedException ie) {ie.printStackTrace();}	
		}
	
}
