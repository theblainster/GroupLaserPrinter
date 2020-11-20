import java.util.*;

public class PrintQueue implements ISimAssembly {
    private LinkedList<Document> documents = new LinkedList<Document>();

    public void setValue(int newValue) {};
    public int  getValue()             {return documents.size();}

    public Document nextDoc() {
        return documents.peek();
    }

    public void reportQueue() {
        if (documents.size() != 0) {
            System.out.println("\n\n ---Print Queue---");
            for (int i = 0; i < documents.size(); i++) {
                System.out.println("\n");
                System.out.println("    Document ID: " + documents.get(i).getJobId());
                System.out.println("  Document Name: " + documents.get(i).getName());
                System.out.println("Number of Pages: " + documents.get(i).getPageCount());
            }
            System.out.println("\n\n ---End of Print Queue---");
        }
    }

    public void cancelJob() {documents.remove();}

    public void cancelJob(int id) {
        documents.remove(id);
    }

    public void clearQueue() {
        documents.clear();
    }

    public void addJob(String name, int pageCount) {
        documents.add(new Document(documents.size(), name, pageCount));
    }

    public void printJob() throws Exception {
        if (documents.size() != 0) {
            documents.pop();
        }
        else {
            throw new Exception("No jobs in queue to print.");
        }
    }
}
