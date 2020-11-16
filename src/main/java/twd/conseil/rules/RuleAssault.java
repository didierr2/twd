package twd.conseil.rules;

import java.util.Set;

import twd.conseil.constant.SurvivorClass;
import twd.conseil.constant.Trait;

public class RuleAssault extends AbstractSurvirvorClassRule {

	@Override
	protected SurvivorClass getClassName() {
		return SurvivorClass.SOLDAT;
	}

	@Override
	protected Set<Trait> getMustHaveTraits() {
		return Set.<Trait>of(Trait.VENGEANCE, Trait.RIPOSTE, Trait.CHANCEUX, Trait.PEAU_DURE, Trait.ESQUIVE);
	}

	@Override
	protected Set<Trait> getNiceHaveTraits() {
		return Set.<Trait>of(Trait.ADRESSE); 
	}

	@Override
	protected Set<Trait> getUselessTraits() {
		return Set.<Trait>of(Trait.TIR_INFAILLIBLE, Trait.IMPITOYABLE, Trait.VIGILANT, Trait.ENTRE_BALLES, Trait.POSITION_DEFENSIVE); 
	}
   
}
