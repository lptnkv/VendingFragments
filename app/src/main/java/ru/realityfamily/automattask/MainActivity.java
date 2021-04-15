package ru.realityfamily.automattask;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    List<AutomatFragment> fragmentList = new ArrayList<>();

    public boolean oneFragmentShown = false;

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

        for (int i = 0; i < 4; i++){
            fragmentList.add(new AutomatFragment(automatList.get(i)));
        }
        /*
        Button button = (Button) findViewById(R.id.hideButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oneFragmentShown){
                    showAllAutomats();
                } else {
                    showOneAutomat(0);
                }
            }
        });
         */

        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment1, fragmentList.get(0));
        ft.add(R.id.fragment2, fragmentList.get(1));
        ft.add(R.id.fragment3, fragmentList.get(2));
        ft.add(R.id.fragment4, fragmentList.get(3));
        ft.commit();

        for(Student student : studentList) {
            student.startThread();
        }
    }

    public void UpdateData(Automat automat, Student student) {
        switch (automat.getName()){
            case 1:{
                fragmentList.get(0).update(student);
                fragmentList.get(0).updateQueue(CalculateQueue(1));
                break;
            }
            case 2:{
                fragmentList.get(1).update(student);
                fragmentList.get(1).updateQueue(CalculateQueue(2));
                break;
            }
            case 3:{
                fragmentList.get(2).update(student);
                fragmentList.get(2).updateQueue(CalculateQueue(3));
                break;
            }
            case 4:{
                fragmentList.get(3).update(student);
                fragmentList.get(3).updateQueue(CalculateQueue(4));
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

    public void showOneAutomat(int id){
        FragmentTransaction ft = fm.beginTransaction();
        for (int i = 0; i < fragmentList.size(); i++){
            if (i != id){
                ft.hide(fragmentList.get(i));
            }
        }
        ft.commit();
        oneFragmentShown = true;
    }

    public void showAllAutomats(){
        FragmentTransaction ft = fm.beginTransaction();
        for (int i = 0; i < fragmentList.size(); i++){
            if (fragmentList.get(i).isHidden()){
                ft.show(fragmentList.get(i));
            }
        }
        ft.commit();
        oneFragmentShown = false;
    }

    public static MainActivity getInstance() {
        return instance;
    }
}