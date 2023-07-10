package jpql;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);

            member.setTeam(team);

            em.persist(member);
            em.flush();
            em.clear();
//////////////////////////////////////////////////////////////////////////////////////////////////
            String query = "select m from Member m ";
            em.createQuery(query,String.class)
                    .getResultList();
//////////////////////////////////////////////////////////////////////////////////////////////////
            // CASE 식
//            String query = "select coalesce(m.username, '이름 없는 회원') from Member m ";
//            String query =
//                    "select " +
//                            "case when m.age <= 10 then '학생요금'" +
//                            "     when m.age >= 60 then '경로요금'" +
//                            "     else '일반요금' end " +
//                            "from Member m";
//            em.createQuery(query,String.class)
//                    .getResultList();

//////////////////////////////////////////////////////////////////////////////////////////////////
            //타입 표현
//            String query = "select m.username, 'HELLO', true from Member m" +
//                    " where m.type = :userType";
//            em.createQuery(query, Member.class)
//                    .setParameter("userType", MemberType.ADMIN)
//                    .getResultList();

//////////////////////////////////////////////////////////////////////////////////////////////////
            // 페이징 API
//            List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(10)
//                    .getResultList();

//////////////////////////////////////////////////////////////////////////////////////////////////
            //프로젝션 - 여러 값 조회
            // new 명령어로 조회
//            List<MemberDto> resultList = em.createQuery("select new jpql.MemberDto(m.username, m.age) from Member m", MemberDto.class)
//                    .getResultList();
//
//            MemberDto memberDto = resultList.get(0);
//            memberDto.getAge();
//            memberDto.getUsername();

//            List<Object[]> resultList = em.createQuery("select m.username, m.age from Member m").getResultList();
//            Object[] objects = resultList.get(0);

            // Object[] 타입으로 조회
//            List resultList = em.createQuery("select m.username, m.age from Member m").getResultList();
//            Object o = resultList.get(0);
//            Object[] result = (Object[]) o;
//////////////////////////////////////////////////////////////////////////////////////////////////

            //조인은 명시적으로 하는게 좋다
//            List<Team> resultList = em.createQuery("select m from Member m join m.team t", Team.class)
//                    .getResultList();

//            // 반환 타입이 명확할때 TypedQuery
//            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
//            // 반환 타입이 명확하지 않을 때 사용 Query
//            Query query1 = em.createQuery("select m.username, m.age from Member m");
//            // 리스트 일떄
//            List<Member> resultList = query.getResultList();
//            // 값이 한개
//            Member result = query.getSingleResult();
//            for (Member member1 : resultList) {
//                System.out.println(member1);
//            }

            //파라미터바인딩 where문
//            em.createQuery("select m from Member m where m.username = :username", Member.class)
//                    .setParameter("username", "member1")
//                    .getSingleResult();
//            System.out.println(member);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
        emf.close();
    }
}
