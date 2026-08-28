package com.digitusforum.subscription;

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
public class SubscriptionService {
	public static final String STATUS_ACTIVE = "active";
	public static final String SCOPE_GURU = "guru";
	public static final String GURU_JAVA = "java";

	@Autowired
	SubscriptionRepository subscriptionRepository;

	public List<SubscriptionVO> retrieveByUserId(String userId) {
		if (StringUtils.isBlank(userId))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "subscription.missing.userId");
		List<SubscriptionVO> out = new ArrayList<>();
		ModelMapper mapper = new ModelMapper();
		for (SubscriptionEntity entity : subscriptionRepository.findByUserIdAndDeletedIsFalse(userId)) {
			SubscriptionVO vo = mapper.map(entity, SubscriptionVO.class);
			vo.setHasActive(STATUS_ACTIVE.equalsIgnoreCase(entity.getStatus()));
			out.add(vo);
		}
		return out;
	}

	public SubscriptionVO hasActive(String userId, String guruId) {
		if (StringUtils.isBlank(userId))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "subscription.missing.userId");
		if (StringUtils.isBlank(guruId))
			guruId = GURU_JAVA;
		SubscriptionVO vo = new SubscriptionVO();
		vo.setUserId(userId);
		vo.setGuruId(guruId);
		vo.setScope(SCOPE_GURU);
		Optional<SubscriptionEntity> found = subscriptionRepository.findByUserIdAndGuruIdAndDeletedIsFalse(userId,
				guruId);
		boolean active = found.isPresent() && STATUS_ACTIVE.equalsIgnoreCase(found.get().getStatus());
		vo.setHasActive(active);
		if (found.isPresent()) {
			SubscriptionEntity entity = found.get();
			vo.setSubscriptionId(entity.getSubscriptionId());
			vo.setStatus(entity.getStatus());
			vo.setScope(entity.getScope());
			vo.setStripeCustomerId(entity.getStripeCustomerId());
			vo.setStripeSubscriptionId(entity.getStripeSubscriptionId());
		}
		return vo;
	}

	public SubscriptionVO upsert(SubscriptionVO vo) {
		if (vo == null || StringUtils.isBlank(vo.getUserId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "subscription.missing.userId");
		if (StringUtils.isBlank(vo.getGuruId()))
			vo.setGuruId(GURU_JAVA);
		if (StringUtils.isBlank(vo.getScope()))
			vo.setScope(SCOPE_GURU);
		Optional<SubscriptionEntity> existing = subscriptionRepository
				.findByUserIdAndGuruIdAndDeletedIsFalse(vo.getUserId(), vo.getGuruId());
		SubscriptionEntity entity;
		if (existing.isPresent())
			entity = existing.get();
		else
			entity = new SubscriptionEntity();
		entity.setUserId(vo.getUserId());
		entity.setGuruId(vo.getGuruId());
		entity.setScope(vo.getScope());
		entity.setDeleted(false);
		if (StringUtils.isNotBlank(vo.getStatus()))
			entity.setStatus(vo.getStatus());
		if (StringUtils.isNotBlank(vo.getStripeCustomerId()))
			entity.setStripeCustomerId(vo.getStripeCustomerId());
		if (StringUtils.isNotBlank(vo.getStripeSubscriptionId()))
			entity.setStripeSubscriptionId(vo.getStripeSubscriptionId());
		entity = subscriptionRepository.save(entity);
		SubscriptionVO saved = new ModelMapper().map(entity, SubscriptionVO.class);
		saved.setHasActive(STATUS_ACTIVE.equalsIgnoreCase(entity.getStatus()));
		return saved;
	}

}
