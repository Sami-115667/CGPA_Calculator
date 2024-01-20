package com.example.cgpacal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class First_Semester extends AppCompatActivity {

    public  double totalcgpoint=0.0;
    public  double totalcgcredit=0.0;
    LinearLayout coursesLayout;
    Button addCourseButton, calculateButton, backButton, deleteButton,nextbutton;
    TextView cgpaTextView;
    int courseCount = 0;
    private ArrayList<View> addedViews = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_semester);

        coursesLayout = findViewById(R.id.coursesLayout);
        addCourseButton = findViewById(R.id.addCourseButton);
        calculateButton = findViewById(R.id.calculateButton);
        cgpaTextView = findViewById(R.id.cgpaTextView);
        backButton = findViewById(R.id.back1);
        deleteButton = findViewById(R.id.delete1);
        nextbutton=findViewById(R.id.next1);

        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCourse();
                calculateCGPA();
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCGPA();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCourse();
                calculateCGPA();
            }
        });

        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCGPA();
                Intent intent = new Intent(First_Semester.this, Second_Semester.class);
                intent.putExtra("totalcgpoint", totalcgpoint);
                intent.putExtra("totalcgcredit", totalcgcredit);
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
        coursesLayout.addView(courseNameEditText);

        EditText courseGradeEditText = new EditText(this);
        courseGradeEditText.setLayoutParams(params);
        courseGradeEditText.setTextColor(Color.BLACK);
        courseGradeEditText.setHint("Course " + (courseCount + 1) + " Grade");
        coursesLayout.addView(courseGradeEditText);

        EditText creditHoursEditText = new EditText(this);
        creditHoursEditText.setLayoutParams(params1);
        creditHoursEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        creditHoursEditText.setTextColor(Color.BLACK);
        creditHoursEditText.setHint("Course " + (courseCount + 1) + " Credit Hours");
        coursesLayout.addView(creditHoursEditText);

        addedViews.add(courseNameEditText);
        addedViews.add(courseGradeEditText);
        addedViews.add(creditHoursEditText);

        courseCount++;
    }



    public void calculateCGPA() {
        double totalGradePoints = 0;
        double totalCreditHours = 0;

        for (int i = 0; i < coursesLayout.getChildCount(); i += 3) {
            EditText courseGradeEditText = (EditText) coursesLayout.getChildAt(i + 1);
            EditText creditHoursEditText = (EditText) coursesLayout.getChildAt(i + 2);

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
        totalcgcredit=totalCreditHours;
        totalcgpoint=totalGradePoints;

        if (totalCreditHours > 0) {
            double cgpa = totalGradePoints / totalCreditHours;
            String formattedNumber = String.format("%.2f", cgpa);
            cgpaTextView.setText("CGPA: " + formattedNumber);
        } else {
            cgpaTextView.setText("No courses added.");
        }
    }

    public void deleteCourse() {
        int lastIndex = addedViews.size() - 1;
        if (lastIndex >= 0) {
            coursesLayout.removeView(addedViews.get(lastIndex - 2)); // Remove course name EditText
            coursesLayout.removeView(addedViews.get(lastIndex - 1)); // Remove course grade EditText
            coursesLayout.removeView(addedViews.get(lastIndex));     // Remove credit hours EditText
            addedViews.remove(lastIndex);
            addedViews.remove(lastIndex - 1);
            addedViews.remove(lastIndex - 2);
            courseCount--;
        }
    }
}
