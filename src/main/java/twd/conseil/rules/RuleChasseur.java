package twd.conseil.rules;

import java.util.Set;

import twd.conseil.constant.RuleStatus;
import twd.conseil.constant.SurvivorClass;
import twd.conseil.Survivor;
import twd.conseil.constant.Trait;

public class RuleChasseur implements SurvivorRule {

	Set<Trait> mustHave = Set.<Trait>of(Trait.VENGEANCE, Trait.IMPITOYABLE, Trait.CHANCEUX, Trait.ADRESSE); // manque critical aim
	Set<Trait> niceHave = Set.<Trait>of(Trait.TIR_INFAILLIBLE, Trait.VIGILANT); 
	Set<Trait> useless = Set.<Trait>of(Trait.ESQUIVE, Trait.RIPOSTE, Trait.PEAU_DURE, Trait.ENTRE_BALLES, Trait.POSITION_DEFENSIVE); 

	String aObtenir = "";
	String aOptimiser = "";
	String aSupprimer = "";
	int aObtenirNb = 0;
	int aOptimiserNb = 0;
	int aSupprimerNb = 0;
	
	@Override
	public boolean isElligible(Survivor survivor) {
		return survivor.getClassName().equals(SurvivorClass.CHASSEUR);
	}

	@Override
	public void processRule(Survivor survivor) {
		aObtenirNb = 0;
		for (Trait t : mustHave) {
			if (!survivor.getTraits().contains(t)) {
				aObtenir += t.getName() + ", ";
				aObtenirNb++;
			}
		}
		aOptimiserNb = 0;
		for (Trait t : niceHave) {
			if (survivor.getTraits().contains(t)) {
				aOptimiser += t.getName() + ", ";
				aOptimiserNb++;
			}
		}			
		aSupprimerNb = 0;
		for (Trait t : useless) {
			if (survivor.getTraits().contains(t)) {
				aSupprimer += t.getName() + ", ";
				aSupprimerNb++;
			}
		}			
	}

	@Override
	public RuleStatus statut() {
		RuleStatus status = RuleStatus.ACHIEVED;
		if (aSupprimerNb > 0) {
			status = RuleStatus.NOT_REACHED;
		} 
		else if (aOptimiserNb > 0) {
			status = RuleStatus.TO_IMPROVE;
		}
		return status;
	}

	@Override
	public String description() {
		String descr = "Vérifie les attributs du chasseur : ";
		descr += "\n  - Optimal : ";
		boolean tronqueVirgule = false;
		for (Trait t : mustHave) {
			descr +=  t.getName() + ", ";
			tronqueVirgule = true;
		}
		if (tronqueVirgule) {
			descr = descr.substring(0, descr.length() - 2);
			tronqueVirgule = false;
		}
		descr += "\n  - Utile : ";
		for (Trait t : niceHave) {
			descr +=  t.getName() + ", ";
			tronqueVirgule = true;
		}
		if (tronqueVirgule) {
			descr = descr.substring(0, descr.length() - 2);
			tronqueVirgule = false;
		}
		descr += "\n  - Inutile : ";
		for (Trait t : useless) {
			descr +=  t.getName() + ", ";
			tronqueVirgule = true;
		}
		if (tronqueVirgule) {
			descr = descr.substring(0, descr.length() - 2);
		}
		return descr;
	}

	@Override
	public String recommandation() {
		String reco = "Les attributs sont optimum pour le chasseur";
		if (aSupprimerNb > 0) {
			reco = String.format("Changer %s (peu utile pour un chasseur) et essayer d'obtenir %s", toStringAttribut(aSupprimer, aSupprimerNb), toStringAttribut(aObtenir, aObtenirNb));
		} 
		else if (aOptimiserNb > 0) {
			reco = String.format("Changer %s et essayer d'obtenir %s", toStringAttribut(aOptimiser, aOptimiserNb) + toStringAttribut(aObtenir, aObtenirNb));
		}
		return reco;
	}

	private String toStringAttribut(String attrs, int nb) {
		return (nb > 1 ? "les attributs " : "l'attribut ") + attrs.substring(0, attrs.length() - 2);
	}
    
}
