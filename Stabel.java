public class Stabel<T> extends Lenkeliste<T> {

  /**
    legger til en node med tilhoerende objekt paa slutten av denne stabelen.
  */
  public void leggPaa(T x) {
    super.leggTil(x); //leggTil() er alt definert slik at den legger til bakerst
  }

  /**
    Fjerner noden paa slutten av denne stabelen.
    @return elementet som holdes av den siste noden i den sorterte lenkelisten
  */
  public T taAv() {
    T element = fjern(stoerrelse() - 1);
    return element;
  }
}
