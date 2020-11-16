package twd.conseil.rules;

import java.util.Set;

import twd.conseil.constant.SurvivorClass;
import twd.conseil.constant.Trait;

public class RuleScoutCriticalDamage extends AbstractSurvirvorClassRule {

	@Override
	protected SurvivorClass getClassName() {
		return SurvivorClass.ECLAIREUR;
	}

	@Override
	protected Set<Trait> getMustHaveTraits() {
		return Set.<Trait>of(Trait.BRUTALITE, Trait.FRAPPE_PUISSANTE, Trait.IMPITOYABLE);
	}

	@Override
	protected Set<Trait> getNiceHaveTraits() {
		return Set.<Trait>of(Trait.CHANCEUX, Trait.ENCHAINEMENT, Trait.VIGILANT); 
	}

	@Override
	protected Set<Trait> getUselessTraits() {
		return Set.<Trait>of(Trait.ESQUIVE, Trait.RIPOSTE, Trait.PEAU_DURE, Trait.ENTRE_BALLES, Trait.POSITION_DEFENSIVE); 
	}
   
}
