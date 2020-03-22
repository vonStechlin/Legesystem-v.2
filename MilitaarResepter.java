class MilitaarResepter extends HviteResepter{
  public MilitaarResepter(Legemiddel dopRef, Lege legeRef, Pasient pasientRef, int reit){
    super(dopRef, legeRef, pasientRef, reit);
  }
  //utfyller den abstrakte prismetoden
  public Double prisAaBetale(){
    return 0.0;
  }
}
