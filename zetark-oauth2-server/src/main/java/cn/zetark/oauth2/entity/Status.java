package cn.zetark.oauth2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 描述：
 * 
 * <pre>
 * TODO(添加描述)
 * </pre>
 * 
 * @author [天明]jiannan@intbee.com
 * @version: 0.0.1 Jan 9, 2020-3:50:18 PM
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status {
	private int		code;
	private String	msg;
}
