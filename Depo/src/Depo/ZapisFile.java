package Depo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.PriorityBlockingQueue;

public class ZapisFile {

	//metod zapisue v koren proeta file "Poizdka.txt" 
	//(abo  "getClass().getSimpleName().txt") 
	public static <T> void saveFile (CopyOnWriteArrayList<T> poizdki){
		if (!poizdki.isEmpty()) {
		//File fileTelefon=new File (System.getProperty("java.class.path"));
		File filePoizdki=new File (System.getProperty("user.dir"));
		String path=filePoizdki.getAbsolutePath();
		//Path pathNew=Paths.get(path+"/Poizdki.txt");
		String imyaFile = poizdki.get(0).getClass().getSimpleName();
		Path pathNew=Paths.get(path+"/"+imyaFile+".txt");
		Path from = pathNew;
		//Path to = Paths.get(path+"/PoizdkiPoperedni.txt");
		Path to = Paths.get(path+"/"+imyaFile+"Poperednya.txt");
		if (Files.exists(pathNew)){
		try {Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
			 Files.deleteIfExists(pathNew);
		     Files.createFile(pathNew);
		    } catch (IOException ioe){ ioe.printStackTrace();System.out.println("Pomilka vidalennya/stvorennya");} 
		      catch (NullPointerException npe){ npe.printStackTrace();System.out.println("NullPointerException");}
		}
		else {try {
			Files.createFile(pathNew);
		     }catch (IOException ioe){ ioe.printStackTrace();System.out.println("Pomilka stvorennya");}
		}
		if(Files.isWritable(pathNew)){
		    int i=1;
		    for (T poizdka: poizdki) {
			   try { 
					Files.write(pathNew, (poizdka.toString()+"N" + i).getBytes(), StandardOpenOption.APPEND); 
				    Files.write(pathNew, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
				    i++;
				    }catch (IOException e){ e.printStackTrace();System.out.println("Pomilka Zapisu");}
		   } 
		   }}}
	
//metod zapisue v koren proeta file "Mashinist.txt" (abo  "simpleName clasu T.txt") 
//int peek = 1,2,4,5,.. - rangirovanie ne garantovane (vikoristovuu iterator), 
//int peek =3-rangirovanie garantovane (vikoristivuu pool).
	public static <T> void saveFileDriver (PriorityBlockingQueue <T> drivers, int peek){
		
		if (!drivers.isEmpty()) {
			//File fileTelefon=new File (System.getProperty("java.class.path"));
		File file=new File (System.getProperty("user.dir"));
		String path=file.getAbsolutePath();
			//Path pathNew=Paths.get(path+"/Poizdki.txt");
		String imyaFile = drivers.peek().getClass().getSimpleName();
		Path pathNew=Paths.get(path+"/"+imyaFile+".txt");
		Path from = pathNew;
			//Path to = Paths.get(path+"/PoizdkiPoperedni.txt");
		Path to = Paths.get(path+"/"+imyaFile+"Poperednya.txt");
		if (Files.exists(pathNew)){
			try {Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
				 Files.deleteIfExists(pathNew);
			     Files.createFile(pathNew);
			    } catch (IOException ioe){ ioe.printStackTrace();System.out.println("Pomilka vidalennya/stvorennya");} 
			      catch (NullPointerException npe){ npe.printStackTrace();System.out.println("NullPointerException");}
			}
		else {try {Files.createFile(pathNew);
			      }catch (IOException ioe){ ioe.printStackTrace();System.out.println("Pomilka stvorennya");}
			 }
		if(Files.isWritable(pathNew)) {
			int i=1;
			if (3==peek) {
				while (!drivers.isEmpty()) {
				try { Files.write(pathNew, (drivers.poll().toString()+"N" + i).getBytes(), StandardOpenOption.APPEND); 
		              Files.write(pathNew, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
		              i++;
		        }catch (IOException e){ e.printStackTrace();System.out.println("Pomilka Zapisu");}
				}}
			else {for (T driver: drivers){
				  try { Files.write(pathNew, (driver.toString()+"N" + i).getBytes(), StandardOpenOption.APPEND); 
					    Files.write(pathNew, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
					    i++;
					   }catch (IOException e){ e.printStackTrace();System.out.println("Pomilka Zapisu");}
			   }}
			   }}}
}
