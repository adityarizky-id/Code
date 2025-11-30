import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ===================== ABSTRACT CLASS =====================
abstract class MenuItem {
    private String nama;
    private double harga;
    private String kategori;

    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    // Enkapsulasi: pakai getter & setter
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    // Abstraksi & polymorphism
    public abstract void tampilMenu();
}

// ===================== SUBCLASS MAKANAN =====================
class Makanan extends MenuItem {
    private String jenisMakanan; // contoh: "Berat", "Ringan", "Snack"

    public Makanan(String nama, double harga, String kategori, String jenisMakanan) {
        super(nama, harga, kategori);
        this.jenisMakanan = jenisMakanan;
    }

    public String getJenisMakanan() {
        return jenisMakanan;
    }

    public void setJenisMakanan(String jenisMakanan) {
        this.jenisMakanan = jenisMakanan;
    }

    @Override
    public void tampilMenu() {
        System.out.println("[MAKANAN] " + getNama()
                + " | Kategori: " + getKategori()
                + " | Jenis: " + jenisMakanan
                + " | Harga: Rp " + getHarga());
    }
}

// ===================== SUBCLASS MINUMAN =====================
class Minuman extends MenuItem {
    private String jenisMinuman; // contoh: "Dingin", "Panas", "Jus"

    public Minuman(String nama, double harga, String kategori, String jenisMinuman) {
        super(nama, harga, kategori);
        this.jenisMinuman = jenisMinuman;
    }

    public String getJenisMinuman() {
        return jenisMinuman;
    }

    public void setJenisMinuman(String jenisMinuman) {
        this.jenisMinuman = jenisMinuman;
    }

    @Override
    public void tampilMenu() {
        System.out.println("[MINUMAN] " + getNama()
                + " | Kategori: " + getKategori()
                + " | Jenis: " + jenisMinuman
                + " | Harga: Rp " + getHarga());
    }
}

// ===================== SUBCLASS DISKON =====================
class Diskon extends MenuItem {
    private double persenDiskon; // 10 = 10%

    public Diskon(String nama, double persenDiskon) {
        super(nama, 0.0, "DISKON");
        this.persenDiskon = persenDiskon;
    }

    public double getPersenDiskon() {
        return persenDiskon;
    }

    public void setPersenDiskon(double persenDiskon) {
        this.persenDiskon = persenDiskon;
    }

    @Override
    public void tampilMenu() {
        System.out.println("[DISKON] " + getNama()
                + " | Potongan: " + persenDiskon + "%");
    }
}

// ===================== EXCEPTION KHUSUS =====================
class MenuItemNotFoundException extends Exception {
    public MenuItemNotFoundException(String message) {
        super(message);
    }
}

// ===================== KELAS MENU (DAFTAR MENU RESTORAN) =====================
class Menu {
    private List<MenuItem> daftarMenu = new ArrayList<>();

    public void tambahMenuItem(MenuItem item) {
        daftarMenu.add(item);
    }

    public void tampilkanMenu() {
        if (daftarMenu.isEmpty()) {
            System.out.println("Menu masih kosong.");
            return;
        }
        System.out.println("===== DAFTAR MENU RESTORAN =====");
        for (int i = 0; i < daftarMenu.size(); i++) {
            System.out.print((i + 1) + ". ");
            daftarMenu.get(i).tampilMenu(); // polymorphism
        }
    }

    public MenuItem getMenuItem(int index) throws MenuItemNotFoundException {
        if (index < 0 || index >= daftarMenu.size()) {
            throw new MenuItemNotFoundException("Item menu dengan index tersebut tidak ditemukan.");
        }
        return daftarMenu.get(index);
    }

    public int getJumlahItem() {
        return daftarMenu.size();
    }

    // ===== FILE I/O: SIMPAN & MUAT MENU =====
    public void simpanKeFile(String namaFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(namaFile))) {
            for (MenuItem item : daftarMenu) {
                if (item instanceof Makanan) {
                    Makanan m = (Makanan) item;
                    writer.write("MAKANAN;" + m.getNama() + ";" + m.getHarga() + ";" +
                            m.getKategori() + ";" + m.getJenisMakanan());
                } else if (item instanceof Minuman) {
                    Minuman m = (Minuman) item;
                    writer.write("MINUMAN;" + m.getNama() + ";" + m.getHarga() + ";" +
                            m.getKategori() + ";" + m.getJenisMinuman());
                } else if (item instanceof Diskon) {
                    Diskon d = (Diskon) item;
                    writer.write("DISKON;" + d.getNama() + ";" + d.getPersenDiskon());
                }
                writer.newLine();
            }
        }
    }

    public void muatDariFile(String namaFile) throws IOException {
        daftarMenu.clear();
        File file = new File(namaFile);
        if (!file.exists()) {
            System.out.println("File menu belum ada. Membuat menu kosong.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(namaFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Format:
                // MAKANAN;nama;harga;kategori;jenisMakanan
                // MINUMAN;nama;harga;kategori;jenisMinuman
                // DISKON;nama;persenDiskon
                String[] parts = line.split(";");
                if (parts.length > 0) {
                    String tipe = parts[0];
                    switch (tipe) {
                        case "MAKANAN":
                            if (parts.length == 5) {
                                String nama = parts[1];
                                double harga = Double.parseDouble(parts[2]);
                                String kategori = parts[3];
                                String jenisMakanan = parts[4];
                                daftarMenu.add(new Makanan(nama, harga, kategori, jenisMakanan));
                            }
                            break;
                        case "MINUMAN":
                            if (parts.length == 5) {
                                String nama = parts[1];
                                double harga = Double.parseDouble(parts[2]);
                                String kategori = parts[3];
                                String jenisMinuman = parts[4];
                                daftarMenu.add(new Minuman(nama, harga, kategori, jenisMinuman));
                            }
                            break;
                        case "DISKON":
                            if (parts.length == 3) {
                                String nama = parts[1];
                                double persen = Double.parseDouble(parts[2]);
                                daftarMenu.add(new Diskon(nama, persen));
                            }
                            break;
                    }
                }
            }
        }
    }
}

// ===================== KELAS PESANAN =====================
class Pesanan {
    private List<MenuItem> itemDipesan = new ArrayList<>();
    private List<Diskon> diskonDiterapkan = new ArrayList<>();

    public void tambahItem(MenuItem item) {
        if (item instanceof Diskon) {
            diskonDiterapkan.add((Diskon) item);
        } else {
            itemDipesan.add(item);
        }
    }

    public double hitungTotalSebelumDiskon() {
        double total = 0.0;
        for (MenuItem item : itemDipesan) {
            total += item.getHarga();
        }
        return total;
    }

    public double hitungTotalDiskonPersen() {
        double totalPersen = 0.0;
        for (Diskon d : diskonDiterapkan) {
            totalPersen += d.getPersenDiskon();
        }
        if (totalPersen > 100) {
            totalPersen = 100; // Batas maksimal diskon 100%
        }
        return totalPersen;
    }

    public double hitungTotalSetelahDiskon() {
        double total = hitungTotalSebelumDiskon();
        double persenDiskon = hitungTotalDiskonPersen();
        double potongan = total * (persenDiskon / 100.0);
        return total - potongan;
    }

    public boolean kosong() {
        return itemDipesan.isEmpty() && diskonDiterapkan.isEmpty();
    }

    public String generateStruk() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== STRUK PESANAN RESTORAN =====\n");
        if (itemDipesan.isEmpty()) {
            sb.append("Tidak ada item makanan/minuman yang dipesan.\n");
        } else {
            sb.append("Item yang dipesan:\n");
            for (MenuItem item : itemDipesan) {
                sb.append("- ")
                        .append(item.getNama())
                        .append(" (Rp ")
                        .append(item.getHarga())
                        .append(")\n");
            }
        }

        sb.append("\nDiskon yang diterapkan:\n");
        if (diskonDiterapkan.isEmpty()) {
            sb.append("- Tidak ada diskon\n");
        } else {
            for (Diskon d : diskonDiterapkan) {
                sb.append("- ")
                        .append(d.getNama())
                        .append(" (")
                        .append(d.getPersenDiskon())
                        .append("%)\n");
            }
        }

        double totalSebelum = hitungTotalSebelumDiskon();
        double totalDiskonPersen = hitungTotalDiskonPersen();
        double totalSetelah = hitungTotalSetelahDiskon();

        sb.append("\nTotal sebelum diskon : Rp ").append(totalSebelum).append("\n");
        sb.append("Total diskon         : ").append(totalDiskonPersen).append("%\n");
        sb.append("Total yang harus dibayar: Rp ").append(totalSetelah).append("\n");
        sb.append("==================================\n");

        return sb.toString();
    }

    // Simpan struk ke file teks
    public void simpanStrukKeFile(String namaFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(namaFile))) {
            writer.write(generateStruk());
        }
    }

    // Static helper untuk baca struk dari file
    public static void tampilkanStrukDariFile(String namaFile) throws IOException {
        File file = new File(namaFile);
        if (!file.exists()) {
            System.out.println("File struk tidak ditemukan.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(namaFile))) {
            String line;
            System.out.println("===== STRUK DARI FILE: " + namaFile + " =====");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}

// ===================== KELAS MAIN (PROGRAM UTAMA) =====================
public class MainRestoran {
    private static final String NAMA_FILE_MENU = "menu_restoran.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Menu menu = new Menu();

        // Coba muat menu dari file di awal
        try {
            menu.muatDariFile(NAMA_FILE_MENU);
        } catch (IOException e) {
            System.out.println("Gagal memuat menu dari file: " + e.getMessage());
        }

        int pilihan;
        do {
            tampilkanMenuUtama();
            System.out.print("Pilih menu: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Input harus angka, coba lagi: ");
                scanner.next();
            }
            pilihan = scanner.nextInt();
            scanner.nextLine(); // buang newline

            switch (pilihan) {
                case 1:
                    tambahItemBaru(menu);
                    break;
                case 2:
                    menu.tampilkanMenu();
                    break;
                case 3:
                    prosesPesanan(menu);
                    break;
                case 4:
                    simpanMenuKeFile(menu);
                    break;
                case 5:
                    muatMenuDariFile(menu);
                    break;
                case 6:
                    tampilkanStrukDariFile();
                    break;
                case 0:
                    System.out.println("Keluar dari program...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
            System.out.println();
        } while (pilihan != 0);
    }

    private static void tampilkanMenuUtama() {
        System.out.println("===== SISTEM MANAJEMEN RESTORAN =====");
        System.out.println("1. Tambah item baru ke menu");
        System.out.println("2. Tampilkan menu restoran");
        System.out.println("3. Buat pesanan pelanggan");
        System.out.println("4. Simpan daftar menu ke file");
        System.out.println("5. Muat daftar menu dari file");
        System.out.println("6. Tampilkan struk pesanan dari file");
        System.out.println("0. Keluar");
    }

    // ====== MENU 1: TAMBAH ITEM BARU ======
    private static void tambahItemBaru(Menu menu) {
        System.out.println("Tambah item baru:");
        System.out.println("1. Makanan");
        System.out.println("2. Minuman");
        System.out.println("3. Diskon");
        System.out.print("Pilih jenis item: ");

        int jenis = scanner.nextInt();
        scanner.nextLine(); // buang newline

        switch (jenis) {
            case 1:
                System.out.print("Nama makanan: ");
                String namaM = scanner.nextLine();
                System.out.print("Harga: ");
                double hargaM = scanner.nextDouble();
                scanner.nextLine();
                System.out.print("Kategori (misal: Main Course, Snack): ");
                String kategoriM = scanner.nextLine();
                System.out.print("Jenis makanan (misal: Berat, Ringan): ");
                String jenisMakanan = scanner.nextLine();
                menu.tambahMenuItem(new Makanan(namaM, hargaM, kategoriM, jenisMakanan));
                System.out.println("Makanan berhasil ditambahkan.");
                break;
            case 2:
                System.out.print("Nama minuman: ");
                String namaMin = scanner.nextLine();
                System.out.print("Harga: ");
                double hargaMin = scanner.nextDouble();
                scanner.nextLine();
                System.out.print("Kategori (misal: Coffee, Tea): ");
                String kategoriMin = scanner.nextLine();
                System.out.print("Jenis minuman (misal: Panas, Dingin): ");
                String jenisMinuman = scanner.nextLine();
                menu.tambahMenuItem(new Minuman(namaMin, hargaMin, kategoriMin, jenisMinuman));
                System.out.println("Minuman berhasil ditambahkan.");
                break;
            case 3:
                System.out.print("Nama diskon: ");
                String namaD = scanner.nextLine();
                System.out.print("Persentase diskon (contoh: 10 untuk 10%): ");
                double persen = scanner.nextDouble();
                scanner.nextLine();
                menu.tambahMenuItem(new Diskon(namaD, persen));
                System.out.println("Diskon berhasil ditambahkan.");
                break;
            default:
                System.out.println("Jenis item tidak dikenal.");
        }
    }

    // ====== MENU 3: PROSES PESANAN ======
    private static void prosesPesanan(Menu menu) {
        if (menu.getJumlahItem() == 0) {
            System.out.println("Menu masih kosong. Tambahkan item terlebih dahulu.");
            return;
        }

        Pesanan pesanan = new Pesanan();
        int pilihan;
        do {
            System.out.println("Pilih item yang ingin dipesan (0 untuk selesai):");
            menu.tampilkanMenu();
            System.out.print("Nomor item: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Input harus angka, coba lagi: ");
                scanner.next();
            }
            pilihan = scanner.nextInt();
            scanner.nextLine(); // buang newline

            if (pilihan == 0) {
                break;
            }

            try {
                MenuItem item = menu.getMenuItem(pilihan - 1);
                pesanan.tambahItem(item);
                System.out.println("Item \"" + item.getNama() + "\" ditambahkan ke pesanan.");
            } catch (MenuItemNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (true);

        if (pesanan.kosong()) {
            System.out.println("Tidak ada pesanan yang tercatat.");
            return;
        }

        // Tampilkan struk di layar
        String struk = pesanan.generateStruk();
        System.out.println(struk);

        // Simpan struk ke file jika mau
        System.out.print("Simpan struk ke file? (y/n): ");
        String jawab = scanner.nextLine();
        if (jawab.equalsIgnoreCase("y")) {
            System.out.print("Masukkan nama file struk (contoh: struk1.txt): ");
            String namaFile = scanner.nextLine();
            try {
                pesanan.simpanStrukKeFile(namaFile);
                System.out.println("Struk berhasil disimpan ke file: " + namaFile);
            } catch (IOException e) {
                System.out.println("Gagal menyimpan struk: " + e.getMessage());
            }
        }
    }

    // ====== MENU 4: SIMPAN MENU ======
    private static void simpanMenuKeFile(Menu menu) {
        try {
            menu.simpanKeFile(NAMA_FILE_MENU);
            System.out.println("Daftar menu berhasil disimpan ke file: " + NAMA_FILE_MENU);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan menu: " + e.getMessage());
        }
    }

    // ====== MENU 5: MUAT MENU ======
    private static void muatMenuDariFile(Menu menu) {
        try {
            menu.muatDariFile(NAMA_FILE_MENU);
            System.out.println("Daftar menu berhasil dimuat dari file: " + NAMA_FILE_MENU);
        } catch (IOException e) {
            System.out.println("Gagal memuat menu: " + e.getMessage());
        }
    }

    // ====== MENU 6: TAMPILKAN STRUK DARI FILE ======
    private static void tampilkanStrukDariFile() {
        System.out.print("Masukkan nama file struk (contoh: struk1.txt): ");
        String namaFile = scanner.nextLine();
        try {
            Pesanan.tampilkanStrukDariFile(namaFile);
        } catch (IOException e) {
            System.out.println("Gagal membaca struk: " + e.getMessage());
        }
    }
}
