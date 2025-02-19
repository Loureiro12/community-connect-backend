package br.com.community.connect.events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.community.connect.events.model.Event;
import br.com.community.connect.events.service.EventService;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventService service;

    @PostMapping("/events")
    public Event addNewEvent(@RequestBody Event newEvent){
        return service.addNewEvent(newEvent);
    }

    @GetMapping("/events")
    public List<Event> getAllEvents(){
        return service.getAllEvents();
    }

    @GetMapping("/events/{prettyName}")
    public ResponseEntity<Event> getEventByPrettyName(@PathVariable String prettyName){
        Event evt = service.getByPrettyName(prettyName);

        if (evt != null) {
            return ResponseEntity.ok().body(evt);
        }

        return ResponseEntity.notFound().build();
    }
}
