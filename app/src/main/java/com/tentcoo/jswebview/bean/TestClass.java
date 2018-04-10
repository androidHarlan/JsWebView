package com.tentcoo.jswebview.bean;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;


import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import android.util.Log;

import com.tentcoo.jswebview.EventBusTag;
import com.tentcoo.jswebview.TestApplication;
import com.tentcoo.jswebview.WifiUtils;
import com.tentcoo.pineapple.core.dispatch.service.IService;

import com.tentcoo.pineapple.core.framework.template.annotation.Mapping;
import com.tentcoo.pineapple.utils.SystemApiUtils;


public class TestClass implements IService {

	private WifiUtils localWifiUtils;
	private List<ScanResult> wifiResultList;

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getMappedName() {
		// TODO Auto-generated method stub
		return "testClass";
	}

	@Mapping(name = "flash_mode")
	public boolean getDemoService(int i, int y) {
		SystemApiUtils.getInstance(TestApplication.mContext).flashlightUtils();
		return true;
	}

	@Mapping(name = "rotation")
	public boolean  getRotationService() {
		Log.e("backinfo","getRotationService");
		EventBus.getDefault().post("true", EventBusTag.rotation);
		return true;
	}
	@Mapping(name = "geth5data")
	public boolean  geth5data() {
		Log.e("backinfo","getH5DatagetH5DatagetH5DatagetH5DatagetH5DatagetH5DatagetH5DatagetH5Data");
		EventBus.getDefault().post("true", EventBusTag.getH5Data);
		return true;
	}

	@Mapping(name = "getwifi")
	public String getWifListService() {
		localWifiUtils = new WifiUtils(TestApplication.mContext);
		localWifiUtils.WifiOpen();
		localWifiUtils.WifiStartScan();

		while (localWifiUtils.WifiCheckState() != WifiManager.WIFI_STATE_ENABLED) {
			Log.i("WifiState", String.valueOf(localWifiUtils.WifiCheckState()));
		}
		
		wifiResultList = localWifiUtils.getScanResults();
		localWifiUtils.getConfiguration();
		JSONArray jsonArray = new JSONArray();
		for (ScanResult result : wifiResultList) {
			JSONObject object = new JSONObject();
			try {
				object.put("SSID", result.SSID);
				object.put("level",Math.abs(result.level));
				System.out.println(Math.abs(result.level)+"");
				jsonArray.put(object);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}

		return jsonArray.toString();

	}

	@Mapping(name = "wifi_ssid")
	public boolean getWifService(String SSID) {
		Log.e("backinfo","SSID:"+SSID);
		int wifiItemId = localWifiUtils.IsConfiguration("\"" + SSID + "\"");
		if (wifiItemId != -1) {
			if (localWifiUtils.ConnectWifi(wifiItemId)) {

			}

			return true;
		} else {
			return false;
		}

	}

	@Mapping(name = "connect_wifi")
	public boolean getConnectWifiService(String SSID, String password) {
		int netId = localWifiUtils
				.AddWifiConfig(wifiResultList, SSID, password);
		if (netId != -1) {
			localWifiUtils.getConfiguration();// 添加了配置信息，要重新得到配置信
			if (localWifiUtils.ConnectWifi(netId)) {

			}
		} else {

		}
		return true;
	}
}
