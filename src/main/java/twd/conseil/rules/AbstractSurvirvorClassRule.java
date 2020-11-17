package twd.conseil.rules;

import java.util.Set;

import twd.conseil.Survivor;
import twd.conseil.constant.RuleStatus;
import twd.conseil.constant.SurvivorClass;
import twd.conseil.constant.Trait;

public abstract class AbstractSurvirvorClassRule implements SurvivorRule {
    
	Set<Trait> mustHave = null;
	Set<Trait> niceHave = null; 
	Set<Trait> useless = null; 

	String aObtenir = "";
	String aOptimiser = "";
	String aChanger = "";
	String aSupprimer = "";
	int aObtenirNb = 0;
	int aOptimiserNb = 0;
	int aChangerNb = 0;
	int aSupprimerNb = 0;
    
    
    protected abstract SurvivorClass getClassName();
    protected abstract Set<Trait> getMustHaveTraits();
    protected abstract Set<Trait> getNiceHaveTraits();
    protected abstract Set<Trait> getUselessTraits();

	@Override
	public boolean isElligible(Survivor survivor) {
		return survivor.getClassName().equals(getClassName());
	}


	@Override
	public void processRule(Survivor survivor) {
		loadTraits();		

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

		// On prend en compte tous les autres
		for (Trait t : survivor.getTraits()) {
			if (!mustHave.contains(t)
				&& !niceHave.contains(t)
				&& !useless.contains(t)) {
					aChanger += t.getName() + ", ";
					aChangerNb++;
				}
		}
	}

	@Override
	public RuleStatus statut() {
		RuleStatus status = RuleStatus.ACHIEVED;
		if (aSupprimerNb > 0) {
			status = RuleStatus.NOT_REACHED;
		} 
		else if (aChangerNb > 0) {
			status = RuleStatus.TO_IMPROVE;
		}
		else if (aObtenirNb > 0 && aOptimiserNb > 0) {
			status = RuleStatus.TO_IMPROVE;
		}
		return status;
	}

	@Override
	public String description() {
		loadTraits();
		// System.err.println(getClass().getSimpleName() + "> description : " + getClassName());
		String descr = "Vérifie les attributs du " + getClassName().getName() + " : ";
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
		String reco = "Les attributs sont optimum pour le " + getClassName().getName();
		if (aSupprimerNb > 0) {
			reco = String.format("Changer %s (peu utile pour un %s) et essayer d'obtenir %s", toStringTraits(aSupprimer, aSupprimerNb), getClassName().getName(), (aObtenirNb > 0 ? toStringTraits(aObtenir, aObtenirNb) : toStringTraits(aOptimiser, aOptimiserNb)));
		} 
		else if (aChangerNb > 0) {
			reco = String.format("Changer %s et essayer d'obtenir %s", toStringTraits(aChanger, aChangerNb), (aObtenirNb > 0 ? toStringTraits(aObtenir, aObtenirNb) : toStringTraits(aOptimiser, aOptimiserNb)));
		}
		else if (aObtenirNb > 0 && aOptimiserNb > 0) {
			reco = String.format("Changer %s et essayer d'obtenir %s", toStringTraits(aOptimiser, aOptimiserNb), toStringTraits(aObtenir, aObtenirNb));
		}
		return reco;
	}

	private String toStringTraits(String attrs, int nb) {
		return (nb > 1 ? "les attributs " : "l'attribut ") + attrs.substring(0, attrs.length() - 2);
	}

	
	private void loadTraits() {
		if (mustHave == null) {
			mustHave = getMustHaveTraits();
        	niceHave = getNiceHaveTraits();
			useless = getUselessTraits();
		}
	}

}
