package pqr.com.staircase;

/**
 * Created by ibm on 26/11/2018.
 */

public class ResultReportDataProvider1
{

    private String serialNum,resultDate,productName,priceName,priceValue,priceNumber;

    public ResultReportDataProvider1(String serialNum,String resultDate,String productName,String priceName,String priceValue,String priceNumber)
    {

        this.setSerialNum(serialNum);
        this.setResultDate(resultDate);
        this.setProductName(productName);
        this.setPriceName(priceName);
        this.setPriceValue(priceValue);
        this.setPriceNumber(priceNumber);

    }



    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getResultDate() {
        return resultDate;
    }

    public void setResultDate(String resultDate) {
        this.resultDate = resultDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public String getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(String priceValue) {
        this.priceValue = priceValue;
    }

    public String getPriceNumber() {
        return priceNumber;
    }

    public void setPriceNumber(String priceNumber) {
        this.priceNumber = priceNumber;
    }
}