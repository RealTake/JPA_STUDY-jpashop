package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

   /**
    * 회원가입
    * */
   @Transactional
   public Long join(Member member) {
       validateDuplicateMember(member); // 중복 회원 검증
       memberRepository.save(member);
       return member.getId();
   }

    private void validateDuplicateMember(Member member) {
        List<Member> result = memberRepository.findByName(member.getName());
        if (!result.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


    /**
     * 전체 회원 조회
     * */
    public List<Member> findMembers() {
       return memberRepository.findAll();
    }

    /**
     * 회원 조회
     * @param id
     * @return
     */
    public Member findOne(Long id) {
       return memberRepository.findOne(id);
    }

    /**
     * 유저 이름 데이터 수정
     * @return cqs를 적용해 다른 리턴값은 없다.
     */
    @Transactional
    public void update(Long id, String name) {
        // Dirty Checking 을 이용하여 수정하는 것이 좋다.(merge 대신)
        final Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
