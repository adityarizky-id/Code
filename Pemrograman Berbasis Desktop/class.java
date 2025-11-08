// Class Menu untuk merepresentasikan item menu
public class Menu {
    // Variabel instance (atribut)
    private String nama;
    private String kategori; // 'Makanan' atau 'Minuman'
    private double harga;
    
    // Konstruktor untuk inisialisasi objek Menu
    public Menu(String nama, String kategori, double harga) {
        this.nama = nama;
        this.kategori = kategori;
        this.harga = harga;
    }
    
    // Method accessor (getter)
    public String getNama() {
        return nama;
    }
    
    public String getKategori() {
        return kategori;
    }
    
    public double getHarga() {
        return harga;
    }
}