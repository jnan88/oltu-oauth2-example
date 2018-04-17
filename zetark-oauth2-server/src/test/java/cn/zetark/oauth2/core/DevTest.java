/**
 * 
 */
package cn.zetark.oauth2.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @Description: 包含事务的测试
 * 
 *               <pre>
 &#64;RunWith(SpringJUnit4ClassRunner.class)
 &#64;TransactionalDevTest
 *               </pre>
 * 
 * @Author qizai
 * @Version: 0.0.1
 * @CreateAt 2016年8月12日-上午10:07:22
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ContextConfiguration({ "/conf/spring/context.xml" })
@ActiveProfiles(Profiles.DEV)
@Transactional
public @interface DevTest {

}
