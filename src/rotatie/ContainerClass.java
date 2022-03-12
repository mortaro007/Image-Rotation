package rotatie;

//Clasa abstracta ce contine cele 2 metode pentru BufferClass si extinde clasa de rotatii
abstract class ContainerClass extends RotationImage{
	public abstract int[][] get(int h1, int h2, int w); //Metoda get pentru a returna matricea
	public abstract void put(int pixel[][]); 			//Metoda put pentru a asigna pixeli
}
