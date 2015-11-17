package Principal;

import java.util.Random;

import javax.swing.ImageIcon;

public class Rugulus  extends Enemigo{

	// CUANDO SE AGREGA EN JUEGO A TERRENO HAY Q SETEAR X,Y Y LA CELDA
	public Rugulus(Terreno t)//, int x, int y)
	{
		terreno = t;
//		posX = x;
//		posY = y;
		//imagen = new ImageIcon(getClass().getResource("/images/Rugulus.jpg"));
		imagen = new ImageIcon(getClass().getResource("/images/bomberman.png"));
		modoDios = false;
//		miCelda = terreno.getCelda(posX, posY);
		estaVivo = true;
		
	}
	
	@Override
	public int destruirse() {
		super.miCelda.eliminarElementoEnCelda();
		estaVivo = false;
		return 15;
		
	}




	@Override
	/* Si direccion es 0, intenta avanzar hacia Arriba.
	 * Si direccion es 1, intenta avanzar hacia Abajo.
	 * Si direccion es 2, intenta avanzar hacia Izquierda.
	 * Si direccion es 3, intenta avanzar hacia Derecha.
	 */
	public void avanzar() {
		 Random  rnd = new Random();
//		 int direccion = rnd.nextInt(4);
		 int direccion = 3;
		 Celda celdaAAvanzar = terreno.getCelda(posX,posY);
		 if (direccion == 0)
			 celdaAAvanzar = terreno.getCelda(posX, posY-1);
		 if (direccion == 1)
			 celdaAAvanzar = terreno.getCelda(posX, posY+1);
		 if (direccion == 2)
			 celdaAAvanzar = terreno.getCelda(posX-1,posY);
		 if (direccion == 3)
			 celdaAAvanzar = terreno.getCelda(posX+1, posY);
		 celdaAAvanzar.avanzar(this);
	}

	

}
