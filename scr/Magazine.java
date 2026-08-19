public class Magazine extends  LibraryItem implements Renewable {
    protected double numIssue;

    public Magazine(int catalogueId,Member members, String title, ItemStatus item, double numIssue, int loadPeriod, double fine_bay) {
        super(catalogueId,members ,title, item, loadPeriod, fine_bay);
        this.numIssue = numIssue;
        catalogue[catalogueCount]=this;
       catalogueCount --;

    }
    public double getNumIssue() {
        return numIssue;
    }

    public void setNumIssue(double numIssue) {
        this.numIssue = numIssue;
    }

    @Override
    public double Receive_fine() {
        if (item != ItemStatus.ON_LOAN && loadPeriod >= 7)
            return 0;
        payment=addFine() * loadPeriod;
        return payment;
    }

    @Override
    public boolean Renew_loan(LibraryItem library) {
        if (Receive_fine() == 0 && !members.Record_Borrowing(library))
            return false;
        members.Record_Borrowing(library);
        numRenewal++;
        return true;
    }


}