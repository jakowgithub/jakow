package Depo;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;

public enum StationL1 {Station11 ("Station11",6,24,1500,0), Station12 ("Station12",6,24,1500,0), 
	                   Station13 ("Station13",6,24,1500,0), Station14 ("Station14",6,24,1500,0), 
	                   Revers ("Revers",0,24,0,0), VDorozi ("VDorozi",0,24,0,0) ;
	@DatabaseField()
	private String nazvaStation;
	
	private int kilkistPasag;//moge odnochasno perebuvati na stanzii
	
	private double timeP, timeK;
	
	private int potochnaKP;//potochna kilkist pasagiriv 
	
	private List <Pasagir> pasagirStation = new ArrayList<Pasagir> ();
	
	StationL1 (String nS, double tP,double tK, int kP, int pKP){
		timeP=tP; 
		timeK=tK;
		kilkistPasag=kP;
		potochnaKP=pKP;
		nazvaStation=nS;
	}

	StationL1 (){}//pustoj dlya ormlite
	
double getTimeP (){return timeP;}
double getTimeK (){return timeK;}
int getKilkistPasag(){return kilkistPasag;}
int getPotochnaKP(){return potochnaKP;}
String getNazvaStation(){return nazvaStation;}
List <Pasagir> getPasigirStation(){return pasagirStation;}

void setPotochnaKPas(int pkp){potochnaKP=pkp;}
//metod visague z potyga pasagiriv na station
void setPasigirStation( Potyag ptg, List <Pasagir> pas){ 
    if ((!pas.isEmpty()) && 
    	(pas!=null)      &&
       ((pasagirStation.size()+pas.size()) < kilkistPasag)){
    	pas.forEach(p->{
    		p.setStationKey(getNazvaStation());
    		p.setPotyagKey(0);
    		p.setVagonKey(0);
    	});
    	pasagirStation.addAll(pas);
    	setPotochnaKPas(pasagirStation.size());	
    	for (Pasagir psr: pasagirStation) {
    	for (Vagon v: ptg.getPotyg()) {
    		if (v.getPasigirVagon().contains(psr)) {
    			v.getPasigirVagon().remove(psr);
    			break;
    		}}}
    }
    else System.out.println("Perepovnennya station abo null na vxid");
}}
