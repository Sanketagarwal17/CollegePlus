package cool.test.mycollege;

public class FeedBack {
    String smiley,cat,message;

    public FeedBack() {
    }

    public FeedBack(String smiley, String cat, String message) {
        this.smiley = smiley;
        this.cat = cat;
        this.message = message;
    }

    public String getSmiley() {
        return smiley;
    }

    public String getCat() {
        return cat;
    }

    public String getMessage() {
        return message;
    }
}
