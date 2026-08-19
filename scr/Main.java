
void main() {
    Scanner in = new Scanner(System.in);

    Library library = new Library();

    int choice;

    do {

        System.out.println("\n========= Library SYSTEM =========");
        System.out.println("1.Register member ");
        System.out.println("2.Borrow item");
        System.out.println("3.View catalogue ");
        System.out.println("4.Return item");
        System.out.println("5.Renew loan");
        System.out.println("6.Search item by ID");
        System.out.println("7. Show Customer Accounts");
        System.out.println("8. Exit");
        System.out.print("Choice : ");

        choice = in.nextInt();
        in.nextLine();

        switch (choice) {

            case 1:

                System.out.print("Full Name : ");
                String name = in.nextLine();

                System.out.print("National ID : ");
                int nationalId = in.nextInt();

                System.out.print("Your balance : ");
                double balance = in.nextDouble();

                System.out.println("1- Student");
                System.out.println("2- Staff");
                System.out.println("3- Public");
                System.out.print("Choice : ");

                int Choice = in.nextInt();
                in.nextLine();

                MembershipType type;

                if (Choice == 1)
                    type = MembershipType.Student;
                else if (Choice == 2)
                    type = MembershipType.Staff;
                else
                    type = MembershipType.Public;

                Member member =
                        new Member(name, nationalId, balance, type);

                if (library.addCustomer(member))
                    System.out.println("Member is  registered.");
                else
                    System.out.println("Member is not registered.");

                break;

            case 2:

                System.out.print("Customer National ID : ");
                nationalId = in.nextInt();

                Member members =
                        library.findMember(nationalId);

                if (members == null) {

                    System.out.println("Member is Not Found.");
                    break;
                }

                System.out.print(" Item ID : ");
                int itemId = in.nextInt();

                System.out.println("1- Book");
                System.out.println("2- Magazine");
                System.out.println("3- DVD");
                System.out.print("Choice : ");

                int typeChoice = in.nextInt();

                String author;
                int pageCount;
                int loadPeriod;

                System.out.print("  loadPeriod: ");
                loadPeriod = in.nextInt();
                if (typeChoice == 1) {
                    System.out.print("  Author: ");
                    author = in.nextLine();
                    System.out.print("  pageCount: ");
                    pageCount = in.nextInt();

                    ItemStatus item = ItemStatus.AVAILABLE;
                    Book b = new Book(itemId, members, "Book", item, author, pageCount, loadPeriod, 5);

                    library.addItem(b);
                } else if (typeChoice == 2) {

                    System.out.print("  numIssue: ");
                    int numIssue = in.nextInt();

                    ItemStatus item = ItemStatus.AVAILABLE;
                    Magazine m = new Magazine(itemId, members, "Magazine", item, numIssue, loadPeriod, 3);

                    library.addItem(m);
                } else if (typeChoice == 3) {


                    ItemStatus item = ItemStatus.AVAILABLE;
                    DVD d = new DVD(itemId, "DVD", members, item, loadPeriod, 15);

                    library.addItem(d);
                } else
                    System.out.println(" Invalid Choice ");

                break;

            case 3:
                LibraryItem libraryItem = new LibraryItem();
                library.printCatalogue(libraryItem);

            case 4:

                System.out.print("Item ID : ");
                int itemId1 = in.nextInt();

                LibraryItem libraryItem1 =
                        library.findItem(itemId1);

                if (libraryItem1 == null) {

                    System.out.println("Item is Not Found.");
                    break;
                }
                if (libraryItem1.overdueDay > 0) {
                    System.out.println("Overdue days " + libraryItem1.overdueDay);
                    System.out.println(libraryItem1.amount);
                } else if (libraryItem1.overdueDay == 0)
                    System.out.println(" not found ");
                else
                    System.out.println(" Invalid overdue days ");

                break;

            case 5:
                System.out.print("Item ID : ");
                int itemId2 = in.nextInt();

                LibraryItem libraryItem2 =
                        library.findItem(itemId2);

                if (libraryItem2 == null) {

                    System.out.println("Item is Not Found.");
                    break;
                }
                if (libraryItem2 instanceof Book)
                    if (libraryItem2.numRenewal < 2)
                        System.out.println("We have not reached the limit of renewal.");
                    else
                        System.out.println("We have reached the limit of renewal.");


                break;

            case 6:
                Member member1;
                System.out.print("Item ID : ");
                int itemId3 = in.nextInt();

                LibraryItem libraryItem3 =
                        library.findItem(itemId3);

                if (libraryItem3 == null) {

                    System.out.println("Item is Not Found.");
                    break;
                }
                libraryItem3.display();

                break;

            case 7:

                System.out.println("Enter status:");
                System.out.println("1. AVAILABLE");
                System.out.println("2. ON_LOAN");
                System.out.println("3. LOST");

                int statusChoice = in.nextInt();
                in.nextLine();

                ItemStatus status;

                if (statusChoice == 1) {
                    status = ItemStatus.AVAILABLE;
                } else if (statusChoice == 2) {
                    status = ItemStatus.ON_LOAN;
                } else if (statusChoice == 3) {
                    status = ItemStatus.LOST;
                } else {
                    System.out.println("Invalid status.");
                    break;
                }

                library.listByStatus(status);

                break;


            case 8:

                System.out.print("Membership ID: ");
                int memberId = in.nextInt();

                Member member4 = library.findMember(memberId);

                if (member4 == null) {
                    System.out.println("Member not found.");
                    break;
                }

                System.out.print("Payment amount: ");
                double payment = in.nextDouble();
                in.nextLine();

                if (payment <= 0) {
                    System.out.println("Payment must be positive.");
                    break;
                }

                if (payment > member4.librarys[member4.num].amount) {
                    System.out.println("Payment cannot be greater than balance owed.");
                    break;
                }

                if (member4.librarys[member4.num].payment()){
                    System.out.println("Payment successful.");
                }

                break;


            case 9:

                library.listAllMembers();

                break;


            case 10:

                System.out.println("\n========= LIBRARY REPORT =========");

                System.out.println(
                        "Catalogue size: "
                                + library.getCatalogueSize()
                );

                System.out.println(
                        "Items ever added: "
                                + library.getItemsEverAdded()
                );

                System.out.println(
                        "Items on loan: "
                                + library.getItemsOnLoan()
                );

                System.out.println(
                        "Loan rate: "
                                + library.getLoanRate() + "%"
                );

                System.out.println(
                        "Total outstanding: "
                                + library.getTotalOutstanding() + " EGP"
                );

                System.out.println(
                        "Projected fines for 5 days: "
                                + library.getProjectedFines(5) + " EGP"
                );

                break;


            case 11:

                System.out.print("Enter Item ID: ");
                String lostId = in.nextLine();

                library.markItemAsLost(lostId);

                break;


            case 0:

                System.out.println("Good Bye.");
                break;


            default:
                System.out.println("Invalid choice.");
                break;
        }

        } while (choice != 0);


}