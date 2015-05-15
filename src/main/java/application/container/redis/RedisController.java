package application.container.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * java 使用redis
 * jedis
 * @author dingshijie
 *
 */
@Controller
@RequestMapping(value = "/redis")
public class RedisController {

	@Autowired
	private RedisService redisService;

	@RequestMapping(value = "/redis.html", method = RequestMethod.GET)
	public String redis() {
		System.out.println("redis page");
		return "redis/redis";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@RequestParam(value = "key") String key, @RequestParam(value = "value") String value, Model model) {
		model.addAttribute("result", redisService.add(key, value));

		return "redis/result";
	}

	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public String find(@RequestParam(value = "key") String key, Model model) {

		model.addAttribute("result", redisService.find(key));

		return "redis/result";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Object delete(@RequestParam(value = "key") String key, Model model) {
		model.addAttribute("result", redisService.delete(key));

		return "redis/result";
	}

	/**
	 * list 操作
	 */

	@RequestMapping(value = "/addtolist", method = RequestMethod.POST)
	public String addToList(String key, String value, Model model) {

		model.addAttribute("result", redisService.addToList(key, value));

		return "redis/result";
	}

	@RequestMapping(value = "/findlist", method = RequestMethod.POST)
	@ResponseBody
	public Object findList(String key) {
		return redisService.findList(key);
	}

	@RequestMapping(value = "/lpop", method = RequestMethod.POST)
	public String lPop(String key, Model model) {
		model.addAttribute("result", redisService.lPop(key));

		return "redis/result";
	}

	@RequestMapping(value = "/rpop", method = RequestMethod.POST)
	public String rPop(String key, Model model) {
		model.addAttribute("result", redisService.rPop(key));

		return "redis/result";
	}

	/**
	 * set 操作
	 */
	@RequestMapping(value = "/addtoset", method = RequestMethod.POST)
	public String addToSet(String key, String value, Model model) {
		model.addAttribute("result", redisService.addToSet(key, value));

		return "redis/result";
	}

	@RequestMapping(value = "findset", method = RequestMethod.POST)
	@ResponseBody
	public Object findSet(String key) {
		return redisService.findSet(key);
	}
}
