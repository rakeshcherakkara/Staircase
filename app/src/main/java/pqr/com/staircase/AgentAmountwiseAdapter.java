package pqr.com.staircase;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ibm on 14/11/2018.
 */

public class AgentAmountwiseAdapter extends BaseExpandableListAdapter {

    private HashMap<String,List<String>> header_titles;
    private HashMap<String,List<String>> child_titles;
    private Context ctx;

    AgentAmountwiseAdapter(Context ctx,HashMap<String,List<String>> header_titles,HashMap<String,List<String>> child_titles)
    {
        this.ctx = ctx;
        this.header_titles = header_titles;
        this.child_titles = child_titles;

    }

    @Override
    public int getGroupCount() {
        return header_titles.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return header_titles.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return child_titles.get(String.valueOf(i1));
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        //String title = (String) this.getGroup(i);

        if(view == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.agent_amountwise_parent_layout1,null);
        }

        TextView dates = view.findViewById(R.id.dates);
        TextView agent_name = view.findViewById(R.id.agent_name);
        TextView agent_code = view.findViewById(R.id.agent_code);
        TextView total_agentbal = view.findViewById(R.id.total_agent_balance);
        TextView total_subagentbal = view.findViewById(R.id.total_subagent_balance);
        TextView total_bal = view.findViewById(R.id.total_balance);

        List<String> agentData = new ArrayList<>();

        for(Map.Entry<String,List<String>> ee: header_titles.entrySet())
        {
                if(ee.getKey().equals(String.valueOf(i)))
                {
                    agentData = ee.getValue();

                }
        }

        agent_name.setText(agentData.get(0));
        agent_code.setText(agentData.get(1));
        total_agentbal.setText(agentData.get(2));
        total_subagentbal.setText(agentData.get(3));
        total_bal.setText(agentData.get(4));
        dates.setText(agentData.get(5));

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        //String title = (String) this.getChild(i,i1);

        if(view == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.agent_amountwise_child_layout1,null);
        }

        TextView agent_sale = view.findViewById(R.id.ch01);
        TextView agent_win = view.findViewById(R.id.ch11);
        TextView agent_dc = view.findViewById(R.id.ch21);
        TextView agent_bal = view.findViewById(R.id.ch31);

        TextView sub_sale = view.findViewById(R.id.ch41);
        TextView sub_win = view.findViewById(R.id.ch51);
        TextView sub_dc = view.findViewById(R.id.ch61);
        TextView sub_bal = view.findViewById(R.id.ch71);

        TextView total_sale = view.findViewById(R.id.ch81);
        TextView total_win = view.findViewById(R.id.ch91);
        TextView total_bal = view.findViewById(R.id.ch101);

        List<String> agentData = new ArrayList<>();

        for(Map.Entry<String,List<String>> ee: child_titles.entrySet())
        {
            if(ee.getKey().equals(String.valueOf(i)))
            {
                agentData = ee.getValue();
            }
        }

        Log.d("Listview", String.valueOf(agentData));

        agent_sale.setText(agentData.get(0));
        agent_win.setText(agentData.get(2));
        agent_dc.setText(agentData.get(1));
        agent_bal.setText(agentData.get(3));

        sub_sale.setText(agentData.get(4));
        sub_win.setText(agentData.get(5));
        sub_dc.setText(agentData.get(6));
        sub_bal.setText(agentData.get(7));

        total_sale.setText(agentData.get(8));
        total_win.setText(agentData.get(9));
        total_bal.setText(agentData.get(10));



        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
