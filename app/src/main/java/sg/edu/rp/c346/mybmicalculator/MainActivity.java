package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    Button btnCalculate;
    Button btnReset;
    TextView tvDate;
    TextView tvBMI;
    TextView tvComment;

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String datemsg =  prefs.getString("date","Last Calculated Date: ");
        String bmimsg =  prefs.getString("bmi","Last Calculated Date: 0.0");
        String commentmsg =  prefs.getString("comment","");
        tvDate.setText(datemsg);
        tvBMI.setText(bmimsg);
        tvComment.setText(commentmsg);
    }

    @Override
    protected void onPause() {
        super.onPause();
        String lastDate = tvDate.getText().toString();
        String lastBMI = tvBMI.getText().toString();
        String lastComment = tvComment.getText().toString();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("date" ,lastDate);
        prefEdit.putString("bmi" ,lastBMI);
        prefEdit.putString("comment" ,lastComment);
        prefEdit.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvBMI = findViewById(R.id.textViewBMI);
        tvDate = findViewById(R.id.textViewDate);
        tvComment = findViewById(R.id.textViewComment);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etWeight.setText("");
                etHeight.setText("");
                tvDate.setText("Last Calculated Date: ");
                tvBMI.setText("Last Calculated BMI: 0.0 ");
                tvComment.setText("");
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.clear().commit();
            }

        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Float weight = Float.parseFloat(etWeight.getText().toString());
                Float height = Float.parseFloat(etHeight.getText().toString());
                Float BMI = weight/(height * height);
                String bmimsg = String.format("Last Calculated BMI: " + BMI.toString());
                tvBMI.setText(bmimsg);

                if(BMI < 18.5){
                    tvComment.setText(R.string.underweight);
                }
                else if(BMI < 24.9){
                    tvComment.setText(R.string.normal);
                }
                else if(BMI < 29.9){
                    tvComment.setText(R.string.overweight);
                }
                else {
                    tvComment.setText(R.string.obese);
                }

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);
                String datemsg = String.format("Last Calculated Date: " + datetime.toString());
                tvDate.setText(datemsg);
            }
        });

    }


}
