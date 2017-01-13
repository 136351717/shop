package cn.itcast.shop.order.adminaction;

import java.util.List;

import cn.itcast.shop.order.service.OrderService;
import cn.itcast.shop.order.vo.Order;
import cn.itcast.shop.order.vo.OrderItem;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AdminOrderAction extends ActionSupport implements ModelDriven<Order> {
	
	private Order order = new Order();
	private OrderService orderService;
	private Integer page;

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public Order getModel() {
		return order;
	}
	
	
	//后台查询所有订单的方法
	public String findAll(){
		PageBean<Order> pageBean = orderService.findAll(page);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}
	
	
	//根据订单id查询订单项
	public String findOrderItem(){
		List<OrderItem> list = orderService.findOrderItem(order.getOid());
		ActionContext.getContext().getValueStack().set("list", list);
		return "findOrderItem";
	}
	
	//修改订单状态
	public String updateState(){
		order = orderService.findByOid(order.getOid());
		order.setState(3);
		orderService.save(order);
		return "updateStateSuccess";
	}
	
}
