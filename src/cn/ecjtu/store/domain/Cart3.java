package cn.ecjtu.store.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车实体类
 * 
 * @author 优雅而痞（Xiexiang)
 *
 */
public class Cart3 {
	//使用list来存储
	private List<CartItem> list = new ArrayList<CartItem>();// 个数不确定的购物项
	private double allTotal; // 购物车金额总计/积分
	// 添加购物项到购物车
	// 当用户点击加入购物车按钮时，可以将当前要购买商品的id,商品数量发送到服务端，服务端根据商品id查询到商品的信息
	// 有了商品信息product对象，有了要购买的数量，当前购物项也就得到了

	public void addCartItemToCart(CartItem cartItem) {
		// 判断之前是否已存在当前商品
		// 没有，直接list.add();
		// 存在，获取原先的数量，获取本次数量，相加
		// 设置变量，默认为false
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

	// 移除购物项
	// 当用户点击购物项链接时，可以将当前购物车类别的商品id发送到服务端
	public void removeCartItem(String pid) {
		/**
		 * 遍历list，看每个CartItem上的product对象上的id是否和服务器端获取到的pid相同，如果相同就删除
		 * 
		 * 
		 */
		for (CartItem cartItem : list) {
			if (cartItem.getProduct().getPid().equals(pid)) {
				// 直接调用list.remove，无法删除当前cartItem ,需要通过迭代器删除

			}
		}

	}

	// 清空购物车
	public void clearCart() {
		list.clear();
	}

}
