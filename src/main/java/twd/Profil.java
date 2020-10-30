package twd;

public enum Profil {

    ATTAQUANT (0, 0, 100, 100, 20), 
    DEFENSEUR (100, 100, 0, 0, 0), 
    EQUILIBRE_ATTAQUE (30, 30, 100, 100, 10),
    EQUILIBRE (50, 50, 50, 50, 20);

    double pdegats = 1;
    boolean bdegats = true;
    double psante = 1;
    boolean bsante = true;
    double pdc = 1;
    boolean bdc = true;
    double pcc = 1;
    boolean bcc = true;
    double prd = 1;
    boolean brd = true;

    Profil(int sante, int rd, int degats, int dc, int cc) {
        psante = sante / 100d;
        bsante = sante > 0;
        prd = rd / 100d;
        brd = rd > 0;
        pdegats = degats / 100d;
        bdegats = degats > 0;
        pdc = dc / 100d;
        bdc = dc > 0;
        pcc = cc / 100d;
        bcc = cc > 0;
    }

    public boolean accept(Insigne insigne) {
        boolean accept = true;
        switch(insigne.effet) {
            case "degats" :
                accept = bdegats;
                break;
            case "sante" : 
                accept = bsante;
                break;
            case "degats critiques" :
                accept = bdc;
                break;
			case "coups critiques" :
                accept = bcc;
                break;
            case "reduction degats" :
                accept = brd;
                break;
            default:
                accept = false;
        }
        return accept;
    }


}
