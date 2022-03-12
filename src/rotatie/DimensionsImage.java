package rotatie;
import java.awt.image.BufferedImage;

//Clasa extinsa a citirii imaginii in care se vor duce dimensiuni
public abstract class DimensionsImage extends ReaderImage{

	public DimensionsImage() {}
	
	public int getsWidth()
	{
		return this.image.getWidth()-1;  //Se preia latimea imaginii
	}
	
	public int getsHeight()
	{
		return this.image.getHeight()-1; //Se preia inaltimea imaginii
	}
	
	public BufferedImage getImage() 
	{ 
        return this.image; 				 //Se va duce BufferedImage cu imaginea salvata
    }
	
	public int getDegrees()
	{
		return this.degrees; 			 //Se va duce valoarea numarului de grade introduse
	}
	
	public String getOut()
	{
		return this.path_out; 			 //Se va duce destinatia fisierului de output
	}
}
