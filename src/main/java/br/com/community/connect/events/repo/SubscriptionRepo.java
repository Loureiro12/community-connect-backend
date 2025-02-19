package br.com.community.connect.events.repo;

import br.com.community.connect.events.model.Event;
import br.com.community.connect.events.model.Subscription;
import br.com.community.connect.events.model.User;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepo extends CrudRepository<Subscription, Integer> {
    public Subscription findByEventAndSubscriber(Event evt, User user);
}
