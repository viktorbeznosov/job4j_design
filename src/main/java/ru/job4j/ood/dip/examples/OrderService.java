package ru.job4j.ood.dip.examples;

public class OrderService {

    private MailSender sender = new MailSender();

    private OrderStore store = new OrderStore();

    public void createOrder(User user, Order order) {
        store.save(order);
        sender.send(user, "Order" + order.getId() + " created");
    }

}
