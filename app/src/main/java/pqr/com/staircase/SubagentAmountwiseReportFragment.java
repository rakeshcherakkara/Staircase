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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubagentAmountwiseReportFragment extends Fragment {

    EditText fromDate,toDate;
    Spinner productSpinner;
    Button saleReportButton;
    ListView saleReportListView;
    TextView grandTotal;

    ArrayList<String> productNames;
    ArrayAdapter<String> productNamesAdapter;

    SubagentAmountwiseAdapter agentSaleReportAdapter;
    SubagentAmountwiseDataProvider agentSaleReportDataProvider;
    ProgressDialog progressDialog1;

    String agentSaleReportUrl = "http://192.168.1.8:8000/api/subagentamountwisereport";


    public SubagentAmountwiseReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subagent_amountwise_report, container, false);

        fromDate = view.findViewById(R.id.fromDate);
        toDate = view.findViewById(R.id.toDate);
        productSpinner = view.findViewById(R.id.productSpinner);
        saleReportButton = view.findViewById(R.id.winnersReportButton);
        saleReportListView = view.findViewById(R.id.saleReportListView);
        grandTotal = view.findViewById(R.id.grandTotal);

        productNames = SubagentData.getInstance().getProductNames();

        progressDialog1 = new ProgressDialog(getContext());
        productNamesAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,productNames);

        productSpinner.setAdapter(productNamesAdapter);

        agentSaleReportAdapter = new SubagentAmountwiseAdapter(getContext(),R.layout.subagent_amountwise_layout);


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


        saleReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fromDate_1 = fromDate.getText().toString();
                final String toDate_1 = toDate.getText().toString();
                final int subagent_id = SubagentData.getInstance().getSubagent_id();
                final String product_name = productSpinner.getSelectedItem().toString();


                agentSaleReportAdapter.clear();
                saleReportButton.setEnabled(false);

                StringRequest salereportRequest = new StringRequest(Request.Method.POST, agentSaleReportUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            float grandTotal1 = 0;
                            progressDialog1.dismiss();
                            saleReportButton.setEnabled(true);
                            JSONObject agentsale_object = new JSONObject(response);
                            JSONArray agentsale_array = agentsale_object.getJSONArray("subagent_amountwise");
                            //Log.d("array",String.valueOf(agentsale_array));
                            int agentsale_length = agentsale_array.length();
                            if(agentsale_length!=0)
                            {
                                for(int i=0;i<agentsale_length;i++)
                                {
                                    String sale_date = agentsale_array.getJSONObject(i).getString("date");

                                    JSONArray bill_array = agentsale_array.getJSONObject(i).getJSONArray("amount");
                                    int bill_length = bill_array.length();

                                    if(bill_length!=0)
                                    {
                                        String sale_amount = bill_array.getJSONObject(0).getString("sale_amount");
                                        String grand_total = bill_array.getJSONObject(0).getString("winning_amount");
                                        String balance = bill_array.getJSONObject(0).getString("balance");

                                        grandTotal1 += Float.parseFloat(grand_total);

                                        agentSaleReportDataProvider = new SubagentAmountwiseDataProvider(sale_date,sale_amount,grand_total,balance);
                                        agentSaleReportAdapter.add(agentSaleReportDataProvider);
                                    }
                                    else
                                    {
                                        agentSaleReportDataProvider = new SubagentAmountwiseDataProvider(sale_date,"NA","NA","NA");
                                        agentSaleReportAdapter.add(agentSaleReportDataProvider);
                                    }


                                }
                            }

                            grandTotal.setText(String.valueOf(grandTotal1));


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
                        params.put("to_date", toDate_1);
                        params.put("product_name", product_name);
                        params.put("subagent_id", String.valueOf(subagent_id));

                        return params;
                    }
                };

                salereportRequest.setRetryPolicy(new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                MySingleton.getInstance(getContext()).addToRequestque(salereportRequest);

                progressDialog1.setTitle("Please Wait...");
                progressDialog1.setMessage("Loading Data...Please Wait...");
                progressDialog1.setIndeterminate(false);
                progressDialog1.setCancelable(true); // disable dismiss by tapping outside of the dialog
                progressDialog1.show();

            }
        });



        saleReportListView.setAdapter(agentSaleReportAdapter);
        agentSaleReportAdapter.notifyDataSetChanged();

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
