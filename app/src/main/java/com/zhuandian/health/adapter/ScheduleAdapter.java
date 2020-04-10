package com.zhuandian.health.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhuandian.health.R;
import com.zhuandian.health.db.DBManager;
import com.zhuandian.health.entity.ScheduleEntity;

import org.w3c.dom.Text;

import java.util.List;

/**
 * @author xiedong
 * @desc
 * @date 2020-04-10.
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private List<ScheduleEntity> mDatas;
    private Context context;
    private OnItemLongClickListener longClickListener;

    public void setLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public ScheduleAdapter(List<ScheduleEntity> mDatas, Context context) {
        this.mDatas = mDatas;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(mDatas.get(position).getTitle());
        holder.tvContent.setText(mDatas.get(position).getContent());

        holder.tvTime.setText(mDatas.get(position).getDay()+ "  "+mDatas.get(position).getHour()+":"+mDatas.get(position).getMinute());

        String type = "";
        switch (mDatas.get(position).getType()) {
            case 1:
                type = "作息时间提醒";
                break;
            case 2:
                type = "用餐时间禁忌提醒";
                break;
            case 3:
                type = "服药时间剂量提醒";
                break;
            case 4:
                type = "运动康复提醒";
                break;
            default:
                type = "";
                break;
        }

        holder.tvType.setText(type);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener != null) {
                    longClickListener.longClick(mDatas.get(position));
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvContent;
        TextView tvTime;
        TextView tvType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvType = itemView.findViewById(R.id.tv_type);
        }
    }


    public interface OnItemLongClickListener {
        void longClick(ScheduleEntity entity);
    }
}
