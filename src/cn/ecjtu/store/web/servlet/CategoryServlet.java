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
 * ����Servlet
 * 
 * @author ���Ŷ�Ʀ��Xiexiang)
 *
 */
@SuppressWarnings("serial")
public class CategoryServlet extends BaseServlet {
	public String findAllCats(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��redis�л�ȡȫ��������Ϣ
		Jedis jedis = JedisUtils.getJedis();
		String jsonStr = jedis.get("allcats");
		if (null == jsonStr || "".equals(jsonStr)) {
			/**
			 * ����ҵ����ȡȫ������ ��ȫ��������Ӧ���ͻ���
			 */
			CategoryService categoryService = new CategoryServiceImpl();
			List<Category> allCatsList = categoryService.getAllCats();
			// ת��Ϊjson��ʽ
			jsonStr = JSONArray.fromObject(allCatsList).toString();

			jedis.set("allCats", jsonStr);
			System.out.println(jsonStr);
			System.out.println("redis������û������");
			response.setContentType("application/json;charset=utf-8");
			// ��Ӧx
			response.getWriter().print(jsonStr);

		} else {
			System.out.println("redis������������");
			// ���������������Ӧ������ʱjson��ʽ��
			response.setContentType("application/json;charset=utf-8");
			// ��Ӧx
			response.getWriter().print(jsonStr);

		}
		JedisUtils.closeJedis(jedis);

		return null;
	}
}
