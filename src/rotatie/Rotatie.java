package rotatie;

// Interfata folosita pentru a citi imagiena si a grupa cele 3 metode de rotire a matricei
interface Rotatie {
	void readImage(String[] args);
	public int[][] Rotatie_90(int[][] matrice);
	public int[][] Rotatie_180(int[][] matrice);
	public int[][] Rotatie_270(int[][] matrice);
}
