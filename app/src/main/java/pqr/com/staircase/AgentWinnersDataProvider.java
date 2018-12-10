package pqr.com.staircase;

/**
 * Created by ibm on 17/11/2018.
 */

public class AgentWinnersDataProvider {

    String subagent,customer,ticket,price,quantity,total;

    public AgentWinnersDataProvider(String subagent, String customer, String ticket, String price, String quantity, String total)
    {

        this.setSubagent(subagent);
        this.setCustomer(customer);
        this.setTicket(ticket);
        this.setPrice(price);
        this.setQuantity(quantity);
        this.setTotal(total);


    }

    public String getSubagent() {
        return subagent;
    }

    public void setSubagent(String subagent) {
        this.subagent = subagent;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
