package com.owl.wifi.web.wifi.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.owl.wifi.content.Status;
import com.owl.wifi.util.KryoUtil;
import com.owl.wifi.util.Result;
import com.owl.wifi.web.dao.ShopprobeMapper;
import com.owl.wifi.web.entity.Shopprobe;
import com.owl.wifi.web.wifi.model.ProbeDeviceModel;
import com.owl.wifi.web.wifi.service.ProbeDeviceService;

@Service
@Transactional
public class ProbeDeviceServiceImpl implements ProbeDeviceService {

        private static Map<String, ProbeDeviceModel> cache;
        private static Kryo kryo = KryoUtil.buildMapKryo(String.class, ProbeDeviceModel.class);

        private static final Logger log = LoggerFactory.getLogger(ProbeDeviceServiceImpl.class);
        @Autowired
        private ShopprobeMapper shopprobeMapper;

        static {
                String date = new SimpleDateFormat("yyMMddHH").format(new Date());
                File file = new File("probe.db." + date);
                if (file.exists()) {
                        try {
                                InputStream in = new FileInputStream(file);
                                cache = kryo.readObject(new Input(in), HashMap.class);
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
                if (cache == null) {
                        cache = new HashMap<String, ProbeDeviceModel>(1024, 1.2f);
                }
        }

        @Override
        public Result saveProbeDevice(JsonArray json) throws ParseException {
                Result result = new Result();
                String dmac = json.get(0).getAsJsonObject().get("dmac").getAsString().replaceAll(":", "");
                Shopprobe shopProbe = shopprobeMapper.findShopShopIdByDevice(dmac.toLowerCase());
                if (shopProbe == null) {
                        result.setStatus(9999);
                        result.setInfo("设备未注册");
                        return result;
                }
                
                shopProbe.setLastuploadtime(new Date());
                shopprobeMapper.updateByPrimaryKeySelective(shopProbe);
                
                for (int i = 0; i < json.size(); i++) {
                        JsonObject node = json.get(i).getAsJsonObject();
                        log.debug(node.toString());
                        ProbeDeviceModel probe = parseProbeDeviceModel(shopProbe.getShopId(), dmac, node);
                        ProbeDeviceModel oldProbe = cache.get(probe.getId());
                        if (oldProbe != null) {
                                probe.setStime(Math.min(probe.getStime(), oldProbe.getStime()));
                                probe.setEtime(Math.max(probe.getEtime(), oldProbe.getEtime()));
                        }
                        cache.put(probe.getId(), probe);
                }
                result.setStatus(Status.success_status);
                result.setInfo(Status.success_info);
                return result;
        }

        private ProbeDeviceModel parseProbeDeviceModel(int shopId, String dmac, JsonObject node) throws ParseException {
                ProbeDeviceModel probedevice = new ProbeDeviceModel();
                probedevice.setMac(node.get("mac").getAsString().replaceAll(":", ""));
                probedevice.setDmac(dmac);
                probedevice.setStime(node.get("stime").getAsLong());
                probedevice.setEtime(node.get("etime").getAsLong());
                probedevice.setShopId(shopId);
                probedevice.setId(probedevice.getShopId() + probedevice.getMac());
                return probedevice;
        }

        @Override
        public List<ProbeDeviceModel> findLeaveMac(int interval) {
                long time = System.currentTimeMillis() / 1000 - interval;
                List<ProbeDeviceModel> list = new ArrayList<ProbeDeviceModel>();
                for (String key : cache.keySet()) {
                        ProbeDeviceModel model = cache.get(key);
                        if (model.getEtime() < time) {
                                list.add(model);
                        }
                }
                for (ProbeDeviceModel model : list) {
                        cache.remove(model.getId());
                }
                return list;
        }

        /**
         * 持久化wifi探针数据
         * 
         * @author admin
         *
         */
        @PreDestroy
        public void destory() {
                long start = System.currentTimeMillis();
                log.info("start to persist probe data!");
                String filename = "probe.db." + new SimpleDateFormat("yyMMddHH").format(new Date());
                try {
                        Output output = new Output(new FileOutputStream(filename));
                        kryo.writeObject(output, cache);
                        output.flush();
                        output.close();
                        log.info("persist completed! eclipse " + (System.currentTimeMillis() - start));
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }

        @Override
        public Collection<ProbeDeviceModel> getShopMacList(int shopId) {
                List<ProbeDeviceModel> list = new ArrayList<ProbeDeviceModel>();
                for (ProbeDeviceModel model : cache.values()) {
                        if (model.getShopId() == shopId) {
                                list.add(model);
                        }
                }
                return list;
        }

}
