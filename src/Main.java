import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LibraryRepository repository = new LibraryRepository();
        LibraryManager manager = new LibraryManager(repository);

        while(true) {
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
                    adminMenu(manager, repository, sc);
                } else {
                    System.out.println("=> 일반 유저 " + id + "님 환영합니다. (유저 메뉴 미구현)");
                }
            } else {
                System.out.println("[로그인 실패] 정보를 다시 확인하세요.");
            }
        }
    }

    private static void adminMenu(LibraryManager manager, LibraryRepository repository, Scanner sc) {
        while(true) {
            System.out.println("\n=======================================");
            System.out.println("           [ 관리자 전용 메뉴]            ");
            System.out.println("=======================================");
            System.out.println("1. 도서 등록");
            System.out.println("2. 도서 수정 및 삭제");
            System.out.println("3. 전체 도서 목록");
            System.out.println("4. 도서 검색");
            System.out.println("0. 종료");
            System.out.println("----------------------------------------");
            System.out.print("명령 입력 : ");
            String choice = sc.nextLine();
            if (choice.equals("0")) {
                if (confirmExit(sc)) {
                    System.out.println(" [데이터 저장 중...]");
                    System.out.println(" => 모든 데이터가 성공적으로 저장되었습니다.");
                    System.out.println("도서 관리 프로그램을 이용해 주셔서 감사합니다.");
                    System.out.print("----------------------------------------");
                    return;
                }

                System.out.print("종료를 취소합니다.");
            } else {
                switch (choice) {
                    case "1":
                        System.out.println("[도서 등록]");
                        System.out.print("- 제목 입력 : ");
                        String t = sc.nextLine();
                        System.out.print("- 저자 입력 : ");
                        String a = sc.nextLine();
                        System.out.println("---------------------------------------");
                        if (!t.isEmpty() && !a.isEmpty()) {
                            manager.addBook(t, a);
                            System.out.println("[결과] 등록이 완료되었습니다. (도서 ID:" + repository.getBookCount() + ")");
                            break;
                        }

                        System.out.println("제목과 저자명은 공백일 수 없습니다.");
                        return;
                    case "2":
                        System.out.println("[도서 수정 및 삭제]");
                        System.out.print("- 관리할 도서 ID 입력: ");
                        int id = Integer.parseInt(sc.nextLine());
                        System.out.println("-----------------------------------------------------------");
                        Book b = (Book)repository.getBookMap().get(id);
                        if (b != null) {
                            String status = b.isAvailable() ? "대출가능" : "대출중";
                            System.out.printf("  현재 정보: [%s | %s | %s]\n", b.getTitle(), b.getAuthor(), status);
                            System.out.println("  1. 제목 수정  2. 저자 수정  3. 도서 삭제  0. 취소");
                            System.out.println("-----------------------------------------------------------");
                            System.out.print("  선택: ");
                            switch (sc.nextLine()) {
                                case "3":
                                    if (manager.deleteBook(id)) {
                                        System.out.println("[결과] 해당 도서 정보가 시스템에서 삭제되었습니다.");
                                    }
                                    continue;
                                case "1":
                                    System.out.print("- 새 제목 입력: ");
                                    manager.editBook(id, sc.nextLine(), b.getAuthor());
                                    System.out.println("[결과] 수정이 완료되었습니다.");
                                    continue;
                                case "2":
                                    System.out.print("- 새 저자 입력: ");
                                    manager.editBook(id, b.getTitle(), sc.nextLine());
                                    System.out.println("[결과] 수정이 완료되었습니다.");
                                default:
                                    continue;
                            }
                        }

                        System.out.println("[결과] 존재하지 않는 ID입니다.");
                        break;
                    case "3":
                        System.out.println("=======================================");
                        System.out.println("\n[도서 목록]");
                        System.out.println("ID  |   제목   |   저자   |   상태");
                        System.out.println("----------------------------------------");
                        repository.getBookMap().forEach((idx, book) -> System.out.printf("%d | %s | %s | %s\n", idx, book.getTitle(), book.getAuthor(), book.isAvailable() ? "대출중" : "대출가능"));
                }
            }
        }
    }

    private static boolean confirmExit(Scanner sc) {
        System.out.println("=======================================");
        System.out.println("           [ 프로그램 종료 확인]            ");
        System.out.println("=======================================");
        System.out.print(" 정말로 프로그램을 종료하시겠습니까? [Y/N]: ");
        String answer = sc.nextLine().trim().toLowerCase();
        System.out.println("----------------------------------------");
        return answer.equals("y");
    }
}
