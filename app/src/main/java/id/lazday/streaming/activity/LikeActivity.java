package id.lazday.streaming.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;

import id.lazday.streaming.R;
import id.lazday.streaming.adapter.LikeAdapter;
import id.lazday.streaming.data.SessionManager;
import id.lazday.streaming.data.model.CallResponse;
import id.lazday.streaming.data.model.Like;
import id.lazday.streaming.rest.Api;
import id.lazday.streaming.rest.ApiInterface;
import id.lazday.streaming.util.MenuDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikeActivity extends AppCompatActivity {

    private ListView list_like;
    private TextView text_notif;
    private ProgressBar progress_bar;

    SessionManager session;
    HashMap<String, String> users;

    private void getList(){
        ApiInterface apiService = Api.getUrl().create(ApiInterface.class);

        Log.e("_logID", users.get(session.ANDROID_ID));
        Call<CallResponse> call  = apiService.getLike(users.get(session.ANDROID_ID));
        call.enqueue(new Callback<CallResponse>() {
            @Override
            public void onResponse(Call<CallResponse> call, Response<CallResponse> response) {

                Log.e("_logLike", response.toString());
                final List<Like> likes = response.body().getLikes();

                Log.e("_logSize", String.valueOf(likes.size()) );
                if (likes.size() > 0) {
                    progress_bar.setVisibility(View.GONE);
                    text_notif.setVisibility(View.GONE);

                    list_like.setVisibility(View.VISIBLE);
                    list_like.setAdapter(new LikeAdapter( LikeActivity.this, likes ));
                    list_like.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            MenuDialog dialog = new MenuDialog();
                            dialog.showDialog( LikeActivity.this, likes.get(position).getList_id(),
                                    likes.get(position).getFilename(), false );
                        }
                    });

                } else {
                    progress_bar.setVisibility(View.GONE);
                    list_like.setVisibility(View.GONE);

                    text_notif.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<CallResponse> call, Throwable t) {
                Log.e("_TAG", t.toString());
                progress_bar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        session = new SessionManager(this);
        users = session.getData();

        list_like = findViewById(R.id.list_like);
        progress_bar    = findViewById(R.id.progress_bar);
        text_notif      = findViewById(R.id.text_notif);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Like");
    }

    @Override
    public void onResume(){
        super.onResume();
        getList();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
