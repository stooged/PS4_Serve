package stooged.ps4serve;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import fi.iki.elonen.NanoHTTPD;

public class MainActivity extends AppCompatActivity {
    NanoHTTPD webServer;

    TextView text1,text2;
    Button btn1,btn2,btn3,btn4;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KillWebServer();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        SetPermissions();

        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);

        text1.setText("http://" +   Utils.getIp()  + ":8080/");

        Utils.createResFile(this,R.raw.index_html,"index.html");
        Utils.createResFile(this,R.raw.expl_js,"expl.js");
        Utils.createResFile(this,R.raw.fix_js,"fix.js");
        Utils.createResFile(this,R.raw.gadgets_js,"gadgets.js");
        Utils.createResFile(this,R.raw.rop_js,"rop.js");
        Utils.createResFile(this,R.raw.syscalls_js,"syscalls.js");

        switch (Utils.GetSetting(this,"SELECTED","HEN"))
        {
            case "HEN":
                text2.setText("Selected: " + btn1.getText());
                btn1.setBackgroundColor(Color.parseColor("#ce923e"));
                Utils.createResFile(this,R.raw.hen_pl,"payload.js");
                break;
            case "FTP":
                text2.setText("Selected: " + btn2.getText());
                btn2.setBackgroundColor(Color.parseColor("#ce923e"));
                Utils.createResFile(this,R.raw.ftp_pl,"payload.js");
                break;
            case "DMP":
                text2.setText("Selected: " + btn3.getText());
                btn3.setBackgroundColor(Color.parseColor("#ce923e"));
                Utils.createResFile(this,R.raw.dump_pl,"payload.js");
                break;
            case "BAK":
                text2.setText("Selected: " + btn4.getText());
                btn4.setBackgroundColor(Color.parseColor("#ce923e"));
                Utils.createResFile(this,R.raw.backup_pl,"payload.js");
                break;
            default:
                text2.setText("Selected: " + btn1.getText());
                btn1.setBackgroundColor(Color.parseColor("#ce923e"));
                Utils.createResFile(this,R.raw.hen_pl,"payload.js");
                break;
        }

        StartWebServer();
    }

    private void KillWebServer() {
        if (webServer != null) {
            if (webServer.isAlive()) {
                webServer.closeAllConnections();
                webServer.stop();
            }
            webServer = null;
        }
    }

    private void StartWebServer()
    {
        webServer = new Server(this,8080);
        try {
            webServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void StopWebServer()
    {
        if (webServer != null) {
            if (webServer.isAlive()) {
                webServer.closeAllConnections();
                webServer.stop();
            }
        }
    }

    private void SetPermissions() {
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            String[] perms = {"android.permission.INTERNET", "android.permission.ACCESS_WIFI_STATE", "android.permission.ACCESS_NETWORK_STATE"};
            int permsRequestCode = 200;
            requestPermissions(perms, permsRequestCode);
        }
    }

    public void btn1_Click(View view) {
        text2.setText("Selected: " + btn1.getText());
        btn1.setBackgroundColor(Color.parseColor("#ce923e"));
        btn2.setBackgroundColor(Color.parseColor("#0099cc"));
        btn3.setBackgroundColor(Color.parseColor("#0099cc"));
        btn4.setBackgroundColor(Color.parseColor("#0099cc"));
        Utils.createResFile(this,R.raw.hen_pl,"payload.js");
        Utils.SaveSetting(this,"SELECTED","HEN");
        Utils.ShowToast(this,"HEN payload Selected", Toast.LENGTH_SHORT);
    }

    public void btn2_Click(View view) {
        text2.setText("Selected: " + btn2.getText());
        btn2.setBackgroundColor(Color.parseColor("#ce923e"));
        btn1.setBackgroundColor(Color.parseColor("#0099cc"));
        btn3.setBackgroundColor(Color.parseColor("#0099cc"));
        btn4.setBackgroundColor(Color.parseColor("#0099cc"));
        Utils.createResFile(this,R.raw.ftp_pl,"payload.js");
        Utils.SaveSetting(this,"SELECTED","FTP");
        Utils.ShowToast(this,"FTP payload Selected", Toast.LENGTH_SHORT);
    }

    public void btn3_Click(View view) {
        text2.setText("Selected: " + btn3.getText());
        btn3.setBackgroundColor(Color.parseColor("#ce923e"));
        btn2.setBackgroundColor(Color.parseColor("#0099cc"));
        btn1.setBackgroundColor(Color.parseColor("#0099cc"));
        btn4.setBackgroundColor(Color.parseColor("#0099cc"));
        Utils.createResFile(this,R.raw.dump_pl,"payload.js");
        Utils.SaveSetting(this,"SELECTED","DMP");
        Utils.ShowToast(this,"Dumper payload Selected", Toast.LENGTH_SHORT);
    }

    public void btn4_Click(View view) {
        text2.setText("Selected: " + btn4.getText());
        btn4.setBackgroundColor(Color.parseColor("#ce923e"));
        btn2.setBackgroundColor(Color.parseColor("#0099cc"));
        btn3.setBackgroundColor(Color.parseColor("#0099cc"));
        btn1.setBackgroundColor(Color.parseColor("#0099cc"));
        Utils.createResFile(this,R.raw.backup_pl,"payload.js");
        Utils.SaveSetting(this,"SELECTED","BAK");
        Utils.ShowToast(this,"Backup payload Selected", Toast.LENGTH_SHORT);
    }



}