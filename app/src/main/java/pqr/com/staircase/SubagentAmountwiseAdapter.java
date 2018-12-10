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
 * Created by ibm on 29/11/2018.
 */

public class SubagentAmountwiseAdapter extends ArrayAdapter
{

    List list = new ArrayList();

    public SubagentAmountwiseAdapter(@NonNull Context context, int resource) {

        super(context, resource);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    static class DataHandler
    {
        TextView date,sale_amount,winning_amount,balance;

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

        SubagentAmountwiseAdapter.DataHandler handler;

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.subagent_amountwise_layout,parent,false);

            handler = new SubagentAmountwiseAdapter.DataHandler();
            handler.date = row.findViewById(R.id.date_amount);
            handler.sale_amount = row.findViewById(R.id.sale_amount);
            handler.winning_amount = row.findViewById(R.id.winning_amount);
            handler.balance = row.findViewById(R.id.balance);


            row.setTag(handler);


        }
        else
        {
            handler = (SubagentAmountwiseAdapter.DataHandler) row.getTag();
        }

        SubagentAmountwiseDataProvider dataProvider = (SubagentAmountwiseDataProvider) this.getItem(position);

        handler.date.setText(dataProvider.getDate());
        handler.sale_amount.setText(dataProvider.getSale_amount());
        handler.winning_amount.setText(dataProvider.getWinning_amount());
        handler.balance.setText(dataProvider.getBalance());

        return row;
    }
}
