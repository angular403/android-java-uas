package id.lazday.streaming;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HasilQuiz extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_quiz);

        TextView hasil = (TextView)findViewById(R.id.hasil);
        TextView nilai = (TextView)findViewById(R.id.nilai);

        hasil.setText("Jawaban Benar : " +QuizActivity.benar+ "\nJawaban Salah : " + QuizActivity.salah);
        nilai.setText("" + QuizActivity.hasil);
    }

    public void ulangin(View view){
        finish();
        Intent i = new Intent(getApplicationContext(),QuizActivity.class);
        startActivity(i);
    }
}
