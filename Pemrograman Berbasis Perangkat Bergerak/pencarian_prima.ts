/**
 * Soal 3: Bilangan Prima
 * NIM: 050469856 -> Batas Akhir = 56 + 10 = 66
 */
// Fungsi untuk mengecek apakah suatu bilangan adalah prima
function isPrima(bilangan: number): boolean {
    if (bilangan <= 1) return false;
    if (bilangan <= 3) return true; 
    if (bilangan % 2 === 0 || bilangan % 3 === 0) return false;

    // Cek dari 5, lompatan 6 (optimasi 6k ± 1)
    for (let i = 5; i * i <= bilangan; i = i + 6) {
        if (bilangan % i === 0 || bilangan % (i + 2) === 0) return false;
    }

    return true;
}

function cariBilanganPrima(nim: string): string {
    const duaDigitTerakhir = parseInt(nim.slice(-2));
    const batasAkhir = duaDigitTerakhir + 10;

    if (isNaN(batasAkhir) || batasAkhir < 1) {
        return "NIM tidak valid atau batas akhir tidak dapat ditentukan.";
    }

    let bilanganPrima: number[] = [];

    // Perulangan untuk mengecek semua bilangan dari 1 hingga batas akhir
    for (let i = 1; i <= batasAkhir; i++) {
        if (isPrima(i)) {
            bilanganPrima.push(i);
        }
    }

    return `Batas Akhir: ${batasAkhir}\nOutput: ${bilanganPrima.join(", ")}`;
}

// EKSEKUSI SOAL 3
const nimS3 = "050469856";
// console.log(cariBilanganPrima(nimS3));