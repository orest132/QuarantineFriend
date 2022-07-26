package me.kollcaku.QuarantineFriends.controller;

import me.kollcaku.QuarantineFriends.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static me.kollcaku.QuarantineFriends.utility.SecurityConstant.ANGULAR_CROSS_ORIGIN;
import static me.kollcaku.QuarantineFriends.utility.SecurityConstant.REQUEST_MAPPING;

@RestController
@RequestMapping(REQUEST_MAPPING)
@CrossOrigin(ANGULAR_CROSS_ORIGIN)
public class SubscribeController {
    SubscribeService subscribeService;

    @Autowired
    public SubscribeController(SubscribeService subscribeService) {
        this.subscribeService = subscribeService;
    }

    @CrossOrigin
    @RequestMapping(value = "/subscribe", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe() {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        this.subscribeService.subscribeStory(sseEmitter);
        return sseEmitter;
    }
}
