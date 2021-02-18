package product.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import product.model.exception.ProductException;
import product.model.vo.Product_IO;
import product.model.vo.Product_Stock;

public class ProductDao {

	private Properties prop = new Properties();

	public ProductDao() {
		String fileName = "resources/product-qurey.properties";
		try {
			prop.load(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//상품전체조회
	public List<Product_Stock> selectAll(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAll");
		List<Product_Stock> list = null;

		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			list = new ArrayList<>();
			while (rset.next()) {
				String PRODUCT_ID = rset.getString("PRODUCT_ID");
				String PRODUCT_NAME = rset.getString("PRODUCT_NAME");
				int PRICE = rset.getInt("PRICE");
				String DESCRIPTION = rset.getString("DESCRIPTION");
				int STOCK = rset.getInt("STOCK");
				Product_Stock product = new Product_Stock(PRODUCT_ID, PRODUCT_NAME, PRICE, DESCRIPTION, STOCK);
				list.add(product);
			}

		} catch (SQLException e) {
			throw new ProductException("상품 전체 조회", e);
		} finally {
			close(rset);
			close(pstmt);
		}

		return list;
	}

	//상품아이디 검색
	public Product_Stock selectId(Connection conn, String productId) {
		Product_Stock product = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectId");
		String PRODUCT_ID = "";
		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, productId);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				PRODUCT_ID = rset.getString("PRODUCT_ID");
				String PRODUCT_NAME = rset.getString("PRODUCT_NAME");
				int PRICE = rset.getInt("PRICE");
				String DESCRIPTION = rset.getString("DESCRIPTION");
				int STOCK = rset.getInt("STOCK");
				product = new Product_Stock(PRODUCT_ID, PRODUCT_NAME, PRICE, DESCRIPTION, STOCK);
			}

		} catch (SQLException e) {
			throw new ProductException("상품아이디 검색", e);
		} finally {
			close(rset);
			close(pstmt);
		}

		return product;
	}
	
	//상품명 검색
	public Product_Stock selectName(Connection conn, String productName) {
		Product_Stock product = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectName");
		String PRODUCT_ID = "";
		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, "%" + productName + "%");
			rset = pstmt.executeQuery();

			while (rset.next()) {
				PRODUCT_ID = rset.getString("PRODUCT_ID");
				String PRODUCT_NAME = rset.getString("PRODUCT_NAME");
				int PRICE = rset.getInt("PRICE");
				String DESCRIPTION = rset.getString("DESCRIPTION");
				int STOCK = rset.getInt("STOCK");
				product = new Product_Stock(PRODUCT_ID, PRODUCT_NAME, PRICE, DESCRIPTION, STOCK);
			}

		} catch (SQLException e) {
			throw new ProductException("상품명 검색", e);
		} finally {
			close(rset);
			close(pstmt);
		}

		return product;
	}

	//상품추가
	public int insertProduct(Connection conn, Product_Stock product) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertProduct");

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, product.getPRODUCT_ID());
			pstmt.setString(2, product.getPRODUCT_NAME());
			pstmt.setInt(3, product.getPRICE());
			pstmt.setString(4, product.getDESCRIPTION());
			pstmt.setInt(5, product.getSTOCK());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new ProductException("상품추가", e);
		}
		return result;
	}

	//상품삭제
	public int deleteMember(Connection conn, Product_Stock product) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("deleteProduct");
		int result = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, product.getPRODUCT_ID());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new ProductException("상품삭제", e);
		}

		return result;
	}

	//상품명변경
	public int updateProductName(Connection conn, Product_Stock product) {
		int result = 0;
		PreparedStatement pstmt = null;

		String query = prop.getProperty("updateProductName");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, product.getPRODUCT_NAME());
			pstmt.setString(2, product.getPRODUCT_ID());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			throw new ProductException("상품명 변경", e);
		} finally {
			close(pstmt);
		}

		return result;
	}

	//상품가격변경
	public int updateProductPrice(Connection conn, Product_Stock product) {
		int result = 0;
		PreparedStatement pstmt = null;

		String query = prop.getProperty("updateProductPrice");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, product.getPRICE());
			pstmt.setString(2, product.getPRODUCT_ID());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			throw new ProductException("상품가격변경", e);
		} finally {
			close(pstmt);
		}

		return result;
	}

	//상품설명변경
	public int updateProductDescription(Connection conn, Product_Stock product) {
		int result = 0;
		PreparedStatement pstmt = null;

		String query = prop.getProperty("updateProductDescription");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, product.getDESCRIPTION());
			pstmt.setString(2, product.getPRODUCT_ID());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			throw new ProductException("상품설명 변경", e);
		} finally {
			close(pstmt);
		}

		return result;
	}

	//입출고메뉴 전체입출고내역조회
	public List<Product_IO> selectAllIO(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAllIO");
		List<Product_IO> list2 = null;

		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			list2 = new ArrayList<>();
			while (rset.next()) {
				int IO_NO = rset.getInt("IO_NO");
				String PRODUCT_ID = rset.getString("PRODUCT_ID");
				Date IODATE = rset.getDate("IODATE");
				int AMOUNT = rset.getInt("AMOUNT");
				String STATUS = rset.getString("STATUS");
				Product_IO product = new Product_IO(IO_NO, PRODUCT_ID, IODATE, AMOUNT, STATUS);
				list2.add(product);
			}

		} catch (SQLException e) {
			throw new ProductException("전체입출고내역조회", e);
		} finally {
			close(rset);
			close(pstmt);
		}

		return list2;
	}

	//상품 입출고 상품입고
	public int insertProductIO(Connection conn, Product_IO productIO) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertProductIO");

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productIO.getPRODUCT_ID());
			pstmt.setInt(2, productIO.getAMOUNT());
			pstmt.setString(3, productIO.getSTATUS());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new ProductException("상품입고", e);
		}
		return result;
	}

	//상품 입출고  상품출고
	public int releaseProductIO(Connection conn, Product_IO productIO) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertProductIO");

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productIO.getPRODUCT_ID());
			pstmt.setInt(2, productIO.getAMOUNT());
			pstmt.setString(3, productIO.getSTATUS());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new ProductException("상품출고", e);
		}
		return result;
	}
}