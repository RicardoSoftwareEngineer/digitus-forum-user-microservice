package com.digitusforum.purchase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseController {
	@Autowired
	PurchaseService purchaseService;

	@RequestMapping(value = "/user/v1/purchase/retrieveByUserId")
	public List<PurchaseVO> retrieveByUserId(@RequestBody PurchaseVO purchaseVO) {
		return purchaseService.retrieveByUserId(purchaseVO.getUserId());
	}

	@RequestMapping(value = "/user/v1/purchase/hasPurchase")
	public PurchaseVO hasPurchase(@RequestBody PurchaseVO purchaseVO) {
		return purchaseService.hasPurchase(purchaseVO.getUserId(), purchaseVO.getTrainingId());
	}

	@RequestMapping(value = "/user/v1/purchase/upsertPaid")
	public PurchaseVO upsertPaid(@RequestBody PurchaseVO purchaseVO) {
		return purchaseService.upsertPaid(purchaseVO);
	}

}
