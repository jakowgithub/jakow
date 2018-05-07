package Depo;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "depo")
public class Depo implements Serializable {
	
	@DatabaseField(persisted = false)
	private static final long serialVersionUID = 1L;
	
	@DatabaseField(generatedId = true) 
	private  int idDepo;
	
	@DatabaseField(persisted = false)
	private Enum <NazvaDepo> nazvaDepo;
	
	@DatabaseField()
	private int kilkistVPDepo; 
	
	@DatabaseField()
	private int kilkistVMDepo;
	
	@DatabaseField()
	private int maxNomerVagona;
	
	@DatabaseField()
	private int kilkistPotygiv;
	
	@DatabaseField(persisted = false)
	private List <Object> depo = new ArrayList<Object> ();
	
	@DatabaseField()
	private String nazvaDepoString;
	
	Depo(){};
//konstruktor stvorue vagoni Pas, Mash v Depo	
    Depo (int  kVPDepo,int  kVMDepo,Enum <NazvaDepo> nazvaD){
		kilkistVPDepo=kVPDepo;
		kilkistVMDepo=kVMDepo;
		nazvaDepo=nazvaD;
		nazvaDepoString=nazvaDepo.toString();
		
if (kilkistVPDepo>0){
	for (int i=1; i<=kilkistVPDepo; i++){
	Vagon vP= new Vagon (i+9000,"VagonPasagir");
	vP.setEmnistVagona(220);
	vP.setNazvaDepoKey(nazvaDepo.toString());
	vP.setNomerPotyagaKey(0);//yakcho vagon ne v potyazi
	depo.add(vP);
			}
		maxNomerVagona=kilkistVPDepo+1;
		}
if (kilkistVMDepo>0){
		for (int i=1; i<=kilkistVMDepo; i++){
			Vagon vP= new Vagon (i+9000+maxNomerVagona-1,"VagonMashinist");
			vP.setEmnistVagona(210);
			vP.setNazvaDepoKey(nazvaDepo.toString());
			vP.setNomerPotyagaKey(0);//yakcho vagon ne v potyazi  
			depo.add(vP);
			}
			maxNomerVagona+=kilkistVMDepo;	
} }	
	//klonuvannya depo
	Depo (Depo dep){this(dep.getKilkistVPDepo(), dep.getKilkistVMDepo(),dep.getNazvaDepo());}
	
int getKilkistVPDepo (){return kilkistVPDepo;}
int getKilkistVMDepo (){return kilkistVMDepo;}
int getKilkistPotygiv (){return kilkistPotygiv;}
Enum <NazvaDepo> getNazvaDepo()  {return nazvaDepo;}
List <Object> getDepo(){return depo;}
int getMaxNomerVagona (){return maxNomerVagona;}
String getNazvaDepoString (){return nazvaDepoString;}

void dodatVdepo (Object ob){
	if (ob instanceof Vagon){ Vagon v=(Vagon)ob;
		if (v.getNazvaVagona().equalsIgnoreCase("VagonPasagir")) kilkistVPDepo++; 
		if (v.getNazvaVagona().equalsIgnoreCase("VagonMashinist")) kilkistVMDepo++;
		    depo.add(ob);
		    }
		if(ob instanceof Potyag){Potyag p=(Potyag)ob;
			setKilkistPotygiv (kilkistPotygiv+1); 
			p.setDepoKey(getNazvaDepo().toString());
			p.setLineKey(getNazvaDepo().toString());
			depo.add(ob);
			}	
}	
void  setKilkistVPDepo (int kVP){kilkistVPDepo=kVP;}
void  setKilkistVMDepo (int kVM){kilkistVMDepo=kVM;}
void  setKilkistPotygiv (int kP){kilkistPotygiv=kP;}
void  setNazvaDepo (Enum <NazvaDepo> nD){nazvaDepo=nD;}
void setNazvaDepoString (String nd){nazvaDepoString=nd;}

	public static void main(String[] args) {
//2.Stvoruu Depo -stvoruu vagoni 6-VagonPas, 4-VagonMach.
	Depo depo1=new Depo (6,4, NazvaDepo.DepoDarniza);
//Stvoruu potygi z vagoni ne depo				
	Potyag potyag1= new Potyag(1,NazvaDepo.DepoDarniza); 
	Potyag potyag2= new Potyag(2,NazvaDepo.DepoGeroivDnipra);
	Potyag potyag3= new Potyag(3,NazvaDepo.DepoDarniza);
//priznachiv Mashinistiv
	potyag1.setMashinist(new Mashinist("Ivanenko", "1111111111"));
	potyag2.setMashinist(new Mashinist("Petrenko", "2222222222"));
	potyag3.setMashinist(new Mashinist("Kozak", "4444444444"));
	Mashinist xXX=new Mashinist("XXX", "5555555555");
	
//Dodau potyagi v depo
	depo1.dodatVdepo(potyag1);
	depo1.dodatVdepo(potyag2);
	depo1.dodatVdepo(potyag3);
//Stvoruu potygi z vagoni depo
	Potyag potyag4= new Potyag(depo1);
	potyag4.setMashinist(new Mashinist("Sidorenko", "3333333333"));
	//Dodau potyag v depo
	depo1.dodatVdepo(potyag4);
	
	for(Object ob:depo1.depo){
		if (ob instanceof Vagon){ Vagon v=(Vagon)ob;
        System.out.println(depo1.nazvaDepo+" "+v.getNazvaVagona()+" "+ v.getNomerVagona()+" "+v.hashCode());
			}
		System.out.println();
		if (ob instanceof Potyag){Potyag p=(Potyag)ob;
			for(Vagon v:p.getPotyg()){
            System.out.println(depo1.nazvaDepo+" "+p.getNomerPotyga()+" "+v.getNazvaVagona()+" "+ v.getNomerVagona()+" "+v.hashCode());
            }}
             }	
	System.out.println("--------------------------------------------------------------------------");
	//System.out.println(NazvaDepo.valueOf("DepoDarniza"));
	
ReadWrite.writeDepo(depo1);
Depo depo2=null;
//Depo depo2=ReadWrite.readDepo("DepoDarniza.txt");
for ( NazvaDepo nD:NazvaDepo.values()){
Path from=Paths.get(new File (System.getProperty("user.dir")).getAbsolutePath()+System.getProperty("file.separator")+nD+".txt");
if (Files.exists(from)) depo2=ReadWrite.readDepo(nD+".txt");
}	

for(Object ob:depo2.depo){
	if (ob instanceof Vagon){ Vagon v=(Vagon)ob;
    System.out.println(depo2.nazvaDepo+" "+v.getNazvaVagona()+" "+ v.getNomerVagona()+" "+v.hashCode());
		}
	System.out.println();
	if (ob instanceof Potyag){Potyag p=(Potyag)ob;
		for(Vagon v:p.getPotyg()){
        System.out.println(depo2.nazvaDepo+" "+p.getNomerPotyga()+" "+v.getNazvaVagona()+" "+ v.getNomerVagona()+" "+v.hashCode());
        }}
         }	
System.out.println("================================================================================");
 
DepoSR.sr(depo1);
Depo depo3=DepoDSR.dsr("DepoDarniza-SR.txt");
for(Object ob:depo3.depo){
	if (ob instanceof Vagon){ Vagon v=(Vagon)ob;
    System.out.println(depo3.nazvaDepo+" "+v.getNazvaVagona()+" "+ v.getNomerVagona()+" "+v.hashCode());
		}
	System.out.println();
	if (ob instanceof Potyag){Potyag p=(Potyag)ob;
		for(Vagon v:p.getPotyg()){
        System.out.println(depo3.nazvaDepo+" "+p.getNomerPotyga()+" "+v.getNazvaVagona()+" "+ v.getNomerVagona()+" "+v.hashCode());
        }}
         }	
System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");

Line redLine1=new Line(1);
redLine1.vipuskNaLiniu(potyag1,potyag2,potyag3,potyag4 );

}}
