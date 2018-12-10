package pqr.com.staircase;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ibm on 17/11/2018.
 */

public class AgentWinnersAdapter extends BaseExpandableListAdapter {

    private HashMap<String,List<String>> header_titles;
    private HashMap<String,List<String>> child_titles;
    private Context ctx;

    AgentWinnersAdapter(Context ctx,HashMap<String,List<String>> header_titles,HashMap<String,List<String>> child_titles)
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
            view = layoutInflater.inflate(R.layout.agent_winners_parent_layout,null);
        }

        TextView dates = view.findViewById(R.id.dates);
        TextView agent_name = view.findViewById(R.id.agent_name);
        TextView agent_code = view.findViewById(R.id.agent_code);
        TextView total_winnings = view.findViewById(R.id.total_winning);
        TextView total_winning_amount = view.findViewById(R.id.total_winning_amount);

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
        total_winnings.setText(agentData.get(2));
        total_winning_amount.setText(agentData.get(3));
        dates.setText(agentData.get(4));

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        //String title = (String) this.getChild(i,i1);

        if(view == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.agent_winners_child_layout,null);
        }

        List<String> agentData = new ArrayList<>();

        for(Map.Entry<String,List<String>> ee: child_titles.entrySet())
        {
            if(ee.getKey().equals(String.valueOf(i)))
            {
                agentData = ee.getValue();
            }
        }



        AgentWinnerListViewAdapter agentWinnerListViewAdapter = new AgentWinnerListViewAdapter(ctx,R.layout.agent_winnerreport_row2);
        AgentWinnersDataProvider agentWinnersDataProvider;
        ListView winnners_list_view = view.findViewById(R.id.winners_report_listview);
        winnners_list_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // disallow the onTouch for your scrollable parent view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        for(int k=1;k<=agentData.size();k++)
        {

            if(k%6==0)
            {

                String subagent = agentData.get(k-6);
                if(subagent.equals(""))
                {
                    subagent="NA";
                }
                String customer = agentData.get(k-5);
                String ticket = agentData.get(k-4);
                String price = agentData.get(k-3);
                String quantity = agentData.get(k-2);
                String total = agentData.get(k-1);
                agentWinnersDataProvider = new AgentWinnersDataProvider(subagent,customer,ticket,price,quantity,total);
                agentWinnerListViewAdapter.add(agentWinnersDataProvider);

            }

        }



        winnners_list_view.setAdapter(agentWinnerListViewAdapter);


        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
