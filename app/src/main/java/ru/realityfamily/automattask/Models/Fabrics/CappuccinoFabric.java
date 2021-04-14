package ru.realityfamily.automattask.Models.Fabrics;

import ru.realityfamily.automattask.Models.Coffee;
import ru.realityfamily.automattask.Models.IProduct;
import ru.realityfamily.automattask.Models.Snack;

public class CappuccinoFabric implements IFabric {
    @Override
    public IProduct generateProduct() {
        return new Coffee("Cappuccino", 25);
    }
}
