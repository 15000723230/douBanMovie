package example.com.mydbmv;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *    author :YangXiaoMeng
 *    e-mail :xmyang3@gc.omron.com
 *    time   :2018/09/07
 *    desc   :DouBanMovie
 *    version:1.0
 * </pre>
 */
public class MainActivity extends AppCompatActivity {

    ArrayList<MovieInfo> movieList = new ArrayList<>();
    RecyclerView recyclerView;
    MovieAdapter mMovieAdapter;
    List<InTheater.SubjectsBean> myMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_main_movies);
        MovieRepo movieRepo = new MovieRepo(getApplicationContext());
        movieList = movieRepo.getSaveInfoList();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (isNetworkOk()) {

            Toast.makeText(this, "net is ok", Toast.LENGTH_SHORT).show();
            requestNetwork();
            mMovieAdapter = new MovieAdapter(this, movieList);
            recyclerView.setAdapter(mMovieAdapter);
            mMovieAdapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                }
            });
        } else if ((movieList.size() != 0)) {

            mMovieAdapter = new MovieAdapter(this, movieList);
            recyclerView.setAdapter(mMovieAdapter);
            mMovieAdapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                }
            });
            Toast.makeText(this, "get data without network", Toast.LENGTH_SHORT).show();

        } else {
            mMovieAdapter = new MovieAdapter(this, movieList);
            recyclerView.setAdapter(mMovieAdapter);
            Toast.makeText(this, "can not get data", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isNetworkOk() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();

    }

    public void requestNetwork() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<InTheater> inTheaterCall = service.getInTheaters(0, 34);
        inTheaterCall.enqueue(new Callback<InTheater>() {

            @Override
            public void onResponse(@NonNull Call<InTheater> call, @NonNull Response<InTheater> response) {

                InTheater inTheater = response.body();
                assert inTheater != null;
                myMovieList = inTheater.getSubjects();

                MovieRepo movieRepo = new MovieRepo(getApplicationContext());
                MovieInfo movieInfo = new MovieInfo();
                if (movieList == null || movieList.size() == 0) {
                    for (int i = 0; i < 34; i++) {
                        movieInfo.setId(myMovieList.get(i).getId());
                        movieInfo.setYear(myMovieList.get(i).getYear());
                        movieInfo.setRating(String.valueOf(myMovieList.get(i).getRating().getAverage()));
                        movieInfo.setTitle(myMovieList.get(i).getTitle());
                        movieInfo.setVideo_url(myMovieList.get(i).getAlt());
                        movieInfo.setImage_url(myMovieList.get(i).getImages().getSmall());
                        movieRepo.insertData(movieInfo);
                    }

                } else {
                    for (int j = 0; j < 34; j++) {
                        movieInfo.setYear(myMovieList.get(j).getYear());
                        movieInfo.setTitle(myMovieList.get(j).getTitle());
                        movieInfo.setRating(String.valueOf(myMovieList.get(j).getRating().getAverage()));
                        movieInfo.setImage_url(myMovieList.get(j).getImages().getSmall());
                        movieInfo.setVideo_url(myMovieList.get(j).getAlt());
                        movieInfo.setId(myMovieList.get(j).getId());
                        movieRepo.update(movieInfo, myMovieList.get(j).getId());
                    }
                }
                movieList = movieRepo.getSaveInfoList();
                mMovieAdapter.update(movieList);
            }

            @Override
            public void onFailure(@NonNull Call<InTheater> call, @NonNull Throwable t) {
            }
        });

    }
}












