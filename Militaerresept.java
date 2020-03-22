public class Militaerresept extends HvitResept {

  public Militaerresept(Legemiddel l, Lege u, Pasient pasient, int r) {
    super(l, u, pasient, r);
  }

  @Override
  public double prisAaBetale() {
    double opprinneligPris = super.prisAaBetale();
    double redusertPris = (opprinneligPris - (opprinneligPris * 1));
    return redusertPris;
  }
}
