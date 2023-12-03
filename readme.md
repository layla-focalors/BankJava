## Bank Payment System by layla-focalors
### Project : laypid (1.0.0 LTS)

#### Description
간단한 은행 계좌 관리 시스템 개발  
프로그래밍 언어 : 자바(Java)  

개발 기간 : 1일 (23-12-03:01:10AM ~ 23-12-03:08:27PM)  

목표 :
1. 은행 계좌 시스템
2. 계좌 개설, 입금, 출금, 이체, 조회, 삭제 기능
3. 대출 기능

조건 :
1. 생성자 사용 <pre>ISA_Account(String account_nickname, String account_number, String owner
   , String passwd, int interest_rate, int total_inbound){
   super(account_nickname, account_number, owner, passwd);
   this.interest_rate = interest_rate;
   this.total_interest = 0;
   this.total_inbound = total_inbound;
   }</pre>
2. 인터페이스 기반 구축 <pre>interface Bankable {
   public abstract int deposit(int amount); // 입금 메서드
   public abstract int withdraw(int amount, String payment_title); // 출금 메서드
   public abstract void printBankInfo(); // 은행 정보 출력 메서드
   public abstract void printBankHistory(); // 은행 내역 출력 메서드
   }</pre>
3. 배열 사용 <pre>printBankHistory[] history; public int withdraw(int amount, String payment_title) {
   if(this.deposit < amount) {
   System.out.println("잔액이 부족합니다.");
   return 0;
   }
   this.deposit -= amount;
   this.total_amount_used += amount;
   this.history[this.payment_no] = new printBankHistory(payment_title, amount);
   this.payment_no++;
   return amount;
   }</pre>
4. 캡슐화로 접근 암호화 <pre>private final String passwd; String GetPassword() { return this.passwd; }</pre>
5. 상속 사용 <pre>class ISA_Account extends Account {
   int interest_rate; // 이자율
   int total_interest; // 총 이자액
   int total_inbound; // 총 가입 기간

   ISA_Account(String account_nickname, String account_number, String owner
   , String passwd, int interest_rate, int total_inbound){
   super(account_nickname, account_number, owner, passwd);
   this.interest_rate = interest_rate;
   this.total_interest = 0; . . .</pre>
6. 오버라이딩 <pre>do {
   account_manager.printMenu(); . . .</pre>
7. 예외처리 <pre>try {
   if(this.account_no >= this.accounts.length) {
   throw new Exception("계좌를 더 이상 생성할 수 없습니다.");
   }
   } catch (Exception e) {
   System.out.println(e.getMessage());
   return;
   }</pre>