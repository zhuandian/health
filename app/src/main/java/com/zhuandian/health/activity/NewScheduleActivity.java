package com.zhuandian.health.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.zhuandian.health.R;
import com.zhuandian.health.db.DBManager;
import com.zhuandian.health.entity.ScheduleEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewScheduleActivity extends AppCompatActivity {

    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.rb_type1)
    RadioButton rbType1;
    @BindView(R.id.rb_type2)
    RadioButton rbType2;
    @BindView(R.id.rb_type3)
    RadioButton rbType3;
    @BindView(R.id.rb_type4)
    RadioButton rbType4;
    @BindView(R.id.timepicker)
    TimePicker timepicker;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.calendarview)
    CalendarView calendarView;

    private int type = 1;
    private String day;
    private int hour=0;
    private int minute=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);
        ButterKnife.bind(this);

        timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                NewScheduleActivity.this.minute = minute;
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                day = String.format("%s-%s-%s", year, month+1, dayOfMonth);
            }
        });
    }

    @OnClick({R.id.rb_type1, R.id.rb_type2, R.id.rb_type3, R.id.rb_type4, R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_type1:
                type = 1;
                break;
            case R.id.rb_type2:
                type = 2;
                break;
            case R.id.rb_type3:
                type = 3;
                break;
            case R.id.rb_type4:
                type = 4;
                break;
            case R.id.tv_commit:
                insertData2DB();
                break;
        }
    }

    private void insertData2DB() {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
            Toast.makeText(this, "请完善输入信息...", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(day) || hour == 0 || minute == 0) {
            Toast.makeText(this, "请选择时间日期...", Toast.LENGTH_SHORT).show();
            return;
        }
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setType(type);
        scheduleEntity.setTitle(title);
        scheduleEntity.setContent(content);
        scheduleEntity.setHour(hour);
        scheduleEntity.setMinute(minute);
        scheduleEntity.setDay(day);

        DBManager.getInstance().insertData(scheduleEntity);

        Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();
        finish();

    }
}
