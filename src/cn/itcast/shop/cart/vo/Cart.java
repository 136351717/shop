package cn.itcast.shop.cart.vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * ���ﳵ
 */

public class Cart implements Serializable{
	//����
	private Map<Integer,CartItem> map = new LinkedHashMap<Integer, CartItem>();
	
	public Collection<CartItem> getCartItems(){
		return map.values();
	}
	
	private double total;

	public double getTotal() {
		return total;
	}
	
	
	//���ﳵ����������
	//1 ��������ӹ��ﳵ���Ƴ�
	public void removeCart(Integer pid){
		//�Ƴ�������
		CartItem cartItem = map.remove(pid);
		//�ܼۼ���
		total -= cartItem.getSubTotal();
	}
	
	//2 ��չ��ﳵ
	public void clearCart(){
		map.clear();
		//�ܼ���Ϊ0
		total = 0;
	}
	
	//3 ���ﳵ����ӹ�����
	public void addCart(CartItem cartItem){
		//��ȡ�������pid
		Integer pid = cartItem.getProduct().getPid();
		//�鿴�������Ƿ���ڹ�����
		if(map.containsKey(pid)){
			CartItem _cartItem = map.get(pid);
			_cartItem.setCount(_cartItem.getCount()+cartItem.getCount());
		}else{
			map.put(pid, cartItem);
		}
		total += cartItem.getSubTotal();
		
	}
}
