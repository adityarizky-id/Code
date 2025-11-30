import java.util.Scanner;

public class RestoranApp {

    // Scanner global
    static Scanner scanner = new Scanner(System.in);

    // Kelas untuk menyimpan data menu
    static class MenuItem {
        String name;
        double price;
        String category; // "Makanan" / "Minuman"

        MenuItem(String name, double price, String category) {
            this.name = name;
            this.price = price;
            this.category = category;
        }
    }

    // Penyimpanan menu menggunakan array
    static MenuItem[] menuItems = new MenuItem[100];
    static int menuCount = 0;

    public static void main(String[] args) {
        initDefaultMenu();

        int pilihan;
        do {
            System.out.println("=======================================");
            System.out.println("        APLIKASI RESTORAN SEDERHANA   ");
            System.out.println("=======================================");
            System.out.println("1. Menu Pelanggan");
            System.out.println("2. Pengelolaan Menu (Pemilik)");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu (1-3): ");

            String input = scanner.nextLine();
            if (input.equals("1") || input.equals("2") || input.equals("3")) {
                pilihan = Integer.parseInt(input);
            } else {
                System.out.println("Pilihan tidak valid, silakan coba lagi.\n");
                pilihan = -1;
            }

            switch (pilihan) {
                case 1:
                    menuPelanggan();
                    break;
                case 2:
                    menuPemilik();
                    break;
                case 3:
                    System.out.println("Terima kasih telah menggunakan aplikasi restoran.");
                    break;
                default:
                    // tidak melakukan apa-apa, loop akan jalan lagi
                    break;
            }

        } while (pilihan != 3);

        scanner.close();
    }

    // Inisialisasi beberapa menu awal
    static void initDefaultMenu() {
        tambahMenuBaru("Nasi Goreng", 25000, "Makanan");
        tambahMenuBaru("Mie Goreng", 23000, "Makanan");
        tambahMenuBaru("Ayam Bakar", 30000, "Makanan");
        tambahMenuBaru("Es Teh Manis", 8000, "Minuman");
        tambahMenuBaru("Es Jeruk", 9000, "Minuman");
        tambahMenuBaru("Air Mineral", 5000, "Minuman");
    }

    // Menambah menu ke array
    static void tambahMenuBaru(String name, double price, String category) {
        if (menuCount >= menuItems.length) {
            System.out.println("Kapasitas menu penuh. Tidak dapat menambah menu baru.");
            return;
        }
        menuItems[menuCount] = new MenuItem(name, price, category);
        menuCount++;
    }

    // ====================== MENU PELANGGAN ======================

    static void menuPelanggan() {
        System.out.println("\n========== MENU PELANGGAN ==========");
        if (menuCount == 0) {
            System.out.println("Belum ada menu di restoran.");
            return;
        }
        prosesPemesanan();
    }

    static void tampilkanDaftarMenu() {
        System.out.println("\n---------- DAFTAR MENU ----------");
        System.out.printf("%-4s %-20s %-10s %-10s%n", "No", "Nama Menu", "Kategori", "Harga");
        for (int i = 0; i < menuCount; i++) {
            MenuItem item = menuItems[i];
            if (item != null) {
                System.out.printf("%-4d %-20s %-10s Rp %.0f%n",
                        (i + 1), item.name, item.category, item.price);
            }
        }
        System.out.println("---------------------------------\n");
    }

    static void prosesPemesanan() {
        tampilkanDaftarMenu();

        // Menyimpan pesanan (index menu & jumlah)
        int[] orderIndex = new int[100];
        int[] orderQty = new int[100];
        int orderCount = 0;

        while (true) {
            System.out.print("Masukkan NAMA menu yang ingin dipesan (atau ketik 'selesai' untuk mengakhiri): ");
            String namaMenu = scanner.nextLine();

            if (namaMenu.equalsIgnoreCase("selesai")) {
                break;
            }

            int index = cariMenuByName(namaMenu);
            if (index == -1) {
                System.out.println("Menu tidak ditemukan. Silakan masukkan nama menu sesuai daftar.");
                continue;
            }

            int qty = 0;
            while (true) {
                System.out.print("Masukkan jumlah pesanan untuk " + menuItems[index].name + ": ");
                String qtyInput = scanner.nextLine();
                try {
                    qty = Integer.parseInt(qtyInput);
                    if (qty <= 0) {
                        System.out.println("Jumlah harus lebih dari 0.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input jumlah tidak valid. Masukkan angka.");
                }
            }

            // Cek apakah menu sudah pernah dipesan, jika ya tambahkan jumlahnya
            boolean ditemukan = false;
            for (int i = 0; i < orderCount; i++) {
                if (orderIndex[i] == index) {
                    orderQty[i] += qty;
                    ditemukan = true;
                    break;
                }
            }

            if (!ditemukan) {
                orderIndex[orderCount] = index;
                orderQty[orderCount] = qty;
                orderCount++;
            }

            System.out.println("Pesanan ditambahkan.\n");
        }

        if (orderCount == 0) {
            System.out.println("Tidak ada pesanan yang dilakukan.");
            return;
        }

        cetakStruk(orderIndex, orderQty, orderCount);
    }

    static int cariMenuByName(String name) {
        for (int i = 0; i < menuCount; i++) {
            if (menuItems[i] != null && menuItems[i].name.equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    static void cetakStruk(int[] orderIndex, int[] orderQty, int orderCount) {
        System.out.println("\n============= STRUK PEMBAYARAN =============");
        System.out.printf("%-20s %-8s %-12s %-12s%n", "Item", "Qty", "Harga", "Total");
        System.out.println("--------------------------------------------");

        double subtotal = 0;
        double subtotalMinuman = 0;

        for (int i = 0; i < orderCount; i++) {
            MenuItem item = menuItems[orderIndex[i]];
            int qty = orderQty[i];
            double totalItem = item.price * qty;
            subtotal += totalItem;

            if (item.category.equalsIgnoreCase("Minuman")) {
                subtotalMinuman += totalItem;
            }

            System.out.printf("%-20s %-8d Rp %-10.0f Rp %-10.0f%n",
                    item.name, qty, item.price, totalItem);
        }

        System.out.println("--------------------------------------------");
        System.out.printf("Subtotal                          : Rp %.0f%n", subtotal);

        // Diskon 10% jika subtotal > 100.000
        double diskon10 = 0;
        if (subtotal > 100000) {
            diskon10 = subtotal * 0.10;
            System.out.printf("Diskon 10%% (Subtotal > 100000)   : -Rp %.0f%n", diskon10);
        }

        // Penawaran beli 1 gratis 1 (kita anggap: gratis 1 minuman termurah yang dipesan)
        double diskonMinuman = hitungDiskonMinuman(orderIndex, orderQty, orderCount, subtotal);
        if (diskonMinuman > 0) {
            System.out.printf("Promo Beli 1 Gratis 1 (Minuman)  : -Rp %.0f%n", diskonMinuman);
        }

        double subtotalSetelahDiskon = subtotal - diskon10 - diskonMinuman;
        System.out.printf("Subtotal setelah diskon           : Rp %.0f%n", subtotalSetelahDiskon);

        // Pajak 10% dari subtotal setelah diskon
        double pajak = subtotalSetelahDiskon * 0.10;
        System.out.printf("Pajak 10%%                         : Rp %.0f%n", pajak);

        // Biaya pelayanan tetap Rp 20.000
        double biayaPelayanan = 20000;
        System.out.printf("Biaya Pelayanan                   : Rp %.0f%n", biayaPelayanan);

        double totalBayar = subtotalSetelahDiskon + pajak + biayaPelayanan;
        System.out.println("--------------------------------------------");
        System.out.printf("TOTAL BAYAR                       : Rp %.0f%n", totalBayar);
        System.out.println("============================================\n");
    }

    // Hitung diskon minuman (beli 1 gratis 1) jika subtotal > 50.000
    // Di sini diimplementasikan sebagai: gratis 1 minuman termurah yang dipesan.
    static double hitungDiskonMinuman(int[] orderIndex, int[] orderQty, int orderCount, double subtotal) {
        if (subtotal <= 50000) {
            return 0;
        }

        double minDrinkPrice = Double.MAX_VALUE;
        boolean adaMinuman = false;

        for (int i = 0; i < orderCount; i++) {
            MenuItem item = menuItems[orderIndex[i]];
            if (item.category.equalsIgnoreCase("Minuman") && orderQty[i] > 0) {
                adaMinuman = true;
                if (item.price < minDrinkPrice) {
                    minDrinkPrice = item.price;
                }
            }
        }

        if (!adaMinuman) {
            return 0;
        }
        // gratis 1 minuman termurah
        return minDrinkPrice;
    }

    // ====================== MENU PEMILIK / PENGELOLA ======================

    static void menuPemilik() {
        int pilihan;
        do {
            System.out.println("\n========== MENU PENGELOLA RESTORAN ==========");
            System.out.println("1. Lihat Daftar Menu");
            System.out.println("2. Tambah Menu Baru");
            System.out.println("3. Ubah Harga Menu");
            System.out.println("4. Hapus Menu");
            System.out.println("5. Kembali ke Menu Utama");
            System.out.print("Pilih menu (1-5): ");

            String input = scanner.nextLine();
            if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4") || input.equals("5")) {
                pilihan = Integer.parseInt(input);
            } else {
                System.out.println("Pilihan tidak valid, silakan coba lagi.");
                pilihan = -1;
            }

            switch (pilihan) {
                case 1:
                    tampilkanDaftarMenu();
                    break;
                case 2:
                    tambahMenuDariPemilik();
                    break;
                case 3:
                    ubahHargaMenu();
                    break;
                case 4:
                    hapusMenu();
                    break;
                case 5:
                    System.out.println("Kembali ke menu utama...\n");
                    break;
                default:
                    // tidak melakukan apa-apa
                    break;
            }

        } while (pilihan != 5);
    }

    static void tambahMenuDariPemilik() {
        System.out.println("\n--- Tambah Menu Baru ---");
        System.out.print("Masukkan nama menu: ");
        String nama = scanner.nextLine();

        String kategori;
        while (true) {
            System.out.print("Masukkan kategori (Makanan/Minuman): ");
            kategori = scanner.nextLine();
            if (kategori.equalsIgnoreCase("Makanan") || kategori.equalsIgnoreCase("Minuman")) {
                break;
            } else {
                System.out.println("Kategori tidak valid. Hanya 'Makanan' atau 'Minuman'.");
            }
        }

        double harga = 0;
        while (true) {
            System.out.print("Masukkan harga: ");
            String hargaInput = scanner.nextLine();
            try {
                harga = Double.parseDouble(hargaInput);
                if (harga <= 0) {
                    System.out.println("Harga harus lebih dari 0.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input harga tidak valid. Masukkan angka.");
            }
        }

        tambahMenuBaru(nama, harga, kategori.substring(0, 1).toUpperCase() + kategori.substring(1).toLowerCase());
        System.out.println("Menu baru berhasil ditambahkan.\n");
    }

    static void ubahHargaMenu() {
        if (menuCount == 0) {
            System.out.println("Belum ada menu.");
            return;
        }

        tampilkanDaftarMenu();
        int nomorMenu = -1;

        while (true) {
            System.out.print("Masukkan nomor menu yang ingin diubah harganya: ");
            String input = scanner.nextLine();
            try {
                nomorMenu = Integer.parseInt(input);
                if (nomorMenu < 1 || nomorMenu > menuCount) {
                    System.out.println("Nomor menu tidak valid.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Masukkan angka.");
            }
        }

        MenuItem item = menuItems[nomorMenu - 1];
        System.out.println("Anda akan mengubah harga menu: " + item.name + " (Saat ini: Rp " + item.price + ")");

        String konfirmasi;
        while (true) {
            System.out.print("Yakin ingin mengubah? (Ya/Tidak): ");
            konfirmasi = scanner.nextLine();
            if (konfirmasi.equalsIgnoreCase("Ya") || konfirmasi.equalsIgnoreCase("Tidak")) {
                break;
            } else {
                System.out.println("Input harus 'Ya' atau 'Tidak'.");
            }
        }

        if (konfirmasi.equalsIgnoreCase("Ya")) {
            double hargaBaru = 0;
            while (true) {
                System.out.print("Masukkan harga baru: ");
                String hargaInput = scanner.nextLine();
                try {
                    hargaBaru = Double.parseDouble(hargaInput);
                    if (hargaBaru <= 0) {
                        System.out.println("Harga harus lebih dari 0.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input harga tidak valid. Masukkan angka.");
                }
            }

            item.price = hargaBaru;
            System.out.println("Harga menu berhasil diubah.\n");
        } else {
            System.out.println("Perubahan harga dibatalkan.\n");
        }
    }

    static void hapusMenu() {
        if (menuCount == 0) {
            System.out.println("Belum ada menu.");
            return;
        }

        tampilkanDaftarMenu();
        int nomorMenu = -1;

        while (true) {
            System.out.print("Masukkan nomor menu yang ingin dihapus: ");
            String input = scanner.nextLine();
            try {
                nomorMenu = Integer.parseInt(input);
                if (nomorMenu < 1 || nomorMenu > menuCount) {
                    System.out.println("Nomor menu tidak valid.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Masukkan angka.");
            }
        }

        MenuItem item = menuItems[nomorMenu - 1];
        System.out.println("Anda akan menghapus menu: " + item.name);

        String konfirmasi;
        while (true) {
            System.out.print("Yakin ingin menghapus? (Ya/Tidak): ");
            konfirmasi = scanner.nextLine();
            if (konfirmasi.equalsIgnoreCase("Ya") || konfirmasi.equalsIgnoreCase("Tidak")) {
                break;
            } else {
                System.out.println("Input harus 'Ya' atau 'Tidak'.");
            }
        }

        if (konfirmasi.equalsIgnoreCase("Ya")) {
            // Geser array ke kiri mulai dari index yang dihapus
            for (int i = nomorMenu - 1; i < menuCount - 1; i++) {
                menuItems[i] = menuItems[i + 1];
            }
            menuItems[menuCount - 1] = null;
            menuCount--;
            System.out.println("Menu berhasil dihapus.\n");
        } else {
            System.out.println("Penghapusan menu dibatalkan.\n");
        }
    }
}
