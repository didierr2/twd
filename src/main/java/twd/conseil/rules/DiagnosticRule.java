package twd.conseil.rules;

import twd.conseil.constant.RuleStatus;

public interface DiagnosticRule {
    
    RuleStatus statut();

    default String description() { return "";};

    default String recommandation() { return "";};

    

}
