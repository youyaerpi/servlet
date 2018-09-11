package cn.ecjtu.store.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import cn.ecjtu.store.domain.Category;
import cn.ecjtu.store.domain.PageModel;
import cn.ecjtu.store.domain.Product;
import cn.ecjtu.store.service.impl.CategoryService;
import cn.ecjtu.store.service.impl.CategoryServiceImpl;
import cn.ecjtu.store.service.impl.ProductService;
import cn.ecjtu.store.service.impl.ProductServiceImpl;
import cn.ecjtu.store.utils.UUIDUtils;
import cn.ecjtu.store.utils.UploadUtils;
import cn.ecjtu.store.web.base.BaseServlet;

@SuppressWarnings("serial")
public class AdminProductServlet extends BaseServlet {

	public String findAllProductsWithPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String parameter = request.getParameter("num");
		// 获取当前页
		int curNum = Integer.parseInt(parameter);
		// 调用业务层查询全部商品分类返回pageModel
		ProductService productService = new ProductServiceImpl();
		PageModel pm = productService.findAllProductsWithPage(curNum);
		// 将pageModel放到request
		request.setAttribute("page", pm);
		// 转发到/admin/product/list.jsp
		return "/admin/product/list.jsp";

	}

	public String addProductUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取全部分类信息
		
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> list = categoryService.getAllCats();
		// 将全部分类信息放入request
		request.setAttribute("allCats", list);
		// 转发到/admin/product/add.jsp
		return "/admin/product/add.jsp";

	}

	// addProduct
	public String addProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		//携带表单上的数据向dao传递
		Product product= new Product();
		// 添加商品，上传图片
		try {
			// 利用request.getInputStream();获取请求体中全部数据，进行拆分和封装
			// 上传三步
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> list = upload.parseRequest(request);
			// 遍历集合
			for (FileItem fileItem : list) {
				if (fileItem.isFormField()) {
					// 如果当前项时普通项
					// 将普通项上的name属性的值作为键，将获取到的内容作为值，放入map中
					map.put(fileItem.getFieldName(), fileItem.getString("utf-8"));

				} else {
					// 获取到要保存文件的名称,生成uuid名称
					String oidFileName = fileItem.getName();
					String newFileName = UploadUtils.getUUIDName(oidFileName);
					// 上传项
					// 获取输入流对象,通过输入流可以得到图片二进制数据
					InputStream input = fileItem.getInputStream();
					// 获取真实路径和分级目录
					String realPath = getServletContext().getRealPath("/products/3/");
					String dir = UploadUtils.getDir(newFileName);
					//拼凑目录
					String path=realPath+dir;
					//内存中声明一个目录
					File newDir= new File(path);
					if(!newDir.exists()){
						newDir.mkdirs();
						
					}
					//在服务端创建一个空文件（后缀必须和上传到服务端的文件名后缀一至）
					File finalFile=new File(newDir,newFileName);
					if(!finalFile.exists()){
						finalFile.createNewFile();					
					}
					OutputStream output = new FileOutputStream(finalFile);
					IOUtils.copy(input, output);
					IOUtils.closeQuietly(input);
					IOUtils.closeQuietly(output);
					map.put("pimage", "/products/3/"+dir+"/"+newFileName);				
					}
			}
			//利用beanutils將map中的数据填充到product对象中去
			BeanUtils.populate(product, map);
			product.setPid(UUIDUtils.getId());
			product.setPdate(new Date());
			product.setPflag(0);
			//调用sevice_dao将product对象上携带的数据存入数据库，重定向到查询全部商品信息路径
			ProductService productService = new ProductServiceImpl();
			productService.savaProduct(product);
			response.sendRedirect("/shopping_mall/AdminProductServlet?method=findAllProductsWithPage&num=1");
		} catch (Exception e) {
			// TODO: handle exception
		}
		// 转发到/admin/product/add.jsp
		return null;
	}
}
