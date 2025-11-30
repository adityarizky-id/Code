public class Menu {
    // Atribut (Variabel, Tipe Data, Identifier)
    private String nama;
    private double harga;
    private String kategori; // "Makanan" atau "Minuman"

    // Konstruktor
    public Menu(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    // Method Getter
    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }

    // Method Setter (Diperlukan untuk fungsi mengubah harga)
    public void setHarga(double harga) {
        this.harga = harga;
    }

    // Method untuk menampilkan detail menu (opsional, bisa juga di Main)
    @Override
    public String toString() {
        return String.format("%s (%.2f) - Kategori: %s", nama, harga, kategori);
    }
}