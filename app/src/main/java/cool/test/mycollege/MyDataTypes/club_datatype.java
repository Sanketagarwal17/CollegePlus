package cool.test.mycollege.MyDataTypes;

/**
 * Created by Vipin soni on 04-08-2018.
 */

public class club_datatype {

    String club_name,link;
    int image_id,desc;

    public club_datatype(String club_name, String link, int desc, int image_id) {
        this.club_name = club_name;
        this.link = link;
        this.desc = desc;
        this.image_id = image_id;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getDesc() {
        return desc;
    }

    public void setDesc(int desc) {
        this.desc = desc;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }
}
