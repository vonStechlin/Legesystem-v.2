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
      // oppretter objekt for tekstfilen og to skannerobjekter til aa parsere den
      File fil = new File("inndata.txt");
      Scanner innFil = new Scanner(fil);
      int linjeTeller=0;
      // while metode for aa parsere filen
      while (innFil.hasNextLine()) {
        String[] linje = innFil.nextLine().split(" ");
        // System.out.println(in.nextLine());

        // kodeblokk for aa behandle pasientdata
        // behandler tekstblokker hver for seg. de begynner og slutter med '#'.
        if (linje[0].equals("#")) {
          // sjekker hvilken type data tekstblokken har
          if (linje[1].equals("Pasienter")) {
            // hopper over foerste linje, splitter paa ','
            String[] nesteLinje = innFil.nextLine().split(",");
            // oppretter passende objekter og legger inn i liste
            while (!(nesteLinje[0].charAt(0) == '#')) {
              Pasient pasient = new Pasient(nesteLinje[0], nesteLinje[1]);
              pasientliste.leggTil(pasient);
              nesteLinje = innFil.nextLine().split(",");
              linjeTeller++;
              }
            }
          }
          // lignende prosedyre for leger
          if (linje[1].equals("Leger")) {
            String[] nesteLinje = innFil.nextLine().split(",");
            while (!(nesteLinje[0].charAt(0) == '#')) {
              // sjekker om legen er spesialist eller ei,
              if (!(nesteLinje[1].equals("0"))) {
                String spesialistIDStr = nesteLinje[1].trim();
                int spesialistID = Integer.parseInt(spesialistIDStr);
                Lege spesialist = new Spesialist(nesteLinje[0], spesialistID);
                legeliste.leggTil(spesialist);
              } else {
                Lege lege = new Lege(nesteLinje[0]);
                legeliste.leggTil(lege);
              }
              nesteLinje = innFil.nextLine().split(",");
              linjeTeller++;
            }
          }

          // legemidler
          if (linje[1].equals("Legemidler")) {
            String[] nesteLinje = innFil.nextLine().split(",");
            while (!(nesteLinje[0].charAt(0) == '#')) {
              // sjekker type. a for narkotisk, b for vanedannende, og c for vanlig.
              if (nesteLinje[1].trim().toLowerCase().equals("vanlig")) {
                String navn = nesteLinje[0].trim();
                String strengPris = nesteLinje[2].trim();
                Double pris = Double.parseDouble(strengPris);
                String strengVirkestoff = nesteLinje[3].trim();
                Double virkestoff = Double.parseDouble(strengVirkestoff);
                Legemiddel vanlig = new Vanlig(navn, pris, virkestoff);
                legemiddelliste.leggTil(vanlig);
              }
              else if (nesteLinje[1].trim().toLowerCase().equals("vanedannende")){
                String navn = nesteLinje[0].trim();
                String strengPris = nesteLinje[2].trim();
                Double pris = Double.parseDouble(strengPris);
                String strengVirkestoff = nesteLinje[3].trim();
                Double virkestoff = Double.parseDouble(strengVirkestoff);
                String strengStyrke = nesteLinje[4].trim();
                int styrke = Integer.parseInt(strengStyrke);
                Legemiddel vanedannende = new Vanedannende(navn, pris, virkestoff, styrke);
                legemiddelliste.leggTil(vanedannende);
              }
              else if (nesteLinje[1].trim().toLowerCase().equals("narkotisk")){
                String navn = nesteLinje[0].trim();
                String strengPris = nesteLinje[2].trim();
                Double pris = Double.parseDouble(strengPris);
                String strengVirkestoff = nesteLinje[3].trim();
                Double virkestoff = Double.parseDouble(strengVirkestoff);
                String strengStyrke = nesteLinje[4].trim();
                int styrke = Integer.parseInt(strengStyrke);
                Legemiddel narkotisk = new Narkotisk(navn, pris, virkestoff, styrke);
                legemiddelliste.leggTil(narkotisk);
              }
              nesteLinje = innFil.nextLine().split(",");
              linjeTeller++;
            }
          }

          // resepter
          if (linje[1].equals("Resepter")) {
            String[] nesteLinje = innFil.nextLine().split(",");
            while (!(nesteLinje[0].charAt(0) == '#')) {
              String legemiddelNr = nesteLinje[0];
              int intLegemiddelNr = Integer.parseInt(legemiddelNr);
              Legemiddel legemiddel = null;
              for (Legemiddel l : legemiddelliste){
                if (l.hentId() == intLegemiddelNr) legemiddel = l;
              }
              Lege lege = null;
              for (Lege l : legeliste){
                if (l.hentNavn().equals(nesteLinje[1])) lege = l;
              }
              Pasient pasient = null;
              for (Pasient p : pasientliste){
                if (p.hentID() == Integer.parseInt(nesteLinje[2])) pasient = p;
              }
              int reit=0;
              reit = Integer.parseInt(nesteLinje[4]);
              try {
                if (pasient==null || lege==null || legemiddel==null || reit==0){
                  throw new Exception("feilformattert reseptdata på linje: " + linjeTeller);
                }

                if (nesteLinje[3].equals("militaer")) {
                  Militaerresept resept = lege.skrivMilitaerResept(legemiddel, pasient, reit);
                  reseptliste.leggTil(resept);
                }
                else if (nesteLinje[3].equals("blaa")) {
                  BlaaResept resept = lege.skrivBlaaResept(legemiddel, pasient, reit);
                  reseptliste.leggTil(resept);
                }
                else if (nesteLinje[3].equals("hvit")) {
                  HvitResept resept = lege.skrivHvitResept(legemiddel, pasient, reit);
                  reseptliste.leggTil(resept);
                }
                if (nesteLinje[3].equals("p")) {
                  PResept resept = lege.skrivPResept(legemiddel, pasient);
                  reseptliste.leggTil(resept);
                }
              }
              catch (Exception exception) {System.out.println(exception);}
              nesteLinje = innFil.nextLine().split(",");
              linjeTeller++;
            }
          }
        }


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
    System.out.println("HOVEDMENY");
    boolean avslutte = false;

    // selve menyen tilbyr 8 ulike kommandoer og mulighet for aa avslutte
    while (!avslutte) {
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
      String svar = in.next();
      if (svar.equals("0")) skrivUtOversikt();
      else if (svar.equals("1")) leggTilLege();
      else if (svar.equals("2")) leggTilPasient();
      else if (svar.equals("3")) {
        try { leggTilResept(); }
        catch (Exception exception) {
          System.out.println(exception);
        }
      }
      else if (svar.equals("4")) leggTilLegemiddel();
      else if (svar.equals("5")) brukResept();
      else if (svar.equals("6")) skrivUtStatistikk();
      // else if (svar.equals("7")) skrivTilFil();
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
    System.out.println("### LEGER ###");
    skrivUtLeger();
    System.out.println();
    System.out.println("### LEGEMIDLER ###");
    skrivUtLegemidler();
    System.out.println();
    System.out.println("### PASIENTER ###");
    skrivUtPasienter();
    System.out.println();
    System.out.println("### RESEPTER ###");
    skrivUtResepter();
    System.out.println();
  }


  // metode som registrerer en ny lege til systemet
  static void leggTilLege() {
    Scanner in = new Scanner(System.in);
    String navn;
    System.out.println("Du har valgt aa legge til en lege. Vennligst oppgi legens navn.");
    navn = in.nextLine();
    Lege nyLege = new Lege(navn);
    legeliste.leggTil(nyLege);
    System.out.println("Legen " + nyLege.hentNavn() + " er lagt inn i systemet!");
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

    // bruker velger hva slags type resepten skal vaere
    // bruker trim og toLowerCase for aat svar skal vaere gyldig selv med mellomrom og stor bokstav
    System.out.println("Tast 'b' for blaa resept. Tast 'h' for hvit resept.");
    String input = in.next().trim().toLowerCase(); //hvordan gjoere om til smaa bokstaver??? - ifoelge internett kan denne metoden virke 'toLowerCase()'

    if (input.equals("b")) {
      System.out.println("Hvor mange reit skal resepten ha?");
      int reit = Integer.parseInt(in.next());
      Resept resept = utskrivendeLege.skrivBlaaResept(legemiddel, pasient, reit);
      reseptliste.leggTil(resept);
    }
    else if (input.equals("h")) {
      System.out.println("Tast 'v' for vanlig resept; tast 'm' for militaerresept" +
      " tast 'p' for p-resept.");
      input = in.next().trim().toLowerCase();

      if (input.equals("p")) {
        Resept resept = utskrivendeLege.skrivPResept(legemiddel, pasient);
        reseptliste.leggTil(resept);
      }
      else {
        System.out.println("Hvor mange reit skal resepten ha?");
        int reit = Integer.parseInt(in.next());

        if (input.equals("v")) {
          Resept resept = utskrivendeLege.skrivHvitResept(legemiddel, pasient, reit);
          reseptliste.leggTil(resept);
        }

        if (input.equals("m")) {
          Resept resept = utskrivendeLege.skrivMilitaerResept(legemiddel, pasient, reit);
          reseptliste.leggTil(resept);
        }
      }
    }
  }

  // legger til pasient
  static void leggTilPasient() {
    Scanner in = new Scanner(System.in);
    System.out.println("Du har valg å legge til ny pasient. Oppgi pasientens navn.");
    String navn = in.next();
    System.out.println("Oppgi pasientens foedselsnr.");
    String fnr = in.next();
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
      System.out.println("Oppgi vanedannende styrke");
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
  }


  // metode for aa bruke resept. Foelger forslaget i oppgaveteksten
  static void brukResept() {
    Scanner in = new Scanner(System.in);

    System.out.println("Hvilken pasient vil du se resepter for?");
    skrivUtPasienter();
    int pasnr = Integer.parseInt(in.next());
    Pasient pasient = pasientliste.hent(pasnr);

    System.out.println("Valgt pasient: " + pasient + ".");
    System.out.println("Angi ID-nr. på resepten du vil bruke.");
    pasient.skrivResepter();
    int reseptID = Integer.parseInt(in.next());

    /*
      Resept-IDen stemmer overense med reseptens indeks i reseptlisten, fordi
      reseptene i listen er sortert fra lavest ID-nr. til hoeyest ID-nr.
    */
    Resept resept = reseptliste.hent(reseptID);
    boolean brukt = resept.bruk(); //metoden returnerer false hvis ingen reit igjen
    if (!brukt) {
      System.out.println("Kunne ikke bruke resept på " + resept.hentLegemiddel() +
      " (ingen gjenvaerende reit).");
    } else {
      System.out.println("Brukte resept paa " + resept.hentLegemiddel() +
      ". Antall gjenvaerende reit: " + resept.hentReit() + ".");
    }
  }
  // metode for aa skrive ut statistikk
  static void skrivUtStatistikk() throws Exception {
    Scanner in = new Scanner(System.in);
    boolean avslutte = false;
    while (!avslutte) {
      System.out.println();
      System.out.println("0: Antall utskrevne resepter paa vanedannende legemidler.");
      System.out.println("1: Antall utskrevne resepter paa narkotiske legemidler.");
      System.out.println("2: Liste over leger som har skrevet ut resepter paa narktosike legemidler.");
      System.out.println("3: Liste over pasienter med gyldige resepter paa narkoktiske legemidler.");
      System.out.println("4: Gaa tilbake til hovedmenyen.");

      String svar = in.next();

      // skriver ut antall vanedannende resepter, finner dette ved aa gaa gjennom legelisten og kalle en hent-metode i legeklassen
      if (svar.equals("0")) {
        int sumNarkotisk = 0;

        for (Lege l : legeliste) {
          sumNarkotisk += l.antVanedannendeResepter();
        }

        System.out.println("Det er blitt skrevet ut " + sumNarkotisk + " resepter paa vanedannende legemidler.");
      }

      // samme for narkotiske
      if (svar.equals("1")) {
        int sumNarkotisk = 0;

        for (Lege l : legeliste) {
          sumNarkotisk += l.antNarkResepter();

        }

        System.out.println("Det er blitt skrevet ut " + sumNarkotisk + " resepter paa narkotiske legemidler.");
      }

      // kaller en metode i legeklassen som returnerer true dersom legeobjektet har skrevet ut narkotisk-resept, printer deretter antall.
      if (svar.equals("2")) {
        if (legeliste.stoerrelse() == 0) {
          System.out.println("Finner ingen leger med utskrevne resepter paa narkotiske legemidler.");
        }
        for (Lege l : legeliste) {
          if (l.harNark()) {
            System.out.println(l.hentNavn() + " har skrevet ut " + l.antNarkResepter() + " resepter paa narkotiske legemidler");
          }
        }
      }
      // samme tankegang her, bare for paasienter
      if (svar.equals("3")) {
        if (pasientliste.stoerrelse() == 0) {
          System.out.println("Finner ingen pasienter med gyldige resepter paa narkotiske legemidler.");
        }
        for (Pasient p : pasientliste) {
          if (p.harNark()) {
          }
        }
      }

      if (svar.equals("4")) {
        avslutte = true;
      }
    }
  }


  // metode for aa skrive til fil
  static void skrivTilFil(){
    Scanner in = new Scanner(System.in);

    // ber bruker angi filnavn
    System.out.println("Angi filnavn: ");
    String filNavn = in.next();
    File utfil = new File(filNavn);

    // dersom navnet ikke er gyldig kan man proeve igjen
    try {
      PrintWriter utskrift = new PrintWriter(utfil);
    }
    catch (Exception e) {
      System.out.println("Feilmelding: prøv igjen med et annet navn.");
      skrivTilFil();
    }
    // lager en lang tekststreng med all info
    String utskrift = "# Pasienter (navn,fnr)\n";
    for (Pasient p : pasientliste) {
      utskrift = utskrift + p.hentNavnOgFdn() + "\n";
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

    utskrift.close();
  }
}
