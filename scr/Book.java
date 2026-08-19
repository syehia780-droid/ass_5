public class Book extends LibraryItem implements Renewable  {
    protected String author;
    protected int pageCount;
public Book(){}
    public Book( int catalogueId, Member members,String title, ItemStatus item, String author, int pageCount,int loadPeriod, double fine_bay) {
        super( catalogueId,members ,title, item,loadPeriod,fine_bay);
        this.author = author;
        catalogue[catalogueCount]=this;
        catalogueCount --;
        this.pageCount = pageCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getNumIssue() {
        return pageCount;
    }

    public void setNumIssue(int numIssue) {
        this.pageCount = numIssue;
    }



    @Override
    public boolean Renew_loan(LibraryItem library) {

        if( amount!=0&&!members.Record_Borrowing(library))
            return false;
        members.Record_Borrowing(library);
        numRenewal++;
        return true;
    }
}
