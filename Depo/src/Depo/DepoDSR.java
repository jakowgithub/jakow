package Depo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
//import java.util.List;

public class DepoDSR {
	
public static Depo dsr (String iF){
Depo d=null;
try (ObjectInputStream in = new ObjectInputStream(
	  new FileInputStream(iF))) {
Depo d2 = (Depo) in.readObject();
//p.forEach(plt-> System.out.println(plt.nomer+" "+plt.data+ " "+plt.suma));
d=d2;
}
catch (FileNotFoundException e) {e.printStackTrace();} 
catch (IOException e) {	e.printStackTrace();} 
catch (ClassNotFoundException e) {e.printStackTrace();}

return d;
	}
}
