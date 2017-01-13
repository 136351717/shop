package cn.itcast.shop.categorysecond.adminaction;

import java.util.List;

import cn.itcast.shop.category.service.CategoryService;
import cn.itcast.shop.category.vo.Category;
import cn.itcast.shop.categorysecond.service.CategorySecondService;
import cn.itcast.shop.categorysecond.vo.CategorySecond;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AdminCategorySecondAction extends ActionSupport implements ModelDriven<CategorySecond> {
	
	private CategorySecond categorySecond = new CategorySecond();
	private CategoryService categoryService;
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Override
	public CategorySecond getModel() {
		return categorySecond;
	}
	
	private Integer page;
	
	public void setPage(Integer page) {
		this.page = page;
	}
	
	private CategorySecondService categorySecondService;

	public void setCategorySecondService(CategorySecondService categorySecondService) {
		this.categorySecondService = categorySecondService;
	}
	
	//带分页的查找所有二级分类的方法
	public String findAll(){
		// 调用Service进行查询.
		PageBean<CategorySecond> pageBean = categorySecondService
				.findByPage(page);
		// 将pageBean的数据存入到值栈中.
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}

	//向二级分类表中插入一条新的二级分类数据,此时需要查出所有的一级分类然后用于在页面以下拉列表的形式显示供用户选择,以下方法是跳转到添加页面
	public String addPage(){
		List<Category> cList = categoryService.findAll();
		ActionContext.getContext().getValueStack().set("cList", cList);
		return "addPageSuccess";
	}
	
	//以下是真正的向二级分类表中插入一条新的数据
	public String save(){
		categorySecondService.save(categorySecond);
		return "saveSuccess";
	}
	
	//删除二级列表中的一条数据
	public String delete(){
		categorySecond = categorySecondService.findByCsid(categorySecond.getCsid());
		categorySecondService.delete(categorySecond);
		return "deleteSuccess";
	}
	
	
	//跳转二级分类的编辑页面
	public String edit(){
		categorySecond = categorySecondService.findByCsid(categorySecond.getCsid());
		List<Category> cList = categoryService.findAll();
		ActionContext.getContext().getValueStack().set("cList", cList);
		return "editSuccess";
	}
	
	//编辑二级分类的数据
	public String update(){
		categorySecondService.update(categorySecond);
		return "updateSuccess";
	}
	
}
