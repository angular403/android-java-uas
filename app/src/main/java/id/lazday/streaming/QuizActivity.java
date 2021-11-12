package id.lazday.streaming;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    TextView pertanyaan;
    RadioGroup rg;
    RadioButton pilihanA, pilihanB, pilihanC, pilihanD;
    int nomor = 0;

    public static int hasil, benar, salah;

    //    pertanyaan quiz
    String[] pertanyaan_quiz = new String[]{
            "1. Ibu Kota Negara Republik Indonesia Adalah ",
            "2. Presiden Pertama Negara Indonesia Adalah ",
            "3. Lambang Negara Indonesia Adalah ",
            "4. Lagu Kebangsaan Indonesia Adalah ",
            "5. Bendera Negara Indonesia Adalah "
    };


//    jawaban quiz

    String[] pilihan_jawaban = new String[]{
            "Bandung", "Jakarta", "Padang", "Medan",
            "Soekarno", "Habibie", "Megawati", "Jokowi",
            "Elang", "Harimau", "Gajah", "Garuda Pancasila",
            "Indonesia Merdeka", "Bhineka Tunggal Ika", "Indonesia Raya", "Majulah Negeriku",
            "Cokelat", "Putih Merah", "Orange", "Merah Putih",
    };

//    jawaban yang benar

    String[] jawaban_benar = new String[]{
            "Jakarta",
            "Soekarno",
            "Garuda Pancasila",
            "Indonesia Raya",
            "Merah Putih",
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        pertanyaan = (TextView) findViewById(R.id.pertanyaan);
        rg = (RadioGroup) findViewById(R.id.radio_group);
        pilihanA = (RadioButton) findViewById(R.id.pilihanA);
        pilihanB = (RadioButton) findViewById(R.id.pilihanB);
        pilihanC = (RadioButton) findViewById(R.id.pilihanC);
        pilihanD = (RadioButton) findViewById(R.id.pilihanD);

        pertanyaan.setText(pertanyaan_quiz[nomor]);
        pilihanA.setText(pilihan_jawaban[0]);
        pilihanB.setText(pilihan_jawaban[1]);
        pilihanC.setText(pilihan_jawaban[2]);
        pilihanD.setText(pilihan_jawaban[3]);

        rg.check(0);
        benar = 0;
        salah = 0;
    }

    public void next(View view) {
        if (pilihanA.isChecked() || pilihanB.isChecked() || pilihanC.isChecked() || pilihanD.isChecked()) {
            RadioButton jawaban_user = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
            String ambil_jawaban_user = jawaban_user.getText().toString();
            rg.check(0);
            if (ambil_jawaban_user.equalsIgnoreCase(jawaban_benar[nomor])) benar++;
            else salah++;
            nomor++;
            if (nomor < pertanyaan_quiz.length) {
                pertanyaan.setText(pertanyaan_quiz[nomor]);
                pilihanA.setText(pilihan_jawaban[(nomor * 4) + 0]);
                pilihanB.setText(pilihan_jawaban[(nomor * 4) + 1]);
                pilihanC.setText(pilihan_jawaban[(nomor * 4) + 2]);
                pilihanD.setText(pilihan_jawaban[(nomor * 4) + 3]);

            } else {
                hasil = benar * 20;
                Intent selesai = new Intent(getApplicationContext(), HasilQuiz.class);
                startActivity(selesai);
            }
        }else{
            Toast.makeText(this, "Pilih Jawaban Terlebih Dahulu", Toast.LENGTH_SHORT).show();
        }
    }
}