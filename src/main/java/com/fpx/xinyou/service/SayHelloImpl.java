package com.fpx.xinyou.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.wcb.dubbo.test.SayHi;

@Service
public class SayHelloImpl implements SayHi {

	@Override
	public String hi(String name) {
		// TODO Auto-generated method stub
		return "Hi my boy "+name;
	}

}
