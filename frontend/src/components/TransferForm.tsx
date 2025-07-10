import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Textarea } from "@/components/ui/textarea";
import { ArrowRight, Send } from "lucide-react";
import { useToast } from "@/hooks/use-toast";

interface Account {
  id: string;
  type: string;
  balance: number;
  accountNumber: string;
}

interface TransferFormProps {
  accounts: Account[];
  onTransfer: (from: string, to: string, amount: number, description: string) => void;
  onBack: () => void;
}

export function TransferForm({ accounts, onTransfer, onBack }: TransferFormProps) {
  const [fromAccount, setFromAccount] = useState("");
  const [toAccount, setToAccount] = useState("");
  const [amount, setAmount] = useState("");
  const [description, setDescription] = useState("");
  const { toast } = useToast();

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!fromAccount || !toAccount || !amount) {
      toast({
        title: "Error",
        description: "Please fill in all required fields",
        variant: "destructive",
      });
      return;
    }

    if (fromAccount === toAccount) {
      toast({
        title: "Error",
        description: "Cannot transfer to the same account",
        variant: "destructive",
      });
      return;
    }

    const transferAmount = parseFloat(amount);
    const sourceAccount = accounts.find(acc => acc.id === fromAccount);
    
    if (sourceAccount && transferAmount > sourceAccount.balance) {
      toast({
        title: "Insufficient Funds",
        description: "The transfer amount exceeds your account balance",
        variant: "destructive",
      });
      return;
    }

    onTransfer(fromAccount, toAccount, transferAmount, description);
    
    toast({
      title: "Transfer Successful",
      description: `Successfully transferred ${new Intl.NumberFormat('pl-PL', {
        style: 'currency',
        currency: 'PLN'
      }).format(transferAmount)}`,
    });

    // Reset form
    setFromAccount("");
    setToAccount("");
    setAmount("");
    setDescription("");
  };

  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('pl-PL', {
      style: 'currency',
      currency: 'PLN'
    }).format(amount);
  };

  const selectedFromAccount = accounts.find(acc => acc.id === fromAccount);

  return (
    <div className="max-w-2xl mx-auto space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h2 className="text-2xl font-bold text-foreground">Transfer Money</h2>
          <p className="text-muted-foreground">Send money between your accounts or to others</p>
        </div>
        <Button variant="outline" onClick={onBack}>
          Back to Dashboard
        </Button>
      </div>

      <Card className="shadow-card">
        <CardHeader>
          <CardTitle className="flex items-center">
            <Send className="mr-2 h-5 w-5" />
            New Transfer
          </CardTitle>
          <CardDescription>Fill in the transfer details below</CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-6">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              {/* From Account */}
              <div className="space-y-2">
                <Label htmlFor="from-account">From Account</Label>
                <Select value={fromAccount} onValueChange={setFromAccount}>
                  <SelectTrigger>
                    <SelectValue placeholder="Select source account" />
                  </SelectTrigger>
                  <SelectContent>
                    {accounts.map((account) => (
                      <SelectItem key={account.id} value={account.id}>
                        <div className="flex justify-between items-center w-full">
                          <span>{account.type}</span>
                          <span className="text-muted-foreground ml-2">
                            {formatCurrency(account.balance)}
                          </span>
                        </div>
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
                {selectedFromAccount && (
                  <p className="text-sm text-muted-foreground">
                    Available: {formatCurrency(selectedFromAccount.balance)}
                  </p>
                )}
              </div>

              {/* To Account */}
              <div className="space-y-2">
                <Label htmlFor="to-account">To Account</Label>
                <Select value={toAccount} onValueChange={setToAccount}>
                  <SelectTrigger>
                    <SelectValue placeholder="Select destination account" />
                  </SelectTrigger>
                  <SelectContent>
                    {accounts.map((account) => (
                      <SelectItem key={account.id} value={account.id}>
                        <div className="flex justify-between items-center w-full">
                          <span>{account.type}</span>
                          <span className="text-muted-foreground ml-2">
                            {account.accountNumber.slice(-4)}
                          </span>
                        </div>
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>
            </div>

            {/* Transfer Preview */}
            {fromAccount && toAccount && (
              <div className="bg-muted/50 p-4 rounded-lg border">
                <div className="flex items-center justify-center space-x-4">
                  <div className="text-center">
                    <p className="font-medium">
                      {accounts.find(acc => acc.id === fromAccount)?.type}
                    </p>
                    <p className="text-sm text-muted-foreground">From</p>
                  </div>
                  <ArrowRight className="h-5 w-5 text-primary" />
                  <div className="text-center">
                    <p className="font-medium">
                      {accounts.find(acc => acc.id === toAccount)?.type}
                    </p>
                    <p className="text-sm text-muted-foreground">To</p>
                  </div>
                </div>
              </div>
            )}

            {/* Amount */}
            <div className="space-y-2">
              <Label htmlFor="amount">Amount (PLN)</Label>
              <Input
                id="amount"
                type="number"
                step="0.01"
                min="0.01"
                placeholder="0.00"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
                className="text-lg"
                required
              />
            </div>

            {/* Description */}
            <div className="space-y-2">
              <Label htmlFor="description">Description (Optional)</Label>
              <Textarea
                id="description"
                placeholder="Add a note for this transfer..."
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                rows={3}
              />
            </div>

            <Button type="submit" variant="banking" className="w-full" size="lg">
              <Send className="mr-2 h-4 w-4" />
              Transfer {amount && `${formatCurrency(parseFloat(amount))}`}
            </Button>
          </form>
        </CardContent>
      </Card>
    </div>
  );
}