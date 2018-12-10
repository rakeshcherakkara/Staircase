package pqr.com.staircase;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ibm on 19/10/2018.
 */

public class AgentLoginDetails
{

   //String loginUrl =  "http://192.168.1.8:8000/api/agentLogin";
   ProgressDialog progressDialog1;
   //String loginUrl =  "http://192.168.0.36:8000/api/agentLogin";
   String loginUrl =  "http://18.224.40.201/api/agentLogin";

    private Context context;

    public AgentLoginDetails(Context context)
    {
        this.context = context;
        progressDialog1 = new ProgressDialog(context);
    }




    public void getAgentDetails(final String username, final String password)
    {

        ScrollingMessage scrollingMessage = new ScrollingMessage(context);
        scrollingMessage.ScrollIt();


        StringRequest loginRequest = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    progressDialog1.dismiss();
                    JSONArray jsonArray = new JSONArray(response);
                    String code = jsonArray.getJSONObject(0).getString("code");
                    Toast.makeText(context,code,Toast.LENGTH_LONG).show();

                    if(code.equals("agent_success"))
                    {

                        int agent_id = Integer.parseInt(jsonArray.getJSONObject(0).getString("agent_id"));
                        int block = Integer.parseInt(jsonArray.getJSONObject(0).getString("block"));
                        int sale_block = Integer.parseInt(jsonArray.getJSONObject(0).getString("sale_block"));
                        String sale_date = jsonArray.getJSONObject(0).getString("date");
                        String agent_name = jsonArray.getJSONObject(0).getString("agent_name");
                        String agent_code = jsonArray.getJSONObject(0).getString("agent_username");
                        String join_date = jsonArray.getJSONObject(0).getString("join_date");
                        String agent_balance = jsonArray.getJSONObject(0).getString("balance");

                        JSONArray productArray = jsonArray.getJSONObject(0).getJSONArray("agent_products");
                        JSONArray ratesArray = jsonArray.getJSONObject(0).getJSONArray("agent_rates");
                        JSONArray subagentsArray = jsonArray.getJSONObject(0).getJSONArray("subagents");

                        int product_count = productArray.length();
                        int rates_count = ratesArray.length();
                        int subagens_count = subagentsArray.length();

                        FirebaseMessaging.getInstance().subscribeToTopic("agent");

                        //service started for checking app blocked or not

                        Intent serv = new Intent(context,MyService.class);
                        context.startService(serv);


                        if(product_count!=0)
                        {
                            for(int i=0;i<product_count;i++)
                            {
                                int product_id = Integer.parseInt(productArray.getJSONObject(i).getString("product_id"));
                                String product_name = productArray.getJSONObject(i).getString("product_name");
                                FirebaseMessaging.getInstance().subscribeToTopic(product_name);

                                AgentData.getInstance().setProducts(product_id,product_name);
                            }

                        }

                        if(rates_count!=0)
                        {
                            for(int j=0;j<rates_count;j++)
                            {
                                int product_id1 = Integer.parseInt(ratesArray.getJSONObject(j).getString("product_id"));
                                String product_name1 = ratesArray.getJSONObject(j).getString("product_name");
                                Float type3_rate = Float.valueOf(ratesArray.getJSONObject(j).getString("type3_rate"));
                                Float type2_rate = Float.valueOf(ratesArray.getJSONObject(j).getString("type2_rate"));
                                Float type1_rate = Float.valueOf(ratesArray.getJSONObject(j).getString("type1_rate"));

                                List<Float> values = new ArrayList<>();
                                values.add(type1_rate);
                                values.add(type2_rate);
                                values.add(type3_rate);

                                AgentData.getInstance().setRates(product_name1,values);

                            }

                        }



                        if(subagens_count!=0)
                        {
                            for(int k=0;k<subagens_count;k++)
                            {

                                int subagent_id = Integer.parseInt(subagentsArray.getJSONObject(k).getString("subagent_id"));
                                String subagent_name = subagentsArray.getJSONObject(k).getString("subagent_name");

                                AgentData.getInstance().setSubagents(subagent_id,subagent_name);

                            }
                        }


                        AgentData.getInstance().setBlock(block);
                        AgentData.getInstance().setSale_block(sale_block);
                        AgentData.getInstance().setAgent_id(agent_id);
                        AgentData.getInstance().setAgent_name(agent_name);
                        AgentData.getInstance().setAgent_code(agent_code);
                        AgentData.getInstance().setSale_date(sale_date);
                        AgentData.getInstance().setBalance(agent_balance);
                        AgentData.getInstance().setTotal_subagents(String.valueOf(subagens_count));
                        AgentData.getInstance().setJoin_date(join_date);

                        Intent gotoAgentDashboard =  new Intent(context,AgentDashboardActivity.class);
                        context.startActivity(gotoAgentDashboard);
                    }
                    else if(code.equals("subagent_success"))
                    {

                        int agent_id = Integer.parseInt(jsonArray.getJSONObject(0).getString("subagent_id"));
                        int subagent_id = Integer.parseInt(jsonArray.getJSONObject(0).getString("subagent_id"));
                        String sale_date = jsonArray.getJSONObject(0).getString("date");
                        String subagent_name = jsonArray.getJSONObject(0).getString("subagent_name");
                        String agent_name = jsonArray.getJSONObject(0).getString("agent_name");
                        String subagent_code = jsonArray.getJSONObject(0).getString("sub_username");
                        String join_date = jsonArray.getJSONObject(0).getString("join_date");
                        String subagent_balance = jsonArray.getJSONObject(0).getString("balance");

                        JSONArray productArray = jsonArray.getJSONObject(0).getJSONArray("subagent_products");
                        JSONArray ratesArray = jsonArray.getJSONObject(0).getJSONArray("subagent_rates");

                        FirebaseMessaging.getInstance().subscribeToTopic("subagent");

                        int product_count = productArray.length();
                        int rates_count = ratesArray.length();

                        if(product_count!=0)
                        {
                            for(int i=0;i<product_count;i++)
                            {
                                int product_id = Integer.parseInt(productArray.getJSONObject(i).getString("product_id"));
                                String product_name = productArray.getJSONObject(i).getString("product_name");
                                FirebaseMessaging.getInstance().subscribeToTopic(product_name);

                                SubagentData.getInstance().setProducts(product_id,product_name);


                            }

                        }

                        if(rates_count!=0)
                        {
                            for(int j=0;j<rates_count;j++)
                            {
                                int product_id1 = Integer.parseInt(ratesArray.getJSONObject(j).getString("product_id"));
                                String product_name1 = ratesArray.getJSONObject(j).getString("product_name");
                                Float type3_rate = Float.valueOf(ratesArray.getJSONObject(j).getString("type3_rate"));
                                Float type2_rate = Float.valueOf(ratesArray.getJSONObject(j).getString("type2_rate"));
                                Float type1_rate = Float.valueOf(ratesArray.getJSONObject(j).getString("type1_rate"));

                                List<Float> values = new ArrayList<>();
                                values.add(type1_rate);
                                values.add(type2_rate);
                                values.add(type3_rate);

                                SubagentData.getInstance().setRates(product_name1,values);

                            }

                        }

                        FirebaseMessaging.getInstance().subscribeToTopic("subagent");

                        SubagentData.getInstance().setAgent_id(agent_id);
                        SubagentData.getInstance().setAgent_name(agent_name);
                        SubagentData.getInstance().setSubagent_id(subagent_id);
                        SubagentData.getInstance().setDate(sale_date);
                        SubagentData.getInstance().setSubagent_name(subagent_name);
                        SubagentData.getInstance().setSubagent_code(subagent_code);
                        SubagentData.getInstance().setJoin_date(join_date);
                        SubagentData.getInstance().setBalance(subagent_balance);


                        Intent gotoSubagentDashboard = new Intent(context,SubagentDashboardActivity.class);
                        context.startActivity(gotoSubagentDashboard);
                    }
                    else if(code.equals("agent_blocked"))
                    {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setTitle(context.getResources().getString(R.string.block));
                        builder1.setMessage("App Is Blocked.");
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
                    }
                    else
                    {
                        Toast.makeText(context,"Check Your Credentials",Toast.LENGTH_LONG).show();
                    }


                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                 Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                //Toast.makeText(context,AgentData.getInstance().getProducts(),Toast.LENGTH_LONG).show();


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
                params.put("username", username);
                params.put("password", password);
                return params;
            }

        };

        MySingleton.getInstance(context).addToRequestque(loginRequest);

        progressDialog1.setTitle("Please Wait...");
        progressDialog1.setMessage("Validating User...");
        progressDialog1.setIndeterminate(false);
        progressDialog1.setCancelable(true); // disable dismiss by tapping outside of the dialog
        progressDialog1.show();


        //this is for scrolling message




    }


}
