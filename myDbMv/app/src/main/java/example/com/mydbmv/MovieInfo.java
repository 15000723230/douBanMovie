package example.com.mydbmv;

/**
 *<pre>
 *    author :YangXiaomeng
 *    e-mail :xmyang3@gc.omron.com
 *    time   :2018/09/07
 *    desc   :DouBanMovie
 *    version:1.0
 *</pre>
 */

public class MovieInfo {
    public static final String TABLE_NAME="movies";
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_IMAGE_URL = "image_url";
    public static final String KEY_VIDEO_URL = "video_url";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_RATING = "rating";
    public static final String KEY_YEAR = "year";

    public String id;
    public String title;
    public String image_url;
    public String video_url;
    public String content;
    public String rating;
    public String year;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public String getContent() {
        return content;
    }

    public String getRating() {
        return rating;
    }

    public String getYear() {
        return year;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
