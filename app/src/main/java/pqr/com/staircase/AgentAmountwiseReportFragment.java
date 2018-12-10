package pqr.com.staircase;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
public class AgentAmountwiseReportFragment extends Fragment {

    EditText fromDate,toDate;
    Spinner productSpinner,subagentSpinner;
    Button amountwiseReportButton;
    ProgressDialog progressDialog;

    ArrayList<String> productNames,subAgents;
    ArrayAdapter<String> productNamesAdapter,subagentAdapter;

    ExpandableListView expandableListView;

    String agentamountwise_url = "http://192.168.1.8:8000/api/agentamountwisereport";


    HashMap<String,List<String>> agentHash = new HashMap<>();
    HashMap<String,List<String>> subagentHash = new HashMap<>();

    HashMap<String,List<String>> agentHash1 = new HashMap<>();
    HashMap<String,List<String>> subagentHash1 = new HashMap<>();


    public AgentAmountwiseReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agent_amountwise_report, container, false);

        fromDate = view.findViewById(R.id.fromDate);
        toDate = view.findViewById(R.id.toDate);
        productSpinner = view.findViewById(R.id.productSpinner);
        subagentSpinner = view.findViewById(R.id.subagentSpinner);

        productNames = AgentData.getInstance().getProductNames();
        subAgents = AgentData.getInstance().getSubagentNames();

        progressDialog = new ProgressDialog(getContext());
        productNamesAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,productNames);
        subagentAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,subAgents);

        productSpinner.setAdapter(productNamesAdapter);
        subagentSpinner.setAdapter(subagentAdapter);

        expandableListView = view.findViewById(R.id.agent_amountwise_listview);
        amountwiseReportButton = view.findViewById(R.id.winnersReportButton);

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



        amountwiseReportButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                final String fromDate_1 = fromDate.getText().toString();
                final String toDate_1 = toDate.getText().toString();
                final String agentname = AgentData.getInstance().getAgent_name();
                final String product_name = productSpinner.getSelectedItem().toString();
                final String subagent_name = subagentSpinner.getSelectedItem().toString();

                amountwiseReportButton.setEnabled(false);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, agentamountwise_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        amountwiseReportButton.setEnabled(true);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("agent_report");
                            JSONArray jsonArray1 = jsonObject.getJSONArray("subagent_report");

                            ArrayList<String> agentList = new ArrayList<>();
                            ArrayList<String> subagentList = new ArrayList<>();


                            int agent_count = jsonArray.length();
                            int subagent_count = jsonArray1.length();

                            if(agent_count!=0)
                            {
                                for(int i=0;i<agent_count;i++)
                                {
                                    String agent_name = AgentData.getInstance().getAgent_name();
                                    String agent_code = AgentData.getInstance().getAgent_code();
                                    String agent_balance = jsonArray.getJSONObject(i).getString("agent_balance");
                                    String subagent_balance = jsonArray.getJSONObject(i).getString("subagent_balance");
                                    String total_balance = jsonArray.getJSONObject(i).getString("total_balance");
                                    String dates = jsonArray.getJSONObject(i).getString("date");

                                    String agent_sale_amount = jsonArray.getJSONObject(i).getString("agent_sale_amount");
                                    String agent_dc_amount = jsonArray.getJSONObject(i).getString("agent_dc_amount");
                                    String agent_win_amount = jsonArray.getJSONObject(i).getString("agent_win_amount");
                                    String agent_total_amount = jsonArray.getJSONObject(i).getString("agent_total_amount");


                                    String subagent_sale_amount = jsonArray.getJSONObject(i).getString("subagent_sale_amount");
                                    String subagent_dc_amount = jsonArray.getJSONObject(i).getString("subagent_dc_amount");
                                    String subagent_win_amount = jsonArray.getJSONObject(i).getString("subagent_win_amount");
                                    String subagent_total_amount = jsonArray.getJSONObject(i).getString("subagent_total_amount");

                                    String total_sale_amount = jsonArray.getJSONObject(i).getString("total_sale_amount");
                                    String total_win_amount = jsonArray.getJSONObject(i).getString("total_win_amount");



                                    agentList.add(agent_name);
                                    agentList.add(agent_code);
                                    agentList.add(agent_balance);
                                    agentList.add(subagent_balance);
                                    agentList.add(total_balance);
                                    agentList.add(dates);

                                    subagentList.add(agent_sale_amount);
                                    subagentList.add(agent_dc_amount);
                                    subagentList.add(agent_total_amount);
                                    subagentList.add(agent_balance);

                                    subagentList.add(subagent_sale_amount);
                                    subagentList.add(subagent_dc_amount);
                                    subagentList.add(subagent_total_amount);
                                    subagentList.add(subagent_balance);

                                    subagentList.add(total_sale_amount);
                                    subagentList.add(total_win_amount);
                                    subagentList.add(total_balance);

                                }

                                agentHash.put("0",agentList);
                                subagentHash.put("0",subagentList);

                                if(subagent_count!=0)
                                {

                                    for(int j=0;j<subagent_count;j++)
                                    {

                                        ArrayList<String> submainList = new ArrayList<>();
                                        ArrayList<String> subchildList = new ArrayList<>();


                                        String subagent_name = jsonArray1.getJSONArray(j).getJSONObject(0).getString("subagent_name");
                                        String subagent_code = jsonArray1.getJSONArray(j).getJSONObject(0).getString("username");
                                        String agent_bal = "NA";
                                        String subagent_bal = jsonArray1.getJSONArray(j).getJSONObject(0).getString("subagent_balance");
                                        String master_bal = jsonArray1.getJSONArray(j).getJSONObject(0).getString("agent_balance");
                                        String dates = jsonArray1.getJSONArray(j).getJSONObject(0).getString("date");


                                        String agent_sale_amount = jsonArray1.getJSONArray(j).getJSONObject(0).getString("agent_sale_amount");
                                        String agent_dc_amount = jsonArray1.getJSONArray(j).getJSONObject(0).getString("agent_dc");
                                        String agent_win_amount = jsonArray1.getJSONArray(j).getJSONObject(0).getString("agent_total_amount");

                                        String sale_amount = jsonArray1.getJSONArray(j).getJSONObject(0).getString("sale_amount");
                                        String win_amount = jsonArray1.getJSONArray(j).getJSONObject(0).getString("total_amount");
                                        String dc = jsonArray1.getJSONArray(j).getJSONObject(0).getString("dc");


                                        submainList.add(subagent_name);
                                        submainList.add(subagent_code);
                                        submainList.add(agent_bal);
                                        submainList.add(subagent_bal);
                                        submainList.add(master_bal);
                                        submainList.add(dates);


                                        subchildList.add(agent_sale_amount);
                                        subchildList.add(agent_dc_amount);
                                        subchildList.add(agent_win_amount);
                                        subchildList.add(master_bal);

                                        subchildList.add(sale_amount);
                                        subchildList.add(win_amount);
                                        subchildList.add(dc);
                                        subchildList.add(subagent_bal);

                                        subchildList.add("NA");
                                        subchildList.add("NA");
                                        subchildList.add("NA");


                                        agentHash.put(String.valueOf(j+1),submainList);
                                        subagentHash.put(String.valueOf(j+1),subchildList);

                                    }


                                }

                                AgentAmountwiseAdapter agentAmountwiseAdapter = new AgentAmountwiseAdapter(getContext(),agentHash,subagentHash);

                                expandableListView.setAdapter(agentAmountwiseAdapter);
                            }


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
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("from_date", fromDate_1);
                        params.put("to_date", toDate_1 );
                        params.put("product_name", product_name);
                        params.put("agent_name", agentname);
                        params.put("subagent_name", subagent_name);
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
