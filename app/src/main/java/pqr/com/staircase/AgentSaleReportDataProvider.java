package pqr.com.staircase;

/**
 * Created by ibm on 09/11/2018.
 */

public class AgentSaleReportDataProvider
{
    String billno,customer,ticket,quantity,total;

    public AgentSaleReportDataProvider(String billno, String customer, String ticket, String quantity, String total)
    {
        this.setBillno(billno);
        this.setCustomer(customer);
        this.setQuantity(quantity);
        this.setTicket(ticket);
        this.setTotal(total);
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
