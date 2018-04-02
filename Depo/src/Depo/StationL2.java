package Depo;

public enum StationL2 {Station21 ("Station21",6,24,1500,0),Station22 ("Station22",6,24,1500,0), 
	                   Station23 ("Station23",6,24,1500,0), Station24 ("Station24",6,24,1500,0), 
	                   Revers("Revers",0,24,0,0), VDorozi ("VDorozi",0,24,0,0);
	private String nazvaStation;
	private int kilkistPasag;//moge odnochasno perebuvati na stanzii
	private double timeP, timeK;
	private int potochnaKP;//potochna kilkist pasagiriv 
	
	StationL2 (String nS, double tP,double tK, int kP, int pKP){
		timeP=tP; 
		timeK=tK;
		kilkistPasag=kP;
		potochnaKP=pKP;
		nazvaStation=nS;
	}
	double getTimePochatok (){return timeP;}
	double getTimeKinez (){return timeK;}
	int getKilkistPasag(){return kilkistPasag;}
	int getPotochnaKPas(){return potochnaKP;}
	String getNazvaStation(){return nazvaStation;}
	
	void setPotochnaKPas (int pkp){potochnaKP=pkp;}

}
