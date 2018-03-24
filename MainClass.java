package com.wipro.performance.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.wipro.performance.bean.EmployeeBean;
import com.wipro.performance.entity.Service;

public class MainClass {
	public static void main(String args[]) {
		EmployeeBean bean = new EmployeeBean();
		Service app = new Service();
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the ADID details");
		bean.setADID(scan.next());
		System.out.println("Enter the BU unit");
		bean.setBusinessUnit(scan.next());
		System.out.println("Enter the joining date in the format like DDMMYY ");
		String date= scan.next();
		try {
			bean.setDateOfJoining(new Date(new SimpleDateFormat("ddMMyyyy").parse(date).getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Enter the current salary");
		bean.setCurrentSalary(scan.nextFloat());
		System.out.println("Enter the attendance");
		bean.setTotalAttendance(scan.nextFloat());
		System.out.println("Enter the Manager Rating");
		bean.setManagerRating(scan.nextFloat());
		String st= app.getAppraisalDetails(bean);
		System.out.println(st);
	}
	
}
