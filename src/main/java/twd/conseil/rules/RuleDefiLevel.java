package twd.conseil.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import static twd.conseil.util.StringUtils.toStringSurvivants;

import twd.conseil.Survivor;
import twd.conseil.constant.RuleStatus;

public class RuleDefiLevel implements TeamRule {

	TreeMap<Integer, List<Survivor>> mp = new TreeMap<>((k1, k2) -> k2 - k1);
	int nbSurvivors = 0;

	@Override
	public String getRuleTitle() {
		return "Niveau de difficulté pour les Défis :";
	}


	@Override
	public void processRule(List<Survivor> survivors) {

		nbSurvivors = survivors.size();

		// On classe tous les survivants par level (= le level max + etoile rose)
		for (Survivor s : survivors) {
			Integer level = s.getLevel() + s.getExtraStars();
			if (!mp.containsKey(level))	{
				mp.put(level, new ArrayList<Survivor>());
			}
			mp.get(level).add(s);
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
			statut = RuleStatus.TO_IMPROVE;
		}
		return statut;
	}

	@Override
	public String description() {
		String descr = "Vérifie que les survivants sont équilibrés pour les défis : ";
		descr += "\n  - Equilibré : au moins 6 survivants sont au même niveau max";
		descr += "\n  - Améliorable : entre 3 et 6 survivants sont au même niveau max";
		descr += "\n  - Déséquilibré : moins de 3 survivants sont au même niveau max";
		return descr;
	}

	@Override
	public String recommandation() {
		String reco = "L'équipe est équilibrée pour les défis";
		Entry<Integer, List<Survivor>> level = mp.firstEntry();
		int nbSurv = level.getValue().size();
		if (nbSurv == 1) {
			reco = String.format("Seulement 1 survivant possède le level max (level %s) : %s", level.getKey(), toStringSurvivants(level.getValue()));
		}
		else {
			reco = String.format("Seulement %s survivants possèdent le level max (level %s) : %s", nbSurv, level.getKey(), toStringSurvivants(level.getValue()));
		}
		return reco;
	}	
    
}
