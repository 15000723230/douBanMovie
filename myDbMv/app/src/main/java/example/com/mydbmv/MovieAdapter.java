package example.com.mydbmv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * <pre>
 *    author :YangXiaomeng
 *    e-mail :xmyang3@gc.omron.com
 *    time   :2018/09/07
 *    desc   :DouBanMovie
 *    version:1.0
 * </pre>
 */

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<MovieInfo> movieList;
    private OnItemClickListener onItemClickListener;

    MovieAdapter(Context context, ArrayList<MovieInfo> movieList) {
        this.mContext = context;
        this.movieList = movieList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_main_movie, parent,
                false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {
        ((ViewHolder) holder).ratingView.setText("评分：" + movieList.get(position).getRating());
        ((ViewHolder) holder).titleView.setText("片名：" + movieList.get(position).getTitle());
        ((ViewHolder) holder).releaseDayView.setText("上映日期:" + movieList.get(position)
                .getYear());
        Glide
                .with(mContext)
                .load(movieList.get(position).getImage_url())
                .into(((ViewHolder) holder).imageView);
        ((ViewHolder) holder).itemView.setTag(position);
        //set click ID
        ((ViewHolder) holder).mLayout.setOnClickListener(new View.OnClickListener() {
            //set a binding listener for each item
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    final Bitmap bitmap = setImage(((ViewHolder) holder).imageView);
                    //invoke the method of setImage，and get the return value of Bitmap
                    Intent intent = new Intent("android.intent.action.CALL");
                    intent.putExtra("Score", movieList.get(position).getRating());
                    intent.putExtra("Title", movieList.get(position).getTitle());
                    intent.putExtra("Date", movieList.get(position).getYear());
                    intent.putExtra("Alt", movieList.get(position).getVideo_url());
                    intent.putExtra("ID", movieList.get(position).getId());
                    intent.putExtra("image", bitmap);
                    Log.e("url", "(Integer) v.getTag()==" + v.getTag());
                    mContext.startActivity(intent);
                    onItemClickListener.onItemClick(v, (Integer) v.getTag());
                }
            }
        });
    }

    /**
     * @param movieList Data get from the sever
     */
    public void update(ArrayList<MovieInfo> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * @param view imageViews in the main activity
     * @return the bitmap of pictures in the main activity
     */
    private Bitmap setImage(ImageView view) {
        Bitmap image = ((BitmapDrawable) view.getDrawable()).getBitmap();
        return Bitmap.createBitmap(image);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        TextView ratingView;
        TextView releaseDayView;
        ImageView imageView;
        View mLayout;

        /**
         * @param itemView views on the item
         */
        ViewHolder(final View itemView) {
            super(itemView);
            mLayout = itemView;
            titleView = itemView.findViewById(R.id.tv_item_title);
            ratingView = itemView.findViewById(R.id.tv_item_rating);
            releaseDayView = itemView.findViewById(R.id.tv_item_releaseDay);
            imageView = itemView.findViewById(R.id.iv_main_item);
        }
        //the creator of viewHolder
    }

    @Override
    public int getItemCount() {
        if (movieList == null) return 0;
        return movieList.size();
    }
}
