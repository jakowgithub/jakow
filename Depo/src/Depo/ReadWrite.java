package Depo;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.io.File;
//import java.nio.file.LinkOption;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
//import java.util.LinkedList;
import java.util.List;
//import java.util.SortedMap;

public class ReadWrite {
	
	static void writeDepo (Depo d){
Path to=Paths.get(new File (System.getProperty("user.dir")).getAbsolutePath()+System.getProperty("file.separator")+d.getNazvaDepo()+".txt");
		
try {if (Files.notExists(to)) Files.createFile(to);
     else {Files.deleteIfExists(to); Files.createFile(to);}
	String result="";		 
for(Object ob:d.getDepo()){
	if (ob instanceof Vagon){ Vagon v=(Vagon)ob;
       result=	d.getNazvaDepo()+" "+
		        d.getKilkistVMDepo()+" "+
        		d.getKilkistVPDepo()+" "+
        		d.getKilkistPotygiv()+" "+
        		"Vagon"+" "+
        		v.getNazvaVagona()+" "+ 
        		v.getNomerVagona()+" "+
		        v.getEmnistVagona()+" "+
        		v.getKilkistPasagirVVagone();
        if ((Files.isWritable(to)&&(Files.exists(to)))) {
			 String r=result.trim();
			 if ((null!=r) && (!r.isEmpty())) {
			   	Files.write(to, r.getBytes(), StandardOpenOption.APPEND); 
			   	Files.write(to, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
		    		 }}
			 else System.out.println("Fail nedostupnij dlya zapisu.");
				}
	if (ob instanceof Potyag){Potyag p=(Potyag)ob;
		    for(Vagon v:p.getPotyg()){
			 result=d.getNazvaDepo()+" "+
				    d.getKilkistVMDepo()+" "+
		        	d.getKilkistVPDepo()+" "+
		        	d.getKilkistPotygiv()+" "+
		        	"Potyag"+" "+
					p.getNomerPotyga()+" "+
					p.getStation()+" "+
				    "Mashinist"+" "+
					p.getMashinist().getPIB()+" "+
					p.getMashinist().getIPN()+" "+
					"Vagon"+" "+
			        v.getNazvaVagona()+" "+ 
			        v.getNomerVagona()+" "+
					v.getEmnistVagona()+" "+
			        v.getKilkistPasagirVVagone();
					if ((Files.isWritable(to)&&(Files.exists(to)))) {
						 String r=result.trim();
						 if ((null!=r) && (!r.isEmpty())) {
						   	Files.write(to, r.getBytes(), StandardOpenOption.APPEND); 
						   	Files.write(to, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
						    		 }}
						 else System.out.println("Fail nedostupnij dlya zapisu.");
	            }}			
}}
		catch (NullPointerException npe){npe.printStackTrace();System.out.println("NullPointerException");}
	    catch (IOException ioe){ioe.printStackTrace();System.out.println("IOException");}
}
	
static Depo readDepo (String imyaFile){
	Depo d=new Depo();	
	Potyag pTEMP=new Potyag();
	Path from = Paths.get(new File (System.getProperty("user.dir")).getAbsolutePath()+System.getProperty("file.separator")+imyaFile);
try{ if (Files.isReadable(from)) {
     List <String> ryadki= Files.readAllLines((from),Charset.defaultCharset());
     int  i=0, j=0; 
     String rydokPoper="";
     for (String r: ryadki ){
          String[] data=r.split(" ");
          String [] dataPoper=rydokPoper.split(" ");
          if(0==i) {
        	  d.setNazvaDepo (NazvaDepo.valueOf(data[0].trim()));
        	  d.setKilkistVMDepo(Integer.parseInt(data[1].trim()));
        	  d.setKilkistVPDepo(Integer.parseInt(data[2].trim()));
        	  d.setKilkistPotygiv(Integer.parseInt(data[3].trim()));
          }
          if (data[4].trim().equalsIgnoreCase("Vagon")){
        	  Vagon v=new Vagon(Integer.parseInt(data[6]),data[5]);
        	  if (data[5].trim().equalsIgnoreCase("VagonPasagir"))v.setEmnistVagona(220);
        	  if (data[5].trim().equalsIgnoreCase("VagonMashinist"))v.setEmnistVagona(210);
        	  v.setKilkistPasagirVVagone(0);
        	  d.dodatVdepo(v);
          }
          if (data[4].trim().equalsIgnoreCase("Potyag")){
        	  
        	  if((!(data[5].trim().equalsIgnoreCase(dataPoper[5].trim()))&&
        		 (!rydokPoper.isEmpty())) && (0!=j) ){
        		  Potyag p=new Potyag();
        		  p.setNomerPotyga(Integer.parseInt(dataPoper[5].trim()));
        		  p.setStation(NazvaDepo.valueOf(dataPoper[6].trim()));
        		  pTEMP.getPotyg().forEach(v-> p.getPotyg().add(v));
        		  pTEMP.getPotyg().removeAll(p.getPotyg());
        		  p.setMashinist(new Mashinist (dataPoper[8].trim(), dataPoper[9].trim()));
        		  d.dodatVdepo(p);
        	  } 
        	  Vagon v=new Vagon(Integer.parseInt(data[12]),data[11]);  
           	  if (data[11].trim().equalsIgnoreCase("VagonPasagir"))v.setEmnistVagona(220);
        	  if (data[11].trim().equalsIgnoreCase("VagonMashinist"))v.setEmnistVagona(210);
        	  v.setKilkistPasagirVVagone(0);
        	  pTEMP.getPotyg().add(v);
        	  
        	  if (ryadki.size()-1==i){
            	  Potyag p=new Potyag();
        		  p.setNomerPotyga(Integer.parseInt(data[5].trim()));
        		  p.setStation(NazvaDepo.valueOf(data[6].trim()));
        		  pTEMP.getPotyg().forEach(vag-> p.getPotyg().add(vag));
        		  pTEMP.getPotyg().removeAll(p.getPotyg());
        		  p.setMashinist(new Mashinist (data[8].trim(), data[9].trim()));
        		  d.dodatVdepo(p);
                 }   
                 j++;
          }
          rydokPoper=ryadki.get(i);
          i++;
     }
}}
catch (NullPointerException npe){npe.printStackTrace();System.out.println("NullPointerException");}
catch (IOException ioe){ioe.printStackTrace();System.out.println("IOException");}

return d;
}}
