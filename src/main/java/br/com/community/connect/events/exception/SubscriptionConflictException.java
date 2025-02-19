package br.com.community.connect.events.exception;

public class SubscriptionConflictException extends RuntimeException {
    public SubscriptionConflictException(String msg) {
        super(msg);
    }
}
