package twd.conseil.rules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import twd.conseil.Survivor;
import twd.conseil.constant.RuleStatus;
import twd.conseil.constant.SurvivorClass;

public class RuleScout implements SurvivorRule {

	RuleScoutCriticalDamage ruleDamage = new RuleScoutCriticalDamage();
	RuleScoutThreatReduction ruleThreat = new RuleScoutThreatReduction();
	int damageVsThreat = 0;
	final static Logger log = LogManager.getLogger();

	@Override
	public boolean isElligible(Survivor survivor) {
		return survivor.getClassName().equals(SurvivorClass.ECLAIREUR);
	}

	@Override
	public RuleStatus statut() {
		return damageVsThreat > 0 ? ruleDamage.statut() : ruleThreat.statut();
	}

	@Override
	public String description() {
		String descr = "Il existe 2 types de scout : dommages critiques (puissance maximum) et réduction de la menace (peut prendre quelques coups) :";
		descr += "\n*** Dommages critiques ***\n";
		descr += ruleDamage.description();
		descr += "\n*** Réduction de la menace ***\n";
		descr += ruleThreat.description();
		return descr;
	}

	@Override
	public String recommandation() {
		String reco = "Ce scout a un profil " + (damageVsThreat >= 0 ? "dommages critiques" : "réduction de la menace") + " : ";
		reco += (damageVsThreat >= 0 ? ruleDamage.recommandation() : ruleThreat.recommandation());
		return reco;
	}

	@Override
	public void processRule(Survivor survivor) {
		ruleDamage.processRule(survivor);
		ruleThreat.processRule(survivor);
		int pDammage = negativePoints(ruleDamage);
		int pThreat = negativePoints(ruleThreat);
		damageVsThreat = pDammage - pThreat;
		log.debug(String.format("    scout : damage = %s, threat=%s", pDammage, pThreat));
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
