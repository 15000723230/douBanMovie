package example.com.mydbmv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *    author :YangXiaomeng
 *    e-mail :xmyang3@gc.omron.com
 *    time   :2018/09/07
 *    desc   :DouBanMovie
 *    version:1.0
 * </pre>
 */

public class DetailActivity extends AppCompatActivity {
    String id;
    String content;
    String video_url;
    TextView summary;
    TextView movieTitle;
    TextView rating;
    TextView releasingDay;
    ImageView imageView;
    Button myBtn;
    Context context;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        context = this;
        initView();
        initData();
        summary.setMovementMethod(ScrollingMovementMethod.getInstance());
        MovieRepo movieRepo = new MovieRepo(context);
        MovieInfo movieInfo = movieRepo.getSaveInfoById(Integer.parseInt(id));
        String detailList = movieInfo.content;
        if (isNetworkOk()) {
            requestNetwork();
            Toast.makeText(this, "有网络！", Toast.LENGTH_SHORT).show();
        } else if (detailList != null) {
            summary.setText("电影详情：" + movieInfo.content);
            Toast.makeText(this, "没有网络但是数据库有数据！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "获取不到数据！", Toast.LENGTH_SHORT).show();
        }
        skip();
    }

    @SuppressLint("SetTextI18n")
    public void initData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        Double score = intent.getDoubleExtra("Score", 0.0);
        String title = intent.getStringExtra("Title");
        String date = intent.getStringExtra("Date");
        Bitmap bitmap = intent.getParcelableExtra("image");
        video_url = intent.getStringExtra("Alt");
        rating.setText("评分;" + score.toString());
        movieTitle.setText("片名；" + title);
        releasingDay.setText("上映日期:" + date);
        imageView.setImageBitmap(bitmap);
    }

    public void initView() {
        summary = findViewById(R.id.tv_detail_content);
        movieTitle = findViewById(R.id.tv_detail_title);
        rating = findViewById(R.id.tv_detail_rating);
        releasingDay = findViewById(R.id.tv_detail_releaseDay);
        imageView = findViewById(R.id.iv_detail);
        myBtn = findViewById(R.id.btn_detail_skip);
    }

    public void requestNetwork() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<DetailContent> detailContentCall = apiService.getContent(id);
        detailContentCall.enqueue(new Callback<DetailContent>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<DetailContent> call, @NonNull Response<DetailContent> response) {
                DetailContent detailContent = response.body();
                assert detailContent != null;
                content = detailContent.getSummary();
                MovieInfo movieInfo = new MovieInfo();
                movieInfo.setContent(content);
                MovieRepo movieRepo = new MovieRepo(context);
                movieRepo.update(movieInfo, id);
                summary.setText("电影详情：" + content);
            }

            @Override
            public void onFailure(@NonNull Call<DetailContent> call, @NonNull Throwable t) {
            }
        });

    }

    public boolean isNetworkOk() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void skip() {
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                final MovieInfo movieInfo = new MovieInfo();
                movieInfo.video_url = String.valueOf(video_url);
                Uri content_url = Uri.parse(movieInfo.video_url);//此处填链接
                intent.setData(content_url);
                context.startActivity(intent);
            }
        });

    }
}
