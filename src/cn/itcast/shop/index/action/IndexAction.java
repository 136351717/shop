package cn.itcast.shop.index.action;

import java.util.List;

import cn.itcast.shop.category.service.CategoryService;
import cn.itcast.shop.category.vo.Category;
import cn.itcast.shop.product.service.ProductService;
import cn.itcast.shop.product.vo.Product;
import cn.itcast.shop.user.service.UserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class IndexAction extends ActionSupport{

	private CategoryService categoryService;
	private ProductService productService;
	
	
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}



	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}






	public String execute(){
		//一级分类
		List<Category> cList = categoryService.findAll();
		ActionContext.getContext().getSession().put("cList", cList);
		//热门商品
		List<Product> hList = productService.findHot();
		ActionContext.getContext().getValueStack().set("hList", hList);
		//最新商品
		List<Product> nList = productService.findNew();
		ActionContext.getContext().getValueStack().set("nList", nList);
		return "index";
	}
}
