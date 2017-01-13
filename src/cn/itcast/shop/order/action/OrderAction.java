package cn.itcast.shop.order.action;

import java.io.IOException;
import java.util.Date;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.cart.vo.Cart;
import cn.itcast.shop.cart.vo.CartItem;
import cn.itcast.shop.order.service.OrderService;
import cn.itcast.shop.order.vo.Order;
import cn.itcast.shop.order.vo.OrderItem;
import cn.itcast.shop.user.vo.User;
import cn.itcast.shop.utils.PageBean;
import cn.itcast.shop.utils.PaymentUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * ����Action��
 * 
 * @author ����.����
 * 
 */
public class OrderAction extends ActionSupport implements ModelDriven<Order> {
	// ģ������ʹ�õĶ���
	private Order order = new Order();

	public Order getModel() {
		return order;
	}
	
	private String pd_FrpId;
	private String r3_Amt;
	
	public void setR3_Amt(String r3Amt) {
		r3_Amt = r3Amt;
	}

	public void setPd_FrpId(String pdFrpId) {
		pd_FrpId = pdFrpId;
	}

	private Integer page;
	private String r6_Order;
	

	public void setR6_Order(String r6Order) {
		r6_Order = r6Order;
	}

	public void setPage(Integer page) {
		this.page = page;
	}


	// ע��OrderService
	private OrderService orderService;

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	//��ת����ҳ��
	public String save(){
		//��ȡ��session�еĹ��ﳵ
		Cart cart = (Cart)ActionContext.getContext().getSession().get("cart");
		//��װorder����,���ڴ洢�����ݿ�
		if(cart!=null){
			order.setTotal(cart.getTotal());
		}else{
			this.addActionError("û�й���,��Ҫ�ȹ�������ύ����!");
			return "msg";
		}
		order.setOrdertime(new Date());
		order.setState(1);
		//��ȡ����¼���û���������ȡ���û���Ϣ
		User user = (User)ActionContext.getContext().getSession().get("existUser");
		if(user!=null){
			order.setUser(user);
		}else{
			this.addActionError("û�е�¼,��Ҫ�ȵ�¼�����ύ����!");
			return "login";
		}
		//��ȡ�õĹ��ﳵ�еĹ������б�������Ȼ��ֵ�������������ڽ�������洢�����ݿ���
		for(CartItem cartItem:cart.getCartItems()){
			OrderItem orderItem = new OrderItem();
			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getSubTotal());
			orderItem.setOrder(order);
			orderItem.setProduct(cartItem.getProduct());
			//�򶩵������ÿ�α����õ��Ķ�����
			order.getOrderItems().add(orderItem);
		}
		//����orderService����������Ͷ�������󶼴洢�����ݿ���
		orderService.save(order);
		//�����������ݿ��к���Ҫ��չ��ﳵ
		cart.clearCart();
		return "saveOrder";
	}
	
	//�����û���ID��ѯ��ǰ��¼�û������ж���
	public String findByUid(){
		User existUser = (User)ActionContext.getContext().getSession().get("existUser");
		Integer uid = existUser.getUid();
		PageBean<Order> pageBean = orderService.findByUid(uid,page);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByUidSuccess";
	}
	
	//�ڶ����б�ҳ���е��δ�������ת������ҳ��,��Ҫ�����ݿ��и��ݴ����oid����ָ������������ʾ������ҳ�������û�����
	public String findByOid(){
		order = orderService.findByOid(order.getOid());
		return "findByOidSuccess";
	}
	
	public String payOrder() throws IOException{
		//����oid��ѯ��������Ķ���,���ҽ���ַ�����͵绰����Ϣһ����뵽Order����
		Order currOrder = orderService.findByOid(order.getOid());
		currOrder.setAddr(order.getAddr());
		currOrder.setName(order.getName());
		currOrder.setPhone(order.getPhone());
		orderService.update(currOrder);
		
		//֧����������
		String p0_Cmd = "Buy"; // ҵ������:
		String p1_MerId = "10001126856";// �̻����:
		String p2_Order = order.getOid().toString();// �������:
		String p3_Amt = "0.01"; // ������:
		String p4_Cur = "CNY"; // ���ױ���:
		String p5_Pid = ""; // ��Ʒ����:
		String p6_Pcat = ""; // ��Ʒ����:
		String p7_Pdesc = ""; // ��Ʒ����:
		String p8_Url = "http://192.168.1.101:8080/shop/order_callBack.action"; // �̻�����֧���ɹ����ݵĵ�ַ:
		String p9_SAF = ""; // �ͻ���ַ:
		String pa_MP = ""; // �̻���չ��Ϣ:
		String pd_FrpId = this.pd_FrpId;// ֧��ͨ������:
		String pr_NeedResponse = "1"; // Ӧ�����:
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl"; // ��Կ
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue); // hmac
		// ���ױ���������:
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
		
		// �ض���:���ױ�����:
		ServletActionContext.getResponse().sendRedirect(sb.toString());
		return NONE;
	}
	
	
	// ����ɹ�����ת������·��:
	public String callBack(){
		// �޸Ķ�����״̬:
		Order currOrder = orderService.findByOid(Integer.parseInt(r6_Order));
		// �޸Ķ���״̬Ϊ2:�Ѿ�����:
		currOrder.setState(2);
		orderService.update(currOrder);
		this.addActionMessage("֧���ɹ�!�������Ϊ: "+r6_Order +" ������Ϊ: "+r3_Amt);
		return "msg";
	}
	
	
	//ǰ̨ȷ���ջ�����
	public String updateState(){
		Order currOrder = orderService.findByOid(order.getOid());
		currOrder.setState(4);
		orderService.update(currOrder);
		return "updateStateSuccess";
	}
	
}
