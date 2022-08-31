package com.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.model.vo.ProductIO;
import com.model.vo.ProductStock;

import static com.common.JDBCTemplate.close;

public class ProductDAO {
	private Properties prop = null;
	
	public ProductDAO() {
		prop = new Properties();
		try {
			prop.load(new FileReader("resources\\query.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<ProductStock> selectAll(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<ProductStock> list = new ArrayList<ProductStock>();
		
		String sql = prop.getProperty("selectAll");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ProductStock p = new ProductStock();
				p.setProductId(rs.getString("PRODUCT_ID"));
				p.setpName(rs.getString("P_NAME"));
				p.setPrice(rs.getInt("PRICE"));
				p.setDescription(rs.getString("DESCRIPTION"));
				p.setStock(rs.getInt("STOCK"));
				list.add(p);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return list;
	}

	public int insertProduct(Connection conn, ProductStock product) {
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		int result = -1;
		
		String sql = prop.getProperty("selectOne");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, product.getProductId());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = 9;
			}else {
				String sql2 = prop.getProperty("insertProduct");
				
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, product.getProductId());
				pstmt2.setString(2, product.getpName());
				pstmt2.setInt(3, product.getPrice());
				pstmt2.setString(4, product.getDescription());
				pstmt2.setInt(5, product.getStock());
				
				result = pstmt2.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
			close(pstmt2);
		}
		
		return result;
	}

	public int updateProduct(Connection conn, ProductStock product) {
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		int result = 0;
		
		String sql = prop.getProperty("selectOne");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, product.getProductId());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String sql2 = prop.getProperty("updateProduct");
				
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, product.getpName());
				pstmt2.setInt(2, product.getPrice());
				pstmt2.setString(3, product.getDescription());
				pstmt2.setInt(4, product.getStock());
				pstmt2.setString(5, product.getProductId());
				
				result = pstmt2.executeUpdate();
			}else {
				result = 9;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(pstmt2);
		}
		
		return result;
	}

	public int deleteProduct(Connection conn, String productId) {
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		
		int result = 0;
		
		String sql = prop.getProperty("selectOne");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String sql2 = prop.getProperty("deleteProduct");
				
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, productId); 
				
				result = pstmt2.executeUpdate();
			}else {
				result = 9;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(pstmt2);
		}
		
		return result;
	}

	public ArrayList<ProductStock> searchProduct(Connection conn, String search) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<ProductStock> list = new ArrayList<ProductStock>();
		
		String sql = prop.getProperty("searchProduct");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+search+"%");
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ProductStock product = new ProductStock();
				product.setProductId(rs.getString("PRODUCT_ID"));
				product.setpName(rs.getString("P_NAME"));
				product.setPrice(rs.getInt("PRICE"));
				product.setDescription(rs.getString("DESCRIPTION"));
				product.setStock(rs.getInt("STOCK"));
				list.add(product);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
		}
		
		return list;
	}

	public ArrayList<ProductIO> selectAllIO(Connection conn, String gubun) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<ProductIO> list = new ArrayList<ProductIO>();
		
		String property = "";
		switch(gubun) {
			case "ALL":
				property = "selectAllIO";
				break;
			case "IN":
				property = "selectAllIn";
				break;
			case "OUT":
				property = "selectAllOut";
				break;
		}
		String sql = prop.getProperty(property);
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ProductIO io = new ProductIO();
				io.setIoNum(rs.getInt("IO_NUM"));
				io.setProductId(rs.getString("PRODUCT_ID"));
				io.setIoDate(rs.getString("IO_DATE"));
				io.setAmount(rs.getInt("AMOUNT"));
				io.setStatus(rs.getString("STATUS"));
				list.add(io);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
		}
		
		return list;
	}

	public int insertProductIO(Connection conn, ProductIO productIO) {
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		
		int result = 0;
		boolean flag = false;
		
		if(productIO.getStatus().equals("출고")) flag = true;
		
		try {
			if(flag) {
				String sql2 = prop.getProperty("selectOne");
				
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, productIO.getProductId());

				rs2 = pstmt2.executeQuery();
				
				if(rs2.next()) {
					int stock = rs2.getInt("STOCK");
					if(stock < productIO.getAmount()) {
						return 8;//재고수량보다 출고수량이 많을 때
					}
				}else {
					return 9;//PRODUCT_STOCK 에 없는 제품
				}
			}
			
			String sql = prop.getProperty("insertProductIO");
			
			pstmt = conn.prepareStatement(sql);
			//출고 전에 재고 개수 확인하여 출고 수가 재고 수 보다 많으면 오류 메시지 출력(8)
			pstmt.setString(1, productIO.getProductId());
			pstmt.setInt(2, productIO.getAmount());
			pstmt.setString(3, productIO.getStatus());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(pstmt2);
			close(rs2);
		}
		
		return result;
	}

}
