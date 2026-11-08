package com.example.calculator;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView txthasil, txtangka, txtriwayat;
    private View layoutUtama;
    private Button btnTema;

    private String container = "";

    // Riwayat tetap disimpan sementara menggunakan Array.
    private final String[] riwayat = new String[10];
    private int jumlahRiwayat = 0;

    private PrefsManager prefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutUtama = findViewById(R.id.layoutUtama);
        txthasil = findViewById(R.id.txthasil);
        txtangka = findViewById(R.id.txtangka);
        txtriwayat = findViewById(R.id.txtriwayat);
        btnTema = findViewById(R.id.btnTema);

        prefsManager = new PrefsManager(this);

        tampilkanRiwayat();
    }

    // Membaca kembali preferensi setiap Activity aktif.
    @Override
    protected void onResume() {
        super.onResume();

        if (prefsManager != null) {
            terapkanTema(prefsManager.isDarkMode());
        }
    }

    // Tombol untuk mengganti Dark Mode dan Light Mode.
    public void ubahTema(View view) {
        boolean temaSekarangGelap = prefsManager.isDarkMode();
        boolean temaBerikutnyaGelap = !temaSekarangGelap;

        prefsManager.setDarkMode(temaBerikutnyaGelap);
        terapkanTema(temaBerikutnyaGelap);
    }

    // Mengubah warna tampilan berdasarkan preferensi.
    private void terapkanTema(boolean darkMode) {
        int warnaLatar = Color.parseColor(darkMode ? "#000000" : "#FFFFFF");
        int warnaHasil = Color.parseColor(darkMode ? "#FFFFFF" : "#212121");
        int warnaAngka = Color.parseColor(darkMode ? "#909090" : "#616161");
        int warnaRiwayat = Color.parseColor(darkMode ? "#808080" : "#757575");

        layoutUtama.setBackgroundColor(warnaLatar);
        txthasil.setTextColor(warnaHasil);
        txtangka.setTextColor(warnaAngka);
        txtriwayat.setTextColor(warnaRiwayat);

        aturWarnaTombol((ViewGroup) layoutUtama, darkMode);

        if (btnTema != null) {
            btnTema.setText(darkMode ? "☀ Mode Terang" : "☾ Mode Gelap");
            btnTema.setTextColor(warnaHasil);
            btnTema.setBackgroundTintList(
                    ColorStateList.valueOf(
                            Color.parseColor(darkMode ? "#333333" : "#E0E0E0")
                    )
            );
        }
    }

    // Menelusuri tombol di dalam layout dan memberikan warna sesuai jenis tombol.
    private void aturWarnaTombol(ViewGroup parent, boolean darkMode) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);

            if (child instanceof ViewGroup) {
                aturWarnaTombol((ViewGroup) child, darkMode);
            } else if (child instanceof Button && child.getId() != R.id.btnTema) {
                Button tombol = (Button) child;
                String teks = tombol.getText().toString().trim();

                int warnaLatar;
                int warnaTeks;

                if ("=".equals(teks) || "DEL".equalsIgnoreCase(teks)
                        || "+".equals(teks) || "-".equals(teks)
                        || "X".equalsIgnoreCase(teks) || "/".equals(teks)
                        || "%".equals(teks)) {

                    warnaLatar = Color.parseColor("#FF9800");
                    warnaTeks = Color.WHITE;

                } else if ("AC".equalsIgnoreCase(teks)
                        || "+/-".equals(teks)) {

                    warnaLatar = Color.parseColor(darkMode ? "#1A1A1A" : "#EEEEEE");
                    warnaTeks = Color.parseColor(darkMode ? "#FFFFFF" : "#212121");

                } else {
                    warnaLatar = Color.parseColor(darkMode ? "#424242" : "#E0E0E0");
                    warnaTeks = Color.parseColor(darkMode ? "#FFFFFF" : "#212121");
                }

                tombol.setBackgroundTintList(ColorStateList.valueOf(warnaLatar));
                tombol.setTextColor(warnaTeks);
            }
        }
    }

    // MENAMPILKAN EKSPRESI
    private void tampilkanhasil() {
        txtangka.setText(container.replace("*", "×"));
    }

    // ANGKA
    public void nol(View view) {
        container += "0";
        tampilkanhasil();
    }

    public void satu(View view) {
        container += "1";
        tampilkanhasil();
    }

    public void dua(View view) {
        container += "2";
        tampilkanhasil();
    }

    public void tiga(View view) {
        container += "3";
        tampilkanhasil();
    }

    public void empat(View view) {
        container += "4";
        tampilkanhasil();
    }

    public void lima(View view) {
        container += "5";
        tampilkanhasil();
    }

    public void enam(View view) {
        container += "6";
        tampilkanhasil();
    }

    public void tujuh(View view) {
        container += "7";
        tampilkanhasil();
    }

    public void delapan(View view) {
        container += "8";
        tampilkanhasil();
    }

    public void sembilan(View view) {
        container += "9";
        tampilkanhasil();
    }

    // CLEAR
    public void allClear(View view) {
        txthasil.setText("0");
        txtangka.setText("");
        container = "";
    }

    // TAMBAH / KURANG TANDA
    public void plusMinus(View view) {
        if (container.isEmpty()) {
            return;
        }

        if (container.startsWith("-")) {
            container = container.substring(1);
        } else {
            container = "-" + container;
        }

        tampilkanhasil();
    }

    // TITIK DESIMAL
    public void dot(View view) {
        int lastOperator = Math.max(
                Math.max(
                        Math.max(
                                Math.max(
                                        container.lastIndexOf("+"),
                                        container.lastIndexOf("-")
                                ),
                                container.lastIndexOf("*")
                        ),
                        container.lastIndexOf("/")
                ),
                container.lastIndexOf("%")
        );

        String angkaTerakhir = container.substring(lastOperator + 1);

        if (!angkaTerakhir.contains(".")) {
            if (container.isEmpty()
                    || container.endsWith("+")
                    || container.endsWith("-")
                    || container.endsWith("*")
                    || container.endsWith("/")
                    || container.endsWith("%")) {

                container += "0.";
            } else {
                container += ".";
            }

            tampilkanhasil();
        }
    }

    // DELETE
    public void delete(View view) {
        if (!container.isEmpty()) {
            container = container.substring(0, container.length() - 1);
            tampilkanhasil();
        }
    }

    // OPERASI MATEMATIKA
    public void tambah(View view) {
        if (bolehTambahOperator()) {
            container += "+";
            tampilkanhasil();
        }
    }

    public void kurang(View view) {
        if (bolehTambahOperator()) {
            container += "-";
            tampilkanhasil();
        }
    }

    public void kali(View view) {
        if (bolehTambahOperator()) {
            container += "*";
            tampilkanhasil();
        }
    }

    public void bagi(View view) {
        if (bolehTambahOperator()) {
            container += "/";
            tampilkanhasil();
        }
    }

    public void modulo(View view) {
        if (bolehTambahOperator()) {
            container += "%";
            tampilkanhasil();
        }
    }

    // CEK OPERATOR
    private boolean bolehTambahOperator() {
        if (container.isEmpty()) {
            return false;
        }

        char terakhir = container.charAt(container.length() - 1);

        return terakhir != '+'
                && terakhir != '-'
                && terakhir != '*'
                && terakhir != '/'
                && terakhir != '%';
    }

    // ARRAY: SIMPAN RIWAYAT (TIDAK DISIMPAN KE SHAREDPREFERENCES)
    private void simpanRiwayat(String data) {
        if (jumlahRiwayat < riwayat.length) {
            riwayat[jumlahRiwayat] = data;
            jumlahRiwayat++;
        } else {
            for (int i = 0; i < riwayat.length - 1; i++) {
                riwayat[i] = riwayat[i + 1];
            }

            riwayat[riwayat.length - 1] = data;
        }
    }

    // SAMA DENGAN
    public void equals(View view) {
        if (container.isEmpty()) {
            return;
        }

        try {
            String ekspresi = container;
            double nilai = eval(ekspresi);

            if (Double.isInfinite(nilai) || Double.isNaN(nilai)) {
                txthasil.setText("Error");
                return;
            }

            String hasilTeks = formatHasil(nilai);
            txthasil.setText(hasilTeks);

            String ekspresiTampil = ekspresi.replace("*", "×");
            simpanRiwayat(ekspresiTampil + " = " + hasilTeks);
            tampilkanRiwayat();

            container = "";

        } catch (Exception e) {
            txthasil.setText("Error");
        }
    }

    // FORMAT HASIL
    private String formatHasil(double nilai) {
        if (nilai == (long) nilai) {
            return String.valueOf((long) nilai);
        }

        return String.valueOf(nilai);
    }

    // EVALUASI PERHITUNGAN
    public static double eval(final String str) {
        return new Object() {

            int pos = -1;
            int ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') {
                    nextChar();
                }

                if (ch == charToEat) {
                    nextChar();
                    return true;
                }

                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();

                if (pos < str.length()) {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                return x;
            }

            // TAMBAH DAN KURANG
            double parseExpression() {
                double x = parseTerm();

                for (;;) {
                    if (eat('+')) {
                        x += parseTerm();
                    } else if (eat('-')) {
                        x -= parseTerm();
                    } else {
                        return x;
                    }
                }
            }

            // KALI, BAGI, DAN MODULO
            double parseTerm() {
                double x = parseFactor();

                for (;;) {
                    while (ch == ' ') {
                        nextChar();
                    }

                    switch (ch) {
                        case '*':
                            nextChar();
                            x *= parseFactor();
                            break;

                        case '/':
                            nextChar();
                            x /= parseFactor();
                            break;

                        case '%':
                            nextChar();
                            x %= parseFactor();
                            break;

                        default:
                            return x;
                    }
                }
            }

            // ANGKA, TANDA MINUS, DAN PANGKAT
            double parseFactor() {
                if (eat('+')) {
                    return +parseFactor();
                }

                if (eat('-')) {
                    return -parseFactor();
                }

                double x;
                int startPos = this.pos;

                if (eat('(')) {
                    x = parseExpression();

                    if (!eat(')')) {
                        throw new RuntimeException("Missing ')'");
                    }

                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') {
                        nextChar();
                    }

                    x = Double.parseDouble(str.substring(startPos, this.pos));

                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) {
                    x = Math.pow(x, parseFactor());
                }

                return x;
            }

        }.parse();
    }

    // MENAMPILKAN RIWAYAT
    private void tampilkanRiwayat() {
        StringBuilder teks = new StringBuilder();

        if (jumlahRiwayat == 0) {
            txtriwayat.setText("Belum ada riwayat");
            return;
        }

        int i = jumlahRiwayat - 1;

        do {
            String data = riwayat[i];

            if (data == null || data.isEmpty()) {
                i--;
                continue;
            }

            teks.append(data).append("\n");
            i--;

        } while (i >= 0);

        txtriwayat.setText(teks.toString());
    }
}
