public class Member {
    private String name;
    private  final int id;
    protected MembershipType type;
    protected double balance;
    private byte numItem;
    private int itemCount;
    protected int num=0;
    protected LibraryItem []librarys;



    public Member(String name,int id,double balance, MembershipType type) {
        this.name = name;
        this.id = id;
        this.balance = balance;
        this.type = type;
        numItem = 0;
        itemCount= 0;
        num++;
        librarys=new LibraryItem[num];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }


    public MembershipType getType() {
        return type;
    }

    public void setType(MembershipType type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public byte getNumItem() {
        return numItem;
    }

    public void setNumItem(byte numItem) {
        this.numItem = numItem;
    }

    public boolean isAllowedToBorrow(LibraryItem library)
    {
        if(library.item!=ItemStatus.AVAILABLE)
            return false;
        if(library instanceof Book && library.numRenewal>3) {
            library.item=ItemStatus.LOST;
            return false;
        }
        if(library instanceof Magazine && library.numRenewal>1) {
            library.item = ItemStatus.LOST;
            return false;
        }
        if(library instanceof Book && library.numRenewal>0)
        {
//            library.item=ItemStatus.LOST;
            return false;
        }


        return true;
    }
    public boolean Record_Borrowing(LibraryItem library)
    {
        if(isAllowedToBorrow(library)&&numItem<3&&balance<=100) {
            library.item=ItemStatus.ON_LOAN;

            librarys[numItem] = library;

            numItem++;

            return true;
        }
        library.item=ItemStatus.LOST;
            return false;

    }
    public boolean addItem(LibraryItem library) {

        if (itemCount < librarys.length) {
            librarys[itemCount++] = library;
            return true;
        }

        return false;
    }
    public void display() {
        System.out.println("Name : " + name);
        System.out.println("Status : " + type);
        System.out.println("ID : " + id);

        for (int i = 0; i < numItem; i++) {
            System.out.println("The number of renewal : " + librarys[i].getNumRenewal());
        }
    }





}
