package com.service;

import com.model.dao.ProductDAO;
import com.model.vo.ProductIO;
import com.model.vo.ProductStock;

import static com.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

public class ProductService {
	private ProductDAO dao = new ProductDAO();
	
	public void exitProgram() {
		close(getConnection());
	}

	public ArrayList<ProductStock> selectAll() {
		Connection conn = getConnection();
		ArrayList<ProductStock> list = dao.selectAll(conn);
		return list;
	}

	public int insertProduct(ProductStock product) {
		Connection conn = getConnection();
		int result = dao.insertProduct(conn, product);
		if(result == 1)	commit(conn);
		else			rollback(conn);
		return result;
	}

	public int updateProduct(ProductStock product) {
		Connection conn = getConnection();
		int result = dao.updateProduct(conn, product);
		if(result == 1)	commit(conn);
		else			rollback(conn);
		return result;
	}

	public int deleteProduct(String productId) {
		Connection conn = getConnection();
		int result = dao.deleteProduct(conn, productId);
		if(result == 1)	commit(conn);
		else			rollback(conn);
		return result;
	}

	public ArrayList<ProductStock> searchProduct(String search) {
		Connection conn = getConnection();
		ArrayList<ProductStock> product = dao.searchProduct(conn, search);
		return product;
	}

	public ArrayList<ProductIO> selectAllIO(String gubun) {
		Connection conn = getConnection();
		ArrayList<ProductIO> ios = dao.selectAllIO(conn, gubun);
		return ios;
	}

	public int insertProductIO(ProductIO productIO) {
		Connection conn = getConnection();
		int result = dao.insertProductIO(conn, productIO);
		if(result == 1) {
			commit(conn);
		}else {
			rollback(conn);
		}
		return result;
	}
	
}
