package com.controller;

import java.util.ArrayList;

import com.model.vo.ProductIO;
import com.model.vo.ProductStock;
import com.service.ProductService;
import com.view.ProductMenu;

public class ProductController {
	private ProductService ps = new ProductService();

	public void selectAll() {
		ProductMenu pm = new ProductMenu();
		ArrayList<ProductStock> list = ps.selectAll();
		if(list.size() > 0) {
			pm.printProductStockList(list);
		}else {
			pm.displayError("조회된 결과가 없습니다.");
		}
	}

	public void insertProduct(ProductStock insertProduct) {
		ProductMenu pm = new ProductMenu();
		int result = ps.insertProduct(insertProduct);
		if(result == 1) {
			pm.displaySuccess("물품이 등록되었습니다.");
		}else if(result == 9){
			pm.displayError("이미 등록된 물품입니다.");
		}else {
			pm.displayError("등록에 실패했습니다.");
		}
	}

	public void updateProduct(ProductStock productStock) {
		ProductMenu pm = new ProductMenu();
		int result = ps.updateProduct(productStock);
		if(result == 1) {
			pm.displaySuccess("성공적으로 수정되었습니다.");
		}else if(result == 9){
			pm.displayError("등록되지 않은 물품입니다.");
		}else {
			pm.displayError("수정에 실패했습니다");
		}
		
	}

	public void deleteProduct(String productId) {
		ProductMenu pm = new ProductMenu();
		int result = ps.deleteProduct(productId);
		if(result == 1) {
			pm.displaySuccess("성공적으로 삭제되었습니다.");
		}else if(result == 9){
			pm.displayError("등록되지 않은 물품입니다.");
		}else {
			pm.displayError("삭제에 실패했습니다.");
		}
	}

	public void searchProduct(String search) {
		ProductMenu pm = new ProductMenu();
		ArrayList<ProductStock> product = ps.searchProduct(search);
		if(product.size() > 0) {
			pm.printProductStockList(product);
		}else {
			pm.displayError ("등록되지않은 물품입니다.");
		}
	}

	public void selectAllIO(String gubun) {
		ProductMenu pm = new ProductMenu();
		ArrayList<ProductIO> list = ps.selectAllIO(gubun);
		if(list.size() > 0) {
			pm.printProductIOList(list);
		}else {
			pm.displayError("조회된 결과가 없습니다.");
		}
	}
	
	public void insertProductIO(ProductIO productIO) {
		ProductMenu pm = new ProductMenu();
		int result = 0;
		if(productIO.getStatus().equals("입고") || productIO.getStatus().equals("출고")) {
			result = ps.insertProductIO(productIO);
		}else {
			result = 0;
		}
		
		if(result == 1) {
			pm.displaySuccess("상품이 " + productIO.getStatus()  + "되었습니다.");
		}else if(result == 9){
			pm.displayError("등록되지 않은 상품입니다.");
		}else if(result == 8){
			pm.displayError("재고수량보다 출고수량이 많습니다.");
		}else {
			pm.displayError("상품 " + productIO.getStatus() + "에 실패했습니다.");
		}
	}

	public void exitProgram() {
		ps.exitProgram();
	}

}
