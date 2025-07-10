import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { 
  Send, 
  Receipt, 
  CreditCard, 
  Smartphone, 
  PiggyBank, 
  Calculator,
  FileText,
  Users,
  Zap
} from "lucide-react";

interface QuickActionsProps {
  onTransfer: () => void;
  onViewTransactions: () => void;
  onPayBills: () => void;
  onMobileTopUp: () => void;
  onCreateSavings: () => void;
  onLoanCalculator: () => void;
  onStatements: () => void;
  onManagePayees: () => void;
}

export function QuickActions({
  onTransfer,
  onViewTransactions,
  onPayBills,
  onMobileTopUp,
  onCreateSavings,
  onLoanCalculator,
  onStatements,
  onManagePayees
}: QuickActionsProps) {

  const quickActions = [
    {
      icon: Send,
      title: "Transfer Money",
      description: "Send money to accounts",
      action: onTransfer,
      variant: "banking" as const
    },
    {
      icon: Receipt,
      title: "View Transactions",
      description: "See transaction history",
      action: onViewTransactions,
      variant: "outline" as const
    },
    {
      icon: CreditCard,
      title: "Pay Bills",
      description: "Pay utilities & services",
      action: onPayBills,
      variant: "outline" as const
    },
    {
      icon: Smartphone,
      title: "Mobile Top-up",
      description: "Recharge phone credit",
      action: onMobileTopUp,
      variant: "outline" as const
    },
    {
      icon: PiggyBank,
      title: "Create Savings",
      description: "Start saving money",
      action: onCreateSavings,
      variant: "success" as const
    },
    {
      icon: Calculator,
      title: "Loan Calculator",
      description: "Calculate loan payments",
      action: onLoanCalculator,
      variant: "outline" as const
    },
    {
      icon: FileText,
      title: "Statements",
      description: "Download statements",
      action: onStatements,
      variant: "outline" as const
    },
    {
      icon: Users,
      title: "Manage Payees",
      description: "Add/edit recipients",
      action: onManagePayees,
      variant: "outline" as const
    }
  ];

  return (
    <Card className="shadow-card">
      <CardHeader>
        <CardTitle className="flex items-center">
          <Zap className="mr-2 h-5 w-5 text-primary" />
          Quick Actions
        </CardTitle>
        <CardDescription>Frequently used banking services</CardDescription>
      </CardHeader>
      <CardContent>
        <div className="grid grid-cols-2 md:grid-cols-4 gap-3">
          {quickActions.map((action, index) => (
            <Button
              key={index}
              variant={action.variant}
              className="h-auto p-4 flex flex-col items-center space-y-2 hover-scale transition-all duration-200"
              onClick={action.action}
            >
              <action.icon className="h-6 w-6" />
              <div className="text-center">
                <p className="font-medium text-sm">{action.title}</p>
                <p className="text-xs opacity-80 hidden md:block">{action.description}</p>
              </div>
            </Button>
          ))}
        </div>
      </CardContent>
    </Card>
  );
}