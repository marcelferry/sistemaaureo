package com.concafras.gestao.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/gestao/healthCheck")
public class HealthCheckController {

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/ping")
	@ResponseBody
	public String ping(@RequestParam("time") long time) {
		return String.valueOf( time );
	}
}
