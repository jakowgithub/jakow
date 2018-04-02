package Depo;

public enum StationL3 {Station31 ("Station31",6,24,1500,0),Station32 ("Station32",6,24,1500,0), 
	                   Station33 ("Station33",6,24,1500,0), Station34 ("Station34",6,24,1500,0), 
	                   Revers ("Revers",0,24,0,0), VDorozi ("VDorozi",0,24,0,0);
	private String nazvaStation;
	private int kilkistPasag;//moge odnochasno perebuvati na stanzii
	private double timeP, timeK;
	private int potochnaKP;//potochna kilkist pasagiriv 
	
	StationL3 (String ns, double tP,double tK, int kP, int pkp){
		timeP=tP;
		timeK=tK;
		kilkistPasag=kP;
		potochnaKP=pkp;
		nazvaStation=ns;
	}
	double getTimePochatok (){return timeP;}
	double getTimeKinez (){return timeK;}
	int getKilkistPasag(){return kilkistPasag;}
    int getPotochnaKPas(){return potochnaKP;}
	
	void setPotochnaKPas (int pkp){potochnaKP=pkp;}
}
