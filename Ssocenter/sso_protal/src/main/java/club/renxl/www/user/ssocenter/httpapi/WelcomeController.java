package club.renxl.www.user.ssocenter.httpapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import club.renxl.www.pageInfo.PageUtil;
import club.renxl.www.response.BaseResponse;
import club.renxl.www.user.ssocenter.dao.AreaMapper;
import club.renxl.www.user.ssocenter.dao.domain.Area;
import club.renxl.www.user.ssocenter.dao.domain.AreaExample;
import club.renxl.www.user.ssocenter.redis.dao.RedisDao;

@RestController
@RequestMapping("welcome")
public class WelcomeController {
	@Autowired
	private  AreaMapper areaMapper;
	@Autowired
	private	RedisDao redisDao;
	
	@RequestMapping("hi")
	public BaseResponse hi(String name) {
		redisDao.set(name, name);
		// System.out.println(redisDao.get(name));
		return BaseResponse.success(name);
	}
	
	
	@RequestMapping("hi1")
	public BaseResponse hi1() {
		AreaExample example = new AreaExample();
		PageHelper.startPage(1, 10);
		List<Area> selectByExample = areaMapper.selectByExample(example );
		PageInfo<Area> page = new PageInfo<Area>(selectByExample);

		PageUtil<Area>  pageUtil= new PageUtil<Area>();
		club.renxl.www.pageInfo.PageInfo<Area> pageInfoByPageInfo = pageUtil.getPageInfoByPageInfo(page, null);
		return BaseResponse.success(pageInfoByPageInfo);
	}
}
