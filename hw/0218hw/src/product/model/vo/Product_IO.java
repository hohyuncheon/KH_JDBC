
package product.model.vo;

import java.sql.Date;

public class Product_IO {

	private int IO_NO;
	private String PRODUCT_ID;
	private Date IODATE;
	private int AMOUNT;
	private String STATUS;

	public Product_IO() {
		super();
	}

	public Product_IO(int iO_NO, String pRODUCT_ID, Date iODATE, int aMOUNT, String sTATUS) {
		super();
		IO_NO = iO_NO;
		PRODUCT_ID = pRODUCT_ID;
		IODATE = iODATE;
		AMOUNT = aMOUNT;
		STATUS = sTATUS;
	}

	public int getIO_NO() {
		return IO_NO;
	}

	public void setIO_NO(int iO_NO) {
		IO_NO = iO_NO;
	}

	public String getPRODUCT_ID() {
		return PRODUCT_ID;
	}

	public void setPRODUCT_ID(String pRODUCT_ID) {
		PRODUCT_ID = pRODUCT_ID;
	}

	public Date getIODATE() {
		return IODATE;
	}

	public void setIODATE(Date iODATE) {
		IODATE = iODATE;
	}

	public int getAMOUNT() {
		return AMOUNT;
	}

	public void setAMOUNT(int aMOUNT) {
		AMOUNT = aMOUNT;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	@Override
	public String toString() {
		return "Product [IO_NO=" + IO_NO + ", PRODUCT_ID=" + PRODUCT_ID + ", IODATE=" + IODATE + ", AMOUNT=" + AMOUNT
				+ ", STATUS=" + STATUS + "]";
	}

}
