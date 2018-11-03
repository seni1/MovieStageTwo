package com.example.android.movieapponetestone.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.movieapponetestone.R;
import com.example.android.movieapponetestone.adapter.ModelAdapter;
import com.example.android.movieapponetestone.api.App;
import com.example.android.movieapponetestone.db.AppDatabase;
import com.example.android.movieapponetestone.db.PopularDao;
import com.example.android.movieapponetestone.model.popular.Popular;
import com.example.android.movieapponetestone.model.popular.PopularResult;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Popular> popularArrayList;

    public static final String KEY = "5571cb8edc0c8203b51ddfb985abd954";

    private RecyclerView recyclerView;
    private ModelAdapter adapter;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.model_list);

        recyclerView = findViewById(R.id.rv_model);
        emptyView = findViewById(R.id.empty_view);

        popularArrayList = new ArrayList<>();

        adapter = new ModelAdapter(popularArrayList, getApplicationContext());

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

        getMovies(App.getApi().getPopularMovies(KEY), "Sorry, ... Internet to view the most popular movies.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_menu, menu);
        return true;
    }

    private void emptyView() {
        if (popularArrayList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
                getMovies(App.getApi().getPopularMovies(KEY), "Sorry, ... Internet to view the most popular movies.");
                return true;
            case R.id.top_rated:
                getMovies(App.getApi().getTopRatedMovies(KEY), "Sorry, ... Internet to view the most top rated movies.");
                return true;
            case R.id.fav:
                AppDatabase db = App.getInstance().getDatabase();
                final PopularDao dao = db.popularDao();
                popularArrayList.clear();
                adapter.setData(dao.getAll());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getMovies(Call<PopularResult> resultCall, final String message) {
        resultCall.enqueue(new Callback<PopularResult>() {
            @Override
            public void onResponse(Call<PopularResult> call, Response<PopularResult> response) {
                popularArrayList.clear();
                adapter.setData(response.body().getResults());
            }

            @Override
            public void onFailure(Call<PopularResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                emptyView();
            }
        });
    }


}
