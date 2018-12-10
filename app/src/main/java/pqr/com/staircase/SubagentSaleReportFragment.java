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
import android.widget.CheckBox;
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
public class SubagentSaleReportFragment extends Fragment {

    EditText fromDate,toDate,customerName;
    Spinner productSpinner;
    Button saleReportButton;
    ListView saleReportListView;
    TextView totalQuantity,grandTotal;
    CheckBox consolidateCheckbox;

    ArrayList<String> productNames;
    ArrayAdapter<String> productNamesAdapter;

    AgentSaleReportAdapter agentSaleReportAdapter;
    AgentSaleReportDataProvider agentSaleReportDataProvider;
    ProgressDialog progressDialog1;

    String agentSaleReportUrl = "http://192.168.1.8:8000/api/subagentsalereport";


    public SubagentSaleReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_subagent_sale_report, container, false);

        fromDate = view.findViewById(R.id.fromDate);
        toDate = view.findViewById(R.id.toDate);
        customerName = view.findViewById(R.id.customer);
        productSpinner = view.findViewById(R.id.productSpinner);
        consolidateCheckbox = view.findViewById(R.id.consolidateCheckbox);
        saleReportButton = view.findViewById(R.id.winnersReportButton);
        saleReportListView = view.findViewById(R.id.saleReportListView);
        totalQuantity = view.findViewById(R.id.totalQuantity);
        grandTotal = view.findViewById(R.id.grandTotal);

        productNames = SubagentData.getInstance().getProductNames();

        progressDialog1 = new ProgressDialog(getContext());
        productNamesAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,productNames);

        productSpinner.setAdapter(productNamesAdapter);

        agentSaleReportAdapter = new AgentSaleReportAdapter(getContext(),R.layout.agentsalereport_layout);


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
                final String customer_1 = customerName.getText().toString();
                final String product_name = productSpinner.getSelectedItem().toString();
                String consolidate = null;

                agentSaleReportAdapter.clear();

                if(consolidateCheckbox.isChecked()) {
                    consolidate = "yes";
                }
                else {
                    consolidate = "no";
                }

                saleReportButton.setEnabled(false);

                final String finalConsolidate = consolidate;
                StringRequest salereportRequest = new StringRequest(Request.Method.POST, agentSaleReportUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {
                            int totalQuant = 0;
                            float grandTotal1 = 0;
                            progressDialog1.dismiss();
                            saleReportButton.setEnabled(true);
                            JSONObject agentsale_object = new JSONObject(response);
                            JSONArray agentsale_array = agentsale_object.getJSONArray("subagent_salereport");
                            int agentsale_length = agentsale_array.length();
                            if(agentsale_length!=0)
                            {
                                for(int i=0;i<agentsale_length;i++)
                                {
                                    String sale_date = agentsale_array.getJSONObject(i).getString("date");

                                    agentSaleReportDataProvider = new AgentSaleReportDataProvider("","",sale_date,"","");
                                    agentSaleReportAdapter.add(agentSaleReportDataProvider);


                                    JSONArray bill_array = agentsale_array.getJSONObject(i).getJSONArray("bills");
                                    int bill_length = bill_array.length();

                                    if(bill_length!=0)
                                    {
                                        for(int j=0;j<bill_length;j++)
                                        {

                                            String bill_number = bill_array.getJSONObject(j).getString("bill_number");
                                            String customer = bill_array.getJSONObject(j).getString("customer");
                                            String total_quantity = bill_array.getJSONObject(j).getString("total_quantity");
                                            String grand_total = bill_array.getJSONObject(j).getString("grand_total");

                                            totalQuant += Integer.parseInt(total_quantity);
                                            grandTotal1 += Float.parseFloat(grand_total);

                                            agentSaleReportDataProvider = new AgentSaleReportDataProvider(bill_number,customer,"",total_quantity,grand_total);
                                            agentSaleReportAdapter.add(agentSaleReportDataProvider);

                                            if(finalConsolidate.equals("no"))
                                            {
                                                JSONArray tickets_array = bill_array.getJSONObject(j).getJSONArray("tickets");

                                                int tickets_length = tickets_array.length();

                                                if(tickets_length!=0)
                                                {
                                                    for(int k=0;k<tickets_length;k++)
                                                    {

                                                        String bill_num = tickets_array.getJSONObject(0).getString("sale_id");
                                                        String ticket_type = tickets_array.getJSONObject(k).getString("type");
                                                        String number = tickets_array.getJSONObject(k).getString("number");
                                                        String quantity = tickets_array.getJSONObject(k).getString("quantity");
                                                        String total = tickets_array.getJSONObject(k).getString("total");


                                                        agentSaleReportDataProvider = new AgentSaleReportDataProvider(bill_num,customer,(ticket_type+number),quantity,total);
                                                        agentSaleReportAdapter.add(agentSaleReportDataProvider);

                                                    }
                                                }
                                            }

                                        }
                                    }


                                }
                            }
                            totalQuantity.setText(String.valueOf(totalQuant));
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
                        params.put("customer", customer_1);
                        params.put("consolidate", finalConsolidate);

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
