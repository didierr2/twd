package twd.conseil.rules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import twd.conseil.Survivor;
import twd.conseil.constant.RuleStatus;
import twd.conseil.constant.SurvivorClass;

public class RuleScout implements SurvivorRule {

	RuleScoutCriticalDamage ruleDamage = new RuleScoutCriticalDamage();
	RuleScoutThreatReduction ruleThreat = new RuleScoutThreatReduction();
	boolean profilDamage = true;
	final static Logger log = LogManager.getLogger(RuleScout.class);

	@Override
	public boolean isElligible(Survivor survivor) {
		return survivor.getClassName().equals(SurvivorClass.ECLAIREUR);
	}

	@Override
	public RuleStatus statut() {
		return profilDamage ? ruleDamage.statut() : ruleThreat.statut();
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
		String reco = "Ce scout a un profil " + (profilDamage ? "dommages critiques" : "réduction de la menace") + " : ";
		reco += (profilDamage ? ruleDamage.recommandation() : ruleThreat.recommandation());
		return reco;
	}

	@Override
	public void processRule(Survivor survivor) {
		ruleDamage.processRule(survivor);
		ruleThreat.processRule(survivor);
		int ecartDamage = negativePoints(ruleDamage);
		int ecartThreat = negativePoints(ruleThreat);
		profilDamage = ecartDamage < ecartThreat;
		log.debug(String.format("    scout : damage = %s, threat=%s", ecartDamage, ecartThreat));
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
