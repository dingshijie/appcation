package application.container.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class Index {

	@RequestMapping(value = "index.html", method = RequestMethod.GET)
	public String index() {
		System.out.println("index page");
		return "index";
	}

}
