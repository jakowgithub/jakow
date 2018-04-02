package Depo;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class DepoSR {
	public 	static void sr(Depo d){ 
		String nD=d.getNazvaDepo().toString();
		//serializing it
	try (
		ObjectOutputStream out = new ObjectOutputStream(
				                 new FileOutputStream(nD+"-SR.txt"))) {
			  				         out.writeObject(d);} 
		 catch (IOException e) {e.printStackTrace();}
		}	
}
