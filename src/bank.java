import java.util.Scanner;

interface Bankable {
    public abstract int deposit(int amount); // 입금 메서드
    public abstract int withdraw(int amount, String payment_title); // 출금 메서드
    public abstract void printBankInfo(); // 은행 정보 출력 메서드
    public abstract void printBankHistory(); // 은행 내역 출력 메서드
}

class printBankHistory {
    String use_history_str; // 어디서 결제
    int use_history_int; // 어디서 입출금했는지(ATM)
    printBankHistory(String use_history_str, int use_history_int){
        this.use_history_str = use_history_str;
        this.use_history_int = use_history_int;
    }
}

class Account implements Bankable {
    String account_nickname; // 계좌 별칭
    String account_number; // 계좌 번호
    String passwd; // 계좌 비밀번호
    String owner;// 계좌 주인
    int total_amount_used; // 계좌에서 사용한 금액들의 총 합계
    int total_deposit; // 계좌에 입금한 금액들의 총 합계
    int payment_no; // 계좌를 총 몇 번 결제 했는지.
    int deposit; // 계좌 잔액

    // 계좌 이용 내역
    printBankHistory[] history;

    //Account 클래스 초기화
    Account(String account_nickname, String account_number, String owner, String passwd) {
        this.account_nickname = account_nickname;
        this.account_number = account_number;
        this.owner = owner;
        this.passwd = passwd;
        this.total_amount_used = 0;
        this.total_deposit = 0;
        this.payment_no = 0;
        this.deposit = 0;
        this.history = new printBankHistory[100];
    }
    String getAccountNumber() { return this.account_number; }

    public int deposit(int amount) {
        this.deposit += amount;
        this.total_deposit += amount;
        this.history[this.payment_no] = new printBankHistory("입금", amount);
        this.payment_no++;
        return amount;
    }
    public int withdraw(int amount, String payment_title) {
        if(this.deposit < amount) {
            System.out.println("잔액이 부족합니다.");
            return 0;
        }
        this.deposit -= amount;
        this.total_amount_used += amount;
        this.history[this.payment_no] = new printBankHistory(payment_title, amount);
        this.payment_no++;
        return amount;
    }
    public void printBankInfo() {
        System.out.println("계좌 정보 출력 -----------------------------");
        System.out.printf("계좌 별칭 : %s\n", this.account_nickname);
        System.out.printf("계좌 번호 : %s\n", this.account_number);
        System.out.printf("계좌 주인 : %s\n", this.owner);
        System.out.printf("계좌 잔액 : %d원\n", this.deposit);
        System.out.printf("총 입금액 : %d원\n", this.total_deposit);
        System.out.printf("총 사용액 : %d원\n", this.total_amount_used);
        System.out.println("-------------------------------------------");
    }
    public void printBankHistory() {
        System.out.println("계좌 정보 출력(내역) -----------------------------");
        for(int i=0;i<this.payment_no; i++) {
            System.out.printf("사용내역(%d번째) : [%s][%d원]\n",
                    i+1,
                    this.history[i].use_history_str,
                    this.history[i].use_history_int);
        }
    }
}

class ISA_Account extends Account {
    int interest_rate; // 이자율
    int total_interest; // 총 이자액

    ISA_Account(String account_nickname, String account_number, String owner
            , String passwd, int interest_rate) {
        super(account_nickname, account_number, owner, passwd);
        this.interest_rate = interest_rate;
        this.total_interest = 0;
    }

    public int deposit(int amount) {
        this.deposit += amount;
        this.total_deposit += amount;
        this.total_interest += (int)(amount * (this.interest_rate / 100.0));
        this.history[this.payment_no] = new printBankHistory("입금", amount);
        this.payment_no++;
        return amount;
    }
    public int withdraw(int amount, String payment_title) {
        if(this.deposit < amount) {
            System.out.println("잔액이 부족합니다.");
            return 0;
        }
        this.deposit -= amount;
        this.total_amount_used += amount;
        this.history[this.payment_no] = new printBankHistory(payment_title, amount);
        this.payment_no++;
        return amount;
    }
    public void printBankInfo() {
        System.out.println("계좌 정보 출력 -----------------------------");
        System.out.printf("계좌 별칭 : %s\n", this.account_nickname);
        System.out.printf("계좌 번호 : %s\n", this.getAccountNumber());
        System.out.printf("계좌 주인 : %s\n", this.owner);
        System.out.printf("계좌 잔액 : %d원\n", this.deposit);
        System.out.printf("총 입금액 : %d원\n", this.total_deposit);
        System.out.printf("총 사용액 : %d원\n", this.total_amount_used);
        System.out.printf("총 이자액 : %d원\n", this.total_interest);
        System.out.println("-------------------------------------------");
    }
}

class saving_account extends Account {
    int interest_rate; // 이자율
    int total_interest; // 총 이자액

    saving_account(String account_nickname, String account_number, String owner
            , String passwd, int interest_rate) {
        super(account_nickname, account_number, owner, passwd);
        this.interest_rate = interest_rate;
        this.total_interest = 0;
    }

    public int deposit(int amount) {
        this.deposit += amount;
        this.total_deposit += amount;
        this.total_interest += (int)(amount * (this.interest_rate / 100.0));
        this.history[this.payment_no] = new printBankHistory("입금", amount);
        this.payment_no++;
        return amount;
    }
    public int withdraw(int amount, String payment_title) {
        if(this.deposit < amount) {
            System.out.println("잔액이 부족합니다.");
            return 0;
        }
        this.deposit -= amount;
        this.total_amount_used += amount;
        this.history[this.payment_no] = new printBankHistory(payment_title, amount);
        this.payment_no++;
        return amount;
    }
    public void printBankInfo() {
        System.out.println("계좌 정보 출력 -----------------------------");
        System.out.printf("계좌 별칭 : %s\n", this.account_nickname);
        System.out.printf("계좌 번호 : %s\n", this.getAccountNumber());
        System.out.printf("계좌 주인 : %s\n", this.owner);
        System.out.printf("계좌 잔액 : %d원\n", this.deposit);
        System.out.printf("총 입금액 : %d원\n", this.total_deposit);
        System.out.printf("총 사용액 : %d원\n", this.total_amount_used);
        System.out.printf("총 이자액 : %d원\n", this.total_interest);
        System.out.println("-------------------------------------------");
    }
}

class stock_Account extends Account {
    int interest_rate; // 이자율
    int total_interest; // 총 이자액

    stock_Account(String account_nickname, String account_number, String owner
            , String passwd, int interest_rate) {
        super(account_nickname, account_number, owner, passwd);
        this.interest_rate = interest_rate;
        this.total_interest = 0;
    }

    public int deposit(int amount) {
        this.deposit += amount;
        this.total_deposit += amount;
        this.total_interest += (int)(amount * (this.interest_rate / 100.0));
        this.history[this.payment_no] = new printBankHistory("입금", amount);
        this.payment_no++;
        return amount;
    }
    public int withdraw(int amount, String payment_title) {
        if(this.deposit < amount) {
            System.out.println("잔액이 부족합니다.");
            return 0;
        }
        this.deposit -= amount;
        this.total_amount_used += amount;
        this.history[this.payment_no] = new printBankHistory(payment_title, amount);
        this.payment_no++;
        return amount;
    }
    public void printBankInfo() {
        System.out.println("계좌 정보 출력 -----------------------------");
        System.out.printf("계좌 별칭 : %s\n", this.account_nickname);
        System.out.printf("계좌 번호 : %s\n", this.getAccountNumber());
        System.out.printf("계좌 주인 : %s\n", this.owner);
        System.out.printf("계좌 잔액 : %d원\n", this.deposit);
        System.out.printf("총 입금액 : %d원\n", this.total_deposit);
        System.out.printf("총 사용액 : %d원\n", this.total_amount_used);
        System.out.printf("총 이자액 : %d원\n", this.total_interest);
        System.out.println("-------------------------------------------");
    }
}

class business_Account extends Account {
    int interest_rate; // 이자율
    int total_interest; // 총 이자액

    business_Account(String account_nickname, String account_number, String owner
            , String passwd, int interest_rate) {
        super(account_nickname, account_number, owner, passwd);
        this.interest_rate = interest_rate;
        this.total_interest = 0;
    }

    public int deposit(int amount) {
        this.deposit += amount;
        this.total_deposit += amount;
        this.total_interest += (int)(amount * (this.interest_rate / 100.0));
        this.history[this.payment_no] = new printBankHistory("입금", amount);
        this.payment_no++;
        return amount;
    }
    public int withdraw(int amount, String payment_title) {
        if(this.deposit < amount) {
            System.out.println("잔액이 부족합니다.");
            return 0;
        }
        this.deposit -= amount;
        this.total_amount_used += amount;
        this.history[this.payment_no] = new printBankHistory(payment_title, amount);
        this.payment_no++;
        return amount;
    }
    public void printBankInfo() {
        System.out.println("계좌 정보 출력 -----------------------------");
        System.out.printf("계좌 별칭 : %s\n", this.account_nickname);
        System.out.printf("계좌 번호 : %s\n", this.getAccountNumber());
        System.out.printf("계좌 주인 : %s\n", this.owner);
        System.out.printf("계좌 잔액 : %d원\n", this.deposit);
        System.out.printf("총 입금액 : %d원\n", this.total_deposit);
        System.out.printf("총 사용액 : %d원\n", this.total_amount_used);
        System.out.printf("총 이자액 : %d원\n", this.total_interest);
        System.out.println("-------------------------------------------");
    }
}

class Payment_Account extends Account {
    int interest_rate; // 이자율
    int total_interest; // 총 이자액

    Payment_Account(String account_nickname, String account_number, String owner
            , String passwd, int interest_rate) {
        super(account_nickname, account_number, owner, passwd);
        this.interest_rate = interest_rate;
        this.total_interest = 0;
    }

    public int deposit(int amount) {
        this.deposit += amount;
        this.total_deposit += amount;
        this.total_interest += (int)(amount * (this.interest_rate / 100.0));
        this.history[this.payment_no] = new printBankHistory("입금", amount);
        this.payment_no++;
        return amount;
    }
    public int withdraw(int amount, String payment_title) {
        if(this.deposit < amount) {
            System.out.println("잔액이 부족합니다.");
            return 0;
        }
        this.deposit -= amount;
        this.total_amount_used += amount;
        this.history[this.payment_no] = new printBankHistory(payment_title, amount);
        this.payment_no++;
        return amount;
    }
    public void printBankInfo() {
        System.out.println("계좌 정보 출력 -----------------------------");
        System.out.printf("계좌 별칭 : %s\n", this.account_nickname);
        System.out.printf("계좌 번호 : %s\n", this.getAccountNumber());
        System.out.printf("계좌 주인 : %s\n", this.owner);
        System.out.printf("계좌 잔액 : %d원\n", this.deposit);
        System.out.printf("총 입금액 : %d원\n", this.total_deposit);
        System.out.printf("총 사용액 : %d원\n", this.total_amount_used);
        System.out.printf("총 이자액 : %d원\n", this.total_interest);
        System.out.println("-------------------------------------------");
    }
}

class Account_manager {
    Account[] accounts;
    int account_no;

    Account_manager(int limit_account) {
        this.accounts = new Account[limit_account];
        this.account_no = 0;
    }

    void createAccount(int option){
        Scanner sc = new Scanner(System.in);
        String account_nickname, account_number, owner, passwd;
        int deposit, withdraw;

        System.out.print("계좌 별칭 : "); account_nickname = sc.next();
        System.out.print("계좌 번호 : "); account_number = sc.next();
        System.out.print("계좌 주인 : "); owner = sc.next();
        System.out.print("계좌 암호 : "); passwd = sc.next();

        if(option == 0){
            System.out.println("--------- ISA ---------");
            System.out.print("이자율 : "); withdraw = sc.nextInt();
            this.accounts[this.account_no++] = new ISA_Account(account_nickname, account_number, owner, passwd, withdraw);
            System.out.println("ISA 계좌가 생성되었습니다.");
        } else if(option == 1){
            System.out.println("--------- 적금 ---------");
            System.out.print("이자율 : "); withdraw = sc.nextInt();
            this.accounts[this.account_no++] = new saving_account(account_nickname, account_number, owner, passwd, withdraw);
            System.out.println("적금 계좌가 생성되었습니다.");
        } else if(option == 2){
            System.out.println("--------- 증권 ---------");
            System.out.print("이자율 : "); withdraw = sc.nextInt();
            this.accounts[this.account_no++] = new stock_Account(account_nickname, account_number, owner, passwd, withdraw);
            System.out.println("증권 계좌가 생성되었습니다.");
        } else if(option == 3){
            System.out.println("--------- 사업자(법인/개인사업자) ---------");
            System.out.print("이자율 : "); withdraw = sc.nextInt();
            this.accounts[this.account_no++] = new business_Account(account_nickname, account_number, owner, passwd, withdraw);
            System.out.println("사업자 계좌가 생성되었습니다.");
        } else if(option == 4){
            System.out.println("--------- 입출금 ---------");
            System.out.print("이자율 : "); withdraw = sc.nextInt();
            this.accounts[this.account_no++] = new Payment_Account(account_nickname, account_number, owner, passwd, withdraw);
            System.out.println("입출금 계좌가 생성되었습니다.");
        } else {
            System.out.println("잘못된 입력입니다.");
        }
    }
}

public class bank {
}
