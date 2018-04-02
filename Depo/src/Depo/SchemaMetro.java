package Depo;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import java.awt.event.*;

public class SchemaMetro {
// ogoloshuu vsi komponenti
	private JPanel panelPivden=new JPanel(); 
	private JPanel panelPivnich=new JPanel();
	private JPanel panelEast=new JPanel();
	private JPanel panelWest=new JPanel();
	private JPanel panelZentr= new JPanel();
	private JPanel vmistVikna=new JPanel();

	private JLabel lablePivnich=new JLabel();
	private JLabel lablePivden=new JLabel();
	private JLabel lableEast=new JLabel();
	private JLabel lableWest=new JLabel();
	
	static private JLabel labelPole [][]=new JLabel [50][60];
	static private JButton buttonStation [][]=new JButton [3][4];
	static private JButton buttonRedLine1 []=new JButton [120];
	static private JButton buttonBlueLine2 []=new JButton [98];
	static private JButton buttonGreenLine3 []=new JButton [100];
    private JButton buttonStart=new JButton("Start");
    private JButton buttonStop=new JButton("Stop");
    private JButton buttonWest []=new JButton [22];
    static Font kursiv=new Font ("TimesRoman", Font.BOLD, 8);
    static Font k8=new Font ("TimesRoman", Font.ITALIC, 8);
    static Border ramkaGovta1 = BorderFactory.createLineBorder(Color.YELLOW, 1);
    static Border ramkaGovta3 = BorderFactory.createLineBorder(Color.YELLOW, 3);
    
    SchemaMetro(){
panelZentr.setLayout(new GridLayout (50,60));
panelPivnich.add(lablePivnich);
panelPivden.add(lablePivden);
panelEast.add(lableEast);
int b=0, r=0, g=0, bRL1=0, bBL2=0, bGL3=0;

MetroRuch mr = new MetroRuch(this);

panelWest.setLayout(new GridLayout (24,1));
panelWest.add(buttonStart); 
buttonStart.addActionListener((ae)->{mr.lines = mr.metroRuch();}); 
panelWest.add(buttonStop); //buttonStop.setFont(kursiv);
buttonStop.addActionListener((ae)->{mr.metroStop(mr.getlines());}); 
for(int i=0; i<22; i++){
	buttonWest[i]=new JButton("S "+(i+1)*6);//buttonWest[i].setFont(kursiv);
	panelWest.add(buttonWest[i]);
	buttonWest[i].addActionListener((ae)->{});
	}
for (int i=0; i<3; i++){
    for (int j=0; j<4;j++){ 
    buttonStation [i][j]=new JButton("S"+((i+1)*10+(j+1)));
    buttonStation [i][j].setFont(kursiv);
    buttonStation [i][j].setBorder(ramkaGovta3);
}}
for (int i=0; i<120; i++){buttonRedLine1 [i]=new JButton(); buttonRedLine1 [i].setFont(k8);} 
for (int i=0; i<98; i++){buttonBlueLine2 [i]=new JButton(); buttonBlueLine2 [i].setFont(k8);}
for (int i=0; i<100; i++){buttonGreenLine3 [i]=new JButton();}
//new JButton(""+i); buttonGreenLine3 [i].setFont(k6); buttonGreenLine3 [i].setBorder(ramkaGovta1);
for (int i=0; i<50; i++){
    for (int j=0; j<60; j++){ 
    	labelPole [i][j]=new JLabel();	
if ((41==j)&&((2==i)||(20==i)||(36==i)||(45==i))){ 
	//buttonStation [1][b].setBackground(Color.BLUE);	
	panelZentr.add(buttonStation [1][b]);
	    b++;	
	} 
else {if((33==i)&&((2==j)||(18==j)||(38==j)||(56==j))){
	//buttonStation [0][r].setBackground(Color.RED);	        
	panelZentr.add(buttonStation [0][r]);
             r++;
    }       
else {if(((3==i)&&(48==j))  ||
	     ((13==i)&&(38==j)) ||     
	     ((36==i)&&(15==j)) ||
         ((47==i)&&(4==j))){
	      //buttonStation [2][g].setBackground(Color.GREEN);	
          panelZentr.add(buttonStation [2][3-g]);
           g++;
    }	
else {if (((40==j)||(42==j))&&(i<=47) ||
		  ((41==j)&&((0==i)||(47==i)))) {
//kolir fona, obovyazkovo .setOpaque (true)
     //labelPole [i][j].setBackground(Color.BLUE);
     //labelPole [i][j].setOpaque (true);
     //panelZentr.add(labelPole [i][j]);
	buttonBlueLine2 [bBL2].setBackground(Color.BLUE);
	panelZentr.add(buttonBlueLine2 [bBL2]);
	bBL2++;
	}		
else {if ((((32==i)||(34==i))&&(j<=58)) ||
		  ((33==i)&&((0==j)||(58==j)))) {
	     buttonRedLine1 [bRL1].setBackground(Color.RED);
	     panelZentr.add(buttonRedLine1 [bRL1]);
	     bRL1++;
	  }
else {if (((48==i)&&(2==j)) ||
		  ((49==i)&&(2==j)) ||
		  ((49==i)&&(3==j)) ||
		  ((1==i)&&(49==j)) ||
		  ((1==i)&&(50==j)) ||
		  ((2==i)&&(50==j)) ||
		  ((i>=1)&&(i<=47)&&((i+j)==49)) ||
		  ((i>=3)&&(i<=49)&&((i+j)==53))) {
	buttonGreenLine3[bGL3].setBackground(Color.GREEN);
	panelZentr.add(buttonGreenLine3[bGL3]);
	bGL3++;
	  }
else panelZentr.add(labelPole [i][j]);	    
}}}}}
}}    
//Zadau sxemu dlya paneli vmistVikna
vmistVikna.setLayout(new BorderLayout ());
vmistVikna.add(panelZentr,"Center");
vmistVikna.add(panelPivnich,"North");
vmistVikna.add(panelPivden,"South");
vmistVikna.add(panelEast,"East");
vmistVikna.add(panelWest,"West");

//Stvoruu Frame ta viznachau ego osnovnu panel
JFrame frame = new JFrame ("METRO");
frame.setContentPane(vmistVikna);
//Zadau dostatnij rozmir vikna
frame.pack();
//frame.setSize(230, 650);
//Vidobragaem vikno
frame.setVisible(true);
//vstanjvluu operaziu pri zakritti vikna
frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);	
 }
//metod  zminue kolir v JButton na govtij
static void setKolirKnopkiGovtij(int x, JButton buttonL []){
	buttonL[x].setBackground(Color.YELLOW);
	}
//metod  zminue kolir v JButton na kolir linii
 static void setKolirKnopkiPotochnij(int x, JButton buttonL []){
	int currentX=x;
	Color currentColor=Color.ORANGE;
	if(buttonL.equals(buttonRedLine1))currentColor=Color.RED;
	if(buttonL.equals(buttonBlueLine2))currentColor=Color.BLUE;
	if(buttonL.equals(buttonGreenLine3))currentColor=Color.GREEN;	
	buttonL[currentX].setBackground(currentColor);
	}
//metod  dodae govtu ramku v JButton 
static void setRamkaGovta (int x, JButton buttonL []){
	int currentX=x;
	if(buttonL.equals(buttonRedLine1)){
		buttonRedLine1[currentX].setFont(kursiv);
		buttonRedLine1[currentX].setBorder(ramkaGovta1);
		}
	if(buttonL.equals(buttonBlueLine2)){
		buttonBlueLine2[currentX].setFont(kursiv);
		buttonBlueLine2[currentX].setBorder(ramkaGovta1);
		}
	if(buttonL.equals(buttonGreenLine3)){
		buttonGreenLine3[currentX].setFont(kursiv);
		buttonGreenLine3[currentX].setBorder(ramkaGovta1);
		}
}
//metod  pribirae govtu ramku z JButton 
static void resetRamkaGovta (int x, JButton buttonL []){
	int currentX=x;
	if(buttonL.equals(buttonRedLine1))   buttonRedLine1[currentX].setBorder(null);
	if(buttonL.equals(buttonBlueLine2))  buttonBlueLine2[currentX].setBorder(null);
	if(buttonL.equals(buttonGreenLine3)) buttonGreenLine3[currentX].setBorder(null);
		
}
//metod vidobragae ruch potyagiv RedLine1_T 
 static void printRedLine1_T (int x, int nomerPotyaga){
	int curX=x+61;
	if (curX>100)curX-=2;
	if (curX>115){
		switch (curX){
		case 116: curX=58;break;
		case 117: curX=56;break;
		case 118: curX=55;break;
		case 119: curX=55;break;
		case 120: curX=55;break;
		}}
		setRamkaGovta(curX, buttonRedLine1);
		buttonRedLine1[curX].setText(""+nomerPotyaga);
}
//metod stjrae ruch potyagiv RedLine1_T 
static void deleteRedLine1_T (int x){
	    int curX=x+61;
	    if (curX>100)curX-=2;
		if (curX>115){
			switch (curX){
			case 116: curX=58; break;
			case 117: curX=56; break;
			case 118: curX=55; break;
			case 119: curX=55; break;
			case 120: curX=55; break;
			}}
			resetRamkaGovta(curX, buttonRedLine1);
			buttonRedLine1[curX].setText("");
}
//metod vidobragae ruch potyagiv RedLine1_N 
static void printRedLine1_N (int x, int nomerPotyaga){
		int curX=54-x;
		if (curX<39)curX+=2;
		if (curX<0){
			switch (curX){
			case -1: curX=57; break;
			case -2: curX=59; break;
			case -3: curX=60; break;
			case -4: curX=60; break;
			case -5: curX=60; break;
			}}
			setRamkaGovta(curX, buttonRedLine1);
			buttonRedLine1[curX].setText(""+nomerPotyaga);
}
static void deleteRedLine1_N (int x){
	int curX=54-x;
	if (curX<39)curX+=2;
	if (curX<0){
		switch (curX){
		case -1: curX=57;break;
		case -2: curX=59;break;
		case -3: curX=60;break;
		case -4: curX=60;break;
		case -5: curX=60;break;
		}}
		resetRamkaGovta(curX, buttonRedLine1);
		buttonRedLine1[curX].setText("");
}
//metod vidobragae ruch potyagiv BlueLine2_T 
static void printBlueLine2_T (int x, int nomerPotyaga){
	int curX=2*x+5;
	if (curX>95){
		switch (curX){
		case 97:  curX=96;break;
		case 99:  curX=97;break;
		case 101: curX=94;break;
		}}
	setRamkaGovta(curX, buttonBlueLine2);
	buttonBlueLine2[curX].setText(""+nomerPotyaga);
}
static void deleteBlueLine2_T (int x){
	int curX=(2*x)+5;
	if (curX>95){
		switch (curX){
		case 97:  curX=96; break;
		case 99:  curX=97; break;
		case 101: curX=94; break;
		}}
	resetRamkaGovta(curX, buttonBlueLine2);
	buttonBlueLine2[curX].setText("");
}

//metod vidobragae ruch potyagiv BlueLine2_N 
static void printBlueLine2_N (int x, int nomerPotyaga){
	int curX=92-(2*x);
	if (curX<2){
		switch (curX){
		case  0: curX=1; break;
		case -2: curX=0; break;
		case -4: curX=3; break;
		}}
	setRamkaGovta(curX, buttonBlueLine2);
	buttonBlueLine2[curX].setText(""+nomerPotyaga);
}
static void deleteBlueLine2_N (int x){
	int curX=92-(2*x);
	if (curX<2){
		switch (curX){
		case  0: curX=1; break;
		case -2: curX=0; break;
		case -4: curX=3; break;
		}}
	resetRamkaGovta(curX, buttonBlueLine2);
	buttonBlueLine2[curX].setText("");
}

public static void main (String[] args) {
		SchemaMetro sM = new SchemaMetro(); 
			 
}}

