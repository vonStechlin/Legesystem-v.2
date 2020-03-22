class Spesialist extends Lege implements Godkjenningsfritak {

  private int kontrollID;

  public Spesialist(String n, int id) {
    super(n);
    kontrollID = id;
  }

  public int hentKontrollID() {
    return kontrollID;
  }

  @Override
  public String toString() {
    String str = super.toString() + "\nkontroll-ID: " + kontrollID;
    return str;
  }
}
