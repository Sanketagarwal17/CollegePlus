package cool.test.mycollege.StudyNotes;

public class UploadStudyNotesModel {

    public String url,subjectname,subjecttopic;
    public  int year,branch;

    public UploadStudyNotesModel(String url, String subjectname, String subjecttopic, int year, int branch) {
        this.url = url;
        this.subjectname = subjectname;
        this.subjecttopic = subjecttopic;
        this.year = year;
        this.branch = branch;
    }

    public int getBranch() {
        return branch;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getSubjecttopic() {
        return subjecttopic;
    }

    public void setSubjecttopic(String subjecttopic) {
        this.subjecttopic = subjecttopic;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
