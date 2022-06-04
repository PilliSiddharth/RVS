package com.example.rvs;

import static android.content.ContentValues.TAG;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.memorynotfound.pdf.itext.WatermarkPageEvent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainActivity<TimeActivity> extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();

    File myFile;
    File pdfFolder;
    String mFilename;

    HashMap map;
    Button view_file;

    String struc_system_value;
    String struc_components_value;

    RadioButton moment_frame, moment_frame_braces, moment_frame_walls;
    RadioButton floor_in_situ, floor_precast_in_situ, floor_precast;

    TextView occupancy_selection, structral_system_textview, structral_components_textview;
    EditText datepicker, inspector_id, chooseTime, building_name, address1, address2, city_town, latitude, longitude;
    AlertDialog.Builder residential_builder, educational_builder, lifeline_builder, commercial_builder, office_builder, mixed_builder, industrial_builder;
    //    roof
    String occupancy_value;
    String occupancy_text_value;
//    ArrayList<String> occupancy_text_value = new ArrayList<>();
    EditText roof, residential, educational, lifeline, commercial, office, mixeduse, industrial, other, occupancy;

    RadioGroup struc_system;
    RadioGroup struc_components;

    String date_str="";
    String time_data="";

    boolean[] selectedoccupancy;
    ArrayList<Integer> occupancy_list = new ArrayList<>();
    ArrayList<String> occupancy_data = new ArrayList<>();
    String[] occupancy_array = {"Residential", "Educational", "Lifeline", "Commercial", "Office", "Mixed Use", "Industrial", "Other"};
    int occupancy_ans = -1;

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

    ArrayList<String> red_arr = new ArrayList<>();
    ArrayList<String> yellow_arr = new ArrayList<>();
    String green_check;

    private static final int STORAGE_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Rapid Visual Screening (RVS)");
        inspector_id = findViewById(R.id.inspector_id);
        datepicker = findViewById(R.id.datepicker);
        chooseTime = findViewById(R.id.timepicker);
        roof = findViewById(R.id.roof);
        occupancy = findViewById(R.id.select_occupancy);

        residential = findViewById(R.id.residential);
        residential.setVisibility(View.GONE);

        educational = findViewById(R.id.residential);
        educational.setVisibility(View.GONE);

        lifeline = findViewById(R.id.residential);
        lifeline.setVisibility(View.GONE);

        commercial = findViewById(R.id.residential);
        commercial.setVisibility(View.GONE);

        office = findViewById(R.id.residential);
        office.setVisibility(View.GONE);

        mixeduse = findViewById(R.id.residential);
        mixeduse.setVisibility(View.GONE);

        industrial = findViewById(R.id.residential);
        industrial.setVisibility(View.GONE);

        other = findViewById(R.id.other);
        other.setVisibility(View.GONE);


        building_name = findViewById(R.id.building_name);
        address1 = findViewById(R.id.address1);
        address2 = findViewById(R.id.address2);
        city_town = findViewById(R.id.city_name);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);

        occupancy_selection = findViewById(R.id.textView24);

        struc_system = findViewById(R.id.struc_system);
        struc_components = findViewById(R.id.struc_components);

        structral_system_textview = findViewById(R.id.textView17);
        structral_components_textview = findViewById(R.id.textView18);

        view_file = findViewById(R.id.viewfile_btn);
        view_file.setVisibility(View.GONE);

        ScrollView scrollview = findViewById(R.id.scrollview);


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


        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    ;

                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        time_data = "check";
                        chooseTime.setText(hourOfDay+":"+minutes);
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


        selectedoccupancy = new boolean[occupancy_array.length];

        occupancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Select Occupancy");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(occupancy_array, occupancy_ans, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        occupancy_ans = i;

                        if (occupancy_array[occupancy_ans] == "Residential") {

                            occupancy_selection.setText("Residential");
                            residential.setHint("Select Residential");
                            residential.setVisibility(View.VISIBLE);

                            residential.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    residential_builder = new AlertDialog.Builder(MainActivity.this);

                                    residential_builder.setTitle("Select Residential");
                                    residential_builder.setCancelable(false);
                                    residential_builder.setSingleChoiceItems(residential_array, residential_ans, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            residential_ans = i;
                                        }

                                    });

                                    residential_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            residential.setText(residential_array[residential_ans]);
                                            occupancy_value = "Residential";
                                            occupancy_text_value = residential_array[residential_ans];
                                        }
                                    });

                                    residential_builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    residential_builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            residential_ans = -1;
                                            residential.setText("");
                                        }
                                    });
                                    residential_builder.show();
                                }
                            });
                        } else if (occupancy_array[occupancy_ans] == "Educational") {

                            occupancy_selection.setText("Educational");
                            educational.setHint("Select Educational");
                            educational.setVisibility(View.VISIBLE);

                            selectededu = new boolean[educational_array.length];

                            educational.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    educational_builder = new AlertDialog.Builder(MainActivity.this);

                                    educational_builder.setTitle("Select Educational");
                                    educational_builder.setCancelable(false);
                                    educational_builder.setSingleChoiceItems(educational_array, educational_ans, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            educational_ans = i;
                                        }

                                    });

                                    educational_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            educational.setText(educational_array[educational_ans]);
                                            occupancy_value = "Educational";
                                            occupancy_text_value = educational_array[educational_ans];
                                        }
                                    });

                                    educational_builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    educational_builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            educational_ans = -1;
                                            educational.setText("");
                                        }
                                    });
                                    educational_builder.show();
//                builder.show();
//                System.out.println(stringb);
                                }
                            });

                        } else if (occupancy_array[occupancy_ans] == "Lifeline") {

                            occupancy_selection.setText("Lifeline");
                            lifeline.setHint("Select Lifeline");
                            lifeline.setVisibility(View.VISIBLE);

                            selectedlife = new boolean[lifeline_array.length];

                            lifeline.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    lifeline_builder = new AlertDialog.Builder(MainActivity.this);

                                    lifeline_builder.setTitle("Select Lifeline");
                                    lifeline_builder.setCancelable(false);
                                    lifeline_builder.setSingleChoiceItems(lifeline_array, lifeline_ans, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            lifeline_ans = i;
                                        }

                                    });

                                    lifeline_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            lifeline.setText(lifeline_array[lifeline_ans]);
                                            occupancy_value = "Lifeline";
                                            occupancy_text_value = lifeline_array[lifeline_ans];
                                        }
                                    });

                                    lifeline_builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    lifeline_builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            lifeline_ans = -1;
                                            lifeline.setText("");
                                        }
                                    });
                                    lifeline_builder.show();
//                builder.show();
//                System.out.println(stringb);
                                }
                            });

                        } else if (occupancy_array[occupancy_ans] == "Commercial") {

                            occupancy_selection.setText("Commercial");
                            commercial.setHint("Select Commercial");
                            commercial.setVisibility(View.VISIBLE);

                            selectedcomm = new boolean[commercial_array.length];

                            commercial.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    commercial_builder = new AlertDialog.Builder(MainActivity.this);

                                    commercial_builder.setTitle("Select Commercial");
                                    commercial_builder.setCancelable(false);
                                    commercial_builder.setSingleChoiceItems(commercial_array, commercial_ans, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            commercial_ans = i;
                                        }

                                    });

                                    commercial_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            commercial.setText(commercial_array[commercial_ans]);
                                            occupancy_value = "Commercial";
                                            occupancy_text_value= commercial_array[commercial_ans];
                                        }
                                    });

                                    commercial_builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    commercial_builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            commercial_ans = -1;
                                            commercial.setText("");
                                        }
                                    });

                                    commercial_builder.show();
//                builder.show();
//                System.out.println(stringb);
                                }
                            });
                        } else if (occupancy_array[occupancy_ans] == "Office") {
                            occupancy_selection.setText("Office");
                            office.setHint("Select Office");
                            office.setVisibility(View.VISIBLE);

                            selectedoffice = new boolean[office_array.length];

                            office.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    office_builder = new AlertDialog.Builder(MainActivity.this);

                                    office_builder.setTitle("Select Office");
                                    office_builder.setCancelable(false);
                                    office_builder.setSingleChoiceItems(office_array, office_ans, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            office_ans = i;
                                        }

                                    });

                                    office_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            office.setText(office_array[office_ans]);
                                            occupancy_value = "Office";
                                            occupancy_text_value = office_array[office_ans];
                                        }
                                    });

                                    office_builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    office_builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            office_ans = -1;
                                            office.setText("");
                                        }
                                    });
                                    office_builder.show();
//                builder.show();
//                System.out.println(stringb);
                                }
                            });

                        } else if (occupancy_array[occupancy_ans] == "Mixed Use") {

                            occupancy_selection.setText("Mixed Use");
                            mixeduse.setHint("Select Mixed Use");
                            mixeduse.setVisibility(View.VISIBLE);

                            selectedmixed = new boolean[mixed_array.length];

                            mixeduse.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mixed_builder = new AlertDialog.Builder(MainActivity.this);

                                    mixed_builder.setTitle("Select Mixed Use");
                                    mixed_builder.setCancelable(false);
                                    mixed_builder.setSingleChoiceItems(mixed_array, mixed_ans, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            mixed_ans = i;
                                        }

                                    });

                                    mixed_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            mixeduse.setText(mixed_array[mixed_ans]);
                                            occupancy_value = "Mixed Use";
                                            occupancy_text_value = mixed_array[mixed_ans];
                                        }
                                    });

                                    mixed_builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    mixed_builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            mixed_ans = -1;
                                            mixeduse.setText("");
                                        }
                                    });
                                    mixed_builder.show();
//                builder.show();
//                System.out.println(stringb);
                                }
                            });
                        } else if (occupancy_array[occupancy_ans] == "Industrial") {

                            occupancy_selection.setText("Industrial");
                            industrial.setHint("Select Industrial");
                            industrial.setVisibility(View.VISIBLE);
                            industrial.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    industrial.setVisibility(View.VISIBLE);
                                    industrial_builder = new AlertDialog.Builder(MainActivity.this);

                                    industrial_builder.setTitle("Select Industrial");
                                    industrial_builder.setCancelable(false);
                                    industrial_builder.setSingleChoiceItems(industrial_array, industrial_ans, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            industrial_ans = i;
                                        }

                                    });

                                    industrial_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            industrial.setText(industrial_array[industrial_ans]);
                                            occupancy_value = "Industrial";
                                            occupancy_text_value = industrial_array[industrial_ans];
                                        }
                                    });

                                    industrial_builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    industrial_builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            industrial_ans = -1;
                                            industrial.setText("");
                                        }
                                    });
                                    industrial_builder.show();
                                }
                            });
                        } else if (occupancy_array[occupancy_ans] == "Other") {
                            occupancy_selection.setText("Enter the Other Occupancy");
                            other.setVisibility(View.VISIBLE);
                            other.setHint("Enter Occupancy");
                            occupancy_value = "Other";
                            occupancy_text_value = other.getText().toString();
                        }
                    }

                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        occupancy.setText(occupancy_array[occupancy_ans]);
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
                        occupancy_ans = -1;
                        occupancy.setText("");
                    }
                });
                builder.show();
//                System.out.println(stringb);
            }
        });


        site1 = (CheckBox) findViewById(R.id.sitting1);
        site2 = (CheckBox) findViewById(R.id.sitting2);
        site3 = (CheckBox) findViewById(R.id.sitting3);
        site4_yellow = (CheckBox) findViewById(R.id.sitting4_yellow);
        soil1 = (CheckBox) findViewById(R.id.soil1);
        soil2 = (CheckBox) findViewById(R.id.soil2);
        soil3 = (CheckBox) findViewById(R.id.soil3);
        soil4 = (CheckBox) findViewById(R.id.soil4);
        struc1 = (CheckBox) findViewById(R.id.struc_aspects1);
        struc2 = (CheckBox) findViewById(R.id.struc_aspects2);
        struc3 = (CheckBox) findViewById(R.id.struc_aspects3);
        struc4 = (CheckBox) findViewById(R.id.struc_aspects4);
        struc5 = (CheckBox) findViewById(R.id.struc_aspects5);
        struc6 = (CheckBox) findViewById(R.id.struc_aspects6);
        struc7 = (CheckBox) findViewById(R.id.struc_aspects7);
        struc8 = (CheckBox) findViewById(R.id.struc_aspects8);
        struc9 = (CheckBox) findViewById(R.id.struc_aspects9);
        struc10 = (CheckBox) findViewById(R.id.struc_aspects10);
        struc11_yellow = (CheckBox) findViewById(R.id.struc_aspects11_yellow);
        struc12_yellow = (CheckBox) findViewById(R.id.struc_aspects12_yellow);
        struc13_yellow = (CheckBox) findViewById(R.id.struc_aspects13_yellow);

        moment_frame = findViewById(R.id.moment_frame);
        moment_frame_braces = findViewById(R.id.moment_frame_braces);
        moment_frame_walls = findViewById(R.id.moment_frame_walls);

        floor_in_situ = findViewById(R.id.floor_situ);
        floor_precast_in_situ = findViewById(R.id.floor_precast_situ);
        floor_precast = findViewById(R.id.floor_precast);

        Button button = (Button) findViewById(R.id.postdataBtn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder errbuilder = new AlertDialog.Builder(MainActivity.this);
                try {
                    errbuilder.setTitle("Required Fields")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setCancelable(false)
                            .setMessage("Make sure to Fill all the required Fields.")
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });

                    if (site1.isChecked()) {
                        red_arr.add(site1.getText().toString());
                    }
                    if (site2.isChecked()) {
                        red_arr.add(site2.getText().toString());
                    }
                    if (site3.isChecked()) {
                        red_arr.add(site3.getText().toString());
                    }
                    if (site4_yellow.isChecked()) {
                        yellow_arr.add(site4_yellow.getText().toString());
                    }
                    if (soil1.isChecked()) {
                        red_arr.add(soil1.getText().toString());
                    }
                    if (soil2.isChecked()) {
                        red_arr.add(soil2.getText().toString());
                    }
                    if (soil3.isChecked()) {
                        red_arr.add(soil3.getText().toString());
                    }
                    if (soil4.isChecked()) {
                        red_arr.add(soil4.getText().toString());
                    }
                    if (struc1.isChecked()) {
                        red_arr.add(struc1.getText().toString());
                    }
                    if (struc2.isChecked()) {
                        red_arr.add(struc2.getText().toString());
                    }
                    if (struc3.isChecked()) {
                        red_arr.add(struc3.getText().toString());
                    }
                    if (struc4.isChecked()) {
                        red_arr.add(struc4.getText().toString());
                    }
                    if (struc5.isChecked()) {
                        red_arr.add(struc5.getText().toString());
                    }
                    if (struc6.isChecked()) {
                        red_arr.add(struc6.getText().toString());
                    }
                    if (struc7.isChecked()) {
                        red_arr.add(struc7.getText().toString());
                    }
                    if (struc8.isChecked()) {
                        red_arr.add(struc8.getText().toString());
                    }
                    if (struc9.isChecked()) {
                        red_arr.add(struc9.getText().toString());
                    }
                    if (struc10.isChecked()) {
                        red_arr.add(struc10.getText().toString());
                    }
                    if (struc11_yellow.isChecked()) {
                        yellow_arr.add(struc11_yellow.getText().toString());
                    }
                    if (struc12_yellow.isChecked()) {
                        yellow_arr.add(struc12_yellow.getText().toString());
                    }
                    if (struc13_yellow.isChecked()) {
                        yellow_arr.add(struc13_yellow.getText().toString());
                    }

                    if (moment_frame.isChecked()){
                        struc_system_value = moment_frame.getText().toString();
                    }
                    if (moment_frame_braces.isChecked()){
                        struc_system_value = moment_frame_braces.getText().toString();
                    }
                    if (moment_frame_walls.isChecked()){
                        struc_system_value = moment_frame_walls.getText().toString();
                    }

                    if(floor_in_situ.isChecked()){
                        struc_components_value = floor_in_situ.getText().toString();
                    }
                    if (floor_precast.isChecked()){
                        struc_components_value = floor_precast.getText().toString();
                    }
                    if(floor_precast_in_situ.isChecked()){
                        struc_components_value = floor_precast_in_situ.getText().toString();
                    }


                    if(check_inspection_id() & check_inspection_date() & check_inspection_time() & check_occupancy() & check_struc_system() & check_struc_components()
                            & check_building_name() & check_address1() & check_city_town() & check_latitude() & check_longitude()  & check_roof() & check_occupancy()) {

                        if(yellow_arr.isEmpty() & red_arr.isEmpty()){
                            green_check = "This building has no life threatening factors, hence it is safe.";
                        }
                        else{
                            green_check="";
                        }

                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED) {
                                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                requestPermissions(permissions, STORAGE_CODE);
                            } else {
                                savePdf();
                            }
                        } else {
                            savePdf();

                        }
                    //                    System.out.println("Occupancy Type: " + occupancy_text_value.get(0));
//                    System.out.println("User selected Type: " + occupancy_text_value.get(1));

                    } else {
                        Toast.makeText(getApplicationContext(), "Please make sure to fill all the required fields.", Toast.LENGTH_SHORT).show();
                        scrollview.smoothScrollTo(0, 0);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    AlertDialog dialog = errbuilder.create();
                    dialog.show();
                }
                yellow_arr.clear();
                red_arr.clear();
//                residential_data.clear();
//                educational_data.clear();
//                lifeline_data.clear();
//                commercial_data.clear();
                roof_data.clear();
//                office_data.clear();
//                mixed_data.clear();
//                industrial_data.clear();
//                occupancy_text_value.clear();
//                map.clear();
            }
        });
    }

//    Drawable customErrorDrawable = getResources().getDrawable(R.drawable.ic_baseline_error_outline_24);
//    customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());


    private void updateLabelDate() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);

//        date_str = "check";
        datepicker.setText(dateFormat.format(myCalendar.getTime()));
    }

    private boolean check_inspection_id() {
        if (inspector_id.getText().length() == 0) {
            inspector_id.setError("This field is required");
            return false;
        } else {
            return true;
        }
    }

    private boolean check_inspection_date() {
        if (datepicker.getText().length() == 0) {
            datepicker.setError("This field is required");
            System.out.println("true");
            System.out.println(datepicker.getText().toString());
            return false;
        } else {
            System.out.println(datepicker.getText());
            datepicker.setError(null);
            System.out.println("flase");
            return true;
        }
    }

    private boolean check_inspection_time() {
        if (chooseTime.getText().length() == 0) {
            chooseTime.setError("This field is required");
            return false;
        } else {
            chooseTime.setError(null);
            return true;
        }
    }

    private boolean check_building_name() {
        if (building_name.getText().length() == 0) {
            building_name.setError("This field is required");
            return false;
        } else {
            return true;
        }
    }

    private boolean check_address1() {
        if (address1.getText().length() == 0) {
            address1.setError("This field is required");
            return false;
        } else {
            return true;
        }
    }

    private boolean check_city_town() {
        if (city_town.getText().length() == 0) {
            city_town.setError("This field is required");
            return false;
        } else {
            return true;
        }
    }

    private boolean check_latitude() {
        if (latitude.getText().length() == 0) {
            latitude.setError("This field is required");
            return false;
        } else {
            return true;
        }
    }

    private boolean check_longitude() {
        if (longitude.getText().length() == 0) {
            longitude.setError("This field is required");
            return false;
        } else {
            return true;
        }
    }

    private boolean check_roof() {
        if (roof.getText().length() == 0) {
            roof.setError("This field is required");
            return false;
        } else {
            roof.setError(null);
            return true;
        }
    }

    private boolean check_struc_system(){
        if (struc_system.getCheckedRadioButtonId() == -1){
            structral_system_textview.setError("This field is required");
            return false;
        }else{
            structral_system_textview.setError(null);
            return true;
        }
    }

    private boolean check_struc_components(){
        if (struc_components.getCheckedRadioButtonId() == -1){
            structral_components_textview.setError("This field is required");
            return false;
        }else{
            structral_components_textview.setError(null);
            return true;
        }
    }


    private boolean check_occupancy() {
        if (occupancy.getText().length() == 0) {
            occupancy.setError("This field is required");
            return false;
        }else if(residential.getText().length() > 2 && educational.getText().length() > 2 && lifeline.getText().length() > 2 && commercial.getText().length() > 2 && office.getText().length() > 2 && mixeduse.getText().length() > 2 && industrial.getText().length() > 2 && other.getText().length() > 2){
            occupancy.setError(null);
            residential.setError("This field is required");
            educational.setError("This field is required");
            lifeline.setError("This field is required");
            commercial.setError("This field is required");
            office.setError("This field is required");
            mixeduse.setError("This field is required");
            industrial.setError("This field is required");
            other.setError("This field is required");
            return false;
        }
        else{
            occupancy.setError(null);
            residential.setError(null);
            educational.setError(null);
            lifeline.setError(null);
            commercial.setError(null);
            office.setError(null);
            mixeduse.setError(null);
            industrial.setError(null);
            other.setError(null);
            return true;
        }
    }

    private void savePdf() throws FileNotFoundException, DocumentException {

        pdfFolder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "pdfdemo");

        if (!pdfFolder.exists()) {
            pdfFolder.mkdir();
            Log.i(TAG, "Pdf Directory created");
        }


        Document doc = new Document();
        mFilename = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
//        String pdffile_path = pdfFolder + mFilename + ".pdf";
        myFile = new File(pdfFolder + mFilename + ".pdf");
        OutputStream mFilePath = new FileOutputStream(myFile);

        try {
            PdfWriter writer = PdfWriter.getInstance(doc, mFilePath);
            Rectangle rect = new Rectangle(30, 30, 550, 800);
            writer.setBoxSize("art", rect);
            HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            writer.setPageEvent(event);
            writer.setPageEvent(new WatermarkPageEvent());

            doc.open();

            Paragraph intro = new Paragraph("\nRVS has generated a pdf which contains all the required data and calculations to understand the safety of the structure post earthquake, please read the pdf thoroughly to understand which features add risk to the structures.\n\n");
            intro.setAlignment(Element.ALIGN_CENTER);

            doc.addAuthor("RVS");
            doc.addTitle("RVS - Generated PDF");

            Paragraph intro_image = new Paragraph();
            intro_image.add(new Chunk("RVS\n", FontFactory.getFont(FontFactory.HELVETICA, 30, Font.BOLD, BaseColor.BLACK)));
            intro_image.setAlignment(Element.ALIGN_CENTER);

            doc.add(intro_image);


            doc.add(intro);
            doc.add(new Paragraph("\n"));

            Paragraph inspection_title = new Paragraph("Inspection",FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD, BaseColor.BLACK));
            inspection_title.setAlignment(Element.ALIGN_CENTER);
            doc.add(inspection_title);
            doc.add(new Paragraph("\n"));

            PdfPTable inspection_table = new PdfPTable(3);
            float widths[] = { 5, 3, 3};
            inspection_table.setWidths(widths);
            inspection_table.setHeaderRows(1);

            PdfPCell cell = new PdfPCell(new Phrase("Inspection ID"));
            cell.setBackgroundColor(new BaseColor(0, 173, 239));
            inspection_table.addCell(cell);

            cell = new PdfPCell(new Phrase("Inspection Date"));
            cell.setBackgroundColor(new BaseColor(0, 173, 239));
            inspection_table.addCell(cell);

            cell = new PdfPCell(new Phrase("Inspection Time"));
            cell.setBackgroundColor(new BaseColor(0, 173, 239));
            inspection_table.addCell(cell);

            Phrase ph;

            cell = new PdfPCell();
            ph = new Phrase(inspector_id.getText().toString());
            cell.addElement(ph);
            inspection_table.addCell(cell);

            cell = new PdfPCell();
            ph = new Phrase(datepicker.getText().toString());
            cell.addElement(ph);
            inspection_table.addCell(cell);

            cell = new PdfPCell();
            ph = new Phrase(chooseTime.getText().toString());
            cell.addElement(ph);
            inspection_table.addCell(cell);

            doc.add(inspection_table);
            doc.add(new Paragraph("\n"));


            Paragraph building_description = new Paragraph("Building Description",FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD, BaseColor.BLACK));
            building_description.setAlignment(Element.ALIGN_CENTER);
            doc.add(building_description);
            doc.add(new Paragraph("\n"));

            PdfPTable building_info = new PdfPTable(4);
            float widths2[] = { 5,3,5,5};
            building_info.setWidths(widths2);
            building_info.setHeaderRows(1);

            cell = new PdfPCell(new Phrase("Building Name"));
            cell.setBackgroundColor(new BaseColor(0, 173, 239));
            building_info.addCell(cell);


            cell = new PdfPCell(new Phrase("City/Town"));
            cell.setBackgroundColor(new BaseColor(0, 173, 239));
            building_info.addCell(cell);

            cell = new PdfPCell(new Phrase("Latitude"));
            cell.setBackgroundColor(new BaseColor(0, 173, 239));
            building_info.addCell(cell);

            cell = new PdfPCell(new Phrase("Longitude"));
            cell.setBackgroundColor(new BaseColor(0, 173, 239));
            building_info.addCell(cell);



            cell = new PdfPCell();
            ph = new Phrase(building_name.getText().toString());
            cell.addElement(ph);
            building_info.addCell(cell);

            cell = new PdfPCell();
            ph = new Phrase(city_town.getText().toString());
            cell.addElement(ph);
            building_info.addCell(cell);

            cell = new PdfPCell();
            ph = new Phrase(latitude.getText().toString());
            cell.addElement(ph);
            building_info.addCell(cell);

            cell = new PdfPCell();
            ph = new Phrase(longitude.getText().toString());
            cell.addElement(ph);
            building_info.addCell(cell);

            doc.add(building_info);
            doc.add(new Paragraph("\n"));



            PdfPTable address_info = new PdfPTable(2);
            float widths3[] = {20, 20};
            address_info.setWidths(widths3);
            address_info.setHeaderRows(1);


            cell = new PdfPCell(new Phrase("Address 1"));
            cell.setBackgroundColor(new BaseColor(0, 173, 239));
            address_info.addCell(cell);

            cell = new PdfPCell(new Phrase("Address 2"));
            cell.setBackgroundColor(new BaseColor(0, 173, 239));
            address_info.addCell(cell);

            cell = new PdfPCell();
            ph = new Phrase(address1.getText().toString());
            cell.addElement(ph);
            address_info.addCell(cell);

            cell = new PdfPCell();
            ph = new Phrase(address2.getText().toString());
            cell.addElement(ph);
            address_info.addCell(cell);

            doc.add(address_info);
            doc.add(new Paragraph("\n"));


            Paragraph struc_system_title = new Paragraph("Structural System",FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD, BaseColor.BLACK));
            struc_system_title.setAlignment(Element.ALIGN_CENTER);
            doc.add(struc_system_title);
            doc.add(new Paragraph("\n"));

            PdfPTable struc_system_table = new PdfPTable(1);
            float widths4[] = {6};
            struc_system_table.setWidths(widths4);
            struc_system_table.setHeaderRows(1);

            cell = new PdfPCell(new Phrase("Structural System"));
            cell.setBackgroundColor(new BaseColor(0, 173, 239));
            struc_system_table.addCell(cell);

            cell = new PdfPCell();
            ph = new Phrase(struc_system_value);
            cell.addElement(ph);
            struc_system_table.addCell(cell);

            doc.add(struc_system_table);
            doc.add(new Paragraph("\n"));

            Paragraph struc_comp_title = new Paragraph("Structural Components",FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD, BaseColor.BLACK));
            struc_comp_title.setAlignment(Element.ALIGN_CENTER);
            doc.add(struc_comp_title);
            doc.add(new Paragraph("\n"));


            PdfPTable struc_components_table = new PdfPTable(2);
            float widths5[] = {6, 4};
            struc_components_table.setWidths(widths5);
            struc_components_table.setHeaderRows(1);

            cell = new PdfPCell(new Phrase("FLOOOR"));
            cell.setBackgroundColor(new BaseColor(0, 173, 239));
            struc_components_table.addCell(cell);

            cell = new PdfPCell(new Phrase("ROOF"));
            cell.setBackgroundColor(new BaseColor(0, 173, 239));
            struc_components_table.addCell(cell);

            cell = new PdfPCell();
            ph = new Phrase(struc_components_value);
            cell.addElement(ph);
            struc_components_table.addCell(cell);

            cell = new PdfPCell();
            ph = new Phrase(roof_array[roof_ans]);
            cell.addElement(ph);
            struc_components_table.addCell(cell);

            doc.add(struc_components_table);
            doc.add(new Paragraph("\n"));




//            PdfPTable inspection_table = new PdfPTable(2);
//
//            inspection_table.addCell(new PDfPCell.add("Inspection ID"));
//            inspection_table.addCell(new Cell().add("Raju"));
//            inspection_table.addCell(new Cell().add("Id"));
//            inspection_table.addCell(new Cell().add("1001"));
//            inspection_table.addCell(new Cell().add("Designation"));
//            inspection_table.addCell(new Cell().add("Programmer"));


//            com.itextpdf.text.List inspection_id_table_list = new com.itextpdf.text.List();
//            inspection_id_table_list.add(inspector_id.getText().toString());
//            Phrase inspection_id_phrase = new Phrase();
//            inspection_id_phrase.add(inspection_id_table_list);
//            PdfPCell inspection_id_phraseCell = new PdfPCell();
//            inspection_id_phraseCell.addElement(inspection_id_phrase);
//
//            PdfPTable inspection_id_phraseTable = new PdfPTable(2);
//            inspection_id_phraseTable.setSpacingBefore(5);
//            inspection_id_phraseTable.addCell("Inspection ID: ");
//            inspection_id_phraseTable.addCell(inspection_id_phraseCell);
//
//            Phrase inspection_id_phraseTableWrapper = new Phrase();
//            inspection_id_phraseTableWrapper.add(inspection_id_phraseTable);
//            doc.add(inspection_id_phraseTableWrapper);
//            doc.add(new Paragraph("\n"));
//
//            PdfPCell inspection_id_cell = new PdfPCell();
//            inspection_id_cell.addElement(inspection_id_table_list);
//
//            PdfPTable inspection_id_table = new PdfPTable(2);
//            inspection_id_table.setSpacingBefore(5);
//            inspection_id_table.addCell("List placed directly into cell");
//            inspection_id_table.addCell(inspection_id_cell);
//
//
//            com.itextpdf.text.List inspection_date_table_list = new com.itextpdf.text.List();
//            inspection_date_table_list.add(datepicker.getText().toString());
//            Phrase inspection_date_phrase = new Phrase();
//            inspection_date_phrase.add(inspection_date_table_list);
//            PdfPCell inspection_date_phraseCell = new PdfPCell();
//            inspection_date_phraseCell.addElement(inspection_date_phrase);
//
//            PdfPTable inspection_date_phraseTable = new PdfPTable(2);
//            inspection_date_phraseTable.setSpacingBefore(5);
//            inspection_date_phraseTable.addCell("Inspection Date: ");
//            inspection_date_phraseTable.addCell(inspection_date_phraseCell);
//
//            Phrase inspection_date_phraseTableWrapper = new Phrase();
//            inspection_date_phraseTableWrapper.add(inspection_date_phraseTable);
//            doc.add(inspection_date_phraseTableWrapper);
//            doc.add(new Paragraph("\n"));
//
//            PdfPCell inspection_date_cell = new PdfPCell();
//            inspection_date_cell.addElement(inspection_date_table_list);
//
//            PdfPTable inspection_date_table = new PdfPTable(2);
//            inspection_date_table.setSpacingBefore(5);
//            inspection_date_table.addCell("List placed directly into cell");
//            inspection_date_table.addCell(inspection_date_cell);
//
//
//            com.itextpdf.text.List inspection_time_table_list = new com.itextpdf.text.List();
//            inspection_time_table_list.add(chooseTime.getText().toString());
//            Phrase inspection_time_phrase = new Phrase();
//            inspection_time_phrase.add(inspection_time_table_list);
//            PdfPCell inspection_time_phraseCell = new PdfPCell();
//            inspection_time_phraseCell.addElement(inspection_time_phrase);
//
//            PdfPTable inspection_time_phraseTable = new PdfPTable(2);
//            inspection_time_phraseTable.setSpacingBefore(5);
//            inspection_time_phraseTable.addCell("Inspection Time: ");
//            inspection_time_phraseTable.addCell(inspection_time_phraseCell);
//
//            Phrase inspection_time_phraseTableWrapper = new Phrase();
//            inspection_time_phraseTableWrapper.add(inspection_time_phraseTable);
//            doc.add(inspection_time_phraseTableWrapper);
//            doc.add(new Paragraph("\n"));
//
//            PdfPCell inspection_time_cell = new PdfPCell();
//            inspection_time_cell.addElement(inspection_time_table_list);
//
//            PdfPTable inspection_time_table = new PdfPTable(2);
//            inspection_time_table.setSpacingBefore(5);
//            inspection_time_table.addCell("List placed directly into cell");
//            inspection_time_table.addCell(inspection_time_cell);


//            com.itextpdf.text.List building_name_table_list = new com.itextpdf.text.List();
//            building_name_table_list.add(building_name.getText().toString());
//            Phrase building_name_phrase = new Phrase();
//            building_name_phrase.add(building_name_table_list);
//            PdfPCell building_name_phraseCell = new PdfPCell();
//            building_name_phraseCell.addElement(building_name_phrase);
//
//            PdfPTable building_name_phraseTable = new PdfPTable(2);
//            building_name_phraseTable.setSpacingBefore(5);
//            building_name_phraseTable.addCell("Building Name: ");
//            building_name_phraseTable.addCell(building_name_phraseCell);
//
//            Phrase building_name_phraseTableWrapper = new Phrase();
//            building_name_phraseTableWrapper.add(building_name_phraseTable);
//            doc.add(building_name_phraseTableWrapper);
//            doc.add(new Paragraph("\n"));
//
//            PdfPCell building_name_cell = new PdfPCell();
//            building_name_cell.addElement(building_name_table_list);
//
//            PdfPTable building_name_table = new PdfPTable(2);
//            building_name_table.setSpacingBefore(5);
//            building_name_table.addCell("List placed directly into cell");
//            building_name_table.addCell(building_name_cell);
//
//
//            com.itextpdf.text.List address1_table_list = new com.itextpdf.text.List();
//            address1_table_list.add(address1.getText().toString());
//            Phrase address1_phrase = new Phrase();
//            address1_phrase.add(address1_table_list);
//            PdfPCell address1_phraseCell = new PdfPCell();
//            address1_phraseCell.addElement(address1_phrase);
//
//            PdfPTable address1_phraseTable = new PdfPTable(2);
//            address1_phraseTable.setSpacingBefore(5);
//            address1_phraseTable.addCell("Address 1: ");
//            address1_phraseTable.addCell(address1_phraseCell);
//
//            Phrase address1_phraseTableWrapper = new Phrase();
//            address1_phraseTableWrapper.add(address1_phraseTable);
//            doc.add(address1_phraseTableWrapper);
//            doc.add(new Paragraph("\n"));
//
//            PdfPCell address1_cell = new PdfPCell();
//            address1_cell.addElement(address1_table_list);
//
//            PdfPTable address1_table = new PdfPTable(2);
//            address1_table.setSpacingBefore(5);
//            address1_table.addCell("List placed directly into cell");
//            address1_table.addCell(address1_cell);
//
//
//            com.itextpdf.text.List address2_table_list = new com.itextpdf.text.List();
//            address2_table_list.add(address2.getText().toString());
//            Phrase address2_phrase = new Phrase();
//            address2_phrase.add(address2_table_list);
//            PdfPCell address2_phraseCell = new PdfPCell();
//            address2_phraseCell.addElement(address2_phrase);
//
//            PdfPTable address2_phraseTable = new PdfPTable(2);
//            address2_phraseTable.setSpacingBefore(5);
//            address2_phraseTable.addCell("Address 2: ");
//            address2_phraseTable.addCell(address2_phraseCell);
//
//            Phrase address2_phraseTableWrapper = new Phrase();
//            address2_phraseTableWrapper.add(address2_phraseTable);
//            doc.add(address2_phraseTableWrapper);
//            doc.add(new Paragraph("\n"));
//
//            PdfPCell address2_cell = new PdfPCell();
//            address2_cell.addElement(address2_table_list);
//
//            PdfPTable address2_table = new PdfPTable(2);
//            address2_table.setSpacingBefore(5);
//            address2_table.addCell("List placed directly into cell");
//            address2_table.addCell(address2_cell);
//
//
//            com.itextpdf.text.List city_town_table_list = new com.itextpdf.text.List();
//            city_town_table_list.add(city_town.getText().toString());
//            Phrase city_town_phrase = new Phrase();
//            city_town_phrase.add(city_town_table_list);
//            PdfPCell city_town_phraseCell = new PdfPCell();
//            city_town_phraseCell.addElement(city_town_phrase);
//
//            PdfPTable city_town_phraseTable = new PdfPTable(2);
//            city_town_phraseTable.setSpacingBefore(5);
//            city_town_phraseTable.addCell("City/Town: ");
//            city_town_phraseTable.addCell(city_town_phraseCell);
//
//            Phrase city_town_phraseTableWrapper = new Phrase();
//            city_town_phraseTableWrapper.add(city_town_phraseTable);
//            doc.add(city_town_phraseTableWrapper);
//            doc.add(new Paragraph("\n"));
//
//            PdfPCell city_town_cell = new PdfPCell();
//            city_town_cell.addElement(city_town_table_list);
//
//            PdfPTable city_town_table = new PdfPTable(2);
//            city_town_table.setSpacingBefore(5);
//            city_town_table.addCell("List placed directly into cell");
//            city_town_table.addCell(city_town_cell);
//
//
//            com.itextpdf.text.List latitude_table_list = new com.itextpdf.text.List();
//            latitude_table_list.add(latitude.getText().toString());
//            Phrase latitude_phrase = new Phrase();
//            latitude_phrase.add(latitude_table_list);
//            PdfPCell latitude_phraseCell = new PdfPCell();
//            latitude_phraseCell.addElement(latitude_phrase);
//
//            PdfPTable latitude_phraseTable = new PdfPTable(2);
//            latitude_phraseTable.setSpacingBefore(5);
//            latitude_phraseTable.addCell("Latitude: ");
//            latitude_phraseTable.addCell(latitude_phraseCell);
//
//            Phrase latitude_phraseTableWrapper = new Phrase();
//            latitude_phraseTableWrapper.add(latitude_phraseTable);
//            doc.add(latitude_phraseTableWrapper);
//            doc.add(new Paragraph("\n"));
//
//            PdfPCell latitude_cell = new PdfPCell();
//            latitude_cell.addElement(latitude_table_list);
//
//            PdfPTable latitude_table = new PdfPTable(2);
//            latitude_table.setSpacingBefore(5);
//            latitude_table.addCell("List placed directly into cell");
//            latitude_table.addCell(latitude_cell);
//
//
//            com.itextpdf.text.List longitude_table_list = new com.itextpdf.text.List();
//            longitude_table_list.add(longitude.getText().toString());
//            Phrase longitude_phrase = new Phrase();
//            longitude_phrase.add(longitude_table_list);
//            PdfPCell longitude_phraseCell = new PdfPCell();
//            longitude_phraseCell.addElement(longitude_phrase);
//
//            PdfPTable longitude_phraseTable = new PdfPTable(2);
//            longitude_phraseTable.setSpacingBefore(5);
//            longitude_phraseTable.addCell("Longitude: ");
//            longitude_phraseTable.addCell(longitude_phraseCell);
//
//            Phrase longitude_phraseTableWrapper = new Phrase();
//            longitude_phraseTableWrapper.add(longitude_phraseTable);
//            doc.add(longitude_phraseTableWrapper);
//            doc.add(new Paragraph("\n"));
//
//            PdfPCell longitude_cell = new PdfPCell();
//            longitude_cell.addElement(longitude_table_list);
//
//            PdfPTable longitude_table = new PdfPTable(2);
//            longitude_table.setSpacingBefore(5);
//            longitude_table.addCell("List placed directly into cell");
//            longitude_table.addCell(longitude_cell);


//            com.itextpdf.text.List sturc_system_table_list = new com.itextpdf.text.List();
//            sturc_system_table_list.add(struc_system_value);
//            Phrase sturc_system_phrase = new Phrase();
//            sturc_system_phrase.add(sturc_system_table_list);
//            PdfPCell sturc_system_phraseCell = new PdfPCell();
//            sturc_system_phraseCell.addElement(sturc_system_phrase);
//
//            PdfPTable sturc_system_phraseTable = new PdfPTable(2);
//            sturc_system_phraseTable.setSpacingBefore(5);
//            sturc_system_phraseTable.addCell("Structural System: ");
//            sturc_system_phraseTable.addCell(sturc_system_phraseCell);
//
//            Phrase sturc_system_phraseTableWrapper = new Phrase();
//            sturc_system_phraseTableWrapper.add(sturc_system_phraseTable);
//            doc.add(sturc_system_phraseTableWrapper);
//            doc.add(new Paragraph("\n"));
//
//            PdfPCell sturc_system_cell = new PdfPCell();
//            sturc_system_cell.addElement(sturc_system_table_list);
//
//            PdfPTable sturc_system_table = new PdfPTable(2);
//            sturc_system_table.setSpacingBefore(5);
//            sturc_system_table.addCell("List placed directly into cell");
//            sturc_system_table.addCell(sturc_system_cell);
//
//
//            com.itextpdf.text.List sturc_components_table_list = new com.itextpdf.text.List();
//            sturc_components_table_list.add(struc_components_value);
//            Phrase sturc_components_phrase = new Phrase();
//            sturc_components_phrase.add(sturc_components_table_list);
//            PdfPCell sturc_components_phraseCell = new PdfPCell();
//            sturc_components_phraseCell.addElement(sturc_components_phrase);
//
//            PdfPTable sturc_components_phraseTable = new PdfPTable(2);
//            sturc_components_phraseTable.setSpacingBefore(5);
//            sturc_components_phraseTable.addCell("FLOOOR: ");
//            sturc_components_phraseTable.addCell(sturc_components_phraseCell);
//
//            Phrase sturc_components_phraseTableWrapper = new Phrase();
//            sturc_components_phraseTableWrapper.add(sturc_components_phraseTable);
//            doc.add(sturc_components_phraseTableWrapper);
//            doc.add(new Paragraph("\n"));
//
//            PdfPCell sturc_components_cell = new PdfPCell();
//            sturc_components_cell.addElement(sturc_components_table_list);
//
//            PdfPTable sturc_components_table = new PdfPTable(2);
//            sturc_components_table.setSpacingBefore(5);
//            sturc_components_table.addCell("List placed directly into cell");
//            sturc_components_table.addCell(sturc_components_cell);
//
//
//            com.itextpdf.text.List roof_table_list = new com.itextpdf.text.List();
//            roof_table_list.add(roof_array[roof_ans]);
//            Phrase roof_phrase = new Phrase();
//            roof_phrase.add(roof_table_list);
//            PdfPCell roof_phraseCell = new PdfPCell();
//            roof_phraseCell.addElement(roof_phrase);
//
//            PdfPTable roof_phraseTable = new PdfPTable(2);
//            roof_phraseTable.setSpacingBefore(5);
//            roof_phraseTable.addCell("Roof: ");
//            roof_phraseTable.addCell(roof_phraseCell);
//
//            Phrase roof_phraseTableWrapper = new Phrase();
//            roof_phraseTableWrapper.add(roof_phraseTable);
//            doc.add(roof_phraseTableWrapper);
//            doc.add(new Paragraph("\n"));
//
//            PdfPCell roof_cell = new PdfPCell();
//            roof_cell.addElement(roof_table_list);
//
//            PdfPTable roof_table = new PdfPTable(2);
//            roof_table.setSpacingBefore(5);
//            roof_table.addCell("List placed directly into cell");
//            roof_table.addCell(roof_cell);

            Paragraph occp_title = new Paragraph("Occupancy",FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD, BaseColor.BLACK));
            occp_title.setAlignment(Element.ALIGN_CENTER);
            doc.add(occp_title);
            doc.add(new Paragraph("\n"));

            com.itextpdf.text.List occupance_type_table_list = new com.itextpdf.text.List();
            occupance_type_table_list.add(occupancy_value);
            Phrase occupance_type_phrase = new Phrase();
            occupance_type_phrase.add(occupance_type_table_list);
            PdfPCell occupance_type_phraseCell = new PdfPCell();
            occupance_type_phraseCell.addElement(occupance_type_phrase);

            PdfPTable occupance_type_phraseTable = new PdfPTable(2);
            occupance_type_phraseTable.setSpacingBefore(5);
            occupance_type_phraseTable.addCell("Occupancy Type: ");
            occupance_type_phraseTable.addCell(occupance_type_phraseCell);

            Phrase occupance_type_phraseTableWrapper = new Phrase();
            occupance_type_phraseTableWrapper.add(occupance_type_phraseTable);
            doc.add(occupance_type_phraseTableWrapper);
            doc.add(new Paragraph("\n"));

            PdfPCell occupance_type_cell = new PdfPCell();
            occupance_type_cell.addElement(occupance_type_table_list);

            PdfPTable occupance_type_table = new PdfPTable(2);
            occupance_type_table.setSpacingBefore(5);
            occupance_type_table.addCell("List placed directly into cell");
            occupance_type_table.addCell(occupance_type_cell);


            com.itextpdf.text.List occupance_user_table_list = new com.itextpdf.text.List();
            occupance_user_table_list.add(occupancy_text_value);
            Phrase occupance_user_phrase = new Phrase();
            occupance_user_phrase.add(occupance_user_table_list);
            PdfPCell occupance_user_phraseCell = new PdfPCell();
            occupance_user_phraseCell.addElement(occupance_user_phrase);

            PdfPTable occupance_user_phraseTable = new PdfPTable(2);
            occupance_user_phraseTable.setSpacingBefore(5);
            occupance_user_phraseTable.addCell(occupancy_value+": ");
            occupance_user_phraseTable.addCell(occupance_user_phraseCell);

            Phrase occupance_user_phraseTableWrapper = new Phrase();
            occupance_user_phraseTableWrapper.add(occupance_user_phraseTable);
            doc.add(occupance_user_phraseTableWrapper);
            doc.add(new Paragraph("\n"));

            PdfPCell occupance_user_cell = new PdfPCell();
            occupance_user_cell.addElement(occupance_user_table_list);

            PdfPTable occupance_user_table = new PdfPTable(2);
            occupance_user_table.setSpacingBefore(5);
            occupance_user_table.addCell("List placed directly into cell");
            occupance_user_table.addCell(occupance_user_cell);



            Paragraph life_threat_params = new Paragraph("Life Threatening Parameters",FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD, BaseColor.BLACK));
            life_threat_params.setAlignment(Element.ALIGN_CENTER);
            doc.add(life_threat_params);
            doc.add(new Paragraph("\n"));


            Set red_set = new HashSet(red_arr);
            List<String> red_array_list = new ArrayList<String>(red_set);
            com.itextpdf.text.List red_list_table_list = new com.itextpdf.text.List();
            for (int sts = 0; sts < red_array_list.size(); sts++) {
                red_list_table_list.add(red_array_list.get(sts) + "\n\n");
            }

            Phrase red_list_phrase = new Phrase();
            red_list_phrase.add(red_list_table_list);
            PdfPCell red_list_phraseCell = new PdfPCell();
            red_list_phraseCell.setBackgroundColor(new BaseColor(255,0,0));
            red_list_phraseCell.addElement(red_list_phrase);

            PdfPTable red_list_phraseTable = new PdfPTable(2);
            red_list_phraseTable.setSpacingBefore(5);
            PdfPCell red_list_title_cell = new PdfPCell();
            red_list_title_cell.setBackgroundColor(new BaseColor(255,0,0));
            red_list_title_cell.addElement(new Paragraph("Red (Unusable): "));
            red_list_phraseTable.addCell(red_list_title_cell);
//            red_list_phraseTable.addCell("RED (Unusable): ");
            red_list_phraseTable.addCell(red_list_phraseCell);

            Phrase red_list_phraseTableWrapper = new Phrase();
            red_list_phraseTableWrapper.add(red_list_phraseTable);
            doc.add(red_list_phraseTableWrapper);
            doc.add(new Paragraph("\n"));

            PdfPCell red_list_cell = new PdfPCell();
            red_list_cell.addElement(red_list_table_list);

            PdfPTable red_list_table = new PdfPTable(2);
            red_list_table.setSpacingBefore(5);
            red_list_table.addCell("List placed directly into cell");
            red_list_table.addCell(red_list_cell);

//            yellow_par =

            Set yellow_set = new HashSet(yellow_arr);
            List<String> yellow_array_list = new ArrayList<String>(yellow_set);
            com.itextpdf.text.List yellow_list_table_list = new com.itextpdf.text.List();
            for (int sts = 0; sts < yellow_array_list.size(); sts++) {
                yellow_list_table_list.add(yellow_array_list.get(sts) + "\n\n");
            }

            Phrase yellow_list_phrase = new Phrase();
            yellow_list_phrase.add(yellow_list_table_list);
            PdfPCell yellow_list_phraseCell = new PdfPCell();
            yellow_list_phraseCell.setBackgroundColor(new BaseColor(248,233,0));
            yellow_list_phraseCell.addElement(yellow_list_phrase);

            PdfPTable yellow_list_phraseTable = new PdfPTable(2);
            yellow_list_phraseTable.setSpacingBefore(5);
            PdfPCell yellow_list_title_cell = new PdfPCell();
            yellow_list_title_cell.setBackgroundColor(new BaseColor(248,233,0));
            yellow_list_title_cell.addElement(new Paragraph("Yellow (Usable with Temporary Interventions): "));
            yellow_list_phraseTable.addCell(yellow_list_title_cell);
//            yellow_list_phraseTable.addCell("Yellow (Usable with Temporary Interventions): ");
            yellow_list_phraseTable.addCell(yellow_list_phraseCell);

            Phrase yellow_list_phraseTableWrapper = new Phrase();
            yellow_list_phraseTableWrapper.add(yellow_list_phraseTable);
            doc.add(yellow_list_phraseTableWrapper);
            doc.add(new Paragraph("\n"));

            PdfPCell yellow_list_cell = new PdfPCell();
            yellow_list_cell.addElement(yellow_list_table_list);

            PdfPTable yellow_list_table = new PdfPTable(2);
            yellow_list_table.setSpacingBefore(5);
            yellow_list_table.addCell("List placed directly into cell");
            yellow_list_table.addCell(yellow_list_cell);
            doc.add(new Paragraph("\n"));



//            Set yellow_set = new HashSet(yellow_arr);
//            List<String> yellow_array_list = new ArrayList<String>(yellow_set);
//            com.itextpdf.text.List yellow_list_table_list = new com.itextpdf.text.List();
//            for (int sts = 0; sts < yellow_array_list.size(); sts++) {
//                yellow_list_table_list.add(yellow_array_list.get(sts) + "\n\n");
//            }

            com.itextpdf.text.List green_list_table_list = new com.itextpdf.text.List();
            green_list_table_list.add(green_check);
            Phrase green_list_phrase = new Phrase();
            green_list_phrase.add(green_list_table_list);
            PdfPCell green_list_phraseCell = new PdfPCell();
            green_list_phraseCell.setBackgroundColor(new BaseColor(0,227,31));
            green_list_phraseCell.addElement(green_list_phrase);

            PdfPTable green_list_phraseTable = new PdfPTable(2);
            green_list_phraseTable.setSpacingBefore(5);
            PdfPCell green_list_title_cell = new PdfPCell();
            green_list_title_cell.setBackgroundColor(new BaseColor(0,227,31));
            green_list_title_cell.addElement(new Paragraph("Green (Usable): "));
            green_list_phraseTable.addCell(green_list_title_cell);
//            yellow_list_phraseTable.addCell("Yellow (Usable with Temporary Interventions): ");
            green_list_phraseTable.addCell(green_list_phraseCell);

            Phrase green_list_phraseTableWrapper = new Phrase();
            green_list_phraseTableWrapper.add(green_list_phraseTable);
            doc.add(green_list_phraseTableWrapper);
            doc.add(new Paragraph("\n"));

            PdfPCell green_list_cell = new PdfPCell();
            green_list_cell.addElement(green_list_table_list);

            PdfPTable green_list_table = new PdfPTable(2);
            green_list_table.setSpacingBefore(5);
            green_list_table.addCell("List placed directly into cell");
            green_list_table.addCell(green_list_cell);
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));




            Paragraph life_threat_check = new Paragraph("Life Threatening Parameters",FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD, BaseColor.BLACK));
            life_threat_check.setAlignment(Element.ALIGN_CENTER);
            doc.add(life_threat_check);
            doc.add(new Paragraph("\n"));


            PdfPTable life_threat = new PdfPTable(3);
            float widths7[] = { 3,3,3};
            life_threat.setWidths(widths7);
            life_threat.setHeaderRows(1);

            cell = new PdfPCell(new Phrase("RED (Unusable)"));
            cell.setBackgroundColor(new BaseColor(255, 0, 0));
            life_threat.addCell(cell);

            cell = new PdfPCell(new Phrase("Yellow (Usable with Temporary interventions)"));
            cell.setBackgroundColor(new BaseColor(248, 233, 0));
            life_threat.addCell(cell);

            cell = new PdfPCell(new Phrase("Green (Usable)"));
            cell.setBackgroundColor(new BaseColor(0, 227, 31));
            life_threat.addCell(cell);

            cell = new PdfPCell();
            ph = new Phrase(" ");
            cell.addElement(ph);
            life_threat.addCell(cell);

            cell = new PdfPCell();
            ph = new Phrase(" ");
            cell.addElement(ph);
            life_threat.addCell(cell);

            cell = new PdfPCell();
            ph = new Phrase(" ");
            cell.addElement(ph);
            life_threat.addCell(cell);

            doc.add(life_threat);


            doc.close();

            Toast.makeText(this, mFilename + ".pdf\nis saved to\n" + mFilePath, Toast.LENGTH_SHORT).show();
            view_file.setVisibility(View.VISIBLE);
            view_file.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent viewPdf = new Intent(Intent.ACTION_VIEW);
                    viewPdf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri URI = FileProvider.getUriForFile(MainActivity.this, MainActivity.this.getApplicationContext().getPackageName() + ".provider", myFile);
                    viewPdf.setDataAndType(URI, "application/pdf");
                    viewPdf.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    MainActivity.this.startActivity(viewPdf);
                }
            });


        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        }
    }


