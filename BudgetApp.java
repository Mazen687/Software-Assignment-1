import java.util.ArrayList;
import java.util.Scanner;

public class BudgetApp {
    static double balance = 0;
    static ArrayList<String> names = new ArrayList<>();
    static ArrayList<Double> prices = new ArrayList<>();
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args)
    {
        System.out.print("Enter starting budget: ");
        balance = input.nextDouble();

        while (true)
        {
            showMenu();
        }
    }

    public static void showMenu()
    {
        System.out.println("\nBalance: $" + balance);
        System.out.println("1. Add Expense");
        System.out.println("2. View History");
        System.out.println("3. Remove Last");
        System.out.println("4. Exit");
        
        int choice = input.nextInt();

        switch (choice)
        {
            case 1:
                addExpense();
                break;
            case 2:
                viewHistory();
                break;
            case 3:
                removeLast();
                break;
            case 4:
                System.exit(0);
        }
    }

    public static void addExpense()
    {
        System.out.print("Item name: ");
        input.nextLine(); 
        String item = input.nextLine();

        System.out.print("Cost: ");
        double cost = input.nextDouble();

        balance -= cost;
        names.add(item);
        prices.add(cost);
    }

    public static void viewHistory()
    {
        for (int i = 0; i < names.size(); i++)
        {
            System.out.println(names.get(i) + ": $" + prices.get(i));
        }
    }

    public static void removeLast()
    {
        if (!names.isEmpty())
        {
            int lastIndex = names.size() - 1;
            
            double refund = prices.get(lastIndex);
            balance += refund;
            
            names.remove(lastIndex);
            prices.remove(lastIndex);
            
            System.out.println("Last item removed and money added back.");
        }
        else
        {
            System.out.println("History is empty.");
        }
    }
}