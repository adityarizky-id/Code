/**
 * Soal 2: Deret Aritmatika
 * NIM: 050469856 -> a=56, b=8+1=9
 */
function buatDeretAritmatika(nim: string, jumlahSuku: number = 10): string {
    const awalStr = nim.slice(-2);
    const bedaStr = nim.slice(-3, -2);

    const angkaAwal = parseInt(awalStr);
    const beda = parseInt(bedaStr) + 1; // Digit ke-3 dari belakang + 1

    if (isNaN(angkaAwal) || isNaN(beda) || jumlahSuku <= 0) {
        return "NIM tidak valid atau parameter tidak valid.";
    }

    let deret: number[] = [];

    // Perulangan untuk menghitung 10 suku (n=1 sampai n=10)
    for (let n = 1; n <= jumlahSuku; n++) {
        // Un = a + (n-1)b
        const sukuKeN = angkaAwal + (n - 1) * beda;
        deret.push(sukuKeN);
    }

    return `Angka Awal (a): ${angkaAwal}, Beda (b): ${beda}\nOutput: ${deret.join(", ")}`;
}

// EKSEKUSI SOAL 2
const nimS2 = "050469856";
// console.log(buatDeretAritmatika(nimS2));