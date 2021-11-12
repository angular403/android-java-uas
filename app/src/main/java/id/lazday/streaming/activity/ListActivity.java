package id.lazday.streaming.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.List;

import id.lazday.streaming.R;
import id.lazday.streaming.adapter.ListAdapter;
import id.lazday.streaming.data.model.CallResponse;
import id.lazday.streaming.data.model.ListVideo;
import id.lazday.streaming.data.model.Video;
import id.lazday.streaming.rest.Api;
import id.lazday.streaming.rest.ApiInterface;
import id.lazday.streaming.data.Constant;
import id.lazday.streaming.util.ImgDownload;
import id.lazday.streaming.util.MenuDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    private ListView list_episode;
    private ProgressBar progress_bar;
    private ImageView img_view;
    private TextView text_notif, text_summary, text_eps, text_show;

    private void getList(){
        ApiInterface apiService = Api.getUrl().create(ApiInterface.class);
        Log.e("_logVideoId", bundle.getString("VIDEO_ID"));
        Call<CallResponse> call  = apiService.getList(bundle.getString("VIDEO_ID"));
        call.enqueue(new Callback<CallResponse>() {
            @Override
            public void onResponse(Call<CallResponse> call, Response<CallResponse> response) {
                Log.e("_logCat", response.toString());
                final List<ListVideo> lists = response.body().getLists();

                Log.e("_logSize", String.valueOf(lists.size()) );
                if (lists.size() > 0) {
                    progress_bar.setVisibility(View.GONE);
                    list_episode.setVisibility(View.VISIBLE);

                    list_episode.setAdapter(new ListAdapter( ListActivity.this, lists ));
                    list_episode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            MenuDialog dialog = new MenuDialog();
                            dialog.showDialog( ListActivity.this, lists.get(position).getList_id(),
                                    lists.get(position).getFilename(), true );
                        }
                    });

                    text_eps.setText(String.valueOf( lists.size() ) + " videos");

                } else {
                    progress_bar.setVisibility(View.GONE);
                    text_notif.setVisibility(View.VISIBLE);
                }

                final List<Video> videos = response.body().getVideos();
                for (int i=0; i < videos.size(); i++){
                    text_summary.setText(
                            videos.get(i).getSummary() + " \n\n#" +
                                    videos.get(i).getCategory().replace(", ", " #")
                    );
                    ImgDownload.Picasso(Constant.COVER_PATH + videos.get(i).getCover() , img_view);

                }
            }

            @Override
            public void onFailure(Call<CallResponse> call, Throwable t) {
                Log.e("_TAG", t.toString());
                progress_bar.setVisibility(View.GONE);
            }
        });
    }

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bundle = getIntent().getExtras();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle( bundle.getString("VIDEO_TITLE") );

        list_episode    = findViewById(R.id.list_episode);
        progress_bar    = findViewById(R.id.progress_bar);
        img_view        = findViewById(R.id.imageviewplaceholder);
        text_notif      = findViewById(R.id.text_notif);
        text_summary    = findViewById(R.id.text_summary);
        text_eps        = findViewById(R.id.text_eps);
        text_show       = findViewById(R.id.text_show);

        text_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text_show.getText().toString().equals("More")){
                    text_show.setText("Less");
                    text_summary.setSingleLine(false);
                } else {
                    text_show.setText("More");
                    text_summary.setSingleLine(true);
                }
            }
        });

        getList();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
