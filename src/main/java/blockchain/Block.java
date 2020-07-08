package blockchain;

import app.Constants;
import app.CryptographyHelper;
import crypto.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Block {
    private int id;
    private int nonce;
    private long timeStamp;
    private String hash;
    private String previousHash;
    public List<Transaction> transactions;

    public Block(String previousHash ) {
        this.transactions = new ArrayList<Transaction>();
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        generateHash();
    }

    public void generateHash() {
        String dataToHash = Integer.toString(id)+previousHash+Long.toString(timeStamp)+transactions.toString()+Integer.toString(nonce);
        String hashValue = CryptographyHelper.generateHash(dataToHash);
        this.hash = hashValue;
    }

    public boolean addTransaction(Transaction transaction) {

        if(transaction == null) return false;

        //if the block is the genesis block we do not process
        if((!previousHash.equals(Constants.GENESIS_PREV_HASH))) {
            if((!transaction.verifyTransaction())) {
                System.out.println("Transaction is not valid...");
                return false;
            }
        }

        transactions.add(transaction);
        System.out.println("Transaction is valid and it's added to the block "+this);
        return true;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public void incrementNonce() {
        this.nonce++;
    }
}
