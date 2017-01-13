package cn.itcast.shop.cart.vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * 购物车
 */

public class Cart implements Serializable{
	//属性
	private Map<Integer,CartItem> map = new LinkedHashMap<Integer, CartItem>();
	
	public Collection<CartItem> getCartItems(){
		return map.values();
	}
	
	private double total;

	public double getTotal() {
		return total;
	}
	
	
	//购物车的三个功能
	//1 将购物项从购物车中移除
	public void removeCart(Integer pid){
		//移除购物项
		CartItem cartItem = map.remove(pid);
		//总价减少
		total -= cartItem.getSubTotal();
	}
	
	//2 清空购物车
	public void clearCart(){
		map.clear();
		//总价设为0
		total = 0;
	}
	
	//3 向购物车是添加购物项
	public void addCart(CartItem cartItem){
		//获取购物项的pid
		Integer pid = cartItem.getProduct().getPid();
		//查看集合中是否存在购物项
		if(map.containsKey(pid)){
			CartItem _cartItem = map.get(pid);
			_cartItem.setCount(_cartItem.getCount()+cartItem.getCount());
		}else{
			map.put(pid, cartItem);
		}
		total += cartItem.getSubTotal();
		
	}
}
