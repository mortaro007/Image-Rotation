package rotatie;

//Clasa extinsa a clasei de dimensiuni in care se afla toate tipurile de rotatii pentru imagine
public class RotationImage extends DimensionsImage{
	public RotationImage() {}
	
	@Override
	public int[][] Rotatie_90(int[][] matrice) {
	    int height, width; //Initializare inaltime, latime
	    
	    height = getsHeight(); //Apelare metoda ce intoarce inaltimea unei imagini
	    width  = getsWidth();  //Apelare metoda ce intoarce latimea unei imagini

	    int[][] rotire_matrice = new int[width][height]; //Initializare matrice cu valorile de latime si inaltime

	    for (int i = 0; i < height; i++){
	    	for (int j = 0; j < width; j++){
	    		rotire_matrice [j][i] = matrice[i][width-1-j]; //Parcurgere matrice si interschimbare elemente
	    	}
	    }
	return rotire_matrice; // Se va returna matricea modificata
	}
	
	@Override
	public int[][] Rotatie_180(int[][] matrice) {
		int height, width; //Initializare inaltime, latime
		
		height = getsHeight();
	    width  = getsWidth();

        int[][] rotire_matrice = new int[height][width]; //Initializare matrice cu valorile de inaltime si latime

        for (int i = 0; i < height; i++){
	    	for (int j = 0; j < width; j++){
	    		rotire_matrice [i][j] = matrice[height-i-1][width-1-j]; //Parcurgere matrice si interschimbare elemente
	    	}
	    }
        return rotire_matrice; //Se va returna matricea modificata
	}
	
	@Override
	public int[][] Rotatie_270(int[][] matrice) {
		int height, width; 	  //Initializare inaltime, latime

		height = getsHeight();
	    width  = getsWidth();

	    int[][] rotire_matrice = new int[width][height]; 	   //Initializare matrice cu valorile de latime si inaltime
	    
	    for (int i = 0; i < height; i++){
	    	for (int j = 0; j < width; j++){
			   rotire_matrice [j][i] = matrice[height-i-1][j]; //Parcurgere matrice si interschimbare elemente
	    	}
	    }
	    return rotire_matrice; //Se va returna matricea modificata
	}
}