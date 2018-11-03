package com.example.android.movieapponetestone.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.movieapponetestone.R;
import com.example.android.movieapponetestone.api.App;
import com.example.android.movieapponetestone.db.AppDatabase;
import com.example.android.movieapponetestone.db.PopularDao;
import com.example.android.movieapponetestone.model.movies.Review;
import com.example.android.movieapponetestone.model.movies.ReviewResult;
import com.example.android.movieapponetestone.model.popular.Popular;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    public static final String KEY = "key";
    public static final String POS = "pos";

    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/w185";

    private TextView title;
    private TextView userRating;
    private TextView releaseDate;
    private TextView synopsis;
    private ImageView posterImage;
    private TextView bla;

//    private Button insert;
//    private Button delete;

    private FloatingActionButton fab;

    private int pos;
    public static final int KEY_K = 297761;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.model_detail);

        init();
        getRev();

        getData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void init() {

//        insert = findViewById(R.id.ins_btn);
//        delete = findViewById(R.id.del_btn);


        fab = findViewById(R.id.fab);

        title = findViewById(R.id.tv_title);
        userRating = findViewById(R.id.user_rating);
        releaseDate = findViewById(R.id.release_date);
        synopsis = findViewById(R.id.synopsis);
        posterImage = findViewById(R.id.poster_image);
        bla = findViewById(R.id.bla);
    }

    public static void newIntent(Context context, Popular popular, int pos) {
        Intent intent = new Intent(context, DetailActivity.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable(KEY, popular);
        intent.putExtra(POS, pos);
        intent.putExtras(bundle);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void getData() {
        final Popular data = (Popular) getIntent().getSerializableExtra(KEY);
        Intent intent = getIntent();
        pos = intent.getIntExtra(POS, 0);

        title.setText(data.getTitle());
        releaseDate.setText(data.getReleaseDate());
        synopsis.setText(data.getOverview());
        userRating.setText(String.valueOf(data.getVoteAverage()));

        Glide.with(this).load(IMAGE_URL + data.getPosterPath()).into(posterImage);


        AppDatabase db = App.getInstance().getDatabase();
        final PopularDao dao = db.popularDao();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dao.fab(data);
                Toast.makeText(DetailActivity.this, "Fav was added", Toast.LENGTH_LONG).show();
            }
        });

//        insert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dao.insert(data);
//                Toast.makeText(DetailActivity.this, "Fav was added", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dao.delete(data);
//                Toast.makeText(DetailActivity.this, "Fav was deleted", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void getRev() {
        App.getApi().getReviews(KEY_K, MainActivity.KEY).enqueue(new Callback<ReviewResult>() {
            @Override
            public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                if (pos > 5) {
                    pos = 0;
                    Review review = response.body().getResults().get(pos);
                    bla.setText(review.getContent());
                } else {
                    Review review = response.body().getResults().get(pos);
                    bla.setText(review.getContent());
                }
            }

            @Override
            public void onFailure(Call<ReviewResult> call, Throwable t) {

            }
        });
    }
}
