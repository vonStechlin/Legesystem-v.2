import java.io.*;
import java.util.*;

public class Testprogram {
  // Lenkeliste<Legemiddel> legemiddelliste;
  // Lenkeliste<Resept> reseptliste;
  // SortertLenkeliste<Lege> legeliste;
  // Lenkeliste<Pasient> pasientliste;

  public static void main(String[] args) throws FileNotFoundException {
    File inndata = new File("inndata.txt");
    Scanner in = new Scanner(inndata);
    Scanner in2 = new Scanner(inndata);
    int teller = 0;
    while (in.hasNextLine()) {
      String linje = in.nextLine();
      teller++;
      if (linje.split(" ")[1].equals("Pasienter")) {
        System.out.println(linje + "\n" + teller);
        while (in2.nextLine().split(" ")[0].equals("#")) {
          String linjee = in2.nextLine();
          System.out.println(linjee);
          String navn = linje.split(" ")[0];
          // String foedselsnr = linje.split(" ")[1];
          // Pasient pasient = new Pasient(navn, foedselsnr);
          // pasientliste.leggTil(pasient);
        }
      }
    }
  }
}
