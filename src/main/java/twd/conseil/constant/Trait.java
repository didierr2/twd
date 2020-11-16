package twd.conseil.constant;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Trait {
    PEAU_DURE ("peau dure", "iron skin"),
    ADRESSE ("adresse au tir", "marksman"),
    VENGEANCE ("vengeance", "revenge"),
    POSITION_DEFENSIVE ("position defensive", "defensive stance"),
    TIR_INFAILLIBLE ("tir infaillible", "sure shot"),
    RIPOSTE ("riposte", "retaliate"),
    ENTRE_BALLES("entre les balles", "bullet dodge"),
    ESQUIVE ("esquive", "dodge"),
    BRUTALITE ("brutalite", "strong"),
    ENCHAINEMENT ("enchainement", "follow through"),
    FRAPPE_PUISSANTE ("frappe puissante", "power strike"),
    CHANCEUX ("chanceux", "lucky"),
    VIGILANT ("vigilant", "vigilant"),
    PUNITION ("punition", "punish"),
    IMPITOYABLE ("impitoyable", "ruthless"),
    VISEE_CRITIQUE ("visee critique", "critical aim"),
    AUTRE ("autre", "other");

    static Map<String, Trait> traits = new HashMap<>();
    static {
        for (Trait trait : EnumSet.allOf(Trait.class)) {
            Trait.traits.put (trait.fr, trait);
            Trait.traits.put (trait.en, trait);
        }
    }

    String fr;
    String en;
    Trait (String nameFr, String nameEn) {
        fr = nameFr;
        en = nameEn;
    }

    public static Trait of(String name) {
        Trait trait = AUTRE;
        String key = name
            .toLowerCase()
            .replace("é", "e")
            .replace("è", "e")
            .replace("ê", "e")
            .replace("ë", "e")
            .replace("î", "i")
            .replace("ï", "i")
            .replace("à", "a")
            .replace("â", "a");
        if (traits.containsKey(key)) {
            trait = traits.get(key);
        }
        return trait;
    }

    public String getName() {
        return fr;
    }
}
