package cool.test.mycollege.MyDataTypes;

/**
 * Created by Augustine on 3/4/2018.
 */
/*
// TODO : Handle Mass bunks, Vacations 
   Probably a pause attendance feature?
 */
public class Subject {
    public String subjectName;
    public String subjectCode;
    public boolean isPractical = false;// Implement additional methods to determine if practical is moved or other stuff
    public boolean isSessional=false;
    public boolean isTheory=false;
    public boolean isCommon=false;
    public boolean isGreaterthan75;
    
    public int classPerWeek;
    public int classesAttended;
    public int totalClasses;
    
    
    public Subject(String name, String code)
    {
        subjectName=name;
        subjectCode=code;
    }


    public void updateAttendance(int x)
    {
        if(x==-1)//Class has been cancelled
        {

        }
        else if(x==0)// Class hasn't been attended
        {
            totalClasses++;
        }
        else if(x==1) // Class has been attended
        {
            totalClasses++;
            classesAttended++;
        }
        
        if(4*classesAttended>=3*totalClasses)
            isGreaterthan75=true;

    }
    
    
    public boolean isSafe(){
        return isGreaterthan75;
    }




}
