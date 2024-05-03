import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class TugasRumahSakit {
   public static void main(String[] args)  {
       // FIFO
       Queue<String> antrianRS = new ArrayDeque<>();
       
       // Implementasi scanner untuk user-input
       Scanner input = new Scanner(System.in);
       while(true) {
           System.out.print("Masukkan nama pasien: ");
           antrianRS.offer(input.nextLine());
           
           System.out.println();
           System.out.println("Apakah sudah selesai? (Y untuk keluar)");
           if (input.nextLine().equalsIgnoreCase("Y")) {
            break;
        }
    }
       // Pasien yang datang setelahnya
       antrianRS.offer("Issadurrofiq");
       antrianRS.offer("Hasbi");
       antrianRS.offer("Andrean");
       
       // Output Nama Pasien
       System.out.println("Nama Pasien dalam antrian:");
       int i = 1;
       for(String next = antrianRS.poll(); next != null; next = antrianRS.poll()) {
           System.out.print(i + ") "); 
           System.out.println(next);
           i++;
       }
       input.close();
   }
}