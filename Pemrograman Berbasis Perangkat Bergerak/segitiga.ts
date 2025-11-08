/**
 * Soal 1: Pola Segitiga Angka
 * NIM: 050469856 -> Tinggi = 6
 */
function buatPolaSegitiga(nim: string): string {
    const lastChar = nim.trim().slice(-1);
    const digitTerakhir = parseInt(lastChar, 10);
    const tinggiSegitiga = digitTerakhir;

    if (Number.isNaN(tinggiSegitiga) || tinggiSegitiga <= 0) {
        return "NIM tidak valid atau tinggi segitiga tidak dapat ditentukan.";
    }

    let hasilPola = `Tinggi Segitiga: ${tinggiSegitiga}\n`;

    // Outer loop untuk baris
    for (let i = 1; i <= tinggiSegitiga; i++) {
        let baris = "";
        // Inner loop untuk angka pada setiap baris
        for (let j = 1; j <= i; j++) {
            baris += j + (j < i ? " " : "");
        }
        hasilPola += baris + "\n";
    }

    return hasilPola;
}

// EKSEKUSI SOAL 1
const nimS1 = "050469856";
// console.log(buatPolaSegitiga(nimS1));
export { buatPolaSegitiga };