package nyc.c4q.hakeemsackes_bramble.timecapsule.audioactivity;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import nyc.c4q.hakeemsackes_bramble.timecapsule.R;

/**
 * Created by catwong on 3/6/17.
 */

public class AudioFragment2 extends Fragment {

    private View mRoot;
    Handler handler;
    MediaRecorder mRecorder;
    private String fileName;
    private Boolean isRecording;
    int recordTime;
    int playTime;
    SeekBar mSeekBar;
    MediaPlayer mPlayer;
    private ImageView iv_audio_record;
    private ImageView iv_audio_stop_record;
    private ImageView iv_audio_play;
    private TextView tv_audio_time;
    private int MAX_DURATION = 15000;
    private int MAX_SECONDS = 15;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_audio, parent, false);
        setViews();
        setMaxSeekBar();
        setRecord();
        setStopRecord();
        setPlay();
        handler = new Handler();
        fileName = Environment.getExternalStorageDirectory()+ "/audio"+System.currentTimeMillis()+".3gp";
        isRecording = false;
        return mRoot;
    }

    private void setViews() {
        iv_audio_record = (ImageView) mRoot.findViewById(R.id.iv_audio_record);
        iv_audio_stop_record = (ImageView) mRoot.findViewById(R.id.iv_audio_stop_record);
        iv_audio_play = (ImageView) mRoot.findViewById(R.id.iv_audio_play);
        tv_audio_time = (TextView) mRoot.findViewById(R.id.tv_audio_time);
        mSeekBar = (SeekBar) mRoot.findViewById(R.id.seek_bar);
    }

    public void MediaRecorderReady() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mRecorder.setOutputFile(fileName);
    }


    public void setMaxDuration(){
        mRecorder.setMaxDuration(MAX_DURATION);
    }

    public void setMaxSeekBar(){
        mSeekBar.setMax(MAX_SECONDS);
    }


    public void setRecord() {
        iv_audio_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording(v);
            }
        });
    }

    public void startRecording(View view){
        if(!isRecording){
            //Create MediaRecorder and initialize audio source, output format, and audio encoder
            MediaRecorderReady();
            setMaxDuration();
            // Starting record time
            recordTime = 0;
            // Show TextView that displays record time
            tv_audio_time.setVisibility(TextView.VISIBLE);
            try {
                mRecorder.prepare();
            } catch (IOException e) {
                Log.e("LOG_TAG", "prepare failed");
            }
            // Start record job
            mRecorder.start();
            Toast.makeText(getActivity(), "Start Recording",
                    Toast.LENGTH_SHORT).show();
            // Change isRecording flag to true
            isRecording = true;
            handler.post(UpdateRecordTime);
            // Post the record progress
        }
    }

    private void setStopRecord() {
        iv_audio_stop_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
            }
        });
    }

    public void stopRecording(){
        if(isRecording){
            // Stop recording and release resource
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
            // Change isRecording flag to false
            isRecording = false;
            // Hide TextView that shows record time
            Toast.makeText(getActivity(), "Stop Recording",
                    Toast.LENGTH_SHORT).show();
//            playRecording(); // Play the audio
        }
    }

    private void setPlay() {
        iv_audio_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playRecording(v);
            }
        });

    }

    public void playRecording(View view){
        // Create MediaPlayer object
        mPlayer = new MediaPlayer();
        // set start time
        playTime = 0;
        // Reset max and progress of the SeekBar
        mSeekBar.setMax(recordTime);
        mSeekBar.setProgress(0);
        try {
            // Initialize the player and start playing the audio
            mPlayer.setDataSource(fileName);
            mPlayer.prepare();
            mPlayer.start();
            Toast.makeText(getActivity(), "Play Recording",
                    Toast.LENGTH_SHORT).show();
            // Post the play progress
            handler.post(UpdatePlayTime);
        } catch (IOException e) {
            Log.e("LOG_TAG", "prepare failed");
        }
    }

    Runnable UpdateRecordTime = new Runnable(){
        public void run(){
            if(isRecording){
                tv_audio_time.setText(String.valueOf(recordTime));
                mSeekBar.setProgress(recordTime);
                recordTime += 1;
                if(recordTime == MAX_SECONDS){
                    tv_audio_time.setText("Recording Done");
                    stopRecording();
                } else if(!isRecording){
                    recordTime = 0;
                }
                // Delay 1s before next call
                handler.postDelayed(this, 1000);

            }
        }
    };
    Runnable UpdatePlayTime = new Runnable(){
        public void run(){
            if(mPlayer.isPlaying()){
                tv_audio_time.setText(String.valueOf(playTime));
                // Update play time and SeekBar
                playTime += 1;
                mSeekBar.setProgress(playTime);
                // Delay 1s before next call
                handler.postDelayed(this, 1000);
            }
        }
    };


}


