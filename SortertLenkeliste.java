public class SortertLenkeliste<T extends Comparable<T>> extends Lenkeliste<T> {

  @Override
  public void sett(int pos, T x) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void leggTil(int pos, T x) {
    throw new UnsupportedOperationException();
  }

  /**
    Fjerner noden paa slutten av den sorterte lenkelisten, dvs det stoerste elementet.
    @return x element av en vilkaarlig klasse
  */
  @Override
  public T fjern() throws UgyldigListeIndeks {

    if (str == 0) { throw new UgyldigListeIndeks(0); }
    Node midl = start;
    for (int i = 0; i < (str - 1); i++) {
      midl = midl.neste; //itererer fram til siste element
    }
    T element = midl.data;
    str--;
    Node midl2 = start;
    for (int i = 0; i < (str - 1); i++) {
      midl2 = midl2.neste; //itererer fram til nest siste element
    }
    midl2.neste = null; //fjerner eneste peker til det som var siste element
    return element;
  }

  /**
    legger til et objekt paa indeksen foer foerste node hvis objekts verdi er
    stoerre enn verdien til parameterobjektet. Hvis verdiene er like, legges
    parameterobjektet inn bak objektet som er der fra foer.
    @param x element av en vilkaarlig klasse
  */
  @Override
  public void leggTil(T x) {
    Node nyNode = new Node();
    nyNode.data = x;

    int teller = 0;
    boolean funnet = false;
    Node nesteNode = null;
    Node forrigeNode = null;
    Node midl = start;

    while (teller < str && !funnet) {
      // System.out.println(x);
      if (hent(teller).compareTo(x) > 0) { //hvis listeverdien er stoerre enn parameterverdien
        try {
          nesteNode = hentNode(teller);
          forrigeNode = hentNode((teller - 1));
          nyNode.neste = nesteNode;
          funnet = true;
        } catch (UgyldigListeIndeks u) {
          forrigeNode = null;
        }
      }
      else {
        teller++;
        if (teller == str) { //hvis iterert helt til slutten av lenkelisten
          forrigeNode = hentNode((teller - 1));
          forrigeNode.neste = nyNode;
        }
        midl = midl.neste;
      }
    }

    if (forrigeNode != null) { // saafremt man kun itererte fram til foerste node
      forrigeNode.neste = nyNode;
    }

    if (teller == 0) { // hvis x skal inn paa indeks 0
      start = nyNode;
    }
    str++;
  }
}
