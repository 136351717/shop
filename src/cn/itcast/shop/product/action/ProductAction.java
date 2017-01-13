package cn.itcast.shop.product.action;

import cn.itcast.shop.product.service.ProductService;
import cn.itcast.shop.product.vo.Product;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ProductAction extends ActionSupport implements ModelDriven<Product>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Product product = new Product();
	private ProductService productService; 
	private int page;
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}


	private Integer cid;
	private Integer csid;



	public Integer getCsid() {
		return csid;
	}

	public void setCsid(Integer csid) {
		this.csid = csid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@Override
	public Product getModel() {
		return product;
	}
	
	public String findByPid(){
		product = productService.findByPid(product.getPid());
		return "findByPid";
	}
	
	
	public String findByCid(){
		// List<Category> cList = categoryService.findAll();
		PageBean<Product> pageBean = productService.findByPageCid(cid, page);// 根据一级分类查询商品,带分页查询
		// 将PageBean存入到值栈中:
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByCid";
	}
	
	public String findByCsid(){
		PageBean<Product> pageBean = productService.findByPageCsid(csid,page);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByCsid";
	}
	
}
