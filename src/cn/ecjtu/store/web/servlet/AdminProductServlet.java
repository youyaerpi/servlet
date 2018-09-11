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
		// ��ȡ��ǰҳ
		int curNum = Integer.parseInt(parameter);
		// ����ҵ����ѯȫ����Ʒ���෵��pageModel
		ProductService productService = new ProductServiceImpl();
		PageModel pm = productService.findAllProductsWithPage(curNum);
		// ��pageModel�ŵ�request
		request.setAttribute("page", pm);
		// ת����/admin/product/list.jsp
		return "/admin/product/list.jsp";

	}

	public String addProductUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��ȡȫ��������Ϣ
		
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> list = categoryService.getAllCats();
		// ��ȫ��������Ϣ����request
		request.setAttribute("allCats", list);
		// ת����/admin/product/add.jsp
		return "/admin/product/add.jsp";

	}

	// addProduct
	public String addProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		//Я�����ϵ�������dao����
		Product product= new Product();
		// �����Ʒ���ϴ�ͼƬ
		try {
			// ����request.getInputStream();��ȡ��������ȫ�����ݣ����в�ֺͷ�װ
			// �ϴ�����
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> list = upload.parseRequest(request);
			// ��������
			for (FileItem fileItem : list) {
				if (fileItem.isFormField()) {
					// �����ǰ��ʱ��ͨ��
					// ����ͨ���ϵ�name���Ե�ֵ��Ϊ��������ȡ����������Ϊֵ������map��
					map.put(fileItem.getFieldName(), fileItem.getString("utf-8"));

				} else {
					// ��ȡ��Ҫ�����ļ�������,����uuid����
					String oidFileName = fileItem.getName();
					String newFileName = UploadUtils.getUUIDName(oidFileName);
					// �ϴ���
					// ��ȡ����������,ͨ�����������Եõ�ͼƬ����������
					InputStream input = fileItem.getInputStream();
					// ��ȡ��ʵ·���ͷּ�Ŀ¼
					String realPath = getServletContext().getRealPath("/products/3/");
					String dir = UploadUtils.getDir(newFileName);
					//ƴ��Ŀ¼
					String path=realPath+dir;
					//�ڴ�������һ��Ŀ¼
					File newDir= new File(path);
					if(!newDir.exists()){
						newDir.mkdirs();
						
					}
					//�ڷ���˴���һ�����ļ�����׺������ϴ�������˵��ļ�����׺һ����
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
			//����beanutils��map�е�������䵽product������ȥ
			BeanUtils.populate(product, map);
			product.setPid(UUIDUtils.getId());
			product.setPdate(new Date());
			product.setPflag(0);
			//����sevice_dao��product������Я�������ݴ������ݿ⣬�ض��򵽲�ѯȫ����Ʒ��Ϣ·��
			ProductService productService = new ProductServiceImpl();
			productService.savaProduct(product);
			response.sendRedirect("/shopping_mall/AdminProductServlet?method=findAllProductsWithPage&num=1");
		} catch (Exception e) {
			// TODO: handle exception
		}
		// ת����/admin/product/add.jsp
		return null;
	}
}
