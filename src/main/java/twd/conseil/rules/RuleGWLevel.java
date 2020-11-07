package twd.conseil.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import static twd.conseil.util.StringUtils.toStringSurvivants;

import twd.conseil.Survivor;
import twd.conseil.constant.RuleStatus;

public class RuleGWLevel implements TeamRule {

	TreeMap<Integer, List<Survivor>> mp = new TreeMap<>((k1, k2) -> k2 - k1);
	int nbSurvivors = 0;

	@Override
	public String getRuleTitle() {
		return "Niveau de difficulté en Guild War :";
	}


	@Override
	public void processRule(List<Survivor> survivors) {

		nbSurvivors = survivors.size();

		// On classe tous les survivants par level d'equipement (= le level max entre level arme et level combinaison)
		for (Survivor s : survivors) {
			Integer equipLevel = s.getDamageLevel() > s.getHealthLevel() ? s.getDamageLevel() : s.getHealthLevel();
			if (!mp.containsKey(equipLevel))	{
				mp.put(equipLevel, new ArrayList<Survivor>());
			}
			mp.get(equipLevel).add(s);
		}
	}

	@Override
	public RuleStatus statut() {
		RuleStatus statut = RuleStatus.ACHIEVED;
		Entry<Integer, List<Survivor>> maxLevel = mp.firstEntry();
		int nbSurv = maxLevel.getValue().size();
		if (nbSurv < 3) {
			statut = RuleStatus.NOT_REACHED;
		}
		else if (nbSurv < 6) {
			statut = RuleStatus.NOT_REACHED;
		}
		return statut;
	}

	@Override
	public String description() {
		String descr = "Vérifie que les survivants sont équilibrés pour la Guild War : ";
		descr += "\n  - Equilibré : au moins 6 survivants sont au même niveau max d'équipement";
		descr += "\n  - Améliorable : entre 3 et 6 survivants sont au même niveau max d'équipement";
		descr += "\n  - Déséquilibré : moins de 3 survivants sont au même niveau max d'équipement";
		return descr;
	}

	@Override
	public String recommandation() {
		String reco = "L'équipe est équilibrée pour la GW";
		Entry<Integer, List<Survivor>> maxLevel = mp.firstEntry();
		int nbSurv = maxLevel.getValue().size();
		if (nbSurv == 1) {
			reco = String.format("Seulement 1 survivant possède le level max d'équipement (level %s) : %s", maxLevel.getKey(), toStringSurvivants(maxLevel.getValue()));
		}
		else {
			reco = String.format("Seulement %s survivants possèdent le level max d'équipement (level %s) : %s", maxLevel.getKey(), toStringSurvivants(maxLevel.getValue()));
		}
		return reco;
	}	
    
}
