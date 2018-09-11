package cn.ecjtu.store.domain;
/**
 * ������ʵ����
 *
 */
public class CartItem {
	/**
	 * �����ó�����Ҫ������������
	 * 1.ͼƬ 2.��Ʒ3.�۸�4.����5.С��6.������
	 */
	private Product product;//Я����ͼƬ·������Ʒ���ƣ���Ʒ�۸��
	private int num;//��ǰ��Ʒ��������
	private double total;//С�ơ���ǰ��Ʒ���ܼ�
	
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
	 * ���Ծ�������ó�
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

