package com.iqmsoft.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyCommandRunner implements CommandLineRunner {

	private static Log logger = LogFactory.getLog(MyCommandRunner.class);

	@Autowired
	@Qualifier("cacheRedisTemplate")
	private RedisTemplate<Integer, Integer> template;

	@Autowired
	private Calc service;

	private void caching() {
		resetCache();
		service.resetNumOfExecutions();
		int fibonacciSequence = 5;
		final Calc calcService = service;
		calcService.calculateWithRedisCachable(fibonacciSequence);
		logger.info("Number of Executions " + calcService.getNumOfExecutions());
	}

	@Override
	public void run(String... arg0) throws Exception {
		logger.info("Running a command client");
		caching();

	}
	
	private void resetCache(){
		
		this.template.getConnectionFactory().getConnection().flushAll();
		

	}

}