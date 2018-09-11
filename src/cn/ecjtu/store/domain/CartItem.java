package cn.ecjtu.store.domain;
/**
 * 购物项实体类
 *
 */
public class CartItem {
	/**
	 * 分析得出，需要定义六个属性
	 * 1.图片 2.商品3.价格4.数量5.小计6.操作。
	 */
	private Product product;//携带了图片路径，商品名称，商品价格等
	private int num;//当前商品的数量。
	private double total;//小计。当前商品的总价
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	/**
	 * 可以经过计算得出
	 * @return
	 */
	public double getTotal() {
		return product.getShop_price()*num;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "CartItem [product=" + product + ", num=" + num + ", total=" + total + "]";
	}
	public CartItem(Product product, int num, double total) {
		super();
		this.product = product;
		this.num = num;
		this.total = total;
	}
	public CartItem() {
		super();
	}
	
}

