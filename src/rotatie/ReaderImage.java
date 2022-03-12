package rotatie;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import rotatie.BaseImage;

//Clasa extinsa a clasei de baza in care se citeste imaginea si se proceseaza intr-un BufferedImage
public abstract class ReaderImage extends BaseImage implements Rotatie{
	protected String path_in;     	//Locatia fisierului de input
	protected String path_out;	  	//Locatia fisierului de output
	protected String temp_in;     	//Argument pentru numele fisierului de input
	protected String temp_out;    	//Argument pentru numele fisierului de output
	protected String degrees_arg; 	//Argument pentru numarul de grade
	protected int degrees; 		  	//Numar de grade cu care se va roti imaginea
	protected int width;   		  	//Latimea imaginii
	protected int height;  		  	//Inaltimea imaginii
	protected boolean check_args; 	//Verificare argumente
	
	public ReaderImage() {}
	@Override
    public void readImage(String[] args) {
        Scanner sc = new Scanner(System.in); //Initializare citire din consola
        
        if(args.length > 0) //Verificare argumente
    	{
    		this.temp_in = args[0]; //Stocare nume fisier intrare
    		this.temp_out = args[1]; //Stocare nume fisier iesire
    		this.degrees_arg = args[2]; //Stocare grade cu care se va roti
    		this.check_args = true; //Checker pentru args
    	}
        
        if(!check_args)
        {
    	System.out.print("Introduceti numele fisierului al carei imagini urmeaza a fi rotit: "); //Selectie nume fisier intrare
    	temp_in = sc.nextLine(); //Se va asigna argumentului urmatoarea linie introdusa din consola
        path_in = "src/input/" + temp_in; //Path input
        
		    try {
		        this.image = ImageIO.read(new File(path_in + ".bmp")); //Salvare imagine in BufferedImage
		    } catch (IOException e) { //Exceptii
		    	System.out.println("Eroare: " + e);
		        System.exit(0);
		    }
        
        System.out.print("Introduceti numele fisierului imaginii rezultate:  "); //Selectie nume fisier iesire
        temp_out = sc.nextLine(); //Se va asigna argumentului urmatoarea linie introdusa din consola
        
		    if(temp_out.length() == 0){ //In cazul in care un nume pentru fisierul de output nu este introdus, programul se va inchide
		        System.out.println("\nNu s-a specificat un nume pentru fisierul de output");
		        System.out.println("\nProgramul se va inchide");
		        System.exit(0);
		    }
		    else
		    	path_out = "src/output/" + temp_out + ".bmp"; //Path output
		    
		    System.out.println("Selectati cu cate grade doriti sa fie rotita imaginea: "); //Mesaj cu informare asupra numarului de grade care se pot introduce
		    System.out.println("\t 90 / 180 / 270");
		    degrees = sc.nextInt(); //Citire grade
		    sc.close(); //Inchidere citire
        }
        
        else{
        	path_in = "src/input/" + temp_in; //Path input
    	
			try {
		        this.image = ImageIO.read(new File(path_in + ".bmp")); //Salvare imagine in BufferedImage
		    } catch (IOException e) { //Exceptii
		    	System.out.println("Eroare: " + e);
		        System.exit(0);
		    }
		
			if(temp_out.length() == 0){ //In cazul in care un nume pentru fisierul de output nu este introdus, programul se va inchide
		        System.out.println("\nNu s-a specificat un nume pentru fisierul de output");
		        System.out.println("\nProgramul se va inchide");
		        System.exit(0);
		    }
		    else
		    	path_out = "src/output/" + temp_out + ".bmp"; //Path output
			
			if(degrees_arg.length() == 0){ //In cazul in care gradele cu care se va roti imaginea nu au fost introduse, programul se va inchide
		        System.out.println("\nNu s-au specificat gradele cu care se va roti imaginea");
		        System.out.println("\nProgramul se va inchide");
		        System.exit(0);
		    }
		    else
		    	degrees = Integer.valueOf(degrees_arg); //Salvare grade in variabila
        }
    }
}
