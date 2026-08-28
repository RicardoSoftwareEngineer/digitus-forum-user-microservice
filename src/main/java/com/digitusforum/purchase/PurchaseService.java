package com.digitusforum.purchase;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PurchaseService {
	public static final String STATUS_PAID = "paid";

	@Autowired
	PurchaseRepository purchaseRepository;

	public List<PurchaseVO> retrieveByUserId(String userId) {
		if (StringUtils.isBlank(userId))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "purchase.missing.userId");
		List<PurchaseVO> out = new ArrayList<>();
		ModelMapper mapper = new ModelMapper();
		for (PurchaseEntity entity : purchaseRepository.findByUserIdAndDeletedIsFalse(userId)) {
			out.add(mapper.map(entity, PurchaseVO.class));
		}
		return out;
	}

	public PurchaseVO hasPurchase(String userId, String trainingId) {
		if (StringUtils.isBlank(userId))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "purchase.missing.userId");
		if (StringUtils.isBlank(trainingId))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "purchase.missing.trainingId");
		PurchaseVO vo = new PurchaseVO();
		vo.setUserId(userId);
		vo.setTrainingId(trainingId);
		Optional<PurchaseEntity> found = purchaseRepository
				.findByUserIdAndTrainingIdAndStatusAndDeletedIsFalse(userId, trainingId, STATUS_PAID);
		vo.setHasPurchase(found.isPresent());
		if (found.isPresent()) {
			PurchaseEntity entity = found.get();
			vo.setPurchaseId(entity.getPurchaseId());
			vo.setStatus(entity.getStatus());
			vo.setStripeCheckoutSessionId(entity.getStripeCheckoutSessionId());
			vo.setStripePaymentIntentId(entity.getStripePaymentIntentId());
			vo.setCreatedIn(entity.getCreatedIn());
		}
		return vo;
	}

	public PurchaseVO upsertPaid(PurchaseVO vo) {
		if (vo == null || StringUtils.isBlank(vo.getUserId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "purchase.missing.userId");
		if (StringUtils.isBlank(vo.getTrainingId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "purchase.missing.trainingId");

		Optional<PurchaseEntity> existing = purchaseRepository
				.findByUserIdAndTrainingIdAndDeletedIsFalse(vo.getUserId(), vo.getTrainingId());
		PurchaseEntity entity = existing.orElseGet(PurchaseEntity::new);
		if (entity.getPurchaseId() == null)
			entity.setCreatedIn(ZonedDateTime.now());
		entity.setUserId(vo.getUserId());
		entity.setTrainingId(vo.getTrainingId());
		entity.setStatus(STATUS_PAID);
		entity.setDeleted(false);
		if (StringUtils.isNotBlank(vo.getStripeCheckoutSessionId()))
			entity.setStripeCheckoutSessionId(vo.getStripeCheckoutSessionId());
		if (StringUtils.isNotBlank(vo.getStripePaymentIntentId()))
			entity.setStripePaymentIntentId(vo.getStripePaymentIntentId());
		entity = purchaseRepository.save(entity);
		PurchaseVO saved = new ModelMapper().map(entity, PurchaseVO.class);
		saved.setHasPurchase(true);
		return saved;
	}

}
