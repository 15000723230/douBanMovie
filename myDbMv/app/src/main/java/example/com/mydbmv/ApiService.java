package example.com.mydbmv;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
/**
 *<pre>
 *    author :YangXiaomeng
 *    e-mail :xmyang3@gc.omron.com
 *    time   :2018/09/07
 *    desc   :DouBanMovie
 *    version:1.0
 *</pre>
 */


public interface ApiService {
    @GET("v2/movie/in_theaters")
    Call<InTheater>getInTheaters (@Query("start") int start,@Query("count") int count);
    @GET("v2/movie/subject/{id}")
    Call<DetailContent>getContent(@Path("id") String id);

}

