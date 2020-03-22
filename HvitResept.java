public class HvitResept extends Resept {

  public HvitResept(Legemiddel l, Lege u, Pasient pasient, int r) {
    super(l, u, pasient, r);
  }

  public String farge() {
    String str = "Hvit";
    return str;
  }

  @Override
  public double prisAaBetale() { //bruker klassemetoden til superklassen for å hente legemiddelet, for saa aa bruke hentPris()-metoden til klassen Legemiddel for hente prisen.
    return hentLegemiddel().hentPris();
  }
}
