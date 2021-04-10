package com.sbtopzzzteam.somethingcool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.function.Function;

public class Register2Activity extends AppCompatActivity {
    int index = -1;

    ArrayList<View> pages = new ArrayList<>();

    CardView container;

    public static class InputData {
        static String fullName;
        static String phoneNumber;

        static String age;
        static String profession;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        Initialize();

    }

    void setClickHandler(ClickHandler callback) {
        for (View v : pages) {
            v.findViewById(R.id.btnContinue).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick();
                }
            });
        }
    }

    private boolean Initialize() {
        pages.add(View.inflate(Register2Activity.this, R.layout.reg_comp_info1, null));
        pages.add(View.inflate(Register2Activity.this, R.layout.reg_comp_info2, null));
        pages.add(View.inflate(Register2Activity.this, R.layout.reg_comp_info3, null));
        pages.add(View.inflate(Register2Activity.this, R.layout.reg_comp_info4, null));

        container = findViewById(R.id.cv2);

        findViewById(R.id.btnContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });

        setClickHandler(new ClickHandler() {
            @Override
            public void onClick() {
                View page = pages.get(index);
                if (index == 0) {
                    EditText etFullName = ((TextInputLayout) page.findViewById(R.id.etFullName)).getEditText(),
                            etPhoneNumber = ((TextInputLayout) page.findViewById(R.id.etPhoneNumber)).getEditText();
                    String fullName = etFullName.getText().toString();
                    if (!fullName.equals("") && (fullName.split(" ").length > 1)) {
                        InputData.fullName = fullName;
                        InputData.phoneNumber = etPhoneNumber.getText().toString();

                        nextPage();
                    } else {
                        Snackbar.make(page, "Please enter a valid name", Snackbar.LENGTH_LONG).show();
                    }
                } else if (index  == 1) {
                    EditText etAge = ((TextInputLayout) page.findViewById(R.id.etAge)).getEditText();
                    RadioButton rbFreelancer = page.findViewById(R.id.rbFreeLancer),
                            rbStudent = page.findViewById(R.id.rbStudent),
                            rbEmployee = page.findViewById(R.id.rbEmployee),
                            rbSelfEmployed = page.findViewById(R.id.rbSelfEmployed),
                            rbOther = page.findViewById(R.id.rbOther);
                    String profession = rbFreelancer.isChecked() ? rbFreelancer.getText().toString() :
                            rbStudent.isChecked() ? rbStudent.getText().toString() :
                                    rbEmployee.isChecked() ? rbEmployee.getText().toString() :
                                            rbSelfEmployed.isChecked() ? rbSelfEmployed.getText().toString() :
                                                    rbOther.isChecked() ? "Other" : "";
                    if (profession.equals(""))
                        Snackbar.make(page, "Please select your profession to proceed", Snackbar.LENGTH_LONG).show();
                    else {
                        InputData.age = etAge.getText().toString();
                        InputData.profession = profession;

                        nextPage();
                    }
                } else if (index == 2) {
                    RadioButton rb1 = page.findViewById(R.id.rb1),
                            rb2 = page.findViewById(R.id.rb2),
                            rb3 = page.findViewById(R.id.rb3),
                            rb4 = page.findViewById(R.id.rb4),
                            rb5 = page.findViewById(R.id.rb5),
                            rb6 = page.findViewById(R.id.rb6),
                            rb7 = page.findViewById(R.id.rb7);

                    String avgUsage = "";
                    /* avgUsage = rb1.isChecked() ? "0" :
                            rb2.isChecked() ? "15m" :
                                    rb3.isChecked() ? "30m" :
                                            rb4.isChecked() ? "1h" : */
                } else if (index == 3) {

                }
            }
        });

        return true;
    }

    void nextPage() {
        if (index < 1) {
            View page = pages.get(++index);
            page.setAlpha(0);
            View currentPage = container.getChildAt(0);

            ObjectAnimator animator = ObjectAnimator.ofFloat(currentPage, "alpha", 1, 0);
            animator.setDuration(250);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    container.removeAllViews();

                    container.addView(page);

                    ObjectAnimator animator2 = ObjectAnimator.ofFloat(page, "alpha", 0, 1);
                    animator2.setDuration(animator.getDuration());
                    animator2.start();
                }
            });
            animator.start();
        }
    }

    void previousPage() {
        if (index > 0) {
            try {
                View page = pages.get(--index);
                page.setAlpha(0);
                View currentPage = container.getChildAt(0);

                ObjectAnimator animator = ObjectAnimator.ofFloat(currentPage, "alpha", 1, 0);
                animator.setDuration(250);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        container.removeAllViews();

                        container.addView(page);

                        ObjectAnimator animator2 = ObjectAnimator.ofFloat(page, "alpha", 0, 1);
                        animator2.setDuration(animator.getDuration());
                        animator2.start();
                    }
                });
                animator.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (index == 0)
            super.onBackPressed();
        else
            previousPage();
    }
}

interface ClickHandler {
    void onClick();
}