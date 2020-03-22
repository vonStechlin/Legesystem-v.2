
public class PResept extends HvitResept {

  public PResept(Legemiddel l, Lege u, Pasient pasient) {
    super(l, u, pasient, 3);
  }

  @Override
  public double prisAaBetale() {
    double redusertPris;

    final double PRISREDUKSJON = 108.0;
    double opprinneligPris = super.prisAaBetale();
    if (opprinneligPris - PRISREDUKSJON >= 0) {
      redusertPris = (opprinneligPris - PRISREDUKSJON);
    }
    else {
      redusertPris = 0; //brukeren kan aldri betale mindre enn 0 kr.
    }
    return redusertPris;
  }
}
