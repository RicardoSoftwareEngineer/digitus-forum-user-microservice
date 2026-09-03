package com.digitusforum.background;

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

import com.digitusforum.user.UserEntity;
import com.digitusforum.user.UserRepository;

@Service
public class BackgroundService {
	@Autowired
	BackgroundSaveRepository backgroundSaveRepository;
	@Autowired
	UserRepository userRepository;

	public BackgroundSaveVO save(BackgroundSaveVO vo) {
		if (vo == null || StringUtils.isBlank(vo.getUserId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "background.missing.userId");
		if (StringUtils.isBlank(vo.getName()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "background.missing.name");
		if (StringUtils.isBlank(vo.getWallpaperData()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "background.missing.wallpaperData");
		requireUser(vo.getUserId());

		BackgroundSaveEntity entity = new BackgroundSaveEntity();
		entity.setUserId(vo.getUserId());
		entity.setName(vo.getName().trim());
		entity.setWallpaperData(vo.getWallpaperData());
		entity.setDominantColor(StringUtils.defaultString(vo.getDominantColor()));
		entity.setCreatedIn(ZonedDateTime.now());
		entity.setDeleted(false);
		entity = backgroundSaveRepository.save(entity);
		return toVO(entity);
	}

	public List<BackgroundSaveVO> retrieveByUserId(String userId) {
		if (StringUtils.isBlank(userId))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "background.missing.userId");
		List<BackgroundSaveVO> out = new ArrayList<>();
		for (BackgroundSaveEntity entity : backgroundSaveRepository
				.findByUserIdAndDeletedIsFalseOrderByCreatedInDesc(userId)) {
			out.add(toVO(entity));
		}
		return out;
	}

	public BackgroundSaveVO select(BackgroundSaveVO vo) {
		if (vo == null || StringUtils.isBlank(vo.getUserId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "background.missing.userId");
		String backgroundId = StringUtils.isNotBlank(vo.getBackgroundId()) ? vo.getBackgroundId() : vo.getId();
		if (StringUtils.isBlank(backgroundId))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "background.missing.backgroundId");
		UserEntity user = requireUser(vo.getUserId());
		BackgroundSaveEntity entity = backgroundSaveRepository
				.findByIdAndUserIdAndDeletedIsFalse(backgroundId, vo.getUserId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "background.not.found"));
		user.setBackgroundAuto(Boolean.FALSE);
		user.setPinnedBackgroundId(entity.getId());
		userRepository.save(user);
		BackgroundSaveVO out = toVO(entity);
		applyPrefs(out, user);
		return out;
	}

	public BackgroundSaveVO setAuto(BackgroundSaveVO vo) {
		if (vo == null || StringUtils.isBlank(vo.getUserId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "background.missing.userId");
		UserEntity user = requireUser(vo.getUserId());
		user.setBackgroundAuto(Boolean.TRUE);
		user.setPinnedBackgroundId(null);
		userRepository.save(user);
		BackgroundSaveVO out = new BackgroundSaveVO();
		out.setUserId(user.getId());
		applyPrefs(out, user);
		return out;
	}

	public BackgroundSaveVO prefs(BackgroundSaveVO vo) {
		if (vo == null || StringUtils.isBlank(vo.getUserId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "background.missing.userId");
		UserEntity user = requireUser(vo.getUserId());
		BackgroundSaveVO out = new BackgroundSaveVO();
		out.setUserId(user.getId());
		applyPrefs(out, user);
		if (StringUtils.isNotBlank(user.getPinnedBackgroundId())) {
			Optional<BackgroundSaveEntity> pinned = backgroundSaveRepository
					.findByIdAndUserIdAndDeletedIsFalse(user.getPinnedBackgroundId(), user.getId());
			if (pinned.isPresent()) {
				BackgroundSaveVO save = toVO(pinned.get());
				out.setId(save.getId());
				out.setBackgroundId(save.getId());
				out.setName(save.getName());
				out.setWallpaperData(save.getWallpaperData());
				out.setDominantColor(save.getDominantColor());
				out.setCreatedIn(save.getCreatedIn());
			}
		}
		return out;
	}

	private UserEntity requireUser(String userId) {
		return userRepository.findByIdAndDeletedIsFalse(userId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user.not.found"));
	}

	private BackgroundSaveVO toVO(BackgroundSaveEntity entity) {
		BackgroundSaveVO vo = new ModelMapper().map(entity, BackgroundSaveVO.class);
		vo.setBackgroundId(entity.getId());
		return vo;
	}

	private void applyPrefs(BackgroundSaveVO vo, UserEntity user) {
		boolean auto = user.getBackgroundAuto() == null || Boolean.TRUE.equals(user.getBackgroundAuto());
		vo.setBackgroundAuto(auto);
		vo.setPinnedBackgroundId(user.getPinnedBackgroundId());
	}

}
