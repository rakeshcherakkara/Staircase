package pqr.com.staircase;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

/**
 * Created by ibm on 19/11/2018.
 */

public class ScrollingMessage {


    private String message_url = "http://192.168.1.8:8000/scrolling";
    private Context context;

    public ScrollingMessage(Context ctx)
    {
        this.context = ctx;
    }

    public void ScrollIt()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, message_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String msg = jsonObject.getString("message");

                    AgentData.getInstance().setMessage(msg);

                    // Log.d("msg",x);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Log.d("res" ,response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(context).addToRequestque(stringRequest);
    }


}
