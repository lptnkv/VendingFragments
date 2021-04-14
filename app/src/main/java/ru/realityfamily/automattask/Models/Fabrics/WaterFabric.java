package ru.realityfamily.automattask.Models.Fabrics;

import ru.realityfamily.automattask.Models.IProduct;
import ru.realityfamily.automattask.Models.Snack;

public class WaterFabric implements IFabric {
    @Override
    public IProduct generateProduct() {
        return new Snack("Water", 10);
    }
}
