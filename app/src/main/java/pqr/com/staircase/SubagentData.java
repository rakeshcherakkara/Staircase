package pqr.com.staircase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ibm on 27/11/2018.
 */

public class SubagentData
{
    private static SubagentData subagentData = null;

    private SubagentData() {}

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

    public static SubagentData getInstance()
    {

        if(subagentData==null)
        {
            subagentData = new SubagentData();
        }

        return subagentData;

    }


    private int agent_id,subagent_id,sale_block,block;
    private String agent_name,subagent_name,subagent_code,date,balance,join_date;
    private HashMap<Integer,String> products = new HashMap<>();

    public HashMap<Integer, String> getProducts() {
        return products;
    }

    public void setProducts(HashMap<Integer, String> products) {
        this.products = products;
    }

    public Map<String, List<Float>> getRates() {
        return rates;
    }

    public void setRates(Map<String, List<Float>> rates) {
        this.rates = rates;
    }

    private Map<String,List<Float>> rates = new HashMap<>();


    public int getSubagent_id() {
        return subagent_id;
    }

    public void setSubagent_id(int subagent_id) {
        this.subagent_id = subagent_id;
    }

    public int getSale_block() {
        return sale_block;
    }

    public void setSale_block(int sale_block) {
        this.sale_block = sale_block;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public String getSubagent_name() {
        return subagent_name;
    }

    public void setSubagent_name(String subagent_name) {
        this.subagent_name = subagent_name;
    }

    public String getSubagent_code() {
        return subagent_code;
    }

    public void setSubagent_code(String subagent_code) {
        this.subagent_code = subagent_code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getJoin_date() {
        return join_date;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }


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

    public void setProducts(int product_id,String product_name)
    {
        products.put(product_id,product_name);
    }


}
