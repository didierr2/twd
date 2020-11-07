package twd.conseil.rules;

import java.util.Arrays;
import java.util.List;

import twd.conseil.constant.RuleStatus;
import twd.conseil.Survivor;

public class Rules {
    
			
	// Liste de toutes les regles a appliquer pour l'equipe
	public static final List<TeamRule> teamRules = Arrays.asList(
		new RuleGWLevel()
	);

	// Liste de toutes les regles a appliquer pour chaque survivant
	public static final List<SurvivorRule> survivorRules = Arrays.asList(
		new RuleChasseur()
	);

    public static StringBuilder processRules(List<Survivor> survivors) {
        StringBuilder sb = new StringBuilder();

        // Process team rules
        for (TeamRule rule : teamRules) {
            rule.processRule(survivors);
            sb.append(rule.getRuleTitle());
            logRuleDiagnostic(sb, rule);
            sb.append("\n");
        }
        sb.append("\n");
        
        // Process survivalits rules
        for (Survivor survivor : survivors) {
            boolean survivorOk = true;
            sb.append(survivor.getName() + " : ");
            for (SurvivorRule rule : survivorRules) {
                if (rule.isElligible(survivor)) {
                    rule.processRule(survivor);
                    logRuleDiagnostic(sb, rule);
                    survivorOk = survivorOk && rule.statut().equals(RuleStatus.ACHIEVED);
                }
            }    
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
