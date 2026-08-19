public class Library {
    private Member[] members;
    private LibraryItem[] libraryItems;

    private int memberCount;
    private int libraryItemCount;

    public Library() {
        members = new Member[20];
        libraryItems = new LibraryItem[50];

        memberCount = 0;
        libraryItemCount = 0;
    }

    public boolean addCustomer(Member member) {

        for (int i = 0; i < memberCount; i++) {

            if (members[i].getId() == (member.getId()) && memberCount >= members.length) {

                System.out.println("National ID already exists and the member is Out of range ");
                return false;
            }
        }

        members[memberCount++] = member;

        return true;
    }

    public Member findMember(int nationalId) {

        for (int i = 0; i < memberCount; i++) {

            if (members[i].getId() == nationalId) {

                return members[i];
            }
        }

        return null;
    }


    public boolean addItem(LibraryItem libraryItem) {

        for (int i = 0; i < libraryItemCount; i++) {

            if (libraryItems[i].catalogueId == libraryItem.catalogueId && libraryItemCount >= libraryItems.length && libraryItem.catalogueCount <= 0) {

                System.out.println("Catalogue ID already exists,the item is Out of range and the catalogue is full");
                return false;
            }
        }

        libraryItems[libraryItemCount++] = libraryItem;

        libraryItem.getMembers().addItem(libraryItem);

        return true;
    }

    public LibraryItem findItem(int Id) {

        for (int i = 0; i < libraryItemCount; i++) {

            if (libraryItems[i].getCatalogueId() == Id) {

                return libraryItems[i];
            }
        }

        return null;
    }

    void printCatalogue(LibraryItem libraryItem) {
        System.out.println("• List the whole catalogue :");
        for (int i = 0; i < libraryItem.catalogueCount; i++) {
            System.out.println(libraryItem.catalogue[i].getTitle() + " " + libraryItem.catalogue[i].catalogueId + " " + libraryItem.catalogue[i].item + " " + libraryItem.catalogue[i].members.getId());
            if (libraryItem.catalogue[i] instanceof Book)
                System.out.println(" Loan period :14 days , Fine : 5 EGP / overdue day , Renewal : 2 times");
            if (libraryItem.catalogue[i] instanceof Magazine)
                System.out.println(" Loan period : 7 days , Fine : 3 EGP / overdue day , Renewal : 1 times");
            if (libraryItem.catalogue[i] instanceof DVD)
                System.out.println(" Loan period :3 days , Fine : 15 EGP / overdue day , Renewal : 0 times");

        }


    }
    void listByStatus(ItemStatus status)
    {
        for(int i=0;i<libraryItemCount;i++)
            if (libraryItems[i].item == status)
                libraryItems[i].display();
        System.out.println(" not found");

    }

}