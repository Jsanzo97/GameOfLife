package juegoDeLaVida;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Plantillas extends JFrame implements MouseListener, ActionListener{
	
	ArrayList<ArrayList<ArrayList<String>>> plantillas = new ArrayList<ArrayList<ArrayList<String>>>();
	ArrayList<JPanel> paneles = new ArrayList<JPanel>();
	ArrayList<String> nombres = new ArrayList<String>();
	JPanel panelGeneral1 = new JPanel();
	JPanel panelGeneral2 = new JPanel();
	JPanel panelMuestra = new JPanel();
	JPanel panelOpciones = new JPanel();
	JScrollPane scroll = new JScrollPane(panelGeneral1);
	static String pulsado="";
	
	public Plantillas() {

		JButton volver = new JButton("Volver");
		volver.addActionListener(this);
		JButton confirmar = new JButton("Confirmar");
		confirmar.addActionListener(this);
		confirmar.setBounds(20, 100, 100, 30);
		volver.setBounds(170, 100, 100, 30);
		panelOpciones.setLayout(null);
		panelOpciones.add(confirmar);
		panelOpciones.add(volver);
		panelOpciones.setBounds(0,330,300,320);
		panelMuestra.setBounds(0,0,300,320);
		panelGeneral2.setLayout(null);
		panelGeneral2.add(panelMuestra);
		panelGeneral2.add(panelOpciones);
		this.setLayout(new GridLayout(1,2));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//finaliza el programa cuando se da click en la X
        this.setBounds(450, 50, 600, 600);
        
        Start();
	}
	
	public void Start() {
		File dir = new File(".");
		String[] d = dir.list();
		ArrayList<String> ficheros = new ArrayList<String>();
		for(int i=0; i<d.length; i++) {
			if(d[i].length()>3 && d[i].substring(d[i].length()-4,d[i].length()).equals(".txt"))
				ficheros.add(d[i]);
		}
		for(int i =0; i<ficheros.size(); i++) {
			plantillas.add(CrearTablero(ficheros.get(i)));	
		}
		
		
		int borde=4;
		for(int i=0; i< plantillas.size(); i++) {
			int tamX=plantillas.get(i).size()+borde;
			int tamY=plantillas.get(i).get(0).size()+borde;
			int centrarX = (tamX-plantillas.get(i).size())/2;
			int centrarY = (tamY-plantillas.get(i).get(0).size())/2;
			JPanel p = new JPanel();
			GridLayout lay = new GridLayout(plantillas.get(i).size()+borde,plantillas.get(i).get(0).size()+borde);
			p.setLayout(lay);
			
			for(int j=0; j<tamX; j++) {		
				for(int k=0; k<tamY; k++) {
					if((j<centrarX || j>=centrarX + plantillas.get(i).size() || k<centrarY || k>=centrarY + plantillas.get(i).get(0).size())) {
						JPanel pB = new JPanel();
						JLabel bB = new JLabel(Integer.toString(i));
						pB.add(bB);
						pB.setBackground(Color.black);
						bB.setForeground(Color.black);
						pB.addMouseListener(this);
						p.add(pB);
					}else {
						if(plantillas.get(i).get(j-centrarX).get(k-centrarY).equals("X")) {
							JPanel pX = new JPanel();
							JLabel bX = new JLabel(Integer.toString(i));
							pX.add(bX);
							pX.setBackground(Color.green);
							bX.setForeground(Color.green);
							pX.addMouseListener(this);
							p.add(pX);
						}else {
							JPanel pP = new JPanel();
							JLabel bP = new JLabel(Integer.toString(i));
							pP.add(bP);
							pP.setBackground(Color.black);
							bP.setForeground(Color.black);
							pP.addMouseListener(this);
							p.add(pP);
						}
					}	
				}
			}

			JLabel nombre = new JLabel(nombres.get(i));
			nombre.setFont(new Font("Comic Sans MS",Font.BOLD,28));
			
			JPanel espacio = new JPanel();
			espacio.setBounds(0,0,270,50);
			espacio.add(nombre);
			nombre.setBounds(espacio.getBounds());
			
			p.setBounds(0,50,270,270);
			
			JPanel panelPlantilla = new JPanel();
			panelPlantilla.setPreferredSize(new Dimension(270,330));
			panelPlantilla.setLayout(null);
			panelPlantilla.add(espacio);
			panelPlantilla.add(p);
			
			paneles.add(panelPlantilla);
		}

		for(int i=0; i<paneles.size(); i++) {
			panelGeneral1.add(paneles.get(i));
		}
		BoxLayout lay = new BoxLayout(panelGeneral1,BoxLayout.Y_AXIS);
		panelGeneral1.setLayout(lay);
		this.add(scroll);
		this.add(panelGeneral2);
		this.setVisible(true);
	}
	
	public ArrayList<ArrayList<String>> CrearTablero(String nombre) {
		
		ArrayList<ArrayList<String>> tablero = new ArrayList<ArrayList<String>>();
		int caracter,index;
		int filas=0;
		int columnas=0;
		File archivo = new File (nombre);
		FileReader fr = null;
		BufferedReader br = null;
		tablero.add(new ArrayList<String>());
		
		try {
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);
			filas =Integer.parseInt(br.readLine());
			columnas=Integer.parseInt(br.readLine());
			caracter = 0;
		    index=0;
		    
		    while(caracter!=-1) {
		    	caracter = br.read();
		    	if(caracter == 88) {
		    		tablero.get(index).add("X");
		    	}
		    	if(caracter == 46) {
		    		tablero.get(index).add(".");
		    	}
		    	if(caracter == 13 || caracter == 10) {
		    	}
		    	if(caracter == 32) {
		    		tablero.add(new ArrayList<String>());
		    		index++;
		    	}
		    }
		   
		    fr.close();
			br.close();
			
			nombres.add(nombre.substring(0,nombre.length()-4));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return tablero;
	}


	@Override
	public void mouseClicked(MouseEvent ev) {
		JPanel evento = (JPanel) ev.getSource();
		JLabel et = (JLabel) evento.getComponent(0);
		pulsado = et.getText();

		if(panelMuestra.getComponentCount()==0) {
			panelMuestra.removeAll();
			panelMuestra.add(paneles.get(Integer.parseInt(pulsado)));
			panelMuestra.updateUI();
			panelMuestra.repaint();
		}
		else {
			panelGeneral1.add(panelMuestra.getComponent(0));
			panelMuestra.add(paneles.get(Integer.parseInt(pulsado)));
			panelMuestra.updateUI();
			panelMuestra.repaint();
			panelGeneral1.repaint();
			panelGeneral1.updateUI();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if(ev.getActionCommand() == "Confirmar") {
			System.out.println(nombres.get(Integer.parseInt(pulsado)));
			GameOfLife g = new GameOfLife((nombres.get(Integer.parseInt(pulsado))+".txt"));
			this.dispose();
		}
		if(ev.getActionCommand() == "Atras") {
			System.out.println("Atras");
		}	
	}
}
