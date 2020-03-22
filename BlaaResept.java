public class BlaaResept extends Resept {

  private double pris;

  public BlaaResept(Legemiddel l, Lege u, Pasient pasient, int r) {
    super(l, u, pasient, r);
    pris = 10;
  }

  public String farge() {
    String str = "blaa";
    return str;
  }

  @Override
  public double prisAaBetale() { //bruker klassemetoden til superklassen for å hente legemiddelet, for saa aa bruke hentPris()-metoden til klassen Legemiddel for hente prisen.
    double legemiddelPris = hentLegemiddel().hentPris();
    final double RABATT = 0.75;
    double redusertPris = (legemiddelPris - (legemiddelPris * RABATT));
    return redusertPris;
  }
}
