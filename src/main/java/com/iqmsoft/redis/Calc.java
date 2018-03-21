package com.iqmsoft.redis;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class Calc {

	private static Log logger = LogFactory.getLog(Calc.class);
	
	private Map<Integer,Integer> cache = new HashMap<>();
	
	private int numOfExecutions = 0;
	
	@Cacheable("calculation")
	public int calculateWithRedisCachable(int n) {
		numOfExecutions++;
		
		logger.info("Calculating Redis cachable Factorial sequence for N: [" + n + "]");
		
		
		if (n == 1)
			return 1;
		if (n == 0)
			return 1;
		return n * calculateWithRedisCachable(n - 1);
		
	}
	
	public int calculateWithHashMap(int n) {
		numOfExecutions++;
		
		logger.info("Calculating HashMap cached Factorial for N: [" + n + "]");
		
		
		if (n == 1)
			return 1;
		if (n == 0)
			return 1;
		
		
		logger.info("Calculating HashMap cahced Factorial for N: [" + n + "]" + n * calculateWithHashMap(n - 1));
		
		return n * calculateWithHashMap(n - 1);
		
	}
	
	public int calculate(int n) {
		numOfExecutions++;
		
		logger.info("Calculating HashMap cahced Factorial sequence for N: [" + n + "]");
		
		
		if (n == 1)
			return 1;
		if (n == 0)
			return 1;
		
		logger.info("Calculating HashMap cahced Factorial sequence for N: [" + n + "]" + n * calculate(n - 1));
		
		
		return n * calculate(n - 1);
		
	}

	public int getNumOfExecutions() {
		return numOfExecutions;
	}

	public void resetNumOfExecutions() {
		this.numOfExecutions = 0;
	}

	
}