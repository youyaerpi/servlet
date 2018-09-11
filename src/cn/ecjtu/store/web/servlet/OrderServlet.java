package cn.ecjtu.store.web.servlet;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ecjtu.store.domain.Cart;
import cn.ecjtu.store.domain.CartItem;
import cn.ecjtu.store.domain.Order;
import cn.ecjtu.store.domain.OrderItem;
import cn.ecjtu.store.domain.PageModel;
import cn.ecjtu.store.domain.User;
import cn.ecjtu.store.service.impl.OrderService;
import cn.ecjtu.store.service.impl.OrderServiceImpl;
import cn.ecjtu.store.utils.PaymentUtil;
import cn.ecjtu.store.utils.UUIDUtils;
import cn.ecjtu.store.web.base.BaseServlet;

@SuppressWarnings("serial")
public class OrderServlet extends BaseServlet {

	public String saveOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/**
		 * �����ﳵ�е���Ϣ�Զ�������ʽ����
		 */
		// ȷ���û���¼״̬��
		User user = (User) request.getSession().getAttribute("loginUser");
		if (user == null) {
			request.setAttribute("msg", "����δ��¼�����½");
			return "/jsp/info.jsp";
		}
		// ��ȡ���ﳵ
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		// ������������Ϊ�丳ֵ
		Order order = new Order();
		order.setOid(UUIDUtils.getCode());
		order.setOrdertime(new Date());
		order.setTotal(cart.getTotal());
		order.setState(1);
		// �����Եõ�uid���������
		order.setUser(user);
		// �����������ͬʱ�����������Ϊ�����ֵ
		for (CartItem item : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(UUIDUtils.getCode());
			orderItem.setQuantity(item.getNum());
			orderItem.setTotal(item.getTotal());
			orderItem.setProduct(item.getProduct());
			orderItem.setOrder(order);
			order.getList().add(orderItem);
		}
		// ����ҵ��㹦��
		OrderService orderService = new OrderServiceImpl();
		orderService.saveOrder(order);

		// ��չ��ﳵ
		cart.clearCart();
		// ����������request
		request.setAttribute("order", order);
		// ת����/jsp/order_info.jsp

		return "/jsp/order_info.jsp";
	}

	public String findMyOrdersWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ��ȡ�û���Ϣ
		User user = (User) request.getSession().getAttribute("loginUser");
		// ��ȡ��ǰҳ,�������Ĳ���
		int curNum = Integer.parseInt(request.getParameter("num"));

		// ����ҵ��㹦�ܣ���ѯ��ǰ�û�������Ϣ������pagemodel
		OrderService orderService = new OrderServiceImpl();
		PageModel pm = orderService.findMyOrdersWithPage(user, curNum);
		// ��pagemodel����request
		request.setAttribute("page", pm);
		// ת����/jsp/order_list.jsp

		return "/jsp/order_list.jsp";
	}

	// findOrderByOid
	public String findOrderByOid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ��ȡ����oid
		String oid = request.getParameter("oid");
		// ����ҵ��㹦�ܣ����ݶ�����Ų�ѯ������Ϣ
		OrderService orderService = new OrderServiceImpl();
		Order order = orderService.findOrderByOid(oid);
		// ����������request��
		request.setAttribute("order", order);
		// ת����jsp/order_info.jsp
		return "/jsp/order_info.jsp";

	}

	/**
	 * ֧��
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String payOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ��ȡ����oid���ջ��˵�ַ���������绰������
		String oid = request.getParameter("oid");
		String address = request.getParameter("address");
		String name = request.getParameter("name");
		String telephone = request.getParameter("telephone");
		String pd_FrpId = request.getParameter("pd_FrpId");
		// ���¶����ϵ��ջ��˵�ַ���������绰
		OrderService orderService = new OrderServiceImpl();
		Order order = orderService.findOrderByOid(oid);
		order.setName(name);
		order.setTelephone(telephone);
		order.setAddress(address);
		orderService.updateOrder(order);
		// ���ױ�֧�����Ͳ���
		// �Ѹ�������Ҫ�Ĳ���׼����:
		String p0_Cmd = "Buy";
		// �̻����
		String p1_MerId = "10001126856";
		// �������
		String p2_Order = oid;
		// ���,ʵ��Ϊorder.getTotal
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		// ������Ӧ������Servlet
		String p8_Url = "http://localhost:8080/shopping_mall/OrderServlet?method=callBack";
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		// ��˾����Կ
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";

		// �����ױ��ļ����㷨,���������ݽ��м���,���ص���ǩ��
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
				p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

		StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);

		System.out.println(sb.toString());
		//
		// ʹ���ض���
		response.sendRedirect(sb.toString());
		return null;

	}

	// callBack
	public String callBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// �����ױ�֧��������
		// ��֤������Դ��������Ч��
		// �Ķ�֧���������˵��
		// System.out.println("==============================================");
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");

		// hmac
		String hmac = request.getParameter("hmac");
		// ���ñ�����Կ�ͼ����㷨 ��������
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";

		// ��֤���ݺϷ���
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid,
				r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
		if (isValid) {
			// ��Ч
			if (r9_BType.equals("1")) {
				// ������ض���

				// ���֧���ɹ�,���¶���״̬
				OrderService OrderService = new OrderServiceImpl();
				Order order = OrderService.findOrderByOid(r6_Order);
				order.setState(2);
				OrderService.updateOrder(order);
				// ��request������ʾ��Ϣ
				request.setAttribute("msg", "֧���ɹ��������ţ�" + r6_Order + "��" + r3_Amt);
				// ת����/jsp/info.jsp
				return "/jsp/info.jsp";
			} else if (r9_BType.equals("2")) {
				// �޸Ķ���״̬:
				// ��������Ե㣬�������ױ���֪ͨ
				System.out.println("�յ��ױ�֪ͨ���޸Ķ���״̬��");//
				// �ظ����ױ�success��������ظ����ױ���һֱ֪ͨ
				response.getWriter().print("success");
			}

		} else {
			throw new RuntimeException("���ݱ��۸ģ�");
		}
		return null;

	}

}
