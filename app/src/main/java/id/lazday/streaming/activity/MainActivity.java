package id.lazday.streaming.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.List;

import id.lazday.streaming.R;
import id.lazday.streaming.adapter.MainAdapter;
import id.lazday.streaming.data.model.CallResponse;
import id.lazday.streaming.data.model.Video;
import id.lazday.streaming.rest.Api;
import id.lazday.streaming.rest.ApiInterface;
import id.lazday.streaming.util.AppShare;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Menu menu;

    private GridView grid_movie;
    private EditText edit_search;
    private ProgressBar progress_bar;
    private TextView text_notif;

    boolean grid   = true;

    public static String VIDEO_CATEGORY;

    private void getVideo(String title_search, String category){
        progress_bar.setVisibility(View.VISIBLE);
        ApiInterface apiService = Api.getUrl().create(ApiInterface.class);

        Call<CallResponse> call;
        if (category == ""){
            call= apiService.getVideo(title_search);
        } else {
            call= apiService.postCategory(category);
        }
        call.enqueue(new Callback<CallResponse>() {
            @Override
            public void onResponse(Call<CallResponse> call, Response<CallResponse> response) {
                Log.e("_logVid", response.toString());
                final List<Video> videos = response.body().getVideos();

                if (videos.size() > 0){
                    text_notif.setVisibility(View.GONE);
                    progress_bar.setVisibility(View.GONE);

                    grid_movie.setVisibility(View.VISIBLE);
                    grid_movie.setAdapter(new MainAdapter( MainActivity.this, videos ));
                    grid_movie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            Log.e("_logVideoId",videos.get(position).getVideo_id());
                            Intent intent = new Intent( MainActivity.this, ListActivity.class );
                            intent.putExtra("VIDEO_TITLE", videos.get(position).getTitle());
                            intent.putExtra("VIDEO_ID", videos.get(position).getVideo_id());
                            startActivity(intent);
                        }
                    });
                } else {
                    progress_bar.setVisibility(View.GONE);
                    grid_movie.setVisibility(View.GONE);

                    text_notif.setVisibility(View.VISIBLE);
                }

                for (int i = 0; videos.size() > i; i++){ Log.e("_logVideo", videos.get(i).getCreated()); }
            }

            @Override
            public void onFailure(Call<CallResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("_logErr", t.toString());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        VIDEO_CATEGORY = "";

        grid_movie      = findViewById(R.id.grid_movie);
        progress_bar    = findViewById(R.id.progress_bar);
        text_notif      = findViewById(R.id.text_notif);

        progress_bar.setVisibility(View.GONE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        edit_search = findViewById(R.id.edit_search);
        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Log.e("_logSearch", edit_search.getText().toString());
                    getVideo(edit_search.getText().toString(), "");
                    return true;
                }
                return false;
            }
        });

        getVideo("", "");
    }

    @Override
    protected void onResume(){
        // TODO Auto-generated method stub
        super.onResume();

        if (!VIDEO_CATEGORY.equals("")){
            edit_search.setText(""); getVideo("", VIDEO_CATEGORY);
            VIDEO_CATEGORY = "";
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_list) {

            if (grid){
                grid_movie.setNumColumns(1);
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_menu_grid));
                grid = false;
            } else {
                grid_movie.setNumColumns(2);
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_menu_list));
                grid = true;
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            edit_search.setText(""); getVideo("", "");
        } else if (id == R.id.nav_category) {
            startActivity(new Intent(MainActivity.this, CategoryActivity.class));
        } else if (id == R.id.nav_like) {
            startActivity(new Intent(MainActivity.this, LikeActivity.class));
        } else if (id == R.id.nav_store) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://tokopedia.link/YA7pjaOsrO"));
            startActivity(browserIntent);
        } else if (id == R.id.nav_share) {
            AppShare.share(MainActivity.this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
