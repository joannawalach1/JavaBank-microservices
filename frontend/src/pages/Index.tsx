import { useState } from "react";
import { Header } from "@/components/Header";
import { LoginForm } from "@/components/LoginForm";
import { Dashboard } from "@/components/Dashboard";
import { TransferForm } from "@/components/TransferForm";
import { TransactionHistory } from "@/components/TransactionHistory";
import { AccountOverview } from "@/components/AccountOverview";
import { QuickActions } from "@/components/QuickActions";
import { BankingStats } from "@/components/BankingStats";
import { UserProfile } from "@/components/UserProfile";
import { AccountDetails } from "@/components/AccountDetails";
import { TransactionForm } from "@/components/TransactionForm";
import { Button } from "@/components/ui/button";
import { useToast } from "@/hooks/use-toast";

interface User {
  id: string;
  name: string;
  email: string;
}

interface Account {
  id: string;
  type: string;
  balance: number;
  accountNumber: string;
  currency: string;
  status: "active" | "frozen" | "closed";
  creditLimit?: number;
  interestRate?: number;
  monthlyLimit?: number;
  usedThisMonth?: number;
}

interface Transaction {
  id: string;
  type: "credit" | "debit";
  amount: number;
  description: string;
  date: string;
  category?: string;
  accountName?: string;
  status: "completed" | "pending" | "failed";
}

const Index = () => {
  const [user, setUser] = useState<User | null>(null);
  const [currentView, setCurrentView] = useState<"dashboard" | "transfer" | "transactions" | "accounts" | "stats" | "profile" | "account-details" | "new-transaction">("dashboard");
  const [showBalances, setShowBalances] = useState(true);
  const [selectedAccountId, setSelectedAccountId] = useState<string | null>(null);
  const { toast } = useToast();

  // Mock data - in real app, this would come from your Java backend
  const [accounts, setAccounts] = useState<Account[]>([
    {
      id: "1",
      type: "Checking Account",
      balance: 15420.50,
      accountNumber: "1234567890123456",
      currency: "PLN",
      status: "active",
      monthlyLimit: 5000,
      usedThisMonth: 2340.75
    },
    {
      id: "2", 
      type: "Savings Account",
      balance: 45890.25,
      accountNumber: "1234567890123457",
      currency: "PLN",
      status: "active",
      interestRate: 3.5
    },
    {
      id: "3",
      type: "Business Account", 
      balance: 8750.00,
      accountNumber: "1234567890123458",
      currency: "PLN",
      status: "active"
    },
    {
      id: "4",
      type: "Credit Card",
      balance: -2150.00,
      accountNumber: "1234567890123459",
      currency: "PLN", 
      status: "active",
      creditLimit: 10000
    }
  ]);

  const [transactions, setTransactions] = useState<Transaction[]>([
    {
      id: "1",
      type: "credit",
      amount: 2500.00,
      description: "Salary Deposit",
      date: "2024-01-15",
      category: "Income",
      accountName: "Checking Account",
      status: "completed"
    },
    {
      id: "2",
      type: "debit", 
      amount: 450.75,
      description: "Online Shopping",
      date: "2024-01-14",
      category: "Shopping",
      accountName: "Credit Card",
      status: "completed"
    },
    {
      id: "3",
      type: "debit",
      amount: 120.00,
      description: "Utility Bill Payment",
      date: "2024-01-13",
      category: "Bills",
      accountName: "Checking Account",
      status: "completed"
    },
    {
      id: "4",
      type: "credit",
      amount: 75.50,
      description: "Cashback Reward",
      date: "2024-01-12",
      category: "Rewards",
      accountName: "Credit Card",
      status: "completed"
    },
    {
      id: "5",
      type: "debit",
      amount: 89.99,
      description: "Subscription Payment",
      date: "2024-01-11",
      category: "Entertainment",
      accountName: "Checking Account", 
      status: "completed"
    },
    {
      id: "6",
      type: "debit",
      amount: 1200.00,
      description: "Rent Payment",
      date: "2024-01-10",
      category: "Housing",
      accountName: "Checking Account",
      status: "completed"
    },
    {
      id: "7",
      type: "credit",
      amount: 150.00,
      description: "Freelance Payment",
      date: "2024-01-09",
      category: "Income",
      accountName: "Business Account",
      status: "pending"
    }
  ]);
const handleLogin = async (username: string, password: string) => {
  try {
    console.log("🔐 Próba logowania...");

    // 1. Logowanie — pobranie tokena
    const response = await fetch('http://localhost:8080/api/users/login', {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username,
        password,
      }),
    });

    console.log("📡 Odpowiedź z loginu:", response.status);

    if (!response.ok) {
      const errText = await response.text();
      console.error("❌ Błąd logowania:", errText);
      throw new Error("Invalid credentials");
    }

    const data = await response.json();
    console.log("✅ Token odebrany:", data.token);
    const token = data.token;

    // 2. Zapisz token JWT w localStorage
    localStorage.setItem("jwtToken", token);

    // 3. Pobierz profil użytkownika
    console.log("📥 Pobieranie profilu z tokenem...");
    const profileResponse = await fetch('http://localhost:8080/api/users/profile', {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`,
      },
    });

    console.log("📡 Odpowiedź z profilu:", profileResponse.status);

    if (!profileResponse.ok) {
      const errText = await profileResponse.text();
      console.error("❌ Błąd pobierania profilu:", errText);
      throw new Error("Failed to fetch user profile");
    }

    const userData = await profileResponse.json();
    console.log("👤 Dane użytkownika:", userData);

    // 4. Ustaw dane użytkownika w stanie
    setUser({
      id: userData.id,
      name: userData.name,   // uwaga na "name" a nie "username"
      email: userData.email,
    });

    // 5. Pokaż powiadomienie
    toast({
      title: "Login Successful",
      description: `Welcome back, ${userData.name}!`,
    });

  } catch (error: any) {
    console.error("💥 Błąd:", error.message);
    toast({
      title: "Login Failed",
      description: error.message || "Something went wrong",
      variant: "destructive",
    });
  }
};




const handleRegister = async (username: string, email: string, password: string) => {
  try {
    const response = await fetch("http://localhost:8080/api/users/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, email, password }),
    });

    if (!response.ok) {
      const errorText = await response.text();
      console.error("❌ Błąd rejestracji (status != 2xx):", errorText);
      return;
    }

    const data = await response.json();
    console.log("✅ Użytkownik zarejestrowany:", data);

    const mockUser: User = {
      id: data.id ?? "user_new", // Zakładamy, że backend zwraca ID
      name: username,
      email: email,
    };

    setUser(mockUser); // Zakładając, że masz setUser ze stanu

    toast({
      title: "Rejestracja udana",
      description: "Zaloguj się aby kontynuować",
    });

    // Możesz też od razu zalogować lub przekierować na login
    setCurrentView("dashboard");

  } catch (error) {
    console.error("❌ Błąd rejestracji (fetch):", error);
    toast({
      title: "Błąd rejestracji",
      description: "Nie udało się połączyć z serwerem.",
      variant: "destructive",
    });
  }
};

const handleLogout = () => {
  setUser(null);
  setCurrentView("dashboard"); // Lub 'login', w zależności od logiki
  toast({
    title: "Logged Out",
    description: "You have been successfully logged out.",
  });
};

  const handleTransfer = (fromAccountId: string, toAccountId: string, amount: number, description: string) => {
    // Simulate API call to your Java backend
    setAccounts(prev => prev.map(account => {
      if (account.id === fromAccountId) {
        return { ...account, balance: account.balance - amount };
      }
      if (account.id === toAccountId) {
        return { ...account, balance: account.balance + amount };
      }
      return account;
    }));

    // Add transaction records
    const newTransactions: Transaction[] = [
      {
        id: `trans_${Date.now()}_1`,
        type: "debit",
        amount: amount,
        description: `Transfer: ${description || 'Internal Transfer'}`,
        date: new Date().toISOString().split('T')[0],
        status: "completed",
        category: "Transfer",
        accountName: accounts.find(acc => acc.id === fromAccountId)?.type
      },
      {
        id: `trans_${Date.now()}_2`, 
        type: "credit",
        amount: amount,
        description: `Transfer received: ${description || 'Internal Transfer'}`,
        date: new Date().toISOString().split('T')[0],
        status: "completed",
        category: "Transfer",
        accountName: accounts.find(acc => acc.id === toAccountId)?.type
      }
    ];

    setTransactions(prev => [...newTransactions, ...prev]);
    setCurrentView("dashboard");
  };

  if (!user) {
    return <LoginForm onLogin={handleLogin} onRegister={handleRegister} />;
  }

  return (
    <div 
      className="min-h-screen relative"
      style={{
        backgroundColor: '#1d4ed8',
        background: 'linear-gradient(135deg, #1e3a8a 0%, #3b82f6 25%, #1d4ed8 50%, #2563eb 75%, #1e40af 100%)',
        minHeight: '100vh'
      }}
    >
      <Header user={user} onLogout={handleLogout} />
      
      <main className="container mx-auto px-4 py-8 relative z-10">
        {/* Cards with better contrast for blue background */}
        {currentView === "dashboard" && (
          <Dashboard
            user={user}
            accounts={accounts}
            transactions={transactions}
            onTransfer={() => setCurrentView("transfer")}
            onViewTransactions={() => setCurrentView("transactions")}
            onViewStats={() => setCurrentView("stats")}
            onViewAccounts={() => setCurrentView("accounts")}
            onViewProfile={() => setCurrentView("profile")}
          />
        )}
        
        {currentView === "transfer" && (
          <TransferForm
            accounts={accounts}
            onTransfer={handleTransfer}
            onBack={() => setCurrentView("dashboard")}
          />
        )}
        
        {currentView === "transactions" && (
          <TransactionHistory
            transactions={transactions}
            onBack={() => setCurrentView("dashboard")}
          />
        )}

        {currentView === "accounts" && (
          <AccountOverview
            accounts={accounts}
            showBalance={showBalances}
            onToggleBalance={() => setShowBalances(!showBalances)}
            onManageAccount={(accountId) => {
              setSelectedAccountId(accountId);
              setCurrentView("account-details");
            }}
          />
        )}

        {currentView === "stats" && (
          <div className="space-y-6">
            <div className="flex items-center justify-between">
              <div>
                <h2 className="text-2xl font-bold text-foreground">Financial Statistics</h2>
                <p className="text-muted-foreground">Your financial overview and insights</p>
              </div>
              <Button variant="outline" onClick={() => setCurrentView("dashboard")}>
                Back to Dashboard
              </Button>
            </div>
            
            <BankingStats
              monthlyIncome={6500}
              monthlyExpenses={4200}
              savingsGoal={50000}
              currentSavings={45890.25}
              creditUtilization={2150}
              creditLimit={10000}
              showBalances={showBalances}
            />
          </div>
        )}

        {currentView === "profile" && (
          <UserProfile
            user={{
              ...user!,
              phone: "+48 123 456 789",
              address: "ul. Przykładowa 123, 00-001 Warszawa",
              dateOfBirth: "1990-01-01",
              accountStatus: "active",
              memberSince: "2020-01-15"
            }}
            onUpdateProfile={(data) => {
              toast({
                title: "Profile Updated",
                description: "Your profile has been successfully updated.",
              });
            }}
            onBack={() => setCurrentView("dashboard")}
          />
        )}

        {currentView === "account-details" && selectedAccountId && (
          <AccountDetails
            account={{
              ...accounts.find(acc => acc.id === selectedAccountId)!,
              openDate: "2020-01-15",
              lastActivity: "2024-01-15"
            }}
            transactions={transactions.filter(t => t.accountName === accounts.find(acc => acc.id === selectedAccountId)?.type)}
            onBack={() => setCurrentView("accounts")}
            onManageAccount={() => {
              toast({
                title: "Account Management",
                description: "Account management features coming soon!",
              });
            }}
          />
        )}

        {currentView === "new-transaction" && (
          <TransactionForm
            accounts={accounts}
            onTransaction={(type, data) => {
              // Handle different transaction types
              if (type === "transfer") {
                handleTransfer(data.fromAccount, data.toAccount, parseFloat(data.amount), data.description);
              } else {
                toast({
                  title: "Transaction Processed",
                  description: `${type} transaction has been processed successfully.`,
                });
              }
              setCurrentView("dashboard");
            }}
            onBack={() => setCurrentView("dashboard")}
          />
        )}
      </main>
    </div>
  );
};

export default Index;