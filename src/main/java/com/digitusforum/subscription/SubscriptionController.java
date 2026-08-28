package com.digitusforum.subscription;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionController {
	@Autowired
	SubscriptionService subscriptionService;

	@RequestMapping(value = "/user/v1/subscription/retrieveByUserId")
	public List<SubscriptionVO> retrieveByUserId(@RequestBody SubscriptionVO subscriptionVO) {
		return subscriptionService.retrieveByUserId(subscriptionVO.getUserId());
	}

	@RequestMapping(value = "/user/v1/subscription/hasActive")
	public SubscriptionVO hasActive(@RequestBody SubscriptionVO subscriptionVO) {
		return subscriptionService.hasActive(subscriptionVO.getUserId(), subscriptionVO.getGuruId());
	}

	@RequestMapping(value = "/user/v1/subscription/upsert")
	public SubscriptionVO upsert(@RequestBody SubscriptionVO subscriptionVO) {
		return subscriptionService.upsert(subscriptionVO);
	}

}
