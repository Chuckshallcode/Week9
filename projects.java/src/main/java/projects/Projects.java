/**
 * 
 */
package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

/**
 * 
 */
public class Projects {
	private Scanner scanner = new Scanner(System.in);
	private ProjectService projectService = new ProjectService();
	
	private List<String> operations = List.of(
			"1) Add a project"
			);

	public static void main(String[] args) {
		new Projects().processUserSelections();
	}

	private void processUserSelections() {
		boolean done = false;
		
		while(!done) {
			try {
				int selection = getUserSelection();
				
				switch(selection) {
				case -1:
					done = exitMenu();
					break;
					
				case 1:
					addProject();
					break;
					
				default:
					System.out.println("\n" + selection + " is not a valud selectoin.  Try again.");
					break;
				}
			}
			catch(Exception e) {
				System.out.println("\nError: " + e + " Try again.");
			}
		}
	}
	private int getUserSelection() {
		printOperations();
		
		Integer input = getIntInput("Enter a menu selection");
		
		return Objects.isNull(input) ? -1 : input;
	}


	private void addProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
		String notes = getStringInput("Enter the project notes");
		
		Project project = new Project();
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		
		Project dbProject = projectService.addProject(project);
		System.out.println("You have successfully created project: " + dbProject);
	}
	private String getStringInput(String prompt) {
		System.out.println(prompt + ": ");
		String input = scanner.nextLine();
		return input.isBlank() ? null : input.trim();
	}

	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
	
		if(Objects.isNull(input)) {
			return null;
		}
		
		try {
			return new BigDecimal(input).setScale(2);
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal number.");
		}
	}
	
	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		
		try {
			return Integer.valueOf(input);
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid number");
		}
	}
	
	private boolean exitMenu() {
		System.out.println("Exiting the meny.");
		return true;
	}
	
	private void printOperations() {
		System.out.println("\nThese are the available selections.  Press hte Enter key to quit:");
		
		for(String line : operations) {
			System.out.println(" " + line);
		}
	}

}
