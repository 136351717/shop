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
	
	//����ҳ�Ĳ������ж�������ķ���
	public String findAll(){
		// ����Service���в�ѯ.
		PageBean<CategorySecond> pageBean = categorySecondService
				.findByPage(page);
		// ��pageBean�����ݴ��뵽ֵջ��.
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}

	//�����������в���һ���µĶ�����������,��ʱ��Ҫ������е�һ������Ȼ��������ҳ���������б����ʽ��ʾ���û�ѡ��,���·�������ת�����ҳ��
	public String addPage(){
		List<Category> cList = categoryService.findAll();
		ActionContext.getContext().getValueStack().set("cList", cList);
		return "addPageSuccess";
	}
	
	//�����������������������в���һ���µ�����
	public String save(){
		categorySecondService.save(categorySecond);
		return "saveSuccess";
	}
	
	//ɾ�������б��е�һ������
	public String delete(){
		categorySecond = categorySecondService.findByCsid(categorySecond.getCsid());
		categorySecondService.delete(categorySecond);
		return "deleteSuccess";
	}
	
	
	//��ת��������ı༭ҳ��
	public String edit(){
		categorySecond = categorySecondService.findByCsid(categorySecond.getCsid());
		List<Category> cList = categoryService.findAll();
		ActionContext.getContext().getValueStack().set("cList", cList);
		return "editSuccess";
	}
	
	//�༭�������������
	public String update(){
		categorySecondService.update(categorySecond);
		return "updateSuccess";
	}
	
}
