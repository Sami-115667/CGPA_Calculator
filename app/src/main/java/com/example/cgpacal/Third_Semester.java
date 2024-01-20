package com.example.cgpacal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Third_Semester extends AppCompatActivity {
    public double tp21=0.0,tc21=0.0;

    public  double  totalCgPoint=0.0,totalCgCredit=0.0;
    LinearLayout coursesLayout21;
    Button addCourseButton21, calculateButton21, backButton21, deleteButton21,nextbutton21;
    TextView cgpaTextView21,totalcgpaTextview21;

    int courseCount = 0;
    private ArrayList<View> addedViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_semester);
        Intent intent = getIntent();
        totalCgPoint = intent.getDoubleExtra("totalcgpoint", 0.0);
        totalCgCredit = intent.getDoubleExtra("totalcgcredit", 0.0);



        coursesLayout21=findViewById(R.id.coursesLayout21);
        addCourseButton21=findViewById(R.id.addCourseButton21);
        calculateButton21=findViewById(R.id.calculateButton21);
        backButton21=findViewById(R.id.back21);
        deleteButton21=findViewById(R.id.delete21);
        cgpaTextView21=findViewById(R.id.cgpaTextView21);
        totalcgpaTextview21=findViewById(R.id.totalcgpaTextView21);
        nextbutton21=findViewById(R.id.next21);

        addCourseButton21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCourse();
                calculateCGPA();
            }
        });

        calculateButton21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCGPA();
            }
        });

        backButton21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        deleteButton21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCourse();
                calculateCGPA();
            }
        });

        nextbutton21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCGPA();
                Intent intent = new Intent(Third_Semester.this, Fourth_Semester.class);
                intent.putExtra("totalcgpoint", tp21);
                intent.putExtra("totalcgcredit", tc21);
                startActivity(intent);
            }
        });

    }





    public void addCourse() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 8); // Add some margin between EditTexts
        params.gravity = Gravity.CENTER;

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params1.setMargins(0, 0, 0, 8); // Add some margin between EditTexts
        params1.gravity = Gravity.CENTER;
        params1.setMargins(0, 0, 0, 100);


        EditText courseNameEditText = new EditText(this);
        courseNameEditText.setLayoutParams(params);
        courseNameEditText.setTextColor(Color.BLACK);
        courseNameEditText.setHint("Course " + (courseCount + 1) + " Name");
        coursesLayout21.addView(courseNameEditText);

        EditText courseGradeEditText = new EditText(this);
        courseGradeEditText.setLayoutParams(params);
        courseGradeEditText.setTextColor(Color.BLACK);
        courseGradeEditText.setHint("Course " + (courseCount + 1) + " Grade");
        coursesLayout21.addView(courseGradeEditText);

        EditText creditHoursEditText = new EditText(this);
        creditHoursEditText.setLayoutParams(params1);
        creditHoursEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        creditHoursEditText.setTextColor(Color.BLACK);
        creditHoursEditText.setHint("Course " + (courseCount + 1) + " Credit Hours");
        coursesLayout21.addView(creditHoursEditText);

        addedViews.add(courseNameEditText);
        addedViews.add(courseGradeEditText);
        addedViews.add(creditHoursEditText);

        courseCount++;
    }



    public void calculateCGPA() {
        double totalGradePoints = 0;
        double totalCreditHours = 0;
        double totalpoint=totalCgPoint,totalcredit=totalCgCredit;

        for (int i = 0; i < coursesLayout21.getChildCount(); i += 3) {
            EditText courseGradeEditText = (EditText) coursesLayout21.getChildAt(i + 1);
            EditText creditHoursEditText = (EditText) coursesLayout21.getChildAt(i + 2);

            String courseGradeString = courseGradeEditText.getText().toString();
            String creditHoursString = creditHoursEditText.getText().toString();

            if (!courseGradeString.isEmpty() && !creditHoursString.isEmpty()) {
                double courseGradePoints = 0.0;
                if (courseGradeString.equals("A+")) {
                    courseGradePoints = 4.0;
                } else if (courseGradeString.equals("A")) {
                    courseGradePoints = 3.75;
                } else if (courseGradeString.equals("A-")) {
                    courseGradePoints = 3.50;
                } else if (courseGradeString.equals("B+")) {
                    courseGradePoints = 3.25;
                } else if (courseGradeString.equals("B")) {
                    courseGradePoints = 3.0;
                } else if (courseGradeString.equals("B-")) {
                    courseGradePoints = 2.75;
                } else if (courseGradeString.equals("C+")) {
                    courseGradePoints = 2.50;
                } else if (courseGradeString.equals("C")) {
                    courseGradePoints = 2.25;
                } else if (courseGradeString.equals("D")) {
                    courseGradePoints = 2.0;
                }

                double creditHours = Double.parseDouble(creditHoursString);
                totalGradePoints += (courseGradePoints * creditHours);
                totalCreditHours += creditHours;
            }

        }

        totalpoint+=totalGradePoints;
        totalcredit+=totalCreditHours;
        tc21=totalcredit;
        tp21=totalpoint;




        if (totalCreditHours > 0) {
            double cgpa = totalGradePoints / totalCreditHours;

            String formattedNumber = String.format("%.2f", cgpa);
            double totalcgpa= totalpoint/totalcredit;
            String formattedNumber1 = String.format("%.2f", totalcgpa);
            cgpaTextView21.setText("CGPA: " + formattedNumber);
            totalcgpaTextview21.setText("Total CGPA: "+formattedNumber1);
        } else {
            cgpaTextView21.setText("No courses added.");
            if(totalcredit>0){
            double totalcgpa= totalpoint/totalcredit;
            String formattedNumber1 = String.format("%.2f", totalcgpa);
            totalcgpaTextview21.setText("Total CGPA: "+formattedNumber1);}
            else
                totalcgpaTextview21.setText("Total CGPA: 0.0");

        }
    }

    public void deleteCourse() {
        int lastIndex = addedViews.size() - 1;
        if (lastIndex >= 0) {
            coursesLayout21.removeView(addedViews.get(lastIndex - 2)); // Remove course name EditText
            coursesLayout21.removeView(addedViews.get(lastIndex - 1)); // Remove course grade EditText
            coursesLayout21.removeView(addedViews.get(lastIndex));     // Remove credit hours EditText
            addedViews.remove(lastIndex);
            addedViews.remove(lastIndex - 1);
            addedViews.remove(lastIndex - 2);
            courseCount--;
        }
    }

}