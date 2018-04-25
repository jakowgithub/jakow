package Depo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 porydok robotu:
1. Stvoriti liniu: Line redLine1=new Line(1); dozvoleno tilki 1,2,3 Line
2. Priznachin maschinistiv na potygi: potyag1.setMashinist(new Mashinist("Ivanenko", "1111111111"));
   abo: Mashinist petrenko=new Mashinist("Petrenko", "5555555555"); potyag1.setMashinist(petrenko);
   bez mashinista potyag ne vijde na liniu
3. Vipustit` potyag (potyagi) na liniu: redLine1.vipuskNaLiniu (potyag1, potyag2...);
                                   abo:  Potyag [] potyagBL2=new Potyag[8];
                                         blueLine2.vipuskNaLiniuM(potyagBL2);
4. dodat v masiv linij novu liniu: line[0]=redLine1;
*/

public class Line {
	private String nazvaLinii;
	private int line_T [];//masiv elementiv tuda, 1 element=100 m = dovgina potyaga
	private int line_N [];//masiv elementiv nazad, 1 element=100 m = dovgina potyaga
	private ArrayList <Potyag> potyagNaLinii;//masiv potyagiv na linii
	private int zagalnaL, tmp, distanziya, firstTime, period, 
	            prapor1, prapor2, prapor3,
	            lichilnikTimer1, lichilnikTimer2, lichilnikTimer3,
		        praporSt_T,praporSt_N ;
	private int []indexSt_T;//index Stanzij v masive elementov tuda
	private int []indexSt_N;//index Stanzij v masive elementov nazad
	private Timer timer;
	SchemaMetro schemaMetro;
	
	ArrayList <Potyag> getPotyagNaLinii (){return potyagNaLinii;}
	String getNazvaLinii (){return nazvaLinii;}
	int []  getline_T (){return line_T; }
	int []  getline_N (){return line_N; }
	Timer getTimer (){return timer;}
	//void setLichilnikTimer(int lt){lichilnikTimer1=lt;}
	
Line (int nomerLinii){
	
if ((nomerLinii>=1) && (nomerLinii<=3)){
	switch (nomerLinii){
	case 1:{int kSt=VidstanMigSt1.values().length;
		    indexSt_T=new int[kSt];indexSt_N=new int [kSt];
		    nazvaLinii="Red";
			for(VidstanMigSt1 l: VidstanMigSt1.values()){
			    zagalnaL+=l.getVidstanMigStanziami();
			    indexSt_T[tmp]=(zagalnaL/100)+tmp+1;
				tmp++;
				}
			//vidlik vid Station11 index 0;   
			for (int i=indexSt_T.length-2; i>=0; i--) {indexSt_T[i+1]=indexSt_T[i];}	    
			    indexSt_T[0]=0;
			    zagalnaL=((zagalnaL)/100)+kSt;
			    indexSt_N[0]=0;
	} break;
	case 2:{int kSt=VidstanMigSt2.values().length;
		        indexSt_T=new int[kSt];indexSt_N=new int [kSt];
	            nazvaLinii="Blue";
	            for(VidstanMigSt2 l:VidstanMigSt2.values()){
	        	   zagalnaL+=l.getVidstanMigStanziami();
	        	   indexSt_T[tmp]=(zagalnaL/100)+tmp+1;
				   tmp++;
				   }
	          //vidlik vid Station21 index 0;   
				for (int i=indexSt_T.length-2;i>=0;i--) {indexSt_T[i+1]=indexSt_T[i];}	    
				    indexSt_T[0]=0;
	                zagalnaL=((zagalnaL)/100)+kSt;
    } break;
	case 3:{int kSt=VidstanMigSt3.values().length;
		        indexSt_T=new int[kSt]; indexSt_N=new int [kSt];
	            nazvaLinii="Green";
	            for(VidstanMigSt3 l:VidstanMigSt3.values()){
	            	zagalnaL+=l.getVidstanMigStanziami();
	            	indexSt_T[tmp]=(zagalnaL/100)+tmp+1;
					tmp++;
	            }
	          //vidlik vid Station31 index 0;   
				for (int i=indexSt_T.length-2; i>=0; i--) {indexSt_T[i+1]=indexSt_T[i];}	    
				    indexSt_T[0]=0;
	                zagalnaL=((zagalnaL)/100)+kSt;
	 } break;
		}
	    System.out.println(zagalnaL+ " " + this.getNazvaLinii());	
	    line_T=new int[zagalnaL];
		line_N=new int[zagalnaL];
		potyagNaLinii=new ArrayList <>(zagalnaL-1);
		//perevertau index Station v line nazad 
		int kSt=indexSt_T.length;
		indexSt_N [0]=0;
		if (kSt>0) for (int i=1; i<kSt; i++) {indexSt_N [i] = indexSt_N [i-1]+(indexSt_T[kSt-i]-indexSt_T[kSt-i-1]);}		
		System.out.println(Arrays.toString(indexSt_T));
		System.out.println(Arrays.toString(indexSt_N));
}
else System.out.println("3 < Nomer Line < 1");	
}

boolean unikalMashinist (String pib, ArrayList<Potyag> pnl){
	for (Potyag p: pnl) {if (pib.equals(p.getMashinist().getPIB())) return false;}
	return true;	
}
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
void vipuskNaLiniu (Potyag ... potyag){

 if ((potyag.length>0)&&((potyag.length+potyagNaLinii.size()) < zagalnaL-1)){

	for (int i=0; i<potyag.length; i++){	
		//perevirka nayavnosti mashinista
	if ((potyag[i].getMashinist().getPIB()!="Jon Dow") &&
		(potyag[i].getMashinist().getPIB()!=null)      &&
		(!potyag[i].getMashinist().getPIB().isEmpty()))  {
//perevirka nayavnosti 5 vagoniv MPPPM abo MXXXM
	    if((5==potyag[i].getPotyg().size())&&
	       (potyag[i].getPotyg().get(0).getNazvaVagona().equals("VagonMashinist")) &&
	       (potyag[i].getPotyg().get(4).getNazvaVagona().equals("VagonMashinist"))){
//perevirka nayavnosti RIZNICH machinistiv v potyagax na linii
		if(0==potyagNaLinii.size()){potyagNaLinii.add(potyag[i]); prapor1=1;}
	    else {if (unikalMashinist (potyag[i].getMashinist().getPIB(), potyagNaLinii)) {potyagNaLinii.add(potyag[i]); prapor1=1;}
	          else     System.out.println("Povtor Mashinista v potyazi N" + potyag[i].getNomerPotyga());
	    	}   
	    //potyagNaLinii.add(potyag[i]);
		//prapor=1;
		potyag[i].setDepoKey("Na linii "+this.getNazvaLinii());
		potyag[i].getPotyg().forEach(v->v.setNazvaDepoKey("Na linii "+this.getNazvaLinii()));
		potyag[i].setLineKey(this.getNazvaLinii());
	    }
	    else System.out.println("Vidsutnij 5 vagoniv MPPPM v potyazi N"+potyag[i].getNomerPotyga());
	}
	else {System.out.println("Vidsutnij mashinist v potyazi N"+potyag[i].getNomerPotyga());}
	}	
//distanziya mig potygami, elementiv, 1 element = 100 m.
if (potyagNaLinii.size() > 0) distanziya=(int)((2*zagalnaL)/(potyagNaLinii.size()));	
//viznachau startPosition potyagiv na linii z uraxuvannyam distanzii,
for (int i=0; i<potyagNaLinii.size(); i++){
	 int dst=(potyagNaLinii.size()-1-i)*distanziya;
	 potyagNaLinii.get(i).setCurrentPosition(dst);
//nomeri potyagiv v masiv Line_T abo Line_N dlya ruchu 
	if (dst<zagalnaL) line_T[dst]=potyagNaLinii.get(i).getNomerPotyga(); 
	else              line_N[dst-zagalnaL]=potyagNaLinii.get(i).getNomerPotyga();
}
//start  ruchu potyagiv 144km/h=>2,5s=100m; 60km/h=>6,0s=100m; 0,5s-otladka	
    if (1==prapor1){
    	 firstTime=0;
	     period=500;//6000, 0,5s-dlya otladki
	     timer=new Timer("Timer1");
	     timer.schedule(new ZapuskPotyaga(0, this.getNazvaLinii()),firstTime, period);     
	     }
}
else {System.out.println("Vidsutnij potyag abo kilkist potygiv > zagalna dovgina linii");}
}	

class ZapuskPotyaga extends TimerTask {
	int currentPosition;
	String nazvaLinii;
    ZapuskPotyaga(int cP, String nl){
		currentPosition=cP;
		nazvaLinii=nl;
}
public void run() {
                   
if (lichilnikTimer1 > 180) { 
	timer.cancel();
	lichilnikTimer1=0; 
	if (!Vixid.pasagiriVixid.isEmpty()) Vixid.drukPasagiriVixid();
	if (!MetroRuch.poizdki.isEmpty()) ZapisFile.saveFile(MetroRuch.poizdki);
}
else{ System.out.println("TR "+Arrays.toString(line_T)); System.out.println("NR "+Arrays.toString(line_N)); 
//druk v SchemaMetro line_T ta Line_N   
for (int k=0; k<line_T.length; k++){ 
	  if (0!=line_T[k]) SchemaMetro.printRedLine1_T(k, line_T[k]);
	  else              SchemaMetro.deleteRedLine1_T(k);
	  if (0!=line_N[k]) SchemaMetro.printRedLine1_N(k, line_N[k]);
	  else              SchemaMetro.deleteRedLine1_N(k);	  
  }
	//rozraxunok line_T
    for (int i=line_T.length-1; i>=0; i--) {
		if ((0!=line_T[i]) && (0==line_T[i+1])) {
			int tmp3=0;//viznachau index potyaga (tmp3) v potyagNaLinii po nomeru v line_T[i]
			for(Potyag p: potyagNaLinii){
				if(line_T[i]==p.getNomerPotyga()) break;
				tmp3++;
				}			
			for (int j=0; j<indexSt_T.length; j++){
				//dosyag potyg stanzii? 
				if(i==indexSt_T[j]){  //60 elementiv, 0,16,36,54-index Station_T
			     praporSt_T=1; 	     
               
			     //vigruzka/zagruzka pasagiriv z/v potyaga	
			    // yaka Station?
			     StationV station = MetroRuch.stationsRed.get(j);
			     synchronized (station.getPasagiriPeron()) {
			    	 if (!potyagNaLinii.get(tmp3).getVsixPasagirivPotyaga().isEmpty()) potyagNaLinii.get(tmp3).vixidV5Potokov();
			         if (!station.getPasagiriPeron().isEmpty()) potyagNaLinii.get(tmp3).vxidPv5Potokov(station, station.getPasagiriPeron());
			         }
			     potyagNaLinii.get(tmp3).setLichilnikSt(potyagNaLinii.get(tmp3).getLichilnikSt()+1);    
			     break;
}
			     else praporSt_T=0;
}				
			// 8 kilkist chasu zupinki potyaga na statzii, 8x6s(6000 timer)=48s
			if ((potyagNaLinii.get(tmp3).getLichilnikSt()>=8) ||
				(0==praporSt_T)){
				line_T[i+1]=line_T[i];
				line_T[i]=0;
				System.out.println("RED LineT potyag "+ potyagNaLinii.get(tmp3).getNomerPotyga() + " CurrentPosition "+ potyagNaLinii.get(tmp3).getCurentPosition());
				potyagNaLinii.get(tmp3).setCurrentPosition(potyagNaLinii.get(tmp3).getCurentPosition()+1);
}
			if (potyagNaLinii.get(tmp3).getLichilnikSt()>=8) potyagNaLinii.get(tmp3).setLichilnikSt(0);
}
}  //potyag dosyag ostanjogo elementa line_T -> perexid na line_N
    if((0!=line_T [line_T.length-1]) &&
   	   (0==line_N[0])) {
   	    //zbilshuu dosvid mashinista max=10000 poizdok (5r x 11 mis x 22 dib x 8 poizdok)
    	for(Potyag p: potyagNaLinii){
    		if (line_T [line_T.length-1]==p.getNomerPotyga()){
    			if (p.getMashinist().getDosvid()<10000) p.getMashinist().setDosvid(p.getMashinist().getDosvid()+1);
    			//stvoruu klas poizdka ta dodau v arraylist poizdki
    	    	Poizdka fakt =  new Poizdka (p.getMashinist(), this.nazvaLinii+"_T");
    	    	MetroRuch.poizdki.add(fakt);
    	    	//zamina mashinistiv
	    	    MetroRuch.driversRedTMP.add(p.getMashinist());
	    	    if (MetroRuch.driversRedTMP.size() >= MetroRuch.driversRed.size()) {
	    	    	MetroRuch.driversRed.addAll(MetroRuch.driversRedTMP);
	    	    	MetroRuch.driversRedTMP.clear();	    
	    	    	}
	    	    if (!MetroRuch.driversRed.isEmpty()) p.setMashinist(MetroRuch.driversRed.poll());
    		    break;
    		}}
    	
    	line_N[0]=line_T [line_T.length-1];
   	    line_T [line_T.length-1]=0;
   	    
   }       
    //obraxunok line_N
    for (int i=line_N.length-1; i>=0; i--) {
		if ((0!=line_N[i])&&(0==line_N[i+1])) {
			 int tmp4=0;//index potyaga v potyagNaLinii dlya line_N		
			for(Potyag p: potyagNaLinii){
				if(line_N[i]==p.getNomerPotyga()) break;
				tmp4++;
				}			
			for (int j=0; j<indexSt_N.length; j++){//dosyag potyg stanzii?
			     if(i==indexSt_N[j]){
			     praporSt_N=1; 
			     
			   //vigruzka/zagruzka pasagiriv z/v potyaga	
			  // yaka Station?
				  StationV station = MetroRuch.stationsRed.get(3-j);//60 elementiv, 0,18,38,54-index Station Line_N
				  synchronized (station.getPasagiriPeron()) {
				    if (!potyagNaLinii.get(tmp4).getVsixPasagirivPotyaga().isEmpty()) potyagNaLinii.get(tmp4).vixidV5Potokov();
				    if (!station.getPasagiriPeron().isEmpty()) potyagNaLinii.get(tmp4).vxidPv5Potokov(station, station.getPasagiriPeron());
				         }		     
				  potyagNaLinii.get(tmp4).setLichilnikSt(potyagNaLinii.get(tmp4).getLichilnikSt()+1);
				  break;
}
			     else praporSt_N=0;
}				
			if ((potyagNaLinii.get(tmp4).getLichilnikSt()>=8) ||
				(0==praporSt_N)){
				line_N[i+1]=line_N[i];
				line_N[i]=0;
				System.out.println("RED LineN psotyag "+ potyagNaLinii.get(tmp4).getNomerPotyga() + 
				           " CurrentPosition "+ potyagNaLinii.get(tmp4).getCurentPosition());
				potyagNaLinii.get(tmp4).setCurrentPosition(potyagNaLinii.get(tmp4).getCurentPosition()+1);				
}
			if (potyagNaLinii.get(tmp4).getLichilnikSt()>=8) potyagNaLinii.get(tmp4).setLichilnikSt(0);
}
}	
  //potyag dosyag ostanjogo elementa line_N -> perexid na line_T, currentPosition=0, LichilnikSt=0; 
    if((0!=line_N [line_N.length-1]) &&
   	   (0==line_T[0])) {
    	//zbilshuu dosvid mashinista max=10000 poizdok (5r x 11 mis x 22 dib x 8 poizdok)
    	for(Potyag p: potyagNaLinii){
    		if (line_N [line_N.length-1]==p.getNomerPotyga()){
    			if (p.getMashinist().getDosvid() < 10000) p.getMashinist().setDosvid(p.getMashinist().getDosvid()+1);
    			//stvoruu klas poizdka ta dodau v arraylist poizdki
 	    	       Poizdka fakt =  new Poizdka (p.getMashinist(), this.nazvaLinii+"_N");
 	    	       MetroRuch.poizdki.add(fakt);
 	    	      //zamina mashinistiv
 	    	    	MetroRuch.driversRedTMP.add(p.getMashinist());
 	    	    	if (MetroRuch.driversRedTMP.size() >= MetroRuch.driversRed.size()) {
 	    	    			   MetroRuch.driversRed.addAll(MetroRuch.driversRedTMP);
 	    	    			   MetroRuch.driversRedTMP.clear();	    
 	    	    	}
 	    	    	if (!MetroRuch.driversRed.isEmpty()) p.setMashinist(MetroRuch.driversRed.poll());
    		        break;
    		}}
    	line_T[0]=line_N [line_N.length-1];
   	    line_N [line_N.length-1]=0;
   	    potyagNaLinii.forEach(p-> { if(line_T[0]==p.getNomerPotyga()) {
   	    		p.setCurrentPosition(0);
   	    		p.setLichilnikSt(0);}
   	    	});
   	    } 
    lichilnikTimer1++;   
}
}}
//==========================================================================
void vipuskNaLiniuM (Potyag [] mP){

	 if ((mP.length>0)&&((mP.length+potyagNaLinii.size())<zagalnaL-1)){

		for (int i=0;i<mP.length;i++){	
			//perevirka nayavnosti mashinista
		if ((mP[i].getMashinist().getPIB()!="Jon Dow") &&
			(mP[i].getMashinist().getPIB()!=null)      &&
			(!mP[i].getMashinist().getPIB().isEmpty())){
	//perevirka nayavnosti 5 vagoniv MPPPM abo MXXXM
		    if((5==mP[i].getPotyg().size())&&
		       (mP[i].getPotyg().get(0).getNazvaVagona().equals("VagonMashinist")) &&
		       (mP[i].getPotyg().get(4).getNazvaVagona().equals("VagonMashinist"))){
	//perevirka nayavnosti RIZNICH machinistiv v potyagax na linii
			if(0==potyagNaLinii.size()){potyagNaLinii.add(mP[i]); prapor2=1;}
		    else {if (unikalMashinist (mP[i].getMashinist().getPIB(), potyagNaLinii)){
		                        potyagNaLinii.add(mP[i]); prapor2=1;
		    	  }
		         else System.out.println("Povtor Mashinista v potyazi N"+mP[i].getNomerPotyga());
		    	 }   
		    //potyagNaLinii.add(mP[i]);
			//prapor2=1;
			mP[i].setDepoKey("Na linii "+this.getNazvaLinii());
			mP[i].getPotyg().forEach(v->v.setNazvaDepoKey("Na linii "+this.getNazvaLinii()));
			mP[i].setLineKey(this.getNazvaLinii());
		    }
		    else System.out.println("Vidsutnij 5 vagoniv MPPPM v potyazi N"+mP[i].getNomerPotyga());
		}
		else {System.out.println("Vidsutnij mashinist v potyazi N"+mP[i].getNomerPotyga());}
		}	
	//distanziya mig potygami, elementiv, 1 element = 100 m.
	if (potyagNaLinii.size()>0) distanziya=(int)((2*zagalnaL)/(potyagNaLinii.size()));	
	//viznachau startPosition potyagiv na linii z uraxuvannyam distanzii,
	for (int i=0; i<potyagNaLinii.size(); i++){
		int dst=(potyagNaLinii.size()-1-i)*distanziya;
		potyagNaLinii.get(i).setCurrentPosition(dst);
	//nomeri potyagiv v masiv Line_T abo Line_N dlya ruchu 
		if (dst < zagalnaL) line_T[dst]=potyagNaLinii.get(i).getNomerPotyga(); 
		else                line_N[dst-zagalnaL]=potyagNaLinii.get(i).getNomerPotyga();
	}
	//start  ruchu potyagiv 144km/h=>2,5s=100m; 60km/h=>6,0s=100m; 0,5s-otladka	
	    if (1==prapor2){
	    	 firstTime=0;
		     period=500;//6000, 0,5s-dlya otladki
		     timer=new Timer("Timer2");
		     //timer.schedule(new ZapuskPotyaga2(0, distanziya),firstTime, period);
		     timer.schedule(new ZapuskPotyaga2 (0, this.getNazvaLinii()),firstTime, period); 
		     }
	}
	else {System.out.println("Vidsutnij potyag abo kilkist potygiv > zagalna dovgina linii");}
	}	

	class ZapuskPotyaga2 extends TimerTask {
		int currentPosition;
		String nazvaLinii;
	ZapuskPotyaga2(int cP, String nl){
			currentPosition=cP;
			nazvaLinii=nl;
	}
	public void run() {
	                   
	if(lichilnikTimer2 > 180){
		timer.cancel();
		lichilnikTimer2=0;
		}
	else{ System.out.println("TB "+Arrays.toString(line_T)); System.out.println("NB "+Arrays.toString(line_N)); 
	//druk v SchemaMetro line_T ta Line_N   
	for (int k=0; k<line_T.length; k++){ 
		  if (0!=line_T[k]) SchemaMetro.printBlueLine2_T(k, line_T[k]);
		  else              SchemaMetro.deleteBlueLine2_T(k);
		  if (0!=line_N[k]) SchemaMetro.printBlueLine2_N(k, line_N[k]);
		  else              SchemaMetro.deleteBlueLine2_N(k);	  
	  }
		//obraxunok line_T
	    for (int i=line_T.length-2; i>=0; i--) {
			if ((0!=line_T[i])&&(0==line_T[i+1])) {
				int tmp3=0;//viznachau index potyaga v potyagNaLinii po nomeru v line_T[i]
				for(Potyag p: potyagNaLinii){
					if(line_T[i]==p.getNomerPotyga()) break;
					tmp3++;
					}			
				for (int j=0; j<indexSt_T.length; j++){//dosyag potyg stanzii?
				     if(i==indexSt_T[j]){//Blueline 49 elementiv,  index Station_Tuda 0,18,34,43 / index Station_Nazad 0,9,25, 43 
				     praporSt_T=1; 
				     
				     //vigruzka/zagruzka pasagiriv z/v potyaga	
					// yaka Station?
				     StationV station= MetroRuch.stationsBlue.get(j);
					 synchronized (station.getPasagiriPeron()) {
					 if (!potyagNaLinii.get(tmp3).getVsixPasagirivPotyaga().isEmpty()) potyagNaLinii.get(tmp3).vixidV5Potokov();
					 if (!station.getPasagiriPeron().isEmpty()) potyagNaLinii.get(tmp3).vxidPv5Potokov(station, station.getPasagiriPeron());
					 }
					 potyagNaLinii.get(tmp3).setLichilnikSt(potyagNaLinii.get(tmp3).getLichilnikSt()+1);
					 break;
	}
				     else praporSt_T=0;
	}				
				// 8 kilkist chasu zupinki potyaga na statzii, 8x6s(6000 timer)=48s
				if ((potyagNaLinii.get(tmp3).getLichilnikSt()>=8) ||
					(0==praporSt_T)){
					line_T[i+1]=line_T[i];
					line_T[i]=0;
					potyagNaLinii.get(tmp3).setCurrentPosition(potyagNaLinii.get(tmp3).getCurentPosition()+1);
	}
				if (potyagNaLinii.get(tmp3).getLichilnikSt()>=8) potyagNaLinii.get(tmp3).setLichilnikSt(0);
	}
	}  //potyag dosyag ostanjogo elementa line_T -> perexid na line_N 
	    if((0!=line_T [line_T.length-1]) &&
	   	   (0==line_N[0])) {
	   	    //zbilshuu dosvid mashinista max=10000 poizdok (5r x 11 mis x 22 dib x 8 poizdok)
	    	for(Potyag p: potyagNaLinii){
	    		if (line_T [line_T.length-1]==p.getNomerPotyga()){
	    			if (p.getMashinist().getDosvid() < 10000)  p.getMashinist().setDosvid(p.getMashinist().getDosvid()+1);
	    			//stvoruu klas poizdka ta dodau v arraylist poizdki
	    	    	   Poizdka fakt2 =  new Poizdka (p.getMashinist(), this.nazvaLinii+"_T");
	    	    	   MetroRuch.poizdki.add(fakt2);
	    	    	 //zamina mashinistiv
	    	    		MetroRuch.driversBlueTMP.add(p.getMashinist());
	 	    	    	if (MetroRuch.driversBlueTMP.size() >= MetroRuch.driversBlue.size()) {
	 	    	    		MetroRuch.driversBlue.addAll(MetroRuch.driversBlueTMP);
	 	    	    		MetroRuch.driversBlueTMP.clear();	    
	 	    	    	}
	 	    	    	if (!MetroRuch.driversBlue.isEmpty()) p.setMashinist(MetroRuch.driversBlue.poll());
	 	    	    	break;
	    		}}
	    	
	    	line_N[0]=line_T [line_T.length-1];
	   	    line_T [line_T.length-1]=0;
	   }       
	    //obraxunok line_N
	    for (int i=line_N.length-2; i>=0; i--) {
			if ((0!=line_N[i])&&(0==line_N[i+1])) {
				 int tmp4=0;//index potyaga v potyagNaLinii dlya line_N		
				for(Potyag p: potyagNaLinii){
					if(line_N[i]==p.getNomerPotyga()) break;
					tmp4++;
					}			
				for (int j=0; j<indexSt_N.length; j++){//dosyag potyg stanzii?
				     if(i==indexSt_N[j]){//49 elementiv,  index Station_Tuda 0,18,34,43 / index Station_Nazad 0,9,25, 43
				     praporSt_N=1; 
				       
				   //vigruzka/zagruzka pasagiriv z/v potyaga	
				   // yaka Station?
				     StationV station = MetroRuch.stationsBlue.get(3-j);
					 synchronized (station.getPasagiriPeron()) {
					 if (!potyagNaLinii.get(tmp4).getVsixPasagirivPotyaga().isEmpty()) potyagNaLinii.get(tmp4).vixidV5Potokov();
					 if (!station.getPasagiriPeron().isEmpty()) potyagNaLinii.get(tmp4).vxidPv5Potokov(station, station.getPasagiriPeron());
				     }
					 potyagNaLinii.get(tmp4).setLichilnikSt(potyagNaLinii.get(tmp4).getLichilnikSt()+1);
					 break;
	}
				     else praporSt_N=0;
	}				
				if ((potyagNaLinii.get(tmp4).getLichilnikSt()>=8) ||
					(0==praporSt_N)){
					line_N[i+1]=line_N[i];
					line_N[i]=0;
					potyagNaLinii.get(tmp4).setCurrentPosition(potyagNaLinii.get(tmp4).getCurentPosition()+1);
	}
				if (potyagNaLinii.get(tmp4).getLichilnikSt()>=8) potyagNaLinii.get(tmp4).setLichilnikSt(0);
	}
	}	
	  //potyag dosyag ostanjogo elementa line_N -> perexid na line_T, currentPosition=0, LichilnikSt=0; 
	    if((0!=line_N [line_N.length-1]) &&
	   	   (0==line_T[0])) {
	    	//zbilshuu dosvid mashinista max=10000 poizdok (5r x 11 mis x 22 dib x 8 poizdok)
	    	for(Potyag p: potyagNaLinii){
	    		if (line_N [line_N.length-1]==p.getNomerPotyga()){
	    			if (p.getMashinist().getDosvid()<10000)  p.getMashinist().setDosvid(p.getMashinist().getDosvid()+1);
	    			//stvoruu klas poizdka ta dodau v arraylist poizdki
	    	    	   Poizdka fakt2 =  new Poizdka (p.getMashinist(), this.nazvaLinii+"_N");
	    	    	   MetroRuch.poizdki.add(fakt2);
	    	    	   //zamina mashinistiv
	    	    	    MetroRuch.driversBlueTMP.add(p.getMashinist());
	 	    	        if (MetroRuch.driversBlueTMP.size() >= MetroRuch.driversBlue.size()) {
	 	    	    		MetroRuch.driversBlue.addAll(MetroRuch.driversBlueTMP);
	 	    	    		MetroRuch.driversBlueTMP.clear();	    
	 	    	    	}
	 	    	    	if (!MetroRuch.driversBlue.isEmpty()) p.setMashinist(MetroRuch.driversBlue.poll());
	    		break;
	    		}}
	    	line_T[0]=line_N [line_N.length-1];
	   	    line_N [line_N.length-1]=0;
	   	    potyagNaLinii.forEach(p-> {
	   	    	if(line_T[0]==p.getNomerPotyga()) {
	   	    		p.setCurrentPosition(0);
	   	    		p.setLichilnikSt(0);}
	   	    	});
	} 
	    lichilnikTimer2++;   
	}
	}}
//*******************************************************************************
	void vipuskNaLiniuGreen (Potyag [] mP){

		 if ((mP.length>0)&&((mP.length+potyagNaLinii.size())<zagalnaL-1)){

			for (int i=0;i<mP.length;i++){	
				//perevirka nayavnosti mashinista
			if ((mP[i].getMashinist().getPIB()!="Jon Dow") &&
				(mP[i].getMashinist().getPIB()!=null)      &&
				(!mP[i].getMashinist().getPIB().isEmpty())){
		//perevirka nayavnosti 5 vagoniv MPPPM abo MXXXM
			    if((5==mP[i].getPotyg().size())&&
			       (mP[i].getPotyg().get(0).getNazvaVagona().equals("VagonMashinist")) &&
			       (mP[i].getPotyg().get(4).getNazvaVagona().equals("VagonMashinist"))){
		//perevirka nayavnosti RIZNICH machinistiv v potyagax na linii
				if(0==potyagNaLinii.size()){potyagNaLinii.add(mP[i]); prapor3=1;}
			    else {if (unikalMashinist (mP[i].getMashinist().getPIB(), potyagNaLinii)){
			                        potyagNaLinii.add(mP[i]); prapor3=1;
			    	  }
			         else System.out.println("Povtor Mashinista v potyazi N"+mP[i].getNomerPotyga());
			    	 }   
			    //potyagNaLinii.add(mP[i]);
				//prapor2=1;
				mP[i].setDepoKey("Na linii "+this.getNazvaLinii());
				mP[i].getPotyg().forEach(v->v.setNazvaDepoKey("Na linii "+this.getNazvaLinii()));
				mP[i].setLineKey(this.getNazvaLinii());
			    }
			    else System.out.println("Vidsutnij 5 vagoniv MPPPM v potyazi N"+mP[i].getNomerPotyga());
			}
			else {System.out.println("Vidsutnij mashinist v potyazi N"+mP[i].getNomerPotyga());}
			}	
		//distanziya mig potygami, elementiv, 1 element = 100 m.
		if (potyagNaLinii.size()>0) distanziya=(int)((2*zagalnaL)/(potyagNaLinii.size()));	
		//viznachau startPosition potyagiv na linii z uraxuvannyam distanzii,
		for (int i=0; i<potyagNaLinii.size(); i++){
			int dst=(potyagNaLinii.size()-1-i)*distanziya;
			potyagNaLinii.get(i).setCurrentPosition(dst);
		//nomeri potyagiv v masiv Line_T abo Line_N dlya ruchu 
			if (dst < zagalnaL) line_T[dst]=potyagNaLinii.get(i).getNomerPotyga(); 
			else                line_N[dst-zagalnaL]=potyagNaLinii.get(i).getNomerPotyga();
		}
		//start  ruchu potyagiv 144km/h=>2,5s=100m; 60km/h=>6,0s=100m; 0,5s-otladka	
		    if (1==prapor3){
		    	 firstTime=0;
			     period=500;//6000, 0,5s-dlya otladki
			     timer=new Timer("Timer3");
			     timer.schedule(new ZapuskPotyaga3 (0, this.getNazvaLinii()),firstTime, period); 
			     }
		}
		else {System.out.println("Vidsutnij potyag abo kilkist potygiv > zagalna dovgina linii");}
		}	

		class ZapuskPotyaga3 extends TimerTask {
			int currentPosition;
			String nazvaLinii;
		ZapuskPotyaga3(int cP, String nl){
				currentPosition=cP;
				nazvaLinii=nl;
		}
		public void run() {
		                   
		if(lichilnikTimer3 > 180){
			timer.cancel();
			lichilnikTimer3=0;
			}
		else{ System.out.println("TG "+Arrays.toString(line_T));
			  System.out.println("NG "+Arrays.toString(line_N)); 
			//druk v SchemaMetro line_T ta Line_N   
			for (int k=0; k<line_T.length; k++){ 
				  if (0!=line_T[k]) SchemaMetro.printGreenLine3_T(k, line_T[k]);
				  else              SchemaMetro.deleteGreenLine3_T(k);
				  if (0!=line_N[k]) SchemaMetro.printGreenLine3_N(k, line_N[k]);
				  else              SchemaMetro.deleteGreenLine3_N(k);	  
			  }
		      
			//obraxunok line_T
		    for (int i=line_T.length-2; i>=0; i--) {
				if ((0!=line_T[i])&&(0==line_T[i+1])) {
					int tmp5=0;//viznachau index potyaga v potyagNaLinii po nomeru v line_T[i]
					for(Potyag p: potyagNaLinii){
						if(line_T[i]==p.getNomerPotyga()) break;
						tmp5++;
						}			
					for (int j=0; j<indexSt_T.length; j++){//dosyag potyg stanzii?
					     if(i==indexSt_T[j]){//Greenline 50 elementiv,  index Station_Tuda 0,11,34,44 / index Station_Nazad 0,10,33, 44 
					     praporSt_T=1;      
					     //vigruzka/zagruzka pasagiriv z/v potyaga	
						// yaka Station?
					     StationV station = MetroRuch.stationsGreen.get(j);
						 synchronized (station.getPasagiriPeron()) {
						 if (!potyagNaLinii.get(tmp5).getVsixPasagirivPotyaga().isEmpty())   potyagNaLinii.get(tmp5).vixidV5Potokov();
						 if (!station.getPasagiriPeron().isEmpty())   potyagNaLinii.get(tmp5).vxidPv5Potokov(station, station.getPasagiriPeron());
						 }
						 potyagNaLinii.get(tmp5).setLichilnikSt(potyagNaLinii.get(tmp5).getLichilnikSt()+1);
						 break;
		}
					     else praporSt_T=0;
		}				
					// 8 kilkist chasu zupinki potyaga na statzii, 8x6s(6000 timer)=48s
					if ((potyagNaLinii.get(tmp5).getLichilnikSt()>=8) ||
						(0==praporSt_T)){
						line_T[i+1]=line_T[i];
						line_T[i]=0;
						potyagNaLinii.get(tmp5).setCurrentPosition(potyagNaLinii.get(tmp5).getCurentPosition()+1);
		}
					if (potyagNaLinii.get(tmp5).getLichilnikSt()>=8) potyagNaLinii.get(tmp5).setLichilnikSt(0);
		}
		}  //potyag dosyag ostanjogo elementa line_T -> perexid na line_N 
		    if((0!=line_T [line_T.length-1]) &&
		   	   (0==line_N[0])) {
		   	    //zbilshuu dosvid mashinista max=10000 poizdok (5r x 11 mis x 22 dib x 8 poizdok)
		    	for(Potyag p: potyagNaLinii){
		    		if (line_T [line_T.length-1]==p.getNomerPotyga()){
		    			if (p.getMashinist().getDosvid() < 10000)  p.getMashinist().setDosvid(p.getMashinist().getDosvid()+1);
		    			//stvoruu klas poizdka ta dodau v arraylist poizdki
		    	    	   Poizdka fakt3 =  new Poizdka (p.getMashinist(), this.nazvaLinii+"_T");
		    	    	   MetroRuch.poizdki.add(fakt3);
		    	    	 //zamina mashinistiv 
			    	    		MetroRuch.driversGreenTMP.add(p.getMashinist());
			 	    	    	if (MetroRuch.driversGreenTMP.size() >= MetroRuch.driversGreen.size()) {
			 	    	    		MetroRuch.driversGreen.addAll(MetroRuch.driversGreenTMP);
			 	    	    		MetroRuch.driversGreenTMP.clear();	    
			 	    	    	}
			 	    	    	if (!MetroRuch.driversGreen.isEmpty()) p.setMashinist(MetroRuch.driversGreen.poll());
		 	    	    	break;
		    		}}
		    	
		    	line_N[0]=line_T [line_T.length-1];
		   	    line_T [line_T.length-1]=0;
		   }       
		    //obraxunok line_N
		    for (int i=line_N.length-2; i>=0; i--) {
				if ((0!=line_N[i])&&(0==line_N[i+1])) {
					 int tmp6=0;//index potyaga v potyagNaLinii dlya line_N		
					for(Potyag p: potyagNaLinii){
						if(line_N[i]==p.getNomerPotyga()) break;
						tmp6++;
						}			
					for (int j=0; j<indexSt_N.length; j++){//dosyag potyg stanzii?
					     if(i==indexSt_N[j]){//Greenline 50 elementiv,  index Station_Tuda 0,11,34,44 / index Station_Nazad 0,10,33, 44 
					     praporSt_N=1; 
					       
					   //vigruzka/zagruzka pasagiriv z/v potyaga	
					   // yaka Station?
					     StationV station = MetroRuch.stationsGreen.get(3-j);
						 synchronized (station.getPasagiriPeron()) {
						 if (!potyagNaLinii.get(tmp6).getVsixPasagirivPotyaga().isEmpty()) potyagNaLinii.get(tmp6).vixidV5Potokov();
						 if (!station.getPasagiriPeron().isEmpty()) potyagNaLinii.get(tmp6).vxidPv5Potokov(station, station.getPasagiriPeron());
					     }
						 potyagNaLinii.get(tmp6).setLichilnikSt(potyagNaLinii.get(tmp6).getLichilnikSt()+1);
						 break;
		}
					     else praporSt_N=0;
		}				
					if ((potyagNaLinii.get(tmp6).getLichilnikSt()>=8) ||
						(0==praporSt_N)){
						line_N[i+1]=line_N[i];
						line_N[i]=0;
						potyagNaLinii.get(tmp6).setCurrentPosition(potyagNaLinii.get(tmp6).getCurentPosition()+1);
		}
					if (potyagNaLinii.get(tmp6).getLichilnikSt()>=8) potyagNaLinii.get(tmp6).setLichilnikSt(0);
		}
		}	
		  //potyag dosyag ostanjogo elementa line_N -> perexid na line_T, currentPosition=0, LichilnikSt=0; 
		    if((0!=line_N [line_N.length-1]) &&
		   	   (0==line_T[0])) {
		    	//zbilshuu dosvid mashinista max=10000 poizdok (5r x 11 mis x 22 dib x 8 poizdok)
		    	for(Potyag p: potyagNaLinii){
		    		if (line_N [line_N.length-1]==p.getNomerPotyga()){
		    			if (p.getMashinist().getDosvid()<10000)  p.getMashinist().setDosvid(p.getMashinist().getDosvid()+1);
		    			//stvoruu klas poizdka ta dodau v arraylist poizdki
		    	    	   Poizdka fakt4 =  new Poizdka (p.getMashinist(), this.nazvaLinii+"_N");
		    	    	   MetroRuch.poizdki.add(fakt4);
		    	    	   //zamina mashinistiv 
			    	    		MetroRuch.driversGreenTMP.add(p.getMashinist());
			 	    	    	if (MetroRuch.driversGreenTMP.size() >= MetroRuch.driversGreen.size()) {
			 	    	    		MetroRuch.driversGreen.addAll(MetroRuch.driversGreenTMP);
			 	    	    		MetroRuch.driversGreenTMP.clear();	    
			 	    	    	}
			 	    	    	if (!MetroRuch.driversGreen.isEmpty()) p.setMashinist(MetroRuch.driversGreen.poll());
		    		break;
		    		}}
		    	line_T[0]=line_N [line_N.length-1];
		   	    line_N [line_N.length-1]=0;
		   	    potyagNaLinii.forEach(p-> {
		   	    	if(line_T[0]==p.getNomerPotyga()) {
		   	    		p.setCurrentPosition(0);
		   	    		p.setLichilnikSt(0);}
		   	    	});
		} 
		    lichilnikTimer3++;   
		}
		}}
}
//Arrays.sort(zPlata);