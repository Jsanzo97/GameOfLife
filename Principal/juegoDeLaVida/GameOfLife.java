/**
 * @author Jorge
 */
package juegoDeLaVida;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GameOfLife extends JFrame implements ActionListener{
	
	int tamX = 0;
	int tamY = 0;
	int velocidad = 50;
	JPanel p = new JPanel();
	JPanel p2 = new JPanel();
	JPanel info = new JPanel();
	JButton op1 = new JButton("Avanzar");
	JButton op2 = new JButton("Simular");
	JButton op3 = new JButton("Elegir otra");
	JButton op4 = new JButton(" STOP ");
	JLabel celulas = new JLabel();
	JLabel tam = new JLabel();
	boolean b=true;
	boolean simulando = true;
	int iteraciones = 0;
	String nombre = "";
	ArrayList<ArrayList<String>> tablero = new ArrayList<ArrayList<String>>();
	
	public GameOfLife(String nombre){
		
		this.nombre = nombre;
		this.setTitle("GameOfLife");
		
		info.setLayout(new GridLayout(2,1));
		info.add(celulas);
		info.add(tam);
		
		op1.addActionListener(this);
		op2.addActionListener(this);
		op3.addActionListener(this);
		op4.addActionListener(this);
		
		p.setBounds(0,0,780,500);
		p.setBackground(Color.BLACK);
		this.add(p);
		
		p2.setLayout(new GridLayout(1,1));
		p2.add(op1);
		p2.add(op2);
		p2.add(op3);
		p2.add(op4);
		p2.add(info);
		p2.setBounds(0,500,785,60);
		this.add(p2);
		
		this.setLayout(null);
		this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//finaliza el programa cuando se da click en la X
        this.setBounds(250, 0, 800, 600);
        Start();
	}
		
	public void Start (){
		//Declaramos los escaner y la estructura principal del programa que es
		//una matriz de de arraylist
		
		if(nombre!="") {
			CrearTablero(nombre);
			for(int i=0; i<tablero.size(); i++) {
				for(int j=0; j<tablero.get(0).size(); j++) {
					System.out.print(tablero.get(i).get(j));
				}
				System.out.println();
			}
		}
		Pintar();
		celulas.setText(Integer.toString(Recuento()));
		celulas.setFont(new Font("Comic Sans MS",Font.BOLD,16));
		celulas.setHorizontalAlignment(SwingConstants.CENTER);
	}
	//Metodo que sirve para crear el tablero en funcion del fichero
	public void CrearTablero(String nombre) {
		
		int caracter,index;
		int filas=0;
		int columnas=0;
		File archivo = new File (nombre);
		FileReader fr = null;
		BufferedReader br = null;
		tablero.add(new ArrayList<String>());
		
		//Leemos el fichero por caracteres, el 88 es la X, el 46 el . , el 13 es
		//retorno de carro asique lo ignoramos, el 10 nueva linea tambien lo ignoramos
		//y el 32 fin de linea que es cuando pasamos a la siguiente fila de la matriz
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
			
			System.out.println("Fichero leido: "+nombre);
			System.out.println(filas);
			System.out.println(columnas);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	//Metodo para modificar el tablero en cada iteracion
	public ArrayList<ArrayList<String>> Modificar(ArrayList<ArrayList<String>> tablero) {

		//Primero colocamos un marco alrededor para evitar que se salga de rango
		tablero = Ampliar(tablero);
		ArrayList<ArrayList<String>> tablero2 = new ArrayList<ArrayList<String>>();
		int contador=0;

		//Creamos otro tablero auxiliar donde guardaremos el resultado de la iteracion
		for(int i=0; i<tablero.size(); i++) {
			tablero2.add(new ArrayList<String>());
			for(int j=0; j<tablero.get(i).size(); j++) {
				tablero2.get(i).add(".");
			}
		}
		//Empezamos a recorrer el tablero comprobado que debe pasar con cada celda
		for(int i=1; i<tablero.size()-1; i++) {
			for(int j=1; j<tablero.get(i).size()-1; j++) {
				if(tablero.get(i).get(j) == "X") {
					if(tablero.get(i-1).get(j) == "X")
						contador++;
					if(tablero.get(i-1).get(j-1) == "X")
						contador++;
					if(tablero.get(i-1).get(j+1)=="X")
						contador++;
					if(tablero.get(i).get(j+1)=="X")
						contador++;
					if(tablero.get(i).get(j-1)=="X")
						contador++;
					if(tablero.get(i+1).get(j)=="X")
						contador++;
					if(tablero.get(i+1).get(j-1)=="X")
						contador++;
					if(tablero.get(i+1).get(j+1)=="X")
						contador++;
					if(contador<2 || contador>3) {		//Muere X por superpoblacion o falta de poblacion cercana
						tablero2.get(i).set(j,".");
					}else {
						tablero2.get(i).set(j,"X");
					}
					contador=0;
				}else {
					if(tablero.get(i-1).get(j) == "X")
						contador++;
					if(tablero.get(i-1).get(j-1) == "X")
						contador++;
					if(tablero.get(i-1).get(j+1)=="X")
						contador++;
					if(tablero.get(i).get(j+1)=="X")
						contador++;
					if(tablero.get(i).get(j-1)=="X")
						contador++;
					if(tablero.get(i+1).get(j)=="X")
						contador++;
					if(tablero.get(i+1).get(j-1)=="X")
						contador++;
					if(tablero.get(i+1).get(j+1)=="X")
						contador++;
					if(contador==3) 					//Una celda muerta pasa a viva
						tablero2.get(i).set(j,"X");
					contador=0;	
				}
			}
		}
		return tablero2;
	}

	//Metodo que coloca un marco alrededos del tablero
	public ArrayList<ArrayList<String>> Ampliar(ArrayList<ArrayList<String>> tablero) {
		ArrayList<ArrayList<String>> tablero2 = new ArrayList<ArrayList<String>>();

		//El tamaño del marco es de +2 a cada lado tanto arriba/abajo como derecha/izquierda
		for(int i=0; i<tablero.size()+4; i++) {
			tablero2.add(new ArrayList<String>());
			for(int j=0; j<tablero.get(0).size()+4; j++) {
				tablero2.get(i).add(".");
			}
		}
		//Colocamos CENTRADO EN (2,2) el tablero original en el nuevo con el marco
		for(int i=0; i<tablero.size(); i++) {
			for(int j=0; j<tablero.get(i).size(); j++) {
				tablero2.get(i+2).set(j+2,tablero.get(i).get(j));
			}
		}
		return tablero2;
	}

	//Metodo para eliminar las filas y columnas vacias que sobran
	public void Limpiar() {
		int contadorPrimera=0;
		int posicionPrimera=tablero.get(0).size();
		int contadorUltima=0;
		int posicionUltima=0;
		ArrayList<ArrayList<String>> tablero2 = new ArrayList<ArrayList<String>>();

		//Primero solo guardamos las filas que tengan alguna X o que no tengan X pero
		//alguna fila posterior a ella si tenga 
		for(int i=0; i<tablero.size()-1; i++) {
			if(tablero.get(i).contains("X")) {
				tablero2.add(tablero.get(i));
			}else{
				if(tablero2.size()>0){
					if(HayMasX(i)) {			//HayMasX devuelve true si alguna fila posterior contiene X si no devuelve false
						tablero2.add(tablero.get(i));
					}else {
						break;							//break para que se detenga el bucle una vez que no queden mas X asi nos evitamos muchas comparaciones innecesarias
					}
				}
			}
		}
		//Ahora tenemos que eliminar las columnas para ello buscaremos la posicion mas 
		//pequeña donde haya una X y la mas grande.
		for(int i=0; i<tablero2.size(); i++) {
			for(int j=0; j<tablero2.get(i).size(); j++) {
				if(tablero2.get(i).get(j) == "."){
					contadorPrimera++;
				}else {
					break;
				}
			}
			if(contadorPrimera<posicionPrimera) {
				posicionPrimera=contadorPrimera;
			}
			contadorPrimera=0;
			
			for(int k=0; k<tablero2.get(i).size(); k++) {
				contadorUltima++;
				if(tablero2.get(i).get(k).equals("X")) {
					if(contadorUltima>posicionUltima)
						posicionUltima=contadorUltima;
				}
			}
			contadorUltima=0;
		}
		//Una vez obtenidas vaciamos el tablero y copiamos el nuevo pero solo entre la posicion
		//de la primera X y la ultima de esta manera eliminamos las columnas que nos sobraban
		tablero.removeAll(tablero);
		for(int i=0; i<tablero2.size(); i++) {
			tablero.add(new ArrayList<String>());
			for(int j=posicionPrimera; j<posicionUltima; j++) {
				tablero.get(i).add(tablero2.get(i).get(j));
			}
		}
	}

	//Metodo que cuenta el numero de celdas vivas que quedan
	public int Recuento() {
		int contador=0;
		for(int i=0; i<tablero.size(); i++)
			for(int j=0; j<tablero.get(i).size(); j++)
				if(tablero.get(i).get(j).equals("X"))
					contador++;
		return contador;
	}

	//Metodo que comprueba si hay alguna fila con X despues de una entera de puntos
	public boolean HayMasX(int indice) {
		boolean b=false;
		for(int i=indice; i<tablero.size(); i++) {
			if(tablero.get(i).contains("X")) {
				b=true;
				break;		//Break para ahorrarnos comparaciones innecesarias
			}
		}
		return b;
	}

	public void Simular() {
		Runnable s = new Simulacion(this,iteraciones);
		Thread simulacion = new Thread(s);
		simulacion.start();
	}
	
	public void Pintar() {
		int tamAX = tamX;
		if(tablero.size()+4>=tamAX)
			tamX = tablero.size()+4;
		int tamAY = tamY;
		if(tablero.get(0).size()+4>=tamAY)
			tamY = tablero.get(0).size()+4;
		if(tamX>=80 || tamY>=80)
			simulando=false;
		if(simulando==true) {
			GridLayout lay = new GridLayout(tamX,tamY);
			p.setLayout(lay);
			int centrarX = (tamX-tablero.size())/2;
			int centrarY = (tamY-tablero.get(0).size())/2;
			p.removeAll();
			for(int i=0; i<tamX; i++) {
				for(int j=0; j<tamY; j++) {
					if(i<centrarX || i>=centrarX + tablero.size() || j<centrarY || j>=centrarY + tablero.get(0).size()) {
						JButton bB = new JButton();
						bB.setBackground(Color.black);
						p.add(bB);
					}else {
						if(tablero.get(i-centrarX).get(j-centrarY).equals("X")) {
							JButton bX = new JButton();
							bX.setBackground(Color.green);
							p.add(bX);
						}else {
							JButton bP = new JButton();
							bP.setBackground(Color.black);
							p.add(bP);
						}
					}
				}
			}
			p.repaint();
			this.setVisible(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		String respuesta="";
		if(ev.getActionCommand() == "Avanzar") {
			celulas.setText(Integer.toString(Recuento()));
			if(Recuento()>2) {
				if(tamX>=80 || tamY>=80)
					JOptionPane.showMessageDialog(this, "Tamaño maximo alcanzado");
				else {
					tablero = Modificar(tablero);
					Limpiar();
					Pintar();
					p.updateUI();
				}
			}else {
				JOptionPane.showMessageDialog(this, "Tamaño minimo alcanzado");
			}
		}
		if(ev.getActionCommand() == "Simular") {
			simulando = true;
			do {
				respuesta = JOptionPane.showInputDialog("¿Cuantas iteraciones?");
				if(respuesta==null)
					respuesta="0";
			}while(respuesta.equals(""));
			iteraciones = Integer.parseInt(respuesta);
			if(iteraciones > 0) {
				if(tamX>=80 || tamY>=80){
					JOptionPane.showMessageDialog(this, "Tamaño maximo alcanzado");
				}else {
					Simular();
					}
				}
			}
		if(ev.getActionCommand() == " STOP ") {
			simulando=false;
		}
		if(ev.getActionCommand()=="Elegir otra") {
			this.dispose();
			Plantillas p = new Plantillas();
		}
		}
	}

