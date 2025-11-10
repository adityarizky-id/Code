import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    // Array/List untuk menyimpan data menu (Object)
    private static List<Menu> daftarMenu = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    // Initial Data Menu (minimal 4 per kategori)
    private static void initMenu() {
        daftarMenu.add(new Menu("Nasi Goreng Spesial", 35000.0, "Makanan"));
        daftarMenu.add(new Menu("Sate Ayam", 40000.0, "Makanan"));
        daftarMenu.add(new Menu("Gado-Gado", 25000.0, "Makanan"));
        daftarMenu.add(new Menu("Soto Betawi", 30000.0, "Makanan"));

        daftarMenu.add(new Menu("Es Teh Manis", 10000.0, "Minuman"));
        daftarMenu.add(new Menu("Es Jeruk", 15000.0, "Minuman"));
        daftarMenu.add(new Menu("Kopi Susu", 20000.0, "Minuman"));
        daftarMenu.add(new Menu("Air Mineral", 5000.0, "Minuman"));
    }
    // ... (method main ada di bagian C)