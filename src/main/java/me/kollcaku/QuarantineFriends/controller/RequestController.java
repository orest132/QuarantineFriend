package me.kollcaku.QuarantineFriends.controller;

import me.kollcaku.QuarantineFriends.dto.RequestDTO;
import me.kollcaku.QuarantineFriends.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static me.kollcaku.QuarantineFriends.utility.SecurityConstant.ANGULAR_CROSS_ORIGIN;
import static me.kollcaku.QuarantineFriends.utility.SecurityConstant.REQUEST_MAPPING;

@RestController
@RequestMapping(value = {REQUEST_MAPPING})
@CrossOrigin(ANGULAR_CROSS_ORIGIN)
public class RequestController {

    RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/request/send/{id}")
    public void sendRequest(@RequestBody RequestDTO request, @PathVariable("id") Long id){
        System.out.println(request.getFrom_user().toString());
        this.requestService.sendRequest(request, id);
    }

//    @GetMapping("/kot")
//    public void test(){
//        System.out.println("smth");
//    }

    @GetMapping("/request/all/{userId}")
    public List<RequestDTO> getAllRequests(@PathVariable("userId") Long id){
        System.out.println("in get");
        return this.requestService.getAllRequests(id);
    }

    @DeleteMapping("/request/{requestId}")
    public void deleteRequest(@PathVariable("requestId") Long id){
        this.requestService.deleteRequest(id);

    }

}
