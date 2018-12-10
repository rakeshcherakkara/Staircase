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
 * Created by ibm on 02/11/2018.
 */

public class TicketsAdapter extends ArrayAdapter {


    List list = new ArrayList();

    public TicketsAdapter(@NonNull Context context, int resource) {

        super(context, resource);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    static class DataHandler
    {
        TextView ticket_type,ticket_number,row_quantity,row_total;

    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        list.add(object);
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

        DataHandler handler;

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.tickets_layout,parent,false);

            handler = new DataHandler();
            handler.ticket_type = row.findViewById(R.id.ticket_type);
            handler.ticket_number = row.findViewById(R.id.ticket_number);
            handler.row_quantity = row.findViewById(R.id.row_quantity);
            handler.row_total = row.findViewById(R.id.row_total);

            row.setTag(handler);


        }
        else
        {
            handler = (DataHandler) row.getTag();
        }

        TicketsDataProvider dataProvider = (TicketsDataProvider) this.getItem(position);

        handler.ticket_type.setText(dataProvider.getTicket_type());
        handler.ticket_number.setText(dataProvider.getTicket_number());
        handler.row_quantity.setText(dataProvider.getRow_quantity());
        handler.row_total.setText(dataProvider.getRow_total());

        return row;
    }
}