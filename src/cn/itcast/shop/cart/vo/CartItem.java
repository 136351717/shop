package cn.itcast.shop.cart.vo;

import cn.itcast.shop.product.vo.Product;

/*
 * 购物项类的封装
 */

public class CartItem {
	
	private Product product;
	private int count;
	private double subTotal;
	
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubTotal() {
		return count*product.getShop_price();
	}
}
