package twd.conseil.rules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import twd.conseil.Survivor;
import twd.conseil.constant.RuleStatus;
import twd.conseil.constant.SurvivorClass;

public class RuleWarrior implements SurvivorRule {

	RuleWarriorCriticalDamage ruleDamage = new RuleWarriorCriticalDamage();
	RuleWarriorBalanced ruleBalanced = new RuleWarriorBalanced();
	int damageVsBalanced = 0;
	final static Logger log = LogManager.getLogger();

	@Override
	public boolean isElligible(Survivor survivor) {
		return survivor.getClassName().equals(SurvivorClass.GUERRIER);
	}

	@Override
	public RuleStatus statut() {
		return damageVsBalanced > 0 ? ruleDamage.statut() : ruleBalanced.statut();
	}	

	@Override
	public String description() {
		String descr = "Il existe 2 types de guerriers : dommages critiques (puissance maximum) et équilibré (peut prendre quelques coups) :";
		descr += "\n*** Dommages critiques ***\n";
		descr += ruleDamage.description();
		descr += "\n*** Equilibré ***\n";
		descr += ruleBalanced.description();
		return descr;
	}

	@Override
	public String recommandation() {
		String reco = "Ce guerrier a un profil " + (damageVsBalanced >= 0 ? "dommages critiques" : "équilibré") + " : ";
		reco += (damageVsBalanced >= 0 ? ruleDamage.recommandation() : ruleBalanced.recommandation());
		return reco;
	}

	@Override
	public void processRule(Survivor survivor) {
		ruleDamage.processRule(survivor);
		ruleBalanced.processRule(survivor);
		int pDammage = negativePoints(ruleDamage);
		int pBalanced = negativePoints(ruleBalanced);
		damageVsBalanced = pDammage - pBalanced;
		log.debug(String.format("    warrior : damage = %s, balanced=%s", pDammage, pBalanced));


	}
   
	private int negativePoints(AbstractSurvirvorClassRule rule) {
		int p = 0;
		p += (rule.aObtenirNb * 3);
		p += (rule.aSupprimerNb * 3);
		p += (rule.aOptimiserNb * 1);
		p += (rule.aChangerNb * 1);
		return p;
	}


}
