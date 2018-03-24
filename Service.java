package com.wipro.performance.entity;
import com.wipro.performance.bean.EmployeeBean;
import java.lang.Throwable;
import java.lang.reflect.GenericArrayType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.wipro.performance.exception.InvalidADIDException;
import com.wipro.performance.exception.InvalidBUException;
import com.wipro.performance.exception.InvalidCurrentSalaryException;
import com.wipro.performance.exception.InvalidDOJException;

public class Service {
	public String validateData(EmployeeBean ebean){
		String Status="";
		
		if(ebean.getADID().length()<6||ebean.getADID().length()>6||!ebean.getADID().matches("[a-zA-Z0-9]+")){
			try {
				throw new InvalidADIDException();
			} catch (InvalidADIDException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(!("java".equalsIgnoreCase(ebean.getBusinessUnit())||(!("oracle".equalsIgnoreCase(ebean.getBusinessUnit()))||(!("bigData".equalsIgnoreCase(ebean.getBusinessUnit())))))){
			try {
				throw new InvalidBUException();
			} catch (InvalidBUException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Calendar calender = Calendar.getInstance();
		Date today = calender.getTime();
		calender.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = calender.getTime();
		long prevDay= System.currentTimeMillis() - 1000*60*60*24;
		Date prev = new Date(prevDay);
		if(ebean.getDateOfJoining().after(today)||ebean.getDateOfJoining().after(prev)) {
			try {
				throw new InvalidDOJException();
			} catch (InvalidDOJException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(ebean.getCurrentSalary()<50000) {		
			try {
				throw new InvalidCurrentSalaryException();
			} catch (InvalidCurrentSalaryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			Status="SUCCESS";
		}
		if(ebean.getTotalAttendance()>=0||ebean.getTotalAttendance()<=200){
			Status="SUCCESS";
		}else {
			Status="Invalid Attendance";
		if(ebean.getManagerRating()>=0||ebean.getManagerRating()<=5){
			Status="SUCCESS";
		}else {
			Status="Invalid Rating";	
			}
		}
		
		return Status;
	}
	
	public String computeAppraisal(EmployeeBean ebean){
		double attendanceHike=0;
		float finaHike=0f;
		String hike="";
		float managerRatingHike=0f;
		
		if(validateData(ebean)=="SUCCESS") {
			if(ebean.getTotalAttendance()>=0&&ebean.getTotalAttendance()<=100) {
				attendanceHike=ebean.getCurrentSalary();
			}else if(ebean.getTotalAttendance()>100&&ebean.getTotalAttendance()<=150) {
				attendanceHike=(5*(ebean.getCurrentSalary())/100);
			}else if(ebean.getTotalAttendance()>150&&ebean.getTotalAttendance()<=200) {
				attendanceHike=(6*(ebean.getCurrentSalary())/100);
			}
			
			if(ebean.getManagerRating()==5) {
				managerRatingHike=(10*(ebean.getCurrentSalary())/100);
			}else if(ebean.getManagerRating()==4) {
				managerRatingHike=(9*(ebean.getCurrentSalary())/100);
			}else if(ebean.getManagerRating()==3) {
				managerRatingHike=(8*(ebean.getCurrentSalary())/100);
			}else if(ebean.getManagerRating()==2) {
				managerRatingHike=(7*(ebean.getCurrentSalary())/100);
			}else if(ebean.getManagerRating()==1) {
				managerRatingHike=(6*(ebean.getCurrentSalary())/100);
			}else {
				managerRatingHike=ebean.getCurrentSalary();
			}
			finaHike=(float) (attendanceHike+managerRatingHike);
			hike= Float.toString(finaHike);
		}else {
			hike=validateData(ebean);
		}
	
		
		return hike;	
	}
	public String getAppraisalDetails(EmployeeBean ebean){
		float updatedSalary=0f;
		String salary="";
		updatedSalary=Float.parseFloat(computeAppraisal(ebean))+ebean.getCurrentSalary();
		if(updatedSalary>ebean.getCurrentSalary()) {
			salary=Float.toString(updatedSalary);
		}else {
			salary=ebean.getADID()+ ":" +computeAppraisal(ebean);
		}
		return salary;
		
	}
}
