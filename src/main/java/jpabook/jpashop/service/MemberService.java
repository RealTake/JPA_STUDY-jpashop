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
}
