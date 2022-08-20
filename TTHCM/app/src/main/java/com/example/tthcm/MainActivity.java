package com.example.tthcm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.tthcm.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    static ActivityMainBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    static List<Question> listQuestion = new ArrayList<>();
    static Question question = new Question();
    static Context context;
    static Random random = new Random();
    static int time = 500;
    static int maxLoop = 0;
    static int countLoop[];
    static MediaPlayer music;
    static int countQuestion = 0;
    static int correctAnswer = 0;
    static boolean firstClick = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = MainActivity.this;
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot i : snapshot.child("questions").getChildren()) {
                    listQuestion.add(i.getValue(Question.class));
                }
                countLoop = new int[listQuestion.size()];
                initLoop();
                chooseQuestion();
                sortAnswer();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Máy chủ đang bảo trì!", Toast.LENGTH_SHORT);
            }
        });

        binding.ansA.setOnClickListener(v -> {
            if (checkClick()) {
                if (binding.ansA.getText().equals(question.getAnswer())) {
                    Correct(1);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            chooseQuestion();
                            sortAnswer();
                        }
                    }, time);
                } else {
                    InCorrect(1);
                }
            }
        });

        binding.ansB.setOnClickListener(v -> {
            if (checkClick()) {
                if (binding.ansB.getText().equals(question.getAnswer())) {
                    Correct(2);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            chooseQuestion();
                            sortAnswer();
                        }
                    }, time);
                } else {
                    InCorrect(2);
                }
            }
        });

        binding.ansC.setOnClickListener(v -> {
            if (checkClick()) {
                if (binding.ansC.getText().equals(question.getAnswer())) {
                    Correct(3);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            chooseQuestion();
                            sortAnswer();
                        }
                    }, time);
                } else {
                    InCorrect(3);
                }
            }
        });

        binding.ansD.setOnClickListener(v -> {
            if (checkClick()) {
                if (binding.ansD.getText().equals(question.getAnswer())) {
                    Correct(4);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            chooseQuestion();
                            sortAnswer();
                        }
                    }, time);
                } else {
                    InCorrect(4);
                }
            }
        });
    }

    private static void sortAnswer() {
        setDefault();
        List <Integer> listAnswer = new ArrayList<>();
        Integer []check = new Integer[5];
        for (int i = 0; i <= 4; i++) check[i] = 0;
        while (true) {
            Integer v = random.nextInt(4) + 1;
            if (check[v] == 0) {
                listAnswer.add(v);
                check[v] = 1;
            }
            int cnt = 0;
            for (int i = 1; i <= 4; i++) {
                if (check[i] == 1) {
                    cnt++;
                }
            }
            if (cnt == 4) break;
        }
        setAnswer(listAnswer);
    }

    private static void setAnswer(List<Integer> listAnswer) {
        List <String> s = new ArrayList<>();
        s.add("");
        s.add(question.getA());
        s.add(question.getB());
        s.add(question.getC());
        s.add(question.getD());

        binding.question.setVisibility(View.VISIBLE);
        binding.ansA.setVisibility(View.VISIBLE);
        binding.ansB.setVisibility(View.VISIBLE);
        binding.ansC.setVisibility(View.VISIBLE);
        binding.ansD.setVisibility(View.VISIBLE);

        binding.question.setText(question.getQuestion());
        binding.ansA.setText(s.get(listAnswer.get(0)));
        binding.ansB.setText(s.get(listAnswer.get(1)));
        binding.ansC.setText(s.get(listAnswer.get(2)));
        binding.ansD.setText(s.get(listAnswer.get(3)));
        setScore();
    }

    private static void Correct(int n) {
        if (firstClick) {
            correctAnswer++;
        }
        switch (n) {
            case 1:
                binding.ansA.setBackgroundColor(context.getResources().getColor(R.color.green));
                binding.ansB.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                binding.ansC.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                binding.ansD.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                break;
            case 2:
                binding.ansA.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                binding.ansB.setBackgroundColor(context.getResources().getColor(R.color.green));
                binding.ansC.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                binding.ansD.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                break;
            case 3:
                binding.ansA.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                binding.ansB.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                binding.ansC.setBackgroundColor(context.getResources().getColor(R.color.green));
                binding.ansD.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                break;
            case 4:
                binding.ansA.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                binding.ansB.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                binding.ansC.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                binding.ansD.setBackgroundColor(context.getResources().getColor(R.color.green));
                break;
        }
    }

    private static void InCorrect(int n) {
        firstClick = false;
        switch (n) {
            case 1:
                binding.ansA.setBackgroundColor(context.getResources().getColor(R.color.red));
                binding.ansB.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                binding.ansC.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                binding.ansD.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                break;
            case 2:
                binding.ansA.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                binding.ansB.setBackgroundColor(context.getResources().getColor(R.color.red));
                binding.ansC.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                binding.ansD.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                break;
            case 3:
                binding.ansA.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                binding.ansB.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                binding.ansC.setBackgroundColor(context.getResources().getColor(R.color.red));
                binding.ansD.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                break;
            case 4:
                binding.ansA.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                binding.ansB.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                binding.ansC.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                binding.ansD.setBackgroundColor(context.getResources().getColor(R.color.red));
                break;
        }
    }

    private static void setDefault() {
        binding.question.setVisibility(View.GONE);
        binding.ansA.setVisibility(View.GONE);
        binding.ansB.setVisibility(View.GONE);
        binding.ansC.setVisibility(View.GONE);
        binding.ansD.setVisibility(View.GONE);

        binding.ansA.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
        binding.ansB.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
        binding.ansC.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
        binding.ansD.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
    }

    private static Boolean checkClick() {
        return (getColor(binding.ansA.getBackground()) == context.getResources().getColor(R.color.purple_200) ||
                        getColor(binding.ansA.getBackground()) == (context.getResources().getColor(R.color.red))) &&
                (getColor(binding.ansB.getBackground()) == context.getResources().getColor(R.color.purple_200) ||
                        getColor(binding.ansB.getBackground()) == (context.getResources().getColor(R.color.red))) &&
                (getColor(binding.ansC.getBackground()) == context.getResources().getColor(R.color.purple_200) ||
                        getColor(binding.ansC.getBackground()) == (context.getResources().getColor(R.color.red))) &&
                (getColor(binding.ansD.getBackground()) == context.getResources().getColor(R.color.purple_200) ||
                        getColor(binding.ansD.getBackground()) == (context.getResources().getColor(R.color.red)));
    }

    private static void chooseQuestion() {
        countQuestion++;
        firstClick = true;
        int cnt = 0;
        boolean can = false;
        for (int i = 0; i < listQuestion.size(); i++) {
            if (countLoop[i] > maxLoop) {
                maxLoop = countLoop[i];
            }
        }
        for (int i = 0; i < listQuestion.size(); i++) {
            if (countLoop[i] == maxLoop) {
                cnt++;
            }
        }
        if (cnt == listQuestion.size()) {
            can = true;
        }
        if (can) {
            int choose = random.nextInt(listQuestion.size());
            question = listQuestion.get(choose);
            countLoop[choose]++;
        } else {
            while (true) {
                int choose = random.nextInt(listQuestion.size());
                if (countLoop[choose] < maxLoop) {
                    countLoop[choose]++;
                    question = listQuestion.get(choose);
                    break;
                }

            }
        }

    }

    private static void setScore() {
        binding.countQuestion.setText("Số câu hỏi: " + (countQuestion - 1));
        binding.correctAnswer.setText("Trả lời đúng: " + correctAnswer);
        Double v = (correctAnswer * 1000.0 / (countQuestion==1?1:countQuestion - 1));
        Integer vv = Integer.valueOf((int) (v*100));
        if (vv % 10 >= 5) {
            vv++;
        }
        vv /= 10;
        binding.scale.setText("Tỉ lệ đúng: " + vv*1.0/100 + "%");
    }

    private static int getColor(Drawable drawable) {
        ColorDrawable colorDrawable = (ColorDrawable) drawable;
        return colorDrawable.getColor();
    }

    private static void initLoop() {
        for (int i = 0; i < countLoop.length; i++) {
            countLoop[i] = 0;
        }
    }

}