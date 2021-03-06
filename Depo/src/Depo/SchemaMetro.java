package Depo;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

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

MetroRuch mr = new MetroRuch (this);

panelWest.setLayout(new GridLayout (24,1));
panelWest.add(buttonStart); 
buttonStart.addActionListener((ae)->{mr.lines = mr.metroRuch(); }); 
panelWest.add(buttonStop); 
buttonStop.addActionListener((ae)->{mr.metroStop(mr.getlines());}); 
for(int i=0; i<10; i++){
	buttonWest[i]=new JButton("S "+(i+1)*10);
	panelWest.add(buttonWest[i]);
	buttonWest[i].addActionListener((ae)->{});
	}
for(int i=0; i<12; i++){
	if (i<4) buttonWest[i]=new JButton("Station"+(11+i));
	   else if (i<8) buttonWest[i]=new JButton("Station"+(17+i));
	        else if (i<12) buttonWest[i]=new JButton("Station"+(23+i));
	panelWest.add(buttonWest[i]);
	buttonWest[i].addActionListener((aL)->{});
	}
buttonWest[0].addActionListener((aL)->{
	if ((mr.lines[2]!= null) && (mr.lines[1]!= null) && (mr.lines[0]!= null)) {
	mr.formaIS();
	mr.showAllStation();
	
	}
	});
for (int i=0; i<3; i++){
    for (int j=0; j<4;j++){ 
    buttonStation [i][j]=new JButton("S"+((i+1)*10+(j+1)));
    buttonStation [i][j].setFont(kursiv);
    buttonStation [i][j].setBorder(ramkaGovta3);
    buttonStation [i][j].addActionListener((ae)->{});
}}
for (int i=0; i<120; i++){
	buttonRedLine1 [i]=new JButton(); 
	buttonRedLine1 [i].setFont(k8); 
	buttonRedLine1 [i].addActionListener (al -> {
		mr.formaInfoTrain();
		JButton clickedButtonRed = (JButton) al.getSource();
		mr.infoTrain_Red(clickedButtonRed);
		});
	} 
for (int i=0; i<98; i++){ 
	buttonBlueLine2 [i]=new JButton(); 
	buttonBlueLine2 [i].setFont(k8);
	}
for (int i=0; i<100; i++){
	buttonGreenLine3 [i]=new JButton(); 
	buttonGreenLine3 [i].setFont(k8);
	}

for (int i=0; i<50; i++){
    for (int j=0; j<60; j++){ 
    	labelPole [i][j]=new JLabel();	
if ((41==j)&&((2==i)||(20==i)||(36==i)||(45==i))){ 
	panelZentr.add(buttonStation [1][b]);
	    b++;	
	} 
else {if((33==i)&&((2==j)||(18==j)||(38==j)||(56==j))){        
	panelZentr.add(buttonStation [0][r]);
             r++;
    }       
else {if(((3==i)&&(48==j))  ||
	     ((13==i)&&(38==j)) ||     
	     ((36==i)&&(15==j)) ||
         ((47==i)&&(4==j))){
          panelZentr.add(buttonStation [2][3-g]);
           g++;
    }	
else {if (((40==j)||(42==j))&&(i<=47) ||
		  ((41==j)&&((0==i)||(47==i)))) {
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
	    if (curX>100) curX-=2;
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
//metod vidobragae ruch potyagiv GreenLine3_T 
static void printGreenLine3_T (int x, int nomerPotyaga){
    
	int curX=83-(2*x);
	
    if (curX<59)  {  curX=87-(2*x); 
    
	if (61==curX) curX=59;
	if (21==curX) curX=22;
	if (19==curX) curX=20;
	if (17==curX) curX=19;
	if (15==curX) curX=17;
	if (13==curX) curX=14;
	
	    if (curX<13)  { curX=91-(2*x);
	
	       if (15==curX) curX=14;
	       if (13==curX) curX=14;
	       if ( 1==curX) curX=0;
	       if (-1==curX) curX=1;
	       if (-3==curX) curX=2;
	       if (-5==curX) curX=4;
	       if (-7==curX) curX=6;
    }}
	setRamkaGovta(curX, buttonGreenLine3);
	buttonGreenLine3[curX].setText(""+nomerPotyaga);
}
static void deleteGreenLine3_T (int x){

int curX=83-(2*x);
	
    if (curX<59)  {  curX=87-(2*x); 
    
	if (61==curX) curX=59;
	if (21==curX) curX=22;
	if (19==curX) curX=20;
	if (17==curX) curX=19;
	if (15==curX) curX=17;
	if (13==curX) curX=14;
	
	if (curX<13)  { curX=91-(2*x);
	
	if (15==curX) curX=14;
	if (13==curX) curX=14;
	if ( 1==curX) curX=0;
	if (-1==curX) curX=1;
	if (-3==curX) curX=2;
	if (-5==curX) curX=4;
	if (-7==curX) curX=6;
    }}
    resetRamkaGovta(curX, buttonGreenLine3);
	buttonGreenLine3[curX].setText("");	
}
//metod vidobragae ruch potyagiv GreenLine3_N 
static void printGreenLine3_N (int x, int nomerPotyaga){
	
	int curX=8+(2*x);
	if (14==curX) curX=13;
	if (16==curX) curX=15;
	
	if (curX>16) { curX=4+(2*x);
	
	   if (14==curX) curX=15;
	   if (20==curX) curX=21;
	   if (22==curX) curX=21;
	   
	        if (curX>60) { curX=2*x;
	        
	           if (58==curX) curX=60;
	           if (90==curX) curX=91;
	           if (92==curX) curX=90;
	           if (94==curX) curX=89;
	           if (96==curX) curX=87;
	           if (98==curX) curX=85;
	}}
	setRamkaGovta(curX, buttonGreenLine3);
	buttonGreenLine3[curX].setText(""+nomerPotyaga);
}
static void deleteGreenLine3_N (int x){
	
	int curX=8+(2*x);
	if (14==curX) curX=13;
	if (16==curX) curX=15;
	
	if (curX>16) { curX=4+(2*x);
	
	   if (14==curX) curX=15;
	   if (20==curX) curX=21;
	   if (22==curX) curX=21;
	   
	        if (curX>60) { curX=2*x;
	        
	           if (58==curX) curX=60;
	           if (90==curX) curX=91;
	           if (92==curX) curX=90;
	           if (94==curX) curX=89;
	           if (96==curX) curX=87;
	           if (98==curX) curX=85;
	 }}
	resetRamkaGovta(curX, buttonGreenLine3);
	buttonGreenLine3[curX].setText("");
}
//method nadae JButton RedLine
JButton getJButtonRed (int i) {return buttonRedLine1 [i];}
//method nadae JButton BlueLine
JButton getJButtonBlue (int i) {return buttonBlueLine2 [i];}
//method nadae JButton GreenLine
JButton getJButtonGreen (int i) {return buttonGreenLine3 [i];}



public static void main (String[] args) {
		SchemaMetro sM = new SchemaMetro(); 		
}}

