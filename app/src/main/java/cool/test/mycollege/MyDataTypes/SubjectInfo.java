package cool.test.mycollege.MyDataTypes;

/**
 * Created by Augustine on 3/10/2018.
 */

public class SubjectInfo {

    public int _id;
    public String _subjectname;

     int _attended=0, _bunked=0;
     double _percent=0.0;

    public SubjectInfo(){

        _attended=0; _bunked=0;
        _percent=0.0;
    }




   public void attendInitiated()
   {
       _attended++;
       _percent= Double.valueOf(_attended/(_attended+_bunked));
   }

   public void bunkInitiated(){
       _bunked++;
       _percent= Double.valueOf(_bunked/(_attended+_bunked));
   }
}
