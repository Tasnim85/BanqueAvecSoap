package service;

import DAO.AccountDAO;
import java.security.Key;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.sql.SQLException;
import metier.Account;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "BankService")
public class banque_service {

    public String CreateAccount(@WebParam(name = "code") String code, @WebParam(name = "solde") float solde) {
    try {
        AccountDAO.createAccount(code, solde);
        return "Account created successfully.";
    } catch (Exception e) {
        return "Error creating account: " + e.getMessage();
    }
}

    @WebMethod(operationName = "GetAccount")
    public Account getAccount(@WebParam(name = "code") String code) {
        return AccountDAO.getAccountByCode(code);
    }

    @WebMethod(operationName = "UpdateAccount")
public String updateAccount(@WebParam(name = "code") String code, @WebParam(name = "solde") Float solde) {
    try {
        AccountDAO.updateAccount(code, solde);
        return "Account updated successfully.";
    } catch (Exception e) {
        return "Error updating account: " + e.getMessage();
    }
}

    @WebMethod(operationName = "DeleteAccount")
    public String deleteAccount(@WebParam(name = "code") String code) {
        try {
            AccountDAO.deleteAccount(code);
            return "Account deleted successfully.";
        } catch (Exception e) {
            return "Error deleting account: " + e.getMessage();
        }
    }

    @WebMethod(operationName = "ConvertEuroToDinar")
    public double convertirEuroToDinar(@WebParam(name = "montant") double mnt) {
        return mnt * 3.34;
    }
    
    @WebMethod(operationName = "EncryptString")
    public String encryptString(@WebParam(name = "data") String data, @WebParam(name = "key") String key) {
        try {
            SecretKeySpec secretKey = generateSecretKey(key);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error encrypting string: " + e.getMessage();
        }
    }

    @WebMethod(operationName = "DecryptString")
    public String decryptString(@WebParam(name = "encryptedData") String encryptedData, @WebParam(name = "key") String key) {
        try {
            SecretKeySpec secretKey = generateSecretKey(key);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error decrypting string: " + e.getMessage();
        }
    }

    private SecretKeySpec generateSecretKey(String key) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(key.toCharArray(), "salt".getBytes(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    @WebMethod(operationName = "RetirerMontant")
    public boolean retirerMontant(@WebParam(name = "code") String code, @WebParam(name = "montant") Float mnt) {
    try {
        Account c = AccountDAO.getAccountByCode(code);
        if (c != null && mnt > 0 && mnt <= c.getSolde()) {
            c.setSolde(c.getSolde() - mnt);
            AccountDAO.updateAccount(code,c.getSolde());
            return true;
        } else {
            return false;
        }
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

    @WebMethod(operationName = "VerserMontant")
    public void verserMontant(@WebParam(name = "code") String code, @WebParam(name = "montant") Float mnt) {
    try {
        Account c = AccountDAO.getAccountByCode(code);
        if (c != null && mnt > 0) {
            c.setSolde(c.getSolde() + mnt);
            AccountDAO.updateAccount(code,c.getSolde());
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    @WebMethod(operationName = "AccountExists")
    public boolean accountExists(@WebParam(name = "code") String code) throws SQLException {
        return AccountDAO.accountExists(code);
    }
}
