#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class IDGeneratorImpl implements IDGenerator {
	private ThreadLocal<SimpleDateFormat> tSDF = new ThreadLocal<SimpleDateFormat>();
//	private AtomicInteger order = new AtomicInteger();
	// 用来验证在多进程下生成的序号
//	private ThreadLocal<Integer> order = new ThreadLocal<Integer>(); 
	private AtomicInteger order = new AtomicInteger((int) (Math.random() * 1000000));
	private volatile Date lastTime = new Date();
	
	/**
	 * 通过随机的起始字段，一分钟内保持递增，单服务器1000000以内不会重复。
	 * @return
	 */
	@Override
	public long getID() {
		Date d = new Date();
		
		SimpleDateFormat sdf = tSDF.get();
		if (sdf == null) {
			sdf = new SimpleDateFormat("yyyyMMddHHmm");
			tSDF.set(sdf);
		}
		String format = sdf.format(d);
		
		int order1;
		// 多进程的情况下不稳定，利用随机的间隔来生成唯一的序号，生成的数量越少，唯一性越高
		// 超过1分钟重新随机一个初始值，避免id连续过多。还有多进程的情况下，万一第一次初始值区间不好，多次机会。
		if ((d.getTime() - lastTime.getTime()) > 60000) {
			// 随机初始
			Random rand = new Random();
			order1 = rand.nextInt(1000000);
			// 重复几次问题不大 减少锁的性能问题
			order.set(order1);
			lastTime = d;
		} else {
			order1 = order.incrementAndGet();
			order1 = order1 % 1000000;
		} 
//		Random rand = new Random();
//		int order2 = rand.nextInt(1000000);  // du : 2049724
		format = format + String.format("%06d", order1 & 0xFFFFFFFFL);
		return Long.parseLong(format);
	}
	
	@Override
	public String getIDStr() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static void main(String[] args) throws InterruptedException {
		final IDGeneratorImpl gen = new IDGeneratorImpl();
		final ConcurrentHashMap<Long, Long> duTest = new ConcurrentHashMap<>();
		final AtomicInteger du = new AtomicInteger(0);
//		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3);
		int count = 3;
		final CountDownLatch done = new CountDownLatch(count);
		final Object lock = new Object();
		long start = System.currentTimeMillis();
		for (int i = 0 ; i < count; ++i) {
			final int type = i  + 1;
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					for (int i = 0; i < 100000; ++i) {
						long id = gen.getID();
						synchronized (lock) {
							if (duTest.containsKey(id)) {
								du.incrementAndGet();
							}
							duTest.put(id, id);
						}
//						System.out.println(Thread.currentThread().getName() + " : " + id);
					}
					done.countDown();
				}
			}).start();
		}
		done.await();
		System.out.println("du : " + du.get());
		System.out.println(System.currentTimeMillis() - start);
//		Set<String> uuids = new HashSet<String>();
//		Set<Long> uuidsLeast = new HashSet<Long>();
//		Set<Long> uuidsHighest = new HashSet<Long>();
//		int uuidDu = 0;
//		int uuidLestDu = 0;
//		int uuidHighDu = 0;
//		for (int i = 0; i < 1000000; ++i) {
//			UUID randomUUID = UUID.randomUUID();
//
//			if (!uuids.add(randomUUID.toString())) {
//				uuidDu++;
//			};
//			if (!uuidsLeast.add(randomUUID.getLeastSignificantBits())) {
//				uuidLestDu++;
//			}
//
//			if (!uuidsHighest.add(randomUUID.getMostSignificantBits())) {
//				uuidHighDu++;
//			}
//		}
//		System.out.println(uuidDu);		
//		System.out.println(uuidLestDu);		
//		System.out.println(uuidHighDu);		
	}


}
