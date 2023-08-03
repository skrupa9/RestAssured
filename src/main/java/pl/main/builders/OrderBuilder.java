package pl.main.builders;

import pl.main.pojo.Order;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OrderBuilder {

    private Order order = new Order();

    public Order build() {
        Order builder = order;
        order = new Order();
        return builder;
    }

    public OrderBuilder withAllData() {
        withId();
        withPetId();
        withQuantity();
        withShipDate();
        withStatus();
        withComplete();
        return this;
    }

    public OrderBuilder withId() {
        order.setId(randomNumber(9));
        return this;
    }

    public OrderBuilder withId(Integer id) {
        order.setId(id);
        return this;
    }

    public OrderBuilder withPetId() {
        order.setPetId(randomNumber(10));
        return this;
    }

    public OrderBuilder withPetId(Integer petId) {
        order.setPetId(petId);
        return this;
    }

    public OrderBuilder withQuantity() {
        order.setQuantity(randomNumber(5));
        return this;
    }

    public OrderBuilder withQuantity(Integer quantity) {
        order.setQuantity(quantity);
        return this;
    }

    public OrderBuilder withShipDate() {
        order.setShipDate(currentTimeStamp());
        return this;
    }

    public OrderBuilder withShipDate(String shipDate) {
        order.setShipDate(shipDate);
        return this;
    }

    public OrderBuilder withStatus() {
        order.setStatus("placed");
        return this;
    }

    public OrderBuilder withStatus(String status) {
        order.setStatus(status);
        return this;
    }

    public OrderBuilder withComplete() {
        order.setComplete(true);
        return this;
    }

    public OrderBuilder withComplete(Boolean complete) {
        order.setComplete(complete);
        return this;
    }

    private int randomNumber(int maxValue) {
        return (int)(Math.random()*maxValue+1);
    }

    private String currentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(Calendar.getInstance().getTime());
    }

}
