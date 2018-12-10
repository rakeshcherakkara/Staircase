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
 * Created by ibm on 26/11/2018.
 */

public class ResultReportAdapter extends ArrayAdapter {


    private List list = new ArrayList();

    public ResultReportAdapter(@NonNull Context context, int resource) {

        super(context, resource);
    }


    public void add(ResultReportDataProvider object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row;

        row = convertView;

        ResultReportHolder resultReportHolder;
        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(R.layout.result_reportlist,parent,false);

            resultReportHolder = new ResultReportHolder();
            resultReportHolder.res_sno = row.findViewById(R.id.res_sno);
//            resultReportHolder.res_date = row.findViewById(R.id.res_date);
//            resultReportHolder.res_product = row.findViewById(R.id.res_product);
//            resultReportHolder.res_price_name = row.findViewById(R.id.res_price_name);
//            resultReportHolder.res_price_value = row.findViewById(R.id.res_price_value);
            resultReportHolder.res_price_number = row.findViewById(R.id.res_price_number);

            row.setTag(resultReportHolder);

        }
        else
        {
            resultReportHolder = (ResultReportHolder) row.getTag();
        }

        ResultReportDataProvider resultReportDataProvider = (ResultReportDataProvider)this.getItem(position);

        resultReportHolder.res_sno.setText(resultReportDataProvider.getSerialNum());
//        resultReportHolder.res_date.setText(resultReportDataProvider.getResultDate());
//        resultReportHolder.res_product.setText(resultReportDataProvider.getProductName());
//        resultReportHolder.res_price_name.setText(resultReportDataProvider.getPriceName());
//        resultReportHolder.res_price_value.setText(resultReportDataProvider.getPriceValue());
        resultReportHolder.res_price_number.setText(resultReportDataProvider.getPriceNumber());

        return row;
    }


    static class ResultReportHolder
    {
        TextView res_sno,res_date,res_product,res_price_name,res_price_value,res_price_number;
    }



}
