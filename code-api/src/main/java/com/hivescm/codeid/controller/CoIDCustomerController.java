package com.hivescm.codeid.controller;

import com.hivescm.codeid.service.CodeIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoIDCustomerController {

	@Autowired
	private CodeIdService codeIdService;

	@RequestMapping(value = "/getID1", method = RequestMethod.GET)
	public String generatorID1() throws Exception {
		String json = "now: ,";
		String id = codeIdService.generatorId("1", "2000", json).getResult();
		return id;
	}

	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public String initCodeIDTemplate() {
		return codeIdService.initCodeIDTemplate().getResult();
	}
}
