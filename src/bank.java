import java.time.LocalDate;
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
    private final String passwd; // 계좌 비밀번호
    String owner;// 계좌 주인
    int total_amount_used; // 계좌에서 사용한 금액들의 총 합계
    int total_deposit; // 계좌에 입금한 금액들의 총 합계
    int payment_no; // 계좌를 총 몇 번 결제 했는지.
    int deposit; // 계좌 잔액
    String Birth; // 생년월일

    // 계좌 이용 내역
    printBankHistory[] history;

    //Account 클래스 초기화
    Account(String account_nickname, String account_number, String owner, String passwd, String Birth) {
        this.account_nickname = account_nickname;
        this.account_number = account_number;
        this.owner = owner;
        this.passwd = passwd;
        this.total_amount_used = 0;
        this.total_deposit = 0;
        this.payment_no = 0;
        this.deposit = 0;
        this.history = new printBankHistory[100];
        this.Birth = Birth;
    }
    String getAccountNumber() { return this.account_number; }
    String GetPassword() { return this.passwd; }

    public int deposit(int amount) {
        this.deposit += amount;
        this.total_deposit += amount;
        LocalDate now = LocalDate.now();
        String payments = "입금 " + now.toString();
        this.history[this.payment_no] = new printBankHistory(payments, amount);
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
        System.out.println("------------ 계좌 정보 출력 ------------");
        System.out.printf("계좌 별칭 : %s\n", this.account_nickname);
        System.out.printf("계좌 번호 : %s\n", this.account_number);
        System.out.printf("계좌 주인 : %s\n", this.owner);
        System.out.printf("계좌 잔액 : %d원\n", this.deposit);
        System.out.printf("총 입금액 : %d원\n", this.total_deposit);
        System.out.printf("총 사용액 : %d원\n", this.total_amount_used);
        System.out.println("계좌 비밀번호 : %s" + this.GetPassword());
        System.out.println("가입자 생년월일 : " + this.Birth);
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

    public void setAccount_nickname(String accountNickname) {
        this.account_nickname = accountNickname;
    }
}

class ISA_Account extends Account {
    int mod; // 일반형, 서민형
    int interest_rate; // 이자율
    int total_interest; // 총 이자액
    int total_inbound; // 총 가입 기간

    ISA_Account(String account_nickname, String account_number, String owner
            , String passwd, int interest_rate, int total_inbound, String Birth, int mod){
        super(account_nickname, account_number, owner, passwd, Birth);
        this.interest_rate = interest_rate;
        this.total_interest = 0;
        this.total_inbound = total_inbound;
        this.mod = mod;
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
        System.out.printf("잔여 가입일 : %d년\n", this.total_inbound);
        System.out.printf("총 입금액 : %d원\n", this.total_deposit * 365);
        System.out.printf("총 사용액 : %d원\n", this.total_amount_used);
        System.out.printf("총 이자액 : %d원\n", this.total_interest);
        System.out.println("계좌 비밀번호 : " + this.GetPassword());
        System.out.println("가입자 생년월일 : " + this.Birth);
        if(this.mod == 1){
            System.out.println("가입 타입 : 일반형");
        }else {
            System.out.println("가입 타입 : 서민형");
        }
        System.out.println("-------------------------------------------");
    }
}

class saving_account extends Account {
    int interest_rate; // 이자율
    int total_interest; // 총 이자액

    int total_inbound; // 총 가입 기간
    int deposit2; // 매달 납입할 금액
    int auto; // 자동이체 설정 여부
    int auto2; // 만기 시 처리 방법

    saving_account(String account_nickname, String account_number, String owner
            , String passwd, int interest_rate, int total_inbound, String Birth, int deposit2, int auto, int auto2) {
        super(account_nickname, account_number, owner, passwd, Birth);
        this.interest_rate = interest_rate;
        this.total_interest = 0;
    }

    public int deposit(int amount, String paytitle) {
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
        System.out.println("계좌 비밀번호 : " + this.GetPassword());
        System.out.println("가입자 생년월일 : " + this.Birth);
        System.out.println("적금 관련 메뉴 -----------------------------");
        System.out.printf("매달 납입할 금액 : %d원\n", this.deposit2);
        if(this.auto == 1){
            System.out.println("자동이체 설정 여부 : 설정");
        } else {
            System.out.println("자동이체 설정 여부 : 설정 안함");
        }
        if(this.auto2 == 1){
            System.out.println("만기 시 처리 방법 : 자동 연장");
        } else {
            System.out.println("만기 시 처리 방법 : 자동 해지");
        }
        System.out.println("-------------------------------------------");
    }
}

class minus_Account extends Account {
    int interest_rate; // 이자율
    int total_interest; // 총 이자액
    int max_money; // 한도

    minus_Account(String account_nickname, String account_number, String owner
            , String passwd, int interest_rate, int max_money, String Birth) {
        super(account_nickname, account_number, owner, passwd, Birth);
        this.interest_rate = interest_rate;
        this.total_interest = 0;
        this.max_money = max_money;
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
        if(this.total_amount_used > max_money){
//            System.out.println("DEBUG : " + this.total_amount_used + " : " + max_money);
            System.out.println("한도를 초과하였습니다.");
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
        System.out.println("계좌 비밀번호 : " + this.GetPassword());
        System.out.println("가입자 생년월일 : " + this.Birth);
        System.out.println("한도 금액 : " + this.max_money + "원");
        System.out.println("-------------------------------------------");
    }
}

class stock_Account extends Account {
    int interest_rate; // 이자율
    int total_interest; // 총 이자액

    stock_Account(String account_nickname, String account_number, String owner
            , String passwd, int interest_rate, String Birth) {
        super(account_nickname, account_number, owner, passwd, Birth);
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
        System.out.println("계좌 비밀번호 : " + this.GetPassword());
        System.out.println("가입자 생년월일 : " + this.Birth);
        System.out.println("-------------------------------------------");
    }
}

class business_Account extends Account {
    int interest_rate; // 이자율
    int total_interest; // 총 이자액
    String business_number; // 사업자 번호

    business_Account(String account_nickname, String account_number, String owner
            , String passwd, int interest_rate, String business_number, String Birth) {
        super(account_nickname, account_number, owner, passwd, Birth);
        this.interest_rate = interest_rate;
        this.total_interest = 0;
        this.business_number = business_number;
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
        System.out.println("계좌 비밀번호 : " + this.GetPassword());
        System.out.println("사업자번호 : " + this.business_number);
        System.out.println("가입자 생년월일 : " + this.Birth);
        System.out.println("-------------------------------------------");
    }
}

class Payment_Account extends Account {
    int interest_rate; // 이자율
    int total_interest; // 총 이자액

    Payment_Account(String account_nickname, String account_number, String owner
            , String passwd, int interest_rate, String Birth) {
        super(account_nickname, account_number, owner, passwd, Birth);
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
        System.out.println("계좌 비밀번호 : " + this.GetPassword());
        System.out.println("가입자 생년월일 : " + this.Birth);
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
        try {
            if(this.account_no >= this.accounts.length) {
                throw new Exception("계좌를 더 이상 생성할 수 없습니다.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        Scanner sc = new Scanner(System.in);
        String account_nickname, account_number, owner, passwd;
        int deposit, withdraw;

        System.out.print("계좌 별칭 : "); account_nickname = sc.next();
        System.out.print("계좌 번호 : "); account_number = sc.next();
        System.out.print("계좌 주인 : "); owner = sc.next();
        System.out.print("계좌 암호 : "); passwd = sc.next();
        System.out.print("생년월일 : "); String Birth = sc.next();

        if(option == 0){
            System.out.println("--------- ISA ---------");
            System.out.println("가입 기간을 입력하세요(년)\n최소 가입 기간은 시행령 기준 3년입니다.\n최소 가입 기간보다 가입일지 작은 경우 자동으로 3년으로 변경됩니다.");
            System.out.print("가입 기간 : "); int year = sc.nextInt();
            System.out.print("이자율 : "); withdraw = sc.nextInt();
            if (year < 3) {
                year = 3;
            }
            System.out.println("가입 타입을 선택하세요.");
            System.out.println("1. 일반형, 2. 서민형");
            System.out.print("입력 : ");
            int mod = sc.nextInt();
            if(mod == 1){
                System.out.println("일반형이 선택되었습니다.");
            } else if(mod == 2){
                System.out.println("서민형이 선택되었습니다.");
            } else {
                System.out.println("잘못된 입력입니다.");
            }
            this.accounts[this.account_no++] = new ISA_Account(account_nickname, account_number, owner, passwd, withdraw, year, Birth, mod);
            System.out.println("ISA 계좌가 생성되었습니다.");
        } else if(option == 1){
            System.out.println("--------- 적금 ---------");
            System.out.println("매달 납입할 금액 : "); deposit = sc.nextInt();
            System.out.println("자동이체 설정 여부 : \n 1 : 설정, 2 : 설정 안함");
            System.out.print("입력 : "); int auto = sc.nextInt();
            if(auto == 1){
                System.out.println("자동이체가 설정되었습니다.");
            } else if(auto == 2){
                System.out.println("자동이체가 설정되지 않았습니다.");
            } else {
                System.out.println("잘못된 입력입니다.");
            }
            System.out.println("만기 시 처리 방법 ");
            System.out.println("1. 자동 연장");
            System.out.println("2. 자동 해지");
            System.out.print("입력 : "); int auto2 = sc.nextInt();

            System.out.print("이자율 : "); withdraw = sc.nextInt();
            System.out.println("가입 기간을 입력하세요(년)");
            System.out.print("가입 기간 : "); int year = sc.nextInt();
            this.accounts[this.account_no++] = new saving_account(account_nickname, account_number, owner, passwd, withdraw, year, Birth, deposit, auto, auto2);
            System.out.println("적금 계좌가 생성되었습니다.");
        } else if(option == 2){
            System.out.println("--------- 증권 ---------");
            System.out.print("이자율 : "); withdraw = sc.nextInt();
            this.accounts[this.account_no++] = new stock_Account(account_nickname, account_number, owner, passwd, withdraw, Birth);
            System.out.println("증권 계좌가 생성되었습니다.");
        } else if(option == 3){
            System.out.println("--------- 사업자(법인/개인사업자) ---------");
            System.out.print("이자율 : "); withdraw = sc.nextInt();
            System.out.print("유효한 사업자/법인 번호 : "); String business_number = sc.next();
            this.accounts[this.account_no++] = new business_Account(account_nickname, account_number, owner, passwd, withdraw, business_number, Birth);
            System.out.println("사업자 계좌가 생성되었습니다.");
        } else if(option == 4){
            System.out.println("--------- 입출금 ---------");
            System.out.print("이자율 : "); withdraw = sc.nextInt();
            this.accounts[this.account_no++] = new Payment_Account(account_nickname, account_number, owner, passwd, withdraw, Birth);
            System.out.println("입출금 계좌가 생성되었습니다.");
        } else if(option==5){
            System.out.println("--------- 마이너스 ---------");
            System.out.print("한도 : "); int max_money = sc.nextInt();
            this.accounts[this.account_no++] = new minus_Account(account_nickname, account_number, owner, passwd, 1, max_money, Birth);
            System.out.println("마이너스 계좌가 생성되었습니다.");
        }
        else {
            System.out.println("잘못된 입력입니다.");
        }
    }

    int findAccount(String account_number) {
        for(int i=0;i<this.account_no;i++) {
            if(this.accounts[i].getAccountNumber().equals(account_number)) {
                return i;
            }
        }
        return -1;
    }

    void printAccountInfo(int account_idx) {
        this.accounts[account_idx].printBankInfo();
        this.accounts[account_idx].printBankHistory();
    }

    void deposit(String account_number, int amount) {
        int account_idx = this.findAccount(account_number);
        if(account_idx != -1) {
            this.accounts[account_idx].deposit(amount);
        }
    }

    void withdraw(String account_number, int amount, String payment_title) {
        int account_idx = this.findAccount(account_number);
        if(account_idx != -1) {
            this.accounts[account_idx].withdraw(amount, payment_title);
        }
    }

    void paySomething(String account_number, int amount, String payment_title) {
        int account_idx = this.findAccount(account_number);
        if(account_idx != -1) {
            this.accounts[account_idx].withdraw(amount, payment_title);
        }
    }

    void SendMoney(String account_number, int amount, String payment_title) {
        int account_idx = this.findAccount(account_number);
        if(account_idx != -1) {
            this.accounts[account_idx].withdraw(amount, payment_title);
        }
    }

    void printMenu() {
        System.out.println("-------- LaylaBank --------");
        System.out.println("1. 계좌 생성");
        System.out.println("2. 계좌 조회");
        System.out.println("-------- Account Actions --------");
        System.out.println("3. 입금");
        System.out.println("4. 출금");
        System.out.println("5. 결제");
        System.out.println("6. 이체");
        System.out.println("-------- Money Actions --------");
        System.out.println("7. 대출");
        System.out.println("8. 대출 상환");
        System.out.println("-------- System Actions --------");
        System.out.println("9. 약관 보기");
        System.out.println("10. 계좌 삭제");
//        System.out.println("11. 계좌 정보 수정");
        System.out.println("-------- ETC --------");
        System.out.println("0. 종료");
        System.out.print("입력 : ");
    }

    void printIDP() {
        System.out.println("-------- LaylaBank --------");
        System.out.println("약관에 대한 항목을 확인해주세요.");
        System.out.println("https://banks.layla-focalors.tech");
    }
}

public class bank {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Account_manager account_manager = new Account_manager(1000);
        boolean exit = false;
        String account_number, account_nickname, owner, passwd;
        int deposit, withdraw, option, amount;

        do {
            account_manager.printMenu();
            option = sc.nextInt();
            switch (option){
                case 0 -> {
                    System.out.println("LaylaBank를 사용해주셔서 감사합니다. \n프로그램을 종료합니다.");
                    exit = true;
                }
                case 1 -> {
                    System.out.println("--------- 계좌 생성 ---------");
                    System.out.println("0. ISA");
                    System.out.println("1. 적금");
                    System.out.println("2. 증권");
                    System.out.println("3. 사업자(법인/개인사업자)");
                    System.out.println("4. 입출금");
                    System.out.println("5. 마이너스");
                    System.out.print("입력 : ");
                    account_manager.createAccount(sc.nextInt());
                }
                case 2 -> {
                    System.out.println("--------- 계좌 조회 ---------");
                    System.out.print("계좌 번호 : ");
                    int account_index = account_manager.findAccount(sc.next());
                    if(account_index == -1){
                        System.out.println("계좌가 존재하지 않습니다.");
                    } else {
                        account_manager.printAccountInfo(account_index);
                    }
                }
                case 3 -> {
                    System.out.println("--------- 입금 ---------");
                    System.out.print("계좌 번호 : ");
                    account_number = sc.next();
                    System.out.print("입금액 : ");
                    amount = sc.nextInt();
                    account_manager.deposit(account_number, amount);
                }
                case 4 -> {
                    System.out.println("--------- 출금 ---------");
                    LocalDate now = LocalDate.now();
                    System.out.print("계좌 번호 : ");
                    account_number = sc.next();
                    System.out.print("출금액 : ");
                    amount = sc.nextInt();
                    String paytitle = "출금 " + now.toString();
                    account_manager.withdraw(account_number, amount, paytitle);
                }
                case 5 -> {
                    System.out.println("--------- 결제 ---------");
                    LocalDate now = LocalDate.now();
                    System.out.print("계좌 번호 : ");
                    account_number = sc.next();
                    System.out.print("결제액 : ");
                    amount = sc.nextInt();
                    String paytitle = "결제 " + now.toString();
                    account_manager.paySomething(account_number, amount, paytitle);
                }
                case 6 -> {
                    System.out.println("--------- 이체 ---------");
                    LocalDate now = LocalDate.now();
                    System.out.println("이체할 계좌 번호 : ");
                    account_number = sc.next();
                    System.out.println("이체받을 계좌 번호 : ");
                    account_number = sc.next();
                    System.out.println("이체액 : ");
                    amount = sc.nextInt();
                    String paytitle = "이체 " + now.toString();
                    account_manager.paySomething(account_number, amount, paytitle);
                }
                case 7 -> {
                    System.out.println("--------- 대출 ---------");
                    System.out.print("계좌 번호 : ");
                    account_number = sc.next();
                    System.out.print("대출액 : ");
                    amount = sc.nextInt();
                    System.out.println("상환일 : ");
                    String paytitle = sc.next();
                    account_manager.deposit(account_number, amount);
                }
                case 8 -> {
                    System.out.println("--------- 대출 상환 ---------");
                    System.out.print("계좌 번호 : ");
                    account_number = sc.next();
                    System.out.print("상환액 : ");
                    amount = sc.nextInt();
                    LocalDate now = LocalDate.now();
                    String paytitle = "대출 상환 " + now.toString();
                    account_manager.paySomething(account_number, amount, paytitle);
                }
                case 9 -> {
                    System.out.println("--------- 약관 보기 ---------");
                    account_manager.printIDP();
                }case 10 -> {
                    System.out.println("--------- 계좌 삭제 ---------");
                    System.out.print("계좌 번호 : ");
                    account_number = sc.next();
                    System.out.print("계좌 비밀번호 : ");
                    passwd = sc.next();
                    int account_index = account_manager.findAccount(account_number);
                    if(account_index == -1){
                        System.out.println("계좌가 존재하지 않습니다.");
                    } else {
                        if(account_manager.accounts[account_index].GetPassword().equals(passwd)){
                            account_manager.accounts[account_index] = null;
                            System.out.println("계좌가 삭제되었습니다.");
                        } else {
                            System.out.println("비밀번호가 일치하지 않습니다.");
                        }
                    }

                }
                default -> System.out.println("LaylaBank : 존재하지 않는 메뉴입니다. 다시 확인해주세요");
            }
        } while(!exit);
    }
}
