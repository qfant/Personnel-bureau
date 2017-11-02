package com.page.home.activity;

import android.net.Uri;
import android.os.Bundle;

import com.framework.activity.BaseActivity;
import com.haolb.client.R;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by chenxi.cui on 2017/11/3.
 */

public class VideoActivity extends BaseActivity {
    private VideoView videoView;

    public String url1 ="http://zv.3gv.ifeng.com/live/zhongwen800k.m3u8";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_video);
        videoView = (VideoView) findViewById(R.id.vitamio);
        videoView.setVideoURI(Uri.parse(url1));
        videoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
        MediaController controller = new MediaController(this);
        videoView.setMediaController(controller);
        videoView.setBufferSize(10240); //设置视频缓冲大小。默认1024KB，单位byte
        videoView.requestFocus();
    }
}
