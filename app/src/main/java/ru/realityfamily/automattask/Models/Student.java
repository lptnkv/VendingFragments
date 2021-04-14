package ru.realityfamily.automattask.Models;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import ru.realityfamily.automattask.MainActivity;

public class Student {
    private final String name;
    private List<IProduct> cart = new ArrayList<>();
    private final int cartCount;
    private final Automat automat;
    private volatile boolean buying = false;

    private StudentTask task;
    private StudentThread thread;

    public Student(int name, int cartCount, Automat automat) {
        this.name = "Студент №" + name;
        this.cartCount = cartCount;
        this.automat = automat;

        this.task = new StudentTask(automat, this);
        this.thread = new StudentThread(automat, this);
    }

    public void ChooseProduct() throws InterruptedException {
        while (cart.size() < cartCount) {
            IProduct product = automat.GetProduct();
            if (product != null) {
                TimeUnit.SECONDS.sleep(1);
                cart.add(product);
            }
        }
    }

    public double CartCost() {
        double finalCost = 0;
        for (IProduct product : cart) {
            finalCost += product.getCost();
        }
        return finalCost;
    }

    public void PayForCart() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
    }

    public void StartTask() {
        if (task == null) return;
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void startThread(){
        if (thread == null) return;
        thread.start();
    }

    public String getName() {
        return name;
    }

    public List<IProduct> getCart() {
        return cart;
    }

    public int getAutomatName() {
        return automat.getName();
    }

    public AsyncTask.Status getTaskStatus() {
        return task.getStatus();
    }

    public boolean isBuying(){
        return this.buying;
    }

    class StudentThread extends Thread{
        final Automat automat;
        final Student student;

        public StudentThread(Automat automat, Student student){
            this.automat = automat;
            this.student = student;
        }

        @Override
        public void run() {
            synchronized (automat){
                buying = true;
                updateActivity();
                automat.setStatus(Automat.AutomatStatus.Client_Choosing);
                updateActivity();
                try{
                    student.ChooseProduct();
                    updateActivity();
                    automat.setStatus(Automat.AutomatStatus.Client_Paying);
                    updateActivity();
                    student.PayForCart();
                    updateActivity();
                    automat.setStatus(Automat.AutomatStatus.Giving_Products);
                    updateActivity();
                    automat.GiveProducts();
                    automat.setStatus(Automat.AutomatStatus.Waiting);
                    updateActivity();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                buying = false;
            }
        }

        public void updateActivity(){
            MainActivity.getInstance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MainActivity.getInstance().UpdateData(automat, student);
                }
            });
        }
    }

    class StudentTask extends AsyncTask<Void, Void, Void> {
        final Automat automat;
        final Student student;

        public StudentTask(Automat automat, Student student) {
            this.automat = automat;
            this.student = student;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            synchronized (automat) {
                publishProgress();
                automat.setStatus(Automat.AutomatStatus.Client_Choosing);
                publishProgress();
                try {
                    student.ChooseProduct();
                    publishProgress();
                    automat.setStatus(Automat.AutomatStatus.Client_Paying);
                    publishProgress();
                    student.PayForCart();
                    automat.setStatus(Automat.AutomatStatus.Giving_Products);
                    publishProgress();
                    automat.GiveProducts();
                    automat.setStatus(Automat.AutomatStatus.Waiting);
                    publishProgress();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            MainActivity.getInstance().UpdateData(automat, student);
        }
    }
}
