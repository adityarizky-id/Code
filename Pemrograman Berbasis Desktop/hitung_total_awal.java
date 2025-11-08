double totalBiayaAwal = 0.0;
for (int i = 0; i < indeksPesanan; i++) {
    // Logika untuk menemukan harga menu
    double hargaItem = 0.0;
    String kategoriItem = "";
    for (Menu menu : daftarMenu) {
        if (menu.getNama().equalsIgnoreCase(namaPesanan[i])) {
            hargaItem = menu.getHarga();
            kategoriItem = menu.getKategori();
            break;
        }
    }
    totalBiayaAwal += hargaItem * jumlahPesanan[i];
}
