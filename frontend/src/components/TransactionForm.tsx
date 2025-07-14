import { useState } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Textarea } from "@/components/ui/textarea";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Badge } from "@/components/ui/badge";
import { 
  Send, 
  Receipt, 
  CreditCard, 
  Smartphone,
  Zap,
  DollarSign,
  Calendar,
  User
} from "lucide-react";
import { useToast } from "@/hooks/use-toast";

interface Account {
  id: string;
  type: string;
  balance: number;
  accountNumber: string;
}

interface TransactionFormProps {
  accounts: Account[];
  onTransaction: (type: string, data: any) => void;
  onBack: () => void;
}

export function TransactionForm({ accounts, onTransaction, onBack }: TransactionFormProps) {
  const [activeTab, setActiveTab] = useState("transfer");
  const { toast } = useToast();

  // Transfer Form
  const [transferData, setTransferData] = useState({
    fromAccount: "",
    toAccount: "",
    amount: "",
    description: "",
    transferType: "internal" // internal, external, international
  });

  // Payment Form
  const [paymentData, setPaymentData] = useState({
    fromAccount: "",
    paymentType: "utility", // utility, credit-card, loan, subscription
    amount: "",
    recipient: "",
    description: "",
    dueDate: ""
  });

  // Mobile Top-up Form
  const [topUpData, setTopUpData] = useState({
    fromAccount: "",
    phoneNumber: "",
    amount: "",
    operator: ""
  });

  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('pl-PL', {
      style: 'currency',
      currency: 'PLN'
    }).format(amount);
  };

  const handleTransfer = (e: React.FormEvent) => {
    e.preventDefault();
    if (!transferData.fromAccount || !transferData.toAccount || !transferData.amount) {
      toast({
        title: "Error",
        description: "Please fill in all required fields",
        variant: "destructive",
      });
      return;
    }

    onTransaction("transfer", transferData);
    toast({
      title: "Transfer Initiated",
      description: `Transfer of ${formatCurrency(parseFloat(transferData.amount))} has been initiated`,
    });
    
    // Reset form
    setTransferData({
      fromAccount: "",
      toAccount: "",
      amount: "",
      description: "",
      transferType: "internal"
    });
  };

  const handlePayment = (e: React.FormEvent) => {
    e.preventDefault();
    if (!paymentData.fromAccount || !paymentData.amount || !paymentData.recipient) {
      toast({
        title: "Error",
        description: "Please fill in all required fields",
        variant: "destructive",
      });
      return;
    }

    onTransaction("payment", paymentData);
    toast({
      title: "Payment Scheduled",
      description: `Payment of ${formatCurrency(parseFloat(paymentData.amount))} has been scheduled`,
    });
  };

  const handleTopUp = (e: React.FormEvent) => {
    e.preventDefault();
    if (!topUpData.fromAccount || !topUpData.phoneNumber || !topUpData.amount) {
      toast({
        title: "Error",
        description: "Please fill in all required fields",
        variant: "destructive",
      });
      return;
    }

    onTransaction("topup", topUpData);
    toast({
      title: "Top-up Successful",
      description: `Mobile top-up of ${formatCurrency(parseFloat(topUpData.amount))} completed`,
    });
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h2 className="text-2xl font-bold text-foreground">New Transaction</h2>
          <p className="text-muted-foreground">Choose the type of transaction you want to make</p>
        </div>
        <Button variant="outline" onClick={onBack}>
          Back to Dashboard
        </Button>
      </div>

      <Tabs value={activeTab} onValueChange={setActiveTab} className="space-y-6">
        <TabsList className="grid w-full grid-cols-4">
          <TabsTrigger value="transfer" className="flex items-center space-x-2">
            <Send className="h-4 w-4" />
            <span>Transfer</span>
          </TabsTrigger>
          <TabsTrigger value="payment" className="flex items-center space-x-2">
            <Receipt className="h-4 w-4" />
            <span>Payment</span>
          </TabsTrigger>
          <TabsTrigger value="topup" className="flex items-center space-x-2">
            <Smartphone className="h-4 w-4" />
            <span>Top-up</span>
          </TabsTrigger>
          <TabsTrigger value="other" className="flex items-center space-x-2">
            <Zap className="h-4 w-4" />
            <span>Other</span>
          </TabsTrigger>
        </TabsList>

        {/* Transfer Tab */}
        <TabsContent value="transfer">
          <Card className="shadow-card">
            <CardHeader>
              <CardTitle className="flex items-center">
                <Send className="mr-2 h-5 w-5" />
                Money Transfer
              </CardTitle>
              <CardDescription>Transfer money between accounts or to external recipients</CardDescription>
            </CardHeader>
            <CardContent>
              <form onSubmit={handleTransfer} className="space-y-6">
                <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                  <div className="space-y-2">
                    <Label>Transfer Type</Label>
                    <Select value={transferData.transferType} onValueChange={(value) => 
                      setTransferData({...transferData, transferType: value})
                    }>
                      <SelectTrigger>
                        <SelectValue />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="internal">Internal Transfer</SelectItem>
                        <SelectItem value="external">External Transfer</SelectItem>
                        <SelectItem value="international">International Transfer</SelectItem>
                      </SelectContent>
                    </Select>
                  </div>

                  <div className="space-y-2">
                    <Label>From Account</Label>
                    <Select value={transferData.fromAccount} onValueChange={(value) => 
                      setTransferData({...transferData, fromAccount: value})
                    }>
                      <SelectTrigger>
                        <SelectValue placeholder="Select source account" />
                      </SelectTrigger>
                      <SelectContent>
                        {accounts.map((account) => (
                          <SelectItem key={account.id} value={account.id}>
                            <div className="flex justify-between items-center w-full">
                              <span>{account.type}</span>
                              <Badge variant="outline" className="ml-2">
                                {formatCurrency(account.balance)}
                              </Badge>
                            </div>
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>

                  <div className="space-y-2">
                    <Label>To Account</Label>
                    {transferData.transferType === "internal" ? (
                      <Select value={transferData.toAccount} onValueChange={(value) => 
                        setTransferData({...transferData, toAccount: value})
                      }>
                        <SelectTrigger>
                          <SelectValue placeholder="Select destination account" />
                        </SelectTrigger>
                        <SelectContent>
                          {accounts.filter(acc => acc.id !== transferData.fromAccount).map((account) => (
                            <SelectItem key={account.id} value={account.id}>
                              {account.type}
                            </SelectItem>
                          ))}
                        </SelectContent>
                      </Select>
                    ) : (
                      <Input
                        placeholder="Enter account number or email"
                        value={transferData.toAccount}
                        onChange={(e) => setTransferData({...transferData, toAccount: e.target.value})}
                      />
                    )}
                  </div>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div className="space-y-2">
                    <Label>Amount (PLN)</Label>
                    <div className="relative">
                      <DollarSign className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                      <Input
                        type="number"
                        step="0.01"
                        min="0.01"
                        placeholder="0.00"
                        value={transferData.amount}
                        onChange={(e) => setTransferData({...transferData, amount: e.target.value})}
                        className="pl-10"
                        required
                      />
                    </div>
                  </div>

                  <div className="space-y-2">
                    <Label>Description (Optional)</Label>
                    <Input
                      placeholder="Transfer purpose"
                      value={transferData.description}
                      onChange={(e) => setTransferData({...transferData, description: e.target.value})}
                    />
                  </div>
                </div>

                <Button type="submit" variant="banking" className="w-full" size="lg">
                  <Send className="mr-2 h-4 w-4" />
                  Transfer {transferData.amount && formatCurrency(parseFloat(transferData.amount))}
                </Button>
              </form>
            </CardContent>
          </Card>
        </TabsContent>

        {/* Payment Tab */}
        <TabsContent value="payment">
          <Card className="shadow-card">
            <CardHeader>
              <CardTitle className="flex items-center">
                <Receipt className="mr-2 h-5 w-5" />
                Bill Payment
              </CardTitle>
              <CardDescription>Pay bills and recurring payments</CardDescription>
            </CardHeader>
            <CardContent>
              <form onSubmit={handlePayment} className="space-y-6">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div className="space-y-2">
                    <Label>Payment Type</Label>
                    <Select value={paymentData.paymentType} onValueChange={(value) => 
                      setPaymentData({...paymentData, paymentType: value})
                    }>
                      <SelectTrigger>
                        <SelectValue />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="utility">Utility Bill</SelectItem>
                        <SelectItem value="credit-card">Credit Card</SelectItem>
                        <SelectItem value="loan">Loan Payment</SelectItem>
                        <SelectItem value="subscription">Subscription</SelectItem>
                        <SelectItem value="insurance">Insurance</SelectItem>
                      </SelectContent>
                    </Select>
                  </div>

                  <div className="space-y-2">
                    <Label>From Account</Label>
                    <Select value={paymentData.fromAccount} onValueChange={(value) => 
                      setPaymentData({...paymentData, fromAccount: value})
                    }>
                      <SelectTrigger>
                        <SelectValue placeholder="Select account" />
                      </SelectTrigger>
                      <SelectContent>
                        {accounts.map((account) => (
                          <SelectItem key={account.id} value={account.id}>
                            {account.type} - {formatCurrency(account.balance)}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div className="space-y-2">
                    <Label>Recipient</Label>
                    <div className="relative">
                      <User className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                      <Input
                        placeholder="Company or service name"
                        value={paymentData.recipient}
                        onChange={(e) => setPaymentData({...paymentData, recipient: e.target.value})}
                        className="pl-10"
                        required
                      />
                    </div>
                  </div>

                  <div className="space-y-2">
                    <Label>Amount (PLN)</Label>
                    <div className="relative">
                      <DollarSign className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                      <Input
                        type="number"
                        step="0.01"
                        min="0.01"
                        placeholder="0.00"
                        value={paymentData.amount}
                        onChange={(e) => setPaymentData({...paymentData, amount: e.target.value})}
                        className="pl-10"
                        required
                      />
                    </div>
                  </div>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div className="space-y-2">
                    <Label>Due Date (Optional)</Label>
                    <div className="relative">
                      <Calendar className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                      <Input
                        type="date"
                        value={paymentData.dueDate}
                        onChange={(e) => setPaymentData({...paymentData, dueDate: e.target.value})}
                        className="pl-10"
                      />
                    </div>
                  </div>

                  <div className="space-y-2">
                    <Label>Description</Label>
                    <Input
                      placeholder="Payment reference"
                      value={paymentData.description}
                      onChange={(e) => setPaymentData({...paymentData, description: e.target.value})}
                    />
                  </div>
                </div>

                <Button type="submit" variant="banking" className="w-full" size="lg">
                  <Receipt className="mr-2 h-4 w-4" />
                  Schedule Payment
                </Button>
              </form>
            </CardContent>
          </Card>
        </TabsContent>

        {/* Mobile Top-up Tab */}
        <TabsContent value="topup">
          <Card className="shadow-card">
            <CardHeader>
              <CardTitle className="flex items-center">
                <Smartphone className="mr-2 h-5 w-5" />
                Mobile Top-up
              </CardTitle>
              <CardDescription>Recharge mobile phone credit</CardDescription>
            </CardHeader>
            <CardContent>
              <form onSubmit={handleTopUp} className="space-y-6">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div className="space-y-2">
                    <Label>From Account</Label>
                    <Select value={topUpData.fromAccount} onValueChange={(value) => 
                      setTopUpData({...topUpData, fromAccount: value})
                    }>
                      <SelectTrigger>
                        <SelectValue placeholder="Select account" />
                      </SelectTrigger>
                      <SelectContent>
                        {accounts.map((account) => (
                          <SelectItem key={account.id} value={account.id}>
                            {account.type} - {formatCurrency(account.balance)}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>

                  <div className="space-y-2">
                    <Label>Operator</Label>
                    <Select value={topUpData.operator} onValueChange={(value) => 
                      setTopUpData({...topUpData, operator: value})
                    }>
                      <SelectTrigger>
                        <SelectValue placeholder="Select operator" />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="plus">Plus</SelectItem>
                        <SelectItem value="orange">Orange</SelectItem>
                        <SelectItem value="play">Play</SelectItem>
                        <SelectItem value="tmobile">T-Mobile</SelectItem>
                      </SelectContent>
                    </Select>
                  </div>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div className="space-y-2">
                    <Label>Phone Number</Label>
                    <Input
                      placeholder="+48 xxx xxx xxx"
                      value={topUpData.phoneNumber}
                      onChange={(e) => setTopUpData({...topUpData, phoneNumber: e.target.value})}
                      required
                    />
                  </div>

                  <div className="space-y-2">
                    <Label>Amount (PLN)</Label>
                    <Select value={topUpData.amount} onValueChange={(value) => 
                      setTopUpData({...topUpData, amount: value})
                    }>
                      <SelectTrigger>
                        <SelectValue placeholder="Select amount" />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="10">10 PLN</SelectItem>
                        <SelectItem value="20">20 PLN</SelectItem>
                        <SelectItem value="30">30 PLN</SelectItem>
                        <SelectItem value="50">50 PLN</SelectItem>
                        <SelectItem value="100">100 PLN</SelectItem>
                      </SelectContent>
                    </Select>
                  </div>
                </div>

                <Button type="submit" variant="banking" className="w-full" size="lg">
                  <Smartphone className="mr-2 h-4 w-4" />
                  Top-up Mobile
                </Button>
              </form>
            </CardContent>
          </Card>
        </TabsContent>

        {/* Other Tab */}
        <TabsContent value="other">
          <Card className="shadow-card">
            <CardHeader>
              <CardTitle className="flex items-center">
                <Zap className="mr-2 h-5 w-5" />
                Other Services
              </CardTitle>
              <CardDescription>Additional banking services</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <Button variant="outline" className="h-20 flex-col space-y-2">
                  <CreditCard className="h-6 w-6" />
                  <span>Request New Card</span>
                </Button>
                <Button variant="outline" className="h-20 flex-col space-y-2">
                  <Calendar className="h-6 w-6" />
                  <span>Schedule Transfer</span>
                </Button>
                <Button variant="outline" className="h-20 flex-col space-y-2">
                  <Receipt className="h-6 w-6" />
                  <span>Request Statement</span>
                </Button>
                <Button variant="outline" className="h-20 flex-col space-y-2">
                  <User className="h-6 w-6" />
                  <span>Update Profile</span>
                </Button>
              </div>
            </CardContent>
          </Card>
        </TabsContent>
      </Tabs>
    </div>
  );
}
