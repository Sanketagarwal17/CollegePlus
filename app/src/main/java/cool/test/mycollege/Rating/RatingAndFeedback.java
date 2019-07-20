package cool.test.mycollege.Rating;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import cool.test.mycollege.FeedBack;
import cool.test.mycollege.R;
import cool.test.mycollege.login;

public class RatingAndFeedback extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    Button button;
    EditText editText;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_and_feedback);
        radioGroup = findViewById(R.id.radio_grp);
        button = findViewById(R.id.btn_submit);
        editText = findViewById(R.id.et_feedback);
        mAuth = FirebaseAuth.getInstance();
         final SmileRating smileRating = (SmileRating) findViewById(R.id.smile_rating);

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



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(id);
                // Toast.makeText(RatingAndFeedback.this, "radiobutton : "+ radioButton.getText() , Toast.LENGTH_SHORT).show();
                if((smileRating.getRating() == 0 )|| (TextUtils.isEmpty(editText.getText().toString().trim())))
                {
                    Toast.makeText(RatingAndFeedback.this, "pls select some smiley", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Toast.makeText(RatingAndFeedback.this, "hurray", Toast.LENGTH_SHORT).show();
                    if(mAuth.getCurrentUser() == null)
                    {
                        startActivity(new Intent(RatingAndFeedback.this, login.class));
                        Toast.makeText(RatingAndFeedback.this, "Login first", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String uID = mAuth.getCurrentUser().getUid();
                        FeedBack feedBack = new FeedBack(String.valueOf(smileRating.getRating()),String.valueOf(radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()))),editText.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(uID).child("FeedBack").setValue(feedBack);
                        Toast.makeText(RatingAndFeedback.this, "Thanks for your feedback", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(final int level, boolean reselected) {
                // level is from 1 to 5 (0 when none selected)
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.

            }
        });



    }
}
