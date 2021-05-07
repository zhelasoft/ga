package ga;

import ga.exception.CountableException;
import ga.exception.UncountableException;

public class SampleClass {

	public boolean determineGuess(int userAnswer, int computerNumber) 
			throws UncountableException, CountableException {

		if (userAnswer <= 0 || userAnswer > 100) {
			throw new UncountableException("Invalid number range!");
		}

		if (userAnswer > computerNumber) {
			throw new CountableException("Your guess is too high, try again");
		}

		if (userAnswer < computerNumber) {
			throw new CountableException("Your guess is too low, try again");
		}

		return (userAnswer == computerNumber);
	}

	public void setMemberAge(long age) 
			throws UncountableException, CountableException {

		if (age > 120 || age < 1) {
			throw new UncountableException("Invalid age!");
		}

		if (!(age > 12 && age < 20)) {
			throw new CountableException("Is not teenage");
		}

	}

	public boolean isQualifiedForRetirement(long age, long yearsOfService)
			throws UncountableException, CountableException {

		if (age < 1 || age > 120 || yearsOfService < 1 || 
				yearsOfService > 60 || age < yearsOfService) {
			throw new UncountableException("Invalid number range!");
		}

		if (yearsOfService < 15) {
			throw new CountableException("Not enough years of service");
		}

		if (age < 60) {
			String errorMsg = "The person is young, therefore not qualified for retirement";
			throw new CountableException(errorMsg);
		}

		return true;
	}

	public double withdraw(boolean active, double amount) 
			throws UncountableException, CountableException {

		if (amount < 1) {
			throw new UncountableException("Invalid amount number!");
		}

		if (!active) {
			throw new CountableException("The account is not active");
		}

		if (amount < 1 || amount > 3000) {
			throw new CountableException("");
		}

		return amount;
	}

	// A user wants to improve typing skill
	public int calculateScore(char entered, char prompted) 
			throws CountableException {
		int score = 0;
		char smallEnteredChar = Character.toLowerCase(entered);
		char smallPromptedChar = Character.toLowerCase(prompted);

		if (Character.compare(smallEnteredChar, smallPromptedChar) != 0) {
			throw new CountableException("The letters are not the same!");
		}

		boolean enteredCase = Character.isUpperCase(entered);
		boolean promptedCase = Character.isUpperCase(prompted);

		if (enteredCase != promptedCase) {
			String errorMsg = "The entered key and prompted cases key are not the same";
			throw new CountableException(errorMsg);
		}

		return score;
	}

	public void registerUser(String username, String password) 
			throws CountableException {

		if (username.length() < 6) {
			throw new CountableException("Username can not be less than 6 characters!");
		}

		if (password.length() < 6 || password.length() > 12) {
			throw new CountableException("Password should be between 6-12 characters!");
		}

		// create user and return username
	}

}
