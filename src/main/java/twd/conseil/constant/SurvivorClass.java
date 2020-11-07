package twd.conseil.constant;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum SurvivorClass {
    CHASSEUR ("chasseur", "hunter"),
    TIREUR ("tireur", "shooter"),
    SOLDAT ("soldat", "assault"),
    COGNEUR ("cogneur", "bruiser"),
    GUERRIER ("tirguerrier", "warrior"),
    ECLAIREUR ("eclaireur", "scout"),
    AUTRE ("autre", "");

    static Map<String, SurvivorClass> survivorClasses = new HashMap<>();
    static {
        for (SurvivorClass trait : EnumSet.allOf(SurvivorClass.class)) {
            SurvivorClass.survivorClasses.put (trait.fr, trait);
            SurvivorClass.survivorClasses.put (trait.en, trait);
        }
    }

    String fr;
    String en;
    SurvivorClass (String nameFr, String nameEn) {
        fr = nameFr;
        en = nameEn;
    }

    public static SurvivorClass of(String name) {
        SurvivorClass survivorClass = AUTRE;
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
        if (survivorClasses.containsKey(key)) {
            survivorClass = survivorClasses.get(key);
        }
        return survivorClass;
    }

    public String getName() {
        return fr;
    }
}
