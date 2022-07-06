package com.example.myapplication.rank;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.main.GameActivity;
import com.example.myapplication.R;
import com.example.myapplication.dao.Record;
import com.example.myapplication.dao.RecordDaoImple;
import com.example.myapplication.login.LoginActivity;

import java.util.List;

public class RankActivity extends AppCompatActivity {
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;
    private static int score = 0;
    private static RecordDaoImple recordDaoImple;
    ListView listView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        listView = findViewById(R.id.rankList);
        TextView title = findViewById(R.id.rank_title);
        if(GameActivity.whichMode == "simple" ){
            recordDaoImple = new RecordDaoImple("neweasy.txt",this);
            title.setText("简单模式排行榜");
        }else if(GameActivity.whichMode == "normal"){
            recordDaoImple = new RecordDaoImple("newnormal.txt",this);
            title.setText("普通模式排行榜");
        }else{
            recordDaoImple = new RecordDaoImple("newhard.txt",this);
            title.setText("困难模式排行榜");
        }
        DisplayMetrics dm = getResources().getDisplayMetrics();
        WINDOW_WIDTH = dm.widthPixels;
        WINDOW_HEIGHT = dm.heightPixels;

        String username = LoginActivity.userNameOutput;
        Record record;
        if(username != null){
            record = new Record(username,GameActivity.gameView.getScore());
        }
        else {
            record = new Record("visitor",GameActivity.gameView.getScore());
        }

        recordDaoImple.addRecord(record);
        List<Record> tableTata = recordDaoImple.printRecord();
//        Record record = new Record("hhh",100);
//        List<Record> test = new ArrayList<>();
//        test.add(record);
        RankAdapter rankAdapter = new RankAdapter(RankActivity.this,R.layout.rank_list,tableTata);
        listView.setAdapter(rankAdapter);

//        Button playAgainButton = findViewById(R.id.againButton);
//        playAgainButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(RankActivity.this, GameActivity.class);
//                startActivity(intent);
//            }
//        });

        getSupportActionBar().hide();
    }
}