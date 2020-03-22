class Narkotisk extends Legemiddel {

  private int styrke;

  public Narkotisk(String n, double p, double v, int s) {
    super(n, p, v);
    styrke = s;
  }

  public int hentNarkotiskStyrke() {
    return styrke;
  }
}
