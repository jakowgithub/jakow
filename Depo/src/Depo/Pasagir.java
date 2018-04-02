package Depo;
import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "pasagir")
public class Pasagir implements Serializable {

	@DatabaseField(persisted = false)
	private static final long sVer = 1L;
	
	@DatabaseField(generatedId = true) 
	private  int idPasagir;
	
	@DatabaseField(index = true)
	private String pIB;
	
	@DatabaseField()
	private String iPN;
	
	@DatabaseField()
	private int vik;
	
	@DatabaseField()
	private int vagonKey;
	
	@DatabaseField()
	private int potyagKey;
	
	@DatabaseField()
	private String stationKey;
	
	
	Pasagir(){}
	
	public Pasagir(String pIB, String iPN, int vik) {
		super();
		this.pIB = pIB;
		this.iPN = iPN;
		this.vik = vik;
	}

	@Override
	public String toString() {return "Pasagir [pIB=" + pIB + "]";}
	
	public int getIdPasagir() {return idPasagir;}
	public void setIdPasagir(int idPasagir) {this.idPasagir = idPasagir;}
	public String getPIB() {return pIB;}
	public void setPIB(String pIB) {this.pIB = pIB;}
	public String getIPN() {return iPN;}
	public void setIPN(String iPN) {this.iPN = iPN;}
	public int getVik() {return vik;}
	public void setVik(int vik) {this.vik = vik;}
	public static long getSver() {return sVer;}

	public int getVagonKey() {return vagonKey;}
	public void setVagonKey(int vagonKey) {this.vagonKey = vagonKey;}
	public int getPotyagKey() {return potyagKey;}
	public void setPotyagKey(int potyagKey) {this.potyagKey = potyagKey;}
	public String getStationKey() {return stationKey;}
	public void setStationKey(String stationKey) {this.stationKey = stationKey;}
	
}
