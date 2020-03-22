public class Pasient {

  private String navn;
  private String foedselsnr;
  private int unikID;
  private static int idTeller;
  Stabel<Resept> reseptliste = new Stabel<Resept>();

  public Pasient(String n, String fnr) {
    navn = n;
    foedselsnr = fnr;
    unikID = idTeller;
    idTeller++;
  }

  public String hentNavn() {
    return navn;
  }

  public int hentID() {
    return unikID;
  }

  /**
    Denne metoden er heller tvilsom. Finner vi en bedre loesning?
  */
  public void leggTilResept(Resept r) {
    reseptliste.leggPaa(r);
  }

  public Stabel<Resept> hentReseptListe() {
    return reseptliste;
  }

  public void skrivResepter() {
    for (Resept r : reseptliste) {
      System.out.println("Resept-ID: " + r.hentId() +
      ", legemiddel: " + r.hentLegemiddel().hentNavn() + ".");
    }
  }

  /*
    Itererer gjennom listen over pasientens resepter og finner om noen
    av dem gjelder narkotiske legemidler. Hvis tilfellet, returneres true.
    @return true hvis pasienten har resept paa narkotiske legemidler
  **/
  public boolean harNark() {
    for (Resept r : reseptliste) {
      if (r.hentLegemiddel() instanceof Narkotisk) {
        return true;
      }
    }
    return false;
  }

  /*
    Itererer gjennom listen over pasientens resepter og summerer det totale
    antallet resepter paa narkotiske legemidler.
    @return antallet resepter paa narkotiske legemidler
  **/
  public int antNarkResepter() {
    int antNarkResepter = 0;
    for (Resept r : reseptliste) {
      if (r.hentLegemiddel() instanceof Narkotisk) {
        antNarkResepter++;
      }
    }
    return antNarkResepter;
  }

  @Override
  public String toString() {
    return navn + " (fnr: " + foedselsnr + ")";
  }
}
