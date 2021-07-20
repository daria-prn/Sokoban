package nz.ac.arastudent.dariap.sokoban;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import sokoban.SokobanController;

public class LevelChooserActivity extends AppCompatActivity implements View.OnClickListener  {
    SokobanController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_chooser);
        controller = SokobanController.create();
        createLevelButtons();
    }

    @Override
    public void onClick(View v) {
    }
    public void createLevelButtons(){
        LinearLayout levelList = findViewById(R.id.level_list);
        for (int i=0; i < 5; i++) {
            levelList.addView(this.makeButton(i));
        }
    }

    public View makeButton(int id){
        Button button = new Button(this);
        String buttonName = "Level " .concat(String.valueOf(id+1));
        button.setText(buttonName);
        Integer id1 = new Integer(id);
        final int finalId = id1;
        button.setOnClickListener(view -> startLevel(finalId));
        return button;
    }

    public void startLevel(int id){
        controller.loadLevel(id);
        Intent intent = new Intent(this, LevelActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

