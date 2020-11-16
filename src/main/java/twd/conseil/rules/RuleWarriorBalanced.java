package twd.conseil.rules;

import java.util.Set;

import twd.conseil.constant.SurvivorClass;
import twd.conseil.constant.Trait;

public class RuleWarriorBalanced extends AbstractSurvirvorClassRule {

	@Override
	protected SurvivorClass getClassName() {
		return SurvivorClass.GUERRIER;
	}

	@Override
	protected Set<Trait> getMustHaveTraits() {
		return Set.<Trait>of(Trait.PEAU_DURE, Trait.BRUTALITE, Trait.CHANCEUX, Trait.ESQUIVE);
	}

	@Override
	protected Set<Trait> getNiceHaveTraits() {
		return Set.<Trait>of(Trait.RIPOSTE, Trait.IMPITOYABLE, Trait.FRAPPE_PUISSANTE, Trait.ENCHAINEMENT); 
	}

	@Override
	protected Set<Trait> getUselessTraits() {
		return Set.<Trait>of(Trait.VIGILANT, Trait.POSITION_DEFENSIVE, Trait.ENTRE_BALLES); 
	}
   
}
