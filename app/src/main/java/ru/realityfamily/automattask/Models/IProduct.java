package ru.realityfamily.automattask.Models;

import androidx.annotation.NonNull;

import java.util.Comparator;

public abstract class IProduct implements Comparable<IProduct> {
    private String name;
    private double cost;

    public IProduct(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @NonNull
    @Override
    public String toString() {
        return name + "\t" + cost + " у.е.\n";
    }

    @Override
    public int compareTo(IProduct o) {
        return this.getName().compareTo(o.getName());
    }
}
