package Model.Exceptions;

public class duplicateDirector extends Throwable {
    public static void message(String r)
    {
        View.View.sent("Произошла исключительная ситуация:\n"+r);
    }
}
