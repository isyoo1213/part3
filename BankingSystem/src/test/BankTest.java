package test;

import account.Account;
import bank.Bank;
import bank.CentralBank;
import bank.SavingBank;
import exception.BankException;

import java.util.ArrayList;
import java.util.Scanner;

public class BankTest {
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        final CentralBank centralBank = CentralBank.getInstance();
        // 예금 계좌와 적금 계좌 생성
        final Bank bank1 = new Bank();
        final SavingBank bank2 = new SavingBank();
        final ArrayList<Account> accountList = new ArrayList<>();

        accountList.add(bank1.createAccount());
        accountList.add(bank2.createAccount());
        centralBank.setAccountList(accountList);

        boolean isActive = true;
        while (isActive) {
            System.out.println("\n1. 계좌 목록 | 2. 출금 | 3. 입금 | 4. 송금 | 5. 종료");

            int menuNo;
            if(scanner.hasNextInt()) {
                menuNo = scanner.nextInt();
            } else {
                System.out.println("값이 올바르지 않습니다. 메뉴번호를 정확히 입력해주세요.");
                scanner.next();
                continue;
            }

            try {
                switch (menuNo) {
                    case 1 -> {
                        int sizeOfBank = centralBank.getAccountList().size();
                        for (int i = 0; i < sizeOfBank; i++) {
                            centralBank.getAccountList().get(i).getAccountInfo(centralBank.getAccountList().get(i));
                        }
                    }
                    case 2 -> bank2.withdraw();
                    case 3 -> bank2.deposit();
                    case 4 -> bank2.transfer();
                    case 5 -> isActive = false;
                    default -> System.out.println("\n잘못된 메뉴번호입니다. 다시 입력해주세요.");
                }
            } catch(BankException be) {
                be.printStackTrace();
            } catch(Exception e) {
                System.out.println("\n뱅킹시스템 처리 중 오류가 발생했습니다.");
                e.printStackTrace();
            }
        }
        System.out.println("뱅킹 프로그램을 종료합니다.");
    }
}
