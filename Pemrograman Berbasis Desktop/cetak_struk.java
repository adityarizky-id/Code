public static void cetakStruk(...) {
    // ... Detail Struk menggunakan System.out.println() ...
    
    System.out.println("----------------------------------------");
    // Mencetak Detail Pesanan (secara manual 4 item karena batasan no-loop)
    // ...
    
    System.out.println("----------------------------------------");
    System.out.printf("Total Biaya Awal  : Rp %,.2f\n", totalBiayaAwal);
    
    // Struktur Keputusan untuk menampilkan Diskon
    if (diskon > 0) {
        System.out.printf("Diskon (10%%)    : - Rp %,.2f\n", diskon);
        System.out.printf("Total Setelah Diskon: Rp %,.2f\n", totalSetelahDiskon);
    }
    
    // Struktur Keputusan untuk menampilkan Penawaran
    if (!infoPenawaran.isEmpty()) {
        System.out.println(infoPenawaran);
    }

    System.out.printf("Pajak (10%%)       : + Rp %,.2f\n", pajak);
    System.out.printf("Biaya Pelayanan   : + Rp %,.2f\n", biayaPelayanan);
    System.out.println("----------------------------------------");
    System.out.printf("**TOTAL AKHIR BAYAR**: Rp %,.2f\n", totalAkhir);
    System.out.println("----------------------------------------");
}
