package bank;

import account.Account;
import account.SavingAccount;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;

public class Bank {
    //TODO: Bank 클래스는 출금, 입금, 송금, 계좌 생성, 계좌 검색 기능들을 갖고 있습니다.
    protected static Scanner scanner = new Scanner(System.in);
    protected static int seq = 0;
    public static DecimalFormat df = new DecimalFormat("#,###");

    //계좌번호 생성을 위한 countAcc 변수 생성
    protected static int countAcc = 0;

    //countAcc의 getter, setter 생성
    public static int getCountAcc() {
        return countAcc;
    }

    public static void setCountAcc(int countAcc) {
        Bank.countAcc = countAcc;
    }

    //계좌별 InterestCalculator를 담아줄 HashMap 생성
    HashMap<String, InterestCalculator> interestCalculatorHashMap = new HashMap<>();

    // 뱅킹 시스템의 기능들
    public void withdraw() throws AccountException, BalanceException, AmountException {
        //TODO: 출금 메서드 구현
        //TODO: key, value 형태의 HashMap을 이용하여 interestCalculators 구현
        // TODO: 검색 -> 적금 계좌이면 적금 계좌의 출금 메소드 호출 -> 완료시 break
        // TODO: interestCalculators 이용하 이자 조회 및 출금
        //여기서 key: category, value: 각 category의 InterestCalculator 인스턴스

        //계좌 종류 별 이자 계산을 위한 InterestCalculator 인스턴스와 interest 객체 생성
        BasicInterestCalculator basicInterestCalculator = new BasicInterestCalculator();
        SavingInterestCalculator savingInterestCalculator = new SavingInterestCalculator();
        InterestCalculator bic = basicInterestCalculator;
        InterestCalculator sic = savingInterestCalculator;
        interestCalculatorHashMap.put("N", bic);
        interestCalculatorHashMap.put("S", bic);
        BigDecimal interest = null;

        //외부 while문의 조건으로 findAccountActive 변수 생성
        boolean findAccountActive = true;

        while (findAccountActive) {

            //하드코딩을 피하기 위해 accountlist에서 계좌를 찾아 virtualAccount로 할당한 후 사용
            Account virtualWithdrawAccount = null;
            SavingAccount virtualSavingWithdrawAccount = null;

            System.out.println("\n출금하시려는 계좌번호를 입력하세요.");
            String accNo = scanner.next();

            //적금계좌일 경우 SavingAccount의 메서드를 사용하기 위해 다운캐스팅
            if (this.findAccount(accNo) instanceof SavingAccount) {
                virtualSavingWithdrawAccount = (SavingAccount) this.findAccount(accNo);
            } else {
                virtualWithdrawAccount = this.findAccount(accNo);
            }

            try {
                //가상계좌가 존재하지 않을 경우 exception 처리
                if (virtualWithdrawAccount == null && virtualSavingWithdrawAccount == null) {
                    throw new AccountException("올바른 계좌번호를 입력해주세요.");
                } else {
                    boolean withrawActive = true;
                    while (withrawActive) {
                        //가상계좌가 일반계좌일 경우
                        if (virtualWithdrawAccount instanceof Account) {

                            System.out.println("\n출금할 금액을 입력하세요.");
                            String strAmount = scanner.next();
                            //입력받은 금액을 BigDecimal 형으로 담아줄 withrawAmount 변수 선언
                            BigDecimal withdrawAmount;

                            //출금 금액을 withdrawAmount 변수를 생성해 할당하고, 숫자형이 아닌 경우 exception 처리
                            if (!strAmount.matches("[0-9]+")) {
                                throw new AmountException("금액은 0~9의 숫자의 조합으로만 입력해주세요.");
                            } else {
                                withdrawAmount = new BigDecimal(strAmount);
                            }

                            //출금 금액이 0이하일 경우 exception 처리
                            if (withdrawAmount.compareTo(BigDecimal.ZERO) <= 0) {
                                throw new BalanceException("올바른 출금 금액을 입력해주세요.");
                                //출금 금액이 계좌의 금액보다 클 경우 exception 처리
                            } else if (virtualWithdrawAccount.getBalance().compareTo(withdrawAmount) < 0) {
                                throw new AmountException("잔액이 모자랍니다.");
                            } else {
                                this.findAccount(accNo).withdraw(withdrawAmount);
                                virtualWithdrawAccount.setBalance(this.findAccount(accNo).getBalance());
                                interest = interestCalculatorHashMap.get("N").getInsterest(withdrawAmount);
                                System.out.println("출금이 완료됐습니다." + "\n" + virtualWithdrawAccount.getAccNo() + "계좌의 잔액은 " + virtualWithdrawAccount.getBalance() + "원 입니다.");
                                System.out.println("출금액에 대한 이자는 " + interest + "원 입니다.");
                            }
                        } else {
                            //가상계좌가 적금 계좌일 경우 SavingBank의 withdraw 메서드가 호출되도록 bank인스턴스를 다운캐스팅
                            //SavingBank의 withdraw 메서드가 반환하는 출금 금액을 savingWithdrawAmount에 할당
                            BigDecimal savingWithdrawAmount = ((SavingBank) this).withdraw(virtualSavingWithdrawAccount);
                            if (savingWithdrawAmount != null) {
                                interest = interestCalculatorHashMap.get("S").getInsterest(savingWithdrawAmount);

                                // 출력 구문을 통일하려 했지만 실패
                                virtualWithdrawAccount = virtualSavingWithdrawAccount;
                                System.out.println("출금이 완료됐습니다." + "\n" + virtualWithdrawAccount.getAccNo() + "계좌의 잔액은 " + virtualWithdrawAccount.getBalance() + "원 입니다.");
                                System.out.println("출금액에 대한 이자는 " + interest + "원 입니다.");
                            }
                        }
                        break;
                    }
                }

            } catch (AccountException e) {
                System.out.println(e.getMessage());
            } catch (BalanceException e) {
                System.out.println(e.getMessage());
            } catch (AmountException e) {
                System.out.println(e.getMessage());
            }
            break;
        }

    }

    public void deposit() throws AccountException, AmountException {

        boolean depositActive = true;
        while (depositActive) {
            Account virtualDeopsitAccount;
            //TODO: 입금 메서드 구현
            // 존재하지 않는 계좌이면 다시 물어보기
            // TODO: 입금 처리

            System.out.println("\n입금하시려는 계좌번호를 입력해주세요.");
            String accNo = scanner.next();
            virtualDeopsitAccount = this.findAccount(accNo);

            try {
                if (virtualDeopsitAccount == null) {
                    throw new AccountException("올바른 계좌번호를 입력해주세요.");
                } else {
                    System.out.println("\n입금할 금액을 입력하세요.");
                    String strAmount = scanner.next();
                    BigDecimal depositAmount;

                    if (!strAmount.matches("[0-9]+")) {
                        throw new AmountException("금액은 0~9의 숫자의 조합으로만 입력해주세요.");
                    } else {
                        depositAmount = new BigDecimal(strAmount);
                    }
                    if (depositAmount.compareTo(BigDecimal.ZERO) <= 0) {
                        throw new AmountException("1원 이상의 입금 금액을 입력해주세요");
                    } else {
                        this.findAccount(accNo).deposit(depositAmount);
                        virtualDeopsitAccount.setBalance(this.findAccount(accNo).getBalance());
                        System.out.println("입금이 완료됐습니다." + "\n" + virtualDeopsitAccount.getAccNo() + "계좌의 잔액은 " + virtualDeopsitAccount.getBalance() + "원 입니다.");
                    }
                    break;
                }
            } catch (AccountException e) {
                depositActive = false;
                System.out.println(e.getMessage());
            } catch (AmountException e) {
                depositActive = false;
                System.out.println(e.getMessage());
            }
        }
    }

    public Account createAccount() throws OwnerException {
        //TODO: 계좌 생성하는 메서드 구현
        // 계좌번호 채번
        // 계좌번호는 "0000"+증가한 seq 포맷을 가진 번호입니다.
        //TODO

        if (this.getCountAcc() == 0) {
            this.setCountAcc(this.getCountAcc() + 1);
        }

        String newOwner = null;
        boolean createActive = true;
        while (createActive) {

            System.out.println("\n일반계좌 생성을 시작합니다.");
            System.out.println("생성하시려는 일반계좌의 계좌주 성함을 입력해주세요.");
            newOwner = scanner.next();

            //계좌주 이름이 완성형 한글이 아닐 경우 exception 처리
            try {
                if (newOwner.matches("[가-힣].+")) {
                    createActive = false;
                } else {
                    createActive = true;
                    throw new OwnerException("이름은 완성된 한글로만 입력할 수 있습니다.");
                }
            } catch (OwnerException e) {
                System.out.println(e.getMessage());
            }

        }

        String newAccNo = String.format("%04d", this.getCountAcc());
        BigDecimal newBalance = new BigDecimal("0");

        Account account = new Account(newAccNo, newOwner, newBalance);
        account.setActive(true);
        account.setCategory("N");

        System.out.printf("\n%s님 일반계좌가 발급되었습니다.\n", newOwner);
        account.getAccountInfo(account); //출력

        this.setCountAcc(this.getCountAcc() + 1);

        return account;
    }

    public Account findAccount(String accNo) {

        //TODO: 계좌리스트에서 찾아서 반환하는 메서드 구현

        boolean validation = false;
        int goalIndex = 0;
        for (Account account : CentralBank.getAccountList()) {
            if (account.getAccNo().equals(accNo)) {
                validation = account.getAccNo().equals(accNo);
                goalIndex = CentralBank.getAccountList().indexOf(account);
                break;
            }
        }
        if (validation) {
            return CentralBank.getAccountList().get(goalIndex);
        } else {
            return null;
        }

    }

    public void transfer() throws AccountException, AmountException, BalanceException {
        //TODO: 송금 메서드 구현
        // 잘못 입력하거나 예외처리시 다시 입력가능하도록
        //TODO
        boolean transferActive = true;

        while (transferActive) {
            Account virtualTransferAccount1 = null;
            SavingAccount virtualTransferSavingAccount1 = null;

            System.out.println("\n송금하시려는 계좌번호를 입력해주세요.");
            String fromAccNo = scanner.next();
            if (this.findAccount(fromAccNo) instanceof SavingAccount) {
                virtualTransferSavingAccount1 = (SavingAccount) (this.findAccount(fromAccNo));
            } else if (this.findAccount(fromAccNo) instanceof Account) {
                virtualTransferAccount1 = this.findAccount(fromAccNo);
            }

            try {
                if (virtualTransferAccount1 == null && virtualTransferSavingAccount1 == null) {
                    throw new AccountException("올바른 계좌번호를 입력해주세요.");
                } else if (virtualTransferSavingAccount1 != null && virtualTransferSavingAccount1.getBalance().compareTo(virtualTransferSavingAccount1.getGoalAmount()) < 0) {
                    throw new BalanceException("송금을 위한 적금 계좌의 잔액이 부족합니다.");
                } else {
                    while (transferActive) {
                        Account virtualTransferAccount2;

                        System.out.println("\n어느 계좌번호로 보내시려나요?");
                        String toAccNo = scanner.next();
                        virtualTransferAccount2 = this.findAccount(toAccNo);

                        if (virtualTransferAccount1 instanceof Account) {

                            if (virtualTransferAccount2 == null) {
                                throw new AccountException("올바른 계좌번호를 입력해주세요.");
                            } else if (virtualTransferAccount2 instanceof SavingAccount) {
                                throw new AccountException("\n적금 계좌로는 송금이 불가합니다.");
                            } else if (virtualTransferAccount1.getAccNo() == virtualTransferAccount2.getAccNo()) {
                                throw new AccountException("\n본인 계좌로의 송금은 입금을 이용해주세요.");
                            } else {
                                System.out.println("\n송금할 금액을 입력하세요.");
                                String strAmount = scanner.next();
                                BigDecimal transferAmount;

                                if (!strAmount.matches("[0-9]+")) {
                                    throw new AmountException("금액은 0~9의 숫자의 조합으로만 입력해주세요.");
                                } else {
                                    transferAmount = new BigDecimal(strAmount);
                                }
                                if (transferAmount.compareTo(BigDecimal.ZERO) <= 0) {
                                    throw new AmountException("1원 이상의 송금 금액을 입력해주세요");
                                } else if (virtualTransferAccount1.getBalance().compareTo(transferAmount) < 0) {
                                    throw new BalanceException("잔액이 모자랍니다.");
                                } else {
                                    this.findAccount(fromAccNo).withdraw(transferAmount);
                                    this.findAccount(toAccNo).deposit(transferAmount);
                                    virtualTransferAccount1.setBalance(this.findAccount(fromAccNo).getBalance());
                                    virtualTransferAccount2.setBalance(this.findAccount(toAccNo).getBalance());
                                    System.out.println(virtualTransferAccount1.getAccNo() + "계좌에서 " + virtualTransferAccount2.getAccNo() + "계좌로 송금이 완료됐습니다.");
                                    System.out.println(virtualTransferAccount1.getAccNo() + "계좌의 잔액은 " + virtualTransferAccount1.getBalance() + "원 입니다.");
                                    transferActive = false;
                                }
                                break;
                            }
                        } else {
                            BigDecimal savingTransferAmount = ((SavingBank) this).withdraw(virtualTransferSavingAccount1);
                            if (savingTransferAmount != null) {
                                this.findAccount(toAccNo).deposit(savingTransferAmount);
                                virtualTransferSavingAccount1.setBalance(this.findAccount(fromAccNo).getBalance());
                                virtualTransferAccount2.setBalance(this.findAccount(toAccNo).getBalance());
                                System.out.println(virtualTransferSavingAccount1.getAccNo() + "계좌에서 " + virtualTransferAccount2.getAccNo() + "계좌로 송금이 완료됐습니다.");
                                System.out.println(virtualTransferSavingAccount1.getAccNo() + "계좌의 잔액은 " + virtualTransferSavingAccount1.getBalance() + "원 입니다.");
                                transferActive = false;
                                break;
                            } else {
                                transferActive = false;
                            }
                        }
                        break;
                    }
                    break;

                }
            } catch (AccountException e) {
                transferActive = false;
                System.out.println(e.getMessage());
            } catch (BalanceException e) {
                transferActive = false;
                System.out.println(e.getMessage());
            } catch (AmountException e) {
                transferActive = false;
                System.out.println(e.getMessage());
            }
        }
    }
}
