package br.com.community.connect.events.service;

import br.com.community.connect.events.dto.SubscriptionResponse;
import br.com.community.connect.events.exception.EventNotFoundException;
import br.com.community.connect.events.exception.SubscriptionConflictException;
import br.com.community.connect.events.exception.UserIndicadorNotFoundException;
import br.com.community.connect.events.model.Event;
import br.com.community.connect.events.model.Subscription;
import br.com.community.connect.events.model.User;
import br.com.community.connect.events.repo.EventRepo;
import br.com.community.connect.events.repo.SubscriptionRepo;
import br.com.community.connect.events.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    @Autowired
    private EventRepo evtRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SubscriptionRepo subRepo;

    public SubscriptionResponse createNewSubscription(String eventName, User user, Integer userId) {
        Event evt = evtRepo.findByPrettyName(eventName);

        if (evt == null) {
            throw new EventNotFoundException("Evento "+eventName+ " não existe");
        }

        User userRec = userRepo.findByEmail(user.getEmail());
        if (userRec == null) {
            userRec = userRepo.save(user);
        }

        User indicador = userRepo.findById(userId).orElse(null);

        if (indicador == null) {
            throw new UserIndicadorNotFoundException("Usuário "+userId+ " indicador não existe");
        }

        Subscription subs = new Subscription();
        subs.setEvent(evt);
        subs.setSubscriber(userRec);
        subs.setIndication(indicador);

        Subscription tmpSub = subRepo.findByEventAndSubscriber(evt, userRec);
        if (tmpSub != null) {
            throw new SubscriptionConflictException("Já existe inscrição para o usuário "+userRec.getName()+ " no evento "+evt.getPrettyName());
        }

        Subscription res = subRepo.save(subs);
        return new SubscriptionResponse(res.getSubscriptionNumber(), "http/codecraft.com/subscription"+res.getEvent().getPrettyName()+"/"+res.getSubscriber().getId());
    }
}
