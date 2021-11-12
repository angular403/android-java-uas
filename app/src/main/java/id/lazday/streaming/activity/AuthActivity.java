package id.lazday.streaming.activity;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import java.util.HashMap;
import java.util.List;

import id.lazday.streaming.R;
import id.lazday.streaming.data.SessionManager;
import id.lazday.streaming.data.model.CallResponse;
import id.lazday.streaming.data.model.User;
import id.lazday.streaming.rest.Api;
import id.lazday.streaming.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {

    private ProgressBar progress_bar;
    private String ANDROID_ID;

    SessionManager session;

    private void Auth(){
        ApiInterface apiService = Api.getUrl().create(ApiInterface.class);

        Call<CallResponse> call  = apiService.getAuth(ANDROID_ID);
        call.enqueue(new Callback<CallResponse>() {
            @Override
            public void onResponse(Call<CallResponse> call, Response<CallResponse> response) {
                Log.e("_logUser", response.toString());
                Log.e("_logResponse", response.body().getResponse());
                final List<User> users = response.body().getUsers();

                if (response.body().getResponse().equals("success")){
                    progress_bar.setVisibility(View.GONE);
                    startActivity(new Intent(AuthActivity.this, MainActivity.class));
                }
//                for (int i = 0; users.size() > i; i++){ Log.e("_logUser", users.get(i).getAndroid_id()); }
            }

            @Override
            public void onFailure(Call<CallResponse> call, Throwable t) {
                Log.e("_logErr", t.toString());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        progress_bar = findViewById(R.id.progress_bar);
        ANDROID_ID = Settings.Secure.getString(this.getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
//        Log.e("_logAndroidId", ANDROID_ID);

        session = new SessionManager(this);
        session.checkExist();
        HashMap<String, String> users = session.getData();
//        Log.e("_logAndroidId", users.get(session.ANDROID_ID));

        if (users.get(session.ANDROID_ID) == null && ANDROID_ID != null){
            session.createSession( ANDROID_ID );
            Auth();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        finish();
    }
}
