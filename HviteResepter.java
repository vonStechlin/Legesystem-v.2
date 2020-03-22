abstract class HviteResepter extends Resept{
  public HviteResepter(Legemiddel dopRef, Lege legeRef, Pasient pasientRef, int reit){
    super(dopRef, legeRef, pasientRef, reit);
  }
  String farge = "hvit";
  // utfyller fargemetoden
  public String farge(){
    return farge;
  }
}
