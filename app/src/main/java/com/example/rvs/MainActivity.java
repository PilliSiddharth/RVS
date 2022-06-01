package com.example.rvs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity<TimeActivity> extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    EditText datepicker, inspector_id, chooseTime, building_name, address1, address2, city_town, latitude, longitude, other_structural_system, other_structural_components;

//    roof

    EditText roof, residential, educational, lifeline, commercial, office, mixeduse, industrial, other;

    boolean[] selectedroof;
    ArrayList<Integer> roof_list = new ArrayList<>();
    ArrayList<String> roof_data = new ArrayList<>();
    String[] roof_array = {"Flat", "Pitched", "Hipped", "Split"};
    int roof_ans = -1;

    boolean[] selectedresd;
    ArrayList<Integer> residential_list = new ArrayList<>();
    ArrayList<String> residential_data = new ArrayList<>();
    String[] residential_array = {"Individual House", "Apartments"};
    int residential_ans = -1;

    boolean[] selectededu;
    ArrayList<Integer> educational_list = new ArrayList<>();
    ArrayList<String> educational_data = new ArrayList<>();
    String[] educational_array = {"School", "College", "Institute/University"};
    int educational_ans = -1;

    boolean[] selectedlife;
    ArrayList<Integer> lifeline_list = new ArrayList<>();
    ArrayList<String> lifeline_data = new ArrayList<>();
    String[] lifeline_array = {"Hospital", "Police Station", "Fire Station", "Power Station", "Water Plant", "Sewage Plant"};
    int lifeline_ans = -1;

    boolean[] selectedcomm;
    ArrayList<Integer> commercial_list = new ArrayList<>();
    ArrayList<String> commercial_data = new ArrayList<>();
    String[] commercial_array = {"Hotel", "Shopping", "Recreational"};
    int commercial_ans = -1;

    boolean[] selectedoffice;
    ArrayList<Integer> office_list = new ArrayList<>();
    ArrayList<String> office_data = new ArrayList<>();
    String[] office_array = {"Government", "Private"};
    int office_ans = -1;

    boolean[] selectedmixed;
    ArrayList<Integer> mixed_list = new ArrayList<>();
    ArrayList<String> mixed_data = new ArrayList<>();
    String[] mixed_array = {"Residential & Commercial", "Residential & Industrial"};
    int mixed_ans = -1;

    boolean[] selectedindus;
    ArrayList<Integer> industrial_list = new ArrayList<>();
    ArrayList<String> industrial_data = new ArrayList<>();
    String[] industrial_array = {"Agriculture", "Live Stock"};
    int industrial_ans = -1;

    CheckBox site1, site2, site3, site4_yellow;
    CheckBox soil1, soil2, soil3, soil4;
    CheckBox struc1, struc2, struc3, struc4, struc5, struc6, struc7, struc8, struc9, struc10, struc11_yellow, struc12_yellow, struc13_yellow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Rapid Visual Screening (RVS)");
        datepicker = (EditText) findViewById(R.id.datepicker);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabelDate();
            }
        };
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        chooseTime = (EditText) findViewById(R.id.timepicker);
        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    ;
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        chooseTime.setText(hourOfDay + ":" + minutes);
                    }
                }, 0, 0, false);
                timePickerDialog.show();
            }
        });

        roof = findViewById(R.id.roof);

        selectedroof = new boolean[roof_array.length];

        roof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Select ROOF");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(roof_array, roof_ans, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        roof_ans = i;
                    }

                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        roof.setText(roof_array[roof_ans]);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        roof_ans = -1;
                        roof.setText("");
                    }
                });
                builder.show();
//                System.out.println(stringb);
            }
        });

        residential = findViewById(R.id.residential);

        selectedresd = new boolean[residential_array.length];

        residential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Select Residential");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(residential_array, residential_ans, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        residential_ans = i;
                    }

                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        residential.setText(residential_array[residential_ans]);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        residential_ans = -1;
                        residential.setText("");
                    }
                });
                builder.show();
//                System.out.println(stringb);
            }
        });

        educational = findViewById(R.id.educational);

        selectededu = new boolean[educational_array.length];

        educational.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Select Educational");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(educational_array, educational_ans, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        educational_ans = i;
                    }

                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        educational.setText(educational_array[educational_ans]);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        educational_ans = -1;
                        educational.setText("");
                    }
                });
                builder.show();
//                System.out.println(stringb);
            }
        });
        lifeline = findViewById(R.id.lifeline);

        selectedlife = new boolean[lifeline_array.length];

        lifeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Select Lifeline");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(lifeline_array, lifeline_ans, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        lifeline_ans = i;
                    }

                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        lifeline.setText(lifeline_array[lifeline_ans]);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        lifeline_ans = -1;
                        lifeline.setText("");
                    }
                });
                builder.show();
//                System.out.println(stringb);
            }
        });

        commercial = findViewById(R.id.commercial);

        selectedcomm = new boolean[commercial_array.length];

        commercial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Select Commercial");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(commercial_array, commercial_ans, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        commercial_ans = i;
                    }

                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        commercial.setText(commercial_array[commercial_ans]);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        commercial_ans = -1;
                        commercial.setText("");
                    }
                });
                builder.show();
//                System.out.println(stringb);
            }
        });
        office = findViewById(R.id.office);

        selectedoffice = new boolean[office_array.length];

        office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Select Office");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(office_array, office_ans, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        office_ans = i;
                    }

                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        office.setText(office_array[office_ans]);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        office_ans = -1;
                        office.setText("");
                    }
                });
                builder.show();
//                System.out.println(stringb);
            }
        });

        mixeduse = findViewById(R.id.mixed_use);

        selectedmixed = new boolean[mixed_array.length];

        mixeduse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Select Mixed Use");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(mixed_array, mixed_ans, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mixed_ans = i;
                    }

                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mixeduse.setText(mixed_array[mixed_ans]);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mixed_ans = -1;
                        mixeduse.setText("");
                    }
                });
                builder.show();
//                System.out.println(stringb);
            }
        });

        industrial = findViewById(R.id.industrial);

        selectedindus = new boolean[industrial_array.length];

        industrial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Select Mixed Use");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(mixed_array, mixed_ans, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mixed_ans = i;
                    }

                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mixeduse.setText(mixed_array[mixed_ans]);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mixed_ans = -1;
                        mixeduse.setText("");
                    }
                });
                builder.show();
//                System.out.println(stringb);
            }
        });

    }
    private void updateLabelDate() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        datepicker.setText(dateFormat.format(myCalendar.getTime()));
    }

}