package Depo;

import java.time.LocalDateTime;

public class Poizdka {

	private LocalDateTime dateTime;
	private String nazvaLinii;
	private Mashinist mashiist;
	
	public Poizdka(Mashinist mashiist, String nl) {
		super(); 
		this.mashiist = mashiist;
		this.nazvaLinii=nl;
		dateTime=LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "Poizdka " + dateTime + " Line " + nazvaLinii + " " + mashiist.toString();
	}

	public Mashinist getMashiist() {return mashiist;}
	public void setMashiist(Mashinist mashiist) {this.mashiist = mashiist;}
	
	
}
