package lab6scdd;
public class t1 {
    class JointBankAccount {
        private int balance = 50000; // Initial balance
        public synchronized void withdraw(String user, int amount) {
            System.out.println(user + " is trying to withdraw: " + amount);
            if (amount <= balance) {
                System.out.println(user + " has successfully withdrawn: " + amount);
                balance -= amount;
                System.out.println("Remaining balance: " + balance);
            } else {
                System.out.println(user + " could not withdraw: " + amount + ". Insufficient funds.");
            }
        }
        public synchronized int getBalance() {
            return balance;
        } }
    class User extends Thread {
        private JointBankAccount account;
        private String userName;
        private int amount;
        public User(JointBankAccount account, String userName, int amount) {
            this.account = account;
            this.userName = userName;
            this.amount = amount;
        }
        @Override
        public void run() {
            account.withdraw(userName, amount);
        }
    }
    public static void main(String[] args) {
        JointBankAccount jointAccount = simulation.new JointBankAccount();
        User userA = simulation.new User(jointAccount, "Muqadas", 45000);
        User userB = simulation.new User(jointAccount, "DUA", 20000);
        userA.start();
        userB.start();
        try {
            userA.join();
            userB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();      }
        System.out.println("Final balance: " + jointAccount.getBalance() + " Rs");
    }}