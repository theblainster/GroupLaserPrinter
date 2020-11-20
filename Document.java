public class Document {
	private static int    jobId     = 0;
	private        int    pageCount = 0;
	private        String name;
	
	public Document(String n, int p) {
		jobId	  = ++jobId;
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