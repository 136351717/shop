package cn.itcast.shop.category.adminaction;

import java.util.List;

import cn.itcast.shop.category.service.CategoryService;
import cn.itcast.shop.category.vo.Category;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AdminCategoryAction extends ActionSupport implements ModelDriven<Category> {
	
	private Category category = new Category();

	@Override
	public Category getModel() {
		return category;
	}
	
	private CategoryService categoryService;

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	//后台查询一级分类方法
	public String findAll(){
		List<Category> cList = categoryService.findAll();
		ActionContext.getContext().getValueStack().set("cList", cList);
		return "findAll";
		
	}

	//向数据库中插入一条新的一级分类数据
	public String save(){
		categoryService.save(category);
		return "saveSuccess";
	}
	
	
	//将数据库中的一条一级分类数据删除掉
	public String delete(){
		//级联删除需要先根据id查询出一级分类的对象,然后传入对象调用DAO删除方法才能级联删除二级分类
		category = categoryService.findByCid(category.getCid());
		categoryService.delete(category);
		return "deleteSuccess";
	}
	
	
	//后台修改一级分类数据  以下方法是先查找一级分类
	public String edit(){
		category = categoryService.findByCid(category.getCid());
		return "editSuccess";
	}
	
	//后台修改一级分类数据  以下方法是根据查询到的一级分类真正进行修改操作
	public String update(){
		categoryService.update(category);
		return "updateSuccess";
	}
}
