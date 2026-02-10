import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static LibraryManager manager;

    public static void main(String[] args) {
        manager = new LibraryManager(new LibraryRepository());

        while (true) {
            System.out.println("\n=======================================");
            System.out.println("       [ 도서 관리 시스템 - LOGIN ]       ");
            System.out.println("=======================================");
            System.out.print("  아이디(ID)를 입력하세요: ");
            String id = sc.nextLine();
            System.out.print("  비밀번호(PW)를 입력하세요: ");
            String pw = sc.nextLine();
            System.out.println("-----------------------------------------------------------");
            System.out.println("  [확인 중...]");

            if (manager.login(id, pw)) {
                if (manager.getCurrentUser().isAdmin()) {
                    System.out.println("  => 관리자 권한으로 접속했습니다.");
                    adminMenu();
                } else {
                    System.out.println("=> 일반 유저 " + id + "님 환영합니다.");
                    userMenu();
                }
            } else {
                System.out.println("[로그인 실패] 정보를 다시 확인하세요.");
            }
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n=======================================");
            System.out.println("           [ 관리자 전용 메뉴]            ");
            System.out.println("=======================================");
            System.out.println("""
                    1. 도서 등록\
                    
                    2. 도서 수정 및 삭제\
                    
                    3. 전체 도서 목록\
                    
                    4. 도서 검색\
                    
                    0. 종료""");
            System.out.println("----------------------------------------");
            System.out.print("명령 입력 : ");
            String choice = sc.nextLine();

            if (choice.equals("0")) {
                if (confirmExit()) {
                    System.out.println(" [데이터 저장 중...]");
                    manager.saveChanges();
                    System.out.println(" => 모든 데이터가 성공적으로 저장되었습니다.");
                    System.out.println("도서 관리 프로그램을 이용해 주셔서 감사합니다.");
                    System.out.println("----------------------------------------");
                    System.exit(0);
                }
            }

            switch (choice) {
                case "1":
                    System.out.println("[도서 등록]");
                    System.out.print("- 제목 입력 : "); String t = sc.nextLine();
                    System.out.print("- 저자 입력 : "); String a = sc.nextLine();
                    System.out.println("---------------------------------------");
                    if (!t.isEmpty() && !a.isEmpty()) {
                        manager.addBook(t, a);
                        System.out.println("[결과] 등록이 완료되었습니다.");
                    } else {
                        System.out.println("제목과 저자명은 공백일 수 없습니다.");
                    }
                    break;
                case "2":
                    System.out.println("[도서 수정 및 삭제]");
                    System.out.print("- 관리할 도서 ID 입력: ");
                    int id = Integer.parseInt(sc.nextLine());
                    Book b = manager.getBookMap().get(id);
                    if (b == null) {
                        System.out.println("[결과] 존재하지 않는 ID입니다.");
                    } else {
                        System.out.printf("  현재 정보: [%s | %s | %s]%n", b.getTitle(), b.getAuthor(), b.isAvailable() ? "대출가능" : "대출중");
                        System.out.println("  1. 제목 수정  2. 저자 수정  3. 도서 삭제  0. 취소");
                        System.out.print("  선택: ");
                        String sub = sc.nextLine();
                        if (sub.equals("1")) {
                            System.out.print("- 새 제목 입력: ");
                            b.updateBook(sc.nextLine(), b.getAuthor());
                            System.out.println("[결과] 수정이 완료되었습니다.");
                        } else if (sub.equals("2")) {
                            System.out.print("- 새 저자 입력: ");
                            b.updateBook(b.getTitle(), sc.nextLine());
                            System.out.println("[결과] 수정이 완료되었습니다.");
                        } else if (sub.equals("3")) {
                            manager.getBookMap().remove(id);
                            System.out.println("[결과] 해당 도서가 삭제되었습니다.");
                        }
                    }
                    break;
                case "3":
                    System.out.println("=======================================");
                    System.out.println("\n[도서 목록]");
                    System.out.println("ID  |   제목   |   저자   |   상태");
                    System.out.println("----------------------------------------");
                    manager.getBookMap().forEach((idx, book) -> {
                        String status = book.isAvailable() ? "대출가능" : "대출중";
                        System.out.printf("%d | %s | %s | %s\n", idx, book.getTitle(), book.getAuthor(), status);
                    });
                    break;
                case "4":
                    System.out.print(" 검색할 도서 제목 입력: ");
                    String keyword = sc.nextLine();
                    manager.getBookMap().values().stream()
                            .filter(book -> book.getTitle().contains(keyword))
                            .forEach(book -> System.out.printf("[검색 결과] ID: %d | %s | %s | %s\n", book.getId(), book.getTitle(), book.getAuthor(), book.isAvailable() ? "대출가능" : "대출중"));
            }
        }
    }

    private static void userMenu() {
        while (true) {
            System.out.println("\n=======================================");
            System.out.println("           [ 일반 사용자 메뉴 ]            ");
            System.out.println("=======================================");
            System.out.println("1. 도서 대출");
            System.out.println("2. 도서 반납");
            System.out.println("3. 내 현황 보기");
            System.out.println("4. 전체 도서 목록");
            System.out.println("0. 종료");
            System.out.println("----------------------------------------");
            System.out.print("선택: ");
            String choice = sc.nextLine();

            if (choice.equals("0")) {
                if (confirmExit()) {
                    manager.saveChanges();
                    System.exit(0);
                }
                continue;
            }

            switch (choice) {
                case "1":
                    System.out.print("- 대출할 도서 ID 입력: ");
                    int bid = Integer.parseInt(sc.nextLine());
                    if (manager.borrowBook(bid)) System.out.println("[결과] 대출이 완료되었습니다.");
                    else System.out.println("[실패] 이미 대출중이거나 존재하지 않는 도서입니다.");
                    break;
                case "2":
                    System.out.print("- 반납할 도서 ID 입력: ");
                    int rid = Integer.parseInt(sc.nextLine());
                    if (manager.returnBook(rid)) System.out.println("[결과] 정상적으로 반납되었습니다.");
                    else System.out.println("[실패] 반납할 도서가 없거나 잘못된 ID입니다.");
                    break;
                case "3":
                    System.out.println("\n[나의 대출 현황]");
                    manager.getBookMap().values().stream()
                            .filter(book -> !book.isAvailable() && book.getBorrowerId().equals(manager.getCurrentUser().getUserId()))
                            .forEach(book -> System.out.printf("%d | %s | %s\n", book.getId(), book.getTitle(), book.getAuthor()));
                    break;
                case "4":
                    manager.getBookMap().forEach((idx, book) -> {
                        System.out.printf("%d | %s | %s | %s\n", idx, book.getTitle(), book.getAuthor(), book.isAvailable() ? "대출가능" : "대출중");
                    });
                    break;
            }
        }
    }

    private static boolean confirmExit() {
        System.out.println("=======================================");
        System.out.println("           [ 프로그램 종료 확인]            ");
        System.out.println("=======================================");
        System.out.print(" 정말로 프로그램을 종료하시겠습니까? [Y/N]: ");
        String answer = sc.nextLine().trim().toLowerCase();
        System.out.println("----------------------------------------");
        return answer.equals("y");
    }
}