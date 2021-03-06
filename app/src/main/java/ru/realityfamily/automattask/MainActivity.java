package ru.realityfamily.automattask;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

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
    FrameLayout mainFragment;
    ViewGroup tempParent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        fm = getSupportFragmentManager();
        mainFragment = findViewById(R.id.mainFragment);
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
        return queue - 1 > 0 ? queue - 1 + " ??????????????" : "?????????? ???????????? ??????.";
    }

    public void showOneAutomat(int id){
        FragmentTransaction ft = fm.beginTransaction();
        for (int i = 0; i < fragmentList.size(); i++){
            if (i != id){
                ft.hide(fragmentList.get(i));
            }
        }
        ft.commit();
        View v = fragmentList.get(id).getView();
        ViewGroup parent = (ViewGroup) v.getParent();
        parent.removeView(v);
        ViewGroup.LayoutParams lp = mainFragment.getLayoutParams();
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mainFragment.requestLayout();
        mainFragment.addView(v);
        tempParent = parent;
        oneFragmentShown = true;
    }

    public void showAllAutomats(){
        FragmentTransaction ft = fm.beginTransaction();
        for (int i = 0; i < fragmentList.size(); i++){
            if (fragmentList.get(i).isHidden()){
                ft.show(fragmentList.get(i));
            } else {
                ViewGroup.LayoutParams lp = mainFragment.getLayoutParams();
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                mainFragment.requestLayout();
                mainFragment.removeView(fragmentList.get(i).getView());
                tempParent.addView(fragmentList.get(i).getView());
            }
        }
        ft.commit();
        oneFragmentShown = false;
    }

    public static MainActivity getInstance() {
        return instance;
    }
}