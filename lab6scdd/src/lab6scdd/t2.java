package lab6scdd;
public class t2 {
    class Printer {
        private int pagesInTray = 10; // Initial pages in the printer tray
        public synchronized void printPages(int pages) throws InterruptedException {
            while (pages > pagesInTray) {
                System.out.println("Not enough pages in the tray to print " + pages + " pages. Waiting for pages to be added...");
                wait(); // Wait until notified
            }
            pagesInTray -= pages;
            System.out.println("Printing " + pages + " pages. Remaining pages in tray: " + pagesInTray);
        }
        public synchronized void addPages(int pages) {
            pagesInTray += pages;
            System.out.println("Added " + pages + " pages to the tray. Total pages in tray: " + pagesInTray);
            notify(); // Notify waiting threads
        }
    }
    class PrintJob extends Thread {
        private Printer printer;
        private int pagesToPrint;
        public PrintJob(Printer printer, int pagesToPrint) {
            this.printer = printer;
            this.pagesToPrint = pagesToPrint;
        }
        @Override
        public void run() {
            try {
                printer.printPages(pagesToPrint);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    class PageAdder extends Thread {
        private Printer printer;
        public PageAdder(Printer printer) {
            this.printer = printer;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(2000); // Wait for 2 seconds before adding pages
                printer.addPages(10); // Add 10 pages to the tray
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        t2 simulation = new t2(); // Create an instance of the outer class
        Printer printer = simulation.new Printer();
        PrintJob printJob = simulation.new PrintJob(printer, 15);
        printJob.start();
        PageAdder pageAdder = simulation.new PageAdder(printer);
        pageAdder.start();
        try {
            printJob.join();
            pageAdder.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Printing job completed.");
    }
}