package com.example.mymusic;

import android.Manifest;

import android.annotation.SuppressLint;

import android.content.pm.PackageManager;

import android.database.Cursor;

import android.media.MediaPlayer;

import android.os.Bundle;

import android.provider.MediaStore;

import android.view.View;

import android.widget.ImageView;

import android.widget.SeekBar;

import android.widget.TextView;

import android.widget.Toast;



import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;



import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;



public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private static final int LEVEL_PAUSE = 0;

    private static final int LEVEL_PLAY = 1;

    //Khai báo trình nghe nhạc MediaPlayer
    private static final MediaPlayer player = new MediaPlayer();

    private static final int STATE_IDE = 1;

    private static final int STATE_PLAYING = 2;

    private static final int STATE_PAUSED = 3;

    // SongEntity is a customize class
    private final ArrayList<SongEntity> listSong = new ArrayList<>();

    private TextView tvName, tvAlbum, tvTime;

    private SeekBar seekBar;

    private ImageView ivPlay;



    private int index;

    private SongEntity songEntity;

    private Thread thread;

    private int state = STATE_IDE;

    private String totalTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        // flow main control for app view
        // Việc tách logic xử lý của app thành 1 phương thức riêng
        // sẽ giúp việc xứ lý sau này thuận tiện hơn khi phát sinh vấn đề cần xử lý trong vòng đời Activity
        initViews();

    }


    // flow main control
    private void initViews() {

        ivPlay = findViewById(R.id.iv_play);

        ivPlay.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_next).setOnClickListener(this);

        tvName = findViewById(R.id.tv_name);
        tvAlbum = findViewById(R.id.tv_album);
        tvTime = findViewById(R.id.tv_time);
        seekBar = findViewById(R.id.seekbar);

        seekBar.setOnSeekBarChangeListener(this);

        // Kiểm tra cấp quyền đọc bộ nhớ ngoài READ_EXTERNAL_STORAGE
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
            return;

        }

        // phương thức này được gọi để tạo listsong cho trình phát nhạc
        loadingListSongOffline();

    }


    // Phương thức xừ lý cấp quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            loadingListSongOffline();

        } else {

            Toast.makeText(this, R.string.txt_alert, Toast.LENGTH_SHORT).show();
            finish();

        }

    }

    // Phương thức LoadingListSongOffLine() dùng để đọc data từ bộ nhớ ngoài và tạo listsong
    private void loadingListSongOffline() {

        //ContentResolver cho phép truy cập đến tài nguyên của ứng dụng thông qua 1 đường dẫn uri
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,

                null, null, null, null);

        if (cursor != null) {

            cursor.moveToFirst();

            listSong.clear();

            while (!cursor.isAfterLast()) {

                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));

                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));

                String album = "N/A";

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {

                    album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ARTIST));

                }

                listSong.add(new SongEntity(name, path, album));

                cursor.moveToNext();

            }

            cursor.close();

        }

        // Khởi tạo recycleView
        RecyclerView rv = findViewById(R.id.rv_song);

        // Tạo thành phần sắp xếp các item trong RecyclerView bằng phương thức setLayoutManager()
        // LinearLayoutManager: Hỗ trợ scroll các item theo chiều ngang hoặc chiều dọc
        // READ MORE: https://kipalog.com/posts/Su-dung-RecyclerView
        rv.setLayoutManager(new LinearLayoutManager(this));

        // Tạo các item trong RecycleView
        rv.setAdapter(new MusicAdapter(listSong, this));

        //phương thức này để phát nhạc
        play();

        //phương thức này để dừng nhạc
        playPause();

    }


    //Phương thức chọn bài hát
    public void playSong(SongEntity songEntity) {

        index = listSong.indexOf(songEntity);

        this.songEntity = songEntity;

        play();

    }


    // Xử lý onClick
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.iv_play) {

            playPause();

        } else if (v.getId() == R.id.iv_next) {

            next();

        } else if (v.getId() == R.id.iv_back) {

            back();

        }

    }


    // PHương thức lùi lại bài phía trước
    private void back() {

        if (index == 0) {

            index = listSong.size() - 1;

        } else {

            index--;

        }

        play();

    }


    // Phương thức chuyển bài kế tiếp
    private void next() {

        if (index >= listSong.size()) {

            index = 0;

        } else {

            index++;

        }

        play();

    }


    // Phương thức tạm dừng nhạc
    private void playPause() {

        if (state == STATE_PLAYING && player.isPlaying()) {

            player.pause();

            ivPlay.setImageLevel(LEVEL_PAUSE);

            state = STATE_PAUSED;

        } else if (state == STATE_PAUSED) {

            player.start();

            state = STATE_PLAYING;

            ivPlay.setImageLevel(LEVEL_PLAY);

        } else {

            play();

        }

    }


    //Phương thức chơi nhạc
    private void play() {

        songEntity = listSong.get(index);

        tvName.setText(songEntity.getName());

        tvAlbum.setText(songEntity.getAlbum());

        player.reset();

        try {

            player.setDataSource(songEntity.getPath());

            player.prepare();

            player.start();

            ivPlay.setImageLevel(LEVEL_PLAY);

            state = STATE_PLAYING;

            totalTime = getTime(player.getDuration());

            seekBar.setMax(player.getDuration());

            if (thread == null) {

                startLooping();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }



    private void startLooping() {

        thread = new Thread() {

            @Override

            public void run() {

                while (true) {

                    try {

                        Thread.sleep(200);

                    } catch (Exception e) {

                        return;

                    }

                    runOnUiThread(() -> updateTime());

                }

            }

        };

        thread.start();

    }



    private void updateTime() {

        if (state == STATE_PLAYING || state == STATE_PAUSED) {

            int time = player.getCurrentPosition();

            tvTime.setText(String.format("%s/%s", getTime(time), totalTime));

            seekBar.setProgress(time);

        }

    }



    @SuppressLint("SimpleDateFormat")

    private String getTime(int time) {

        return new SimpleDateFormat("mm:ss").format(new Date(time));

    }



    @Override
    protected void onDestroy() {

        super.onDestroy();

        thread.interrupt();

    }



    @Override

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {



    }



    @Override

    public void onStartTrackingTouch(SeekBar seekBar) {



    }



    @Override

    public void onStopTrackingTouch(SeekBar seekBar) {

        if (state == STATE_PLAYING || state == STATE_PAUSED) {

            player.seekTo(seekBar.getProgress());

        }

    }

}