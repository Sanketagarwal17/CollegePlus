package cool.test.mycollege.MyDataTypes;

/**
 * Created by Vipin soni on 22-12-2017.
 */

public class MyAttendanceData {
    private String sub;
    private int attended,total;

    public MyAttendanceData(String sub, int attended, int total) {
        this.sub = sub;
        this.attended = attended;
        this.total = total;
    }

    public String getSub() {
        return sub;
    }

    public int getAttended() {
        return attended;
    }

    public int getTotal() {
        return total;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public void setAttended(int attended) {
        this.attended = attended;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
