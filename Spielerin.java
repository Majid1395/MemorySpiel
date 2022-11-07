

public class Spielerin {
	
	private String name;
	private int punkte = 0;
	private boolean istDran;
	
	public Spielerin(String name) {
		this.name = name;
	}
	
	public String gibNamen() {
		return this.name;
	}
	
	public boolean istDran() {
		return this.istDran;
	}
	
	public void stezeDran(boolean dran) {
		this.istDran = dran;
	}
	
	public int gibPunkte() {
		return this.punkte;
	}
	
	public void erhoehePunkte() {
		punkte++;
	}
}
