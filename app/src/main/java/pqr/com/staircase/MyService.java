package pqr.com.staircase;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    String app_block_url = "http://192.168.1.8:8000/api/appblock";

    private Timer timer = new Timer();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sendRequestToServer();   //Your code here
            }
        }, 0, 1*60*1000);//1 Minute
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }


    public void sendRequestToServer()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, app_block_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Service Running",response);
                if(response.equals("0"))
                {
                    AgentDashboardActivity a = new AgentDashboardActivity();
                    a.loggingout();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(getApplicationContext()).addToRequestque(stringRequest);



    }




}
