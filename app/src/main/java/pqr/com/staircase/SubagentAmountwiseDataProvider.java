package pqr.com.staircase;

/**
 * Created by ibm on 29/11/2018.
 */

public class SubagentAmountwiseDataProvider
{
    private String date,sale_amount,winning_amount,balance;

    public SubagentAmountwiseDataProvider(String date, String sale_amount, String winning_amount, String balance)
    {
        this.setDate(date);
        this.setSale_amount(sale_amount);
        this.setWinning_amount(winning_amount);
        this.setBalance(balance);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSale_amount() {
        return sale_amount;
    }

    public void setSale_amount(String sale_amount) {
        this.sale_amount = sale_amount;
    }

    public String getWinning_amount() {
        return winning_amount;
    }

    public void setWinning_amount(String winning_amount) {
        this.winning_amount = winning_amount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }



}
