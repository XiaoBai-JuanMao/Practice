package com.gec.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public abstract class JDBCUtil<T> {
	
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private static Properties p = new Properties();
	private static DataSource ds; // 创建数据源对象用于接收druid获取的数据源
	static {
		try (InputStream in = JDBCUtil.class.getClassLoader().getResourceAsStream("/db.properties")) {
			// 将流加载进p对象
			p.load(in);
			// 通过DruidData属性对象,得到数据源
			ds = DruidDataSourceFactory.createDataSource(p);
		} catch (Exception e) {
			System.out.println("创建数据源异常:" + e.getMessage());
		}
	}

	private Connection getConn() {
		try {
			// 获取连接,赋值给conn,
			conn = ds.getConnection();
		} catch (SQLException e) {
			System.out.println("连接异常:" + e.getMessage());
		}
		return conn;
	}

	// 统一更新
	public boolean update(String sql, Object... obj) {
		try {
			pst = getConn().prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				pst.setObject(i + 1, obj[i]);
			}
			int row = pst.executeUpdate();
			if (row > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose(conn, pst, rs);
		}
		return false;
	}

	// 统一查询
	public List<T> query(String sql, Object... obj) {
		List<T> list = new ArrayList<>();
		try {
			pst = getConn().prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				pst.setObject(i + 1, obj[i]);
			}
			rs = pst.executeQuery();
			while (rs.next()) {
				list.add(getEntity(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose(conn, pst, rs);
		}
		return list;
	}

	public abstract T getEntity(ResultSet rs) throws Exception;

	// 查询函数
	public int queryCount(String sql, Object... obj) {
		int row = 0;
		try {
			pst = getConn().prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				pst.setObject(i + 1, obj[i]);
			}
			rs = pst.executeQuery();
			if (rs.next()) {
				row = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose(conn, pst, rs);
		}
		return row;
	}

	// 关闭
	private void getClose(Connection conn, PreparedStatement pst, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (pst != null)
				pst.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
