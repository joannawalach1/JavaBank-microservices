import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Progress } from "@/components/ui/progress";
import { 
  TrendingUp, 
  TrendingDown, 
  DollarSign, 
  CreditCard,
  Target,
  Calendar
} from "lucide-react";

interface BankingStatsProps {
  monthlyIncome: number;
  monthlyExpenses: number;
  savingsGoal: number;
  currentSavings: number;
  creditUtilization: number;
  creditLimit: number;
  showBalances: boolean;
}

export function BankingStats({
  monthlyIncome,
  monthlyExpenses,
  savingsGoal,
  currentSavings,
  creditUtilization,
  creditLimit,
  showBalances
}: BankingStatsProps) {

  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('pl-PL', {
      style: 'currency',
      currency: 'PLN'
    }).format(amount);
  };

  const savingsProgress = (currentSavings / savingsGoal) * 100;
  const creditUsagePercentage = (creditUtilization / creditLimit) * 100;
  const netIncome = monthlyIncome - monthlyExpenses;

  const stats = [
    {
      title: "Monthly Income",
      value: monthlyIncome,
      icon: TrendingUp,
      color: "text-success",
      bgColor: "bg-success/10",
      change: "+12.5%",
      changeType: "positive"
    },
    {
      title: "Monthly Expenses", 
      value: monthlyExpenses,
      icon: TrendingDown,
      color: "text-destructive",
      bgColor: "bg-destructive/10",
      change: "-3.2%",
      changeType: "positive"
    },
    {
      title: "Net Income",
      value: netIncome,
      icon: DollarSign,
      color: netIncome > 0 ? "text-success" : "text-destructive",
      bgColor: netIncome > 0 ? "bg-success/10" : "bg-destructive/10",
      change: "+8.7%",
      changeType: netIncome > 0 ? "positive" : "negative"
    },
    {
      title: "Credit Available",
      value: creditLimit - creditUtilization,
      icon: CreditCard,
      color: "text-info",
      bgColor: "bg-info/10",
      change: "85% available",
      changeType: "neutral"
    }
  ];

  return (
    <div className="space-y-6">
      {/* Financial Overview Stats */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        {stats.map((stat, index) => (
          <Card key={index} className="shadow-card hover:shadow-hover transition-shadow">
            <CardContent className="p-4">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-sm font-medium text-muted-foreground">{stat.title}</p>
                  <p className="text-2xl font-bold">
                    {showBalances ? formatCurrency(stat.value) : "••••••"}
                  </p>
                  <p className={`text-xs mt-1 ${
                    stat.changeType === 'positive' ? 'text-success' : 
                    stat.changeType === 'negative' ? 'text-destructive' : 
                    'text-muted-foreground'
                  }`}>
                    {stat.change}
                  </p>
                </div>
                <div className={`p-3 rounded-full ${stat.bgColor}`}>
                  <stat.icon className={`h-6 w-6 ${stat.color}`} />
                </div>
              </div>
            </CardContent>
          </Card>
        ))}
      </div>

      {/* Progress Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {/* Savings Goal */}
        <Card className="shadow-card">
          <CardHeader>
            <CardTitle className="flex items-center">
              <Target className="mr-2 h-5 w-5 text-primary" />
              Savings Goal
            </CardTitle>
            <CardDescription>Track your savings progress</CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="flex justify-between items-center">
              <span className="text-sm font-medium">Current Progress</span>
              <span className="text-sm text-muted-foreground">
                {Math.round(savingsProgress)}%
              </span>
            </div>
            <Progress value={savingsProgress} className="h-3" />
            <div className="flex justify-between text-sm">
              <span className="text-muted-foreground">
                {showBalances ? formatCurrency(currentSavings) : "••••••"}
              </span>
              <span className="font-medium">
                {showBalances ? formatCurrency(savingsGoal) : "••••••"}
              </span>
            </div>
            {showBalances && (
              <p className="text-xs text-muted-foreground">
                {formatCurrency(savingsGoal - currentSavings)} remaining to reach your goal
              </p>
            )}
          </CardContent>
        </Card>

        {/* Credit Utilization */}
        <Card className="shadow-card">
          <CardHeader>
            <CardTitle className="flex items-center">
              <CreditCard className="mr-2 h-5 w-5 text-primary" />
              Credit Utilization
            </CardTitle>
            <CardDescription>Keep below 30% for good credit health</CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="flex justify-between items-center">
              <span className="text-sm font-medium">Usage</span>
              <span className={`text-sm font-medium ${
                creditUsagePercentage > 30 ? 'text-destructive' : 
                creditUsagePercentage > 20 ? 'text-warning' : 'text-success'
              }`}>
                {Math.round(creditUsagePercentage)}%
              </span>
            </div>
            <Progress 
              value={creditUsagePercentage} 
              className={`h-3 ${
                creditUsagePercentage > 30 ? '[&>div]:bg-destructive' : 
                creditUsagePercentage > 20 ? '[&>div]:bg-warning' : '[&>div]:bg-success'
              }`}
            />
            <div className="flex justify-between text-sm">
              <span className="text-muted-foreground">
                Used: {showBalances ? formatCurrency(creditUtilization) : "••••••"}
              </span>
              <span className="font-medium">
                Limit: {showBalances ? formatCurrency(creditLimit) : "••••••"}
              </span>
            </div>
            {creditUsagePercentage > 30 && (
              <p className="text-xs text-destructive">
                ⚠️ Consider paying down credit to improve your credit score
              </p>
            )}
          </CardContent>
        </Card>
      </div>
    </div>
  );
}