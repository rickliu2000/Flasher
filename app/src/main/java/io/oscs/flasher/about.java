package io.oscs.flasher;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import studio.starry.flasher.R;

public class about extends  ActionBarActivity {
   int ver=1222;
    int veronline=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView version = (TextView) findViewById(R.id.Version);
        version.setText("Version:" + String.valueOf(ver));
        TextView date=(TextView) findViewById(R.id.date);
        date.setText("Date:20160314");
        TextView info=(TextView) findViewById(R.id.info);
        info.setText(getHandSetInfo() );
        TextView Ver=(TextView) findViewById(R.id.Current);
        Ver.setText("Current Version : "+String.valueOf(ver));
        initView();
      };


    private void initView() {
        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                TextView Ver=(TextView) findViewById(R.id.Update);
                Ver.setText("Checking Update");
                Ver.setText(getUpdateInfo() );
                            }
        });
        findViewById(R.id.donate).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "+1s", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void chkupdate(){
        Toast.makeText(getApplicationContext(), "Checking Update", Toast.LENGTH_SHORT).show();
    }
    private String getHandSetInfo() {
        String handSetInfo =
                "Device Name:" + android.os.Build.MODEL + "\n" +
                        "SDK Version:" + android.os.Build.VERSION.SDK + "\n" +
                        "Android Version:" + android.os.Build.VERSION.RELEASE+"\n"+
                        "You have to find recovery images and boot images for "+ android.os.Build.DEVICE +".";

        return handSetInfo;

    }
    private String getUpdateInfo() {
        //Toast.makeText(getApplicationContext(), "Checking Update", Toast.LENGTH_SHORT).show();
        if (veronline<=ver) {
            String UpdateInfo = "Latest Version : "+veronline+"\n"+"Already Latest Version ";
            return UpdateInfo;
        }else {
            String UpdateInfo =
                    "Latest Version : " + veronline;
            return UpdateInfo;
        }


    }
}

