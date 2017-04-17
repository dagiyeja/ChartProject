package main;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;


public class PieChartPanel {
	JFreeChart chart;
	DefaultPieDataset dataset;
	Connection con;
	
	//원할 때 붙일 수 있게 생성자에 붙이지 않음
	public PieChartPanel(Connection con) {
		this.con=con; 
	}
	
	//성별 분석
	public void getGenderData(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		StringBuffer sql=new StringBuffer();
		sql.append("select gender, count(gender) as 응시자수");
		sql.append(", (select count(*) from score) as 총학생수");
		sql.append(", (count(gender)/(select count(*) from score))*100 as 비율");
		sql.append(" from score"); 
		sql.append(" group by gender");
		
		try {
			pstmt=con.prepareStatement(sql.toString());
			rs=pstmt.executeQuery();
			
			dataset=new DefaultPieDataset();
			
			while(rs.next()){
				dataset.setValue(rs.getString("gender"),rs.getInt("비율"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	
	}
	
	//학년별 분석
	public void getGradeData(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		StringBuffer sql=new StringBuffer();
		sql.append("select grade, count(grade) as 응시자수");
		sql.append(", (select count(*) from score) as 총학생수");
		sql.append(", (count(grade)/(select count(*) from score))*100 as 비율");
		sql.append(" from score"); 
		sql.append(" group by grade");
		
		try {
			pstmt=con.prepareStatement(sql.toString());
			rs=pstmt.executeQuery();
			
			dataset=new DefaultPieDataset();
			
			while(rs.next()){
				dataset.setValue(rs.getString("응시자수"),rs.getInt("비율"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	
	}
	
	
	
	//차트가 붙어있는 패널을 main의 p_east패널에 붙인다
	public ChartPanel showChart(){
		//getGenderData();
		getGradeData();
		chart=ChartFactory.createPieChart("성적 데이터 분석", dataset, true, true, false);
		
		//현재 차트에 설정된 폰트를 한글폰트로 바꾸지 않으면 깨져보인다!!
		//System.out.println(chart.getTitle().getFont().getFontName());
		
		Font oldTitle=chart.getTitle().getFont();
		Font oldLegend=chart.getLegend().getItemFont();
		
		chart.getTitle().setFont(new Font("굴림",oldTitle.getStyle() ,oldTitle.getSize()));
		chart.getLegend().setItemFont(new Font("굴림",oldLegend.getStyle() ,oldLegend.getSize()));
		
		PiePlot plot=(PiePlot)chart.getPlot();
		plot.setLabelFont(new Font("굴림", oldLegend.getStyle(), oldLegend.getSize()));
		
		
		ChartPanel  chartPanel=new ChartPanel(chart);
		return chartPanel; 
		
	}
	
}
