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
public class ResultReportFragment extends Fragment {




    EditText resultDate;
    Spinner productSpinnerResult;
    Button resultReportButton;
    ListView resultListView,resultListView1;
    ProgressDialog progressDialog1;

    ArrayList<String> productList;
    ArrayAdapter<String> productListAdapter;


    ResultReportAdapter resultReportAdapter;
    ResultReportAdapter1 resultReportAdapter1;

    ResultReportDataProvider resultReportDataProvider;
    ResultReportDataProvider1 resultReportDataProvider1;

    TextView firstPrice,firstPriceValue,console2Price,console2Value,console1Price,console1Value,secondPrice,secondPriceValue,thirdPriceValue,fourthPriceValue,fifthPriceValue,thirdPrice,fourthPrice,fifthPrice;
    TextView p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16,p17,p18,p19,p20,p21,p22,p23,p24,p25,p26,p27,p28,p29,p30,sixthPriceHeading;

    String result_report_url = "http://192.168.1.8:8000/api/resultreport";
    Calendar dateTime;
    int d,m,y;

    public ResultReportFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_result_report1, container, false);

        firstPrice = view.findViewById(R.id.firstPrice);
        firstPriceValue = view.findViewById(R.id.firstPriceValue);

        console2Price = view.findViewById(R.id.console2Price);
        console2Value = view.findViewById(R.id.console2Value);

        console1Price = view.findViewById(R.id.console1Price);
        console1Value = view.findViewById(R.id.console1Value);

        secondPrice = view.findViewById(R.id.secondPrice);
        secondPriceValue = view.findViewById(R.id.secondPriceValue);

        thirdPrice = view.findViewById(R.id.thirdPrice);
        thirdPriceValue = view.findViewById(R.id.thirdPriceValue);

        fourthPrice = view.findViewById(R.id.fourthPrice);
        fourthPriceValue = view.findViewById(R.id.fourthPriceValue);

        fifthPrice = view.findViewById(R.id.fifthPrice);
        fifthPriceValue = view.findViewById(R.id.fifthPriceValue);

        sixthPriceHeading = view.findViewById(R.id.sixthPriceHeading);


        p1 = view.findViewById(R.id.p1);
        p2 = view.findViewById(R.id.p2);
        p3 = view.findViewById(R.id.p3);
        p4 = view.findViewById(R.id.p4);
        p5 = view.findViewById(R.id.p5);
        p6 = view.findViewById(R.id.p6);
        p7 = view.findViewById(R.id.p7);
        p8 = view.findViewById(R.id.p8);
        p9 = view.findViewById(R.id.p9);
        p10 = view.findViewById(R.id.p10);
        p11 = view.findViewById(R.id.p11);
        p12 = view.findViewById(R.id.p12);
        p13 = view.findViewById(R.id.p13);
        p14 = view.findViewById(R.id.p14);
        p15 = view.findViewById(R.id.p15);
        p16 = view.findViewById(R.id.p16);
        p17 = view.findViewById(R.id.p17);
        p18 = view.findViewById(R.id.p18);
        p19 = view.findViewById(R.id.p19);
        p20 = view.findViewById(R.id.p20);
        p21 = view.findViewById(R.id.p21);
        p22 = view.findViewById(R.id.p22);
        p23 = view.findViewById(R.id.p23);
        p24 = view.findViewById(R.id.p24);
        p25 = view.findViewById(R.id.p25);
        p26 = view.findViewById(R.id.p26);
        p27 = view.findViewById(R.id.p27);
        p28 = view.findViewById(R.id.p28);
        p29 = view.findViewById(R.id.p29);
        p30 = view.findViewById(R.id.p30);


        resultDate = view.findViewById(R.id.resultDate);

        progressDialog1 = new ProgressDialog(getContext());

        productSpinnerResult = view.findViewById(R.id.productSpinnerResult);

        resultReportButton = view.findViewById(R.id.resultReportButton);

        productList = new ArrayList<>();
        productListAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,productList);


        String today_date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        resultDate.setText(today_date);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-7);
        final Date result = calendar.getTime();
        final int d = calendar.get(Calendar.DAY_OF_MONTH);
        final int m = calendar.get(Calendar.MONTH);
        final int y = calendar.get(Calendar.YEAR);

        resultDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog pickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        String finalDate = getDate(year,month,date);
                        resultDate.setText(finalDate);

                    }
                }, y, m, d);

                pickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()+24*60*60*1000);
                pickerDialog.getDatePicker().setMinDate(result.getTime());
                pickerDialog.show();
            }
        });



        productList = AgentData.getInstance().getProductNames();

        productListAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,productList);

        productSpinnerResult.setAdapter(productListAdapter);



        resultReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                resultReportButton.setEnabled(false);

                final String product = productSpinnerResult.getSelectedItem().toString();
                final String product_id = String.valueOf(AgentData.getInstance().getProductIds(product));
                final String sale_date = resultDate.getText().toString();



                StringRequest stringRequest = new StringRequest(Request.Method.POST, result_report_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog1.dismiss();
                        resultReportButton.setEnabled(true);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray first_array = jsonObject.getJSONArray("first");
                            JSONArray last_array = jsonObject.getJSONArray("last");

                            Log.d("prices",jsonObject.toString());

                            if(first_array.length()!=0)
                            {
                                String consoling2 = "";
                                String consoling1 = "";
                                for(int i=0;i<first_array.length();i++)
                                {

                                    String priceName = first_array.getJSONObject(i).getString("price_name");
                                    String price = first_array.getJSONObject(i).getString("price");
                                    String priceValue = first_array.getJSONObject(i).getString("price_value");
                                    String priceType = first_array.getJSONObject(i).getString("price_type");



                                    if(priceName.equals("first"))
                                    {
                                        firstPrice.setText("First Price ("+price+")");
                                        firstPriceValue.setText(priceValue);
                                    }

                                    if(priceName.equals("consolation2"))
                                    {
                                        console2Price.setText("Consolation2 Price ("+price+")");
                                        consoling2 += priceType+priceValue;
                                        //console2Value.setText(priceValue);
                                    }

                                    if(priceName.equals("consolation1"))
                                    {
                                        console1Price.setText("Consoaltion1 Price ("+price+")");
                                       // console1Value.setText(priceValue);
                                        consoling1 += priceType+priceValue;
                                    }

                                    if(priceName.equals("second"))
                                    {
                                        secondPrice.setText("Second Price ("+price+")");
                                        secondPriceValue.setText(priceValue);
                                    }

                                    if(priceName.equals("third"))
                                    {
                                        thirdPrice.setText("Third Price ("+price+")");
                                        thirdPriceValue.setText(priceValue);
                                    }

                                    if(priceName.equals("fourth"))
                                    {
                                        fourthPrice.setText("Fourth Price ("+price+")");
                                        fourthPriceValue.setText(priceValue);
                                    }

                                    if(priceName.equals("fifth"))
                                    {
                                        fifthPrice.setText("Fifth Price ("+price+")");
                                        fifthPriceValue.setText(priceValue);
                                    }


                                }

                                console2Value.setText(consoling2);
                                console1Value.setText(consoling1);



                            }

                            if(last_array.length()!=0)
                            {
                                //String priceName = last_array.getJSONObject(0).getString("price_name");
                                String priceName = "Sixth Price";
                                String price = last_array.getJSONObject(0).getString("price");

                                sixthPriceHeading.setText(priceName +"("+price+")");

                                p1.setText(last_array.getJSONObject(0).getString("price_value"));
                                p2.setText(last_array.getJSONObject(1).getString("price_value"));
                                p3.setText(last_array.getJSONObject(2).getString("price_value"));
                                p4.setText(last_array.getJSONObject(3).getString("price_value"));
                                p5.setText(last_array.getJSONObject(4).getString("price_value"));
                                p6.setText(last_array.getJSONObject(5).getString("price_value"));
                                p7.setText(last_array.getJSONObject(6).getString("price_value"));
                                p8.setText(last_array.getJSONObject(7).getString("price_value"));
                                p9.setText(last_array.getJSONObject(8).getString("price_value"));
                                p10.setText(last_array.getJSONObject(9).getString("price_value"));
                                p11.setText(last_array.getJSONObject(10).getString("price_value"));
                                p12.setText(last_array.getJSONObject(11).getString("price_value"));
                                p13.setText(last_array.getJSONObject(12).getString("price_value"));
                                p14.setText(last_array.getJSONObject(13).getString("price_value"));
                                p15.setText(last_array.getJSONObject(14).getString("price_value"));
                                p16.setText(last_array.getJSONObject(15).getString("price_value"));
                                p17.setText(last_array.getJSONObject(16).getString("price_value"));
                                p18.setText(last_array.getJSONObject(17).getString("price_value"));
                                p19.setText(last_array.getJSONObject(18).getString("price_value"));
                                p20.setText(last_array.getJSONObject(19).getString("price_value"));
                                p21.setText(last_array.getJSONObject(20).getString("price_value"));
                                p22.setText(last_array.getJSONObject(21).getString("price_value"));
                                p23.setText(last_array.getJSONObject(22).getString("price_value"));
                                p24.setText(last_array.getJSONObject(23).getString("price_value"));
                                p25.setText(last_array.getJSONObject(24).getString("price_value"));
                                p26.setText(last_array.getJSONObject(25).getString("price_value"));
                                p27.setText(last_array.getJSONObject(26).getString("price_value"));
                                p28.setText(last_array.getJSONObject(27).getString("price_value"));
                                p29.setText(last_array.getJSONObject(28).getString("price_value"));
                                p30.setText(last_array.getJSONObject(29).getString("price_value"));


//                                for(int j=1;j<=last_array.length();j++)
//                                {
//
//
//
//
////                                    if(j%6==0)
////                                    {
////                                        String l1 = last_array.getJSONObject(j-6).getString("price_value");
////                                        String l2 = last_array.getJSONObject(j-5).getString("price_value");
////                                        String l3 = last_array.getJSONObject(j-4).getString("price_value");
////                                        String l4 = last_array.getJSONObject(j-3).getString("price_value");
////                                        String l5 = last_array.getJSONObject(j-2).getString("price_value");
////                                        String l6 = last_array.getJSONObject(j-1).getString("price_value");
////
////
////
////
////                                    }
//
//
//                                }



                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        // Log.d("Result",String.valueOf(response));


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("date",sale_date);
                        params.put("product_id",product_id);
                        return  params;
                    }

                };

                MySingleton.getInstance(getContext()).addToRequestque(stringRequest);

                progressDialog1.setTitle("Please Wait...");
                progressDialog1.setMessage("Loading Data...Please Wait...");
                progressDialog1.setIndeterminate(false);
                progressDialog1.setCancelable(true); // disable dismiss by tapping outside of the dialog
                progressDialog1.show();




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

