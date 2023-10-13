public class User {
    private Integer ID;
    private String name;
    private int balance;
    private String transferType;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferType() {
        return transferType;
    }

    public void printConsole() {
        System.out.format("\nID: %d\nName: %s\nBalance: %d\n", ID, name, balance);
    }
}
