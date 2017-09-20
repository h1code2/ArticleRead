package huangtx.article.read.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import huangtx.article.read.R;
import huangtx.article.read.WebActivity;
import huangtx.article.read.customize.ListInfo;

/**
 * Created by huangtongx on 2017/5/19.
 */

public class PublicInfoAdapter extends RecyclerView.Adapter<PublicInfoAdapter.MyView> {

    private Context context;
    private List<ListInfo> list;
    private LayoutInflater mlayout;

    public PublicInfoAdapter(Context mContext, List<ListInfo> mList) {
        this.context = mContext;
        this.list = mList;
        this.mlayout = LayoutInflater.from(mContext);
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyView(mlayout.inflate(R.layout.main_rectcleriew_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyView holder, final int position) {

        holder.mType.setText(list.get(position).getType());
        holder.mTitle.setText(list.get(position).getTitle());
        holder.mSummary.setText(list.get(position).getSummary());
        holder.mLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("url", list.get(position).getUrl());
                intent.putExtra("title", list.get(position).getTitle());
                intent.setClass(context, WebActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class MyView extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mType;
        private TextView mSummary;
        private LinearLayout mLinear;

        MyView(View view) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.mTitle);
            mType = (TextView) view.findViewById(R.id.mType);
            mSummary = (TextView) view.findViewById(R.id.mSummary);

            mLinear = (LinearLayout) view.findViewById(R.id.mLinear);
        }
    }
}
