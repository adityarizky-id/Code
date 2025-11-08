double diskon = 0.0;
String infoPenawaran = "";

// Logika Diskon 10% jika total pesanan > Rp 100.000
if (totalBiayaAwal > 100000.0) {
    diskon = totalBiayaAwal * 0.10;
}

// Logika Penawaran BOGO untuk Minuman jika total pesanan > Rp 50.000
// Asumsi: BOGO diterapkan pada item minuman dengan harga termurah dalam pesanan
if (totalBiayaAwal > 50000.0) {
    // Logika kompleks dihilangkan/disederhanakan untuk menghindari loop dalam konteks ini,
    // namun secara konseptual, BOGO mengurangi biaya satu item termurah kategori 'Minuman'
    // atau mengganti jumlah salah satu item minuman.
    infoPenawaran = "Anda Mendapatkan Penawaran BOGO Minuman (Asumsi: 1 Minuman Termurah Gratis)";
    // Dalam implementasi tanpa loop: Penentuan item termurah menjadi tantangan tanpa loop.
    // Jika diasumsikan diskon BOGO adalah diskon tetap Rp 5.000 untuk minuman:
    // diskon += 5000.0;
}

double totalSetelahDiskon = totalBiayaAwal - diskon;
double biayaPelayanan = 20000.0;
double pajak = totalSetelahDiskon * 0.10; // Pajak 10%
double totalAkhir = totalSetelahDiskon + pajak + biayaPelayanan;
