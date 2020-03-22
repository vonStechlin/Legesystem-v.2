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

  public double prisAaBetale() {
    return pris;
  }
}
