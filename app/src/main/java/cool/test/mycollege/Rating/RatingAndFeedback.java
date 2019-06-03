package cool.test.mycollege.Rating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import cool.test.mycollege.R;

public class RatingAndFeedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_and_feedback);
        SmileRating smileRating = (SmileRating) findViewById(R.id.smile_rating);

        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.
                switch (smiley) {
                    case SmileRating.BAD:
                        Log.i("tag", "Bad");
                        break;
                    case SmileRating.GOOD:
                        Log.i("tag", "Good");
                        break;
                    case SmileRating.GREAT:
                        Log.i("tag", "Great");
                        break;
                    case SmileRating.OKAY:
                        Log.i("tag", "Okay");
                        break;
                    case SmileRating.TERRIBLE:
                        Log.i("tag", "Terrible");
                        break;
                }
            }
        });


        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                // level is from 1 to 5 (0 when none selected)
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.
            }
        });



    }
}
