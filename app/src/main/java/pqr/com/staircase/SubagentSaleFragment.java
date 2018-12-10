package pqr.com.staircase;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubagentSaleFragment extends Fragment {

    String tickets_url = "http://192.168.1.8:8000/api/subagentsale";
    // String tickets_url = "http://18.224.40.201/api/agentsale";
    String message_url = "http://192.168.1.8:8000/scrolling";
    TextView scrollTextView,dateTextView,timeTextView,ticketRateText,billnoText,totalQuantity,grandTotal;
    EditText customerNameText,fromTicketText,toTicketText,quantityText;
    Spinner productSpinner,serialSpinner;
    RadioGroup radioGroup;
    RadioButton type1Radio,type2Radio,type3Radio;
    CheckBox oneCheckBox,hunCheckBox,boxCheckBox,bookCheckBox,anyCheckBox;
    ListView ticketListView;
    Button addButton,cancelButton,sendButton;



    ArrayList<String> productNames,serialList,typeList,numberList,quantityList,totalList;
    ArrayAdapter<String> productListAdapter,serialListAdapter;
    TicketsAdapter ticketsAdapter;
    TicketsDataProvider ticketsDataProvider;

    int totalQuantity_1 = 0;
    float grandTotal_1 = 0.0f;


    public SubagentSaleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subagent_sale, container, false);

        int sale_block = SubagentData.getInstance().getSale_block();
        if(sale_block!=0)
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
            builder1.setTitle(getResources().getString(R.string.sale_block));
            builder1.setMessage("Your Sale Is Blocked, Contact Admin");
            builder1.setCancelable(true);
            builder1.setNegativeButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
            enableDisableViewGroup((ViewGroup) view,false);
        }


        productSpinner = view.findViewById(R.id.productSpinner);
        serialSpinner = view.findViewById(R.id.serialSpinner);
        radioGroup = view.findViewById(R.id.radioGroup);
        fromTicketText = view.findViewById(R.id.fromTicketText);
        ticketRateText = view.findViewById(R.id.ticketRateText);
        scrollTextView = view.findViewById(R.id.scrollTextView);

        ticketListView = view.findViewById(R.id.saleListView);
        ticketsAdapter = new TicketsAdapter(getContext(),R.layout.tickets_layout);

        typeList = new ArrayList<>();
        numberList = new ArrayList<>();
        quantityList = new ArrayList<>();
        totalList = new ArrayList<>();


        //getting date and inserting in dateText from server

        dateTextView = view.findViewById(R.id.dateTextView);
        dateTextView.setText(SubagentData.getInstance().getDate());

        toTicketText = view.findViewById(R.id.toTicketText);
        toTicketText.setVisibility(View.INVISIBLE);

        customerNameText = view.findViewById(R.id.customerNameText);
        customerNameText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});

        quantityText = view.findViewById(R.id.quantityText);
        quantityText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(3)});

        oneCheckBox = view.findViewById(R.id.oneCheckBox);
        hunCheckBox = view.findViewById(R.id.hunCheckBox);
        boxCheckBox = view.findViewById(R.id.box1CheckBox);
        bookCheckBox = view.findViewById(R.id.bookCheckBox);
        anyCheckBox = view.findViewById(R.id.anyCheckBox);

        type1Radio = view.findViewById(R.id.type1Radio);
        type2Radio = view.findViewById(R.id.type2Radio);
        type3Radio = view.findViewById(R.id.type3Radio);

        addButton = view.findViewById(R.id.addButton);
        cancelButton = view.findViewById(R.id.cancelButton);
        sendButton = view.findViewById(R.id.sendButton);


        totalQuantity = view.findViewById(R.id.totalQuantity);
        grandTotal = view.findViewById(R.id.grandTotal);

        totalQuantity_1 = Integer.parseInt(totalQuantity.getText().toString());
        grandTotal_1 = Float.parseFloat(grandTotal.getText().toString());


        billnoText = view.findViewById(R.id.billnoText);
        //serial list

        serialList = new ArrayList<>();
        serialList.add("Select");
        serialListAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,serialList);
        serialSpinner.setAdapter(serialListAdapter);

        //products in spinner
        productNames = SubagentData.getInstance().getProductNames();

        productListAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,productNames);

        productSpinner.setAdapter(productListAdapter);

        final String msg = "";
        scrollTextView.setText(msg);


        //sending request for scrolling message

        StringRequest stringRequest = new StringRequest(Request.Method.GET, message_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String msg1 = jsonObject.getString("message");
                    scrollTextView.setText(msg1);

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
        MySingleton.getInstance(getContext()).addToRequestque(stringRequest);

        //request over


        //radio group

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int radioButtonId) {

                RadioButton radioButton = radioGroup.findViewById(radioButtonId);

                String radioButtonType = radioButton.getText().toString();

                addSerials(radioButtonType);

            }
        });



        //checkbox selection

        anyCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(anyCheckBox.isChecked()) {
                    checkBoxSelection("any",true);
                }
                else {
                    checkBoxSelection("any",false);
                }
            }
        });

        hunCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hunCheckBox.isChecked()) {
                    checkBoxSelection("hun",true);
                }
                else {
                    checkBoxSelection("hun",false);
                }
            }
        });

        oneCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(oneCheckBox.isChecked()){
                    checkBoxSelection("one",true);
                }
                else {
                    checkBoxSelection("one",false);
                }
            }
        });

        bookCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bookCheckBox.isChecked()) {
                    checkBoxSelection("book",true);
                }
                else {
                    checkBoxSelection("book",false);
                }
            }
        });

        boxCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(boxCheckBox.isChecked()){
                    checkBoxSelection("box",true);
                }
                else {
                    checkBoxSelection("box",false);
                }
            }
        });



        //add button on click listener

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String check = checkAllFields();

                if(check.equals(""))
                {
                    //Toast.makeText(getContext(),"Good To Go",Toast.LENGTH_LONG).show();

                    String check_type = getCheckbox();


                    switch (check_type)
                    {
                        case "one":
                            addTickets("one");
                            break;

                        case "hun":
                            addTickets("hun");
                            break;

                        case "box":
                            addTickets("box");
                            break;

                        case "book":
                            addTickets("book");
                            break;

                        case "any":
                            addTickets("any");
                            break;

                        case "nothing":
                            addTickets("nothing");
                            break;

                    }

                }
                else
                {
                    Toast.makeText(getContext(),check,Toast.LENGTH_LONG).show();
                }



            }
        });



        fromTicketText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(type1Radio.isChecked()||type2Radio.isChecked()||type3Radio.isChecked())
                {
                    fromTicketText.setEnabled(true);
                    toTicketText.setEnabled(true);
                    quantityText.setEnabled(true);
                }
                else
                {
                    //Toast.makeText(getContext(),"Please Select Ticket Type",Toast.LENGTH_LONG).show();
                    fromTicketText.setEnabled(false);
                    toTicketText.setEnabled(false);
                    quantityText.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Integer fromLen = fromTicketText.getText().length();

                if(type1Radio.isChecked())
                {
                    if(anyCheckBox.isChecked())
                    {
                        if(fromLen==1)
                        {
                            toTicketText.requestFocus();
                        }
                    }
                    else
                    {
                        if(fromLen==1)
                        {
                            quantityText.requestFocus();
                        }
                    }
                }
                else if(type2Radio.isChecked())
                {
                    if(anyCheckBox.isChecked())
                    {
                        if(fromLen==2)
                        {
                            toTicketText.requestFocus();
                        }
                    }
                    else
                    {
                        if(fromLen==2)
                        {
                            quantityText.requestFocus();
                        }

                    }
                }

                if(type3Radio.isChecked())
                {

                    if(hunCheckBox.isChecked())
                    {

                        if(fromLen==3)
                        {
                            toTicketText.requestFocus();
                        }

                    }
                    else if(oneCheckBox.isChecked())
                    {
                        if(fromLen==3)
                        {
                            toTicketText.requestFocus();
                        }

                    }
                    else if(anyCheckBox.isChecked())
                    {
                        if(fromLen==3)
                        {
                            toTicketText.requestFocus();
                        }
                    }
                    else if(boxCheckBox.isChecked())
                    {
                        if(fromLen==3)
                        {
                            quantityText.requestFocus();
                        }
                    }
                    else if(bookCheckBox.isChecked())
                    {
                        if(fromLen==3)
                        {
                            //quantity.requestFocus();
                            //addButton.requestFocus();
                            fromTicketText.requestFocus();
                        }
                    }
                    else
                    {
                        if(fromLen==3)
                        {
                            quantityText.requestFocus();
                        }
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        toTicketText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Integer toLen = toTicketText.getText().length();

                if(type1Radio.isChecked())
                {
                    if(toLen==1)
                    {
                        quantityText.requestFocus();
                    }
                }
                if(type2Radio.isChecked())
                {
                    if(toLen==2)
                    {
                        quantityText.requestFocus();
                    }

                }
                if(type3Radio.isChecked())
                {
                    if(toLen==3)
                    {
                        quantityText.requestFocus();
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });





        //deleting ticket

        ticketListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setTitle("Delete Ticket");
                builder1.setMessage("Do you really want to Delete Ticket?");
                builder1.setCancelable(true);
                final int x = i;
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                int getQuantity = Integer.parseInt(quantityList.get(x));
                                float getTotal = Float.parseFloat(totalList.get(x));

                                totalQuantity_1 = totalQuantity_1-getQuantity;
                                grandTotal_1 = grandTotal_1-getTotal;

                                typeList.remove(x);
                                numberList.remove(x);
                                quantityList.remove(x);
                                totalList.remove(x);

                                ticketsAdapter.list.remove(x);
                                ticketsAdapter.notifyDataSetChanged();

                                totalQuantity.setText(String.valueOf(totalQuantity_1));
                                //totalAmount.setText(String.valueOf(total_amount));
                                grandTotal.setText(String.valueOf(String.format("%.2f",grandTotal_1)));
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();





                return false;
            }
        });



        //cancel button functionality

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity_1!=0)
                {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setTitle("Sale Cancel");
                    builder1.setMessage("Do you really want to cancel this sale?");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    ft.detach(SubagentSaleFragment.this).attach(SubagentSaleFragment.this).commit();
                                    if(type1Radio.isChecked())
                                    {
                                        type1Radio.setChecked(false);
                                    }
                                    if(type2Radio.isChecked())
                                    {
                                        type2Radio.setChecked(false);
                                    }
                                    if(type3Radio.isChecked())
                                    {
                                        type3Radio.setChecked(false);
                                    }
                                    //ticketRateText.setText("");
//                                    totalQuantity_1 = 0;
//                                    grandTotal_1 = 0.00f;
//                                    typeList.clear();
//                                    numberList.clear();
//                                    quantityList.clear();
//                                    totalList.clear();
//                                    radioGroup.clearCheck();
//                                    productSpinner.setEnabled(true);
//                                    hunCheckBox.setEnabled(false);
//                                    oneCheckBox.setEnabled(false);
//                                    boxCheckBox.setEnabled(false);
//                                    bookCheckBox.setEnabled(false);
//                                    anyCheckBox.setEnabled(false);
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else
                {
                    Toast.makeText(getContext(),"You Need Atleast One Ticket To Cancel.",Toast.LENGTH_LONG).show();
                }

            }
        });


        //send button functionality starts here

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(totalQuantity_1!=0)
                {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    sendButton.setEnabled(false);

//                disc1();

//                String saleTotal = totalAmount.getText().toString();
                    String saleCustomer = customerNameText.getText().toString();
                    String saleProduct = productSpinner.getSelectedItem().toString();

                    String saleAgentid = String.valueOf(SubagentData.getInstance().getSubagent_id());
                    String agentName = SubagentData.getInstance().getSubagent_name();

                    String masterId = String.valueOf(SubagentData.getInstance().getAgent_id());
                    String masterName = SubagentData.getInstance().getAgent_name();

                    JSONObject saleObject = new JSONObject();

                    try {
                        saleObject.put("customer_name",saleCustomer);
                        saleObject.put("agent_id",masterId);
                        saleObject.put("agent_name",masterName);
                        saleObject.put("subagent_id",saleAgentid);
                        saleObject.put("subagent_name",agentName);
                        saleObject.put("product_id",String.valueOf(SubagentData.getInstance().getProductIds(saleProduct)));
                        saleObject.put("product_name",saleProduct);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JSONArray tickets_array = new JSONArray();


                    for(int i=0;i<numberList.size();i++)
                    {
                        //Log.i("Checking JSON Arrays",typeList.get(i)+"---"+numberList.get(i)+"---"+quantList.get(i)+"---"+totalList.get(i));

                        try {
                            JSONObject tickets_object = new JSONObject();
                            tickets_object.put("type",typeList.get(i));
                            tickets_object.put("number",numberList.get(i));
                            tickets_object.put("quantity",quantityList.get(i));
                            tickets_object.put("total",totalList.get(i));
                            tickets_array.put(tickets_object);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        saleObject.put("tickets",tickets_array);
                        saleObject.put("grand_total",String.valueOf(grandTotal_1));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.i("JSON Tickets", String.valueOf(saleObject));

                    //sendting json data to url
                    final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, tickets_url, saleObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {


//                            String res = response.toString();
//                            Log.d("Output-Server",res);
//                            try {
//                                String res1 = response.getString("billid");
//                                Toast.makeText(getContext(),res+"<--->"+res1,Toast.LENGTH_LONG).show();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }

//                        disc();

                            //Toast.makeText(getContext(),response.toString(),Toast.LENGTH_LONG).show();
                            try {

//
                                // To dismiss the dialog

                                String bill = response.getString("billid");
                                int code = Integer.parseInt(bill);

                                if(code>0)
                                {




                                    Toast.makeText(getContext(),"Tickets Sent Successfully, Bill Number : "+bill,Toast.LENGTH_LONG).show();


                                    billnoText.setText(bill);
                                    //progressDialog1.dismiss();


                                    //
                                }
//                                else if(code.equals("timeover"))
//                                {
//
//                                    String timeover = response.getJSONObject("0").getString("msg");
//
//                                    Toast.makeText(getContext(),timeover,Toast.LENGTH_LONG).show();
//
//                                    //progress.dismiss();
////                                progressDialog1.dismiss();
//
//                                }
                                else
                                {

                                    //String errorMsg = response.getJSONObject("0").getString("msg");

                                    Toast.makeText(getContext(),"Error: ",Toast.LENGTH_LONG).show();

                                    //progress.dismiss();
//                                progressDialog1.dismiss();

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();

                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("Error@#$", error.getMessage());
                            //  disc();

                        }
                    })
                    {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            return headers;
                        }
                    };

                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                            0,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


                    MySingleton.getInstance(getContext()).addToRequestque(jsonObjectRequest);
//                progressDialog1.setTitle("Sending Tickets");
//                progressDialog1.setMessage("Loading...Please Wait...");
//                progressDialog1.setIndeterminate(false);
//                progressDialog1.setCancelable(true); // disable dismiss by tapping outside of the dialog
//                progressDialog1.show();

                    if(type1Radio.isChecked())
                    {
                        type1Radio.setChecked(false);
                    }
                    if(type2Radio.isChecked())
                    {
                        type2Radio.setChecked(false);
                    }
                    if(type3Radio.isChecked())
                    {
                        type3Radio.setChecked(false);
                    }
                    FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                    ft1.detach(SubagentSaleFragment.this).attach(SubagentSaleFragment.this).commit();

                    //end sending
//                    radioGroup.clearCheck();
//                    fromTicketText.setText("");
                    // toTicketText.setText("");
//                    quantityText.setText("");
//
//
//                    hunCheckBox.setChecked(false);
//                    oneCheckBox.setChecked(false);
//                    boxCheckBox.setChecked(false);
//                    bookCheckBox.setChecked(false);
//                    anyCheckBox.setChecked(false);
//
//                    hunCheckBox.setEnabled(false);
//                    oneCheckBox.setEnabled(false);
//                    boxCheckBox.setEnabled(false);
//                    bookCheckBox.setEnabled(false);
//                    anyCheckBox.setEnabled(false);

//                    totalQuantity_1 =0;
//                    grandTotal_1 = 0.00f;
//
//                    grandTotal.setText("0");
//                    totalQuantity.setText("0");

                    // ft.detach(AgentSaleFragment.this).attach(AgentSaleFragment.this).commit();
//
//                type3.setChecked(true);
//                serialList.add("ABC");
//                serialSpinner.setAdapter();

//                    hunCheckBox.setEnabled(true);
//                    oneCheckBox.setEnabled(true);
//                    boxCheckBox.setEnabled(true);
//                    bookCheckBox.setEnabled(true);
//                    anyCheckBox.setEnabled(true);



                    //sendButton.setEnabled(false);
                    //Toast.makeText(getContext(),"Page Refreshed",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getContext(),"You Need Atleast One Ticket to Send...Please Add Tickets",Toast.LENGTH_LONG).show();
                }
            }




        });



        //send button functionality ends here


        return view;




    }




    public void addSerials(String serial)
    {
        String product_name = productSpinner.getSelectedItem().toString();
        switch (serial)
        {
            case "1":
                if(!serialList.isEmpty())
                {
                    serialList.clear();
                }
                serialList.add("A");
                serialList.add("B");
                serialList.add("C");
                serialSpinner.setAdapter(serialListAdapter);
                serialListAdapter.notifyDataSetChanged();
                setLengthTicket(1);

                //calling for ticket rate by providing product name and type

                float type1_rate = SubagentData.getInstance().getRates(product_name,"1");

                ticketRateText.setText(Float.toString(type1_rate));

                break;

            case "2":
                if(!serialList.isEmpty())
                {
                    serialList.clear();
                }
                serialList.add("AB");
                serialList.add("BC");
                serialList.add("AC");
                serialSpinner.setAdapter(serialListAdapter);
                serialListAdapter.notifyDataSetChanged();
                setLengthTicket(2);

                //calling for ticket rate by providing product name and type
                float type2_rate = SubagentData.getInstance().getRates(product_name,"2");
                ticketRateText.setText(Float.toString(type2_rate));

                break;

            case "3":
                if(!serialList.isEmpty())
                {
                    serialList.clear();
                }
                serialList.add("ABC");
                serialSpinner.setAdapter(serialListAdapter);
                serialListAdapter.notifyDataSetChanged();
                setLengthTicket(3);

                //calling for ticket rate by providing product name and type
                float type3_rate = SubagentData.getInstance().getRates(product_name,"3");
                ticketRateText.setText(Float.toString(type3_rate));

                break;

        }



    }

    public void setLengthTicket(int length)
    {
        fromTicketText.setText("");
        fromTicketText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(length)});
        toTicketText.setText("");
        toTicketText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(length)});
        if(length==1 || length==2)
        {
            if(oneCheckBox.isChecked())
            {
                oneCheckBox.setChecked(false);
            }
            oneCheckBox.setEnabled(false);


            if(hunCheckBox.isChecked())
            {
                hunCheckBox.setChecked(false);
            }
            hunCheckBox.setEnabled(false);

            if(boxCheckBox.isChecked())
            {
                boxCheckBox.setChecked(false);
            }
            boxCheckBox.setEnabled(false);

            if(bookCheckBox.isChecked())
            {
                bookCheckBox.setChecked(false);
            }
            bookCheckBox.setEnabled(false);
        }

        else
        {
            oneCheckBox.setEnabled(true);
            hunCheckBox.setEnabled(true);
            boxCheckBox.setEnabled(true);
            bookCheckBox.setEnabled(true);

        }
    }

    public void checkBoxSelection(String checkType,boolean checked)
    {
        switch (checkType) {

            case "any":
                if(checked) {
                    unCheckBoxes("any");
                    toTicketText.setVisibility(View.VISIBLE);
                }
                else {
                    toTicketText.setVisibility(View.INVISIBLE);
                }
                break;
            case "book":
                if(checked) {
                    unCheckBoxes("book");
                    toTicketText.setVisibility(View.INVISIBLE);
                }
                break;
            case "box":
                if(checked) {
                    unCheckBoxes("box");
                    toTicketText.setVisibility(View.INVISIBLE);
                }
                break;
            case "hun":
                if(checked) {
                    unCheckBoxes("hun");
                    toTicketText.setVisibility(View.VISIBLE);
                }
                else {
                    toTicketText.setVisibility(View.INVISIBLE);
                }
                break;
            case "one":
                if(checked) {
                    unCheckBoxes("one");
                    toTicketText.setVisibility(View.VISIBLE);
                }
                else {
                    toTicketText.setVisibility(View.INVISIBLE);
                }
                break;

        }
    }


    public void unCheckBoxes(String checkType)
    {
        switch (checkType) {

            case "one" :
                hunCheckBox.setChecked(false);
                boxCheckBox.setChecked(false);
                bookCheckBox.setChecked(false);
                anyCheckBox.setChecked(false);
                break;
            case "hun" :
                oneCheckBox.setChecked(false);
                boxCheckBox.setChecked(false);
                bookCheckBox.setChecked(false);
                anyCheckBox.setChecked(false);
                break;
            case "box":
                oneCheckBox.setChecked(false);
                hunCheckBox.setChecked(false);
                bookCheckBox.setChecked(false);
                anyCheckBox.setChecked(false);
                break;
            case "book":
                oneCheckBox.setChecked(false);
                boxCheckBox.setChecked(false);
                hunCheckBox.setChecked(false);
                anyCheckBox.setChecked(false);
                break;
            case "any":
                oneCheckBox.setChecked(false);
                hunCheckBox.setChecked(false);
                boxCheckBox.setChecked(false);
                bookCheckBox.setChecked(false);
                break;

        }
    }




    public String checkAllFields()
    {

        String check_field = "";

        if(!(fromTicketText.getText().toString().equals("") || quantityText.getText().toString().equals("")))
        {
            //check customer name first
            String customer_name = customerNameText.getText().toString();

            if(toTicketText.getVisibility() == View.VISIBLE)
            {
                if(toTicketText.getText().toString().equals(""))
                {
                    check_field = "To Ticket Number Should Not Be Empty";
                }
            }


            if(customer_name.equals(""))
            {
                check_field = "Enter Customer Name";
            }

            //check product spinner empty or not

            String product_name = productSpinner.getSelectedItem().toString();

            if(product_name.equals(""))
            {
                check_field = "Please Select Product Name";
            }

            //checking radio group selected or not

            if(!(type1Radio.isChecked() || type2Radio.isChecked() || type3Radio.isChecked()))
            {

                check_field = "Please Select Type Of Ticket Radio";
            }


            //checking serail text spinner

            String serial_text = serialSpinner.getSelectedItem().toString();

            if(serial_text.equals(""))
            {
                check_field = "Please Select Type of Ticket";
            }






            //checking quantity entered or not



            if(type2Radio.isChecked())
            {
                if(anyCheckBox.isChecked())
                {
                    String from_text = fromTicketText.getText().toString();
                    String to_text = toTicketText.getText().toString();
                    int from_no = Integer.parseInt(from_text);
                    int to_no = Integer.parseInt(to_text);

//                        if(from_text.equals("") || to_text.equals(""))
//                        {
//                            check_field = "Please Enter From And To Ticket Numbers";
//                        }

                    if(from_text.length()!=2 && to_text.length()!=2)
                    {
                        check_field = "Please Enter Correct Ticket Numbers";
                    }

                    if(from_no>=to_no)
                    {
                        check_field = "From Number Always Less Than To Number";
                    }

                }
                else
                {
                    String from_text = fromTicketText.getText().toString();

//                        if(from_text.equals(""))
//                        {
//                            check_field = "Please Enter Ticket Number";
//                        }
                    if(from_text.length()!=2)
                    {
                        check_field = "Please Enter Correct Ticket Number";
                    }
                }
            }


            if(type1Radio.isChecked())
            {
                if(anyCheckBox.isChecked())
                {
                    String from_text = fromTicketText.getText().toString();
                    String to_text = toTicketText.getText().toString();
                    int from_no = Integer.parseInt(from_text);
                    int to_no = Integer.parseInt(to_text);
//                        if(from_text.equals("") || to_text.equals(""))
//                        {
//                            check_field = "Please Enter From And To Ticket Numbers";
//                        }

                    if(from_text.length()!=1 && to_text.length()!=1)
                    {
                        check_field = "Please Enter Correct Ticket Numbers";
                    }

                    if(from_no>=to_no)
                    {
                        check_field = "From Number Always Less Than To Number";
                    }

                }
                else
                {
                    String from_text = fromTicketText.getText().toString();

//                        if(from_text.equals(""))
//                        {
//                            check_field = "Please Enter Ticket Number";
//                        }

                    if(from_text.length()!=1)
                    {
                        check_field = "Please Enter Correct Ticket Number";
                    }

                }


            }

            if(type3Radio.isChecked())
            {
                if(anyCheckBox.isChecked() || hunCheckBox.isChecked() || oneCheckBox.isChecked())
                {
                    String from_text = fromTicketText.getText().toString();
                    String to_text = toTicketText.getText().toString();
                    int from_no = Integer.parseInt(from_text);
                    int to_no = Integer.parseInt(to_text);
//                        if(from_text.equals("") || to_text.equals(""))
//                        {
//                            check_field = "Please Enter From And To Ticket Numbers";
//                        }

                    if(from_text.length()!=3 && to_text.length()!=3)
                    {
                        check_field = "Please Enter Correct Ticket Numbers";
                    }

                    if(from_no>=to_no)
                    {
                        check_field = "From Number Always Less Than To Number";
                    }

                }
                else
                {
                    String from_text = fromTicketText.getText().toString();

//                        if(from_text.equals(""))
//                        {
//                            check_field = "Please Enter Ticket Number";
//                        }

                    if(from_text.length()!=3)
                    {
                        check_field = "Please Enter Correct Ticket Number";
                    }

                }


            }


            String quantity = quantityText.getText().toString();
            if(quantity.equals(""))
            {
                check_field = "Please Enter Quantity";
            }


        }
        else
        {
            check_field = "Please Fill All The Fields";
        }



        return check_field;
    }


    public String getCheckbox()
    {
        String checker = "nothing";
        if(oneCheckBox.isChecked())
        {
            checker = "one";
        }
        if(hunCheckBox.isChecked())
        {
            checker = "hun";
        }
        if(boxCheckBox.isChecked())
        {
            checker = "box";
        }
        if(bookCheckBox.isChecked())
        {
            checker = "book";
        }
        if(anyCheckBox.isChecked())
        {
            checker = "any";
        }

        return  checker;


    }




    public void addTickets(String check_type)
    {

        productSpinner.setEnabled(false);
        switch (check_type)
        {

            case "one":
                //add one's of tickets
                String from_no2 = fromTicketText.getText().toString();
                int one_num_from = Integer.parseInt(from_no2);
                String to_num = toTicketText.getText().toString();
                int one_num_to = Integer.parseInt(to_num);

                String quantity2 = quantityText.getText().toString();
                int qty2 = Integer.parseInt(quantity2);
                float product_rate2 = Float.parseFloat(ticketRateText.getText().toString());
                String type2 = serialSpinner.getSelectedItem().toString();


                for(int i=one_num_from;i<=one_num_to;i++)
                {

                    if(i%111==0)
                    {

                        float row_total = qty2*product_rate2;
                        String total2 = String.valueOf(row_total);
                        String ticket2 = String.valueOf(i);
                        ticketListView.setAdapter(ticketsAdapter);
                        ticketsDataProvider = new TicketsDataProvider(type2,ticket2,quantity2,String.format("%.2f",row_total));
                        typeList.add(type2);
                        numberList.add(ticket2);
                        quantityList.add(quantity2);
                        totalList.add(total2);
                        totalQuantity_1 += qty2;
                        grandTotal_1 += row_total;
                        ticketsAdapter.add(ticketsDataProvider);


                    }


                }

                totalQuantity.setText(String.valueOf(totalQuantity_1));
                grandTotal.setText(String.valueOf(String.format("%.2f",grandTotal_1)));

                fromTicketText.setText("");
                toTicketText.setText("");
                quantityText.setText("");
                oneCheckBox.setChecked(false);



                break;

            case "hun":

                String from_no3 = fromTicketText.getText().toString();
                int one_num_from1 = Integer.parseInt(from_no3);
                String to_num1 = toTicketText.getText().toString();
                int one_num_to1 = Integer.parseInt(to_num1);

                String quantity3 = quantityText.getText().toString();
                int qty3 = Integer.parseInt(quantity3);
                float product_rate3 = Float.parseFloat(ticketRateText.getText().toString());
                String type3 = serialSpinner.getSelectedItem().toString();


                for(int i=one_num_from1;i<=one_num_to1;i++)
                {

                    if(i%100==0)
                    {

                        float row_total = qty3*product_rate3;
                        String total2 = String.valueOf(row_total);
                        String ticket2 = String.valueOf(i);
                        ticketListView.setAdapter(ticketsAdapter);
                        ticketsDataProvider = new TicketsDataProvider(type3,ticket2,quantity3,String.format("%.2f",row_total));
                        typeList.add(type3);
                        numberList.add(ticket2);
                        quantityList.add(quantity3);
                        totalList.add(total2);
                        totalQuantity_1 += qty3;
                        grandTotal_1 += row_total;
                        ticketsAdapter.add(ticketsDataProvider);



                    }


                }

                totalQuantity.setText(String.valueOf(totalQuantity_1));
                grandTotal.setText(String.valueOf(String.format("%.2f",grandTotal_1)));

                fromTicketText.setText("");
                toTicketText.setText("");
                quantityText.setText("");
                hunCheckBox.setChecked(false);
                //add hundreds of tickets
                break;

            case "box":


                String from_no1 = fromTicketText.getText().toString();
                String quantity1 = quantityText.getText().toString();
                int qty = Integer.parseInt(quantity1);
                float product_rate = Float.parseFloat(ticketRateText.getText().toString());
                String type1 = serialSpinner.getSelectedItem().toString();
                ArrayList<String> ticketsbox1 = new ArrayList<>();

                Set<String> ticketsbox = new HashSet<>();

                char[] input = from_no1.toCharArray();

                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) {
                        for (int z = 0; z < 3; z++) {

                            if (x != y && y != z && z != x) {

                                ticketsbox.add(Character.toString(input[x])+Character.toString(input[y])+Character.toString(input[z]));
                                //Log.i("Box tickets---",input[x] + "" + input[y] + "" + input[z]);

                            }
                        }
                    }
                }

                Iterator it = ticketsbox.iterator();
                while(it.hasNext())
                {
                    //Log.i("SET Tickets", (String) it.next());
                    ticketsbox1.add((String)it.next());
                }

                int boxNumbers[] = new int[ticketsbox1.size()];

                for(int k=0;k<ticketsbox1.size();k++)
                {
                    boxNumbers[k] = Integer.parseInt(ticketsbox1.get(k));
                }

                for(int tk=0;tk<ticketsbox1.size();tk++)
                {

                    //RATE HERE
                    float row_total = qty*product_rate;
                    String total1 = String.valueOf(row_total);
                    String tkt = String.valueOf(ticketsbox1.get(tk));
                    typeList.add("ABC");
                    numberList.add(tkt);
                    quantityList.add(quantity1);
                    totalList.add(total1);
                    totalQuantity_1 += qty;
                    grandTotal_1 += row_total;
                    ticketListView.setAdapter(ticketsAdapter);
                    ticketsDataProvider = new TicketsDataProvider(type1,tkt,quantity1,String.format("%.2f",row_total));

                    ticketsAdapter.add(ticketsDataProvider);





                }

                totalQuantity.setText(String.valueOf(totalQuantity_1));
                grandTotal.setText(String.valueOf(String.format("%.2f",grandTotal_1)));
                fromTicketText.setText("");
                quantityText.setText("");
                boxCheckBox.setChecked(false);
                //add combination of tickets

                break;

            case "book":


                String from_no5 = fromTicketText.getText().toString();
                int one_num_from5 = Integer.parseInt(from_no5);


                float product_rate5 = Float.parseFloat(ticketRateText.getText().toString());
                String type5 = serialSpinner.getSelectedItem().toString();


                for(int i=one_num_from5;i<=(one_num_from5+4);i++)
                {

                    float row_total = 5*product_rate5;
                    String total2 = String.valueOf(row_total);
                    String ticket2 = String.valueOf(i);
                    ticketListView.setAdapter(ticketsAdapter);
                    ticketsDataProvider = new TicketsDataProvider(type5,ticket2,"5",String.format("%.2f",row_total));
                    typeList.add(type5);
                    numberList.add(ticket2);
                    quantityList.add("5");
                    totalList.add(total2);
                    totalQuantity_1 += 5;
                    grandTotal_1 += row_total;
                    ticketsAdapter.add(ticketsDataProvider);

                }

                totalQuantity.setText(String.valueOf(totalQuantity_1));
                grandTotal.setText(String.valueOf(String.format("%.2f",grandTotal_1)));

                fromTicketText.setText("");
                quantityText.setText("");
                bookCheckBox.setChecked(false);
                //add series tickets next 5

                break;

            case "any":

                String from_no4 = fromTicketText.getText().toString();
                int one_num_from4 = Integer.parseInt(from_no4);
                String to_num4 = toTicketText.getText().toString();
                int one_num_to4 = Integer.parseInt(to_num4);

                String quantity4 = quantityText.getText().toString();
                int qty4 = Integer.parseInt(quantity4);
                float product_rate4 = Float.parseFloat(ticketRateText.getText().toString());
                String type4 = serialSpinner.getSelectedItem().toString();


                for(int i=one_num_from4;i<=one_num_to4;i++)
                {

                    float row_total = qty4*product_rate4;
                    String total2 = String.valueOf(row_total);
                    String ticket2 = String.valueOf(i);
                    ticketListView.setAdapter(ticketsAdapter);
                    ticketsDataProvider = new TicketsDataProvider(type4,ticket2,quantity4,String.format("%.2f",row_total));
                    typeList.add(type4);
                    numberList.add(ticket2);
                    quantityList.add(quantity4);
                    totalList.add(total2);
                    totalQuantity_1 += qty4;
                    grandTotal_1 += row_total;
                    ticketsAdapter.add(ticketsDataProvider);

                }

                totalQuantity.setText(String.valueOf(totalQuantity_1));
                grandTotal.setText(String.valueOf(String.format("%.2f",grandTotal_1)));
                fromTicketText.setText("");
                toTicketText.setText("");
                quantityText.setText("");
                anyCheckBox.setChecked(false);
                //add series tickets

                break;

            case "nothing":

                String from_no = fromTicketText.getText().toString();
                String quantity = quantityText.getText().toString();
                String type = serialSpinner.getSelectedItem().toString();
                float ticketRate = Float.parseFloat(ticketRateText.getText().toString());
                float total_1 = Integer.parseInt(quantity) * ticketRate;
                String total = String.valueOf(total_1);
                ticketListView.setAdapter(ticketsAdapter);
                ticketsDataProvider = new TicketsDataProvider(type,from_no,quantity,String.valueOf(String.format("%.2f",total_1)));
                typeList.add(type);
                numberList.add(from_no);
                quantityList.add(quantity);
                totalList.add(total);
                totalQuantity_1 += Integer.parseInt(quantity);
                grandTotal_1 += total_1;
                ticketsAdapter.add(ticketsDataProvider);
                totalQuantity.setText(String.valueOf(totalQuantity_1));
                grandTotal.setText(String.valueOf(String.format("%.2f",grandTotal_1)));
                //add a single ticket
                fromTicketText.setText("");
                quantityText.setText("");

                break;




        }


    }

    public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);
            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }
    }




}
