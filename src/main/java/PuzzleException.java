import java.util.ArrayList;
import java.util.List;

public class PuzzleException extends Exception{

    private List<String> errors =new ArrayList<String>();

    public PuzzleException(String message) {
        super(message);
        this.errors.add(message);
    }

    public List<String> getErrors() {
        return errors;
    }
}
