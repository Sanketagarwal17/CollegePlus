package cool.test.mycollege.StudyNotes;

public class DownLoadStudyNotesModel {
String name,topic,url;
int year,batch;


    public DownLoadStudyNotesModel(String name, String topic, String url, int year, int batch) {
        this.name = name;
        this.topic = topic;
        this.url = url;
        this.year = year;
        this.batch = batch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }
}
