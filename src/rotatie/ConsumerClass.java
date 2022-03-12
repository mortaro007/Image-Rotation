package rotatie;
// In aceasta clasa vom primi cate un sfert din matrice, se va prelucra si se va scrie intr-o matrice finala

public class ConsumerClass extends Thread{ //Consumer mosteneste clasa Thread
	  private BufferClass current_buffer; //Variabila de tip buffer
	  private int[] controller; //Vectorul de control
	  private int[][] matrix; //Matricea in care se va salva

	  public ConsumerClass(BufferClass current_buffer, int[] controller, int height, int width) { //Constructor
	    this.matrix = new int[height][width]; //Initializare matrice cu latime si inaltime
	    this.current_buffer = current_buffer; //Salvam variabila de tip buffer
	    this.controller = controller; //Salvam variabila de tip controller
	  }

	  public void run() {
	    int width = matrix[0].length; //Atribuim valoarea din matrice pentru latime
	    int[][] quarters  = new int[controller[0]][width]; //Initializare matrice cu 3 sferturi din primite
	    int[][] last_quarter = new int[controller[3] - controller[2] - 1][width]; //Initializare matrice cu ultimul sfert primit
	    
	    for (int i = 0; i < 3; i++) {
	    	quarters = current_buffer.get(controller[i] - controller[0], controller[i], width); //Primim matricea quarters de la producer
	      for (int j = 0; j < controller[0]; ++j)
	        for (int k = 0; k < width; k++)
	          matrix[j + controller[i] - controller[0]][k] = quarters[j][k]; //Se atribuie valorile matricei finale
	    	  System.out.println("Consumer a primit " + (i+1) +  "/4 din informatie.\n");
	      try {
	        sleep(1000); //Se asteapta o secunda dupa ce consumerul preia matricea
	      } catch (InterruptedException e) {} //Exceptii
	    }

	    last_quarter = current_buffer.get(controller[2] + 1, controller[3], width); //Primim matricea last_quarter de la producer
	    for (int i = 0; i < controller[3] - controller[2] - 1; i++)
	      for (int j = 0; j < width; j++)
	        matrix[i + controller[2]][j] = last_quarter[i][j]; //Se atribuie valorile matricei finale
	    System.out.println("Consumer a primit toata informatia (4/4).\n"); //Infromare consumer primire informatie finala
	    try {
	      sleep(1000); //Se asteapta o secunda dupa ce consumerul preia matricea
	    } catch (InterruptedException e) { //Exceptii
	    	e.printStackTrace();
	    } 
	  }

	  public int[][] get_matrix()
	  {
	    return this.matrix; //Returneaza matricea finala
	  }
}
