package com.giffgaff.controller;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.giffgaff.model.Student;
import com.giffgaff.service.StudService;

@Controller
public class StudentController {

	private static final Logger LOGGER = Logger.getLogger(StudentController.class.getName());
	@Autowired
	StudService studservice;

	@Autowired
	Student student;

	@RequestMapping("/studentApp")
	public String welcomeUser(Model model) {
		
		System.out.println(" ===================================================================");
		System.out.println(" *************** WELCOME TO STUDENT DATA MANAGEMENT  *************** ");
		System.out.println(" ===================================================================");
		System.out.println("Please Enter required option from below from 1 to 4");
		System.out.println("If you want to ADD new student, Enter 1");
		System.out.println("If you want to SEARCH student using student id, Enter 2");
		System.out.println("If you want to DISPLAY ALL STUDENTS in sorted order of highest marks, Enter 3");
		System.out.println("If you want to DISPLAY the student scored HIGHEST MARKS, Enter 4");
		System.out.println(" ===================================================================\n");
		
		Scanner in = new Scanner(System.in);
		int option = in.nextInt();
		//int option = !StringUtils.isNumeric(in.next()) ? -1 :  Integer.parseInt(in.next());

		switch (option) {
		case 1:
			addStudent();
			break;
		case 2:
			getStudent();
			break;
		case 3:
			getAllStudentData();
			break;
		case 4:
			getTopStudent();
			break;
		default:
			System.out.println("XXXXX Sorry, Wrong input! Please Enter Right Option(1 to 4 Only)!!! XXXXX \n =XXX= END =XXX=");
			break;
		}

		model.addAttribute("name", "WELCOME TO STUDENT APPLICATION");
		return "Success";
	}

	/**
	 * It will add student in to database
	 */
	public void addStudent() {
		
		System.out.println("******** ADD New Student Details Here  ********");
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter Student Name ==> ");
		String name = in.nextLine();

		System.out.println("Enter Student Total Marks ==> ");
		String total_marks = in.nextLine();

		student.setName(name);
		student.setTotal_marks(total_marks);
		int status = studservice.insertData(student);
		if (0 != status)
			System.out.println("******** Successfully Inserted  !!! ********");
		
	}
	
	/**
	 * It will get all the students data from database
	 */
	public void getAllStudentData() {
		System.out.println("******** ALL THE STUDENT DETAILS  ********");

		List<Student> students = studservice.fetchData();

		System.out.println(" SID\t" + "SName\t " + " Marks\t");
		System.out.println(" ================================");

		students.stream().forEach(student -> System.out
				.print(student.getStud_id() + "\t" + student.getName() + "\t  " + student.getTotal_marks() + "\n"));


		System.out.println(" ================================");
		System.out.println("******** ALL THE STUDENT DETAILS  ********");

	}
	
	/**
	 * It will get the top scored student data from database
	 */
	public void getTopStudent() {

		System.out.println("******** HIGHEST MARKS STUDENT ********");

		Student topStudent = studservice.highestMarksStudent();

		System.out.println(" SID\t" + "SName\t" + "Marks\t");
		System.out.println(" ================================");
		System.out.print(topStudent.getStud_id() + "\t" + topStudent.getName() + "\t\t" + topStudent.getTotal_marks() + "\n");
		System.out.println(" ================================");
		
		System.out.println("******** HIGHEST MARKS STUDENT ********");
		System.out.println("\n Student Details Has Been Serialized !!");
	}

	/**
	 * It will get the required student data from database
	 */
	public void getStudent() {
		System.out.println("******** SEARCH Student  ********");
		System.out.println("Please enter student ID which you want to search :");
		Scanner in = new Scanner(System.in);

		String id = in.nextLine();

		System.out.println("******** STUDENT DETAILS ********");

		Student studentDetails = studservice.getStudDetail(Integer.parseInt(id));

		System.out.println(" SID\t" + "SName\t\t" + "Marks\t");
		System.out.println(" =============================== ");

		System.out.println(" "+
				studentDetails.getStud_id() + "\t" + studentDetails.getName() + "\t\t" + studentDetails.getTotal_marks());
		
		System.out.println("******** STUDENT DETAILS ********");

	}
}
