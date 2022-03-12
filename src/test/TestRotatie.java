package test;
//Import clase necesare
import java.util.Arrays;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import rotatie.BufferClass;
import rotatie.ConsumerClass;
import rotatie.ProducerClass;
import rotatie.RotationImage;
import java.awt.image.BufferedImage;

public class TestRotatie {

	  //Conditii rulare:   1.Se va introduce numele fisierului care se doreste a fi rotit din folderul de input (fara extensia .bmp)
	  //(cu si pentru	   2.Se va introduce numele fiserului rezultat (fara extensia .bmp)
      // parametri)	       3.Se va introduce numarul de grade cu care se doreste a se roti imaginea
	  private final static Integer[] options = new Integer[]{90, 180, 270}; //Initializare cu 3 valori acceptabile de tip integer
	  private final static RotationImage rotation = new  RotationImage();//Initializare cu clasa de rotatie
	  private final static String[] steps = new String[]{ //Vector in care se vor afisa etapele executiei la final
		      "Etapa de citire informatii:         ",
		      "Etapa de citire fisier sursa:       ",
		      "Etapa de procesare imagine:         ",
		      "Etapa de scriere fisier destinatie: "
		    };

	  public static void main(String[] args) throws IOException{
		
	    long   timeStart = System.currentTimeMillis(); //Incepem sa inregistram timpul de start al executiei unei operatii
	    long[] timeElapsed = new long[steps.length];   //Timpul scurs in executia unei operatii
	    long   timeEnd; 							   //Se va salva finalul executiei ca si timp
	    Path   path_current = Paths.get("");           //Se va aduce locatia curenta unde ruleaza programul
	    String path_open = path_current.toAbsolutePath().toString() + "\\src\\output"; //Se va adauga locatia folderului care se deschide in String
	    
		rotation.readImage(args); //Apelare functie citire
		BufferedImage imagine = rotation.getImage(); //Apelare functie pentru a aduce matricea citita
	    int width = rotation.getsWidth();    //Salvare variabila latime
	    int height = rotation.getsHeight();  //Salvare variabila inaltime
	    String path_out = rotation.getOut(); //Salvare variabila cu folderul de output in String
	    int degrees = rotation.getDegrees(); //Se salveaza in degrees valoarea introdusa in consola
	    while (!Arrays.asList(options).contains(degrees)) { //Pentru cazul in care numarul de grade scris nu este una dintre cele 3 valori
	        System.out.println("\nNumarul de grade: " + degrees + " este incorect."); //Afisare numar de grade introdus
	        System.out.println("\nProgramul se va inchide.");
	        System.exit(0);
	    }
	    
	    //Parcurgere matrice pe sferturi
	    int[] controller = new int[4];
	    for (int i = 0; i < 3; i++){
	    	controller[i] = (height/4) * (i+1);
	    }
	    controller[3] = height+1;

	    timeEnd = System.currentTimeMillis();   //Se va lua timpul in care s-a finalizat etapa
	    timeElapsed[0] = timeEnd - timeStart;   //Se calculeaza timpii de executie pentru etapa de citire a informatiilor
	    timeStart = System.currentTimeMillis(); //Se reinitializeaza variabila de start cu timpul curent

	    BufferClass buffer = new BufferClass(controller[3], width); //Se va crea variabila de tip buffer
	    ProducerClass producer = new ProducerClass(buffer, controller, imagine); //Se va crea producer
	    ConsumerClass consumer = new ConsumerClass(buffer, controller, height, width); //Se va crea consumer
	    producer.start(); //Se porneste producer
	    consumer.start(); //Se porneste consumer
	    try {
	        producer.join(); //Va reuni threadul curent de producer cu cel principal
	        consumer.join(); //Va reuni threadul curent de consumer cu cel principal
	      } catch (InterruptedException e) { //Daca threadul pentru producer sau consumer este intrerupt, atunci se va intra in exceptii
	        e.printStackTrace();
	      } 
	    
	    timeEnd = System.currentTimeMillis();   //Se va lua timpul in care s-a finalizat etapa
	    timeElapsed[1] = timeEnd - timeStart;   //Se calculeaza timpii de executie pentru etapa de citire fisierului sursa
	    timeStart = System.currentTimeMillis(); //Se reinitializeaza variabila de start cu timpul curent
	    
	    int[][] matrix_image = null; //Initializare matrice cu null
	    matrix_image = consumer.get_matrix(); //Matricea curenta se va prelua din consumer
	    
	    int amount = degrees / 90;   //In functie de valoarea introdusa se vor crea 3 cazuri (1,2,3) care vor fi apelate mai jos
	    
	    if (amount % 2 == 1) {       //In cazul in care rotatia va fi de 90 sau 270 de grade, inaltimea si latimea imaginii se vor schimba
	    	height = height + width;
	    	width = height - width;
	    	height = height - width;
	    }
	    
	    BufferedImage image_out = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // Atribuire buffer imagine finala
	    int[][] final_matrix = matrix_image;

	    final_matrix = matrix_image; 						   //Initializare matrice finala cu cea curenta
	    if(amount == 1){
	    	final_matrix = rotation.Rotatie_90(matrix_image);  //Aplicare rotatie 90
	    }
	    else if(amount == 2){
	    	final_matrix = rotation.Rotatie_180(matrix_image); //Aplicare rotatie 180
	    }
	    else if(amount == 3){
	    	final_matrix = rotation.Rotatie_270(matrix_image); //Aplicare rotatie 270
	    }
	    
	    for (int i = 0; i < height; i++){
	    	for (int j = 0; j < width; j++){
	    		image_out.setRGB(j, i, final_matrix[i][j]);    //Parcurgere si atribuire pixeli asupra imaginii
	    	} 
	    }       
	    
	    timeEnd = System.currentTimeMillis();   //Se va lua timpul in care s-a finalizat etapa
	    timeElapsed[2] = timeEnd - timeStart;   //Se calculeaza timpii de executie pentru etapa de procesare a imaginii
	    timeStart = System.currentTimeMillis(); //Se reinitializeaza variabila de start cu timpul curent
	    
	    try {
	      ImageIO.write(image_out, "bmp", new File(path_out)); // Scriere imagine cu extensia bmp in folderul din path_out
	    } catch (IOException e) {
	      System.out.println("Eroare: " + e);   //Exceptie cu mesaj de eroare in cazul in care nu reuseste
	    }

	    timeEnd = System.currentTimeMillis();   //Se va lua timpul in care s-a finalizat etapa
	    timeElapsed[3] = timeEnd - timeStart;   //Se calculeaza timpii de executie pentru etapa de scriere in fisierul de output

	    for (int i = 0; i < 4; i++)
	    {
	      System.out.println(steps[i] + (float)timeElapsed[i]/1000 + " secunde."); //Se vor afisa timpii de executie pentru fiecare valoarea din vectorul steps
	      if (i == 3) // Dupa ce producer a incarcat toata informatia si consumer a primit toata informatia
	      {
	    	  System.out.println("\nSe va deschide folderul de output: " + path_open); //Mesaj cu locatia folderului de output
	    	  Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + path_open); //Se va rula explorer.exe si se va adauga locatia fisierului de output
	      }
	    }

	    timeElapsed = null; //Se va reinitializa cu null timpul scurs
	    
	    //Reinitializare valori matrice si controller (parcurgerea matricei)
	    for (int i = 0; i < matrix_image.length; i++)
	      matrix_image[i] = null;
	    matrix_image = null;
	    controller = null;
	    
	    //Reinitializare valori matrice si imagine intrare si iesire
	    for (int i = 0; i < final_matrix.length; i++)
	      final_matrix[i] = null;
	    final_matrix = null;
	    imagine = null;
	    image_out = null;
	  }
}
