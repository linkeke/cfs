package com.owl.wifi.web.talkingdata.api;

import org.slf4j.Logger;

import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.owl.wifi.web.talkingdata.service.TalkingdataQueryService;

public class TalkingdataRunner extends Thread {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(LoggerFactory.class);
	
	private TalkingdataQueryService talkingdataQueryService;

	private String mac;

	public TalkingdataRunner(String mac,TalkingdataQueryService talkingdataQueryService) {
		this.mac = mac ;
		this.talkingdataQueryService=talkingdataQueryService;
		this.setName("Thread " + mac);
	}

	public void run() {
		try {
			log.info("start query talkingdata for " + mac);
			talkingdataQueryService.queryTalkingData(mac);
		} catch (Exception e) {
			log.warn(e.toString() ,e);
		}
	}
}
