package io.oscs.flasher;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import java.util.ArrayList;

import studio.starry.flasher.R;
import io.oscs.flasher.config.FileConfig;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Flash extends ActionBarActivity {
      public String ModeSel;
      public String s="";
       private Spinner spinnerMode;
    // private Spinner spinnerFilter;
    // private CheckBox checkBox;
    private String[] Mode;
    //private String[] filters;
    private FileConfig fileConfig;
    //private CheckBox showHit;
    // private CheckBox multi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        int ret = execRootCmdSilent("echo test"); // 通过执行测试命令来检测
        if (ret != -1) {
            TextView info=(TextView) findViewById(R.id.chkroot);
            info.setText("Have root now checking buzybox");
        } else {
            TextView info=(TextView) findViewById(R.id.chkroot);
            info.setText("Root not find.This application will be useless");
        }
        setContentView(R.layout.activity_flash);
        fileConfig = new FileConfig();
        initView();
    }


    private void initView() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileSelectorDialog fileDialog = new FileSelectorDialog();
                fileDialog.setOnSelectFinish(new FileSelectorDialog.OnSelectFinish() {
                    @Override
                    public void onSelectFinish(ArrayList<String> paths) {
                        //Toast.makeText(getApplicationContext(), paths.toString(), Toast.LENGTH_SHORT).show();
                        s = paths.toString();
                        EditText editText = (EditText) findViewById(R.id.selectfile);
                        editText.setText(s.substring(1, s.length() - 1));
                    }
                });
                Bundle bundle = new Bundle();
                bundle.putSerializable(FileConfig.FILE_CONFIG, fileConfig);
                fileDialog.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fileDialog.show(ft, "fileDialog");
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(s.isEmpty()){
                    Toast.makeText(getApplicationContext(), "No file is selected!", Toast.LENGTH_SHORT).show();
                }else {
                    int ret = execRootCmdSilent("echo test"); // 通过执行测试命令来检测
                    if (ret != -1) {
                        EditText editText = (EditText) findViewById(R.id.selectfile);
                        s=editText.getText().toString();
                        Toast.makeText(getApplicationContext(), "Have root!Now flashing!", Toast.LENGTH_SHORT).show();
                       // Toast.makeText(getApplicationContext(), "dd if="+s+" of=/dev/block/platform/msm_sdcc.1/by-name/"+ModeSel , Toast.LENGTH_SHORT).show();
                        String result = execRootCmd("dd if="+s+" of=/dev/block/platform/msm_sdcc.1/by-name/"+ModeSel );
                        Toast.makeText(getApplicationContext(), "Process finished!The new recovery should have installed into your phone!", Toast.LENGTH_SHORT).show();
                        //TextView showresult = (TextView) findViewById(R.id.showresult);
                        //showresult.setText(result);
                    } else {
                        Toast.makeText(getApplicationContext(), "No root!", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        spinnerMode = (Spinner) findViewById(R.id.spinner_mode);
        //spinnerFilter = (Spinner) findViewById(R.id.spinner_filter);
       // checkBox = (CheckBox) findViewById(R.id.checkBox_mode);

        Mode = getResources().getStringArray(R.array.mode_selects);
        //filters = getResources().getStringArray(R.array.filter_selects);

        ArrayAdapter<String> adapterMode = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Mode);
        //ArrayAdapter<String> adapterFilter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filters);

        spinnerMode.setAdapter(adapterMode);
       // spinnerFilter.setAdapter(adapterFilter);

        spinnerMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        ModeSel="boot";
                        break;
                    default:
                        ModeSel="recovery";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
/*
        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int t;
                switch (position) {
                    case 1:
                        t = FileFilter.FILTER_NONE;
                        break;
                    case 2:
                        t = FileFilter.FILTER_IMAGE;
                        break;
                    case 3:
                        t = FileFilter.FILTER_TXT;
                        break;
                    case 4:
                        t = FileFilter.FILTER_VIDEO;
                        break;
                    case 5:
                        t = FileFilter.FILTER_AUDIO;
                        break;
                    default:
                        t = FileFilter.FILTER_NONE;
                }
                fileConfig.filterModel = t;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/
        /*
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fileConfig.positiveFiter = isChecked;
            }
        });

        showHit = (CheckBox) findViewById(R.id.checkBox_show_hit);
        showHit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fileConfig.showHiddenFiles = isChecked;
            }
        });

        multi = (CheckBox) findViewById(R.id.checkBox_multi);
        multi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fileConfig.multiModel = isChecked;
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileSelectorAlertDialog fileSelectorAlertDialog=new FileSelectorAlertDialog(Flash.this);
                fileSelectorAlertDialog.show();
            }
        });
        */
    }
    public static String execRootCmd(String cmd) {
        String result = "";
        DataOutputStream dos = null;
        DataInputStream dis = null;

        try {
            Process p = Runtime.getRuntime().exec("su");// 经过Root处理的android系统即有su命令
            dos = new DataOutputStream(p.getOutputStream());
            dis = new DataInputStream(p.getInputStream());

            dos.writeBytes(cmd + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            String line = null;
            while ((line = dis.readLine()) != null) {
                Log.d("result", line);
                result += line;
            }
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    public static int execRootCmdSilent(String cmd) {
        int result = -1;
        DataOutputStream dos = null;

        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());

            dos.writeBytes(cmd + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            p.waitFor();
            result = p.exitValue();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent intent = new Intent();
            intent.setClass(Flash.this,about.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> list = data.getStringArrayListExtra(FileSelector.RESULT);
                Toast.makeText(getApplicationContext(), list.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
