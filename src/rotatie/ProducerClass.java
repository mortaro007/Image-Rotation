package rotatie;
import java.awt.image.BufferedImage;;

public class ProducerClass extends Thread{ 	//Producer mosteneste clasa Thread
	  private BufferClass current_buffer;  	//Variabila de tip buffer
	  private int[] controller; 			//Vectorul de control
	  private BufferedImage image; 			//Atribut de tip BufferedImage in care stocam imaginea

	  public ProducerClass(BufferClass current_buffer, int[] controller, BufferedImage image) {
	    this.current_buffer = current_buffer; //Initializam buffer si ii atribuim valoarea
	    this.controller = controller; 		  //Initializam controllerul si ii atribuim valoarea
	    this.image = image; 				  //Salvam imaginea
	  }

	  public void run() {

	    int width = image.getWidth(); 					   						  //Initializare latime si salvare
	    int[][] quarters  = new int[controller[0]][width]; 						  //Initializare matrice cu 3 sferturi din pixeli
	    int[][] last_quarter = new int[controller[3] - controller[2] - 1][width]; //Initializare matrice cu ultimul sfert din pixeli
	    
	    for(int i = 0; i < 3; i++) {
	      for (int j = 0; j < controller[0]; j++)
	        for (int k = 0; k < width; k++) {
	        	quarters[j][k] = image.getRGB(k, j + controller[i] - controller[0]); //In matricea quarters salvam pixelii imaginii
	        } 							//quarters reprezinta o matrice egala cu un sfert din poza (doar 3/4)
	      current_buffer.put(quarters); //Producer trimite catre consumer
	      System.out.println("Producer a incarcat " + (i+1) + "/4 din informatie.");

	      try {
	        sleep(1000); 					 //Se asteapta o secunda dupa ce producerul pune matricea
	      } catch (InterruptedException e) { //Exceptii
	    	  e.printStackTrace();
	      }
	    }
	    
	    for (int i = 0; i < controller[3] - controller[2] - 1; i++)
	      for (int j = 0; j < width; j++)
	        last_quarter[i][j] = image.getRGB(j, i + controller[2]);  	   //In matricea last_quarters salvam pixelii imaginii (ultimii 1/4)
	    current_buffer.put(last_quarter); 							  	   //Producer trimite catre consumer
	    System.out.println("Producer a incarcat toata informatia (4/4)."); //Infromare producer incarcare finala
	    try {
	      sleep(1000); 					   //Se asteapta o secunda dupa ce producerul pune matricea
	    } catch (InterruptedException e) { //Exceptii
	    	
	    }
	  }
}
