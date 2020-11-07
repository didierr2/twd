package twd.conseil.rules;

import twd.conseil.Survivor;

public interface SurvivorRule extends DiagnosticRule {
    
    void processRule(Survivor survivor);

    default boolean isElligible(Survivor survivor) { return true; }
}
