package cool.test.mycollege.MyDataTypes;

/**
 * Created by Vipin soni on 11-03-2018.
 */

public class StudyMaterialBookData {
    String subjectname,address,branch;
    int year,semester;

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public StudyMaterialBookData(String subjectname, String address, String branch, int year, int semester) {

        this.subjectname = subjectname;
        this.address = address;
        this.branch = branch;
        this.year = year;
        this.semester = semester;
    }
}
