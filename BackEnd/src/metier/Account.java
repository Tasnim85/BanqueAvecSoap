package metier;

import java.util.Date;

public class Account {
    private String code;
    private Float solde;

    // Default constructor
    public Account() {
        // Default initialization if needed
    }

    public Account(String code, Float solde) {
        this.code = code;
        this.solde = solde;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Float getSolde() {
        return solde;
    }

    public void setSolde(Float solde) {
        this.solde = solde;
    }
}