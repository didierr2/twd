package twd;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TwdMain {

	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		new Calculateur().calcule(
				Constants.loadInsignes(), 
				Constants.persos);
	}
}
