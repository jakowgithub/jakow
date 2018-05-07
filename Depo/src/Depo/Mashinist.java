package Depo;
import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author Sergey.V.Yakovenko
 *
 */
@DatabaseTable(tableName = "mashinist")
public class Mashinist implements Serializable{
	
	//@DatabaseField(version = true, columnName = "vers")
	@DatabaseField(persisted = false)
	private static final long sVer = 1L;
	
	@DatabaseField(id = true)
	private String pIB;
	
	@DatabaseField(canBeNull = false)
	private String iPN;
	
	@DatabaseField()
	private int dosvid;

Mashinist(String pr,  String nom) {
	super(); 
	pIB=pr; 
	iPN=nom;
	dosvid=0;
	}
Mashinist(String pr,  String nom, int dos) {
	super(); 
	pIB=pr; 
	iPN=nom;
	dosvid=dos;
	}
Mashinist(){}

String getPIB (){return pIB; }
String getIPN(){return iPN;}
int getDosvid(){return dosvid;}

void setPIB (String p){pIB=p;}
void setIPN (String n){iPN=n;}
void setDosvid (int d){dosvid=d;}

long getSerialVersionUID(){return sVer;}
@Override
public String toString() {
	return "Mashinist ["+ pIB + ", iPN " + iPN + ", dosvid " + dosvid + "]";
}

}
