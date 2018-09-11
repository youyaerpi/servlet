package cn.ecjtu.store.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ecjtu.store.domain.Cart;
import cn.ecjtu.store.domain.CartItem;
import cn.ecjtu.store.domain.Product;
import cn.ecjtu.store.service.impl.ProductService;
import cn.ecjtu.store.service.impl.ProductServiceImpl;
import cn.ecjtu.store.web.base.BaseServlet;

@SuppressWarnings("serial")
public class CartServlet extends BaseServlet {
	
	//��ӹ�������ﳵ
	public String addCartItemToCart(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//��session��ȡ���ﳵ
		Cart cart=(Cart)req.getSession().getAttribute("cart");
		if(null==cart){
		  //�����ȡ����,�������ﳵ����,����session��
			cart=new Cart();
			req.getSession().setAttribute("cart", cart);
		}
   		//�����ȡ��,ʹ�ü���
		//��ȡ����Ʒid,����
		String pid=req.getParameter("pid");
		int num=Integer.parseInt(req.getParameter("quantity"));
		//ͨ����Ʒid��ѯ����Ʒ����
		ProductService ProductService=new ProductServiceImpl();
		Product product=ProductService.findProductByPid(pid);	
		//��ȡ��������Ĺ�����
		CartItem cartItem=new CartItem();
		cartItem.setNum(num);
		cartItem.setProduct(product);

		//���ù��ﳵ�ϵķ���
		cart.addCartItemToCart(cartItem);
		
		//�ض���/jsp/cart.jsp
		resp.sendRedirect("/shopping_mall/jsp/cart.jsp");
		//return "/jsp/cart.jsp";
		return  null;
	}
	
	//removeCartItem
	public String removeCartItem(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//��ȡ��ɾ����Ʒpid
		String pid=req.getParameter("id");
		//��ȡ�����ﳵ
		Cart cart=(Cart)req.getSession().getAttribute("cart");
		//���ù��ﳵɾ���������
		cart.removeCartItem(pid);
		//�ض���/jsp/cart.jsp
		resp.sendRedirect("/shopping_mall/jsp/cart.jsp");
		return null;
	}
	//clearCart
	public String clearCart(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//��ȡ���ﳵ
		Cart cart=(Cart)req.getSession().getAttribute("cart");
		//���ù��ﳵ�ϵ���չ��ﳵ����
		cart.clearCart();
		//���¶���/jsp/cart.jsp
		//Ϊʲôʹ���ض���
		//ʹ��ת����ÿ��ˢ��ҳ�棬�ͻ��ظ�ִ����ӹ�������ﳵ�ķ���������н���취
		resp.sendRedirect("/shopping_mall/jsp/cart.jsp");
		return null;
	}
}




