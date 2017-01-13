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
	
	//��̨��ѯһ�����෽��
	public String findAll(){
		List<Category> cList = categoryService.findAll();
		ActionContext.getContext().getValueStack().set("cList", cList);
		return "findAll";
		
	}

	//�����ݿ��в���һ���µ�һ����������
	public String save(){
		categoryService.save(category);
		return "saveSuccess";
	}
	
	
	//�����ݿ��е�һ��һ����������ɾ����
	public String delete(){
		//����ɾ����Ҫ�ȸ���id��ѯ��һ������Ķ���,Ȼ����������DAOɾ���������ܼ���ɾ����������
		category = categoryService.findByCid(category.getCid());
		categoryService.delete(category);
		return "deleteSuccess";
	}
	
	
	//��̨�޸�һ����������  ���·������Ȳ���һ������
	public String edit(){
		category = categoryService.findByCid(category.getCid());
		return "editSuccess";
	}
	
	//��̨�޸�һ����������  ���·����Ǹ��ݲ�ѯ����һ���������������޸Ĳ���
	public String update(){
		categoryService.update(category);
		return "updateSuccess";
	}
}
