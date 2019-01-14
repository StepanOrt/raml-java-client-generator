
package list.exceptions;


public class FooException
    extends RuntimeException
{

    private int statusCode;
    private String reason;
    private String messageBody;

    public FooException(int statusCode, String reason) {
        this.statusCode = statusCode;
        this.reason = reason;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getReason() {
        return this.reason;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getMessage() {
        String message = ((("Error response received:\nStatus: "+ this.statusCode)+"\nReason: ")+ this.reason);
        if (this.messageBody!= null) {
            message += ("\nBody:\n"+ this.messageBody);
        }
        return message;
    }

}
