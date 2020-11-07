package twd.conseil.rules;

import java.util.List;

import twd.conseil.Survivor;

public interface TeamRule extends DiagnosticRule {
    
    void processRule(List<Survivor> survivors);

    String getRuleTitle();

    

}
