package nyc.c4q.hakeemsackes_bramble.timecapsule.audioactivity;

import android.app.Fragment;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import nyc.c4q.hakeemsackes_bramble.timecapsule.R;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class AudioFragment extends Fragment implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "AUDIO FRAGMENT";
    Random random;
    String outputFile = "ABCDEFGHIJKLMNOP";
    private int MAX_DURATION = 30000;
    private View mRoot;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private SeekBar seekBar;
    private ImageView iv_audio_record;
    private ImageView iv_audio_stop_record;
    private ImageView iv_audio_play;
    private ImageView iv_audio_stop;
    Timer timer = new Timer();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getActivity(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_audio, parent, false);
        setViews();
        setRecord();
        setStopRecord();
        setPlay();
        setStop();
        return mRoot;
    }

    public void MediaRecorderReady() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mRecorder.setOutputFile(outputFile);
    }

    public void setMaxRecord(){
        mRecorder.setMaxDuration(MAX_DURATION); //supposing u want to give maximum length of 60 seconds
        seekBar.setMax(MAX_DURATION); //supposing u want to give maximum length of 60 seconds
        seekBar.setProgress(0);
        startProgress();
    }


    public void startProgress(){
            timer.schedule(new TimerTask() {


                @Override
                public void run() {

                }
            }, 1, 1000);


        }


    public String CreateRandomAudioFileName(int string){
        random = new Random();
        StringBuilder stringBuilder = new StringBuilder(string);
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(outputFile.
                    charAt(random.nextInt(outputFile.length())));
            i++ ;
        }
        return stringBuilder.toString();
    }


    private void setViews() {
        iv_audio_record = (ImageView) mRoot.findViewById(R.id.iv_audio_record);
        iv_audio_stop_record = (ImageView) mRoot.findViewById(R.id.iv_audio_stop_record);
        iv_audio_play = (ImageView) mRoot.findViewById(R.id.iv_audio_play);
        iv_audio_stop = (ImageView) mRoot.findViewById(R.id.iv_audio_stop);
        seekBar = (SeekBar) mRoot.findViewById(R.id.seek_bar);
    }

    public void setRecord() {
        iv_audio_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(v);
            }
        });
    }

    private void start(View view) {
        if (checkPermission()) {

            outputFile =
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                            CreateRandomAudioFileName(5) + "AudioRecording.3gp";

            MediaRecorderReady();
            setMaxRecord();

            try {
                mRecorder.prepare();
            } catch (IllegalStateException e) {
                Log.e(TAG, "prepare() failed");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mRecorder.start();
            iv_audio_record.setEnabled(false);
            iv_audio_stop_record.setEnabled(true);
            Toast.makeText(getActivity(), "Start Recording",
                    Toast.LENGTH_SHORT).show();
        } else {
//            requestPermission();
        }
    }


    private void setStopRecord() {
        iv_audio_stop_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecord(v);
            }
        });
    }

    private void stopRecord(View view) {
        try {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
            iv_audio_stop_record.setEnabled(false);
            iv_audio_play.setEnabled(true);
              Toast.makeText(getActivity(), "Stop Recording",
                    Toast.LENGTH_SHORT).show();
        } catch (IllegalStateException e) {
            //  it is called before start()
            e.printStackTrace();
        } catch (RuntimeException e) {
            // no valid audio/video data has been received
            e.printStackTrace();
        }


    }

    private void setPlay() {
        iv_audio_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play(v);
            }
        });

    }

    private void play(View v) {
        try {
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(outputFile);
            mPlayer.prepare();
            mPlayer.start();
            iv_audio_play.setEnabled(false);
            iv_audio_stop.setEnabled(true);
            Toast.makeText(getActivity(), "Playing the recording",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setStop() {
        iv_audio_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop(v);
            }
        });
    }

    private void stop(View v) {
        try {
            if (mPlayer != null) {
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
                iv_audio_play.setEnabled(true);
                iv_audio_stop.setEnabled(false);
                Toast.makeText(getActivity(), "Playback Stopped",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}



