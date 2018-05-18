package Depo;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class InfoStation {
	
	// ogoloshuu vsi komponenti
	private JPanel vmistVikna=new JPanel();
	private static JLabel labelInfo [][]=new JLabel [9][14];
	private Border ramkaGovta1 = BorderFactory.createLineBorder(Color.YELLOW, 1);
	
	InfoStation(){	
		
		for (int i=0; i<9; i++){
		    for (int j=0; j<14; j++){ 
		    	
		    	if (0==i) {
		    		if (0==j) labelInfo [i][j]=new JLabel(); 
		    		if ((j>0)&&(j<5)) labelInfo [i][j]=new JLabel("Station1"+j);
		    		if ((j>4)&&(j<9)) labelInfo [i][j]=new JLabel("Station2"+(j-4));
		    		if ((j>8)&&(j<13)) labelInfo [i][j]=new JLabel("Station3"+(j-8));
		    		if (13==j) labelInfo [i][j]=new JLabel("Razom");
		    	}
		    	else if (0==j) {
		    		    if (0==i) labelInfo [i][j]=new JLabel();
		    		    if (1==i) labelInfo [i][j]=new JLabel("Vestibul");
		    		    if (2==i) labelInfo [i][j]=new JLabel("Eskal.N1");
		    		    if (3==i) labelInfo [i][j]=new JLabel("Eskal.N2");
		    		    if (4==i) labelInfo [i][j]=new JLabel("Eskal.N3");
		    		    if (5==i) labelInfo [i][j]=new JLabel("Peron");
		    		    if (6==i) labelInfo [i][j]=new JLabel("¹ ==>");
		    		    if (7==i) labelInfo [i][j]=new JLabel("¹ <==");
		    		    if (8==i) labelInfo [i][j]=new JLabel("Vixid");
		    	}
		    	else labelInfo [i][j]=new JLabel();
		   
		    	if ( ((6==i)||(7==i)) && (j>0) ) labelInfo [i][j].setHorizontalAlignment(SwingConstants.CENTER);
		    	labelInfo [i][j].setBorder(ramkaGovta1);
		    	vmistVikna.add(labelInfo [i][j]);
		    }}
		//Zadau sxemu dlya paneli vmistVikna
		vmistVikna.setLayout(new GridLayout (9,14));
		//Stvoruu Frame ta viznachau ego osnovnu panel
		JFrame frame = new JFrame ("InfoStation");
		frame.setContentPane(vmistVikna);
		frame.setLocation(90, 50);
		//Zadau dostatnij rozmir vikna
		frame.pack();
		//frame.setSize(230, 650);
		//Vidobragaem vikno
		frame.setVisible(true);
		//vstanjvluu operaziu pri zakritti vikna
		//frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e) {
					MetroRuch.praporKilkostiIS=0;
				    //System.exit(0);
				    }});
	}
public static void setText (int i, int j, int kilkist) {labelInfo[i][j].setText(""+kilkist);}
public static void setText (int i, int j, String str) {labelInfo[i][j].setText(str);}
}
