package cn.ecjtu.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JDialog;

import cn.ecjtu.store.domain.Category;
import cn.ecjtu.store.service.impl.CategoryService;
import cn.ecjtu.store.service.impl.CategoryServiceImpl;
import cn.ecjtu.store.utils.JedisUtils;
import cn.ecjtu.store.web.base.BaseServlet;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

/**
 * 分类Servlet
 * 
 * @author 优雅而痞（Xiexiang)
 *
 */
@SuppressWarnings("serial")
public class CategoryServlet extends BaseServlet {
	public String findAllCats(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 在redis中获取全部分类信息
		Jedis jedis = JedisUtils.getJedis();
		String jsonStr = jedis.get("allcats");
		if (null == jsonStr || "".equals(jsonStr)) {
			/**
			 * 调用业务层获取全部分类 将全部分类响应到客户端
			 */
			CategoryService categoryService = new CategoryServiceImpl();
			List<Category> allCatsList = categoryService.getAllCats();
			// 转换为json格式
			jsonStr = JSONArray.fromObject(allCatsList).toString();

			jedis.set("allCats", jsonStr);
			System.out.println(jsonStr);
			System.out.println("redis缓存中没有数据");
			response.setContentType("application/json;charset=utf-8");
			// 响应x
			response.getWriter().print(jsonStr);

		} else {
			System.out.println("redis缓存中有数据");
			// 告诉浏览器本次响应的数据时json格式的
			response.setContentType("application/json;charset=utf-8");
			// 响应x
			response.getWriter().print(jsonStr);

		}
		JedisUtils.closeJedis(jedis);

		return null;
	}
}
