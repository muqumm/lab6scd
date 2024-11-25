package lab6scdd;

public class t1 {
    class JointBankAccount {
        private int balance = 50000; // Initial balance

        // Synchronized method to handle withdrawals safely
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

        // Getter for balance
        public synchronized int getBalance() {
            return balance;
        }
    }

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
    	
        t1 simulation = new t1(); // Create an instance of the outer class
        JointBankAccount jointAccount = simulation.new JointBankAccount();
        
        // User A tries to withdraw 45,000
        User userA = simulation.new User(jointAccount, "Muqadas", 45000);
        // User B tries to withdraw 20,000
        User userB = simulation.new User(jointAccount, "DUA", 20000);
        
        // Start both threads
        userA.start();
        userB.start();
        
        // Wait for both threads to finish
        try {
            userA.join();
            userB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Print final balance after both withdrawals
        System.out.println("Final balance: " + jointAccount.getBalance() + " Rs");
    }
}