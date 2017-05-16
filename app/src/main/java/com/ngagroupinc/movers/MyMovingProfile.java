package com.ngagroupinc.movers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MyMovingProfile extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    EditText from_edzip, to_edzip, ed_date;
    ImageView iv_date;
    Spinner sp_spinner;
    Calendar calender;
    int cyear, cmonth, cday;
    String selected_spinner_item;
    List<String> categories_rooms;
    Button btn_quotes,btn_skip;
    String get_from_zip, get_to_zip, get_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_my_moving_profile);
        init();
        insertRoomItemsToList();
        DisplayRoomsListToSpinner();

        iv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);

            }
        });
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyMovingProfile.this,SharingActivity.class));
            }
        });
        btn_quotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "checking....", Toast.LENGTH_SHORT).show();
                getInputValues();
                checkValidations();

            }
        });

        getAndShowDateFromCal();

    }

    private void init() {
        from_edzip = (EditText) findViewById(R.id.ed_city_from);
        to_edzip = (EditText) findViewById(R.id.ed_city_to);
        ed_date = (EditText) findViewById(R.id.ed_date);
        sp_spinner = (Spinner) findViewById(R.id.sp_select);
        sp_spinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        iv_date = (ImageView) findViewById(R.id.iv_date);
        btn_quotes = (Button) findViewById(R.id.btn_giveme_quotes);
        btn_skip=(Button)findViewById(R.id.btn_next);
        categories_rooms = new ArrayList<>();
        sp_spinner.setOnItemSelectedListener(this);


    }

    private void getAndShowDateFromCal() {
        calender = Calendar.getInstance();
        cyear = calender.get(Calendar.YEAR);
        cmonth = calender.get(Calendar.MONTH);
        cday = calender.get(Calendar.DAY_OF_MONTH);
        showDate(cyear, cmonth + 1, cday);
    }

    private void insertRoomItemsToList() {
        categories_rooms.add("PartialHome");
        categories_rooms.add("Studio");
        categories_rooms.add("1 small Bedroom");
        categories_rooms.add("1 Large Bedroom");
        categories_rooms.add("2 BedRoom");
        categories_rooms.add("3 BedRoom");
        categories_rooms.add("4 BedRoom");
        categories_rooms.add(">4 BedRoom");
    }

    private void getInputValues() {
        get_from_zip = from_edzip.getText().toString().trim();
        get_to_zip = to_edzip.getText().toString().trim();
        get_date = ed_date.getText().toString().trim();
    }

    private void DisplayRoomsListToSpinner() {
        ArrayAdapter<String> rooms_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_rooms);
        rooms_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_spinner.setAdapter(rooms_adapter);
    }

    private void showDate(int year, int month, int day) {
        ed_date.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            DatePickerDialog _date =   new DatePickerDialog(this, my_date_listener, cyear,cmonth,
                    cday){
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                {
                    if (year < cyear)
                        view.updateDate(cyear, cmonth, cday);

                    if (monthOfYear < cmonth && year == cyear)
                        view.updateDate(cyear, cmonth, cday);

                    if (dayOfMonth < cday && year == cyear && monthOfYear == cmonth)
                        view.updateDate(cyear, cmonth, cday);

                }
            };
            return _date;
        }
        return null;
    }

        //}
      //  return null;
    //}

    private DatePickerDialog.OnDateSetListener my_date_listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            showDate(year, month + 1, dayOfMonth);
            SharedPreferences s = getApplicationContext().getSharedPreferences("uma", MODE_PRIVATE);
            SharedPreferences.Editor editor_date = s.edit();
            editor_date.putString("Date", dayOfMonth + "/" + month + "/" + year);
            editor_date.commit();

        }


    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected_spinner_item = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void checkValidations() {
        if ((from_edzip.getText().toString().trim().equalsIgnoreCase("") || (to_edzip.getText().toString().trim().equalsIgnoreCase("")))||(from_edzip.getText().toString().trim().equalsIgnoreCase("") && (to_edzip.getText().toString().trim().equalsIgnoreCase("")))) {
            if ((from_edzip.getText().toString().trim().equalsIgnoreCase("") && (to_edzip.getText().toString().trim().equalsIgnoreCase("")))) {
                Toast.makeText(getApplicationContext(), "please fill source and destination zip values", Toast.LENGTH_SHORT).show();
            }else
            {
                if ((from_edzip.getText().toString().trim().equalsIgnoreCase("") || (to_edzip.getText().toString().trim().equalsIgnoreCase("")))) {
                    if (from_edzip.getText().toString().trim().equalsIgnoreCase("")) {
                        from_edzip.setError(getString(R.string.enter_from_validation));
                        from_edzip.requestFocus();
                    } else if (to_edzip.getText().toString().trim().equalsIgnoreCase("")) {
                        to_edzip.setError(getString(R.string.enter_to_validation));
                        to_edzip.requestFocus();
                    }
                }
            }
        }
            else if (!(from_edzip.getText().toString().startsWith("5")) || (from_edzip.getText().toString().length() != 6)) {
            if (!(from_edzip.getText().toString().startsWith("5"))) {
                from_edzip.setError("you zip should starts with 5");
                to_edzip.requestFocus();

            } else {
                from_edzip.setError("you zip should contains 6 digits");
                to_edzip.requestFocus();

            }
        } else if (!(to_edzip.getText().toString().startsWith("5")) || (to_edzip.getText().toString().length() != 6)) {
            if (!(to_edzip.getText().toString().startsWith("5"))) {
                to_edzip.setError("you zip should starts with 5");
                to_edzip.requestFocus();

            } else {
                to_edzip.setError("you zip should contains 6 digits");
                to_edzip.requestFocus();

            }
        }else if ((from_edzip.getText().toString().startsWith("5")) && ((from_edzip).getText().toString().length() == 6) && ((to_edzip.getText().toString().startsWith("5")) && (to_edzip.getText().toString().length() == 6))) {
            Toast.makeText(getApplicationContext(), "values inserted successfully", Toast.LENGTH_SHORT).show();
        }

    }
}
