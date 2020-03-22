abstract class Resept implements Comparable<Resept> {

  private Legemiddel legemiddel;
  private Lege utskrivendeLege;
  private int pasientId;
  private int reit;
  private int unikId;
  private static int idTeller;

  public Resept(Legemiddel l, Lege u, Pasient pasient, int r) {
    legemiddel = l;
    utskrivendeLege = u;
    pasientId = pasient.hentID();
    reit = r;
    unikId = idTeller;
    idTeller++;
  }

  public int hentId() {
    return unikId;
  }

  public Legemiddel hentLegemiddel() {
    return legemiddel;
  }

  public Lege hentLege() {
    return utskrivendeLege;
  }

  public int hentPasientId() {
    return pasientId;
  }

  public int hentReit() {
    return reit;
  }

  public boolean bruk() {
    boolean brukbar;
    if (reit > 0) {
      reit--;
      brukbar = true;
    }
    else {
      brukbar = false;
    }
    return brukbar;
  }

  abstract public String farge();

  abstract public double prisAaBetale();

  @Override
  public String toString() {
    String str = getClass().getName() + "(ID: " + unikId + ", farge: " + farge() + ", legemiddel: " +
    legemiddel.legemiddeltype() + ", utskrivende lege: " + utskrivendeLege.hentNavn() + ", pasient-ID: " + pasientId + ", antall reit: " + reit + ").";
    return str;
  }

  public int compareTo(Resept annen) {
    if ((unikId - annen.hentId()) > 0) return 1;
    if ((unikId - annen.hentId()) < 0) return -1;
    return 0;
  }
}
