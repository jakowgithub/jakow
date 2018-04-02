package Depo;

import java.util.ArrayList;
import java.util.List;

public class StationV implements Runnable {
	private List <Pasagir> pasagiriVestibul = new ArrayList<> ();
	private List <Pasagir> pasagiriPeron = new ArrayList<> ();
	private List <Potyag> potyagi = new ArrayList<> ();
	private int kilkistPasagiriv = 100;//8800
	private int praporKinezGeneraziiPasagiriv = 0;
	private String nazvaStation;
	
	public List<Pasagir> getPasagiriVestibul() {return pasagiriVestibul;}
	public void setPasagiriVestibul(List<Pasagir> pasagiriVestibul) {this.pasagiriVestibul = pasagiriVestibul;}
	
	public List<Pasagir> getPasagiriPeron() {return pasagiriPeron;}
	public void setPasagiriPeron(List<Pasagir> pasagiriPeron) {this.pasagiriPeron = pasagiriPeron;}

	public List<Potyag> getPotyagi() {return potyagi;}
	public void setPotyagi(List<Potyag> potyagi) {this.potyagi = potyagi;}

	public int getPraporKinezGeneraziiPasagiriv() {return praporKinezGeneraziiPasagiriv;}
	public void setPraporKinezGeneraziiPasagiriv(int praporKinezGeneraziiPasagiriv) {this.praporKinezGeneraziiPasagiriv = praporKinezGeneraziiPasagiriv;}

	public String getNazvaStation() {return nazvaStation;}
	public void setNazvaStation(String nazvaStation) {this.nazvaStation = nazvaStation;}

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
