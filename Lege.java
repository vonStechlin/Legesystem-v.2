public class Lege implements Comparable<Lege> {

  private String navn;
  private Lenkeliste<Resept> utskrevedeResepter = new Lenkeliste<Resept>();

  public Lege(String n) {
    navn = n;
  }

  public String hentNavn() {
    return navn;
  }

  /*
    Itererer gjennom listen over legens utskrevne resepter og finner om noen
    av dem gjelder narkotiske legemidler. Hvis tilfellet, returneres true.
    @return true hvis legen har skrevet ut resept paa narkotiske legemidler
  **/
  public boolean harNark() {
    for (Resept r : utskrevedeResepter) {
      if (r.hentLegemiddel() instanceof Narkotisk) {
        return true;
      }
    }
    return false;
  }

  /*
    Itererer gjennom listen over legens utskrevne resepter og summer det totale
    antallet resepter skrevet ut for narkotiske legemidler.
    @return antallet resepter legen har skrevet ut resept paa narkotiske legemidler
  **/
  public int antNarkResepter() {
    int antNarkResepter = 0;
    for (Resept r : utskrevedeResepter) {
      if (r.hentLegemiddel() instanceof Narkotisk) {
        antNarkResepter++;
      }
    }
    return antNarkResepter;
  }

  /*
    Itererer gjennom listen over legens utskrevne resepter og summerer det totale
    antallet resepter skrevet ut for narkotiske legemidler.
    @return antallet resepter legen har skrevet ut paa narkotiske legemidler
  **/
  public int antVanedannendeResepter() throws Exception {
    int antVanedannendeResepter = 0;
    for (Resept r : utskrevedeResepter) {
      if (r.hentLegemiddel() instanceof Vanedannende) {
        antVanedannendeResepter++;
      }
    }
    return antVanedannendeResepter;
  }

  public HvitResept skrivHvitResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
    if (legemiddel instanceof Narkotisk && !(this instanceof Lege)) throw new UlovligUtskrift(this, legemiddel, pasient.hentID()); //dette virker helt gaerent
    HvitResept resept = new HvitResept(legemiddel, this, pasient, reit);
    pasient.leggTilResept(resept);
    utskrevedeResepter.leggTil(resept);
    return resept;
  }

  public Militaerresept skrivMilitaerResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
    if (legemiddel instanceof Narkotisk && !(this instanceof Lege)) throw new UlovligUtskrift(this, legemiddel, pasient.hentID()); //dette virker helt gaerent
    Militaerresept resept = new Militaerresept(legemiddel, this, pasient, reit);
    pasient.leggTilResept(resept);
    utskrevedeResepter.leggTil(resept);
    return resept;
  }

  public PResept skrivPResept(Legemiddel legemiddel, Pasient pasient) throws UlovligUtskrift {
    if (legemiddel instanceof Narkotisk && !(this instanceof Lege)) throw new UlovligUtskrift(this, legemiddel, pasient.hentID()); //dette virker helt gaerent
    PResept resept = new PResept(legemiddel, this, pasient);
    pasient.leggTilResept(resept);
    utskrevedeResepter.leggTil(resept);
    return resept;
  }

  public BlaaResept skrivBlaaResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
    if (legemiddel instanceof Narkotisk && !(this instanceof Lege)) throw new UlovligUtskrift(this, legemiddel, pasient.hentID()); //dette virker helt gaerent
    BlaaResept resept = new BlaaResept(legemiddel, this, pasient, reit);
    pasient.leggTilResept(resept);
    utskrevedeResepter.leggTil(resept);
    return resept;
  }

  @Override
  public String toString() {
    String str = "Klasse: " + getClass().getName() + "\nNavn: " + navn;
    return str;
  }

  public int compareTo(Lege annen) {
    if (navn.compareTo(annen.hentNavn()) > 0) return 1;
    if (navn.compareTo(annen.hentNavn()) < 0) return -1;
    return 0;
  }
}
