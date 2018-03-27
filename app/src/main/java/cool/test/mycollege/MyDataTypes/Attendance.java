package cool.test.mycollege.MyDataTypes;

/**
 * Created by Augustine on 3/4/2018.
 */

public class Attendance {
    public int numOfSubjects;
    public String semesterNum;
    public boolean isFirstyear=false;
    public int numOfPractical;
    public int numOfSessional;
    public int numOfTheory;



    public Attendance(int n) {

        numOfSubjects=n;
        Subject[] studentSubjects = new Subject[n];
    }



}
