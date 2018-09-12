/**
 * Implementacion sencilla del juego de la vida con interfaz grafica
 */
package juegoDeLaVida;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Inicio extends JFrame implements ActionListener {

	public Inicio() {
		
		JButton bL = new JButton("Modo Libre");
		bL.addActionListener(this);
		JButton bP = new JButton("Importar plantilla");
		bP.addActionListener(this);
		
		GridLayout lay = new GridLayout(1,1);
		this.setLayout(lay);
		this.add(bL);
		this.add(bP);
		this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(450, 250, 400, 200);
        this.setVisible(true);
	}
	public static void main(String[] args) {
		Inicio i = new Inicio();
	}
	@Override
	public void actionPerformed(ActionEvent ev) {
		if(ev.getActionCommand() == "Modo Libre")
			System.out.println("Coming soon");
		if(ev.getActionCommand() == "Importar plantilla") {
			this.dispose();
			Plantillas p = new Plantillas();
		}
	}
}
