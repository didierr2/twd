package twd.conseil.rules;

import java.util.Set;

import twd.conseil.constant.SurvivorClass;
import twd.conseil.constant.Trait;

public class RuleScoutThreatReduction extends AbstractSurvirvorClassRule {

	@Override
	protected SurvivorClass getClassName() {
		return SurvivorClass.ECLAIREUR;
	}


	@Override
	protected Set<Trait> getMustHaveTraits() {
		return Set.<Trait>of(Trait.PEAU_DURE, Trait.ESQUIVE, Trait.CHANCEUX, Trait.BRUTALITE);
	}

	@Override
	protected Set<Trait> getNiceHaveTraits() {
		return Set.<Trait>of(Trait.ENCHAINEMENT, Trait.FRAPPE_PUISSANTE, Trait.RIPOSTE, Trait.IMPITOYABLE); 
	}

	@Override
	protected Set<Trait> getUselessTraits() {
		return Set.<Trait>of(Trait.VIGILANT, Trait.POSITION_DEFENSIVE, Trait.ENTRE_BALLES); 
	}
   
}
