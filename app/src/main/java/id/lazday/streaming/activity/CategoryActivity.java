package id.lazday.streaming.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.List;

import id.lazday.streaming.R;
import id.lazday.streaming.adapter.CategoryAdapter;
import id.lazday.streaming.data.model.CallResponse;
import id.lazday.streaming.data.model.Category;
import id.lazday.streaming.rest.Api;
import id.lazday.streaming.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    private ListView listView;

    private void getCategory(){
        ApiInterface apiService = Api.getUrl().create(ApiInterface.class);

        Call<CallResponse> call  = apiService.getCategory();
        call.enqueue(new Callback<CallResponse>() {
            @Override
            public void onResponse(Call<CallResponse> call, Response<CallResponse> response) {
                Log.e("_logCat", response.toString());
                final List<Category> categories = response.body().getCategories();

                listView.setAdapter(new CategoryAdapter( CategoryActivity.this, categories ));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getApplicationContext(), categories.get(position).getCategory() + " selected",
                                Toast.LENGTH_LONG).show();
                        MainActivity.VIDEO_CATEGORY = categories.get(position).getCategory();
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(Call<CallResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("_TAG", t.toString());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        listView    = findViewById(R.id.listView);
        getCategory();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Kategori");
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
