package com.brotherjing.facialexpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Detector;
import com.affectiva.android.affdex.sdk.detector.Face;
import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.brotherjing.facialexpdemo.bean.SearchResponse;
import com.brotherjing.facialexpdemo.bean.SearchSong;
import com.brotherjing.facialexpdemo.bean.douban.Tag;
import com.brotherjing.facialexpdemo.database.DBManager;
import com.brotherjing.facialexpdemo.netease.NetEaseProvider;
import com.brotherjing.facialexpdemo.views.ChangeColorView;
import com.brotherjing.facialexpdemo.views.MyViewGroup;
import com.brotherjing.facialexpdemo.views.UploadTagItemProvider;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements Detector.ImageListener,CameraDetector.CameraEventListener,View.OnClickListener{

    final int DISGUST_INIT = 1000;
    final int DISGUST_TOTAL = 2000;
    final int MODE_LIKE = 0;
    final int MODE_MOOD = 1;

    private SurfaceView mSurfaceView;

    private TextView smileTextView;

    private MyViewGroup tagList;
    private ChangeColorView changeColor;
    private TextView disgustTextView,tvTitle,tvAlbum,tvSinger;
    private Button playBtn,btnPause,btnSwitch,btnMode;
    private ImageView ivAlbum;
    private Player player;
    private SeekBar musicProgress;
    private IconRoundCornerProgressBar progressBar;

    private double hp = DISGUST_INIT;
    private int mood;
    private boolean isSwitching = true;
    private int mode = MODE_LIKE;

    private Thread playingThread;
    private Subscription subscription;

    private CameraDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        player = new Player(musicProgress);

        detector = new CameraDetector(this, CameraDetector.CameraType.CAMERA_FRONT,mSurfaceView);
        detector.setLicensePath("license.txt");
        detector.setImageListener(this);
        setDetect();
        detector.setOnCameraEventListener(this);
    }

    private void setDetect(){
        detector.setDetectAllEmotions(false);
        detector.setDetectAllExpressions(false);
        if(mode==MODE_LIKE){
            detector.setDetectSmile(true);
            detector.setDetectDisgust(true);
        }else{
            detector.setDetectAnger(true);
            detector.setDetectSadness(true);
            detector.setDetectJoy(true);
        }
    }

    private void initView(){
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        smileTextView = (TextView)findViewById(R.id.smileView);
        disgustTextView = (TextView)findViewById(R.id.tvDisgustSum);

        tagList = (MyViewGroup)findViewById(R.id.tag_list);
        changeColor = (ChangeColorView)findViewById(R.id.change_color);

        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvSinger = (TextView)findViewById(R.id.tvSinger);
        tvAlbum = (TextView)findViewById(R.id.tvAlbum);
        ivAlbum = (ImageView)findViewById(R.id.iv_album);
        playBtn = (Button)findViewById(R.id.btn_play);
        btnPause = (Button)findViewById(R.id.btn_pause);
        btnSwitch = (Button)findViewById(R.id.btn_switch);
        btnMode = (Button)findViewById(R.id.btn_mode);
        musicProgress = (SeekBar)findViewById(R.id.seekBar);
        progressBar = (IconRoundCornerProgressBar)findViewById(R.id.progress_bar);

        musicProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());

        btnSwitch.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        playBtn.setOnClickListener(this);
        btnMode.setOnClickListener(this);

        disgustTextView.setText(hp + "");
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.btn_play){
            player.play();
        }else if(id==R.id.btn_pause){
            player.pause();
        }else if(id==R.id.btn_switch){
            if(mode==MODE_MOOD)
                switchSongByEmotion(mood);
            else
                switchSong();
        }else if(id==R.id.btn_mode){
            switchMode();
        }
    }

    private void switchMode(){
        mode = 1-mode;
        if(mode==MODE_MOOD)changeColor.setVisibility(View.VISIBLE);
        else changeColor.setVisibility(View.GONE);
        safeStop();
        setDetect();
        safeStart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        safeStart();
        switchSong();
    }

    @Override
    protected void onStop() {
        super.onStop();
        safeStop();
    }


    @Override
    public void onImageResults(List<Face> list, Frame frame, float v) {
        if (list == null)
            return;
        if (list.size() == 0) {
            smileTextView.setText("NO FACE");
            progressBar.setIconImageResource(R.drawable.neutral2);
        } else {
            Face face = list.get(0);
            if(mode==MODE_LIKE) {
                float smile = face.expressions.getSmile();
                float disgust = face.emotions.getDisgust();
                if (smile < 5 && disgust < 5) {
                    smileTextView.setText("neutral");
                    progressBar.setIconImageResource(R.drawable.neutral2);
                }
                else if (smile > disgust) {
                    smileTextView.setText(String.format("SMILE\n%.2f", face.expressions.getSmile()));
                    progressBar.setIconImageResource(R.drawable.smile2);
                    if (hp < DISGUST_TOTAL - smile && !isSwitching)
                        hp += smile;
                } else {
                    smileTextView.setText(String.format("DISGUST\n%.2f", face.emotions.getDisgust()));
                    progressBar.setIconImageResource(R.drawable.sad2);
                    if (!isSwitching)
                        hp -= disgust;
                }
                if (hp <= 0) {
                    switchSong();
                    hp = DISGUST_INIT;
                    return;
                }
                updateProgressBar(hp);
            }else if(mode==MODE_MOOD){
                float anger = face.emotions.getAnger();
                float joy = face.emotions.getJoy();
                float sadness = face.emotions.getSadness();
                float max = Math.max(anger,Math.max(joy,sadness));
                if(sadness==max){
                    smileTextView.setText(String.format("SADNESS\n%.2f", sadness));
                    progressBar.setIconImageResource(R.drawable.crying2);
                    if(sadness>80&&!isSwitching) {
                        changeColor.toColor(getResources().getColor(R.color.sadness));
                        mood = CONSTANT.MOOD_SADNESS;
                        switchSongByEmotion(mood);
                    }
                }else if(anger==max){
                    smileTextView.setText(String.format("ANGER\n%.2f", anger));
                    progressBar.setIconImageResource(R.drawable.frustrated2);
                    if(anger>80&&!isSwitching) {
                        changeColor.toColor(getResources().getColor(R.color.anger));
                        mood = CONSTANT.MOOD_ANGER;
                        switchSongByEmotion(mood);
                    }
                }else if(joy==max){
                    smileTextView.setText(String.format("JOY\n%.2f", joy));
                    progressBar.setIconImageResource(R.drawable.happy2);
                    if(joy>80&&!isSwitching) {
                        changeColor.toColor(getResources().getColor(R.color.joy));
                        mood = CONSTANT.MOOD_JOY;
                        switchSongByEmotion(mood);
                    }
                }
            }
        }
    }

    @Override
    public void onCameraSizeSelected(int cameraWidth, int cameraHeight, Frame.ROTATE rotation) {
        int cameraPreviewWidth;
        int cameraPreviewHeight;

//cameraWidth and cameraHeight report the unrotated dimensions of the camera frames, so switch the width and height if necessary

        if (rotation == Frame.ROTATE.BY_90_CCW || rotation == Frame.ROTATE.BY_90_CW) {
            cameraPreviewWidth = cameraHeight;
            cameraPreviewHeight = cameraWidth;
        } else {
            cameraPreviewWidth = cameraWidth;
            cameraPreviewHeight = cameraHeight;
        }

//compute the aspect Ratio of the ViewGroup object and the cameraPreview

        float cameraPreviewAspectRatio = (float)cameraPreviewWidth/cameraPreviewHeight;

//size the SurfaceView

        ViewGroup.LayoutParams params = mSurfaceView.getLayoutParams();
        //params.height = newHeight;
        params.width=(int)(params.height*cameraPreviewAspectRatio);
        //params.height = (int)(params.width/cameraPreviewAspectRatio);
        mSurfaceView.setLayoutParams(params);
        mSurfaceView.requestLayout();
    }

    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
            this.progress = progress * player.mediaPlayer.getDuration()
                    / seekBar.getMax();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            player.mediaPlayer.seekTo(progress);
        }

    }

    private void switchSong(){

        isSwitching = true;
        if (playingThread != null) {
            if (playingThread.isAlive()) playingThread.interrupt();
        }
        safeStop();
        NetEaseProvider.updateTagOfLastSong(hp);
        DBManager.printDB();
        Observable<SearchResponse> call = NetEaseProvider.nextSongRandom();
        subscription = call.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            Log.i("yj", ((HttpException) e).code() + "");
                        }
                        e.printStackTrace();
                        safeStart();
                    }

                    @Override
                    public void onNext(SearchResponse searchResponse) {
                        try {
                            if (searchResponse == null || searchResponse.getResult() == null || searchResponse.getResult().getSongCount() == 0) {
                                Log.i("yj", "songs is null!");
                                Toast.makeText(MainActivity.this,"再试一次~",Toast.LENGTH_SHORT).show();
                                isSwitching = false;
                                //safeStart();
                            } else {
                                final SearchSong song = searchResponse.getResult().getSongs().get(0);
                                Picasso.with(MainActivity.this).load(song.getAlbum().getPicUrl()).into(ivAlbum);
                                fillViewWithSong(song);
                                playingThread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        player.playUrl(song.getAudio());
                                        isSwitching = false;
                                    }
                                });
                                playingThread.start();
                            }
                        }catch (NullPointerException e){
                            Toast.makeText(MainActivity.this,"再试一次~",Toast.LENGTH_SHORT).show();
                            isSwitching = false;
                        }finally {
                            safeStart();
                            hp = DISGUST_INIT;
                            updateProgressBar(hp);
                        }
                    }
                });
    }

    private void fillViewWithSong(SearchSong song){
        tvTitle.setText(song.getName());
        //tvAlbum.setText(song.getAlbum().getName());
        if(song.getArtists().isEmpty())return;
        tvSinger.setText(song.getArtists().get(0).getName()+"  "+song.getAlbum().getName());

        tagList.removeAllViews();
        if(GlobalEnv.currentMusic==null)return;
        List<Tag> list = GlobalEnv.currentMusic.getTags();
        for(Tag tag:list){
            UploadTagItemProvider.createItem(this,tagList,tag.getName());
        }
    }

    private void switchSongByEmotion(int mood){
        String tag;
        switch(mood){
            case CONSTANT.MOOD_JOY:
                tag = CONSTANT.TAG_JOY[(int)(Math.random()*CONSTANT.TAG_JOY.length)];
                break;
            case CONSTANT.MOOD_ANGER:
                tag = CONSTANT.TAG_ANGER[(int)(Math.random()*CONSTANT.TAG_ANGER.length)];
                break;
            default:
                tag = CONSTANT.TAG_SADNESS[(int)(Math.random()*CONSTANT.TAG_SADNESS.length)];
                break;
        }
        isSwitching = true;
        if (playingThread != null) {
            if (playingThread.isAlive()) playingThread.interrupt();
        }
        safeStop();
        Observable<SearchResponse> call = NetEaseProvider.nextSongWithTag(tag);
        subscription = call.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            Log.i("yj", ((HttpException) e).code() + "");
                        }
                        safeStart();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(SearchResponse searchResponse) {
                        try {
                            if (searchResponse.getResult().getSongs().isEmpty()) {
                                Toast.makeText(MainActivity.this,"再试一次~",Toast.LENGTH_SHORT).show();
                                Log.i("yj", "songs is null!");
                                isSwitching = false;
                            } else {
                                final SearchSong song = searchResponse.getResult().getSongs().get(0);
                                Picasso.with(MainActivity.this).load(song.getAlbum().getPicUrl()).into(ivAlbum);
                                fillViewWithSong(song);
                                playingThread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        player.playUrl(song.getAudio());
                                        isSwitching = false;
                                    }
                                });
                                playingThread.start();
                            }
                        }catch (NullPointerException e){
                            Toast.makeText(MainActivity.this,"再试一次~",Toast.LENGTH_SHORT).show();
                            isSwitching = false;
                        }finally {
                            safeStart();
                            hp = DISGUST_INIT;
                            updateProgressBar(hp);

                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.pause();
            player.stop();
            player = null;
        }
        if(subscription!=null&&!subscription.isUnsubscribed())subscription.unsubscribe();
    }

    private void safeStart(){
        if(detector!=null&&!detector.isRunning())detector.start();
    }

    private void safeStop(){
        if(detector!=null&&detector.isRunning())detector.stop();
    }

    private void updateProgressBar(double progress){
        progressBar.setProgress((float)progress);
        if(progress<400){
            progressBar.setProgressColor(getResources().getColor(R.color.red));
            progressBar.setIconBackgroundColor(getResources().getColor(R.color.dark_red));
        }else if(progress<1000){
            progressBar.setProgressColor(getResources().getColor(R.color.yellow));
            progressBar.setIconBackgroundColor(getResources().getColor(R.color.dark_yellow));
        }else{
            progressBar.setProgressColor(getResources().getColor(R.color.green));
            progressBar.setIconBackgroundColor(getResources().getColor(R.color.dark_green));
        }
    }
}
