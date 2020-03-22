abstract class Legemiddel {

  private String navn;
  private double pris;
  private double virkestoff;
  private int unikId;
  private static int idTeller;

  public Legemiddel(String n, double p, double v) {
    navn = n;
    pris = p;
    virkestoff = v;
    unikId = idTeller;
    idTeller++;
  }

  public int hentId() {
    return unikId;
  }

  public double hentPris() {
    return pris;
  }

  public double hentVirkestoff() {
    return virkestoff;
  }

  public void settNyPris(double nyPris) {
    pris = nyPris;
  }

  public String hentNavn() {
    return navn;
  }

  public String legemiddeltype() { //en metode for aa gjoere toString()-metodene i de andre klassene mer lesbare
    return getClass().getName();
  }

  @Override
  public String toString() {
    String str = "Klasse: " + getClass().getName() + "\nID: " + unikId + "\nPris: " + pris + "\nVirkestoff i ml: " + virkestoff;
    return str;
  }
}
