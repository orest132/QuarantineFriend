package me.kollcaku.QuarantineFriends.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SubscribeService {
    List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    public void subscribeStory(SseEmitter sseEmitter) {
        try {
            sseEmitter.send(SseEmitter.event().name("INIT"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sseEmitter.onCompletion(()->emitters.remove(sseEmitter));

        emitters.add(sseEmitter);
    }

    public void sendEventToAll(String eventName, Object data){
        for(SseEmitter emitter : emitters){
            try {
                emitter.onCompletion(()->emitters.remove(emitter));
                emitter.send(SseEmitter.event().name(eventName).data(data));
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }
}
