import java.util.Scanner;

public  class LibraryItem {
    protected ItemStatus item;
    Scanner in=new Scanner((System.in));
    protected  int catalogueId;
    private String title;
    protected int catalogueCount = 10;
    protected byte numRenewal=0;
    protected  int loadPeriod;
    protected int overdueDay;
    protected double fine_bay;
    protected double amount;
    protected double payment;
    protected  Member members;
    protected LibraryItem[] catalogue =new LibraryItem[catalogueCount];

public LibraryItem(){}



    public LibraryItem(int catalogueId, Member members, String title, ItemStatus item, int loadPeriod, double fine_bay) {

        this.item = item;
        this.catalogueId=catalogueId;
        this.item = item;
        this.title = title;
        this.loadPeriod = loadPeriod;
        this.members=members;
        this.fine_bay=Math.max(0,fine_bay);

    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = Math.max(0,payment);
    }

    public int getCatalogueCount() {
        return catalogueCount;
    }

    public void setCatalogueCount(int catalogueCount) {
        this.catalogueCount = catalogueCount;
    }

    public LibraryItem[] getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(LibraryItem[] catalogue) {
        this.catalogue = catalogue;
    }

    public ItemStatus getItem() {
        return item;
    }

    public void setItem(ItemStatus item) {
        this.item = item;
    }

    public int getCatalogueId() {
        return catalogueId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte getNumRenewal() {
        return numRenewal;
    }

    public void setNumRenewal(byte numRenewal) {
        this.numRenewal = numRenewal;
    }

    public Member getMembers() {
        return members;
    }

    public void setMembers(Member members)
    {
        this.members = members;
    }
    public void setOverdueDay(LibraryItem libraryItem) {
        if(libraryItem instanceof Book)
            overdueDay=14-loadPeriod;
        else if (libraryItem instanceof Magazine)
            overdueDay=7-loadPeriod;
        else
            overdueDay=3-loadPeriod;
        if(overdueDay==0)
            item=ItemStatus.AVAILABLE;
    }
    public int getOverdueDay() {
        return overdueDay;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double addFine()
    {
        if(item==ItemStatus.ON_LOAN && overdueDay>0&&fine_bay>0) {
            if (members.type.equals(MembershipType.Student)) {
                fine_bay -= (fine_bay * 0.25);
                return fine_bay;
            } else if (members.type.equals(MembershipType.Staff)) {

                fine_bay -= (fine_bay * 0.10);
                return fine_bay;
            }
            return fine_bay;
        }

        return 0;
    }
    public boolean payment()
    {
        amount=addFine()*overdueDay;
        System.out.println("Please pay : "+payment);
       payment=in.nextDouble();
        if(addFine()==0&&payment==0&&payment> members.balance&&payment!=amount)
            return false;
       members.balance -=amount;
        return true;
    }
    public void display() {
        System.out.println("Name : " + title);
        System.out.println("Status : " + item);
        System.out.println("ID : " + catalogueId);

    }
}
