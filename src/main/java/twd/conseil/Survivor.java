package twd.conseil;

import java.util.List;

import lombok.Data;
import twd.conseil.constant.SurvivorClass;
import twd.conseil.constant.Trait;

@Data
public class Survivor {
    private final String name;
    private final SurvivorClass className;
    private final int level;
    private final int extraStars;
    private final int damageLevel;
    private final int healthLevel;
    private final List<Trait> traits;

}
