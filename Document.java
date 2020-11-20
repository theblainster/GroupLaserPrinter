public class Document {
	private 	   int    jobId;
	private        int    pageCount;
	private        String name;

	public Document(int id, String n, int p) {
		jobId	  = id;
		name 	  = n;
		pageCount = p;
	}
	
	public int getPageCount() {
		return pageCount;
	}
	
	public String getName() {
		return name;
	}

	public int getJobId() {return jobId;}
}