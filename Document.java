public class Document {
	private int pageCount = 0;
	private String name;
	
	public Document(String n, int p) {
		name = n;
		pageCount = p;
	}
	
	public int getPageCount() {
		return pageCount;
	}
	
	public String getName() {
		return name;
	}
	
}