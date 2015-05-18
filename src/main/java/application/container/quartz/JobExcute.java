package application.container.quartz;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JobExcute {

	/**
	 * 执行任务
	 */
	public void excute() {
		System.out.println("执行任务" + new DateTime());

		System.out.println("任务结束");
	}

}
