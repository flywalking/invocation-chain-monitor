package com.demo.monitor.manager;

import com.demo.monitor.constant.SampleFlagConstant;

public class SampleFlagHandler {
	
	
	public static float getSampleChance(){
		//TODO
		return 1;
	}
	
	public static int  getSampleFlag(){
		java.util.Random r = new java.util.Random(System.currentTimeMillis());
		float f = r.nextInt(10)/9;
		if(f<=getSampleChance()){
			return SampleFlagConstant.sample;
		}
		return SampleFlagConstant.notSample;
	}
}
