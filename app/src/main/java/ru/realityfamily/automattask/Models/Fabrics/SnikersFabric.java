package ru.realityfamily.automattask.Models.Fabrics;

import ru.realityfamily.automattask.Models.IProduct;
import ru.realityfamily.automattask.Models.Snack;

public class SnikersFabric implements IFabric {
    @Override
    public IProduct generateProduct() {
        return new Snack("Snikers", 32);
    }
}
