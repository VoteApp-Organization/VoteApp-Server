package pl.voteapp.exceptions;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class ApiSuccess {
    private HttpStatus status;
    private String message;
    private List<String> transactions;

    public ApiSuccess(HttpStatus status, String message, List<String> transactions) {
        super();
        this.status = status;
        this.message = message;
        this.transactions = transactions;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<String> transactions) {
        this.transactions = transactions;
    }
}
