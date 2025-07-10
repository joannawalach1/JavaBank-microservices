import { useState } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { 
  CreditCard, 
  TrendingUp, 
  TrendingDown, 
  Send, 
  Receipt, 
  Eye,
  EyeOff,
  ArrowUpRight,
  ArrowDownRight
} from "lucide-react";

interface Account {
  id: string;
  type: string;
  balance: number;
  accountNumber: string;
}

interface Transaction {
  id: string;
  type: "credit" | "debit";
  amount: number;
  description: string;
  date: string;
}

interface DashboardProps {
  user: {
    name: string;
    email: string;
  };
  accounts: Account[];
  transactions: Transaction[];
  onTransfer: () => void;
  onViewTransactions: () => void;
  onViewStats?: () => void;
  onViewAccounts?: () => void;
  onViewProfile?: () => void;
}

export function Dashboard({ user, accounts, transactions, onTransfer, onViewTransactions, onViewStats, onViewAccounts, onViewProfile }: DashboardProps) {
  const [showBalance, setShowBalance] = useState(true);

  const totalBalance = accounts.reduce((sum, account) => sum + account.balance, 0);

  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('pl-PL', {
      style: 'currency',
      currency: 'PLN'
    }).format(amount);
  };

  const formatAccountNumber = (number: string) => {
    return number.replace(/(\d{4})/g, '$1 ').trim();
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h2 className="text-2xl font-bold text-foreground">Welcome back, {user.name}</h2>
          <p className="text-muted-foreground">Here's your financial overview</p>
        </div>
            <div className="flex items-center space-x-4">
              <Button variant="outline" onClick={onViewProfile}>
                👤 Profile
              </Button>
              <Button variant="banking" onClick={onViewStats}>
                📊 Statistics
              </Button>
              <Button variant="outline" onClick={onViewAccounts}>
                🏦 Accounts
              </Button>
              <Button variant="banking" onClick={onTransfer}>
                <Send className="mr-2 h-4 w-4" />
                Transfer
              </Button>
              <Button variant="outline" onClick={onViewTransactions}>
                <Receipt className="mr-2 h-4 w-4" />
                Transactions
              </Button>
            </div>
      </div>

      {/* Total Balance Card */}
      <Card className="bg-gradient-primary text-primary-foreground shadow-hover">
        <CardHeader className="flex flex-row items-center justify-between">
          <div>
            <CardTitle className="text-primary-foreground/90">Total Balance</CardTitle>
            <CardDescription className="text-primary-foreground/70">
              All accounts combined
            </CardDescription>
          </div>
          <Button
            variant="ghost"
            size="sm"
            onClick={() => setShowBalance(!showBalance)}
            className="text-primary-foreground hover:bg-primary-foreground/10"
          >
            {showBalance ? <Eye className="h-4 w-4" /> : <EyeOff className="h-4 w-4" />}
          </Button>
        </CardHeader>
        <CardContent>
          <div className="text-3xl font-bold">
            {showBalance ? formatCurrency(totalBalance) : "••••••"}
          </div>
        </CardContent>
      </Card>

      {/* Accounts Grid */}
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

      {/* Recent Transactions */}
      <Card className="shadow-card">
        <CardHeader>
          <CardTitle className="flex items-center">
            <Receipt className="mr-2 h-5 w-5" />
            Recent Transactions
          </CardTitle>
          <CardDescription>Your latest banking activity</CardDescription>
        </CardHeader>
        <CardContent>
          <div className="space-y-4">
            {transactions.slice(0, 5).map((transaction) => (
              <div key={transaction.id} className="flex items-center justify-between p-3 rounded-lg bg-muted/50">
                <div className="flex items-center space-x-3">
                  <div className={`p-2 rounded-full ${
                    transaction.type === 'credit' 
                      ? 'bg-success/10 text-success' 
                      : 'bg-destructive/10 text-destructive'
                  }`}>
                    {transaction.type === 'credit' ? (
                      <ArrowDownRight className="h-4 w-4" />
                    ) : (
                      <ArrowUpRight className="h-4 w-4" />
                    )}
                  </div>
                  <div>
                    <p className="font-medium">{transaction.description}</p>
                    <p className="text-sm text-muted-foreground">{transaction.date}</p>
                  </div>
                </div>
                <div className="text-right">
                  <p className={`font-medium ${
                    transaction.type === 'credit' ? 'text-success' : 'text-destructive'
                  }`}>
                    {transaction.type === 'credit' ? '+' : '-'}{formatCurrency(transaction.amount)}
                  </p>
                  <Badge variant={transaction.type === 'credit' ? 'default' : 'secondary'} className="text-xs">
                    {transaction.type === 'credit' ? 'Income' : 'Expense'}
                  </Badge>
                </div>
              </div>
            ))}
          </div>
          {transactions.length > 5 && (
            <div className="mt-4 text-center">
              <Button variant="outline" onClick={onViewTransactions}>
                View All Transactions
              </Button>
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  );
}