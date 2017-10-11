package com.owl.wifi.task;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.owl.wifi.web.entity.Shop;
import com.owl.wifi.web.shop.service.ShopService;
import com.owl.wifi.web.shop.service.VisitShopService;
import com.owl.wifi.web.talkingdata.api.TalkingdataRunner;
import com.owl.wifi.web.talkingdata.service.TalkingdataQueryService;
import com.owl.wifi.web.wifi.model.ProbeDeviceModel;
import com.owl.wifi.web.wifi.service.ProbeDeviceService;

@Component
@EnableScheduling
public class ProbeMonitor {
        private static final Logger log = LoggerFactory.getLogger(ProbeMonitor.class);
        private static final ExecutorService executor = Executors.newCachedThreadPool();
        @Autowired
        ProbeDeviceService probeService;
        @Autowired
        VisitShopService visitShopService;
        @Autowired
        TalkingdataQueryService talkingdataQueryService;

        @Autowired
        ShopService shopService;

        @Scheduled(fixedRate = 180000)
        public void monitor() {
                List<ProbeDeviceModel> list = probeService.findLeaveMac(180);
                if (list == null || list.size() == 0)
                        return;
                log.info("visit shop count : " + list.size());
                visitShopService.addVisitShop(list);
                for (ProbeDeviceModel model : list) {
                        Shop shop = shopService.selectShopById(model.getShopId());
                        Integer tkFlag = shop.getTalkingdataflag();
                        if (tkFlag == null || tkFlag == 0) { // 手动导入TK data
                                continue;
                        }
                        executor.submit(new TalkingdataRunner(model.getMac(), talkingdataQueryService));
                }
        }
}
