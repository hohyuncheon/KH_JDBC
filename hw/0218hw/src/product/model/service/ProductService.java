package product.model.service;

import java.sql.Connection;
import java.util.List;

import common.JDBCTemplate;
import product.model.dao.ProductDao;
import product.model.vo.Product_IO;
import product.model.vo.Product_Stock;
import static common.JDBCTemplate.*;

/*
 * service 클래스 
 * 연결처리 + 예외처리 간소화
 */
public class ProductService {

	ProductDao productDao = new ProductDao();

	public List<Product_Stock> selectAll() {
		Connection conn = getConnection();
		List<Product_Stock> list = productDao.selectAll(conn);
		close(conn);
		return list;
	}

	public Product_Stock selectId(String productId) {
		Connection conn = getConnection();
		Product_Stock product = productDao.selectId(conn, productId);
		close(conn);
		return product;
	}

	public Product_Stock selectName(String productName) {
		Connection conn = getConnection();
		Product_Stock product = productDao.selectName(conn, productName);
		close(conn);
		return product;
	}

	public int insertProduct(Product_Stock product) {
		Connection conn = getConnection();
		int result = productDao.insertProduct(conn, product);
		if (result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

	public int deleteMember(Product_Stock product) {
		Connection conn = getConnection();
		int result = productDao.deleteMember(conn, product);
		if (result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

	public int updateProductName(Product_Stock product) {
		Connection conn = getConnection();
		int result = productDao.updateProductName(conn, product);
		if (result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

	public int updateProductPrice(Product_Stock product) {
		Connection conn = getConnection();
		int result = productDao.updateProductPrice(conn, product);
		if (result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

	public int updateProductDescription(Product_Stock product) {
		Connection conn = getConnection();
		int result = productDao.updateProductDescription(conn, product);
		if (result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

	public List<Product_IO> selectAllIO() {
		Connection conn = getConnection();
		List<Product_IO> list2 = productDao.selectAllIO(conn);
		close(conn);
		return list2;
	}

	public int insertProductIO(Product_IO productIO) {
		Connection conn = getConnection();
		int result = productDao.insertProductIO(conn, productIO);
		if (result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

	public int releaseProductIO(Product_IO productIO) {
		Connection conn = getConnection();
		int result = productDao.releaseProductIO(conn, productIO);
		if (result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

}
