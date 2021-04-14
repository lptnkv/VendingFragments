package ru.realityfamily.automattask.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.realityfamily.automattask.Models.Automat;
import ru.realityfamily.automattask.Models.Student;
import ru.realityfamily.automattask.R;

public class AutomatFragment extends Fragment {
    Automat automat;

    public AutomatFragment(Automat automat){
        this.automat = automat;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.automat_fragment, container, false);
        ((TextView) v.findViewById(R.id.autoName)).setText("Автомат " + this.automat.getName());
        return v;
    }

    //Обновление информации фрагмента, передается студент
    public void update(Student student){
        if (automat.getStatus() == Automat.AutomatStatus.Waiting) {
            ((TextView) getView().findViewById(R.id.autoStatus)).setText(automat.getStatus().toString());
            ((TextView) getView().findViewById(R.id.clientId)).setText("");
            ((TextView) getView().findViewById(R.id.autoCart)).setText("");
            ((TextView) getView().findViewById(R.id.autoQueue1)).setText("");
        } else {
            ((TextView) getView().findViewById(R.id.autoStatus)).setText(automat.getStatus().toString());
            ((TextView) getView().findViewById(R.id.clientId)).setText(student.getName());
            ((TextView) getView().findViewById(R.id.autoCart)).setText(student.getCart().toString());
            ((TextView) getView().findViewById(R.id.autoQueue1)).setText("= " + student.CartCost() + " у.е.");
        }
    }

    public void updateQueue(String queue){
        ((TextView) getView().findViewById(R.id.autoQueue2)).setText(queue);
    }


}
