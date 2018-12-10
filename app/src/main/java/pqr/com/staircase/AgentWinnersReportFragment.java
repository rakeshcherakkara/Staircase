package pqr.com.staircase;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgentWinnersReportFragment extends Fragment {

    ExpandableListView expandableListView;

    String winner_url = "http://192.168.1.8:8000/api/agentwinnerreport";
    //String winner_url = "http://192.168.0.36:8000/api/agentwinnerreport";


    EditText fromDate,toDate,customerName;
    TextView totalQuantity,grandTotal;
    Spinner productSpinner,subagentSpinner;

    Button winnersReportButton;




    List<String> agentChild = new ArrayList<>();


    HashMap<String,List<String>> agentHash = new HashMap<>();
    HashMap<String,List<String>> subagentHash = new HashMap<>();

    ArrayList<String> productNames,subAgents;
    ArrayAdapter<String> productNamesAdapter,subagentAdapter;
    ProgressDialog progressDialog;




    public AgentWinnersReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agent_winners_report, container, false);

        expandableListView = view.findViewById(R.id.agent_winners_listview);

        fromDate = view.findViewById(R.id.fromDate);
        toDate = view.findViewById(R.id.toDate);
        customerName = view.findViewById(R.id.customer);
        productSpinner = view.findViewById(R.id.productSpinner);
        subagentSpinner = view.findViewById(R.id.subagentSpinner);

        winnersReportButton = view.findViewById(R.id.winnersReportButton);

        productNames = AgentData.getInstance().getProductNames();
        subAgents = AgentData.getInstance().getSubagentNames();

        progressDialog = new ProgressDialog(getContext());
        productNamesAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,productNames);
        subagentAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,subAgents);

        productSpinner.setAdapter(productNamesAdapter);
        subagentSpinner.setAdapter(subagentAdapter);

        totalQuantity = view.findViewById(R.id.totalQuantity);
        grandTotal = view.findViewById(R.id.grandTotal);




        String today_date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        fromDate.setText(today_date);
        toDate.setText(today_date);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-7);
        final Date result = calendar.getTime();
        final int d = calendar.get(Calendar.DAY_OF_MONTH);
        final int m = calendar.get(Calendar.MONTH);
        final int y = calendar.get(Calendar.YEAR);


        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog pickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        String finalDate = getDate(year,month,date);
                        fromDate.setText(finalDate);

                    }
                }, y, m, d);

                pickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()+24*60*60*1000);
                pickerDialog.getDatePicker().setMinDate(result.getTime());
                pickerDialog.show();

            }
        });


        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog pickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        String finalDate1 = getDate(year,month,date);
                        toDate.setText(finalDate1);

                    }
                }, y, m, d);

                pickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()+24*60*60*1000);
                pickerDialog.getDatePicker().setMinDate(result.getTime());
                pickerDialog.show();

            }
        });




        winnersReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                winnersReportButton.setEnabled(false);
                final String fromDate_1 = fromDate.getText().toString();
                final String toDate_1 = toDate.getText().toString();
                final String agentid = String.valueOf(AgentData.getInstance().getAgent_id());
                final String product_name = productSpinner.getSelectedItem().toString();
                //final String subagent_name = subagentSpinner.getSelectedItem().toString();
                final String customer_name = customerName.getText().toString();


                StringRequest stringRequest = new StringRequest(Request.Method.POST, winner_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        winnersReportButton.setEnabled(true);
                        progressDialog.dismiss();

                        try {

                            List<String> agentHead = new ArrayList<>();

                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);

                            String grand_quantity = jsonObject.getString("total_quantity");
                            String grand_total = jsonObject.getString("grand_total");

                            totalQuantity.setText(grand_quantity);
                            grandTotal.setText(grand_total);

                            agentHead.add(jsonObject.getString("agent_name"));
                            agentHead.add(jsonObject.getString("agent_code"));
                            agentHead.add(jsonObject.getString("total_quantity"));
                            agentHead.add(jsonObject.getString("grand_total"));
                            agentHead.add(jsonObject.getString("date"));

                            agentHash.put("0",agentHead);
                            JSONObject agent_winnings = jsonObject.getJSONObject("agent_winnings");
                            JSONArray subagent_winnings = jsonObject.getJSONArray("subagent_winnings");

                            int subagent_count = subagent_winnings.length();

                            String prices[] = {"first","consolation2","consolation1","second","third","fourth","fifth","sixth"};
                            String prices1[] = {"1st","C2","C1","2nd","3rd","4th","5th","6th"};
                            //ArrayList<String> agentChildList = new ArrayList<>();

//                            JSONArray first  = agent_winnings.getJSONArray("first");
//                            JSONArray console2 = agent_winnings.getJSONArray("consolation2");
//                            JSONArray console1 = agent_winnings.getJSONArray("consolation1");
//                            JSONArray second = agent_winnings.getJSONArray("second");
//                            JSONArray third = agent_winnings.getJSONArray("third");
//                            JSONArray fourth = agent_winnings.getJSONArray("fourth");
//                            JSONArray fifth = agent_winnings.getJSONArray("fifth");
//                            JSONArray sixth = agent_winnings.getJSONArray("sixth");


                            for(int i=0;i<prices.length;i++)
                            {

                                JSONArray winnings = agent_winnings.getJSONArray(prices[i]).getJSONObject(0).getJSONArray("winnings");

                                if(winnings.length()!=0)
                                {

                                    for(int j=0;j<winnings.length();j++)
                                    {

                                        JSONObject tktObject = winnings.getJSONObject(j);
                                        String customer = tktObject.getString("customer");
                                        String ticket = tktObject.getString("type")+" - "+tktObject.getString("number");
                                        String price = prices1[i];
                                        String quantity = tktObject.getString("quantity");
                                        String total = tktObject.getString("total");

                                        agentChild.add("");
                                        agentChild.add(customer);
                                        agentChild.add(ticket);
                                        agentChild.add(price);
                                        agentChild.add(quantity);
                                        agentChild.add(total);

                                    }
                                    Log.d("Winnings_"+prices[i],String.valueOf(winnings));

                                }

                            }



                            subagentHash.put("0",agentChild);

                            if(subagent_count!=0)
                            {
                                for(int s=0;s<subagent_count;s++)
                                {
                                    String subagent_name = subagent_winnings.getJSONObject(s).getString("subagent_name");
                                    String subagent_code = subagent_winnings.getJSONObject(s).getString("subagent_code");
                                    String sub_date = "0.00";
                                    String subtotal_winnings = "0.00";
                                    String subtotal_winning_amount = subagent_winnings.getJSONObject(s).getString("date");;

                                    List<String> subagentHead = new ArrayList<>();

                                    subagentHead.add(subagent_name);
                                    subagentHead.add(subagent_code);
                                    subagentHead.add(sub_date);
                                    subagentHead.add(subtotal_winnings);
                                    subagentHead.add(subtotal_winning_amount);


                                    agentHash.put(String.valueOf(s+1),subagentHead);

                                    JSONArray sub_prices = subagent_winnings.getJSONObject(s).getJSONArray("prices");
                                    JSONObject subprice_object = sub_prices.getJSONObject(0);

                                    List<String> subagentChild = new ArrayList<>();
                                    for(int sp=0;sp<prices.length;sp++)
                                    {

                                        JSONArray subagent_array = subprice_object.getJSONArray(prices[sp]).getJSONObject(0).getJSONArray("winnings");
                                        int subagentwin_count = subagent_array.length();

                                        if(subagentwin_count!=0)
                                        {


                                            for(int sw=0;sw<subagentwin_count;sw++)
                                            {
                                                JSONObject subWinObject = subagent_array.getJSONObject(sw);
                                                String subagentName = subWinObject.getString("subagent_name");
                                                String customerName = subWinObject.getString("customer");
                                                String tktName = subWinObject.getString("type")+subWinObject.getString("number");
                                                String quantity = subWinObject.getString("quantity");
                                                String total = subWinObject.getString("agent_total");

                                                subagentChild.add(subagentName);
                                                subagentChild.add(customerName);
                                                subagentChild.add(tktName);
                                                subagentChild.add(prices[sp]);
                                                subagentChild.add(quantity);
                                                subagentChild.add(total);


                                            }
                                        }


                                    }

                                    subagentHash.put(String.valueOf(s+1),subagentChild);


                                }

                            }




                            //subagentHash.put("1",agentChild);

                            AgentWinnersAdapter agentWinnersAdapter = new AgentWinnersAdapter(getContext(),agentHash,subagentHash);

                            expandableListView.setAdapter(agentWinnersAdapter);

                            //Log.d("first price", String.valueOf(first_price));







                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //Log.d("Responsce",response);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("from_date", fromDate_1);
                        params.put("to_date", toDate_1 );
                        params.put("product_name", product_name);
                        params.put("agent_id", agentid);
                        params.put("subagent_id", "*");
                        params.put("customer", customer_name);
                        return params;
                    }

                };


                MySingleton.getInstance(getContext()).addToRequestque(stringRequest);

                progressDialog.setTitle("Please Wait...");
                progressDialog.setMessage("Loading Data...Please Wait...");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(true); // disable dismiss by tapping outside of the dialog
                progressDialog.show();


            }
        });










        return view;
    }

    public String getDate( int year, int month, int date)
    {

        String date_1="",month_1="",final_date="";


        if(date<10)
        {
            date_1 = "0"+date;
        }
        else
        {
            date_1 = ""+date;
        }

        if((month+1) <10)
        {
            month_1 = "0"+(month+1);
        }
        else
        {
            month_1 = ""+(month+1);
        }

        final_date = date_1+"-"+month_1+"-"+year;

        return final_date;
    }

}
