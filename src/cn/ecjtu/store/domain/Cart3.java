package cn.ecjtu.store.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * ���ﳵʵ����
 * 
 * @author ���Ŷ�Ʀ��Xiexiang)
 *
 */
public class Cart3 {
	//ʹ��list���洢
	private List<CartItem> list = new ArrayList<CartItem>();// ������ȷ���Ĺ�����
	private double allTotal; // ���ﳵ����ܼ�/����
	// ��ӹ�������ﳵ
	// ���û�������빺�ﳵ��ťʱ�����Խ���ǰҪ������Ʒ��id,��Ʒ�������͵�����ˣ�����˸�����Ʒid��ѯ����Ʒ����Ϣ
	// ������Ʒ��Ϣproduct��������Ҫ�������������ǰ������Ҳ�͵õ���

	public void addCartItemToCart(CartItem cartItem) {
		// �ж�֮ǰ�Ƿ��Ѵ��ڵ�ǰ��Ʒ
		// û�У�ֱ��list.add();
		// ���ڣ���ȡԭ�ȵ���������ȡ�������������
		// ���ñ�����Ĭ��Ϊfalse
		boolean flag = false;
		CartItem oldCartItem = null;
		for (CartItem cartItem2 : list) {
			if (cartItem2.getProduct().getPid().equals(cartItem.getProduct().getPid())) {
				flag = true;
				oldCartItem = cartItem2;
			}
		}

		if (flag == false) {
			list.add(cartItem);
		} else {
			int num = oldCartItem.getNum() + cartItem.getNum();
			oldCartItem.setNum(num);

		}

	}

	// �Ƴ�������
	// ���û��������������ʱ�����Խ���ǰ���ﳵ������Ʒid���͵������
	public void removeCartItem(String pid) {
		/**
		 * ����list����ÿ��CartItem�ϵ�product�����ϵ�id�Ƿ�ͷ������˻�ȡ����pid��ͬ�������ͬ��ɾ��
		 * 
		 * 
		 */
		for (CartItem cartItem : list) {
			if (cartItem.getProduct().getPid().equals(pid)) {
				// ֱ�ӵ���list.remove���޷�ɾ����ǰcartItem ,��Ҫͨ��������ɾ��

			}
		}

	}

	// ��չ��ﳵ
	public void clearCart() {
		list.clear();
	}

}
