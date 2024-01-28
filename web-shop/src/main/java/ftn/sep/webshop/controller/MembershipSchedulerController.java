package ftn.sep.webshop.controller;

import ftn.sep.webshop.service.interfaces.IMembershipScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class MembershipSchedulerController {
    @Autowired
    private IMembershipScheduleService membershipScheduleService;

    @Scheduled(cron = "0 0 0 * * *")
    public void renewMembership() {
        membershipScheduleService.renewMembership();
    }
}
