package ru.dyakun.citnis.model.data;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.*;

public class SubscribersStat {

    private final DoubleProperty percent = new SimpleDoubleProperty();
    private final IntegerProperty beneficiariesCount = new SimpleIntegerProperty();
    private final IntegerProperty total = new SimpleIntegerProperty();

    public SubscribersStat() {
        percent.bind(new DoubleBinding() {
            {
                super.bind(beneficiariesCount);
                super.bind(total);
            }
            @Override
            protected double computeValue() {
                return (total.get() == 0) ? 0 : 100 * beneficiariesCount.get() / (double) total.get();
            }
        });
    }

    public void setBeneficiariesCount(int beneficiariesCount) {
        this.beneficiariesCount.set(beneficiariesCount);
    }

    public void setTotal(int total) {
        this.total.set(total);
    }

    public double getPercent() {
        return percent.get();
    }

    public DoubleProperty percentProperty() {
        return percent;
    }

    public int getBeneficiariesCount() {
        return beneficiariesCount.get();
    }

    public IntegerProperty beneficiariesCountProperty() {
        return beneficiariesCount;
    }

    public int getTotal() {
        return total.get();
    }

    public IntegerProperty totalProperty() {
        return total;
    }

}
