package product.controller;

import java.util.List;

import product.model.exception.ProductException;
import product.model.service.ProductService;
import product.model.vo.Product_IO;
import product.model.vo.Product_Stock;
import product.view.ProductMenu;

public class ProductController {

	ProductService productService = new ProductService();

	public List<Product_Stock> selectAll() {
		List<Product_Stock> list = null;

		try {
			list = productService.selectAll();
		} catch (ProductException e) {
			new ProductMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		return list;
	}

	public Product_Stock selectId(String productId) {
		Product_Stock product = null;
		try {
			product = productService.selectId(productId);
		} catch (ProductException e) {
			new ProductMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		return product;
	}

	public Product_Stock selectName(String productName) {
		Product_Stock product = null;
		try {
			product = productService.selectName(productName);
		} catch (ProductException e) {
			new ProductMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		return product;
	}

	public int insertProduct(Product_Stock product) {
		int result = 0;

		try {
			result = productService.insertProduct(product);

		} catch (ProductException e) {
			new ProductMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}

		return result;
	}

	public int deleteproduct(Product_Stock product) {
		int result = 0;
		try {
			result = productService.deleteMember(product);
		} catch (ProductException e) {
			new ProductMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		return result;
	}

	public int updateProductName(Product_Stock product) {
		int result = 0;
		try {
			result = productService.updateProductName(product);
		} catch (ProductException e) {
			new ProductMenu().displayError(e.getMessage());
		}

		return result;
	}

	public int updateProductPrice(Product_Stock product) {
		int result = 0;
		try {
			result = productService.updateProductPrice(product);
		} catch (ProductException e) {
			new ProductMenu().displayError(e.getMessage());
		}

		return result;
	}

	public int updateProductDescription(Product_Stock product) {
		int result = 0;
		try {
			result = productService.updateProductDescription(product);
		} catch (ProductException e) {
			new ProductMenu().displayError(e.getMessage());
		}

		return result;
	}

	// 재고전체조회
	public List<Product_IO> selectAllIO() {
		List<Product_IO> list2 = null;

		try {
			list2 = productService.selectAllIO();
		} catch (ProductException e) {
			new ProductMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		return list2;
	}

	// 입고
	public int insertProductIO(Product_IO productIO) {
		int result = 0;
		try {
			result = productService.insertProductIO(productIO);
		} catch (ProductException e) {
			new ProductMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		return result;
	}

	// 출고
	public int releaseProductIO(Product_IO productIO) {
		int result = 0;
		try {
			result = productService.releaseProductIO(productIO);
		} catch (ProductException e) {
			new ProductMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		return result;
	}
}
