package pqr.com.staircase;

/**
 * Created by ibm on 02/11/2018.
 */

public class TicketsDataProvider
{
    private String ticket_type,ticket_number,row_quantity,row_total;

    public TicketsDataProvider(String ticket_type,String ticket_number,String row_quantity,String row_total)
    {
        this.setTicket_type(ticket_type);
        this.setTicket_number(ticket_number);
        this.setRow_quantity(row_quantity);
        this.setRow_total(row_total);
    }

    public String getTicket_type() {
        return ticket_type;
    }

    public void setTicket_type(String ticket_type) {
        this.ticket_type = ticket_type;
    }

    public String getTicket_number() {
        return ticket_number;
    }

    public void setTicket_number(String ticket_number) {
        this.ticket_number = ticket_number;
    }

    public String getRow_quantity() {
        return row_quantity;
    }

    public void setRow_quantity(String row_quantity) {
        this.row_quantity = row_quantity;
    }

    public String getRow_total() {
        return row_total;
    }

    public void setRow_total(String row_total) {
        this.row_total = row_total;
    }
}
