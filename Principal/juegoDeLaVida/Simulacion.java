package juegoDeLaVida;

import javax.swing.JOptionPane;

public class Simulacion implements Runnable {

	GameOfLife g;
	int iteraciones;
	
	public Simulacion(GameOfLife g,int iteraciones) {
		this.g=g;
		this.iteraciones=iteraciones;
	}
	
	@Override
	public void run() {
		int cont = 0;
		int vivas=0;
		while(g.simulando==true) {
			vivas = g.Recuento();
			if(vivas>2) {
				g.tablero = g.Modificar(g.tablero);
				g.Limpiar();
				g.Pintar();
				//g.celulas.setText(Integer.toString(vivas));
				try {
					Thread.sleep(g.velocidad);
				} catch (InterruptedException e) {
				}
				cont++;
				if(cont>iteraciones) {
					g.simulando=false;
				}
			}else {
				g.simulando=false;
				JOptionPane.showMessageDialog(g, "Tamaño minimo alcanzado");
			}
		}
	}
}