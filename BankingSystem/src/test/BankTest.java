package test;

import account.Account;
import account.SavingAccount;
import bank.Bank;
import bank.CentralBank;
import bank.SavingBank;

import java.util.ArrayList;
import java.util.Scanner;

public class BankTest {
    private static Scanner scanner = new Scanner(System.in);
    private static Bank basicBank = new Bank();
    private static SavingBank savingBank = new SavingBank();
    public static void main(String[] args) throws Exception {

        CentralBank centralBank = CentralBank.getInstance();
        // 예금 계좌와 적금 계좌 생성
        Bank basicBank = new Bank();
        SavingBank savingBank = new SavingBank();
        ArrayList<Account> accountList = new ArrayList<>();

        accountList.add(BankTest.basicBank.createAccount());
        accountList.add(BankTest.basicBank.createAccount());
        accountList.add(BankTest.savingBank.createAccount());
        centralBank.setAccountList(accountList);

        boolean isActive = true;
        while (isActive) {
            System.out.println("\n1. 계좌 목록 | 2. 출금 | 3. 입금 | 4. 송금 | 5. 종료");
            int menuNo = scanner.nextInt();
            switch (menuNo) {
                case 1:
                    int sizeOfBank = centralBank.getAccountList().size();
                    for (int i=0; i<sizeOfBank; i++) {
                        if(centralBank.getAccountList().get(i) instanceof SavingAccount){
                            ((SavingAccount)(centralBank.getAccountList().get(i))).getAccountInfo(((SavingAccount)(centralBank.getAccountList().get(i))));
                        } else {
                            centralBank.getAccountList().get(i).getAccountInfo(centralBank.getAccountList().get(i));
                        }

                    }
                    break;
                case 2:
                    savingBank.withdraw();
                    break;
                case 3:
                    savingBank.deposit();
                    break;
                case 4:
                    savingBank.transfer();
                    break;
                case 5:
                    isActive = false;
                    break;
            }
        }
        System.out.println("뱅킹 프로그램을 종료합니다.");
    }
}
