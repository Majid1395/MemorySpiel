

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class Memory extends JFrame implements ActionListener{

	private int anzahlKlick= 0;
	private int a,b;
	private Spielerin spielerin1, spielerin2;
	private Karte karte;
	private JPanel haupt,kartenPanel;
	private ArrayList<ImageIcon> kartenList;
	private JButton[] Buttons ;
	private Map<ImageIcon, ImageIcon> zuordnungen;
	private static String[] karten = {"karten/0-0.png","karten/0-1.png","karten/1-0.png","karten/1-1.png","karten/2-0.png","karten/2-1.png","karten/3-0.png","karten/3-1.png",
    		"karten/4-0.png","karten/4-1.png","karten/5-0.png","karten/5-1.png","karten/6-0.png","karten/6-1.png","karten/7-0.png","karten/7-1.png"};;
	
	public Memory() {
		super("Memory");   
	    setResizable(false); 
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    
	    //add panels
	    haupt= new JPanel();
	    kartenPanel= new JPanel();
	    
	    haupt.setLayout(new BorderLayout());
	    haupt.setPreferredSize(new Dimension(600, 600)); 
	    kartenPanel.setLayout(new GridLayout(4,4,2,2));	
	    
		//add Karten
	    erzeugteList();
	    
	   //add Buttons
	    Buttons = new JButton[karten.length];
	    
	    for(int i = 0; i<  karten.length ;i++) {
	    	
	    	Buttons[i] = new JButton(karte.rueckseite);
	    	kartenPanel.add(Buttons[i]);
			Buttons[i].addActionListener(this);
			
	    }
	   //Einstellungen Menue
	    JMenuBar mBar = new JMenuBar();
	    JMenu menuSpiel = new JMenu("Einstellungen");
	    JMenuItem miNeu = new JMenuItem("Neu");
	    miNeu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_DOWN_MASK));
	    miNeu.addActionListener(e -> neuesSpiel());
	    menuSpiel.add(miNeu);
	    JMenuItem miVerlassen = new JMenuItem("Verlassen");
	    miVerlassen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
	    miVerlassen.addActionListener(e -> beenden());
	    menuSpiel.add(miVerlassen);
	    mBar.add(menuSpiel);
	    setJMenuBar(mBar);
	    
	    haupt.add(kartenPanel);
	    add(haupt);
	    
	    pack();             
	    setVisible(true);
	    fenster_Menue();
	    
	}
	private void erzeugteList() {
		
	    kartenList = new ArrayList<ImageIcon>();
	   
	    for(int i = 0;i< karten.length;i++) {
		    
		    karte = new Karte(karten[i]);
		   	kartenList.add(karte.motivResize); 	
	 	 }
	    //add HashMap
	    zuordnungen = new HashMap<ImageIcon, ImageIcon>();
	    for(int j = 0;j < karten.length; j += 2) {
	    	zuordnungen.put(kartenList.get(j), kartenList.get(j+1));
	    }
	   
	    System.out.println(zuordnungen.size());
	    
	    Collections.shuffle(kartenList); 
	    
	}
	private void fenster_Menue() {
		//Fenster-Menü,
	    String[] options = {"Neu Starten", "Beenden"}; 
	    
	    int x = JOptionPane.showOptionDialog(this, "Bitte auswählen!",
                "Memory Spiel",
                JOptionPane.DEFAULT_OPTION, JOptionPane.NO_OPTION, null, options, options[0]);
	    
	   if(x == 0)
		   starteSpiel();
	   else
		   beenden();
		
	}
	
	private void starteSpiel() {
		
		//Multiple JOptionPane
		Random random = new Random();
		JTextField name1 = new JTextField();
		JTextField name2 = new JTextField();
		Object[] namen = {
			    "Spieler1:", name1,
			    "Spieler2:", name2
			};
		JOptionPane.showConfirmDialog(this,namen ,"Spieler", JOptionPane.OK_CANCEL_OPTION);
		
		spielerin1 = new Spielerin(name1.getText());
		spielerin2 = new Spielerin(name2.getText());
	
		spielerin1.stezeDran(random.nextBoolean());
		naechsteDran();
		
		
	}
	private void naechsteDran() {
		if(spielerin1.istDran()== true) {
			spielerin2.stezeDran(false);
			JOptionPane.showMessageDialog(this,spielerin1.gibNamen()+" ist Dran..\nBitte zwei Karten wählen " ,"Meldung", JOptionPane.OK_CANCEL_OPTION);
		}
		else if(spielerin1.istDran()== false ) {
			spielerin2.stezeDran(true);
			JOptionPane.showMessageDialog(this,spielerin2.gibNamen()+" ist Dran..\nBitte zwei Karten wählen " ,"Meldung", JOptionPane.OK_CANCEL_OPTION);
		}
	}
	
	
	private void  wechsleSpielerin() {
		
		if(zuordnungen.isEmpty()) {
			pruefeGewonnen();
		}
		else {
			if(spielerin1.istDran()== true) {
				spielerin1.stezeDran(false);
				spielerin2.stezeDran(true);
				naechsteDran();
			}
			else if(spielerin2.istDran()== true) {
				spielerin1.stezeDran(true);
				spielerin2.stezeDran(false);
				naechsteDran();
				
			}
		}
		
	
	}
	
	private void  beenden(){
		System.exit(0);
	}
	
	private String  gibSpielstand() {
		return spielerin1.gibNamen()+" - "+spielerin1.gibPunkte()+" | "+spielerin2.gibNamen()+" - "+spielerin2.gibPunkte();
		
	}
	
	private void pruefeGewonnen() {
		
		if(spielerin1.gibPunkte() > spielerin2.gibPunkte()) {
			JOptionPane.showMessageDialog(this,spielerin1.gibNamen()+" hat gewonnen! " ,"Meldung", JOptionPane.OK_CANCEL_OPTION);
			neuesSpiel();
		}
		else if(spielerin1.gibPunkte() < spielerin2.gibPunkte()) {
			JOptionPane.showMessageDialog(this,spielerin2.gibNamen()+" hat gewonnen! " ,"Meldung", JOptionPane.OK_CANCEL_OPTION);
			neuesSpiel();
		}
		else {
			JOptionPane.showMessageDialog(this,"Die Ergebnisse sind Gleich!\n Niemand hat gewonnen! " ,"Meldung", JOptionPane.OK_CANCEL_OPTION);
			neuesSpiel();
		}
			
	}
	
	private void neuesSpiel() {
		
		
		for(int k=0; k < Buttons.length; k++)
		{
			Buttons[k].setEnabled(true);
			Buttons[k].setIcon(karte.rueckseite);
			a=0;
			b=0;
			anzahlKlick = 0;
			
		}
		kartenList.clear();
		zuordnungen.clear();
		erzeugteList();
		fenster_Menue();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i=0; i < Buttons.length; i++)
		{	
			if(e.getSource()==Buttons[i] && anzahlKlick < 2) //überprüft welcher Button gedrückt wurde
			{
				if(anzahlKlick ==0)
				{
					a=i;
					Buttons[a].setIcon(kartenList.get(a));
						
					anzahlKlick++;
				}		
				else if (anzahlKlick ==1) {
					b=i;
					Buttons[b].setIcon(kartenList.get(b));
					
					anzahlKlick++;
				}	
				
			}
			else if(anzahlKlick == 2) {
				if(!zuordnungen.isEmpty()) {
					pruefePaarGefunden();
					wechsleSpielerin();
					anzahlKlick = 0;
				}
			}	
		}
			
	}
	private void pruefePaarGefunden() {
		if(kartenList.get(b).equals(zuordnungen.get(kartenList.get(a)))) {
			if(spielerin1.istDran()== true) {
				spielerin1.erhoehePunkte();
			}
			else if(spielerin2.istDran()== true ) {
				spielerin2.erhoehePunkte();
			}
			zuordnungen.remove(kartenList.get(b));
			zuordnungen.remove(kartenList.get(a));
			Buttons[a].setEnabled(false);
			Buttons[b].setEnabled(false);
			System.out.println(zuordnungen.size());
			JOptionPane.showMessageDialog(this,":-] Gratuliere! Ein Pärchen gefunden! \n\n Aktueller Spielstand: \n\n" + gibSpielstand(),"Meldung", JOptionPane.CANCEL_OPTION);
			
		}
		else if(kartenList.get(a).equals(zuordnungen.get(kartenList.get(b)))) {
			if(spielerin1.istDran()== true) {
				spielerin1.erhoehePunkte();
			}
			else if(spielerin2.istDran()== true ) {
				spielerin2.erhoehePunkte();
			}
			zuordnungen.remove(kartenList.get(b));
			zuordnungen.remove(kartenList.get(a));
			Buttons[a].setEnabled(false);
			Buttons[b].setEnabled(false);
			System.out.println(zuordnungen.size());
			JOptionPane.showMessageDialog(this,":-] Gratuliere! Ein Pärchen gefunden! \n\n Aktueller Spielstand: \n\n" + gibSpielstand(),"Meldung", JOptionPane.CANCEL_OPTION);
		}
		else  {
			JOptionPane.showMessageDialog(this,":-[ Leider kein Pärchen! \n\n Aktueller Spielstand: \n\n" + gibSpielstand(),"Meldung", JOptionPane.CANCEL_OPTION);
			Buttons[a].setIcon(karte.rueckseite);
			Buttons[b].setIcon(karte.rueckseite);
		}
		
	}
	
	public static void main(String[] args) {
		
			SwingUtilities.invokeLater(() -> new Memory());

	}


}
