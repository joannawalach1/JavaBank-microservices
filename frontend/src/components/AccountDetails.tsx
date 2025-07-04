import { useState } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Progress } from "@/components/ui/progress";
import { 
  CreditCard, 
  TrendingUp, 
  TrendingDown, 
  Eye,
  EyeOff,
  Download,
  Settings,
  BarChart3,
  Calendar,
  DollarSign,
  ArrowUpRight,
  ArrowDownRight,
  Clock
} from "lucide-react";

interface AccountDetailsProps {
  account: {
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
    openDate: string;
    lastActivity: string;
  };
  transactions: Array<{
    id: string;
    type: "credit" | "debit";
    amount: number;
    description: string;
    date: string;
    status: "completed" | "pending" | "failed";
  }>;
  onBack: () => void;
  onManageAccount: () => void;
}

export function AccountDetails({ account, transactions, onBack, onManageAccount }: AccountDetailsProps) {
  const [showBalance, setShowBalance] = useState(true);
  const [selectedPeriod, setSelectedPeriod] = useState("30d");

  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('pl-PL', {
      style: 'currency',
      currency: account.currency
    }).format(amount);
  };

  const formatAccountNumber = (number: string) => {
    return number.replace(/(\d{4})/g, '$1 ').trim();
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case "active": return "bg-success/10 text-success border-success/20";
      case "frozen": return "bg-warning/10 text-warning border-warning/20";
      case "closed": return "bg-destructive/10 text-destructive border-destructive/20";
      default: return "bg-muted";
    }
  };

  const recentTransactions = transactions.slice(0, 10);
  const totalIncome = transactions
    .filter(t => t.type === "credit" && t.status === "completed")
    .reduce((sum, t) => sum + t.amount, 0);
  const totalExpenses = transactions
    .filter(t => t.type === "debit" && t.status === "completed")
    .reduce((sum, t) => sum + t.amount, 0);

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h2 className="text-2xl font-bold text-foreground">Account Details</h2>
          <p className="text-muted-foreground">Detailed view of your {account.type}</p>
        </div>
        <div className="flex items-center space-x-2">
          <Button variant="outline" onClick={onManageAccount}>
            <Settings className="mr-2 h-4 w-4" />
            Manage
          </Button>
          <Button variant="outline" onClick={onBack}>
            Back
          </Button>
        </div>
      </div>

      {/* Account Overview */}
      <Card className="bg-gradient-primary text-primary-foreground shadow-hover">
        <CardHeader>
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-3">
              <div className="bg-primary-foreground/20 p-3 rounded-lg">
                <CreditCard className="h-6 w-6" />
              </div>
              <div>
                <CardTitle className="text-primary-foreground">{account.type}</CardTitle>
                <CardDescription className="text-primary-foreground/70">
                  {formatAccountNumber(account.accountNumber)}
                </CardDescription>
              </div>
            </div>
            <div className="flex items-center space-x-2">
              <Badge className={getStatusColor(account.status)} variant="outline">
                {account.status}
              </Badge>
              <Button
                variant="ghost"
                size="sm"
                onClick={() => setShowBalance(!showBalance)}
                className="text-primary-foreground hover:bg-primary-foreground/10"
              >
                {showBalance ? <Eye className="h-4 w-4" /> : <EyeOff className="h-4 w-4" />}
              </Button>
            </div>
          </div>
        </CardHeader>
        <CardContent>
          <div className="space-y-4">
            <div>
              <p className="text-primary-foreground/70 text-sm">Current Balance</p>
              <p className="text-3xl font-bold">
                {showBalance ? formatCurrency(account.balance) : "••••••"}
              </p>
            </div>
            
            {account.creditLimit && (
              <div className="space-y-2">
                <div className="flex justify-between text-sm">
                  <span className="text-primary-foreground/70">Credit Utilization</span>
                  <span className="text-primary-foreground">
                    {Math.round((Math.abs(account.balance) / account.creditLimit) * 100)}%
                  </span>
                </div>
                <Progress 
                  value={(Math.abs(account.balance) / account.creditLimit) * 100} 
                  className="h-2 bg-primary-foreground/20"
                />
              </div>
            )}
          </div>
        </CardContent>
      </Card>

      <Tabs defaultValue="overview" className="space-y-6">
        <TabsList className="grid w-full grid-cols-4">
          <TabsTrigger value="overview">Overview</TabsTrigger>
          <TabsTrigger value="transactions">Transactions</TabsTrigger>
          <TabsTrigger value="analytics">Analytics</TabsTrigger>
          <TabsTrigger value="settings">Settings</TabsTrigger>
        </TabsList>

        <TabsContent value="overview" className="space-y-6">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <Card className="shadow-card">
              <CardContent className="p-4">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm font-medium text-muted-foreground">Total Income</p>
                    <p className="text-2xl font-bold text-success">
                      {showBalance ? formatCurrency(totalIncome) : "••••••"}
                    </p>
                  </div>
                  <div className="p-3 rounded-full bg-success/10">
                    <TrendingUp className="h-5 w-5 text-success" />
                  </div>
                </div>
              </CardContent>
            </Card>

            <Card className="shadow-card">
              <CardContent className="p-4">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm font-medium text-muted-foreground">Total Expenses</p>
                    <p className="text-2xl font-bold text-destructive">
                      {showBalance ? formatCurrency(totalExpenses) : "••••••"}
                    </p>
                  </div>
                  <div className="p-3 rounded-full bg-destructive/10">
                    <TrendingDown className="h-5 w-5 text-destructive" />
                  </div>
                </div>
              </CardContent>
            </Card>

            <Card className="shadow-card">
              <CardContent className="p-4">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm font-medium text-muted-foreground">Net Flow</p>
                    <p className={`text-2xl font-bold ${totalIncome - totalExpenses >= 0 ? 'text-success' : 'text-destructive'}`}>
                      {showBalance ? formatCurrency(totalIncome - totalExpenses) : "••••••"}
                    </p>
                  </div>
                  <div className="p-3 rounded-full bg-primary/10">
                    <DollarSign className="h-5 w-5 text-primary" />
                  </div>
                </div>
              </CardContent>
            </Card>
          </div>

          {/* Account Information */}
          <Card className="shadow-card">
            <CardHeader>
              <CardTitle>Account Information</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="space-y-2">
                  <p className="text-sm font-medium text-muted-foreground">Account Number</p>
                  <p className="font-mono">{formatAccountNumber(account.accountNumber)}</p>
                </div>
                <div className="space-y-2">
                  <p className="text-sm font-medium text-muted-foreground">Account Type</p>
                  <p>{account.type}</p>
                </div>
                <div className="space-y-2">
                  <p className="text-sm font-medium text-muted-foreground">Open Date</p>
                  <p>{account.openDate}</p>
                </div>
                <div className="space-y-2">
                  <p className="text-sm font-medium text-muted-foreground">Last Activity</p>
                  <p>{account.lastActivity}</p>
                </div>
                {account.interestRate && (
                  <div className="space-y-2">
                    <p className="text-sm font-medium text-muted-foreground">Interest Rate</p>
                    <p className="text-success font-medium">{account.interestRate}% APY</p>
                  </div>
                )}
                {account.creditLimit && (
                  <div className="space-y-2">
                    <p className="text-sm font-medium text-muted-foreground">Credit Limit</p>
                    <p>{showBalance ? formatCurrency(account.creditLimit) : "••••••"}</p>
                  </div>
                )}
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="transactions" className="space-y-6">
          <Card className="shadow-card">
            <CardHeader>
              <CardTitle>Recent Transactions</CardTitle>
              <CardDescription>Latest activity on this account</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-3">
                {recentTransactions.map((transaction) => (
                  <div
                    key={transaction.id}
                    className="flex items-center justify-between p-3 rounded-lg border bg-card hover:bg-muted/50 transition-colors"
                  >
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
                      <p className={`font-semibold ${
                        transaction.type === 'credit' ? 'text-success' : 'text-destructive'
                      }`}>
                        {transaction.type === 'credit' ? '+' : '-'}{formatCurrency(transaction.amount)}
                      </p>
                      <Badge variant={transaction.status === 'completed' ? 'default' : 'secondary'} className="text-xs">
                        {transaction.status}
                      </Badge>
                    </div>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="analytics" className="space-y-6">
          <Card className="shadow-card">
            <CardHeader>
              <CardTitle className="flex items-center">
                <BarChart3 className="mr-2 h-5 w-5" />
                Account Analytics
              </CardTitle>
              <CardDescription>Financial insights and trends</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="text-center py-8">
                <BarChart3 className="h-16 w-16 text-muted-foreground mx-auto mb-4" />
                <p className="text-muted-foreground">Analytics charts will be displayed here</p>
                <p className="text-sm text-muted-foreground mt-2">
                  Integration with charting library needed
                </p>
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="settings" className="space-y-6">
          <Card className="shadow-card">
            <CardHeader>
              <CardTitle>Account Settings</CardTitle>
              <CardDescription>Manage your account preferences</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                <Button variant="outline" className="w-full justify-start">
                  <Download className="mr-2 h-4 w-4" />
                  Download Account Statement
                </Button>
                <Button variant="outline" className="w-full justify-start">
                  <Clock className="mr-2 h-4 w-4" />
                  Set Auto-Transfer
                </Button>
                <Button variant="outline" className="w-full justify-start">
                  <Calendar className="mr-2 h-4 w-4" />
                  Schedule Payment
                </Button>
              </div>
            </CardContent>
          </Card>
        </TabsContent>
      </Tabs>
    </div>
  );
}