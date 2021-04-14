package ru.realityfamily.automattask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.realityfamily.automattask.Fragments.AutomatFragment;
import ru.realityfamily.automattask.Models.Automat;
import ru.realityfamily.automattask.Models.Student;

public class MainActivity extends FragmentActivity {
    private static MainActivity instance;

    List<Automat> automatList = new ArrayList<>();
    List<Student> studentList = new ArrayList<>();
    AutomatFragment fragment1;
    AutomatFragment fragment2;
    AutomatFragment fragment3;
    AutomatFragment fragment4;

    FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        fm = getSupportFragmentManager();
        for (int i = 1; i < 5; i++) {
            automatList.add(
                    new Automat(i)
            );
        }

        for (int i = 1; i < 21; i++) {
            studentList.add(
                    new Student(
                            i,
                            new Random().nextInt(5) + 3,
                            automatList.get(new Random().nextInt(automatList.size()))
                    )
            );
        }

        fragment1 = new AutomatFragment(automatList.get(0));
        fragment2 = new AutomatFragment(automatList.get(1));
        fragment3 = new AutomatFragment(automatList.get(2));
        fragment4 = new AutomatFragment(automatList.get(3));

        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment1, fragment1);
        ft.add(R.id.fragment2, fragment2);
        ft.add(R.id.fragment3, fragment3);
        ft.add(R.id.fragment4, fragment4);
        ft.commit();

        for(Student student : studentList) {
            student.startThread();
        }
    }

    public void UpdateData(Automat automat, Student student) {
        switch (automat.getName()){
            case 1:{
                fragment1.update(student);
                fragment1.updateQueue(CalculateQueue(1));
                break;
            }
            case 2:{
                fragment2.update(student);
                fragment2.updateQueue(CalculateQueue(2));
                break;
            }
            case 3:{
                fragment3.update(student);
                fragment3.updateQueue(CalculateQueue(3));
                break;
            }
            case 4:{
                fragment4.update(student);
                fragment4.updateQueue(CalculateQueue(4));
                break;
            }
        }

    }

    public String CalculateQueue(int automatName) {
        int queue = 0;

        for (Student student : studentList){
            if (student.getAutomatName() == automatName && student.isBuying())
                queue++;
        }
        return queue - 1 > 0 ? queue - 1 + " человек" : "Людей больше нет.";
    }

    public static MainActivity getInstance() {
        return instance;
    }
}