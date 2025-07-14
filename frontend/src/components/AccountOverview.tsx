import { useEffect, useState } from "react";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Progress } from "@/components/ui/progress";
import {
  CreditCard,
  TrendingUp,
  Eye,
  EyeOff,
  MoreHorizontal,
  Settings,
  Copy,
  QrCode,
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
  accountType: string;
  accountNumber: string;
  balance: number;
  currency: string;
  status: "ACTIVE" | "FROZEN" | "CLOSED";
  creditLimit?: number;
  interestRate?: number;
  monthlyLimit?: number;
  usedThisMonth?: number;
}

export function AccountOverview() {
  const [accounts, setAccounts] = useState<Account[]>([]);
  const [showBalance, setShowBalance] = useState(true);
  const { toast } = useToast();

  const formatCurrency = (amount: number, currency = "PLN") =>
    new Intl.NumberFormat("pl-PL", {
      style: "currency",
      currency,
    }).format(amount);

  const formatAccountNumber = (number: string) =>
    number.replace(/(\d{4})/g, "$1 ").trim();

  const copyAccountNumber = (accountNumber: string) => {
    navigator.clipboard.writeText(accountNumber);
    toast({
      title: "Skopiowano",
      description: "Numer konta został skopiowany do schowka",
    });
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case "ACTIVE":
        return "bg-green-100 text-green-800 border-green-300";
      case "FROZEN":
        return "bg-yellow-100 text-yellow-800 border-yellow-300";
      case "CLOSED":
        return "bg-red-100 text-red-800 border-red-300";
      default:
        return "bg-gray-200 text-gray-700 border-gray-300";
    }
  };

  const fetchAccounts = async () => {
    try {
      const token = localStorage.getItem("jwtToken");
      const res = await fetch("http://localhost:8080/api/accounts/user", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (!res.ok) throw new Error("Nie udało się pobrać kont");
      const data = await res.json();
      setAccounts(data);
      console.log("Dane kont:", data);
    } catch (error) {
      console.error("❌ Błąd ładowania kont:", error);
    }
  };

  useEffect(() => {
    fetchAccounts();
  }, []);

  const calculateUsagePercentage = (used: number, limit: number) =>
    Math.min((used / limit) * 100, 100);

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h2 className="text-xl font-bold">Przegląd kont</h2>
          <p className="text-sm text-muted-foreground">
            Wszystkie Twoje konta bankowe w jednym miejscu
          </p>
        </div>
        <Button
          variant="outline"
          size="sm"
          onClick={() => setShowBalance(!showBalance)}
        >
          {showBalance ? <Eye className="mr-2 h-4 w-4" /> : <EyeOff className="mr-2 h-4 w-4" />}
          {showBalance ? "Ukryj salda" : "Pokaż salda"}
        </Button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {accounts.map((account) => (
          <Card key={account.id} className="group shadow-md hover:shadow-lg transition-shadow">
            <CardHeader className="flex justify-between items-start space-y-0 pb-2">
              <div className="flex items-center space-x-3">
                <div className="p-2 rounded-md bg-blue-100">
                  <CreditCard className="text-blue-600 h-5 w-5" />
                </div>
                <div>
                  <CardTitle className="text-base font-semibold">
                    {account.accountType}
                  </CardTitle>
                  <Badge className={getStatusColor(account.status)} variant="outline">
                    {account.status}
                  </Badge>
                </div>
              </div>

              <DropdownMenu>
                <DropdownMenuTrigger asChild>
                  <Button variant="ghost" size="icon" className="opacity-0 group-hover:opacity-100">
                    <MoreHorizontal className="h-4 w-4" />
                  </Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent align="end">
                  <DropdownMenuItem onClick={() => copyAccountNumber(account.accountNumber)}>
                    <Copy className="mr-2 h-4 w-4" />
                    Kopiuj numer konta
                  </DropdownMenuItem>
                  <DropdownMenuItem>
                    <QrCode className="mr-2 h-4 w-4" />
                    Pokaż kod QR
                  </DropdownMenuItem>
                </DropdownMenuContent>
              </DropdownMenu>
            </CardHeader>

            <CardContent className="space-y-4">
              <div>
                <p className="text-sm text-muted-foreground mb-1">Saldo</p>
                <p className="text-2xl font-bold">
                  {showBalance
                    ? formatCurrency(account.balance, account.currency)
                    : "••••••"}
                </p>
              </div>

              <div>
                <p className="text-sm text-muted-foreground mb-1">Numer konta</p>
                <p className="font-mono text-sm">{formatAccountNumber(account.accountNumber)}</p>
              </div>

              {account.creditLimit && (
                <div>
                  <p className="text-sm text-muted-foreground mb-1">Limit kredytowy</p>
                  <Progress
                    value={calculateUsagePercentage(account.balance, account.creditLimit)}
                    className="h-2"
                  />
                  <p className="text-xs mt-1">
                    {showBalance
                      ? `${formatCurrency(account.creditLimit - account.balance)} dostępne`
                      : "•••••• dostępne"}
                  </p>
                </div>
              )}

              {account.interestRate && (
                <div className="flex justify-between text-sm text-muted-foreground">
                  <span>Oprocentowanie</span>
                  <span className="text-green-600 font-medium">
                    <TrendingUp className="inline-block h-4 w-4 mr-1" />
                    {account.interestRate}%
                  </span>
                </div>
              )}
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
}
