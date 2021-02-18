package product.model.vo;

import java.sql.Date;

public class Product_Stock {

	private String PRODUCT_ID;
	private String PRODUCT_NAME;
	private int PRICE;
	private String DESCRIPTION;
	private int STOCK;

	public Product_Stock(String pRODUCT_ID, String pRODUCT_NAME, int pRICE, String dESCRIPTION, int sTOCK) {
		super();
		PRODUCT_ID = pRODUCT_ID;
		PRODUCT_NAME = pRODUCT_NAME;
		PRICE = pRICE;
		DESCRIPTION = dESCRIPTION;
		STOCK = sTOCK;
	}

	public Product_Stock() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPRODUCT_ID() {
		return PRODUCT_ID;
	}

	public void setPRODUCT_ID(String pRODUCT_ID) {
		PRODUCT_ID = pRODUCT_ID;
	}

	public String getPRODUCT_NAME() {
		return PRODUCT_NAME;
	}

	public void setPRODUCT_NAME(String pRODUCT_NAME) {
		PRODUCT_NAME = pRODUCT_NAME;
	}

	public int getPRICE() {
		return PRICE;
	}

	public void setPRICE(int pRICE) {
		PRICE = pRICE;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public int getSTOCK() {
		return STOCK;
	}

	public void setSTOCK(int sTOCK) {
		STOCK = sTOCK;
	}

	@Override
	public String toString() {
		return "Product_Stock [PRODUCT_ID=" + PRODUCT_ID + ", PRODUCT_NAME=" + PRODUCT_NAME + ", PRICE=" + PRICE
				+ ", DESCRIPTION=" + DESCRIPTION + ", STOCK=" + STOCK + "]";
	}

}
