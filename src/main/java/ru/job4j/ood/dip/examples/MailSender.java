package ru.job4j.ood.dip.examples;

public class MailSender {
    public void send(User user, String message) {
        System.out.println("Send email to " + user.getName() + " " + user.getEmail());
    }
}
