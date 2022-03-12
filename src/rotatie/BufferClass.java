package rotatie;

public class BufferClass extends ContainerClass{ 	//Clasa Buffer
	private int[][] current_pixel; 					//Matricea de pixeli ce va fi transferata de la producer la consumer
	private boolean available = false; 				//Variabila boolean care va spune care din threaduri sa functioneze
	
	public BufferClass(int... size){
		current_pixel = new int[size[0]][size[1]]; //Se va initializa matricea de pixeli
	}
	
	public synchronized int[][] get(int h1, int h2, int width){ //Functia get
		while(available == false){ 	//Daca available este fals, se asteapta ca producatorul sa puna o valoare
			try{
				wait(); 			//Asteapta producerul sa puna o valoare
			}
			catch (InterruptedException e){ //Catch exception in cazul in care nu functioneaza
				e.printStackTrace(); 		//Se afiseaza Stack-Trace
			}
		}
		available = false; //In cazul in care s-a ajuns aici inseamna ca consumerul a primit valoarea si transformam variabila booleana in false
		notifyAll();	   //Se notifica celelalte threaduri
		
		int[][] selected_pixel = new int[h2-h1][width];
		for(int i = 0; i < h2 - h1; i++){
			for(int j = 0; j < width; j++){
				selected_pixel[i][j] = current_pixel[i][j]; //Se va atribui valoarea din matricea curenta in matricea care va fi salvata
			}
		}
			
		return selected_pixel; //Functia get returneaza matricea
	}
	
	public synchronized void put(int pixels[][]){ //Functie cu care producerul va pune in consumer
		while(available == true){
			try{
				wait(); //Se asteapta consumatorul pentru a prelua valoarea
			}
			catch (InterruptedException e){ //Catch exception in cazul in care nu functioneaza
				e.printStackTrace(); 		//Se afiseaza Stack-Trace
			}
		}
		
		this.current_pixel = new int[current_pixel.length][current_pixel[0].length]; //Se va salva matricea de pixeli
		this.current_pixel = pixels; //Se vor salva pixelii
		available = true; 			 //Available devine true
		notifyAll(); 				 //Se notifica celelalte threaduri
	}
}
