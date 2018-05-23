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

public class InfoTrain {
	// ogoloshuu vsi komponenti
	private JPanel vmistVikna=new JPanel();
	private static JLabel labelInfo [][]=new JLabel [9][4];
	private Border ramkaGovta1 = BorderFactory.createLineBorder(Color.YELLOW, 1);
	
	InfoTrain (){
		for (int i=0; i<9; i++){
		    for (int j=0; j<4; j++){ 
		    	
		    	if (0==i) {
		    		if (0==j) labelInfo [i][j]=new JLabel("Parametr"); 
		    		if (1==j) labelInfo [i][j]=new JLabel("Value");
		    		if (2==j) labelInfo [i][j]=new JLabel("Pasagir");
		    		if (3==j) labelInfo [i][j]=new JLabel("Notes");
		    	}
		    	else if (0==j) {
		    		    //if (0==i) labelInfo [i][j]=new JLabel();
		    		    if (1==i) labelInfo [i][j]=new JLabel("Line");
		    		    if (2==i) labelInfo [i][j]=new JLabel("Driver");
		    		    if (3==i) labelInfo [i][j]=new JLabel("Train");
		    		    if (4==i) labelInfo [i][j]=new JLabel("Vagon1");
		    		    if (5==i) labelInfo [i][j]=new JLabel("Vagon2");
		    		    if (6==i) labelInfo [i][j]=new JLabel("Vagon3");
		    		    if (7==i) labelInfo [i][j]=new JLabel("Vagon4");
		    		    if (8==i) labelInfo [i][j]=new JLabel("Vagon5");
		    	}
		    	else labelInfo [i][j]=new JLabel();
		   
		    	labelInfo [i][j].setHorizontalAlignment(SwingConstants.CENTER);
		    	labelInfo [i][j].setBorder(ramkaGovta1);
		    	vmistVikna.add(labelInfo [i][j]);
		    }}
	//Zadau sxemu dlya paneli vmistVikna
	vmistVikna.setLayout(new GridLayout (9,4));
	//Stvoruu Frame ta viznachau ego osnovnu panel
	JFrame frame = new JFrame ("InfoTrain");
	frame.setContentPane(vmistVikna);
	frame.setLocation(90, 245);
	//Zadau dostatnij rozmir vikna
	//frame.pack();
	frame.setSize(400, 180);
	//Vidobragaem vikno
	frame.setVisible(true);
	//vstanjvluu operaziu pri zakritti vikna
	//frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				MetroRuch.praporKilkostiIT=0;
			    //System.exit(0);
			    }});
	}
	public static void setText (int i, int j, int kilkist) {labelInfo[i][j].setText(""+kilkist);}
	public static void setText (int i, int j, String str) {labelInfo[i][j].setText(str);}
}
