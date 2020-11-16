package twd.conseil.rules;

import java.util.Set;

import twd.conseil.constant.SurvivorClass;
import twd.conseil.constant.Trait;

public class RuleBruiser extends AbstractSurvirvorClassRule {

	@Override
	protected SurvivorClass getClassName() {
		return SurvivorClass.COGNEUR;
	}

	@Override
	protected Set<Trait> getMustHaveTraits() {
		return Set.<Trait>of(Trait.PUNITION, Trait.RIPOSTE, Trait.PEAU_DURE, Trait.CHANCEUX, Trait.ESQUIVE);
	}

	@Override
	protected Set<Trait> getNiceHaveTraits() {
		return Set.<Trait>of(Trait.POSITION_DEFENSIVE); 
	}

	@Override
	protected Set<Trait> getUselessTraits() {
		return Set.<Trait>of(Trait.FRAPPE_PUISSANTE, Trait.BRUTALITE, Trait.VIGILANT, Trait.IMPITOYABLE, Trait.ENTRE_BALLES, Trait.ENCHAINEMENT); 
	}
   
}
