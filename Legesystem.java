import java.io.*;
import java.util.*;
import java.lang.*;

// lager lenkelister til legesystemet
public class Legesystem {
  static Lenkeliste<Legemiddel> legemiddelliste = new Lenkeliste<Legemiddel>();
  static SortertLenkeliste<Resept> reseptliste = new SortertLenkeliste<Resept>();
  static SortertLenkeliste<Lege> legeliste = new SortertLenkeliste<Lege>();
  static Lenkeliste<Pasient> pasientliste = new Lenkeliste<Pasient>();

  // main metoden
  public static void main(String[] args) throws Exception {
    try {
      File fil = new File("myeInndata.txt");
      lesPasienter(fil);
      lesLegemidler(fil);
      lesLeger(fil);
      lesResepter(fil);
    }
    catch (FileNotFoundException exception) {System.out.println(exception);}

    // starter opp menyen for legesystemet
    Scanner in = new Scanner(System.in);
    System.out.println("Starter legesystem.");
    System.out.print(".");
    Thread.sleep(500);
    System.out.print(".");
    Thread.sleep(500);
    System.out.println(".");
    Thread.sleep(500);
    System.out.println();
    boolean avslutte = false;

    // selve menyen tilbyr 8 ulike kommandoer og mulighet for aa avslutte
    while (!avslutte) {
      System.out.println("HOVEDMENY");
      System.out.println();
      System.out.println("0: Skriv ut oversikt");
      System.out.println("1: Opprett og legg til lege");
      System.out.println("2: Opprett og legg til pasient");
      System.out.println("3: Opprett og legg til resept");
      System.out.println("4: Opprett og legg til legemiddel");
      System.out.println("5: Bruk resept");
      System.out.println("6: Skriv ut statistikk");
      System.out.println("7: Skriv data til fil");
      System.out.println("8: Avslutt program");

      // hvert svaralternativ kaller paa metoder for tjenesten
      String svar = in.next().trim();
      if (svar.equals("0")) skrivUtOversikt();
      else if (svar.equals("1")) try {
        leggTilLege();
      }
      catch (NumberFormatException e) {
        System.out.println("\nUgyldig input!\n");
        Thread.sleep(1000);
      }
      else if (svar.equals("2")) {
        try {
        leggTilPasient();
        }
        catch (IllegalArgumentException e) {
          System.out.println("\nUgyldig input!\n");
          Thread.sleep(1500);
        }
      }
      else if (svar.equals("3")) {
        try {
          leggTilResept();
        }
        catch (UlovligUtskrift e) {
          System.out.println("\n" + e + "\n");
          Thread.sleep(1000);
        }
        catch (NumberFormatException e) {
          System.out.println("\nUgyldig input!\n");
          Thread.sleep(1000);
        }
      }
      else if (svar.equals("4")) {
        try {
          leggTilLegemiddel();
        }
        catch (NumberFormatException e) {
          System.out.println("\nUgyldig verdi.\n");
          Thread.sleep(1000);
        }
      }
      else if (svar.equals("5")) {
        try {
          brukResept();
        }
        catch (UgyldigListeIndeks e) {
          System.out.println(e);
        }
      }
      else if (svar.equals("6")) skrivUtStatistikk();
      else if (svar.equals("7")) skrivTilFil();
      else if (svar.equals("8")) avslutte = true;
      else {
        System.out.println();
        System.out.println("Ugyldig input! Proev igjen.");
        Thread.sleep(1000);
      }
    }
    System.out.println("Programmet avsluttes.");
    Thread.sleep(2000);
  }


  // lager metoder for aa skrive ut all info
  private static void skrivUtLeger() {
    int teller = 0;
    for (Lege l : legeliste) {
      System.out.println(teller + ": " + l.hentNavn());
      teller++;
    }
  }

  private static void skrivUtPasienter() {
    int teller = 0;
    for (Pasient p : pasientliste) {
      System.out.println(teller + ": " + p);
      teller++;
    }
  }

  private static void skrivUtLegemidler() {
    int teller = 0;
    for (Legemiddel l : legemiddelliste) {
      System.out.println(teller + ": " + l.hentNavn());
      teller++;
    }
  }

  private static void skrivUtResepter() {
    int teller = 0;
    for (Resept r : reseptliste) {
      System.out.println(teller + ": " + r);
      teller++;
    }
  }


  // metode for aa skrive ut alle elementene
  static void skrivUtOversikt() {
    Scanner in = new Scanner(System.in);
    boolean avslutte = false;


    while (!avslutte) {
      System.out.println("\n## OVERSIKT ##\n");
      System.out.println("0: Skriv ut pasienter.");
      System.out.println("1: Skriv ut leger.");
      System.out.println("2: Skriv ut legemidler.");
      System.out.println("3: Skriv ut resepter.");
      System.out.println("4: Tilbake til hovedmeny.");

      String svar = in.next();
      if (svar.equals("0")) {
        System.out.println("### PASIENTER ###");
        skrivUtPasienter();
      }
      else if (svar.equals("1")) {
        System.out.println("### LEGER ###");
        skrivUtLeger();
      }
      else if (svar.equals("2")) {
        System.out.println("### LEGEMIDLER ###");
        skrivUtLegemidler();
      }
      else if (svar.equals("3")) {
        System.out.println("### RESEPTER ###");
        skrivUtResepter();
      }
      else if (svar.equals("4")) avslutte = true;
      else {
        System.out.println("Ugyldig input! Proev igjen.");
      }
    }
  }


  // metode som registrerer en ny lege til systemet
  static void leggTilLege() throws NumberFormatException {
    Scanner in = new Scanner(System.in);
    String navn;

    System.out.println("\nDu har valgt aa legge til en lege. Vennligst oppgi legens navn.");
    navn = in.nextLine();

    System.out.println("\nEr " + navn + " en spesialist?");
    System.out.println("0: Ja.");
    System.out.println("1: Nei.");
    String svar = in.next();
    if (svar.equals("0")) {
      System.out.println("Oppgi kontroll-IDen til " + navn + ":");
      int kontrollid = Integer.parseInt(in.next());
      Lege nyLege = new Spesialist(navn, kontrollid);
      legeliste.leggTil(nyLege);
      System.out.println("Spesialisten " + navn + " er lagt inn i systemet!\n");
    }
    else if (svar.equals("1")) {
      Lege nyLege = new Lege(navn);
      legeliste.leggTil(nyLege);
      System.out.println("Legen " + navn + " er lagt inn i systemet!\n");
    }
    else {
      throw new NumberFormatException(svar);
    }
  }

  static void leggTilResept() throws UlovligUtskrift, NumberFormatException {
    Scanner in = new Scanner(System.in);
    int svar;


    // bruker velger lege resepten registreres paa. Skriver ut nummerert liste med
    // legene i systemet og ber bruker velge nr, dette svarer til legens plass i listen
    System.out.println("Hvilken lege skal skrive ut resept?");
    skrivUtLeger();
    svar = Integer.parseInt(in.next());
    Lege utskrivendeLege = legeliste.hent(svar);

    // bruker velger hvilket legemiddel resepten er for
    System.out.println("For hvilket legemiddel skal resepten gjelde?");
    skrivUtLegemidler();
    svar = Integer.parseInt(in.next());
    Legemiddel legemiddel = legemiddelliste.hent(svar);

    // bruker velger hvem resepten skal gjelde for
    System.out.println("For hvilken pasient skal resepten gjelde?");
    skrivUtPasienter();
    svar = Integer.parseInt(in.next());
    Pasient pasient = pasientliste.hent(svar);

    // bare spesialister skal kunne skrive resepter paa narkotiske legemidler
    if (legemiddel instanceof Narkotisk) {
      if (!(utskrivendeLege instanceof Spesialist)) {
        throw new UlovligUtskrift(utskrivendeLege, legemiddel, pasient.hentID());
      }
    }

    System.out.println("Tast 'b' for blaa resept. Tast 'h' for hvit resept.");
    String input = in.next().trim().toLowerCase();

    if (input.equals("b")) {
      System.out.println("Hvor mange reit skal resepten ha?");
      int reit = Integer.parseInt(in.next());
      Resept resept = utskrivendeLege.skrivBlaaResept(legemiddel, pasient, reit);
      reseptliste.leggTil(resept);
      System.out.println("\nResepten ble lagt til i listen!");
    }
    else if (input.equals("h")) {
      System.out.println("Tast 'v' for vanlig resept; tast 'm' for militaerresept" +
      " tast 'p' for p-resept.");
      input = in.next().trim();

      if (input.equals("p")) {
        Resept resept = utskrivendeLege.skrivPResept(legemiddel, pasient);
        reseptliste.leggTil(resept);
        System.out.println("\nResepten ble lagt til i listen!");
      }
      else {
        System.out.println("Hvor mange reit skal resepten ha?");
        int reit = Integer.parseInt(in.next());

        if (input.equals("v")) {
          Resept resept = utskrivendeLege.skrivHvitResept(legemiddel, pasient, reit);
          reseptliste.leggTil(resept);
          System.out.println("\nResepten ble lagt til i listen!");
        }

        if (input.equals("m")) {
          Resept resept = utskrivendeLege.skrivMilitaerResept(legemiddel, pasient, reit);
          reseptliste.leggTil(resept);
          System.out.println("\nResepten ble lagt til i listen!");
        }
      }
    }
    else {
      System.out.println("Ugyldig input!");
    }
  }

  // legger til pasient
  static void leggTilPasient() throws Exception {
    Scanner in = new Scanner(System.in);
    System.out.println("Du har valg aa legge til ny pasient. Oppgi pasientens navn.");
    String navn = in.next();
    System.out.println("Oppgi pasientens foedselsnr.");
    String fnr = in.next();

    char[] fnrArray = fnr.toCharArray();
    for (char ch : fnrArray) {
      if (!Character.isDigit(ch)) {
        throw new IllegalArgumentException();
      }
    }

    Pasient nyPasient = new Pasient(navn, fnr);
    pasientliste.leggTil(nyPasient);
    System.out.println("Pasienten " + nyPasient + " er lagt inn i systemet!");
  }

  // legger til legemiddel
  static void leggTilLegemiddel() throws NumberFormatException {
    Scanner in = new Scanner(System.in);

    System.out.println("Du har valgt aa legge til et nytt legemiddel. Oppgi legemidlets navn.");
    String navn = in.next();

    System.out.println("Oppgi mengden virkestoff i ml.");
    double virkestoff = Double.parseDouble(in.next());

    System.out.println("Oppgi prisen.");
    double pris = Double.parseDouble(in.next());

    System.out.println("Tast 0 for vanlig legemiddel. \nTast 1 for vanedannende legemiddel. \nTast 2 for narkotisk legemiddel.");
    String svar = in.next();

    if (svar.equals("0")) {
      Legemiddel vanlig = new Vanlig(navn, pris, virkestoff);
      legemiddelliste.leggTil(vanlig);
    }

    else if (svar.equals("1")) {
      System.out.println("Oppgi vanedannnde styrke");
      int styrkeV = Integer.parseInt(in.next());
      Legemiddel vanedannende = new Vanedannende(navn, pris, virkestoff, styrkeV);
      legemiddelliste.leggTil(vanedannende);
    }

    else if (svar.equals("2")) {
      System.out.println("Oppgi narkotisk styrke");
      int styrkeN = Integer.parseInt(in.next());
      Legemiddel narkotisk = new Narkotisk(navn, pris, virkestoff, styrkeN);
      legemiddelliste.leggTil(narkotisk);
    }

    System.out.println("\nLegemiddelet ble lagt til i listen!\n");
  }

  static void brukResept() throws Exception {
    if (reseptliste.stoerrelse() <= 0) {
      System.out.println("\nDet finnes ingen resepter i systemet.\n");
      Thread.sleep(1000);
    }
    else {
      Scanner in = new Scanner(System.in);

      System.out.println("Hvilken pasient vil du se resepter for?");
      skrivUtPasienter();
      int pasnr = Integer.parseInt(in.next());
      Pasient pasient = pasientliste.hent(pasnr);

      System.out.println("\nValgt pasient: " + pasient + ".");
      if (pasient.hentReseptListe().stoerrelse() == 0) {
        System.out.println("Pasienten " + pasient  + " har ingen gyldige resepter.");
        Thread.sleep(1000);
      }
      else {
        System.out.println("Angi ID-nr. paa resepten du vil bruke.");
        pasient.skrivResepter();
        int reseptID = Integer.parseInt(in.next());

        // for-loekken soerger for at brukeren bare kan velge resepter i
        // Pasient-objektets egen reseptliste, og ikke i legesystemets reseptliste
        boolean reseptFunnet = false;
        for (Resept r : pasient.hentReseptListe()) {
          if (r.hentId() == reseptID) {
            reseptFunnet = true;
            Resept resept = reseptliste.hent(reseptID);
            boolean brukt = resept.bruk(); //metoden returnerer false hvis ingen reit igjen
            if (!brukt) {
              System.out.println("Kunne ikke bruke resept pa " + resept.hentLegemiddel().hentNavn() +
              " (ingen gjenvaerende reit).");
            } else {
              System.out.println("Brukte resept paa " + resept.hentLegemiddel().hentNavn() +
              ". Antall gjenvaerende reit: " + resept.hentReit() + ".");
            }
          }
        }
        if (!reseptFunnet) {
          System.out.println("\nResepten finnes ikke i pasientens reseptliste.\n");
          Thread.sleep(1000);
        }
      }
    }
  }

  // metode for aa skrive ut statistikk
  static void skrivUtStatistikk() throws Exception {
    Scanner in = new Scanner(System.in);
    boolean avslutte = false;

    while (!avslutte) {
      System.out.println("\n ## STATISTIKK-MENY ## \n");
      System.out.println("0: Antall utskrevne resepter paa vanedannende legemidler.");
      System.out.println("1: Antall utskrevne resepter paa narkotiske legemidler.");
      System.out.println("2: Liste over leger som har skrevet ut resepter paa narktosike legemidler.");
      System.out.println("3: Liste over pasienter med gyldige resepter paa narkoktiske legemidler.");
      System.out.println("4: Gaa tilbake til hovedmenyen.");

      String svar = in.next();

      // skriver ut antall vanedannende resepter, finner dette ved aa gaa gjennom legelisten og kalle en hent-metode i legeklassen
      if (svar.equals("0")) {
        int sumVanedannende = 0;

        for (Lege l : legeliste) {
          sumVanedannende += l.antVanedannendeResepter();
        }

        System.out.println("Det er blitt skrevet ut " + sumVanedannende + " resept(er) paa vanedannnde legemidler.");
      }

      // samme for narkotiske
      else if (svar.equals("1")) {
        int sumNarkotisk = 0;

        for (Lege l : legeliste) {
          sumNarkotisk += l.antNarkResepter();

        }

        System.out.println("Det er blitt skrevet ut " + sumNarkotisk + " resept(er) paa narkotiske legemidler.");
      }

      // kaller en metode i legeklassen som returnerer true dersom legeobjektet har skrevet ut narkotisk-resept, printer deretter antall.
      else if (svar.equals("2")) {
        if (legeliste.stoerrelse() == 0) {
          System.out.println("Finner ingen leger med utskrevne resepter paa narkotiske legemidler.");
        }
        for (Lege l : legeliste) {
          if (l.harNark()) {
            System.out.println(l.hentNavn() + " har skrevet ut " + l.antNarkResepter() + " resept(er) paa narkotiske legemidler");
          }
        }
      }

      // samme tankegang her, bare for pasienter
      else if (svar.equals("3")) {
        if (pasientliste.stoerrelse() == 0) {
          System.out.println("Finner ingen pasienter med gyldige resepter paa narkotiske legemidler.");
        }
        for (Pasient p : pasientliste) {
          if (p.harNark()) {
            System.out.println(p.hentNavn() + " har " + p.antNarkResepter() + " antall gyldige resept(er) paa narkotiske legemidler.");
          }
        }
      }

      else if (svar.equals("4")) {
        avslutte = true;
      }

      else {
        System.out.println("Ugyldig input! Proev igjen");
      }
    }
  }

  /**
    Denne metoden sjekker om en et Lege-objekt med et gitt navn finnes i listen
    over Lege-objekter, og returnerer i så fall dette.
    @param legenavn legens navn
    @return Lege-objekt med navn tilsvarende legenavn
  */
  private static Lege hentLegePaaNavn(String legenavn) {
    for (Lege l : legeliste) {
      if (l.hentNavn().equals(legenavn.trim())) {
        return l;
      }
    }

    return null;
  }

  static void lesPasienter(File filobjekt) throws FileNotFoundException {
    Scanner in = new Scanner(filobjekt);
    try {
      while (in.hasNextLine()) {
        String[] linje = in.nextLine().split(" ");
        if (linje[0].equals("#")) {
          if (linje[1].equals("Pasienter")) {
            String[] nesteLinje = in.nextLine().split(",");
            while (!(nesteLinje[0].charAt(0) == '#')) {
              Pasient pasient = new Pasient(nesteLinje[0], nesteLinje[1]);
              pasientliste.leggTil(pasient);
              nesteLinje = in.nextLine().split(",");
            }
          }
        }
      }
    }
    catch (IllegalArgumentException e) {
      System.out.println("Ugyldig dataformat!");
    }
  }

  static void lesLegemidler(File filobjekt) throws FileNotFoundException {
    Scanner in = new Scanner(filobjekt);
    while (in.hasNextLine()) {
      String[] linje = in.nextLine().split(" ");
      if (linje[0].equals("#")) {
        if (linje[1].equals("Legemidler")) {
          String[] nesteLinje = in.nextLine().split(",");
          while (!(nesteLinje[0].charAt(0) == '#')) {
            try {
              Legemiddel legemiddel;
              String navn = nesteLinje[0].trim();
              double pris = Double.parseDouble(nesteLinje[2].trim());
              double virkestoff = Double.parseDouble(nesteLinje[3].trim());

              if (nesteLinje[1].trim().equals("c") || nesteLinje[1].trim().equals("vanlig")) {
                legemiddel = new Vanlig(navn, pris, virkestoff);
                legemiddelliste.leggTil(legemiddel);
              } else {
                int styrke = Integer.parseInt(nesteLinje[4].trim());

                if (nesteLinje[1].trim().equals("b") || nesteLinje[1].trim().equals("vanedannende")) {
                  legemiddel = new Vanedannende(navn, pris, virkestoff, styrke);
                  legemiddelliste.leggTil(legemiddel);
                }

                else if (nesteLinje[1].trim().equals("a") || nesteLinje[1].trim().equals("narkotisk")) {
                  legemiddel = new Narkotisk(navn, pris, virkestoff, styrke);
                  legemiddelliste.leggTil(legemiddel);
                }

                else {System.out.println("Ugyldig dataformat! Kunne ikke lese linje " + nesteLinje); }
              }

            } catch (IllegalArgumentException e) {
              System.out.println("Ugyldig dataformat " + e.getMessage());
            }
            nesteLinje = in.nextLine().split(",");
          }
        }
      }
    }
  }

  static void lesLeger(File filobjekt) throws FileNotFoundException {
    Scanner in = new Scanner(filobjekt);
    while (in.hasNextLine()) {
      try {
        String[] linje = in.nextLine().split(" ");
        if (linje[1].equals("Leger")) {
          String[] nesteLinje = in.nextLine().split(",");
          while (!(nesteLinje[0].charAt(0) == '#')) {
              try {
                if (!(nesteLinje[1].trim().equals("0"))) {
                  String spesialistIDStr = nesteLinje[1].trim();
                  int spesialistID = Integer.parseInt(spesialistIDStr);
                  Lege spesialist = new Spesialist(nesteLinje[0], spesialistID);
                  legeliste.leggTil(spesialist);
                } else {
                  Lege lege = new Lege(nesteLinje[0]);
                  legeliste.leggTil(lege);
                }
                nesteLinje = in.nextLine().split(",");
              } catch (IndexOutOfBoundsException e) {
                System.out.println("Ugyldig dataformat! Lege");
            }
          }
        }
      } catch (IndexOutOfBoundsException ie) {}
    }
  }

  static void lesResepter(File filobjekt) throws FileNotFoundException {
    Scanner in = new Scanner(filobjekt);
    while (in.hasNextLine()) {
      try {
        String[] linje = in.nextLine().split(" ");
        if (linje[1].equals("Resepter")) {
          String[] nesteLinje = in.nextLine().split(",");
          while (in.hasNextLine()) {
            try {
              Legemiddel legemiddel = legemiddelliste.hent(Integer.parseInt(nesteLinje[0]));
              Lege lege = hentLegePaaNavn(nesteLinje[1]);
              Pasient pasient = pasientliste.hent(Integer.parseInt(nesteLinje[2]));

              if (nesteLinje[3].equals("p")) {
                Resept presept = lege.skrivPResept(legemiddel, pasient);
                reseptliste.leggTil(presept);
              }
              else {
                int reit = Integer.parseInt(nesteLinje[4]);

                if (nesteLinje[3].equals("blaa")) {
                  Resept blaaResept = lege.skrivBlaaResept(legemiddel, pasient, reit);
                  reseptliste.leggTil(blaaResept);
                }

                else if (nesteLinje[3].equals("hvit")) {
                  Resept hvitResept = lege.skrivHvitResept(legemiddel, pasient, reit);
                  reseptliste.leggTil(hvitResept);
                }

                else if (nesteLinje[3].equals("militaer")) {
                  Resept militaerresept = lege.skrivMilitaerResept(legemiddel, pasient, reit);
                  reseptliste.leggTil(militaerresept);
                }

                else {
                  System.out.println("Ugyldig verdi!");
                }
              }

            }
            catch (UgyldigListeIndeks e) {
              System.out.println(e.getMessage());
            }
            catch (UlovligUtskrift e) {
              System.out.println(e.getMessage());
            }

            nesteLinje = in.nextLine().split(",");
          }
        }
      } catch (IndexOutOfBoundsException e) {}
    }
  }


  // HARALDS komm: denne kompilerer, men oppretter bare en blank fil
  // metode for aa skrive til fil
  static void skrivTilFil() {
    Scanner in = new Scanner(System.in);

    // ber bruker angi filnavn
    System.out.println("Angi filnavn: ");
    String filNavn = in.next();
    File utfil = new File(filNavn);

    // dersom navnet ikke er gyldig kan man proeve igjen
    try {
      PrintWriter utskriftWriter = new PrintWriter(utfil);

      try {
        // lager en lang tekststreng med all info
        String utskrift = "# Pasienter (navn,fnr)\n";
        for (Pasient p : pasientliste) {
          utskrift = utskrift + p + "\n";
        }

        utskrift = utskrift + "# Legemidler (navn, type, pris, virkestoff [, styrke])\n";
        for (Legemiddel l : legemiddelliste) {
          utskrift = utskrift + l.hentNavn() + "," + l.hentPris() + "," + l.hentVirkestoff() + "\n";
        }

        utskrift = utskrift + "# Leger (navn, kontrollid / 0 hvis vanlig lege)\n";
        for (Lege l : legeliste) {
          utskrift = utskrift + l.hentNavn() + "," + "0\n";
        }

        utskrift = utskrift + "# Resepter (legemiddelNummer, legeNavn, pasientID, reit)\n";
        for (Resept r : reseptliste) {
          utskrift = utskrift + r.hentLegemiddel().hentId() + r.hentLege().hentNavn() + r.hentPasientId() + r.hentReit() + "\n";
        }
      } finally {
        utskriftWriter.close();
      }
    } catch (Exception e) {
      System.out.println("Feilmelding: prøv igjen med et annet navn.");
      skrivTilFil();
    }
  }
}
