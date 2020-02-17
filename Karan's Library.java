
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Library {

	public static void main(String[] args) {
		mainSystem();
	}

	public static void mainSystem() {
		//Welcome message for the user and a list of avaiable books
		System.out.println("*********************************************************************");
		System.out.println("*Welcome to Karan's Library, these are the books which are available:\n");
		System.out.println("*********************************************************************");
		listBooks();
		System.out.println("*********************************************************************");
		//User is prompted to enter an option(add, update, delete or search)
		System.out.println("Please select an option from below:\n " 
						 + "\n0. Exit."
						 + "\n1. Add/Enter a book." 
						 + "\n2. Update a book." 
						 + "\n3. Delete a book." 
						 + "\n4. Search for a book.");
		
		Scanner scMain = new Scanner(System.in);		//Scanner for the main menu is declared
		int userChoice = scMain.nextInt();				//Scanner takes in user input
		//If-Else statement executes from the option selected by the user
		do {
			if (userChoice == 0) {
				System.out.println("Exiting the Library, Goodbye!");
			}
			  else if (userChoice == 1) {
				addBook();
				break;
			} else if (userChoice == 2) {
				updateBook();
				break;
			} else if (userChoice == 3) {
				deleteBook();
				break;
			}
			  else if (userChoice == 4) {
				searchBook();
				break;
			} else {
				System.out.println("ERROR: Invalid option, please restart and try again...");
				break;
			}
		} while (userChoice != 0);

		scMain.close();			//Scanner is closed
	}

	private static void listBooks() {
		//Connection to the database is established
		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/ebookstore?useSSL=false", "myuser", "");
				Statement stmt = conn.createStatement();
		) {
			String strSelect = "select * from books";					 // prints out all info from books table
			ResultSet rset = stmt.executeQuery(strSelect);
			System.out.println("[ID, TITLE, AUTHOR, QUANTITY]");
			while (rset.next()) { 
				System.out.println(rset.getInt("id") + ", " + rset.getString("title") + ", " + rset.getString("author")
						+ ", " + rset.getInt("qty"));	
			}	
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}	
	
	private static void addBook() {
		try (Connection conn = DriverManager.getConnection( 			// Allocate a database 'Connection' object
				"jdbc:mysql://localhost:3306/ebookstore?useSSL=false", "myuser", "");
				Statement stmt = conn.createStatement(); 				// Allocate a 'Statement' object in the Connection
		) {

			Scanner scAdd = new Scanner(System.in);						//Scanner introduced for the addBook method
			
			System.out.println("Enter 0 to go back to Main Menu or 1 to continue adding a new book.");
			int confirm = scAdd.nextInt();
			scAdd.nextLine();
			if (confirm == 0) {
				mainSystem();
			}
			else if (confirm == 1) {

			System.out.println("\nEnter ID: "); 						// prompt user to enter book id
			int id = scAdd.nextInt();
			scAdd.nextLine();

			System.out.println("\nEnter title: "); 						// prompt user to enter book title
			String title = scAdd.nextLine();

			System.out.println("\nEnter Author: "); 					// prompt user to enter book author
			String author = scAdd.nextLine();

			System.out.println("\nEnter quantity in stock: \n"); 		// prompt user to enter stock of book
			int qty = scAdd.nextInt();

			String sqlInsert = "insert into books values ('" + id + "','" + title + "','" + author + "','" + qty
					+ "');";
			int countInserted = stmt.executeUpdate(sqlInsert); 			// execute using executeUpdate statement - countInserted
																		// increases
			System.out.println(countInserted + " records inserted.\n");

			String strSelect = "select * from books"; 					// prints out all info from books table
			ResultSet rset = stmt.executeQuery(strSelect);

			while (rset.next()) { 										// Move the cursor to the next row
				System.out.println(rset.getInt("id") + ", " + rset.getString("title") + ", " + rset.getString("author")
						+ ", " + rset.getInt("qty"));
			}
			//User is presented with another menu to choose from
			System.out.println("\nWould you like to:\n " 
					 + "\n1. Add/Enter another book into the Library."
					 + "\n2. Go to the Main Menu." 
					 + "\n0. Exit the program");
			int addMore = scAdd.nextInt();
			if (addMore == 1) {
				addBook();
			}
			if (addMore == 2) {
				mainSystem();
			}
			else {
				System.out.println("Thanks for adding to the Library, Goodbye!");
			}
			scAdd.close();												//Scanner is closed
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void updateBook() {
		
		Scanner scUpdate = new Scanner(System.in);


		try (Connection conn = DriverManager.getConnection( 			// Allocate a database 'Connection' object
				"jdbc:mysql://localhost:3306/ebookstore?useSSL=false", "myuser", "");
				Statement stmt = conn.createStatement(); 				// Allocate a 'Statement' object in the Connection
		) {
			//User is presented with a menu asking how they'd like to find the book to update
			System.out.println("Please select an option from below:\n" 
							+ "How would you like to search for the book to be Updated: " 
							+ "\n1. Enter an ID."
							+ "\n2. Enter a Title." 	
							+ "\n3. Enter an Author's name."
							+ "\n0. Back to Main Menu");

			int update_by = scUpdate.nextInt();
			
			//IF THE USER WANTS TO UPDATE ANY COLUMN OF THE TABLE USING THE ID OF A BOOK
			if (update_by == 1) {
					System.out.println("Please enter the ID of the book you wish to update:\n");
					int id = scUpdate.nextInt();
					
					String sqlFind = "select * from books where id = '" + id + "';";
					ResultSet rset = stmt.executeQuery(sqlFind);
					while (rset.next()) { 
						System.out.println(rset.getInt("id") + ", " 
										+ rset.getString("title") + ", " 
										+ rset.getString("author") + ", " 
										+ rset.getInt("qty"));
					}	
					//User presented with a menu to choose what they would like to update
					System.out.println("\nWhat would you like to update: "
							+ "\n1. The ID."
							+ "\n2. The Title."
							+ "\n3. The Author."
							+ "\n4. The quantity in stock.");
					
					int updateWith = scUpdate.nextInt();
					scUpdate.nextLine();
					if (updateWith == 1) {												//Update the ID of the book
						System.out.println("Please enter the new ID for this book:\n");
						int new_id = scUpdate.nextInt();
						scUpdate.nextLine();
						String sqlUpdate = "update books set id = '" + new_id + "' where id = '" + id + "';";
						int countUpdated = stmt.executeUpdate(sqlUpdate);
						System.out.println(countUpdated + " records updated.\n");
						System.out.println("Books Table after the update request:\n");
						listBooks();
					}
					else if (updateWith == 2) {											//Update the Title of the book
						System.out.println("Please enter the new Title for this book:\n");
						String new_title = scUpdate.nextLine();
						//scUpdateId.nextLine();
						String sqlUpdate = "update books set title = '" + new_title + "' where id = '" + id + "';";
						int countUpdated = stmt.executeUpdate(sqlUpdate);
						System.out.println(countUpdated + " records updated.\n");
						System.out.println("Books Table after the update request:\n");
						listBooks();
					}
					else if (updateWith == 3) {											//Update the Author of the book
						System.out.println("Please enter the new Author for this book:\n");
						String new_author = scUpdate.nextLine();
						String sqlUpdate = "update books set author = '" + new_author + "' where id = '" + id + "';";
						int countUpdated = stmt.executeUpdate(sqlUpdate);
						System.out.println(countUpdated + " records updated.\n");
						System.out.println("Books Table after the update request:\n");
						listBooks();
					}
					else if (updateWith == 4) {											//Update the Quantity of the book
						System.out.println("Please enter how much stock we have of this book:\n");
						int new_quantity = scUpdate.nextInt();
						scUpdate.nextLine();
						String sqlUpdate = "update books set qty = '" + new_quantity + "' where id = '" + id + "';";
						int countUpdated = stmt.executeUpdate(sqlUpdate);
						System.out.println(countUpdated + " records updated.\n");
						System.out.println("Books Table after the update request:\n");
						listBooks();
					}
					//User presented with another menu
					System.out.println("\nWould you like to:\n " 
							 + "\n1. Update another book in the Library."
							 + "\n2. Go to the Main Menu." 
							 + "\n0. Exit the program");
					int updateMore = scUpdate.nextInt();
					if (updateMore == 1) {
						updateBook();
					}
					if (updateMore == 2) {
						mainSystem();
					}
					else {
						System.out.println("Thanks for updating the Library, Goodbye!");
					}
				}
				//IF THE USER WANTS TO UPDATE ANY COLUMN OF THE TABLE USING THE TITLE OF A BOOK
				else if (update_by == 2) {
					System.out.println("Please enter the Title of the book you wish to update:\n");
					scUpdate.nextLine();
					String title = scUpdate.nextLine();
					String sqlFind = "select * from books where title = '" + title + "';";
					ResultSet rset = stmt.executeQuery(sqlFind);
					while (rset.next()) { 
						System.out.println(rset.getInt("id") + ", " 
										+ rset.getString("title") + ", " 
										+ rset.getString("author") + ", " 
										+ rset.getInt("qty"));
					}						
					//User presented with a menu to choose what they would like to update
					System.out.println("What would you like to update: "
							+ "\n1. The ID."
							+ "\n2. The Title."
							+ "\n3. The Author."
							+ "\n4. The quantity in stock.");
					int updateWith = scUpdate.nextInt();
					scUpdate.nextLine();
					
					if (updateWith == 1) {												//Update the ID of the book
						System.out.println("Please enter the new ID for this book:\n");
						int new_id = scUpdate.nextInt();
						scUpdate.nextLine();
						String sqlUpdate = "update books set id = '" + new_id + "' where title = '" + title + "';";
						int countUpdated = stmt.executeUpdate(sqlUpdate);
						System.out.println(countUpdated + " records updated.\n");
						System.out.println("Books Table after the update request:\n");
						listBooks();
					}
					else if (updateWith == 2) {											//Update the Title of the book
						System.out.println("Please enter the new Title for this book:\n");
						String new_title = scUpdate.nextLine();
						//scUpdateId.nextLine();
						String sqlUpdate = "update books set title = '" + new_title + "' where title = '" + title + "';";
						int countUpdated = stmt.executeUpdate(sqlUpdate);
						System.out.println(countUpdated + " records updated.\n");
						System.out.println("Books Table after the update request:\n");
						listBooks();
					}
					else if (updateWith == 3) {											//Update the Author of the book
						System.out.println("Please enter the new Author for this book:\n");
						String new_author = scUpdate.nextLine();
						String sqlUpdate = "update books set author = '" + new_author + "' where title = '" + title + "';";
						int countUpdated = stmt.executeUpdate(sqlUpdate);
						System.out.println(countUpdated + " records updated.\n");
						System.out.println("Books Table after the update request:\n");
						listBooks();
					}
					else if (updateWith == 4) {											//Update the Quantity of the book
						System.out.println("Please enter how much stock we have of this book:\n");
						int new_quantity = scUpdate.nextInt();
						scUpdate.nextLine();
						String sqlUpdate = "update books set qty = '" + new_quantity + "' where title = '" + title + "';";
						int countUpdated = stmt.executeUpdate(sqlUpdate);
						System.out.println(countUpdated + " records updated.\n");
						System.out.println("Books Table after the update request:\n");
						listBooks();
					}
					//User presented with another menu
					System.out.println("\nWould you like to:\n " 
							 + "\n1. Update another book in the Library."
							 + "\n2. Go to the Main Menu." 
							 + "\n0. Exit the program");
					int updateMore = scUpdate.nextInt();
					if (updateMore == 1) {
						updateBook();
					}
					if (updateMore == 2) {
						mainSystem();
					}
					else {
						System.out.println("Thanks for updating the Library, Goodbye!");
					}}
				
				//IF THE USER WANTS TO UPDATE ANY COLUMN OF THE TABLE USING THE AUTHOR OF A BOOK		
				else if (update_by == 3) {
					System.out.println("Please enter the Author of the book you wish to update:\n");
					scUpdate.nextLine();
					String author = scUpdate.nextLine();
					String sqlFind = "select * from books where author = '" + author + "';";
					ResultSet rset = stmt.executeQuery(sqlFind);
					while (rset.next()) { 
						System.out.println(rset.getInt("id") + ", " 
										+ rset.getString("title") + ", " 
										+ rset.getString("author") + ", " 
										+ rset.getInt("qty"));
					}			
					//User presented with a menu to choose what they would like to update
					System.out.println("What would you like to update: "
							+ "\n1. The ID."
							+ "\n2. The Title."
							+ "\n3. The Author."
							+ "\n4. The quantity in stock.");
					int updateWith = scUpdate.nextInt();
					scUpdate.nextLine();
					
					if (updateWith == 1) {												//Update the ID of the book
						System.out.println("Please enter the new ID for this book:\n");
						int new_id = scUpdate.nextInt();
						scUpdate.nextLine();
						String sqlUpdate = "update books set id = '" + new_id + "' where author = '" + author + "';";
						int countUpdated = stmt.executeUpdate(sqlUpdate);
						System.out.println(countUpdated + " records updated.\n");
						System.out.println("Books Table after the update request:\n");
						listBooks();
					}
					else if (updateWith == 2) {											//Update the Title of the book
						System.out.println("Please enter the new Title for this book:\n");
						String new_title = scUpdate.nextLine();
						//scUpdateId.nextLine();
						String sqlUpdate = "update books set title = '" + new_title + "' where author = '" + author + "';";
						int countUpdated = stmt.executeUpdate(sqlUpdate);
						System.out.println(countUpdated + " records updated.\n");
						System.out.println("Books Table after the update request:\n");
						listBooks();
					}
					else if (updateWith == 3) {											//Update the Author of the book
						System.out.println("Please enter the new Author for this book:\n");	
						String new_author = scUpdate.nextLine();
						String sqlUpdate = "update books set author = '" + new_author + "' where author = '" + author + "';";
						int countUpdated = stmt.executeUpdate(sqlUpdate);
						System.out.println(countUpdated + " records updated.\n");
						System.out.println("Books Table after the update request:\n");
						listBooks();
					}
					else if (updateWith == 4) {											//Update the Quantity of the book
						System.out.println("Please enter how much stock we have of this book:\n");
						int new_quantity = scUpdate.nextInt();
						scUpdate.nextLine();
						String sqlUpdate = "update books set qty = '" + new_quantity + "' where author = '" + author + "';";
						int countUpdated = stmt.executeUpdate(sqlUpdate);
						System.out.println(countUpdated + " records updated.\n");
						System.out.println("Books Table after the update request:\n");
						listBooks();
					}	
						//User presented with another menu
						System.out.println("\nWould you like to:\n " 
								 + "\n1. Update another book in the Library."
								 + "\n2. Go to the Main Menu." 
								 + "\n0. Exit the program");
						int updateMore = scUpdate.nextInt();
						if (updateMore == 1) {
							updateBook();
						}
						if (updateMore == 2) {
							mainSystem();
						}
						else {
							System.out.println("Thanks for updating the Library, Goodbye!");
						}}
					 else {
						mainSystem();
					 }
		} catch (SQLException ex) {
			ex.printStackTrace();
			scUpdate.close();
		}
	}

	public static void deleteBook() {
		Scanner scDel = new Scanner(System.in);

		try (Connection conn = DriverManager.getConnection( // Allocate a database 'Connection' object
				"jdbc:mysql://localhost:3306/ebookstore?useSSL=false", "myuser", "");
				Statement stmt = conn.createStatement(); // Allocate a 'Statement' object in the Connection
		) {
			//User presented with a menu to choose how they'd like to find the book to be deleted
			System.out.println("Please select an option from below:\n" 
							+ "Delete book by entering: " 
							+ "\n1. An ID."
							+ "\n2. A Title." 	
							+ "\n3. An Author."
							+ "\n0. Back to Main Menu");

			int delete_by = scDel.nextInt();

			if (delete_by == 1) {													//Delete the book by entering an ID
				System.out.println("Please enter the ID of the book you wish to delete:\n");
				int id = scDel.nextInt();

				String sqlDeleteId = "delete from books where id = '" + id + "'";
				int countDeleted = stmt.executeUpdate(sqlDeleteId);
				System.out.println(countDeleted + " records deleted.\n");
				
				System.out.println("Books Table after the delete request:\n");
				listBooks();
				
				System.out.println("\nWould you like to:\n " 
						 + "\n1. Delete another book in the Library."
						 + "\n2. Go to the Main Menu." 
						 + "\n0. Exit the program");
				int deleteMore = scDel.nextInt();
				if (deleteMore == 1) {
					deleteBook();
				}
				if (deleteMore == 2) {
					mainSystem();
				}
				else {
					System.out.println("Thanks for updating the Library, Goodbye!");
				}
			}
			else if (delete_by == 2) {												//Delete the book by entering a Title
				System.out.println("Please enter the title of the book to be deleted: ");
				scDel.nextLine();
				String title = scDel.nextLine();

				String sqlDeleteTitle = "delete from books where title = '" + title + "'";
				int countDeleted = stmt.executeUpdate(sqlDeleteTitle);
				System.out.println(countDeleted + " records deleted.\n");
					
				System.out.println("Books Table after the delete request:\n");
				listBooks();
				System.out.println("\nWould you like to:\n " 
						 + "\n1. Delete another book in the Library."
						 + "\n2. Go to the Main Menu." 
						 + "\n0. Exit the program");
				int deleteMore = scDel.nextInt();
				if (deleteMore == 1) {
					deleteBook();
				}
				if (deleteMore == 2) {
					mainSystem();
				}
				else {
					System.out.println("Thanks for updating the Library, Goodbye!");
				}
			}
			
			else if (delete_by == 3) {													//Delete the book by entering an Author
				System.out.println("Please enter the author of the book to be deleted:\n");
				scDel.nextLine();
				String author = scDel.nextLine();

				String sqlDeleteAuthor = "delete from books where author like '" + author + "'";
				int countDeleted = stmt.executeUpdate(sqlDeleteAuthor);
				System.out.println(countDeleted + " records deleted.\n");

				System.out.println("Books Table after the delete request:\n");
				listBooks();
				System.out.println("\nWould you like to:\n " 
						 + "\n1. Delete another book in the Library."
						 + "\n2. Go to the Main Menu." 
						 + "\n0. Exit the program");
				int deleteMore = scDel.nextInt();
				if (deleteMore == 1) {
					deleteBook();
				}
				if (deleteMore == 2) {
					mainSystem();
				}
				else {
					System.out.println("Thanks for updating the Library, Goodbye!");
				}
			}
			else if (delete_by == 0) {
				mainSystem();
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			scDel.close();
		}
	}

	public static void searchBook() {
	
		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/ebookstore?useSSL=false", "myuser", "");
				Statement stmt = conn.createStatement();
		) {
			Scanner scSearch = new Scanner(System.in);
			//User presented with a menu to choose how they'd like to search for a book
			System.out.println("Please choose from the following search options:"
							+ "\n1. Search by ID."
							+ "\n2. Search by Title."
							+ "\n3. Search by Author"
							+ "\n0. Back to Main Menu");
			int search_by = scSearch.nextInt();
			scSearch.nextLine();
			
			if (search_by == 0) {
				mainSystem();
			}
			else if (search_by == 1) {														//Search for the book by entering an ID
				System.out.println("Please enter the ID of the book you are looking for:\n");
				int id = scSearch.nextInt();
				String sqlFind = "select * from books where id = '" + id + "';";
				System.out.println();
				ResultSet rset = stmt.executeQuery(sqlFind);
				while (rset.next()) { 
					System.out.println(rset.getInt("id") + ", " 
									+ rset.getString("title") + ", " 
									+ rset.getString("author") + ", " 
									+ rset.getInt("qty"));
				}
			}
			else if (search_by == 2) {														//Search for the book by entering an Title
				System.out.println("Please enter the Title or words from the Title of the book you are looking for:\n");
				String title = scSearch.nextLine();
				String sqlFind = "select * from books where title like '%" + title + "%';";
				System.out.println();
				ResultSet rset = stmt.executeQuery(sqlFind);
				while (rset.next()) { 
					System.out.println(rset.getInt("id") + ", " 
									+ rset.getString("title") + ", " 
									+ rset.getString("author") + ", " 
									+ rset.getInt("qty"));
				}
			}
			else if (search_by == 3) {														//Search for the book by entering an Author	
				System.out.println("Please enter the Author or partial names of the author of the book you are looking for:\n");
				String author = scSearch.nextLine();
				String sqlFind = "select * from books where author like '%" + author + "%';";
				System.out.println();
				ResultSet rset = stmt.executeQuery(sqlFind);
				while (rset.next()) { 
					System.out.println(rset.getInt("id") + ", " 
									+ rset.getString("title") + ", " 
									+ rset.getString("author") + ", " 
									+ rset.getInt("qty"));
				}
			}
			System.out.println("\nWould you like to:\n " 
					 + "\n1. Search for another book in the Library."
					 + "\n2. Go to the Main Menu." 
					 + "\n0. Exit the program");
			int searchMore = scSearch.nextInt();
			if (searchMore == 1) {
				searchBook();
			}
			else if (searchMore == 2) {
				mainSystem();
			}
			else {
				System.out.println("Thanks for browsing Karan's Library, see you soon!\nGoodbye!");
			}
			scSearch.close();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}