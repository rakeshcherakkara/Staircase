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
 * Created by ibm on 09/11/2018.
 */

public class AgentSaleReportAdapter extends ArrayAdapter
{
    List list = new ArrayList();

    public AgentSaleReportAdapter(@NonNull Context context, int resource) {

        super(context, resource);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    static class DataHandler
    {
        TextView billno,customer,ticket,quantity,total;

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

        AgentSaleReportAdapter.DataHandler handler;

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.agentsalereport_layout,parent,false);

            handler = new AgentSaleReportAdapter.DataHandler();
            handler.billno = row.findViewById(R.id.billno_report);
            handler.customer = row.findViewById(R.id.customer_report);
            handler.ticket = row.findViewById(R.id.ticket_report);
            handler.quantity = row.findViewById(R.id.quantity_report);
            handler.total = row.findViewById(R.id.total_report);


            row.setTag(handler);


        }
        else
        {
            handler = (AgentSaleReportAdapter.DataHandler) row.getTag();
        }

        AgentSaleReportDataProvider dataProvider = (AgentSaleReportDataProvider) this.getItem(position);



        handler.billno.setText(dataProvider.getBillno());
        handler.customer.setText(dataProvider.getCustomer());
        handler.ticket.setText(dataProvider.getTicket());
        handler.quantity.setText(dataProvider.getQuantity());
        handler.total.setText(dataProvider.getTotal());

        return row;
    }

}
