package twd.conseil.rules;

import java.util.Set;

import twd.conseil.constant.SurvivorClass;
import twd.conseil.constant.Trait;

public class RuleHunter extends AbstractSurvirvorClassRule {

	@Override
	protected SurvivorClass getClassName() {
		return SurvivorClass.CHASSEUR;
	}

	@Override
	protected Set<Trait> getMustHaveTraits() {
		return Set.<Trait>of(Trait.VENGEANCE, Trait.IMPITOYABLE, Trait.CHANCEUX, Trait.ADRESSE, Trait.VISEE_CRITIQUE);
	}

	@Override
	protected Set<Trait> getNiceHaveTraits() {
		return Set.<Trait>of(Trait.TIR_INFAILLIBLE, Trait.VIGILANT); 
	}

	@Override
	protected Set<Trait> getUselessTraits() {
		return Set.<Trait>of(Trait.ESQUIVE, Trait.RIPOSTE, Trait.PEAU_DURE, Trait.ENTRE_BALLES, Trait.POSITION_DEFENSIVE); 
	}
   
}
