package com.zhuandian.health;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.zhuandian.health.activity.NewScheduleActivity;
import com.zhuandian.health.adapter.ScheduleAdapter;
import com.zhuandian.health.db.DBManager;
import com.zhuandian.health.entity.ScheduleEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_new_schedule)
    TextView tvNewSchedule;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    private ScheduleAdapter adapter;
    List<ScheduleEntity> allData = new ArrayList<>();

    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        allData = DBManager.getInstance().getAllData();
        adapter = new ScheduleAdapter(allData, this);
        rvList.setAdapter(adapter);
        adapter.setLongClickListener(new ScheduleAdapter.OnItemLongClickListener() {
            @Override
            public void longClick(ScheduleEntity entity) {
                DBManager.getInstance().deleteDataByTitle(entity.getTitle());
                initDataList();
                Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
            }
        });
        rvList.setLayoutManager(new LinearLayoutManager(this));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                    initDataList();
                }
            }
        });
        initDataList();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {


                Calendar now = Calendar.getInstance();

                int hour = now.get(Calendar.HOUR_OF_DAY);
                int minute = now.get(Calendar.MINUTE);

                Date d = new Date();
                System.out.println(d);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
                String dateNowStr = sdf.format(d);

                List<ScheduleEntity> allData = DBManager.getInstance().getDataByTime(dateNowStr, hour + "", minute + "");
                if (allData.size() > 0) {
                    ScheduleEntity scheduleEntity = allData.get(0);
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           new AlertDialog.Builder(MainActivity.this)
                                   .setTitle(scheduleEntity.getTitle())
                                   .setCancelable(false)
                                   .setMessage(scheduleEntity.getContent())
                                   .setPositiveButton("我知道啦", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {
                                           dialog.dismiss();
                                       }
                                   })
                                   .show();
                       }
                   });
                }


            }
        }, 1000, 3000);

    }

    private void initDataList() {
        allData.clear();
        List<ScheduleEntity> allData = DBManager.getInstance().getAllData();
        Collections.reverse(allData);
        this.allData.addAll(allData);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tv_new_schedule)
    public void onViewClicked() {
        startActivity(new Intent(this, NewScheduleActivity.class));
    }


}
