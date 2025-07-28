import React, { useEffect, useState } from "react";
import { CreditCard } from "lucide-react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

// Funkcje pomocnicze
const formatCurrency = (value: number) => {
  return new Intl.NumberFormat("pl-PL", {
    style: "currency",
    currency: "PLN",
  }).format(value);
};

const formatAccountNumber = (number: string) => {
  return number.replace(/(\d{4})(?=\d)/g, "$1 ");
};

// Typy danych
interface Account {
  id: number;
  type: string;
  balance: number;
  accountNumber: string;
}

interface Transaction {
  id: number;
  amount: number;
  date: string;
  description: string;
  accountId: number;
}

interface DashboardProps {
  user: {
    name: string;
    email: string;
  };
  onTransfer: () => void;
  onViewTransactions: () => void;
  onViewStats?: () => void;
  onViewAccounts?: () => void;
  onViewProfile?: () => void;
}

const Dashboard: React.FC<DashboardProps> = ({
  user,
  onTransfer,
  onViewTransactions,
  onViewStats,
  onViewAccounts,
  onViewProfile,
}) => {
  const [accounts, setAccounts] = useState<Account[]>([]);
  const [transactions, setTransactions] = useState<Transaction[]>([]);
  const [showBalance, setShowBalance] = useState(true);

  // Pobieranie danych z backendu
  useEffect(() => {
    const token = localStorage.getItem("jwtToken");

    const fetchAccounts = async () => {
      try {
        const res = await fetch("http://localhost:8080/api/accounts/user", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (!res.ok) throw new Error("Nie udało się pobrać kont");

        const data = await res.json();
        setAccounts(data);
      } catch (err) {
        console.error("❌ Błąd ładowania kont:", err);
      }
    };

    const fetchTransactions = async () => {
      try {
        const res = await fetch("http://localhost:8080/api/transactions/user", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (!res.ok) throw new Error("Nie udało się pobrać transakcji");

        const data = await res.json();
        setTransactions(data);
      } catch (err) {
        console.error("❌ Błąd ładowania transakcji:", err);
      }
    };

    fetchAccounts();
    fetchTransactions();
  }, []);

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold">Witaj, {user.name}!</h1>

      <div className="flex justify-between items-center">
        <h2 className="text-xl font-semibold">Twoje konta</h2>
        <button
          onClick={() => setShowBalance(!showBalance)}
          className="text-sm text-blue-500"
        >
          {showBalance ? "Ukryj salda" : "Pokaż salda"}
        </button>
      </div>

      {accounts.length === 0 ? (
        <p className="text-muted-foreground">Brak kont do wyświetlenia.</p>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {accounts.map((account) => (
            <Card key={account.id} className="shadow-card hover:shadow-hover transition-shadow">
              <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                <CardTitle className="text-sm font-medium">
                  {account.type}
                </CardTitle>
                <CreditCard className="h-4 w-4 text-muted-foreground" />
              </CardHeader>
              <CardContent>
                <div className="text-2xl font-bold">
                  {showBalance ? formatCurrency(account.balance) : "••••••"}
                </div>
                <p className="text-xs text-muted-foreground mt-1">
                  {formatAccountNumber(account.accountNumber)}
                </p>
              </CardContent>
            </Card>
          ))}
        </div>
      )}
    </div>
  );
};

export default Dashboard;
