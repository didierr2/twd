package twd;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Insigne implements Serializable {

	private static final long serialVersionUID = 1L;
	public int csvLine;
	public String effet;
	public double puissance;
	public int etoiles;
	public int emplacement;
	public String set;
	public String extra;
	public double extraPuissance;
	public boolean equipe = false;

	
}
