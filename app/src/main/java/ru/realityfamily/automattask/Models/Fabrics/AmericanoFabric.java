package ru.realityfamily.automattask.Models.Fabrics;

import ru.realityfamily.automattask.Models.Coffee;
import ru.realityfamily.automattask.Models.IProduct;

public class AmericanoFabric implements IFabric {
    @Override
    public IProduct generateProduct() {
        return new Coffee("Americano", 20);
    }
}
