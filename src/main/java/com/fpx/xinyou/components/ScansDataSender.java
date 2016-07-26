package com.fpx.xinyou.components;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpx.xinyou.conf.AmqpConfig;

@Component("scSender")
public class ScansDataSender implements RabbitTemplate.ConfirmCallback{
	
	
	
	private RabbitTemplate rabbitTemplate;
	
	
	private static final Logger logger = Logger.getLogger(ScansDataSender.class);
	
	@Autowired
	public ScansDataSender(RabbitTemplate rabbitTemplate ) {
	        this.rabbitTemplate = rabbitTemplate;
	        rabbitTemplate.setConfirmCallback(this); //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
	}
	
	
	 public void sendMsg(String strMsg) {
		try {
			CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
//			EventMessage msg = new EventMessage(AmqpConfig.QUEUE,AmqpConfig.EXCHANGE,datas);
			rabbitTemplate.convertAndSend(AmqpConfig.EXCHANGE, AmqpConfig.ROUTINGKEY, strMsg, correlationId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	 
	 
	 public boolean sendMsg(Object o) {
		 try {
			 CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
			 String strMsg = "";
			 ObjectMapper mapper = new ObjectMapper();
			 strMsg = mapper.writeValueAsString(o);
			 logger.warn("the msg will send to mq is: "+strMsg);
			 rabbitTemplate.convertAndSend(AmqpConfig.EXCHANGE, AmqpConfig.ROUTINGKEY, strMsg, correlationId);
		 } catch (Exception e) {
			 e.printStackTrace();
			 return false;
		 }
		 return true;
	 }
	
	 
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {

		System.out.println(" 回调id:" + correlationData);
        if (ack) {
            System.out.println("消息成功发送");
        } else {
            System.out.println("消息发送失败:" + cause);
        }
	}
	

}
