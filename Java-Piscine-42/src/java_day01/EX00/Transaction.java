import java.util.UUID;

public class Transaction {
    private final UUID ID;
    private User sender;
    private User recipient;
    private int transferAmount;

    Transaction() {
        ID = UUID.randomUUID();
    }

    public UUID getUID() {
        return ID;
    }

    public User getSender() {
        return sender;
    }
    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }
    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public int getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(int transferAmount) {
        if (transferAmount >= 0) {
            if (sender.getBalance() < transferAmount){
                System.err.print("\nНе хватает средств для отправки " + transferAmount);
                System.exit(-1);
            }
            sender.setTransferType("OUTCOME");
            recipient.setTransferType("INCOME");
        } else {
            if (Math.abs(recipient.getBalance()) < Math.abs(transferAmount) || recipient.getBalance() < Math.abs(transferAmount)){
                System.err.print("\nНе хватает средств для отправки " + transferAmount);
                System.exit(-1);
            }
            sender.setTransferType("INCOME");
            recipient.setTransferType("OUTCOME");
        }

        sender.setBalance(sender.getBalance() - transferAmount);
        recipient.setBalance(recipient.getBalance() + transferAmount);

        this.transferAmount = transferAmount;
    }

    public void printConsole() {
        System.out.print("\nID: " + getUID() +
                "\nSender: " + getSender().getName() + " " + -1 * getTransferAmount() + " " + getSender().getTransferType() +
                "\nRecipient: " + getRecipient().getName() + " " + getTransferAmount() + " " + getRecipient().getTransferType() +
                "\n\n||SENDER||\n" + "ID: " + sender.getID() + "\nName: " + sender.getName() + "\nBalance: " + sender.getBalance() +
                "\n\n||RECIPIENT||\n" + "ID: " + recipient.getID() + "\nName: " + recipient.getName() + "\nBalance: " + recipient.getBalance() + "\n");
    }
}
