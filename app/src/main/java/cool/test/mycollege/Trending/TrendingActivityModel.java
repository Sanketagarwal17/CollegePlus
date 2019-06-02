package cool.test.mycollege.Trending;

public class TrendingActivityModel {

    String picurl,productname,productdesc,contactno,report,email;

    public TrendingActivityModel(String picurl, String productname, String productdesc, String contactno, String report, String email) {
        this.picurl = picurl;
        this.productname = productname;
        this.productdesc = productdesc;
        this.contactno = contactno;
        this.report = report;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductdesc() {
        return productdesc;
    }

    public void setProductdesc(String productdesc) {
        this.productdesc = productdesc;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }
}
