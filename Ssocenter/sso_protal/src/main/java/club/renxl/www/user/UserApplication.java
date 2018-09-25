package club.renxl.www.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 *   用户模块演进方向
 *   
 *   生产环境动态切换redis集群
 *   
 *   可以通过缓存预热加载用户信息到缓存
 *   
 *   @port 8878
 *   @author renxl
 *
 */
@SpringBootApplication
@EnableEurekaClient // 每一个微服务都是一个eureka客户端
@MapperScan("club.renxl.www.user.ssocenter.dao")
public class UserApplication {
	
	/**
	 * 文章基础提供者
	 * @param args
	 */
	// 基础提供者一律采用restTemplate
	// 消费者一律采用声明式rest feign
	public static void main(String[] args) {
		new SpringApplicationBuilder(UserApplication.class).web(true).run(args);

	}

}
