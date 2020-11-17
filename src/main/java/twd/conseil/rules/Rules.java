package twd.conseil.rules;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import twd.conseil.constant.RuleStatus;
import twd.conseil.Survivor;

public class Rules {
    
    // Logger
    final static Logger log = LogManager.getLogger(Rules.class);
    
	// Liste de toutes les regles a appliquer pour l'equipe
	public static final List<TeamRule> teamRules = Arrays.asList(
		new RuleGWLevel(),
		new RuleDefiLevel()
	);

	// Liste de toutes les regles a appliquer pour chaque survivant
	public static final List<SurvivorRule> survivorRules = Arrays.asList(
        new RuleHunter(),
        new RuleBruiser(),
        new RuleAssault(),
        new RuleWarrior(),
        new RuleScout(),
        new RuleShooter()
	);

    public static StringBuilder processRules(List<Survivor> survivors) {
        StringBuilder sb = new StringBuilder();

        // Process team rules
        for (TeamRule rule : teamRules) {
            log.debug("- [TEAM " + rule.getClass().getSimpleName() + "] process");
            rule.processRule(survivors);
            sb.append(rule.getRuleTitle());
            log.debug("- [TEAM " + rule.getClass().getSimpleName() + "] recommandation - " + rule.statut().name());
            logRuleDiagnostic(sb, rule);
            sb.append("\n");
        }
        sb.append("\n");
        
        // Process survivalist rules
        for (Survivor survivor : survivors) {
            boolean survivorOk = true;
            sb.append(survivor.getName() + " : ");
            log.debug("- [" +  survivor.getName().toUpperCase() + "]");
            for (SurvivorRule rule : survivorRules) {
                if (rule.isElligible(survivor)) {
                    log.debug("  * [SURVIVOR " + rule.getClass().getSimpleName() + "] process");
                    rule.processRule(survivor);
                    log.debug("  * [SURVIVOR " + rule.getClass().getSimpleName() + "] recommandation - " + rule.statut().name());
                    logRuleDiagnostic(sb, rule);
                    survivorOk = survivorOk && rule.statut().equals(RuleStatus.ACHIEVED);
                }
            }    
            log.debug("- [" +  survivor.getName().toUpperCase() + "] status : " + (survivorOk ? "OK" : "KO"));
            if (survivorOk) {
                sb.append("OK");
            }
            sb.append("\n");
        }
        return sb;
    }

    static void logRuleDiagnostic(StringBuilder sb, DiagnosticRule rule) {
     switch (rule.statut()) {
        case TO_IMPROVE : 
            sb.append("\n  - Amélioration : " + rule.recommandation());
        break;
        case NOT_REACHED:
            sb.append("\n  - Problème : " + rule.recommandation());
        break;
        default : 
        break;
     }
    }
}
