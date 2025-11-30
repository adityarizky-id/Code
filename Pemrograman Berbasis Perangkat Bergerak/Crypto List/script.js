// URL API Coinlore
const API_URL = "https://api.coinlore.net/api/tickers/";

const cryptoListEl = document.getElementById("crypto-list");
const btnRefresh = document.getElementById("btn-refresh");

// Panggil pertama kali saat halaman dibuka
fetchAndRenderCrypto();

// Event klik tombol Refresh
btnRefresh.addEventListener("click", () => {
  fetchAndRenderCrypto();
});

/**
 * Ambil data dari API dan render ke tampilan
 */
function fetchAndRenderCrypto() {
  // Tampilkan status loading
  cryptoListEl.innerHTML = "<p>Sedang mengambil data...</p>";

  fetch(API_URL)
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.json();
    })
    .then((data) => {
      // data.data berisi array coin
      const coins = data.data;

      // bersihkan list
      cryptoListEl.innerHTML = "";

      // Misal tampilkan 20 teratas (boleh diubah sesuai kebutuhan)
      coins.slice(0, 20).forEach((coin) => {
        const card = createCryptoCard(coin);
        cryptoListEl.appendChild(card);
      });
    })
    .catch((error) => {
      console.error("Error:", error);
      cryptoListEl.innerHTML =
        "<p>Terjadi kesalahan saat mengambil data.</p>";
    });
}

/**
 * Membuat elemen card untuk satu coin
 * coin: 1 objek dari array data (punya rank, name, symbol, price_usd, dll.)
 */
function createCryptoCard(coin) {
  const card = document.createElement("div");
  card.className = "crypto-card";

  // Struktur card dibuat mirip dengan contoh di soal:
  // Baris label: Rank | Bitcoin | USD
  // Baris value: 1    | BTC     | 27604.54
  // + name dan symbol

  card.innerHTML = `
    <div class="crypto-row-top">
      <span class="crypto-label">Rank</span>
      <span class="crypto-label">USD</span>
    </div>
    <div class="crypto-row-middle">
      <span class="crypto-rank">${coin.rank}</span>
      <span class="crypto-price">${coin.price_usd}</span>
    </div>
    <div class="crypto-name">${coin.name}</div>
    <div class="crypto-symbol">${coin.symbol}</div>
  `;

  return card;
}
