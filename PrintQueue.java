import java.util.*;

public class PrintQueue implements ISimAssembly {
    private Queue<Document> documents = new LinkedList<Document>();

    public void setValue(int newValue) {};
    public int  getValue()             {return documents.size();}

    public int nextDoc() {
        return documents.peek().getJobId();
    }

    public void reportQueue() {
        System.out.println(documents);
    }

    public void cancelJob(int id) {
        documents.remove(id);
    }

    public void clearQueue() {
        documents.clear();
    }

    public void addJob(String name, int pageCount) {
        documents.add(new Document(name, pageCount));
    }

    public void printJob() {
        documents.poll();
    }
}
