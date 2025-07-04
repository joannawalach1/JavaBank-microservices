import { useState } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Progress } from "@/components/ui/progress";
import { 
  CreditCard, 
  TrendingUp, 
  TrendingDown, 
  Eye,
  EyeOff,
  MoreHorizontal,
  Settings,
  Copy,
  QrCode
} from "lucide-react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { useToast } from "@/hooks/use-toast";

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

interface AccountOverviewProps {
  accounts: Account[];
  showBalance: boolean;
  onToggleBalance: () => void;
  onManageAccount: (accountId: string) => void;
}

export function AccountOverview({ 
  accounts, 
  showBalance, 
  onToggleBalance, 
  onManageAccount 
}: AccountOverviewProps) {
  const { toast } = useToast();

  const formatCurrency = (amount: number, currency = 'PLN') => {
    return new Intl.NumberFormat('pl-PL', {
      style: 'currency',
      currency: currency
    }).format(amount);
  };

  const formatAccountNumber = (number: string) => {
    return number.replace(/(\d{4})/g, '$1 ').trim();
  };

  const copyAccountNumber = (accountNumber: string) => {
    navigator.clipboard.writeText(accountNumber);
    toast({
      title: "Copied",
      description: "Account number copied to clipboard",
    });
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case "active": return "bg-success/10 text-success border-success/20";
      case "frozen": return "bg-warning/10 text-warning border-warning/20";
      case "closed": return "bg-destructive/10 text-destructive border-destructive/20";
      default: return "bg-muted";
    }
  };

  const getAccountIcon = (type: string) => {
    switch (type.toLowerCase()) {
      case "credit card":
      case "credit":
        return <CreditCard className="h-5 w-5 text-primary" />;
      default:
        return <CreditCard className="h-5 w-5 text-primary" />;
    }
  };

  const calculateUsagePercentage = (used: number, limit: number) => {
    return Math.min((used / limit) * 100, 100);
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h3 className="text-lg font-semibold">Account Overview</h3>
          <p className="text-sm text-muted-foreground">Manage your banking accounts</p>
        </div>
        <Button
          variant="outline"
          size="sm"
          onClick={onToggleBalance}
          className="flex items-center space-x-2"
        >
          {showBalance ? <Eye className="h-4 w-4" /> : <EyeOff className="h-4 w-4" />}
          <span>{showBalance ? "Hide" : "Show"} Balances</span>
        </Button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {accounts.map((account) => (
          <Card key={account.id} className="shadow-card hover:shadow-hover transition-all duration-300 group">
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-3">
              <div className="flex items-center space-x-3">
                <div className="bg-gradient-primary p-2 rounded-lg">
                  {getAccountIcon(account.type)}
                </div>
                <div>
                  <CardTitle className="text-base font-medium">
                    {account.type}
                  </CardTitle>
                  <Badge className={getStatusColor(account.status)} variant="outline">
                    {account.status}
                  </Badge>
                </div>
              </div>
              
              <DropdownMenu>
                <DropdownMenuTrigger asChild>
                  <Button variant="ghost" size="sm" className="opacity-0 group-hover:opacity-100 transition-opacity">
                    <MoreHorizontal className="h-4 w-4" />
                  </Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent align="end">
                  <DropdownMenuItem onClick={() => onManageAccount(account.id)}>
                    <Settings className="mr-2 h-4 w-4" />
                    Manage Account
                  </DropdownMenuItem>
                  <DropdownMenuItem onClick={() => copyAccountNumber(account.accountNumber)}>
                    <Copy className="mr-2 h-4 w-4" />
                    Copy Account Number
                  </DropdownMenuItem>
                  <DropdownMenuItem>
                    <QrCode className="mr-2 h-4 w-4" />
                    Show QR Code
                  </DropdownMenuItem>
                </DropdownMenuContent>
              </DropdownMenu>
            </CardHeader>
            
            <CardContent className="space-y-4">
              {/* Balance */}
              <div>
                <p className="text-sm text-muted-foreground mb-1">Current Balance</p>
                <p className="text-2xl font-bold">
                  {showBalance ? formatCurrency(account.balance, account.currency) : "••••••"}
                </p>
              </div>

              {/* Account Number */}
              <div>
                <p className="text-sm text-muted-foreground mb-1">Account Number</p>
                <p className="font-mono text-sm">{formatAccountNumber(account.accountNumber)}</p>
              </div>

              {/* Credit Limit (for credit accounts) */}
              {account.creditLimit && (
                <div className="space-y-2">
                  <div className="flex justify-between items-center">
                    <p className="text-sm text-muted-foreground">Credit Limit</p>
                    <p className="text-sm font-medium">
                      {showBalance ? formatCurrency(account.creditLimit) : "••••••"}
                    </p>
                  </div>
                  <Progress 
                    value={calculateUsagePercentage(account.balance, account.creditLimit)} 
                    className="h-2"
                  />
                  <p className="text-xs text-muted-foreground">
                    {showBalance ? 
                      `${formatCurrency(account.creditLimit - account.balance)} available` : 
                      "•••••• available"
                    }
                  </p>
                </div>
              )}

              {/* Monthly Spending (for accounts with limits) */}
              {account.monthlyLimit && account.usedThisMonth !== undefined && (
                <div className="space-y-2">
                  <div className="flex justify-between items-center">
                    <p className="text-sm text-muted-foreground">This Month</p>
                    <p className="text-sm font-medium">
                      {showBalance ? 
                        `${formatCurrency(account.usedThisMonth)} / ${formatCurrency(account.monthlyLimit)}` : 
                        "•••••• / ••••••"
                      }
                    </p>
                  </div>
                  <Progress 
                    value={calculateUsagePercentage(account.usedThisMonth, account.monthlyLimit)} 
                    className="h-2"
                  />
                </div>
              )}

              {/* Interest Rate */}
              {account.interestRate && (
                <div className="flex justify-between items-center">
                  <p className="text-sm text-muted-foreground">Interest Rate</p>
                  <div className="flex items-center space-x-1">
                    <TrendingUp className="h-3 w-3 text-success" />
                    <p className="text-sm font-medium text-success">
                      {account.interestRate}%
                    </p>
                  </div>
                </div>
              )}
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
}