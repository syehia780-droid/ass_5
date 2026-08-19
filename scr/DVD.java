public class DVD extends LibraryItem{
    public DVD(){}
    public DVD(int catalogueId ,String title,Member members, ItemStatus item, int loadPeriod, double fine_bay) {
        super(catalogueId,members,title, item, loadPeriod, fine_bay);
        catalogue[catalogueCount]=this;
        catalogueCount --;
    }

}
