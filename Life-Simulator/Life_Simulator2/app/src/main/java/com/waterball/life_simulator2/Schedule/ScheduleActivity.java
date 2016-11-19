package com.waterball.life_simulator2.Schedule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.waterball.life_simulator2.DB_Facades.Schedule_DB_Facade;
import com.waterball.life_simulator2.Items.Schedule;
import com.waterball.life_simulator2.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {
    private List<Schedule> schedules;
    private Schedule_DB_Facade schedule_db_facade;

    private void init(){
        schedules = Collections.checkedList(new ArrayList<Schedule>(),Schedule.class);
        schedule_db_facade = (Schedule_DB_Facade) Schedule_DB_Facade.getFacade();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        init();
    }
}
