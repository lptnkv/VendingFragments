package ru.realityfamily.automattask.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import ru.realityfamily.automattask.Models.Fabrics.AmericanoFabric;
import ru.realityfamily.automattask.Models.Fabrics.CappuccinoFabric;
import ru.realityfamily.automattask.Models.Fabrics.IFabric;
import ru.realityfamily.automattask.Models.Fabrics.SnikersFabric;
import ru.realityfamily.automattask.Models.Fabrics.TwixFabric;
import ru.realityfamily.automattask.Models.Fabrics.WaterFabric;

public class Automat {
    public enum AutomatStatus{
        Waiting,
        Client_Choosing,
        Client_Paying,
        Giving_Products
    }

    private final int name;
    private AutomatStatus status;
    private TreeMap<IProduct, Integer> snackMenu;

    public Automat(int name) {
        this.name = name;
        this.status = AutomatStatus.Waiting;
        snackMenu = new TreeMap<>();
        putProducts();
    }

    public void putProducts() {
        List<IFabric> fabrics = new ArrayList<>(
                Arrays.asList(
                        new AmericanoFabric(),
                        new CappuccinoFabric(),
                        new SnikersFabric(),
                        new TwixFabric(),
                        new WaterFabric()
                ));

        for (int i = 0; i < 60; i++) {
            int randomFabricIndex = new Random().nextInt(fabrics.size());

            int innerCount = snackMenu.getOrDefault(fabrics.get(randomFabricIndex).generateProduct(), 0);
            snackMenu.put(fabrics.get(randomFabricIndex).generateProduct(), innerCount + 1);
        }
    }

    public IProduct GetProduct() {
        List<IProduct> products = new ArrayList<>(snackMenu.keySet());

        int randomProductIndex = new Random().nextInt(products.size());
        if (snackMenu.get(products.get(randomProductIndex)) > 0) {
            snackMenu.put(products.get(randomProductIndex),
                    snackMenu.get(products.get(randomProductIndex)) - 1);
            return products.get(randomProductIndex);
        } else {
            return null;
        }
    }

    public void GiveProducts() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
    }

    public AutomatStatus getStatus() {
        return status;
    }

    public void setStatus(AutomatStatus status) {
        this.status = status;
    }

    public int getName() {
        return name;
    }
}
