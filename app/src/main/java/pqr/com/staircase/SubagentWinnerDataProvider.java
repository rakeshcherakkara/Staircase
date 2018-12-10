package pqr.com.staircase;

/**
 * Created by ibm on 29/11/2018.
 */

public class SubagentWinnerDataProvider
{
    String billno,customer,price,ticket,quantity,total;



    public SubagentWinnerDataProvider(String billno, String customer, String price, String ticket, String quantity, String total)
    {
        this.setBillno(billno);
        this.setCustomer(customer);
        this.setPrice(price);
        this.setTicket(ticket);
        this.setQuantity(quantity);
        this.setTotal(total);
    }



    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
