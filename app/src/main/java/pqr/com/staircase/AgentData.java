package pqr.com.staircase;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ibm on 20/10/2018.
 */

public class AgentData
{

    private static AgentData agentData = null;

    private AgentData(){}

    public static AgentData getInstance()
    {
        if(agentData==null)
        {
            agentData = new AgentData();
        }
        return agentData;

    }

    private int agent_id;
    private int block;

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getSale_block() {
        return sale_block;
    }

    public void setSale_block(int sale_block) {
        this.sale_block = sale_block;
    }

    private int sale_block;
    private String agent_name;
    private String agent_code;
    private String message;
    private String token;
    private String title;
    private String body;
    private String balance;
    private String join_date;

    public String getJoin_date() {
        return join_date;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTotal_subagents() {
        return total_subagents;
    }

    public void setTotal_subagents(String total_subagents) {
        this.total_subagents = total_subagents;
    }

    private String total_subagents;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAgent_code() {
        return agent_code;
    }

    public void setAgent_code(String agent_code) {
        this.agent_code = agent_code;
    }

    private String sale_date;
    private HashMap<Integer,String> products = new HashMap<>();
    private HashMap<Integer,String> subagents = new HashMap<>();
    private Map<String,List<Float>> rates = new HashMap<>();



    public void setRates(String product_name, List<Float> rate)
    {
        rates.put(product_name,rate);
    }


    public Float getRates(String product_name,String type)
    {

        float ticket_rate = 0.0f;
        //ArrayList<Float> product_rates = new ArrayList<>();

//        for(Map.Entry ra : rates.entrySet())
//        {
//           // Log.d("values:-->", String.valueOf(ra.getValue()));
//
//            ra.get()
//
//        }

        //Log.d("values:--", String.valueOf(rates.get("KING")));
        //Log.d("1st value", String.valueOf(rates.get("KING").get(0)));

        switch (type)
        {
            case "3":
                ticket_rate = rates.get(product_name).get(2);
                break;
            case "2":
                ticket_rate = rates.get(product_name).get(1);
                break;
            case "1":
                ticket_rate = rates.get(product_name).get(0);
                break;
        }


        return ticket_rate;


    }


    public String getSale_date() {
        return sale_date;
    }

    public void setSale_date(String sale_date) {
        this.sale_date = sale_date;
    }


    public void setProducts(int product_id,String product_name)
    {
        products.put(product_id,product_name);
    }


    public void setSubagents(int subagent_id,String subagent_name)
    {
        subagents.put(subagent_id,subagent_name);
    }


    public ArrayList<String> getProductNames()
    {
        ArrayList<String> product_names = new ArrayList<>();

//        product_names.add("Product");
        for(Map.Entry pros : products.entrySet())
        {
            product_names.add((String) pros.getValue());
        }

        return product_names;

    }


    public ArrayList<String> getSubagentNames()
    {
        ArrayList<String> subagent_names = new ArrayList<>();

        subagent_names.add("Select Subagent");
        subagent_names.add("all");
        subagent_names.add("no");
        for(Map.Entry pros : subagents.entrySet())
        {
            subagent_names.add((String) pros.getValue());
        }

        return subagent_names;

    }


    public int getProductIds(String product_name)
    {
        //ArrayList<Integer> product_ids = new ArrayList<>();

        int product_id = 0;

        for(Map.Entry pros : products.entrySet())
        {
            if(pros.getValue().equals(product_name))
            {
                product_id = (int) pros.getKey();
            }
        }

        return product_id;

    }




    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }
}
