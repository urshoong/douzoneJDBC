package com.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.controller.ProductController;
import com.model.vo.ProductIO;
import com.model.vo.ProductStock;

public class ProductMenu {
	private static Scanner sc = new Scanner(System.in);
	private ProductController pc = new ProductController();
	
	public void MainMenu() {
		do {
			System.out.println("1. 전체 조회");
			System.out.println("2. 상품 추가");
			System.out.println("3. 상품 수정 : 상품 id로 조회하고 수정");
			System.out.println("4. 상품 삭제 : 상품 id로 조회하고 삭제");
			System.out.println("5. 상품 검색 : 상품 이름으로 조회");
			System.out.println("6. 상품 입출고 메뉴");
			System.out.println("0. 프로그램 종료");
			System.out.print("입력 : ");
			int menu = sc.nextInt();
			sc.nextLine();//버퍼 비우기
			
			switch(menu) {
				case 1:
					pc.selectAll();
					break;
				case 2:
					pc.insertProduct(insertProduct());
					break;
				case 3:
					pc.updateProduct(updateProduct());
					break;
				case 4:
					pc.deleteProduct(deleteProduct());
					break;
				case 5:
					pc.searchProduct(searchProduct());
					break;
				case 6:
					productIOMenu();
					break;
				case 0:
					System.out.println("프로그램 종료");
					pc.exitProgram();
					return;
				default:
					System.out.println("메뉴를 확인해주세요.");
					break;
			}
			
		}while(true);
	}
	
	public ProductStock insertProduct() {
		ProductStock product = new ProductStock();
		
		System.out.println("====== 물품 등록 ======");
		System.out.print("상품ID : ");
		String productId = sc.nextLine();
		System.out.print("상품명 : ");
		String pName = sc.nextLine();
		System.out.print("가격 : ");
		int price = sc.nextInt();
		sc.nextLine();
		System.out.print("재고 : ");
		int stock = sc.nextInt();
		sc.nextLine();
		System.out.print("비고: ");
		String description = sc.nextLine();
		
		product.setProductId(productId);
		product.setpName(pName);
		product.setPrice(price);
		product.setStock(stock);
		product.setDescription(description);
		
		return product;
	}
	
	public ProductStock updateProduct() {
		ProductStock product = new ProductStock();
		System.out.println("====== 상품 수정 =======");
		System.out.print("상품ID : ");
		String productId = sc.nextLine();
		System.out.print("상품명 : ");
		String pName = sc.nextLine();
		System.out.print("가격 : ");
		int price = sc.nextInt();
		sc.nextLine();
		System.out.print("재고 : ");
		int stock = sc.nextInt();
		sc.nextLine();
		System.out.print("비고: ");
		String description = sc.nextLine();
		
		product.setProductId(productId);
		product.setpName(pName);
		product.setPrice(price);
		product.setStock(stock);
		product.setDescription(description);
		
		return product;
	}
	
	public String deleteProduct() {
		System.out.println("====== 상품 삭제 ======");
		System.out.print("상품ID : ");
		String input = sc.nextLine();
		return input;
	}
	
	public String searchProduct() {
		System.out.println("===== 상품 검색 ======");
		System.out.print("상품명 : ");
		String search = sc.nextLine();
		return search;
	}
	
	public void productIOMenu() {
		do {
			System.out.println("1. 전체 입출고 내역 조회");
			System.out.println("2. 입고 내역만 조회");
			System.out.println("3. 출고 내역만 조회");
			System.out.println("4. 상품 입고");
			System.out.println("5. 상품 출고");
			System.out.println("0. 이전 화면");
			System.out.print("입력 : ");
			int menu = sc.nextInt();
			sc.nextLine();//버퍼 비우기
			
			switch(menu) {
				case 1:
					pc.selectAllIO("ALL");
					break;
				case 2:
					pc.selectAllIO("IN");
					break;
				case 3:
					pc.selectAllIO("OUT");
					break;
				case 4:
					pc.insertProductIO(increaseProductIO());
					break;
				case 5:
					pc.insertProductIO(decreaseProductIO());
					break;
				case 0:
					return;
				default:
					System.out.println("메뉴를 확인해주세요.");
					break;
			}
		}while(true);
	}
	
	public ProductIO increaseProductIO() {
		return inputIOValues("입고");
	}
	
	public ProductIO decreaseProductIO() {
		return inputIOValues("출고");
	}
	
	public ProductIO inputIOValues(String gubun) {
		ProductIO io = new ProductIO();
		System.out.println("====== " + gubun + " 등록 ======");
		System.out.print("제품ID : ");
		String productId = sc.nextLine();
		System.out.print("수량 : ");
		int amount = sc.nextInt();
		sc.nextLine();
		
		io.setProductId(productId);
		io.setAmount(amount);
		io.setStatus(gubun);
		
		return io;
	}
	
	public void printProductStockList(ArrayList<ProductStock> list) {
		//물품 목록 출력
		System.out.println("\n조회된 전체 회원정보는 다음과 같습니다.");
		System.out.println("\n상품ID\t상품명\t가격\t재고\t비고");
		for(ProductStock stock : list) {
			System.out.println(stock);
		}
	}
	
	public void printProductStock(ProductStock product) {
		//물품 단일 출력
		System.out.println("\n조회된 전체 회원정보는 다음과 같습니다.");
		System.out.println("\n상품ID\t상품명\t가격\t재고\t비고");
		System.out.println(product);
	}
	
	public void printProductIOList(ArrayList<ProductIO> list) {
		//입출고 내역 출력
		System.out.println("\n조회된 전체 회원정보는 다음과 같습니다.");
		System.out.println("\n순번\t상품ID\t등록일자\t수량\t상태");
		for(ProductIO io : list) {
			System.out.println(io);
		}
	}
	
	public void displayError(String message) {
		System.out.println("서비스 요청 처리 실패 : " + message);
	}
	
	public void displaySuccess(String message) {
	   System.out.println("서비스 요청 결과 : "+message)  ;
	}
	
}
