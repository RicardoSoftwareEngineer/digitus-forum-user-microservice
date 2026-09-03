package com.digitusforum.background;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BackgroundController {
	@Autowired
	BackgroundService backgroundService;

	@RequestMapping(value = "/user/v1/background/save")
	public BackgroundSaveVO save(@RequestBody BackgroundSaveVO vo) {
		return backgroundService.save(vo);
	}

	@RequestMapping(value = "/user/v1/background/retrieveByUserId")
	public List<BackgroundSaveVO> retrieveByUserId(@RequestBody BackgroundSaveVO vo) {
		return backgroundService.retrieveByUserId(vo.getUserId());
	}

	@RequestMapping(value = "/user/v1/background/select")
	public BackgroundSaveVO select(@RequestBody BackgroundSaveVO vo) {
		return backgroundService.select(vo);
	}

	@RequestMapping(value = "/user/v1/background/setAuto")
	public BackgroundSaveVO setAuto(@RequestBody BackgroundSaveVO vo) {
		return backgroundService.setAuto(vo);
	}

	@RequestMapping(value = "/user/v1/background/prefs")
	public BackgroundSaveVO prefs(@RequestBody BackgroundSaveVO vo) {
		return backgroundService.prefs(vo);
	}

}
