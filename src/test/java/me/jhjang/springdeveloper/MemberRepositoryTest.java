package me.jhjang.springdeveloper;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest //@Transactional
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Sql("/insert-members.sql")
    @Test
    void getAllMembers() {
        List<Member> members = memberRepository.findAll();

        Assertions.assertThat(members.size()).isEqualTo(3);

    }
    @Sql("/insert-members.sql")
    @Test
    void getMemberById() {
        //given

        //when
        Member member = memberRepository.findById(2L).get();
        //then
        assertThat(member.getName()).isEqualTo("B");
    }
    @Sql("/insert-members.sql")
    @Test
    void getMemberByName() {
        //given

        //when
        Member member = memberRepository.findByName("C").get();
        //then
       assertThat(member.getId()).isEqualTo(3);
       }

    @DisplayName("레코드 삽입 테스트")
    @Test
    @Transactional
    void saveMember() {
    //given
    Member m = new Member("jhjang");
    //when
    Member savedMember = memberRepository.save(m);
    // 1. Member 객체 m에 primary key인 id가 없으면:
        // insert into member(name) values('jhjang')
        //2. Member 객체 m에 primary key가 이미 설정되어 있으면:
        // update member set name = 'jhjang' where id = 1;
        // save 메서드가 성공하면 삽입된 또는 update 된 레코드를 Member 객체로 반환
    //3. retur new Member(부여된 id,"jhjang");
    //then
    //Oprional<Member>
    assertThat(savedMember.getId()).isNotNull();
    Long id = savedMember.getId();
        Optional<Member> result = memberRepository.findById(id);
    //MemberRepository의 findById() 메서드는
        // 1. select * from member where id = :id
        // 2. return mew Optional<Member>(1L,"jhjang");
        Member member = result.get();
        String name = member.getName();
        assertThat(name).isEqualTo("jhjang");
//    assertThat(memberRepository.findById(1L).get().getName()).isEqualTo("jhjang");

    }
    @DisplayName("2개의 레코드를 한 번에 삽입하는 테스트")
    @Test
    void saveMembers() {
        //given
        List<Member> members = List.of(new Member("HongGillDong"),
                new Member("Park MunSu"));
        //when
        memberRepository.saveAll(members);

        //then
        assertThat(memberRepository.findAll().size()).isEqualTo(2);
    }

    @Sql("/insert-members.sql")
    @DisplayName("레코드 삭제 테스트")
    @Test
    void deleteAll() {
        //given : sql 애노테이션

        //when
        memberRepository.deleteAll();
        //then
        assertThat(memberRepository.findAll().size()).isZero();
    }

    @Sql("/insert-members.sql")
    @DisplayName("Update Test")
    @Test
    void update() {
        //given
        Member member = memberRepository.findById(2L).get();
        //when
        member.changeName("jhjang");
        //memberRepository.save(member);
        //then
        assertThat(memberRepository.findById(2L).get().getName()).isEqualTo("jhjang");
    }
}
