package iot.vn.com.stream_app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button btnConnect;
    private Button btnUp;
    private Button btnDown;
    private Button btnLeft;
    private Button btnRight;
    private TextView tvStatus;
    private VideoView videoView;
    private boolean status = false;
    private String videoLink = "https://vnso-zn-15-tf-mcloud-bf-s7-mv-zmp3.zadn.vn/XbUNdLRwAME/c8deb627fb62123c4b73/ac42430766428f1cd653/360/Cham-Day-Noi-Dau.mp4?authen=exp=1538411498~acl=/XbUNdLRwAME/*~hmac=b226e7a055a70c3dd2c7eec6a4a5a340";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (isConnected()) {
                        clickConnect();
                    }
                    else {
                        tvStatus.setText("Please enable your network!");
                        tvStatus.setTextColor(getColor(R.color.red));
                        return;
                    }
                } catch (Exception e){
                    Log.d("Exception: ", String.valueOf(e));
                }

            }
        });
    }

    public boolean isConnected() throws InterruptedException, IOException
    {
        String command = "ping -c 1 google.com";
        return (Runtime.getRuntime().exec(command).waitFor() == 0);
    }

    private void clickConnect() {
        if (!status) {
            tvStatus.setText("Connected");
            tvStatus.setTextColor(getColor(R.color.green));
            btnUp.setEnabled(true);
            btnDown.setEnabled(true);
            btnRight.setEnabled(true);
            btnLeft.setEnabled(true);
            status=true;
            videoConnected();
        } else {
            tvStatus.setText("Disconnected !");
            tvStatus.setTextColor(getColor(R.color.red));
            btnUp.setEnabled(false);
            btnDown.setEnabled(false);
            btnRight.setEnabled(false);
            btnLeft.setEnabled(false);
            status=false;
            videoView.stopPlayback();
        }
    }

    private void videoConnected() {
        Uri vidUri = Uri.parse(videoLink);
        videoView.setVideoURI(vidUri);
        MediaController vidControl = new MediaController(this);
        vidControl.setAnchorView(videoView);
        videoView.setMediaController(vidControl);
        videoView.start();
    }

    private void initView() {
        btnConnect = (Button) findViewById(R.id.btn_connect);
        btnDown = (Button) findViewById(R.id.btn_down);
        btnLeft = (Button) findViewById(R.id.btn_left);
        btnRight = (Button) findViewById(R.id.btn_right);
        btnUp = (Button) findViewById(R.id.btn_up);
        tvStatus = (TextView) findViewById(R.id.tv_status);
        videoView = (VideoView) findViewById(R.id.video_view);
    }


}
