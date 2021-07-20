package nz.ac.arastudent.dariap.sokoban;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

import sokoban.SokobanController;

public class LevelActivity extends AppCompatActivity {
    SokobanController controller;
    ImageButton pauseGame;
    ImageButton resumeGame;
    ImageButton soundOff;
    ImageButton soundOn;
    boolean musicOff;
    MediaPlayer mainSound;
    MediaPlayer levelCompleteSound;
    TextView timer;
    long startTime;
    long pausedTime;
    final Handler timerHandler = new Handler();
    final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis()- startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            timer.setText(String.format("%d:%02d", minutes, seconds));
            timerHandler.postDelayed(this, 500);
            if (controller.levelCompleted()) {
                timerHandler.removeCallbacksAndMessages(null);
                mainSound.stop();
                levelCompleteSound.start();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        controller = SokobanController.create();

        TextView levelNameLabel = findViewById(R.id.level_name_label);
        String currentLevelName = controller.getCurrentLevelName();
        levelNameLabel.setText(currentLevelName);
        updateLevel();

        Button button_up = findViewById(R.id.btn_up);
        button_up.setOnClickListener(view -> move("up"));
        Button button_left = findViewById(R.id.btn_left);
        button_left.setOnClickListener(view -> move("left"));
        Button button_down = findViewById(R.id.btn_down);
        button_down.setOnClickListener(view -> move("down"));
        Button button_right = findViewById(R.id.btn_right);
        button_right.setOnClickListener(view -> move("right"));

        startTime = System.currentTimeMillis();
        timer = findViewById(R.id.timer);
        timerHandler.postDelayed(timerRunnable, 0);

         pauseGame = findViewById(R.id.imageButton_pause);
        resumeGame = findViewById(R.id.imageButton_resume);

         pauseGame.setOnClickListener(view -> {
             pauseGame.setVisibility(View.GONE);
             resumeGame.setVisibility(View.VISIBLE);

             pauseGame();

             timerHandler.removeCallbacksAndMessages(null);
             pausedTime = System.currentTimeMillis();
             });

         resumeGame.setOnClickListener(view ->{
             resumeGame.setVisibility(View.GONE);
             pauseGame.setVisibility(View.VISIBLE);

             resumeGame();

             startTime = System.currentTimeMillis() - (pausedTime-startTime);
             timerHandler.postDelayed(timerRunnable,0);
        });
        mainSound = MediaPlayer.create(this, R.raw.main);
        mainSound.setLooping(true);
        mainSound.setVolume(1.5f, 1.5f);
        mainSound.start();
        musicOff = false;
        levelCompleteSound = MediaPlayer.create(this, R.raw.level_complete);

        soundOff = findViewById(R.id.imageButton_soundOff);
        soundOn = findViewById(R.id.imageButton_soundOn);

        soundOff.setOnClickListener(view -> enableSound());

        soundOn.setOnClickListener(view -> disableSound());
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumeGame();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startTime = System.currentTimeMillis() - (pausedTime-startTime);
        timerHandler.postDelayed(timerRunnable, 0);
        resumeGame();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pausedTime = System.currentTimeMillis();
        timerHandler.removeCallbacksAndMessages(null);
        pauseGame();
    }
    @Override
    protected void onStop() {
        super.onStop();
        pausedTime = System.currentTimeMillis();
        timerHandler.removeCallbacksAndMessages(null);
        pauseGame();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainSound.release();
        levelCompleteSound.release();
        timerHandler.removeCallbacksAndMessages(null);
        controller.removeLevel();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_A:
                move("left");
                return true;
            case KeyEvent.KEYCODE_W:
                move("up");
                return true;
            case KeyEvent.KEYCODE_S:
                move("down");
                return true;
            case KeyEvent.KEYCODE_D:
                move("right");
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    public void move(String direction) {
        controller.move(direction);
        updateLevel();
    }

    public void updateLevel() {
        TextView targetsCompletedLabel = findViewById(R.id.targets_completed_label);
        TextView targetsCountLabel = findViewById(R.id.targets_number_label);
        TextView moveCount = findViewById(R.id.move_number_label);
        int currentTargetsCompleted = controller.getTargetsCount();
        targetsCountLabel.setText(String.valueOf(currentTargetsCompleted));
        int currentTargetsCount = controller.getCompletedTargetsCount();
        targetsCompletedLabel.setText(String.valueOf(currentTargetsCount));
        int currentMoveCount = controller.getMoveCount();
        moveCount.setText(String.valueOf(currentMoveCount));
        drawLevel();
    }

    public void drawLevel() {
        int[][] levelImages = controller.getDrawables();
        TableLayout levelDisplay = findViewById(R.id.level_view);
        removeAllChildViews(levelDisplay);
        for (int x = 0; x < levelImages.length; x++) { //2D
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            for (int y = 0; y < levelImages[x].length; y++) { // 2D
                tr.addView(this.getImageView(levelImages[x][y]));
            }
            levelDisplay.addView(tr);
        }
    }

    private View getImageView(Integer image) {
        ImageView imageView = new ImageView(getApplicationContext());
        int width = 60;
        int height = 60;
        TableRow.LayoutParams parms = new TableRow.LayoutParams(width, height);
        imageView.setLayoutParams(parms);
        imageView.setImageResource(image);
        return imageView;
    }

    public void removeAllChildViews(ViewGroup viewGroup) {
     viewGroup.removeAllViews();
    }

    public void pauseGame() {
        TableLayout levelDisplay = findViewById(R.id.level_view);
        TableLayout controls = findViewById(R.id.controls_display);
        TextView gamePaused = findViewById(R.id.text_paused);
        levelDisplay.setVisibility(View.INVISIBLE);
        controls.setVisibility(View.INVISIBLE);
        gamePaused.setVisibility(View.VISIBLE);

    }

    public void resumeGame() {
        TableLayout levelDisplay = findViewById(R.id.level_view);
        TableLayout controls = findViewById(R.id.controls_display);
        TextView gamePaused = findViewById(R.id.text_paused);
        levelDisplay.setVisibility(View.VISIBLE);
        controls.setVisibility(View.VISIBLE);
        gamePaused.setVisibility(View.INVISIBLE);
    }
    public void disableSound() {
        mainSound.stop();
        try {
            mainSound.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        soundOn.setVisibility(View.GONE);
        soundOff.setVisibility(View.VISIBLE);
        musicOff = true;
    }

    public void enableSound() {
        mainSound.start();
        soundOn.setVisibility(View.VISIBLE);
        soundOff.setVisibility(View.GONE);
        musicOff= !true;
    }

}
