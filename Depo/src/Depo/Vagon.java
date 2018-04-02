package Depo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "vagon")
public class Vagon implements Serializable {
	
	@DatabaseField(persisted = false)
	private static final long sVer = 1L;
	
	@DatabaseField(id = true, canBeNull=false)
	private int nomerVagona;
	
	@DatabaseField()
	private int kilkistPasagirVVagone;
	
	@DatabaseField()
	private int emnistVagona;
	
	@DatabaseField()
	private String nazvaVagona;
	
	@DatabaseField()
	private final int dlinaVagona=20;
	
	@DatabaseField()
	private int nomerPotyagaKey;
	
	@DatabaseField()
	private String nazvaDepoKey;
	
	@DatabaseField(persisted = false)
	private List <Pasagir> pasagiriVagon = new ArrayList<Pasagir> ();
	
	Vagon (int nomerV, String nazvaV){
		super();
		nomerVagona=nomerV;
		nazvaVagona=nazvaV;
	}
	Vagon (Vagon vag){this(vag.getNomerVagona(),vag.getNazvaVagona());}	
	
	Vagon (){}		

	int    getNomerVagona(){return nomerVagona;}
	String getNazvaVagona(){return nazvaVagona;}
	int    getKilkistPasagirVVagone(){return kilkistPasagirVVagone;}
	int    getEmnistVagona(){return emnistVagona;}
	int    getDlinaVagona(){return dlinaVagona;}
	int    getNomerPotyagaKey(){return nomerPotyagaKey;}
 String    getNazvaDepoKey(){return nazvaDepoKey;}
 List <Pasagir> getPasigirVagon(){return pasagiriVagon;}
	
	void setNomerVagona(int nomerV){nomerVagona=nomerV;}
	void setNazvaVagona(String nazvaV){nazvaVagona=nazvaV;}
	void setKilkistPasagirVVagone(int pasV){kilkistPasagirVVagone=pasV;}
	void setEmnistVagona(int eV){emnistVagona=eV;}
	void setNomerPotyagaKey(int npk){nomerPotyagaKey=npk;}
	void setNazvaDepoKey(String ndk){nazvaDepoKey=ndk;}
//metod povertae TAK ta zavantague pasagira v vagon, yakcho e misze v vagoni	
    boolean  setPasigirVagon( Pasagir pas){ 
		if ((pas!=null) && 
		   (pasagiriVagon.size()+1 <= emnistVagona)){	
	    		pas.setStationKey("V vagone");
	    		pas.setPotyagKey(getNomerPotyagaKey());
	    		pas.setVagonKey(getNomerVagona());
		    	setKilkistPasagirVVagone(pasagiriVagon.size()+1);
		    	return pasagiriVagon.add(pas);
		}
	    else return false;
	}
	@Override
	public String toString() {
		return "Vagon [nomerVagona=" + nomerVagona + ", kilkistPasagirVVagone=" + kilkistPasagirVVagone
				+ ", emnistVagona=" + emnistVagona + ", nazvaVagona=" + nazvaVagona + ", nomerPotyagaKey="
				+ nomerPotyagaKey + ", nazvaDepoKey=" + nazvaDepoKey + ", pasagirVagon=" + pasagiriVagon.toString() + "]";
	}
	

}
