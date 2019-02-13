package com.example.bakingapp;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.net.URI;

public class StepFragment extends Fragment {
    VideoView stepVideo;
    ProgressBar buffering;
    TextView stepDescriptionTextView;
    String videoURL;
    String stepDescription;
    int currentPosition;
    public StepFragment() {
        super();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_fragment,container,false);

        Bundle args = getArguments();
        videoURL = args.getString("video_url");
        stepDescription = args.getString("step_description");

        stepVideo = view.findViewById(R.id.step_video);
        buffering = view.findViewById(R.id.buffering);
        stepDescriptionTextView = view.findViewById(R.id.step_description);

        if(savedInstanceState != null)
            currentPosition = savedInstanceState.getInt("play_time");

        MediaController controller = new MediaController(getContext());
        controller.setMediaPlayer(stepVideo);
        stepVideo.setMediaController(controller);
        stepDescriptionTextView.setText(stepDescription);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        buffering.setVisibility(View.VISIBLE);
        if(videoURL!=null)
        {
            stepVideo.setVideoURI(Uri.parse(videoURL));
        }
        stepVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                buffering.setVisibility(View.INVISIBLE);
                stepVideo.seekTo(currentPosition);
                stepVideo.start();
            }
        });

        stepVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stepVideo.seekTo(0);
            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();
        stepVideo.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        stepVideo.stopPlayback();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("play_time",stepVideo.getCurrentPosition());
    }

}
