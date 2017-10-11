package com.owl.wifi.web.talkingdata.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.owl.wifi.web.dao.TalkingdataPositionMapper;
import com.owl.wifi.web.entity.TalkingdataPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owl.wifi.util.Help;
import com.owl.wifi.util.MyDateUtil;
import com.owl.wifi.web.dao.TalkingdataDeviceMapper;
import com.owl.wifi.web.dao.TalkingdataMoreMapper;
import com.owl.wifi.web.dao.TalkingdataQueryMapper;
import com.owl.wifi.web.entity.TalkingdataDevice;
import com.owl.wifi.web.entity.TalkingdataMore;
import com.owl.wifi.web.entity.TalkingdataQuery;
import com.owl.wifi.web.talkingdata.api.Talkingdata;
import com.owl.wifi.web.talkingdata.service.TalkingdataQueryService;

@Service
public class TalkingdataQueryServiceImpl implements TalkingdataQueryService {
	private static final Logger log = LoggerFactory.getLogger(TalkingdataQueryServiceImpl.class);
	private static final short[] PORTS = new short[] { 1, 4, 5, 6, 7, 8,10};
	@Autowired
	TalkingdataQueryMapper talkingdataQueryMapper;

	@Autowired
	TalkingdataDeviceMapper talkingdataDeviceMapper;

	@Autowired
	TalkingdataMoreMapper talkingdataMoreMapper;

    @Autowired
    TalkingdataPositionMapper talkingdataPositionMapper;

	@Autowired
	Talkingdata talkingdata;

	@Override
	public void queryTalkingData(String mac) {		
		List<TalkingdataQuery> list = talkingdataQueryMapper.findTalkingdataQueryByMac(mac);
		mac = Help.normalMac(mac);
		for (short port : PORTS) {
			int flag = validTalkingdataPort(list, port);
			updateTalkingdata(mac, port, flag);
		}
	}


    /**
	 * 判断是否需要重新查询talkingdata
	 * @param list
	 * @param port
	 * @return
	 */
	private int validTalkingdataPort(List<TalkingdataQuery> list, short port) {
		if (list == null || list.size() == 0)
			return -1;
		List<Short> normal = new ArrayList<Short>();
		for (TalkingdataQuery query : list) {
			if (port == query.getPortId().shortValue()) {
				if (MyDateUtil.calculatDay(query.getQueryDatetime(), new Date()) > 180) {
					return 0;
				} else {
					return 1;
				}
			}
		}
		return -1;
	}

	/**更新talkingdata tag数据
	 * 
	 * @param mac
	 *            正常的mac地址
	 * @param port
	 *            接口编号
	 * @param flag
	 *            0 需要更新，1不需要更新 -1 数据不存在
	 */
	private void updateTalkingdata(String mac, short port, int flag) {
		if (flag == 1)
			return;
		log.info("query mac : " + mac + " port : " + port + " flag : " + flag);
		if (port == 1) {
			TalkingdataDevice device = talkingdata.idmapping(mac);
			if (device != null) {
				talkingdataDeviceMapper.insert(device);
			}
		} else if (port == 4) {
			List<TalkingdataMore> list = talkingdata.gameTag(mac);
			if (list != null && list.size() > 0)
				insertTalkingdataMore(list, mac);
		} else if (port == 5) {
			List<TalkingdataMore> list = talkingdata.consumeTag(mac);
			if (list != null && list.size() > 0)
				insertTalkingdataMore(list, mac);
		} else if (port == 6) {
			List<TalkingdataMore> list = talkingdata.demographicTag(mac);
			if (list != null && list.size() > 0)
				insertTalkingdataMore(list, mac);
		} else if (port == 7) {
			List<TalkingdataMore> list = talkingdata.appTag(mac);
			if (list != null && list.size() > 0)
				insertTalkingdataMore(list, mac);
		} else if (port == 8) {
			List<TalkingdataMore> list = talkingdata.financeTag(mac);
			if (list != null && list.size() > 0)
				insertTalkingdataMore(list, mac);
		} else if (port == 10) {
            //todo 统计最近一周数据
            List<TalkingdataPosition> list = talkingdata.getMacPosition(mac,"mac");
            if(list !=null && list.size()>0){
                insertTalkingdataPosition(list,mac);
            }
        }
		TalkingdataQuery query = createTalkingdataQuery(mac, port);
		if (flag == 0) {
			talkingdataQueryMapper.updateByPrimaryKey(query);
		} else if (flag == -1) {
			talkingdataQueryMapper.insert(query);
		}
	}

	private TalkingdataQuery createTalkingdataQuery(String mac, short port) {
		TalkingdataQuery query = new TalkingdataQuery();
		query.setMac(mac.replaceAll(":", ""));
		query.setPortId(port);
		query.setQueryDatetime(new Date());
		return query;
	}

	private void insertTalkingdataMore(List<TalkingdataMore> list, String mac) {
		String zipMac = mac.replaceAll(":", "");
		for (TalkingdataMore data : list) {
			data.setMac(zipMac);
			talkingdataMoreMapper.insert(data);
		}
	}
    private void insertTalkingdataPosition(List<TalkingdataPosition> list,String mac){
        for (TalkingdataPosition data : list) {
            talkingdataPositionMapper.insert(data);
        }
    }
}
