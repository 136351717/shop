package cn.itcast.shop.product.adminaction;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.aspectj.util.FileUtil;

import cn.itcast.shop.categorysecond.service.CategorySecondService;
import cn.itcast.shop.categorysecond.vo.CategorySecond;
import cn.itcast.shop.product.service.ProductService;
import cn.itcast.shop.product.vo.Product;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AdminProductAction extends ActionSupport implements ModelDriven<Product> {
	
	private Product product = new Product();
	private Integer page;
	private CategorySecondService categorySecondService;
	
	//�����ļ�����
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	
	
	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	

	public void setCategorySecondService(CategorySecondService categorySecondService) {
		this.categorySecondService = categorySecondService;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	@Override
	public Product getModel() {
		return product;
	}
	
	private ProductService productService;

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	
	//�з�ҳ�Ĳ�ѯ������Ʒ�ķ���
	public String findAll(){
		PageBean<Product> pageBean = productService.findByPage(page);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}
	
	//�����Ʒ֮ǰ��׼��,(��ѯ�����еĶ�������)
	public String addPage(){
		List<CategorySecond> csList = categorySecondService.findAll();
		ActionContext.getContext().getValueStack().set("csList", csList);
		return "addPageSuccess";
	}
	
	//�����Ʒ�ķ���
	public String save() throws IOException{
		product.setPdate(new Date());
		//ͼƬ
		if(upload!=null){
			String realPath = ServletActionContext.getServletContext().getRealPath("/products");
			File diskFile = new File(realPath+"//"+uploadFileName);
			FileUtils.copyFile(upload, diskFile);
			product.setImage("products/"+uploadFileName);
		}
		productService.save(product);
		return "saveSuccess";
	}
	
	
	//ɾ����Ʒ
	public String delete(){
		//�Ȳ�ѯ
		product = productService.findByPid(product.getPid());
		//ɾ��ͼƬ
		String path = product.getImage();
		if(path != null){
			String realPath = ServletActionContext.getServletContext().getRealPath("/"+path);
			File file = new File(realPath);
			file.delete();
		}
		productService.delete(product);
		return "deleteSuccess";
	}
	
	
	//��ת�༭ҳ��
	public String edit(){
		product = productService.findByPid(product.getPid());
		List<CategorySecond> csList = categorySecondService.findAll();
		ActionContext.getContext().getValueStack().set("csList", csList);
		return "editSuccess";
	}
	
	
	//�����ı༭��Ʒ�ķ���
	public String update() throws IOException{
		product.setPdate(new Date());
		//ɾ���Ѿ����ڵ�ͼƬ
		if(upload!=null){
			String path = product.getImage();
			System.out.println(path);
			File file = new File(ServletActionContext.getServletContext().getRealPath("/"+path));
			file.delete();
			
			//�ϴ��µ�ͼƬ
			String realPath = ServletActionContext.getServletContext().getRealPath("/products");
			File diskFile = new File(realPath+"//"+uploadFileName);
			FileUtils.copyFile(upload, diskFile);
			product.setImage("products/"+uploadFileName);
		}
		productService.update(product);
		return "updateSuccess";
	}
}
