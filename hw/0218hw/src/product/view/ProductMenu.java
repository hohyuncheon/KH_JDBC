package product.view;

import java.util.List;
import java.util.Scanner;

import product.controller.ProductController;
import product.model.exception.ProductException;
import product.model.vo.Product_IO;
import product.model.vo.Product_Stock;

public class ProductMenu {
	ProductController productController = new ProductController();
	Scanner sc = new Scanner(System.in);
	String menu = "***** 상품재고관리프로그램 *****\n" 
				+ "1. 전체상품조회\n" 
				+ "2. 상품아이디검색\n" 
				+ "3. 상품명검색\n" 
				+ "4. 상품추가\n"
				+ "5. 상품정보변경\n" 
				+ "6. 상품삭제\n" 
				+ "7. 상품입/출고 메뉴\n" 
				+ "9. 프로그램종료";
	List<Product_Stock> list = null;
	List<Product_IO> listIO = null;
	Product_Stock product = null;
	Product_IO productIO = null;
	private String productId;
	private String productName;
	String msg = null;
	int result = 0;

	public void mainMenu() {
		do {
			System.out.println(menu);
			switch (sc.next()) {
			// 1. 현재 상품 및 재고 현황 보기
			case "1":
				list = productController.selectAll();
				displayProductList(list);
				break;
			// 2. 상품아이디검색
			case "2":
				productId = inputProductId();
				product = productController.selectId(productId);
				displayProduct(product);
				break;

			// 3. 상품명검색
			case "3":
				productName = inputproductName();
				product = productController.selectName(productName);
				displayProduct(product);
				break;

			// 4. 상품추가
			case "4":
				product = inputProduct();
				System.out.println("신규상품 확인" + product);
				result = productController.insertProduct(product);
				msg = result > 0 ? "상품 추가 성공!" : "상품 추가 실패!";
				displayMsg(msg);
				break;
			// 5. 상품정보변경
			case "5":
				updateProductMenu();
				break;
			// 6. 상품삭제
			case "6":
				product = deleteProduct();
				System.out.println("삭제 상품확인 : " + product);
				result = productController.deleteproduct(product);
				// 3. int에 따른 분기처리(성공여부 메세지 호출하겠다는 소리.)
				msg = result > 0 ? "상품 삭제 성공!" : "상품 삭제 실패! 일치하는 상품이 없습니다";
				displayMsg(msg);

				break;
			// 7. 상품입/출고 메뉴
			case "7":
				productInOutMenu();
				break;
			// 9. 프로그램종료
			case "9":
				System.out.println("정말로 종료하시겠습니까?(y/n) : ");
				if (sc.next().charAt(0) == 'y') {
					return;
				}

				break;
			default:
				System.out.println("잘못 입력하셨습니다");
			}

		} while (true);
	}

	private Product_Stock deleteProduct() {
		System.out.println("삭제할 상품아이디를 입력하세요.");
		Product_Stock product = new Product_Stock();
		System.out.print("아이디 : ");
		product.setPRODUCT_ID(sc.next());
		return product;
	}

	private void productInOutMenu() {
		String menu = "***** 상품입출고메뉴*****\n" 
					+ "1. 전체입출고내역조회\n" 
					+ "2. 상품입고\n" 
					+ "3. 상품출고\n" 
					+ "9. 메인메뉴로 돌아가기";

		do {
			System.out.println(menu);
			switch (sc.next()) {
			// 1. 전체입출고내역조회
			case "1":
				listIO = productController.selectAllIO();
				displayProductList2(listIO);

				break;
			// 2. 상품입고
			case "2":
				productIO = inputProductIO();
				System.out.println("입고상품 확인" + productIO);
				result = productController.insertProductIO(productIO);
				msg = result > 0 ? "입고 성공!" : "입고 실패!";
				displayMsg(msg);
				return;

			// 3. 상품출고
			case "3":
				productIO = outputProductIO();
				System.out.println("출고상품 확인" + productIO);

				// 입력한객체의 id부분만 골라냄
				String id = productIO.getPRODUCT_ID();
				product = productController.selectId(id);

				try {
					if (productIO.getAMOUNT() <= product.getSTOCK()) {
						result = productController.releaseProductIO(productIO);
						msg = result > 0 ? "출고 성공!" : "출고 실패!";
						displayMsg(msg);
					} else {
						throw new ProductException("재고가 부족합니다. 출고 수량을 줄이세요.");
					}

				} catch (Exception e) {
					new ProductMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
				}
				return;

			// 9.메인메뉴로 돌아가기
			case "9":
				return;
			default:
				System.out.println("잘못 입력하셨습니다");
			}
		} while (true);

	}

	private Product_IO outputProductIO() {
		System.out.println("출고할 상품을 입력하시오");
		Product_IO productIO = new Product_IO();
		System.out.print("아이디 : ");
		productIO.setPRODUCT_ID(sc.next());
		System.out.print("양 : ");
		productIO.setAMOUNT(sc.nextInt());
		System.out.print("상태 (I/O) : ");
		productIO.setSTATUS(sc.next());
		return productIO;
	}

	private Product_IO inputProductIO() {
		System.out.println("입고할 상품을 입력하시오");
		Product_IO productIO = new Product_IO();
		System.out.print("아이디 : ");
		productIO.setPRODUCT_ID(sc.next());
		System.out.print("양 : ");
		productIO.setAMOUNT(sc.nextInt());
		System.out.print("상태 (I/O) : ");
		productIO.setSTATUS(sc.next());
		return productIO;
	}

	private void displayProductList2(List<Product_IO> list2) {
		if (list2 != null && !list2.isEmpty()) {
			System.out.println(
					"=============================================================================================================================================================");
			for (int i = 0; i < list2.size(); i++) {
				System.out.println((i + 1) + " : " + list2.get(i));
				System.out.println(
						"=============================================================================================================================================================");
			}
		} else {
			System.out.println(">>>>>조회된 상품이 없습니다");
		}
	}

	private void updateProductMenu() {
		String menu = "***** 상품정보변경메뉴 *****\n" 
					+ "1.상품명변경\n" 
					+ "2.가격변경\n" 
					+ "3.설명변경\n" 
					+ "9.메인메뉴로 돌아가기";

		while (true) {
			int result = 0;
			String productId = inputProductId();
			product = productController.selectId(productId);
			if (product == null) {
				System.out.println("해당 상품이 존재하지 않습니다.");
				return;
			}

			displayProduct(product);
			System.out.println(menu);
			String choice = sc.next();
			switch (choice) {
			// 1. 상품명변경
			case "1":
				System.out.println("변경할 상품명 : ");
				product.setPRODUCT_NAME(sc.next());
				result = productController.updateProductName(product);
				displayMsg(result > 0 ? "정보 수정 성공!" : "정보 수정 실패!");
				break;
			// 2. 가격변경
			case "2":
				System.out.println("변경할 가격 : ");
				product.setPRICE(sc.nextInt());
				result = productController.updateProductPrice(product);
				displayMsg(result > 0 ? "정보 수정 성공!" : "정보 수정 실패!");
				break;

			// 3. 설명변경
			case "3":
				System.out.println("변경할 설명 : ");
				product.setDESCRIPTION(sc.next());
				result = productController.updateProductDescription(product);
				displayMsg(result > 0 ? "정보 수정 성공!" : "정보 수정 실패!");
				break;

			// 9.메인메뉴로 돌아가기
			case "9":
				return;
			default:
				System.out.println("잘못 입력하셨습니다");
				continue;
			}
			return;
		}
	}

	private void displayMsg(String msg) {
		System.out.println(">>> 처리결과 : " + msg);
	}

	// 신규상품추가
	private Product_Stock inputProduct() {
		System.out.println("새로운 상품정보를 입력하세요.");
		Product_Stock product = new Product_Stock();

		System.out.print("아이디 : ");
		product.setPRODUCT_ID(sc.next());
		System.out.print("상품명 : ");
		product.setPRODUCT_NAME(sc.next());
		System.out.print("가격 : ");
		product.setPRICE(sc.nextInt());
		System.out.print("설명 : ");
		product.setDESCRIPTION(sc.next());
		System.out.print("재고 : ");
		product.setSTOCK(sc.nextInt());
		return product;
	}

	// 검색할 이름 받기
	private String inputproductName() {
		System.out.println("검색할 이름 : ");
		return sc.next();
	}

	// 검색한 맴버 출력
	private void displayProduct(Product_Stock product) {
		if (product == null) {
			System.out.println(">>>>조회된 아이디가 없습니다");

		} else {
			System.out.println("************************************************");
			System.out.println(product);
			System.out.println("************************************************");
		}
	}

	// 검색할 아이디 입력받기
	private String inputProductId() {
		System.out.println("검색할 아이디 : ");
		return sc.next();
	}

	// 총
	private void displayProductList(List<Product_Stock> list) {
		if (list != null && !list.isEmpty()) {
			System.out.println(
					"=============================================================================================================================================================");
			for (int i = 0; i < list.size(); i++) {
				System.out.println((i + 1) + " : " + list.get(i));
				System.out.println(
						"=============================================================================================================================================================");
			}
		} else {
			System.out.println(">>>>>조회된 상품이 없습니다");
		}
	}

	public void displayError(String errorMsg) {
		System.err.println(errorMsg);
	}
}
