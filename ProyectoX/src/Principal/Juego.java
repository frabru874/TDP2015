package Principal;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import GUI.GUI;
import Threaders.BombaThread;
import Threaders.EnemigoThread;

/**
 * Clase principal del juego. Responsable de iniciar toda la ejecución
 */
public class Juego 
{
	protected int puntaje;
	protected int reloj;
//	protected PositionList<Enemigo> listaEnemigos;
	protected Bomberman jugador;
	protected Terreno terreno;
	protected GUI gui;
	protected int anchoLabel, altoLabel;
	protected EnemigoThread hiloEnemigo;
	/**
	 * Constructor de la clase, crea una nueva instancia de la misma.
	 */
	public Juego(GUI g)
	{
		puntaje= 0;
		terreno= new Terreno();
		jugador= new Bomberman(terreno);
		//Agrego a bomberman al terreno
		terreno.getCelda(1, 1).agregarElementoACelda(jugador);
		gui = g;
		hiloEnemigo = new EnemigoThread(this);
		hiloEnemigo.start();
	}
	
	/**
	 * Retorna el bomberman del Juego
	 * @return jugador
	 */
	public Bomberman getBomberman()
	{
		return jugador;
	}
	
	/**
	 * Retorna el terreno del juego
	 * @return terreno
	 */
	public Terreno getTerreno()
	{
		return terreno;
	}
	
	/**
	 * Mueve al bomberman hacia arriba y actualiza la GUI
	 */
	public void moverBombermanUP() {
		jugador.avanzar(jugador.getX(), jugador.getY()-1);
		if (jugador.getCelda() != jugador.getCeldaAnterior())
			actualizarGUI(jugador.getCelda(),jugador.getCeldaAnterior());
		actualizarPuntaje();
	}
	
	

	

	/**
	 * Mueve al bomberman hacia abajo y actualiza la GUI
	 */
	public void moverBombermanDOWN() {
		jugador.avanzar(jugador.getX(), jugador.getY()+1);
		if (jugador.getCelda() != jugador.getCeldaAnterior())
			actualizarGUI(jugador.getCelda(),jugador.getCeldaAnterior());
		actualizarPuntaje();
	}
	
	/**
	 * Mueve al bomberman hacia la derecha y actualiza la GUI
	 */
	public void moverBombermanRIGHT() {
		jugador.avanzar(jugador.getX()+1, jugador.getY());
		if (jugador.getCelda() != jugador.getCeldaAnterior())
			actualizarGUI(jugador.getCelda(),jugador.getCeldaAnterior());
		actualizarPuntaje();
		
	}
	
	/**
	 * Mueve al bomberman hacia la izquierda y actualiza la GUI
	 */
	public void moverBombermanLEFT() {
		jugador.avanzar(jugador.getX()-1, jugador.getY());
		if (jugador.getCelda() != jugador.getCeldaAnterior())
			actualizarGUI(jugador.getCelda(),jugador.getCeldaAnterior());
		actualizarPuntaje();
	}
	
	
	
	public void actualizarPuntaje() {
		gui.actualizarPuntaje(jugador.getPuntos());
		
	}
	
	/**
	 * Atuliza los componentes graficos de la GUI
	 */
	public void actualizarGUI()
	{	
		anchoLabel = gui.getAnchoLabel();

		altoLabel = gui.getAltoLabel();
		ImageIcon imagenCelda;
		for (int i = 0; i < 31 ; i++)
		{
			for (int j = 0; j <13; j++)
			{
				ElementoEnCelda elementoCelda= terreno.getCelda(i, j).obtenerElem();
				if (elementoCelda == null)
				{
					 imagenCelda= new ImageIcon(getClass().getResource("/images/Transitable.jpg"));
				}
				else
				{
					imagenCelda = elementoCelda.getImagen();
				}
				JLabel labelActual = gui.getLabelEnMatriz(i,j);
				labelActual.setIcon(imagenCelda);
				labelActual.setBounds((i*anchoLabel),(j*altoLabel),anchoLabel,altoLabel);
				
				gui.getPanelJuego().add(labelActual);
			}
		}
		gui.getPanelJuego().repaint();
	}
	
	public void actualizarGUI(Celda c1, Celda c2) {
		ElementoEnCelda elementoC1= c1.obtenerElem();
		ImageIcon imagenC1;
		if (elementoC1 == null)
		{
			 imagenC1= new ImageIcon(getClass().getResource("/images/Transitable.jpg"));
		}
		else
		{
			imagenC1 = elementoC1.getImagen();
			
		}
		JLabel labelActual = gui.getLabelEnMatriz(c1.getPosX(),c1.getPosY());
		
		labelActual.setIcon(imagenC1);
//		labelActual.setBounds((c1.getPosX()*tamLabel),(c1.getPosY()*tamLabel),tamLabel,tamLabel);
		
		gui.getPanelJuego().add(labelActual);
		
		ElementoEnCelda elementoC2= c2.obtenerElem();
		ImageIcon imagenC2;
		if (elementoC2 == null)
		{
			 imagenC2= new ImageIcon(getClass().getResource("/images/Transitable.jpg"));
		}
		else
		{
			imagenC2 = elementoC2.getImagen();
		}
		JLabel labelActual2 = gui.getLabelEnMatriz(c2.getPosX(),c2.getPosY());
		labelActual2.setIcon(imagenC2);
//		labelActual.setBounds((c2.getPosX()*tamLabel),(c2.getPosY()*tamLabel),tamLabel,tamLabel);
		gui.getPanelJuego().add(labelActual2);

		gui.getPanelJuego().repaint();
	}
	
	/**
	 * Pone una bomba en el terreno en la celda actual del bomberman
	 */
	public void ponerBomba() {

		if (jugador.obtenerCantBombas() > 0)
		{	
			BombaThread t = new BombaThread(this,jugador);
			t.start();
		}
		
	}

	public void agregarEnemigo(Enemigo e) {
		 Random  rnd = new Random();
		 int fila = 1+rnd.nextInt(29);
		 int col = 1+rnd.nextInt(11);
		 while(((fila % 2 == 0) && (col % 2 == 0)) || (fila < 9 && col < 5))
		 {
			  fila = 1+rnd.nextInt(29);
			  col = 1+rnd.nextInt(11);
		 }
			 Celda c = terreno.getCelda(fila,col);
		 	 c.agregarElementoACelda(e);
			 e.setCelda(c);
			 e.setCeldaAnterior(c);
			 e.setX(c.getPosX());
			 e.setY(c.getPosY());
		 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void actualizarGUI(Celda c1) {
		ElementoEnCelda elementoC1= c1.obtenerElem();
		ImageIcon imagenC1;
		if (elementoC1 == null)
		{ 
			 imagenC1= new ImageIcon(getClass().getResource("/images/Transitable.jpg"));
		}
		else
		{
			imagenC1 = elementoC1.getImagen();
			if (imagenC1 == null)
				System.out.println("NO HAY IMAGEN");
			
		}
		JLabel labelActual = gui.getLabelEnMatriz(c1.getPosX(),c1.getPosY());
		
		labelActual.setIcon(imagenC1);
	//	labelActual.setBounds((c1.getPosX()*tamLabel),(c1.getPosY()*tamLabel),tamLabel,tamLabel);
		gui.getPanelJuego().add(labelActual);

		gui.getPanelJuego().repaint();
		
	}

	public void quitarEnemigo() {
		Rugulus[] mR = hiloEnemigo.getMisRugulus();
		int pos = 0;
		for(Rugulus r : mR)
		{
			if (r != null)
			{
				if (!r.estaVivo())
				{
					pos = r.getPosEnRugulus();
					mR[pos] = null;
					hiloEnemigo.restarEnemigo();
				}
			}
		}
		Altair[] mA = hiloEnemigo.getMisAltair();
		pos = 0;
		for(Altair a: mA)
		{
			if (a != null)
			{
				if (!a.estaVivo())
				{
					pos = a.getPosEnAltair();
					mA[pos] = null;
					hiloEnemigo.restarEnemigo();
				}
			}
		}
		Sirius mS = hiloEnemigo.getMiSirius();
		if (mS !=null)
		{
			if (!mS.estaVivo())
			{	
				hiloEnemigo.matarSirius();
				mS = null;
				hiloEnemigo.restarEnemigo();
			}
		}
	}

	public void agregarEnemigo(Sirius e, int i, int j) {
		 Celda c = terreno.getCelda(i,j);
	 	 c.agregarElementoACelda(e);
		 e.setCelda(c);
		 e.setCeldaAnterior(c);
		 e.setX(c.getPosX());
		 e.setY(c.getPosY());
		
	}

	
}