/**
 * 
 */
package cn.zetark.oauth2.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 描述：
 * 
 * <pre>
 * TODO(添加描述)
 * </pre>
 * 
 * @author [天明]jiannan@intbee.com
 * @version: 0.0.1 Jan 9, 2020-12:02:10 PM
 *
 */
@Controller
public class IndexController {

	@GetMapping("/")
	public String index() {
		return "index";
	}
}
