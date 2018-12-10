package pqr.com.staircase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ibm on 17/11/2018.
 */

public class AgentWinnerListViewAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public AgentWinnerListViewAdapter(@NonNull Context context, int resource) {

        super(context, resource);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    static class DataHandler
    {
        TextView subagent,customer,ticket,price,quantity,total;

    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public void clear() {
        list.clear();
    }


    @Override
    public int getCount() {
        return this.list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row;
        row = convertView;

        AgentWinnerListViewAdapter.DataHandler handler;

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.agent_winnerreport_row2,parent,false);

            handler = new AgentWinnerListViewAdapter.DataHandler();
            handler.subagent = row.findViewById(R.id.subagent);
            handler.customer = row.findViewById(R.id.customer);
            handler.ticket = row.findViewById(R.id.ticket);
            handler.price = row.findViewById(R.id.price);
            handler.quantity = row.findViewById(R.id.quantity);
            handler.total = row.findViewById(R.id.total);


            row.setTag(handler);


        }
        else
        {
            handler = (AgentWinnerListViewAdapter.DataHandler) row.getTag();
        }

        AgentWinnersDataProvider dataProvider = (AgentWinnersDataProvider) this.getItem(position);


        handler.subagent.setText(dataProvider.getSubagent());
        handler.customer.setText(dataProvider.getCustomer());
        handler.ticket.setText(dataProvider.getTicket());
        handler.price.setText(dataProvider.getPrice());
        handler.quantity.setText(dataProvider.getQuantity());
        handler.total.setText(dataProvider.getTotal());

        return row;
    }



}
