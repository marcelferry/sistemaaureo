package com.concafras.gestao.task;

import org.springframework.beans.factory.annotation.Autowired;

import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.service.EmailService;
import com.concafras.gestao.service.RodizioService;

public class EmailScheduler {

	@Autowired
	private EmailService emailService;

	@Autowired
	private RodizioService rodizioService;

	public void sendLembreteTodos() {
		Rodizio rodizio = rodizioService.findByAtivoTrue();
		emailService.sendLembreteTodos(rodizio);
	}
}
