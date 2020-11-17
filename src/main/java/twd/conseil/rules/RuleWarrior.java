package twd.conseil.rules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import twd.conseil.Survivor;
import twd.conseil.constant.RuleStatus;
import twd.conseil.constant.SurvivorClass;

public class RuleWarrior implements SurvivorRule {

	RuleWarriorCriticalDamage ruleDamage = new RuleWarriorCriticalDamage();
	RuleWarriorBalanced ruleBalanced = new RuleWarriorBalanced();
	boolean profilDamage = true;
	final static Logger log = LogManager.getLogger(RuleWarrior.class);

	@Override
	public boolean isElligible(Survivor survivor) {
		return survivor.getClassName().equals(SurvivorClass.GUERRIER);
	}

	@Override
	public RuleStatus statut() {
		return profilDamage ? ruleDamage.statut() : ruleBalanced.statut();
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
		String reco = "Ce guerrier a un profil " + (profilDamage ? "dommages critiques" : "équilibré") + " : ";
		reco += (profilDamage ? ruleDamage.recommandation() : ruleBalanced.recommandation());
		return reco;
	}

	@Override
	public void processRule(Survivor survivor) {
		ruleDamage.processRule(survivor);
		ruleBalanced.processRule(survivor);
		int ecartDamage = negativePoints(ruleDamage);
		int ecartBalanced = negativePoints(ruleBalanced);
		profilDamage = ecartDamage < ecartBalanced;
		log.debug(String.format("    warrior : damage = %s, balanced=%s", ecartDamage, ecartBalanced));


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
