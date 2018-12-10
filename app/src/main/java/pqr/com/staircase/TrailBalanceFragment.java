package pqr.com.staircase;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrailBalanceFragment extends Fragment {

    TextView opening_balance,sale_amount,win_amount,pay_paid,pay_rec,current_balance;
    String trail_url = "http://192.168.1.8:8000/api/trailbalance";


    public TrailBalanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trail_balance, container, false);

        opening_balance = view.findViewById(R.id.openingBalance);
        sale_amount = view.findViewById(R.id.saleAmount);
        win_amount = view.findViewById(R.id.winningAmount);
        pay_paid = view.findViewById(R.id.paymentMade);
        pay_rec = view.findViewById(R.id.paymentRec);
        current_balance = view.findViewById(R.id.currentBalance);

        final String agent_id = String.valueOf(AgentData.getInstance().getAgent_id());
        final String date = AgentData.getInstance().getSale_date();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, trail_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    opening_balance.setText(jsonObject.getString("opening_balance"));
                    sale_amount.setText(jsonObject.getString("sale_amount"));
                    win_amount.setText(jsonObject.getString("winning_amount"));
                    pay_paid.setText(jsonObject.getString("payment_made"));
                    pay_rec.setText(jsonObject.getString("payment_received"));
                    current_balance.setText(jsonObject.getString("current_balance"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("agent_id", agent_id);
                params.put("date", date);
                return params;
            }
        };

        MySingleton.getInstance(getContext()).addToRequestque(stringRequest);


        return view;


    }

}
