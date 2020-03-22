import java.util.*;

/**
  Denne klassen implementerer en enveis lenket liste.
*/
public class Lenkeliste<T> implements Liste<T>, Iterable<T> {

  protected Node start;
  protected int str;

  class Node {

    protected T data;
    protected Node neste;
  }

  public Iterator<T> iterator() {
    return new LenkelisteIterator();
  }

  class LenkelisteIterator implements Iterator<T> {
    private Node posisjon;
    private Node forrige;
    private boolean etterNext;


    public LenkelisteIterator() {
      posisjon = null;
      forrige = null;
      etterNext = false;
    }

    @Override
    public T next() {
      if (!hasNext()) { throw new NoSuchElementException(); }
      forrige = posisjon; //remember for remove??
      etterNext = true;

      // System.out.println("\nDEBUG hva er posisjon? " + posisjon);
      if (posisjon == null) {
        posisjon = start;
      }
      else {
        posisjon = posisjon.neste;
      }

      // System.out.println("DEBUG posisjon.data =  " + posisjon.data);
      return posisjon.data;
    }

    /**
      Tester hvorvidt det er et element etter posisjonen til iteratoren.
      @return true hvis det er et element etter posisjonen til iteratoren
    */
    @Override
    public boolean hasNext() {
      // hvis iteratoren ennaa ikke har begynt aa iterere: er det et foerste element?
      if (posisjon == null) { return start != null; }
      // finnes det et element i neste posisjon?
      else { return posisjon.neste != null; }
    }
  }


  /**
    legger til et element paa slutten av denne lenkelisten.
    @param x element av en vilkaarlig klasse
  */
  public void leggTil(T x) {
    Node nyNode = new Node();
    nyNode.data = x;

    if (hentNode((str - 1)) != null) {
      hentNode((str - 1)).neste = nyNode;
    } else { // hvis indeks (str - 1) er null, er listen tom
      start = nyNode;
    }

    str++;
  }

  /**
    Returnerer antall noder i lenkelisten.
    @return stoerrelse
  */
  public int stoerrelse() {
    return str;
  }

  /**
    Fjerner det foerste elementet i lenkelisten.
    @return elementet som holdes av den foerste noden i lenkelisten
  */
  public T fjern() throws UgyldigListeIndeks {
    if (start == null) { throw new UgyldigListeIndeks(0); }
    T element = start.data;
    start = start.neste;
    str--;
    return element;
  }

  /**
    Hjelpemetoden som returnerer node paa gitt indeks i lenkelisten.
    @param pos lenkeliste-indeks
    @return node paa en gitt indeks i lenkelisten
  */
  protected Node hentNode(int pos) throws UgyldigListeIndeks {
    if (pos >= stoerrelse() || pos < 0) { return null; }

    Node midl = start;

    for (int i = 0; i < pos; i++) {
      midl = midl.neste; //itererer fram til node hvis indeks = pos
    }

    return midl;

  }

  /**
    Henter ut data i noden paa en gitt indeks i lenkelisten
    @param pos lenkeliste-indeks
    @return data som holdes av en node i en gitt indeks i lenkelisten
  */
  public T hent(int pos) throws UgyldigListeIndeks {

    if (pos >= stoerrelse() || pos < 0) { throw new UgyldigListeIndeks(pos); }

    Node n = hentNode(pos);
    T element = n.data;
    return element;
  }

  /**
    Legger til en node med tilhoerende data paa en gitt indeks i lenkelisten.
    @param pos lenkeliste-indeks
    @param x objekt av vilkaarlig klasse
    @return data som holdes av en node i en gitt indeks i lenkelisten
  */
  public void leggTil(int pos, T x) throws UgyldigListeIndeks {

    if (pos > stoerrelse() || pos < 0) { throw new UgyldigListeIndeks(pos); }

    Node nyNode = new Node();
    nyNode.data = x;

    if (pos == 0) {
      nyNode.neste = start;
      start = nyNode;
    } else {
      Node nesteNode = hentNode(pos);
      nyNode.neste = nesteNode;
      Node forrigeNode = hentNode((pos - 1));
      forrigeNode.neste = nyNode;
    }

    str++;
  }

  /**
    Fjerner en node med paa en gitt indeks i lenkelisten.
    @param pos lenkeliste-indeks
    @return data som holdes av en node i en gitt indeks i lenkelisten
  */
  public T fjern(int pos) throws UgyldigListeIndeks {

    if (pos >= stoerrelse() || pos < 0) { throw new UgyldigListeIndeks(pos); }

    if (pos == 0) {
      T element = fjern(); //kaller paa fjern som alltid fjerner foerste node og returnerer nodens data
      return element;
    } else {
      Node nodeSomFjernes = hentNode(pos);
      T element = nodeSomFjernes.data;
      Node forrigeNode = hentNode((pos - 1));
      forrigeNode.neste = nodeSomFjernes.neste;
      str--;
      return element;
    }
  }

  /**
    Endrer data i en node paa en gitt indeks i lenkelisten.
    @param pos lenkeliste-indeks
    @param x objekt av vilkaarlig klasse
  */
  public void sett(int pos, T x) throws UgyldigListeIndeks {

    if (pos >= stoerrelse() || pos < 0) { throw new UgyldigListeIndeks(pos); }

    Node n = hentNode(pos);
    n.data = x;
  }
}
