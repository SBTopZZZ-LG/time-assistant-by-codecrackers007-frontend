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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.function.Function;

public class Register2Activity extends AppCompatActivity {
    int index = -1;

    ArrayList<View> pages = new ArrayList<>();

    CardView container;

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
                    callback.onClick(v);
                }
            });
        }
    }

    private boolean Initialize() {
        pages.add(View.inflate(Register2Activity.this, R.layout.reg_comp_info1, null));
        pages.add(View.inflate(Register2Activity.this, R.layout.reg_comp_info2, null));

        container = findViewById(R.id.cv2);

        findViewById(R.id.btnContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });

        setClickHandler(new ClickHandler() {
            @Override
            public void onClick(@NonNull View page) {
                nextPage();
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
    void onClick(@NonNull View page);
}